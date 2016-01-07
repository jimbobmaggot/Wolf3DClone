package com.base.engine;

public class Vertex
{

    public static final int SIZE = 8;

    private Vector3f pos;
    private Vector2f textCoord;
    public Vector3f normal;

    public Vertex(Vector3f pos)
    {
        this(pos, new Vector2f(0,0));
    }
    
    public Vertex(Vector3f pos, Vector2f textCoord)
    {
        this(pos, textCoord, new Vector3f(0, 0, 0));
    }
    
    public Vertex(Vector3f pos, Vector2f textCoord, Vector3f normal)
    {
        this.pos = pos;
        this.textCoord = textCoord;
        this.normal = normal;
    }
    
    //Getters

    public Vector3f getPos()
    {
        return pos;
    }

    public Vector2f getTexCoord()
    {
        return textCoord;
    }

    public Vector3f getNormal()
    {
        return normal;
    }

    // Setters

    public void setPos(Vector3f pos)
    {
        this.pos = pos;
    }

    public void setTextCoord(Vector2f textCoord)
    {
        this.textCoord = textCoord;
    }
    
    public void setNormal(Vector3f normal)
    {
        this.normal = normal;
    }
}
