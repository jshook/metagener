package com.metawiring.experimental;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class MandelbrotFrame {

    public BufferedImage render(int width, int height, int maxIter, double zoom) {

        double zx, zy, cx, cy, tmp;
        double xOffset = width / 2;
        double yOffset = height /2;
        double halfX = width - (width / 2);
        double halfY = height - (height / 2);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            System.out.println("processing row:" + y);
            for (int x = 0; x < width; x++) {
                zx = zy = 0;
                cx = (x - halfX) / zoom;
                cy = (y - halfY) / zoom;
                System.out.println("(cx,cy) = ("+ cx + "," + cy + ")");
                int iter = maxIter;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cx;
                    zy = 2.0 * zx * zy + cy;
                    zx = tmp;
                    iter--;
                }
                image.setRGB(x, y, iter | (iter << 8));
            }
        }
        return image;
    }

    /**
     *<pre>For each pixel (Px, Py) on the screen, do:
     {
     x0 = scaled x coordinate of pixel (scaled to lie in the Mandelbrot X scale (-2.5, 1))
     y0 = scaled y coordinate of pixel (scaled to lie in the Mandelbrot Y scale (-1, 1))
     x = 0.0
     y = 0.0
     iteration = 0
     max_iteration = 1000
     // Here N=2^8 is chosen as a reasonable bailout radius.
     while ( x*x + y*y < (1 << 16)  AND  iteration < max_iteration ) {
     xtemp = x*x - y*y + x0
     y = 2*x*y + y0
     x = xtemp
     iteration = iteration + 1
     }
     // Used to avoid floating point issues with points inside the set.
     if ( iteration < max_iteration ) {
     zn = sqrt( x*x + y*y )
     nu = log( log(zn) / log(2) ) / log(2)
     // Rearranging the potential function.
     // Could remove the sqrt and multiply log(zn) by 1/2, but less clear.
     // Dividing log(zn) by log(2) instead of log(N = 1<<8)
     // because we want the entire palette to range from the
     // center to radius 2, NOT our bailout radius.
     iteration = iteration + 1 - nu
     }
     color1 = palette[floor(iteration)]
     color2 = palette[floor(iteration) + 1]
     // iteration % 1 = fractional part of iteration.
     color = linear_interpolate(color1, color2, iteration % 1)
     plot(Px, Py, color)
     }
     </pre>
     */

}


