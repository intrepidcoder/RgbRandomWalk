# RgbRandomWalk
Creates fractal-like images with three 2D random walks in red, green, and blue.

The intensity of a pixel is proportional (until it reaches the brightest value) to the number of times it is visited. See [Wikipedia](https://en.wikipedia.org/wiki/Random_walk) for more info on random walks.

Made just for fun.


## Usage
`java RgbRandomWalk <imagefilepath> <width> <height> <steps>`

`imagefilepath` is the image file to write. Output in in PNG format regardless of specified extension. This can be changed in source file. **Note: will overwrite any existing file. Use caution.**

`width` and `height` are the size of image.

`steps` is the number of steps to take for each random walk.

## Examples

![Example 1](example1.png?raw=true)

Width: 600, height: 400, steps: 500000

![Example 2](example2.png?raw=true)

Width: 600, height: 400, steps: 1000000

![Example 3](example3.png?raw=true)

Width: 1920, height: 1080, steps: 6000000
