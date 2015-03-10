GenGraph
========
_A dedicated statistical data generator_

## Status
This project is in active development. Do not expect it to have a stable API until it enters a release version.

## Overview

Testing data processing systems is generally harder than it should be, in large part because there is a lack of adequate tools. Common issues with testing tools include:
- Lack of conceptual clarity
- Lack of documentation
- Too specific to be useful across system boundaries
- Lack of useful defaults
- Inability to adapt to user demands
- Lack of empiricism
- Lack of quantitative measures

One area in test tooling which seems to always be lacking is the ability to load systems with data and access patterns which are realistic enough to provide meaningful results. The need to describe test data and user patterns is a pretty common requirements, so it seems strange that no common approach has taken root. Stranger, there aren't even many good tools to pick from. I've searched high and low for a useful system to do exactly this, and have not found anything satisfying. Perhaps that means I have a grandiose vision for what such a toolset should be. Perhaps I'm not alone.

The concepts, methods and tools in this repository are meant to become exactly what I wish existed. At the same time, the scope of the toolset will be very intentionally limited. Specifically, this toolkit will focus on providing one aspect of test tooling, namely statistical data generation at high operational rates. It will not be a complete testing tool in and of itself. The reason for limiting the scope in this way is to foster a broader adoption in the test tooling ecosystem. It will hopefully provide some conventions, components, and recipes which can be applied to many testing systems.

Now that we've established what the toolset will not be, we can desribe what it will have as prominent features.

- A descriptor format for defining data generators, statistical distributions, and relationships between them
- A self-contained way to invoke a configuration to generate output data in a basic format
- An API which can be used as a component of a larger test system

Essentially, this system is intended to be a producer of data of arbitrary complexity, at scale, and in a readily consumable way.