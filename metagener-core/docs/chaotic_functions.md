# Chaotic Functions

There are times when you want to be able to simulate brownian motion, or something like it. Maybe sometimes you just want to simulate swings from the norm with a periodic function.

Consider a stock value, for example. It may ebb and flow with the market. It may also do it's own thing. It's hard to say until it happens. Simulating such things as differences from a previous state in a simulation is really not that hard. You can layer up some integral effects by analyzing the derivitave factors, apply some math and end up with a function that works. For example, you can integrate an acceleration function to yield velocity. You can integrate that to yield position.
This type of thing is easy to do in such a way that you can calculate the state of something given the initial state and the _time_ passed.

## Fourier Standard

You can go one level further, and simulate variations in continuous values by calculating a Fourier integral. This method is quite robust for periodic variations, and with enough decorating configuration, even expressive for non-periodic variations. However, the amount of expression required to build the closed-form integral can be cumbersome. Furthermore, the amount of dynamicism you achieve is directly tied to the Fourier recipe that you define. On the plus side, careful selection of parameters as addends that start and return to 0 can give you a fully continuous line. On the down side, this is still predictible by design, and does not allow for a high degree of virtual entropy to be expressed with a simple description. It is also incrementally more expensive to calculate as components are added to the function.

## Chaotic Appearances

If you want to go another level, you need something that doesn't appear to be predictable in the normal domain of human reason. There are some such structures found in mathematics which can be used as a source pallette for data generation. Take the Mandelbrodt set for example. It is an exciting tool for data generation not simply because it is beyond the understanding of most human beings (Real mathemeticians excepted). It is exciting because it has the appearance of something that has endless variation. It holds the attention to the expert and the lay person alike due to the notion that it holds hidden secrets.

Mathematically, it also has certain properties that are very useful for simulating chaotic values in a way that can also be made idempotent. Specifically, it can be used to simulate varying degrees of resolution. As well, there is a closed form function that can be calculated in a reasonable time for a given degree of resolution. It's bounds are well understood.

## Mandelbrodt Sampling

The following section describes a generalized way to create chaotic looking discrete values by transiting a mandelbrodt set.

Given initial parameters:

* d in (0,10) -- degree of resolution (terms in mandelbrodt function)
* (x,y,r,v,o) -- (x,y):initial coordinate, r:radians, and v:velocity, o:tangent velocity

And function argument n:

1. If needed, calculate normalized vector components (dx,dy) from the direction (r) and velocity (v). This is done only once per function instance, at initialization time.
2. If needed, calculate normalized tangent vector components (tx, ty) from (r) and (o). This is done only once per function instance, at initialization time. The tangent vector is at +.5 &pi; radians to (r).
3. Calculate final position as (xn,yn) = (dx &times; n,dy &times; n). Normalize to mandelbrodt space.
4. Take (w) as the number of times (xn) wrapped the mandelbrodt space before it was normalized. Calculate the tangent vector as o &times; w, and add it to the final position.

Do a set test at the final position. Use normal iterative testing semantics for the mandelbrodt set to return a discrete value.

## Smoothing

Since this function provides discrete values, it may be desirable to use it as scaffolding to simulate more continuous values. For this, a simple linear interpolation would be sufficient. It makes sense to memoize certain values near the sample point to facilitate this. For extremely long sequences of samples which are longer than a reasonable memoization bound, compute only the local family of values, and use a fixed slope up to or down to the value. This means that some nearby computation will be needed for some samples, but that the bounds of memory needed to support it are small and tunable.

The smoothing function should be applied as a separate function, actually, with parameters for windowing and slope.