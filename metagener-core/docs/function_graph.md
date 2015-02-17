The Function Graph
==================

# STATUS

This documentation is in active development, so do not consider it ready for use at this time.

Each function graph has a logical representation which shows how data is transformed from the original whole number value to the output. Each unique path from the start node to leaf node represents a composed function which maps a whole number to a value.


## Basics

Consider the function graph below. It introduces the functional syntax as well as the data-flow-first representation which is favored in metagener.

![Basic Function Graph](graph1.png "Basic Function Graph" "width:350px;float:left;")

The arrows follow the order in which functions are applied to the inner-most data of a function. This representation also allows for the visual arrangement of a related set of functions which can map directly to a realized data source in the runtime. The function names represented by capital letters while the function signatures are captured by the (input)=>result notation. Notice that each function has the same input type as the result type of the function feeding it. All functions must have a compatible type signature with the other functions that they are composed with. 
The arrows follow the order in which functions are applied to the innermost functions first. This representation also allows for the visual arrangement of a related set of functions which can map directly to a realized data source at runtime. The function names represented by capital letters while the function signatures are captured by the input=>result notation. Of course, all functions must have a compatible type signature. Notice that each function has the same input type as the result type of the function feeding it.

Consider the composition of function __E(long)=>string__ around __A(long)=>long__. This results in the composed function function __E(A(long)) => string__. The graph shows 3 different end results, represented canonically as composed functions. They are __C(A(long))=>long__, __E(A(long))=>string__, and __D(B(long))=>string__. Each of these composed functions will be described as a ___function pipeline___. What each function actually does to yield its value is not covered. The focus here is in how they can be composed from the graph representation.

The whole graph captures the relationships between different kinds of composed functions with a common starting input. It is easy to see the lineage and common branching points of function definitions in this form.  It mirrors the way that you can think of building generator functions with metagener. The importance of this will become more evident further on.

In practice, the initial function will be a basic sequence generator. This serves  to provide a default ordering that is well-understood by data consumers.

## Configuration to Runtime

A definition of a generator function graph is separate from an instance of it. The act of building a function graph is a different sort of activity than that of consuming its generated data. The metagener runtime acknowledges this by decoupling the representation of a function and instance of it into separate phases. This is realized simply by using a distinct model to hold the graph definition. The configuration type is known as a 'MetagenDef', and all the constitutent types that make it follow the same naming convention.

The runtime API that you read generated data from is constructed as a new GenContext with an instance of a MetagenDef. By default, the generator context will instantiate function pipelines only when they are accessed. In this way, it is possible to create a function library that has many definitions without paying a penalty for those that you do not use in a particular scenario.

The separation of the configuration model from the runtime implementation has other benefits as well. You can create a generator context with any valid MetagenerDef, no matter how you created it. The configuration methods provided by default include:

- Programmatic (Fluent) API
- Metagener DSL
- YAML

It may be desirable to allow the generator context to load and unload definitions dynamically at runtime, but this hasn't been implemented in the current toolset. This means that you have to have a whole MetagenDef in hand before acquiring a generator context.

#### Runtime Construction

The original source of data above is simply a sequence or stream of long values. To simplify, this will be represented by a function with an input type of unit, which represents an empty type. This means that every node in the function graph, even the common input, can be represented in terms of a function, although we are stretching the definition a bit.

As well, If all of the functions are pure, with no side-effects or internal state, then we can build instances of the composed functions at run-time which are highly parallel.

![Runtime Function Graph](graph2.png "Separated Function Graph" "width:450px;float:left;")

Applying both of these ideas to the above graph yields the one shown here. This not only represents the logically separated function pipelines, but also the way that they will be composed at runtime.

## Function Parameters

So far, the examples are more about composed functions or processing pipelines, depending on your vantage point. These examples have not shown real functions that you might use in a practical function graph. The following example will show a practical generator graph. At this stage, the example syntax will drop the input type, since it is implicit in the result of the feeder function. More importantly, this one will show the initial parameters of each function.

## Goals

So what does such a simple set of primitives give us? From basic functional principles, we already know that the input for a given composed function will yield an idempotent result, so long as the functions are pure functions. So far, there is nothing really special going on here.

