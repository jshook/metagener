# Metagener Concepts

Understanding the conceptual basis for metagener is the first step to building recipes or extending the core metagener libraries.

## Generator Functions

Generator functions are the heart of metagener. Most of the metagener tools are meant to make using generator functions as easy as possible. 

From a high level, a metagener configuration can be thought of as a set of function templates which can be accessed by name and used in convenient groups. These function templates are organized into a directed graph according to a metagener definition.

Metagener builds field generator functions by traversing a function template graph, navigating node-by-node to the requested output, instantiating and composing a total field generator function along the way.

This field generator function is dependent on the field definition, including type, the entity definition, and the sampler definition through which it is accessed.

## Metagener Defs

A metagener definition is the configuration of an instantiable metagener context. The context provides the runtime API which is used by client code, or by services which expose metagener in a different way. The definition has its own object model and supporting syntax. 

A metagener service can be created with the core metagener API as long as you have a valid metagener def. This can be constructed as a MetagenerDef object using the fluent API, or more preferably, it can be parsed from a metagener recipe.

Although the function graph template approach is very powerful, it is also very generic and abstract compared to the conventional data modeling terms. In order to match impedance with the practice of data modeling, some of the conventional terms have been adopted into the definition syntax. As well, some mechanical conventions have been adopted which enforce some useful types of modularity in metagener defs. The notions of _entity_, _field_, and _sampling_ are all used directly, and work in the way that you might expect.

The remainder of this section introduces these as configuration primitives and explains how they are meant to be used together.

### Entities

	// define an entity named 'user', with population 1000
	entity user pop=1000

An entity is a basic template for an object type. The word _entity_ was chosen because it conveys the semantics of identity, cardinality, and logical flow within applications. An entity has a population-- the number of distinct entities which can be generated of that named entity.

### Fields

	entity user pop=1000
     field username:text
     field user_id:int

Fields are what give substance to an entity. In other words, entities are unable to generate values by themselves. All generated values in metagener are _generated field values_.

### Field Generator Function

	entity user pop=1000
     field username:text <- filesample(users.txt,uniform)
     field user_id:int <- identity()

Each field has a generator function template. Above, the username field has a generator function template with initial parameters 'users.txt' and 'uniform'. These are initializers for the function instance, not parameters to a function call. This means that filesample is a parameterized function. Functions are only useful if they can take an input. In metagener, you do not specify the input. It is always the output from the previous function. Since this is the case, our function templates contain only initial parameters.

If no generator function is defined, then a default generator function will be assigned according to the field type. Default functions do little more than box the input type into the field's value type.

In the example above, only a single function is shown for each field. However, it is possible to assemble multiple function templates together. There are multiple ways to achive this, as described below.

#### Chained Function Template

	entity address pop=1000000
     field streets:text <- filesample(streets.txt,uniform); suffix(' Street');

This is an example of a chained function template. It represents a pipeline of functions from left to right, data-flow style. This method is easy to follow, less visually busy than traditional composed function formats. The chained function template is the preferred form for defining field generators. It is much easier to see the step-wise mapping of data in this form than one which is solely dependent on many nested parenthesis.

#### Composed Function Defs - not implemented yet

The above chained function def could be expressed in a more traditional way:

	entity address pop=1000000
     field streets:text <- suffix(filesample(streets.txt,uniform),' Street');

This form might appear more familiar to anyone who has ever written in a C-like language. This is not the prefered form for most basic generator function templates in Metagener. However, it will be implemented in order to allow for higher-order functions to be more easily constructed.

Eventually, you'll be able to build functions like this:

	// generate a set of strings between 1 and 5 elements in size, with
    // uniform sampling from the lines in foods.txt
	field favs:set<string> <- genset(1-5,filesample(foods.txt,uniform)); logevery(100);

This represents a combination of both the chained compositional form with the nested compositional form. Once nested functions are supported, this type of construct will be allowed.
    
    
### Sampler Defs

	sampler user_samples : user

A sampler is the only way to define a service endpoint. Entity and associated field definitions only provide part of the information needed. In essence, a sampler is a named stream of entity samples. The above definition should be read as "sampler named _user_samples_, from entity named _user_".

Sampler defs control how you sample an entity. Remember when we discussed how field generator function defs are only function templates? This becomes important when you need to know the final composed function for generating a field value. Samplers serve two main purposes:
1. They allow an entity to be accessed or sampled in multiple ways without having to redefine the entity for each one.
2. They allow you to customize the population sampling function for each defined sampler.

Consider the following sampler def:

	sampler user_samples : user <- pdist(geometric,0.8)

This shows a sampler of the user entity which has a statistical sampling function. Specifically, it uses a goemetric distribution which is configured with 0.8 as a parameter of the distribution.

So what is happening behind the scenes? Metagener uses field generator functions to generate values, given an input known as the _entityid_. We do not address the entityid directly, but we know that it is the input to our field generators. That means that each field generator will produce a stable value for a given entity id by default. This is usually the most desirable behavior for a field generator function. There are cases in which you want to change this up, and that is explained later.

So, the field generator function takes an entity id and provides a field value. What is responsible for determining the entityid which is fed to the field generator funcntions? That would be the _entity sampling function_. The pdist(...) function above is just an example of that.

Putting the entity sampler function (as defined on a sampler) and the field generator functions together, the total generator function that is constructed for a field follows this basic flow (in chained function format):

    sampleid -> entity_sample() -> entityid -> field_generator() -> field value

or, in composed form:

	field_value = field_generator(entity_sample(sampleid));

## Tying it all together

All of the relationships described above are easily modeled as a function graph.  The entry point to the graph is the common sampleid input. Each node in the graph below that corresponds to a defined sampler. Below each sampler is a set of field generator functions, taken from the entity which is targeted by that sampler.

Metagener will probably become more of a general purpose function graph engine at some point, but this is really anecdotal for now. The entity/field/sampler scheme will still be in place, although others may become available as the core engine becomes more generalized.

