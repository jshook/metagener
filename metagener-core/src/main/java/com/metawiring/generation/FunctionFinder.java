package com.metawiring.generation;

import com.metawiring.generation.entityhashfunctions.Murmur3Hash;
import com.metawiring.generation.valuemapfunctions.BoxedInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class FunctionFinder {
    private final static Logger logger = LoggerFactory.getLogger(FunctionFinder.class);

    public static Package[] packages = new Package[] {
            Murmur3Hash.class.getPackage(),
            BoxedInt.class.getPackage()
    };

    @SuppressWarnings("unchecked")
    public static Class<FieldFunction> find(String name) {
        if (name.contains(".")) {
            try {
                return (Class<FieldFunction>) Class.forName(name);
            }  catch (ClassNotFoundException e) {
                logger.error("Could not find class: " + name);
                throw new RuntimeException(e);
            }
        }

        Class<FieldFunction> foundClass = null;
        LinkedList<String> triedLocations = new LinkedList<>();

        for (Package searchPackage: packages) {
            triedLocations.add(searchPackage.getName() + "." + name);
        }

        for (String location : triedLocations) {
            try {
                foundClass = (Class<FieldFunction>) Class.forName(location);
                if (foundClass != null) {
                    break;
                }
            } catch (ClassNotFoundException ignored) {
            }
        }

        if (foundClass == null) {
            logger.error("Could not locate class [" + name + "]");
            throw new RuntimeException("Error locating class for name " + name + " in any of " + triedLocations);
        }
        return (Class<FieldFunction>) foundClass;

    }
}
