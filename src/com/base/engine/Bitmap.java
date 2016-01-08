package com.base.engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Stephen Rumpel
 */
public class Bitmap 
{
    public int width;
    public int height;
    public int[] pixels;
    
    public Bitmap(String fileName)
    {
        try
        {
            BufferedImage image = ImageIO.read(new File("./res/bitmaps/" + fileName));
            
            width = image.getWidth();
            height = image.getHeight();
            
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        }
        catch (IOException e)
        {
            Logger.getLogger(Bitmap.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Bitmap(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }
    
    public Bitmap flipX()
    {
        int[] temp = new int[pixels.length];
        
        for(int i = 0; i < width; i ++)
        {
            for(int j = 0; j < height; j++)
            {
                temp[i + j * width] = pixels[(width - i - 1)+ j * width];
            }
        }
        
        pixels = temp;
        
        return this;
    }
    
    public Bitmap flipY()
    {
        int[] temp = new int[pixels.length];
        
        for(int i = 0; i < width; i ++)
        {
            for(int j = 0; j < height; j++)
            {
                temp[i + j * width] = pixels[i+ (height - j - 1) * width];
            }
        }
        
        pixels = temp;
        
        return this;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int[] getPixels()
    {
        return pixels;
    }
    
    public int getPixel(int x, int y)
    {
        return pixels[x + y * width];
    }
    
    public void setPixel(int x, int y, int value)
    {
         pixels[x + y * width] = value;
    }
}
