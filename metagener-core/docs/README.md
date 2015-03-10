# Metagener

Metagener is a meta-programming library and conceptual power tool for flexible and highly-concurrent statistical data generation.  More specifically, it is a conceptual model for thinking about and composing data generator functions, a language for expressing them, and a runtime for accessing the results of these functions.

## Why build it?

Most testing tools treat the test data as an unfortunate implementation detail. Even in so-called _extensible_ systems, test data is typically strongly-tied to the testing system, requiring significant work to decouple the data from the tool that runs the test. This is even more true when you need to simulate data via some form of generation. In those cases, the data and the tool used to create it become inextricably linked, unseperable. On the other end of the spectrum, the data remains boring and overly-generic-- not specific or relatable to a given data model, data access pattern, or user behavior. Most of the time, it is limited to simple CRUD-style templates, not a sufficient approximation for any non-trivial design.

These problems create an impedence mismatch between the goals of most users and the tools they have to attain them. They may spend a serious amount of effort to build a testing tool which is meaningful to their application. Perhaps worse, they may spend no effort in getting results which are specific to their intended system design. This means that their results will be loosely related to their goals at best.

The two key dimensions of this problem are effort and expressivity. Tools that offer meaningful application-aware tests require too much effort to build and maintain. On the flip side, if you want something that is quick and easy to start testing with, it will probably not be very application-specific. This creates problems of interpretation, and means that you can only get apples-to-apples results after lots of effort.

Metagener endeavors to solve these problems head-on. In order to do so, it must be able to describe data in a flexible, yet intuitive way. It must also provide a conceptual toolkit which makes constructing more nuanced data possible. It should be easy to describe the most mundane data in a mundane way, but possible, even intuitive to construct recipes for complex data as needed.

___Simple things should be simple. Complex things should be possible. -- Alan Kay___

## What makes it different?

Metagener is intended to be one of the tools that I've always wanted for empirical testing. To be clear, there are certain design features that have generally been lacking in the tools I've used. This is a short list of what makes metagener unique:

* __statistical generation with determinism__ -- Choose what level of apples-to-apples you need. Use [established statistical sampling techniques](http://en.wikipedia.org/wiki/Inverse_transform_sampling), but also have the ability to replay the same exact data if you like. Statistical simulation techniques and replay determinism are not mutually exclusive. You can use them both at the same time.
* __composable data generator functions__ -- Use a library of functions which can be chained together to produce trivial or non-trivial streams of data. Libraries of generator functions can be constructed and used to build sophisticated data streams with little effort. Functional programming concepts can be applied directly to building and scaling metagener loads acros systems.
* __a data generation language__ -- Metagener provides a simple and targeted language for building data generation recipes. Paste one in email and send it to a friend.
* __random access to idempotent sample data__ -- The data stream can be accessed in order, or randomly, yielding the same values for a numbered sample.
* __efficient generator runtime__ -- Metagener endeavors to be fast enough to do performance testing with common testing tools. If you use the native API, it should be comparably fast to data generator functions written by hand.
* __neutral data generation semantics__ -- With the singular goal of flexible data generation, Metagener can be used as a component of other testing tools which speak the same generator language. This will yield more consistent results across different types of platforms and testing tools.

Most of all, metagener provides a medium through which users can experiment with and share recipes for more sophisticated data generators.

### Similar Systems

Honestly, I don't know of another tool like it. If there are any that I've missed, please submit them via github and I'll link them in the project docs. Please, submit only open source data generation libraries that are self-standing and clear of any tight coupling to other test tools.

## Getting Started

The best way to use Metagener will be as a native Java API. The CLI is a handy way to experiment with recipes. The RESTful service is there when you need to share a stream across systems, or when you are using a different language than Java 8.

[__The Java API__](using_the_java_api.md) -- Using metagener as a direct Java API will be the most optimal way to get large amounts of data for a steady-state or long-running type of simulation. This allows you to consume data from it continuously at whatever rate is appropriate. If you want to use Metagener as a Maven artifact, you can easily clone and 'mvn install' locally, or you can manually install the jar from the [releases](https://github.com/jshook/metagener/releases) on github. Eventually, Metagener may be in Maven central, but this not a given.

[__Creating Generators__](how_to_build_generators.md) -- It is helpful to have some familiarity with how metagener works in order to build custom recipes. Metagener is as much about the conceptual tools for generating data as it is the runtime and language.

[__Command Line Interface__](command_line_interface.md) -- You can easily start using metagener as an executable jar, from the Metagener [releases](https://github.com/jshook/metagener/releases) area on github. In this form, metagener can write results to stdout, or file(s) in json or CSV format.

[__The RESTful Service__](using_metagener_restfully.md) -- You can run metagener-webapi.jar as an executable jar. It allows you to use it as a network service for any web-based client.

[__Prebundled_Configurations__](prebundled_configurations.md) -- Metagener has some baked-in recipes which can be used to quickly start generating data, if you want to experiment.

## License & Contributions

Metagener is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).  Contributions under the same license are welcome. Pull requests and feature requests will all be considered earnestly.

## Building Metagener

Eventually I hope to get metagener onto Maven central. Until then, if you need to reference it in your Maven projects, all you have to do is download it and run mvn install. That will place it into your local maven repo (~/.m2 by default), at which point you can build with it. Until it is in Maven repo (if ever), this will be the preferred way to include it into a Maven project. It's also the preferred way to build it, even if you are using another build system. Alternately, just use a stable release and download the jar direction in your build scripts.


