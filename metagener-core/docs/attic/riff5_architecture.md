# Architecture

The first design goal of dadoset is this: It must be able to run empty simulations, using stubbed/dummy delegates and diagnostic probes only. This is a "Simulation First" approach, which sets aside for the interim the desire to test real clusters. The reason for this approach is twofold:
0. To allow the cleanest abstractions for the core simulation logic to evolve independent of system-specific influences.
1. To set a realistic implementation target that can be demonstrated.

A conceptual model for the simulation tools that is self-describing and cohesive unto itself will better serve the needs of other systems as a useful building block. That's the rationale, anyway.

## Core Concepts

### Stream Processing

Where possible, a stream-processing methods are used. This is in contrast to relying heavily on memory and multi-consumer buffering for speed and consistency. We do not give up speed and consistency, we simply achieve it with a different approach. We simply do not keep full test data in memory. In return we will have a system which is faster to start, more scalable, and conceptually easier to manage.

### Distributed PRNG Seeding

In order to allow the simulation to span multiple nodes without losing the idea of a single self-consistent test, the initialization of randon number generators will be managed in the scope of the whole cluster. Simply stated, if any PRNG is seeded without input from the cluster configuration, then it is operating in the scope of a node. This would be a hindrance to reproducible, distributed results. The technique used is simple. A PRNG with a very high period (Mersenne Twister) is used. At each division of work (node, thread, ...), any RNGS are initialized with seed which are perturbed from the default (for the test) by the logical enumeration of that division. For example, seeds which are used on the 3rd node (as enumerated by the cluster logic), will be incremented by (3*100). The actual details are slightly different, in order to take into account different numbers of nodes and threads. Essentially, the values are perturbed in the most mathematically compact way which avoids overlaps between layers of work, given the current number of nodes, etc..

#### TODO: Fold this into another section:

The RNG initializer function (or js code block) is a function which takes into account the simulation scope, and produces an RNG addend. A universal RNG initializer function should be derived which works across layers of work, even if you aren't at the thread level.

### Data Generation

Data Generators are used to create example data according to statistical models. The flow of data essentially maps to a composed function, which is represented by the dataflow below. For now, assume that every element on the left is a function instance.

![Data Generation](DataGeneration.png)

This is a high-level explanation which leaves out some details. Specificially, configuration of each type of function instance is handled via the DSL SimScript, which is explained later.

### Event Scheduling

Event Block Schedulers are used to prepare blocks of events with their associcated execution times. Event Block Schedulers take as their inputs an AST object representation of a set of event definitions and their relative rates, timing statistics, etc. The default ordering of events within an event block is completely deterministic. The ordering of events can be modified from the default by assigning a randomization function for the block scheduler to use.

TODO: Show multiple event types and interrelations.

![Event Scheduling](EventScheduling.png)

#### Interrelated Events / Operations

Some events in a simulation will relate to other events by way of a relation in the data that they use or modify. An example of this would be a read after a write. If the reads are only meant to be for defined values, then a read may not occor for a datum which has not been written yet. An added complication to such a scenario may be that you do not want the read order to be in the same order as the writes. It may even be the case that you want some proportion of the reads to be for defined records, and others to be for records which do not exist yet.

In order to account for these and other scenarios, the sequencing logic provides a method for compositing multiple stateful PRNG streams together in a deterministic interleave. For self-congruent data between streams, the data path is simply duplicated with the same seed. For the "extra" data that other interleaved operations may use, an additional RNG flow is configured with a different seed, and it is sampled for the interim cycles.

The figure below demonstrates this visually.

![Simulation Network](SimNetRNGs.png)

The red values are produced by two separate instances of a PRNG with the same seed (1), while the blue values are produced by a PRNG with a different seed (2). The accessor process on the left is the only process using the first PRNG, while the writer process on the right uses a congruently configured RNG as well as another separate stream of data.

The purpose of having a separate copy of the stream from seed 1 is to allow for the processes to operate as  independently as possible. For cases in which there are no relative rate constraints enforced on processes, they are free to operate completely independently. The small price of having multiple copies of RNG streams in fact purchases a great deal of simplicity and scalability. Now, any need to synchronize progress state can be dealt with in terms of rates. The alternative would be to introducing speed-killing synchronization or multi-consumer buffering logic.

This approach has two consequences in terms of RNG flow and setup logic:
1. For interrelated data, the RNG flows must not be shared with other consumers.
2. The sampling rotation between the streams has to be managed explicitly. For small multiples of rates, this is easily handled with a block planning approach. Larger (prime!) multiples of rates may require a more nuanced approach.

#### Event Pacing



### Simulation DSL

All of the event scheduling, timing, and data generation logic has to be configured in some way. That is done by way of a DSL which is tailored to the task. The core primitives in the DSL are all about describing data generation recipes, event scheduling, and timing. This is done with statistical models, of course. Also, scripting support is provided by way of a Nashorn sandbox. All of these elements together describe the core engine. They provide enough logic to provide full testing capability independent of system or application-specific logic. For that, other syntax may be mixed in according to system or application level requirements. This means that the DSL has to be extensible enough to accomodate different plugins. The integration points for these are described below.

## Operational Templates

A simulation is comprised of actions taken in a specific order at specific times. So far, we have talked about Events as abstractions for these actions. Actions have to be concrete and specific to the goals of a particular simulation. However, we do not want the core simulation logic to care about the details of a given system or application. That is where the dividing line between simulation events and application-specific operations serves its purpose. However, we have to have a way of mapping the operations of a given system or application into the operational space of the simulation tools we have described thus far. This is achieved with a binding layer with support form the DSL.



## Simulation Planning
Putting it all together...

Events in this system are anonymous binding points for operations. Operations are system-specific, and may be implemented in any manner which makes sense for a given system. The DSL contains recipes, or operation templates within statement blocks. It also contains event groups which allow for multiple recipes to be expressed as part of a concurrent block, with related rates of activity or other statistical tweaks.

A simulation can be planned by combining the results of event scheduling, operational templates, and data generation. Of these 3, only two have been described in detail so far. The third, Operational Templates, will be described below.

The data generator defines specifically *what* details are used for an operation, and the event scheduler decides the order and timing of operations will occur.

## Threading & Concurrency
There are competing concerns between the 

## Core Elements

Figure 1. below shows 
![data generator](relatable_studies.svg)
![control flow threads](control_flow_threads.svg)

### Other details to put somewhere
Some buffering may be necessary in order to assign blocks of data to consumers in an efficient manner. When used, this must not be for the purposes of sharing buffer state with other consumers. In fact, if there are ever multiple consumers, the buffering logic must be at the data source level before it enters into the view of the consumers.
