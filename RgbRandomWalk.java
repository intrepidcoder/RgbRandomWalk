import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.util.Random;

public class RgbRandomWalk {
    private static Random prng = new Random();

    private static int step(int pos, int upperBound) {
        if (pos <= 0) {
            return prng.nextInt(2);
        } else if (pos >= upperBound - 1) {
            return upperBound - 1 - prng.nextInt(2);
        } else {
            return pos - 1 + prng.nextInt(3);
        }
    }

    // Maps the number of steps into the range 0 to 255.
    private static int colorMap(int stepCount) {
        int[] multipliers = {8, 8, 4, 2};
        int count = 16;
        int value = 0;

        for (int inc : multipliers) {
            if (stepCount > count) {
                value += count * inc;
                stepCount -= count;
            } else {
                value += stepCount * inc;
                stepCount = 0;
            }
        }

        if (stepCount > 0) {
            value += stepCount;
        }
        value = Math.min(value, 255);

        return value;
    }

    public static void main(String[] args) {
        String fileName = "";
        int width;
        int height;
        int steps;

        // Colors for each of the random paths. Will generate a path for each
        // color specified.
        // Use RBG format like HTML, e.g. 0xRRGGBB where RR are two hex digits
        // specifying the red value, GG specify green, and BB specify blue. Also
        // can use builtin colors such as Color.BLUE, see documentation on
        // java.awt.Color for details.
        Color[] colors = {new Color(0x0000ff), new Color(0x00ff00), new Color(0xff0000)};

        String imgFormat = "png";

        if (args.length != 4) {
            System.out.println("Usage: java RgbRandomWalk <imagefilepath> <width> <height> <steps>");
            System.exit(0);
        } else {
            try {
                fileName = args[0];
                width = Integer.parseInt(args[1], 10);
                height = Integer.parseInt(args[2], 10);
                steps = Integer.parseInt(args[3], 10);
            } catch (NumberFormatException e) {
                System.out.println("Usage: java RgbRandomWalk <imagefilepath> <width> <height> <steps>");
                System.out.println("Error: Invalid number.");
                System.exit(0);
            }

            if (width <= 0 || height <= 0 || steps <= 0) {
                System.out.println("Usage: java RgbRandomWalk <imagefilepath> <width> <height> <steps>");
                System.out.println("Error: Negative number.");
                System.exit(0);
            }
        }

        int[] posX = new int[colors.length];
        int[] posY = new int[colors.length];
        int[][][] visitCount = new int[width][height][colors.length];

        for (int i = 0; i < colors.length; i++) {
            posX[i] = width / 2;
            posY[i] = height / 2;
        }

        for (int j = 0; j < steps; j++) {
            for (int i = 0; i < colors.length; i++) {
                posX[i] = step(posX[i], width);
                posY[i] = step(posY[i], height);

                visitCount[posX[i]][posY[i]][i]++;
            }
        }
        int[] pixels = new int[width * height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int c = Color.BLACK.getRGB();
                float[] rgb = new float[3];
                float[] components = new float[3];
                for (int k = 0; k < colors.length; k++) {
                    float value = colorMap(visitCount[i][j][k]) / 256.0f;
                    colors[k].getColorComponents(components);
                    for (int m = 0; m < 3; m++) {
                        rgb[m] += components[m] * value;
                    }
                }
                for (int m = 0; m < 3; m++) {
                    rgb[m] = Math.min(rgb[m], 1.0f);
                }
                pixels[i + width * j] = new Color(rgb[0], rgb[1], rgb[2]).getRGB();
            }
        }

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, width, height, pixels, 0, width);

        try {
            File outputfile = new File(fileName);
            ImageIO.write(img, imgFormat, outputfile);
        } catch (IOException e) {
            System.err.println("Error: cannot open the file" + fileName);
        }
    }
}
