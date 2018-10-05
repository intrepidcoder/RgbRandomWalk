# Random Walk Wallpaper Generator
Generate hazy fractal-like wallpaper images by simulating random walks in different colors.

The intensity of a pixel corresponds to the number of times it is visited during each random walk. See [Wikipedia](https://en.wikipedia.org/wiki/Random_walk) for more info on random walks.

## Usage
```
javac RgbRandomWalk.java
java RgbRandomWalk <imagefilepath> <width> <height> <steps>
```

The `<imagefilepath>` parameter is the filename of the PNG image to output. Output is in PNG format regardless of specified extension. This can be changed in source file. **Note: will overwrite any existing file. Use caution.**

`width` and `height` specify the size of image.

`steps` specifies the number of steps to take for each random walk.

The number of random walks and corresponding colors can be changed by editing the RgbRandomWalk.java file.

## Example output

![Example 1](example1.png?raw=true)

Width: 600, height: 400, steps: 500000

![Example 2](example2.png?raw=true)

Width: 600, height: 400, steps: 1000000

![Example 3](example3.png?raw=true)

Width: 1920, height: 1080, steps: 6000000
