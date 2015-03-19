## Function Shorthand

Certain generator functions will be used quite often. The syntax for these will allow for shorthand forms. All of the shorthand forms are documented here.

### Ranged Random Integer

	0-15 // equivalent to random(0,15)
    gaussian(2.703 - 234.0)
    binomial(5-23)

### Cycled Value

	0..15.0 // equivalent to dmod(15); double();
    0..15   // equivalent to dmod(15);
    5..23   // equivalent to dmod(15); plus(8);
    5..23.0 // equivalent to dmod(15); plus(8); double();

### Open/Closed Random Double

	[2.3-5.0] // 2.3 inclusive up to 5.0 inclusive
    (2.3-5.0) // 2.3 exclusive up to 5.0 exclusive
    [2.3-5.0) // 2.3 inclusive up to 5.0 exclusive
    (2.3-5.0] // 2.3 exclusive up to 5.0 inclusive
