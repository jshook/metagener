## Metagener Components

![module dependencies](module_dependencies.png "Module Dependencies" "width:150px;float:right;") The configuration model is kept in 'metagener-defs'. The 'metagener-dsl' module depends on it. 'metagener-core' depends on them both. This allows any tool that can construct a valid definition to create a runtime metagener service. It also avoids cyclic dependencies between types and implementations. The metagener-webapi (the RESTful runtime) is a dropwizard app (Thanks, Coda and friends) which depends on metagener-core.

The first implementation of Metagener is in Java. There may be other runtimes for it in time. For now, it is packaged as a jar, using Maven 3. Because of the need for proper lambda support, Java 8 or newer is required. Metagener runtimes in other languages will likely be limited to those which support lamdas.

