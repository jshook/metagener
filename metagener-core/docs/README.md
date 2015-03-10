Metagener Intro
===============

Metagener is a toolkit data generation. It provides a conceptual model for describing data sets, a domain-specific language for configuration, and a runtime for creating and interacting with streams of generated data.

## Why?

Most testing tools treat the test data as an unfortunate implementation detail. Even in so-called _extensible_ systems, test data is typically strongly-tied to the testing system, requiring significant work to decouple the data from the tool that runs the test. This is even more true when you need to simulate data via some form of generation. In those cases, the data and the tool used to create it become inextricably linked, unseperable. On the other end of the spectrum, the data remains boring and overly-generic-- not specific or relatable to a given data model, data access pattern, or user behavior. Most of the time, it is limited to simple CRUD-style templates, not a sufficient approximation for any non-trivial design.

These problems create an impedence mismatch between the goals of most users and the tools they have to attain them. They may spend disproportionate effort to build a testing tool which is meaningful to their application. Perhaps worse, they may spend no effort in getting results which are specific and meaningful to their intended system design. The two key dimensions of this problem are effort and expressivity. Tools that offer meaningful application-aware tests require too much effort to build and maintain. On the flip side, if you want something that is quick and easy to start testing with, it will probably not be very application-specific. This creates problems of interpretation, and means that you can only get apples-to-apples results after lots of effort.

Metagener endeavors to solve these problems head-on. In order to do so, it must be able to describe data in a flexible, yet intuitive way. It must also provide a conceptual toolkit which makes constructing more nuanced data possible. It should be easy to describe the most mundane data in a mundane way, but possible, even intuitive to construct recipes for complex data as needed.

___Simple things should be simple. Complex things should be possible. -- Alan Kay___

## What?

Metagener is intended to be one of the tools that I've always wanted for empirical testing. To be clear, there are certain design features that have generally been lacking in the tools I've used. This is a short list of what makes metagener unique:

* __statistical generation with determinism__ -- Choose what level of apples-to-apples you need. Use [established statistical sampling techniques](http://en.wikipedia.org/wiki/Inverse_transform_sampling), but also have the ability to replay the same exact data if you like. Statistical simulation techniques and replay determinism are not mutually exclusive. You can use them both at the same time.
* __composable data generator functions__ -- Use a library of functions which can be chained together to produce trivial or non-trivial streams of data. Libraries of generator functions can be constructed and used to build sophisticated data streams with little effort.
* __a data generation language__ -- Metagener provides a simple and targeted language for building data generation recipes. Paste one in email and send it to a friend.
* __random access to idempotent sample data__ -- The data stream can be accessed in order, or randomly, yielding the same values for a numbered sample.

Most of all, metagener provides a medium through which users can experiment with and share recipes for more sophisticated data generators.

## Usage

Metagener can be used on one of several ways:
* <s>As a CLI</s> <-- in flux
* As a Java runtime API
  * Metagener DSL
  * <s>fluent API</s> <-- in flux
  * <s>Metagener YAML</s> <-- in flux
* As a RESTful service

Each of these will be linked and documented separately as the documentation improves.

The preferable method for describing recipes is to use the configuration DSL, since this provides the most portable format for configuring a metagner runtime. In the future, you should be able to use drop the same .metagener file into any metagener runtime and get a useful stream of data, no matter what the language.

The preferable method for consuming data is via the native Java API, although the RESTful service is available in order to provide a data generator service to other runtimes.

## Metagener Components

![module dependencies](module_dependencies.png "Module Dependencies" "width:150px;float:right;") The configuration model is kept in 'metagener-defs'. The 'metagener-dsl' module depends on it. 'metagener-core' depends on them both. This allows any tool that can construct a valid definition to create a runtime metagener service. It also avoids cyclic dependencies between types and implementations. The metagener-webapi (the RESTful runtime) is a dropwizard app (Thanks, Coda and friends) which depends on metagener-core.

The first implementation of Metagener is in Java. There may be other runtimes for it in time. For now, it is packaged as a jar, using Maven 3. Because of the need for proper lambda support, Java 8 or newer is required. Metagener runtimes in other languages will likely be limited to those which support lamdas.


## Getting Started

__for the full API__ -- Using metagener as a direct Java API will be the most optimal way to get large amounts of data for a steady-state or long-running type of simulation. This allows you to consume data from it continuously at whatever rate is appropriate.

__creating data generator recipes__ -- It is helpful to have some familiarity with how metagener works in order to build custom recipes. Metagener is as much about the conceptual tools for generating data as it is the runtime and language. If you really want to know how metagener works, and how to create your own data generators, please start with [Metagener Concepts](metagener_concepts.md). Or, browse the [Metagener Documentation](metagener_documentation.md) directly.

__for some quick data__ -- If you want to use one of the baked-in metagener recipes, all you have to do is download and run an executable jar from the releases on github. If you run the tool with no options, it will tell you how to select one of the built-in recipes. The CLI and library are released  as metagener-&lt;version&gt;.jar, while the RESTful service is released as metagener-webapi-&lt;version&gt;.jar. Both are executable jars.

metagener.jar has a basic command line interface. Since it is not a persistent process, it will simply use the command line options to load a generator config and dump the sample results out to console. The recipe can include directives for how to dump the data, or it must be specified on the command line. Use the --help for the full set of options.

These recipes can also be used to experiment. There is an option to copy out a recipe from the core jar into a local configuration directory. Both locations are used, with the local configuration directory taking precedence. This makes it easy to get started with an existing recipe while also being able to make changes and see the results.


## Building Metagener

Eventually I hope to get metagener onto Maven central. Until then, if you need to reference it in your Maven projects, all you have to do is download it and run mvn install. That will place it into your local maven repo (~/.m2 by default), at which point you can build with it. Until it is in Maven repo (if ever), this will be the preferred way to include it into a Maven project. It's also the preferred way to build it, even if you are using another build system. Alternately, just use a stable release and download the jar direction in your build scripts.


