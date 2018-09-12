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

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String fileName = "";
        int width = 1920, height = 1080;
        int increment = 8;
        int steps = 6000000;
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

        BufferedImage img;
        Color c;
        // Good parameters are 1920 x 1080, intensity increment 8, and steps = 6000000.

        int rX = width / 2, rY = height / 2;
        int gX = width / 2, gY = height / 2;
        int bX = width / 2, bY = height / 2;
        int r = 0, g = 0, b = 0;
        // int sRGB, other;


        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        rX = width / 2;
        rY = height / 2;
        gX = width / 2;
        gY = height / 2;
        bX = width / 2;
        bY = height / 2;

        for (int j = 0; j < steps; j++) {
            rX = step(rX, width);
            rY = step(rY, height);
            gX = step(gX, width);
            gY = step(gY, height);
            bX = step(bX, width);
            bY = step(bY, height);

            c = new Color(img.getRGB(rX, rY));
            img.setRGB(rX, rY, (c.getRGB() & 0xff00ffff) | (Math.min(c.getRed() + increment, 255) << 16));

            c = new Color(img.getRGB(gX, gY));
            img.setRGB(gX, gY, (c.getRGB() & 0xffff00ff) | (Math.min(c.getGreen() + increment, 255) << 8));

            c = new Color(img.getRGB(bX, bY));
            img.setRGB(bX, bY, (c.getRGB() & 0xffffff00) | (Math.min(c.getBlue() + increment, 255)));
        }

        try {
            File outputfile = new File(fileName);
            ImageIO.write(img, imgFormat, outputfile);
        } catch (Exception e) {
            System.out.println("Error: cannot open the file.");
            System.exit(0);
        }


        long time = System.currentTimeMillis()-start;
        System.out.printf("Time used: %dm, %ds, %dms.%n", time / 60000, (time / 1000) % 60, time % 1000);
    }
}
