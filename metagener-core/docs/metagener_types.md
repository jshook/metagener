## Metagener Types
___How we hack around Java 8 Oddities___

In order for metagener to operate efficiently, primitive types are used when possible. Metagener also needs to support POJOs of any kind as output, so generics are used to bridge the gap.

Fundamentally, there are two key data types required for the heavy lifting: A primitive discrete scalar and a primitive continuous scalar. Rather than trying to build allthethings in the type system of metagener, we simply use a minimum of the types that we need. For the Java runtime and typical 64-bit architectures, that would be:
* long, for the primitive discrete type
* double, for the primitive continuous type

The practicality of this becomes more obvious when you realize that most hashing methods operate directly with the long type, and that most of the identity semantics are handled best with this type as well. The part of a composed field generator function that converts to the native field type is best left to the last (outer-most) functions.

For the non-scalar generator function results, everything else is an object type. You might need to yield a long, or a double, but you might also need to yield a DateTime. Because of the odd way in which the Java 8 APIs deal with functional signatures, we have to make a compromise to be able to dynamically compose functions which have both primitive inputs and have non-primitive outputs.

This does impose a particular compositional model on the whole composed function. Simply put, the final composed function is of type LongFunction&lt;R&gt;, where R is the final result type. This takes, of course, a long as the input. A chained function definition which supports this is as follows:
1. Zero or more LongUnaryOperator functions
2. One LongFunction&lt;R&gt;s 
3. Zero or more Function&lt;T,R&gt;s

Actually, these types are extended internally by Metagener, with additional default methods for function composition and type checking, yielding the LISKOV equivalent:
1. Zero or more LongUnaryFieldFunctions
2. One TypedFieldFunction&lt;R&gt;
3. Zero or more GenericFieldFunction&lt;T,R&gt;

In other words, the LongFunction&lt;R&gt; is the composed function type that is used as the static function signature by all of the generic internal logic.

While it might seem that this particular arrangement is overly restrictive, in practice it is quite reasonable. You generally won't run into type-chain issues when composing functions, since the sensible compositions are the ones that don't violate the pattern. There are some common fix-ups that are applied by the function compositor in order to make this easier. For example, if you create a field def which resolves to an implementation of the GenericFieldFunction&lt;Float,String&gt;, then an auto-boxing function will be inserted ahead of it which does the Float conversion. All basic types have a default boxing function that acts very much like Java's autoboxing in practice, although the implementation is simply aware of the patterns in which the autoboxing is needed to fixup type chaining.

When metagener composes functions together internally, some awareness is needed of the types involved. Unfortunately, type erasure isn't helpful here, and the reflection API is rather cumbersome, yielding autoboxed signatures where primitive signatures would be needed. You can use isPrimitive() to detect non-boxed signatures, but the lack of language and type system consistency causes this to quickly devolve into something resembling a hand-coded neural network. This is unfortunate, since it makes combining late-binding strategies, lambdas, and primitives an ugly affair. Yet, we still need to make sense of valid types and combinations so that we can provide meaningful and corrective feedback to the user when they need it.

So, we need to figure out the input and output types for a function. Due to the limitations of the reflection API and primitive types mentioned above, the types are detected at runtime using the following strategy:
1. For LongUnaryOperator, type types are assumed to be input:long, output:long
2. For TypedFieldFunction, the input is assumed to be long, the outputs are read from the @Output annotation, which is required for TypedFieldFunctions. An error is thrown if this annotation is missing.
3. For GenericFieldFunction, the input and output are read from the @Input and @Output annotations. An error is thrown if either are missing when they are needed.

Of course, it is possible to get an annotation wrong. It's a relative simple thing to test for and fix. The point of requiring this annotation is to give back some of the runtime type awareness that is needed for safe, mediated used of user-configurable lambdas. It's a bit strange, the mix of language features in Java 8, so this is a way to deal with the missing bits in a pragmatic way.
