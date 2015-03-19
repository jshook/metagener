package com.metawiring.generation.core;

import com.metawiring.generation.longfuncs.LoggedIdentity;
import com.metawiring.generation.fieldgenericfuncs.Prefix;
import com.metawiring.generation.fieldgenfuncs.EntityModulo;
import com.metawiring.types.functiontypes.GenericFieldFunction;
import com.metawiring.generation.fieldgenboxes.BoxedInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class FunctionClassFinder {
    private final static Logger logger = LoggerFactory.getLogger(FunctionClassFinder.class);


    public static Package[] packages = new Package[] {
            LoggedIdentity.class.getPackage(),
            BoxedInteger.class.getPackage(),
            Prefix.class.getPackage(),
            EntityModulo.class.getPackage()
    };

    @SuppressWarnings("unchecked")
    /**
     * Find the class in one of the configured locations, or throw a runtime exception.
     */
    public static Class<GenericFieldFunction> find(String name) {

        if (name.contains(".")) {
            try {
                return (Class<GenericFieldFunction>) Class.forName(name);
            }  catch (ClassNotFoundException e) {
                logger.error("Could not find class: " + name);
                throw new RuntimeException(e);
            }
        }

        Class<GenericFieldFunction> foundClass = null;
        LinkedList<String> triedLocations = new LinkedList<>();

        for (Package searchPackage: packages) {
            triedLocations.add(searchPackage.getName() + "." + name);
        }

        for (String location : triedLocations) {
            try {
                foundClass = (Class<GenericFieldFunction>) Class.forName(location);
                if (foundClass != null) {
                    break;
                }
            } catch (ClassNotFoundException ignored) {
            }
        }

        if (foundClass == null) {
            logger.error("Could not locate class [" + name + "]");
            throw new RuntimeException("Error locating class for name [" + name + "] in any of " + triedLocations);
        }
        return (Class<GenericFieldFunction>) foundClass;

    }
}
