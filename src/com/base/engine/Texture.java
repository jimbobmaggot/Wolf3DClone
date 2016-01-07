package com.base.engine;

import java.io.File;
import java.io.FileInputStream;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.TextureLoader;

public class Texture 
{
    public int id;
    
    public Texture(String fileName)
    {
        this(loadTexture(fileName));
    }
    
    public Texture(int id)
    {
        this.id = id;
    }
    
    public void bind()
    {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getId()
    {
        return id;
    }
    
    private static int loadTexture(String fileName)
    {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];
        
        try
        {
            int id = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/textures/" + fileName))).getTextureID();
            
            return id;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        
        return 0;
    }
}