A practical function graph:

![Example graph](graph3.png)

Runtime decoupling:
If we take the pipelined function as they are shown from top to bottom, we have composed functions:

- first_name = genfname(idhash(longs()))
- last_name =  genlname(idhash(longs()))
- user_height = genheight(gaussian(idhash(longs())))
- join_date = gentimestamp(longs())

Taken together, these outputs constitute the values associated with a 

The point of looking at these as a graph is to see the outputs in terms of how they are related. The first three named outputs are derieved idempotently from something that has a common value. These are called field functions. If we designate idhash() as the function which provides an entity id, then we can treat theese downstream functions as field functions. Also, we can control the cardinality of the whole set of sampled data simply by controlling the cardinality of the input. If you get 57 different outputs from longs(), you will have 57 distinct values of the output fields sets. As well, since we are using a hash function with high dispersion, we do not have to rely on the randomness of outputs from longs() to have effective randomness at the entity level. We might as well use a sequence of numbers. In fact, that we can use such a sequence is part of the design of this particular graph, since we might want to go back and access the 54th entity many times and still get the same results.

There are a few things going on here that we haven't seen before.

#### Fields

First, there are some outputs which have names. Those have been marked with slanted boxes. These nodes represent variable names to which the results of each composed function will be assigned. They are called _fields_ in metagener.

## Sampling & Determinism

By using a robust hash function in place of the usual RNG, it is possible to combine functional determinism with statistical distributions in a useful way. To be fair, _deterministic sampling_ is an oxymoron. However, there are specific reasons for combining these two elements. The motives for this are explained below along with some explaination of how it works.

Firstly, idempotent generator results (as with pure functions) allows for a variety of approaches to testing, some of which have been difficult in the past.  You can easily determine if an object or record has changed without having to record the full state in the interim. The sampleid acts much like a handle to a memoized generator result, in the sense that it will always provide the same result for a given value. For example, it will be possible, easy even, to ask "Has user 72 been modified from the original value?" without actually knowing the before and after values. This encodes the expected state of a particular element simply with a number: the __sampleid__.

As well, breaking the test space apart by sampleid will allows for work to be cleanly divided among systems when needed for scale testing. The coherent graph of possible output, in combination with the __sampleid__ access patterns, allows for a method of dividing a test into logical units which can be executed concurrently and in a coordinated fashion.

On the other hand, the ability to use well-known statistical language for describing data sets and behaviors is essential. It is necessary to be able to vary field values across entities or objects according to a known distribution. There are various statistical libraries which provide distribution sampling functions, but they don't generally work the way we need for this approach.

#### Sampling methods

Most statistical libraries are built with the notion that you needn't have a say in the RNG input apart from providing an existing RNG implementation. In metagener, we take control of the inputs for statistical samplers, and configure the function pipeline for pseudo-random yet deterministic results as desired. 

This doesn't change the way that the statistical sampler works apart from exposing some of the plumbing. The values generated by a statistical sampler function will vary according to the distribution, so long as it is given a random distribution of inputs. If you are able to control the uniform input that is used by the distribution sampling function, then you can turn it into a composed function.

In this case we use the result of a suitable hash function, converting it's result into a compatible RNG input for the distribution sampling function. This means scaling it to the unit interval 0.0 - 1.0. Since well-behaved hash functions have high dispersion, the result of hashing over sequences still appers random enough for use in statistical samplers based on inverse CDF methods. However, the values generated for a particular input to the function graph will be idempotent. This allows for reproducible, deterministic, yet statistically defined data sets. It also allows you to do whatever you want with the input to the distribution function, for better or worse. Generally speaking, you won't need to do much with this mechanism. It is built-in to the library functions that you use in the function graph.

#### Sampling Populations

The __sampleid__ is simply the input to each function pipeline when you need a result. When you access a generator value, you do it by asking a sampler for the next entity, which is just a way to memoize the sampleid for a given iteration. You can specify the sampleid if you like. If you do not specificy the sampleid, the sampler will automatically use the next value, incrementing by 1 each time.

