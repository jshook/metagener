package com.metawiring.experimental;

import org.testng.annotations.Test;

import java.awt.image.BufferedImage;

import static org.testng.Assert.*;

public class MandelbrotFrameTest {

    @Test
    public void testMandelbrotFrame() {

        MandelbrotFrame mb = new MandelbrotFrame();

        BufferedImage mbi = mb.render(10, 10, 10, 1.0);
        for (int x = 0; x<mbi.getWidth(); x++) {
            for (int y = 0; y < mbi.getHeight(); y++) {
                System.out.println("(x,y)=v (" +x + "," + y + ")=" + mbi.getRGB(x,y));
            }
        }

    }

}