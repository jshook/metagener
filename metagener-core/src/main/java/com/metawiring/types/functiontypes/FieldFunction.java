package com.metawiring.types.functiontypes;

/**
 * This merely serves as root of all field function types.
 * The compose(..) and andthen(...) methods are implemented
 * as default methods on the subtypes which derive from this tagged interface,
 * thus they aren't polymorphic. (sorry, J8 tried)
 *
 * The basic allowable compositions in this scheme are:
 * TypedFieldFunction( LongFieldFunction( LongFieldFunction( ... ) ) )
 * GenericFieldFunction( TypedFieldFunction( ) )
 */
public interface FieldFunction {

//    can not be defined here without breaking FunctionalInterface
//    FieldFunction compose(FieldFunction outerFunc);
//    can not be defined here without breaking FunctionalInterface
//    FieldFunction andThen(FieldFunction innerFunc);

}