Each sampler is configured for a specific defined entity. That means that the sampler is aware of the entity's population. Each sampler can also have an associated pertubation function which is applied to the sampleid at the beginning of the function pipeline. This allows you to select a sampling distribution (without replacement) from the original population.

## Examples

    entity sensor_event pop=1000000000
     field ts:timestamp <- sample; daterange(2015,2017)
     sensor_id:int      <- entity; range(1-1000)

## Function Names

As of this release, all functions are located within the metagener-core runtime. For commonly accessed functions, the FieldFunctionName enum holds a terse name that is easier to work with syntactically. For example, instead of having to say _PopulationSampler_, you can simply use _dist_. In the future, the function library will be much more extensible, allowing you to provide your own functions via drop-in libraries.

# TODO:

Put a visual explanation here showing sampling functions being injeted into the logical function pipeline.

### Flexible Structure

The graph shows one field "join_date" as having a completely different composed function than the others. Te 


	#direction: right
    [<input> long sequence]

    [long sequence] -> [func A(long=>long)]
    [long sequence] -> [func B(long=>string)]
    [func A(long=>long)] -> [func C(long=>long)]
    [func B(long=>string)] -> [func D(string=>string)]
    [func A(long=>long)] -> [func E(long->string)]


    #direction: right
    //[<start> start] -> [long sequence]

    [func()=>long for AC] -> [func A(long)=>long for AC]
    [func A(long)=>long for AC] -> [func C(long)=>long for AC]

    [func()=>long for AE] -> [func A(long)=>long for AE]
    [func A(long)=>long for AE] -> [func C(long)=>string for AE]

    [func()=>long for BD] -> [func B(long)=>string for BD]
    [func B(long)=>string for BD] -> [func D(string)=>string for BD]
    
    
    #direction: right

	// example
    [func longs()=>long]
    [func longs()=>long] -> [func idhash(long)=>long]
    [func idhash(long)=>long] -> [func genfname(long)=>string]
    [func genfname(long)=>string] -> [<input> first_name]
    [func idhash(long)=>long] -> [func genlname(long)=>string]
    [func genlname(long)=>string] -> [<input> last_name]
    [func idhash(long)=>long] -> [func gaussian(long)=>float]
    [func gaussian(long)=>float] -> [func genheight(float)=>height]
    [func genheight(float)=>height] -> [<input> user_height]
    [func longs()=>long] -> [func gentimestamp(long)=>datetime]
    [func gentimestamp(long)=>datetime] -> [<input> join_date]

### Terms

#### function pipeline

A set of functions which can be composed into a single function, found by following a directed function graph from the common input to one of the leaf nodes.

#### context

A context holds a metagener graph definition. From it, you can access samplers and the associated entitydef and samplerdef data.

#### sampler

The runtime object which is used to access values from a named output.

#### samplerdef

The configuration data which represents a sampler.

#### entity

A named aggregation type in a metagener definition. Analogous to record, object, struct, ...   The name entity was chosen because of it's similarity to the same term in database design, although it isn't exactly the same. For some users, it might actually be the same.

#### entitydef

The configuration data which represents an entity.


### syntax conventions

Function pipelines may be specified without the trailing semicolon. However, individual functions which make the pipeline segment must be delimited with a semicolon.
For now, there is no special treatment of escape characters.

Function specs which are called with no arguments (taking the defaults for any parameters) can be called as _func;_ or _func();_. Function specs which are called with a single argument may pass it without naming the parameter. However, function specs which are called with multiple parameters must be called with named parameters. As well, functions must be able to receive a Map<String,String> in order to support this. The reason for the verbosity is two fold:
1. To encourage the construction of finer-grained functions which can be combined in more flexibile and powerful ways.
2. To avoid confusion when looking pipeline fragments in syntax.

For example:

    func joindate  entity; datetime(1997-2015); dateformat(%T)

seems much clearer than

    func joindate  entity; datetime(range=1997-2015, format=%T)

While this is debatable, the first method follows a simple functional pattern that is also easy to follow visually. It also means that the dateformat(...) function can be used in other places.
