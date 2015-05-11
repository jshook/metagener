# Getting Started

# __THIS IS A DESIGN DOCUMENT __ -- Implementation means making everything herein "true"

First, you need to define an entity:

	entity userentity

Next, you need to define a field on that entity:

	field username:text

The _username_ field is a field of the _userentity_ field. Every field we define in this way will also be on userentity until we define another entity.

So far, we have no way to sample from userentity. To do that, you need to create a sampler:

	sampler userentity

Now you have the most basic of definitions which can yield some values. You see the results from this by testing with the CLI, assuming you used the filename __getting_started.metagener__:

	metagener.sh --sample userentity --sampleid 1

If you want to see several values, you can use

	metagener.sh --sample userentity --sampleid 1..100

## Changing Default Generator Functions
The definition described above looks like this:

	entity userentity
     field username:text
    sampler userentity

Although the sampler is defined last, it is actually the access point for getting sample values. It is listed last because it merely provides a sample window into a defined entity. You can have multiple samplers per entity.

This wouldn't makes sense with the current definition. There is nothing that would make one sampler different than the other. Let's change that:

	sampler userentity_geometric:userentity <- pdist(uniform)

Now, we have another sampler, named _userentity_geometric_. We had to use a different name than the previous one, which automatially took the name of the sampled entity. This &lt;sampler_name&gt;:&lt;entity_name&gt; format is how you name a sampler and its target entity separately.

Also, we have a sampling function defined for this. We didn't have one before. What that means is that the default sampling function was selected, which was just _identity()_. This particular sampling function, _pdist(uniform)_ is an auto-sizing population sampling function which draws a random entity from the population. It is auto-sizing in the sense that it knows the population size of the target entity, and auto-configures its internal distribution sampling logic for this size.

The _username_ field had no configured generator function either, so it took the default _identity()_ function as well. All generator functions have an initial input of type _long_, so when you have a field of a different type, the generator function will be finished with a type-coercion function if necessary. In essence this would cause the field generator function for _username_ to end with String.valueOf(entityId);

With the above changes to sampler, and the default _identity()_ and type coercion function on the _username_ field, the composed generator function which will generate values for _username_ will be: __String username_value = String.valueOf(pdist(uniform,100))__, and _pdist(uniform,100) gets evaluated as a discrete sampling function which draws values from 1 to 100.

# TODO

Identify what it will take to deal with murmur3 caching, and whether or not to force each sampling function to include their own murmur3() call for the ordinal identity.




