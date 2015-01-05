Configuration
=============

## Entities
Entities are used to describe the name and population of different "record" level elements.
An entity definition contains nothing more than that. A name, and a population. Entities do,
however, have fields. Fields provide the content for an entity, much like fields of a type
provide meaning for instances of it. In metagener, each unique entity is known by an entity id,
which is merely a long value. The fields are generated, by default via hashing against that id.
This allows for idempotent generation for a particular entity type and id. Versions of entities
with changing fields might come later, but that is out of scope for the first cut.

### Entity Configuration
Entities are configured as "entity definitions", or _EntityDef_s, and as "entity" in configuration files.

## Fields
Fields do not exist separate from an entity (for now, at least). Each field is a component of an
entity type, but also has a field name, a field type, and an optional generator function.

### Field Configuration
Fields are configured as "field definitions", or _FieldDef_s, and as "field" in configuration files.

### Field Generator Functions
Internally, fields values are generated using their assigned field generator functions. For every
known field type, there is a default field generator function which simply uses the hashed entity id
as an input and provides the simples rendering of it in the target object type. These are named
'Boxed...'-- BoxedBoolean, for example. In some cases, it isn't actually autoboxing doing the work, but
the name is symbolic of the default behavior, so it is used for all default field generator function
implementations. Just be aware that some of these field generator functions are doing slightly more than
'auto boxing' as we know it in the JVM.

## Samplers
A Sampler is a named sampling points into an entity. This means that each sampler is targeted at
a particular named entity. It also has a sampling distribution, as well as statistical controls
for the chosen distribution.

### Sampler Configuration
Samplers are configured as "sampler definitions", or _SamplerDef_s, and as "sample" in configuration files.

## Generator Context
The generator context is the core state holder for a runtime. It is configured by loading the entity
and sampler definitions into it. These definition are available for interrogation by clients. When a request
is made for a named sampler, an EntitySampler is created, if possible, and cached in the generator context.

## Entity Samplers
The core service type provided by metagener is an EntitySampler. This is the access point for consuming
generated entities. Metagener creates an entity sampler for you when you ask for it by
the sampler name you gave it via a SamplerDef. It is important to note that the entity sampler
and the sampler definitions are conceptually the same, but logically separate. The separation of the entity
and sampler configs allows for late binding, which can support different configuration modes. As well, the
setup of entity samplers is done lazily, so that you don't incur additional overhead simply because you have lots
of samplers defined that you might not be using in a particular scenario.

EntitySamplers allow you to access generated data in a few ways, with a few trade-offs.

### Field Value Tuples
The most efficient, assuming you are actually using all the fields on an entity, is to ask the EntitySampler
to yield a value tuple of all the fields you have defined for it. This provides an array of values as
Object[], and is the fastest way to sample, but not the most modular.

### Entity Samples
When you have an EntitySampler, one of the things you can ask it is to provide the next entity sample.
An EntitySample is a holder of identity for the sample. It also provides a method for accessing generated
field values individually, so long as you know the field names. This method allows you to hold the state
of a single entity sample for a period of time. As well, the field values are only generated when you
access them in this mode, saving some generator processing over the tuple method above. This might be
moot, however, given the additional object lifecycles and map lookups.

## Thread Safety
None of the samplers enforces serial access to the methods that yield a sample. The services provided by this API
are not thread safe by design. If this bothers you, keep reading. If this toolset is used in the way it was
designed to be used, synchronization or other locking mechanisms will not be required. A usage guide will
be assembled at some point to explain what one should strive for as well as what one should avoid. There is also
a longer plan in the works to provide concurrency and division-of-work abstractions over the core metagener
library that will enable effective use of highly-threaded systems.

For now, you can use one of these strategies if you need to support multiple concurrent users of the same generated data:

- Provide a synchronized access point within your own APIs. This is relatively easy to do if you must.
- Create an additional instance of the entity streams in your generator context, coding the names for different purposes.
- Create a separate generator context for each thread that needs to see the same stream.

Future versions of metagener will make this 'Thread Safety' section moot, since it will handle the concurrency and
parallelism concerns in a more cohesive way.

## Stream Oriented, but also Random Access
For the most part, the data generated by metagener is produced in stream fashion. This means that behind the scenes,
the logic is arranged in pipeline fashion, with composed mapping functions around a source of entity identifiers.
In practice, this usually means that once you access a cycle of output, it is gone unless you remember/memoize it. Doing
this is generally at odds with the principles of streaming, but there is a happy medium. For non-randomized entity samples
( entity samples which use the default entity hashing logic and hash-based field generator functions), you can randomly
access any enumerable entity, whether it has been generated automatically or not. This can be useful for verifying the
contents of data that has been written at time A and read at time B, so long as you are addressing them by the same
entity id within your simulation.






