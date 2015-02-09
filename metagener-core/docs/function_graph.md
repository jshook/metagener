The Function Graph
==================

Each function graph has a logical representation which shows how data is transformed from the original whole number value to the output. Each unique path from the start node to leaf node represents a composed function which maps a whole number to a value.


## Basics

Consider the function graph below. It introduces the functional syntax as well as the data-flow-first representation which is favored in metagener.

![Basic Function Graph](graph1.png "Basic Function Graph" "width:350px;float:left;")

The arrows follow the order in which functions are applied to the inner-most data of a function. This representation also allows for the visual arrangement of a related set of functions which can map directly to a realized data source in the runtime. The function names represented by capital letters while the function signatures are captured by the (input)=>result notation. Notice that each function has the same input type as the result type of the function feeding it. All functions must have a compatible type signature with the other functions that they are composed with. 
The arrows follow the order in which functions are applied to the innermost functions first. This representation also allows for the visual arrangement of a related set of functions which can map directly to a realized data source at runtime. The function names represented by capital letters while the function signatures are captured by the input=>result notation. Of course, all functions must have a compatible type signature. Notice that each function has the same input type as the result type of the function feeding it.

Consider the composition of function __E(long)=>string__ around __A(long)=>long__. This results in the composed function function __E(A(long)) => string__. The graph shows 3 different end results, represented canonically as composed functions. They are __C(A(long))=>long__, __E(A(long))=>string__, and __D(B(long))=>string__. Each of these composed functions will be described as a ___function pipeline___. What each function actually does to yield its value is not covered. The focus here is in how they can be composed from the graph representation.

The whole graph captures the relationships between different kinds of composed functions with a common starting input. It is easy to see the lineage and common branching points of function definitions in this form.  It mirrors the way that you can think of building generator functions with metagener. The importance of this will become more evident further on.

## Configuration to Runtime

There are distinct phases of construction of a function graph and its associated outputs, called ___samplers___. These are:
0. defined
1. parsed
2. resolved
3. instantiated

In practice, the initial function will be a basic sequence generator. This serves  to provide a default ordering that is well-understood by data consumers.


#### Runtime Construction

The original source of data above is simply a sequence or stream of long values. To simplify, this will be represented by a function with an input type of unit, which represents an empty type. This means that every node in the function graph, even the common input, can be represented in terms of a function, although we are stretching the definition a bit.

As well, If all of the functions are pure, with no side-effects or internal state, then we can build instances of the composed functions at run-time which are highly parallel.

![Runtime Function Graph](graph2.png "Separated Function Graph" "width:450px;float:left;")

Applying both of these ideas to the above graph yields the one shown here. This not only represents the logically separated function pipelines, but also the way that they will be composed at runtime.

## Function Parameters

So far, the examples are more about composed functions or processing pipelines, depending on your vantage point. These examples have not shown real functions that you might use in a practical function graph. The following example will show a practical generator graph. At this stage, the example syntax will drop the input type, since it is implicit in the result of the feeder function. More importantly, this one will show the initial parameters of each function.


## Function Parameters

So far, the examples are more about composed functions or processing pipelines, depending on your vantage point. These examples have not shown real functions that you might use in a practical function graph. The following example will show a practical generator graph. At this stage, the example syntax will drop the input type, since it is implicit in the result of the feeder function. More importantly, this one will show the initial parameters of each function.


## Goals

So what does such a simple set of primitives give us? From basic functional principles, we arready know that the input for a given composed function will yield an idempotent result, so long as the functions are pure functions. So far, there is nothing really special going on here.

In order to explain the function graph approach, an example is in order:

![Example graph](graph3.png)

There are a few things going on here that we haven't seen before. 

### Fields

First, there are some outputs which have names. Those have been marked with slanted boxes. These nodes represent variable names to which the results of each composed function will be assigned. They are called _fields_ in metagener.

The four fields together might constitute the fields of an object, 
Notice that the user_height field is produced by a function that does not have a primitive result type. A function can theoretically 

### Sampling

By using a robust hash function in place of the usual RNG, it is possible to combine functional determinism with statistical distributions in a useful way. To be fair, _deterministic sampling_ is an oxymoron. There are specific reasons for combining these two elements, as well as some explanation as to how this works is below.

Firstly, idempotent (as with pure functions) results for a given initial input allow for types of testing that can only happen when you can test knowable generator values against previous behavior. As well, it allows for work to be cleanly divided among systems when needed for scale testing.

On the other hand, the ability to use well-known statistical language for describing data sets and behaviors is essential. It is necessary to be able to vary field values across entities or objects according to a known distribution.

#### Sampling methods

Most statistical libraries are built with the notion that you needn't have a say in the RNG input apart from providing an existing RNG implementation. In metagener, we take control of the inputs for statistical samplers, and configure the function pipeline for pseudo-random yet deterministic results as desired. This doesn't change the way that the statistical sampler works apart from exposing some of the plumbing. The values generated by a statistical sampler function will vary according to the distribution, so long as it is given a random distribution of inputs. In this case we use the result of a suitable hash function. Since well-behaved hash functions have high dispersion, the result of hashing over sequences still appers random enough for use in statistical samplers based on inverse CDF methods. However, the values generated for a particular input to the function graph will be idempotent. This allows for reproducible, deterministic, yet statistically defined data sets. It also allows you to do whatever you want with the input to the distribution function,  such as slewing proportionally over the range of possible inputs based on a population, producing samples which represent an even cross-section of the distribution. 

#### Sampling Populations




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
