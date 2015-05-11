# Synopsis

The ability to combine a general purpose data generation tool with a variety of testing tools would be a significant win for us. The current Metagener work is not quite ready for this. This document is an attempt at describing the future state in which not only Metagener becomes useful to multiples client runtimes, but also that these client runtimes becomes usable in their own special way within a general purpose test harness.

## Metagener Integration

Although Metagener will allow many subtle and interesting generation recipes, the first requirement for it to come more pervasive is that it integrates easily. This requires that it starts with the most basic interface which is usable to other runtimes. That interface will simply be a mapping layer which can act as a generator function factory. You pass into it a generator clause, such as "murmur3(); pdist(pop=5000,uniform)" and it gives you back a generator that will return a random long between 1 and 5000.

This can be wrapped within a factory layer which can ensure that each uniquely defined function is instantiated only once and then shared between all calls for the same named function. This has the caveat that sometimes you want each call or thread to have its own unique instance of a given function. This can be achieved with a different type of wrapper which uses a thread-local function map as above. In certain cases, the threading model used by the programmer and the instancing model used by the generator function factory could be out of sync. It might be worth creating a primitive that is used to directly tag the instancing model. This might also be better kept at the wrapper level. This particular aspect of function construction will be known as the instancing model.

Also, for some integrations, metagener recipes might take qualifying data from the wrapping runtime, such as cqltestclient. In this case, instead of specifying the population of an entity directly, the population might be handed in as a qualifier to the execution plans. In any case, the generator functions should be converted to use named parameters in the generator's context so that the inputs are configurable regardless of the language type. This can support the goal of making the metagener core operate as a general purpose function compositor, while also allowing a wrapping language to deal with entities, samplers, etc...

There should also be a metagener init block format that can be embedded in other tools so that common parameters can be initialized. This should be a simple line-oriented format which can be handed into an active metagener context before function definitions that depend on it are evaluated.
Metagener Functions

A metagener function, notated herein as mf(), should be able to handle all types of inputs. These include:
- simple values of various types
- references to named values
- functions which evaluate at initialization time
- functions which are evaluated when the function is called
- references to other functions to be evaluated at initialization time
- references to other functions to be evaluated when the function is called

In other words: parameters must be known as:
- direct or indirect: definitions, or references to definitions
- values or functions
- call-time or initialization time

In order to support this strategy, the following conventions will be used in metagener core:
1. All functions will use functions as parameters.
2. The type of function which is passed in will be synthesized based on the definition for the parameter:
3. Simple functions which return a fixed value of the required type.

This approach will delegate some of the optimization of the runtime to the JVM.

## Metagener Function Syntax

In order to facilitate the type-safe construction of more complex functions, parameters need to have their own type model. Each function will provide a model which enumerates the valid instancing signatures. The implementation of the current "EntityDefAware", "SamplerDefAware", and "*StackAware" will likely be subsumed by these instancing signatures.

For example, a function which currently implements EntityDefAware will instead provide an InstancingSignature which takes an EntityDef parameter. Even better, it could simply take a named parameter of type long, named "pop".

This is an attempt to provide a more dynamic form of composition which can work with other dynamic languages and bindings, such as Nashorn, which developers may wish to integrate in the future.

Named parameters have an order of resolution. First, if the value is provided directly by a named parameter in the function def, as in "murmur3(seed=234)", then the seed parameter takes precedence over all other possible parameter resolutions. Second, if the seed parameter is not named in the argument list, the environment is searched for a named variable of the correct type. If it is found, a function is synthesized to pass in which always returns that value.  Third, if a named function with a matching name is found in the environment, it is called to provide the value.  This includes functions which are defined in the current generator block.

Note: The distinction between functions which are defined and referenced as global functions vs. those which are meant to be used as pure functions is not clear yet. This needs to be discussed and specified, at least for documentation purposes. There ideally should be no difference between the two, given the benefits of pure functions.  However, it is likely that some designers will want to design their own generator functions to be used at generator initialization time.

In the hierarchy of function definitions, there are certain invariants which must apply to keep the generator context valid. All functions within a generator block must be input compatible with the output type of the enclosing block. All references to other functions or parameters must be qualified.

A generator block may be accessed as a flyweight value by the client. This means that any preceding functions which are on the path to the named block are applied and the resulting value is stored in memoized form with the block reference.. This is essentially a partially-applied function, with the inner block functions each representing a different way to complete a composed function. It is the same as currying, except that the ways that you may qualify the composed function are known in the context. This is an important distinction which merits explanation. The point of having a partially applied result is to allow for common precedent functions to be applied once within the access scope of the block values. This also enforces a common functional value among the block generator functions. As well, this provides simple ancestor semantics at the api level. This model subsumes the current entity/field/sampler model entirely.

As an optimization, it might be desirable to provide a dedicated stream endpoint for a certain level of block values. This can be in either auto-streamed input form, or as a simple function.

If the composition of block or field values with the associated input stream can be left to another layer, it might simplify the implementation of such.

##Architectural Layers
1. function definition language -- provides a concise and obvious representation of functions, blocks, types, references, etc.
2. type conversion system -- provides conversion functions for common types and proper signaling for conversion errors
3. function compositor -- Allows functions to be composed in generic ways.
4. function block compositor -- Allows function blocks to be composed in generic ways.
5. stream compositor -- Allows clients to instantiate top-level generator functions, with or without sequence qualifiers.

Each of these layers will be described in more details below.

##Function Definition Language
This is a refinement of the metagener scripting language, circa 2015-05-01. It will contain syntactical elements to support the function and parameter and values described in this document.

##Type Conversion System
The type system will simply know how to convert one value type to another, including function types. It should be able to provide simple conversions between known types. It should be able to provide  conversion functions for a requested conversion. It should be able to signal errors for both in an obvious way.

##Function Compositor
The function compositor will take multiple functions in a linear sequence and map them together in lambda form. In some cases, parameters from the function environment may be captured to synthesize a static function, which implies closure form. It should be made very clear that this type of closure is not open to random inclusions. The captured parameters will be limited to only those parameters which are available in the current function block, it’s parent block, and so forth up to the main function block.


## Block Structure
A metagener definition is a sequence of blocks which may contain arbitrarily nested sub-blocks. In this way, the structure of a metagener definition is strictly hierarchical. The outer-most block is anonymous, but behaves exactly the same as inner blocks with respect to name resolution and function processing. Names of functions are thus hierarchical. Using dotted notation, a function block may be addressed in a global way. Consider the following example:

    function1 {
      function2 {
      }
    }

    function3
    {
    }

In this example, function1 and function3 are member functions of the outer-most block. Within block function1 is another, function2. As well function2 is the name of an inner block. The full name of the inner-most block is function1.function2.
Block Inheritance

Within each block, any references to names which are defined within any parent block are valid. The order of resolution starts at the referencing block and continues upwards to the main block.

## Named Parameters
When a function is named, it may also be initialized with named parameters. For example:

    function1 pop=1000 {
    }

This represents a named function “function1” which has a named parameter “pop” with value 1000. Any reference to a parameter named pop is valid within the named block.

## Blocks, Functions, Parameters
The relationship between these primitives may not be clear. In effect, each block must have a function defined. This means that each named block represents a named function. Consider:

    function1 pop=1000 <- identity() {
    }

This is the explicit version of the default block function. This means that every path from the outer-most block to the inner-most function can be represented by a composed function.

TODO: Use the phrase “block function” to replace all other vague terms.

