package com.base.engine;

/**
 *
 * @author Stephen Rumpel
 */
public class Material
{

    private Texture texture;
    private Vector3f color;
    public float specularIntensity;
    public float specularPower;

    public Material(Texture texture)
    {
        this(texture, new Vector3f(1, 1, 1));
    }

    public Material(Texture texture, Vector3f color)
    {
        this(texture, color, 2, 32);
    }
    
    public Material(Texture texture, Vector3f color, float specularIntensity, float specularPower)
    {
        this.texture = texture;
        this.color = color;
        this.specularIntensity = specularIntensity;
        this.specularPower = specularPower;
    }
    
    // Getters

    public Texture getTexture()
    {
        return texture;
    }

    public Vector3f getColor()
    {
        return color;
    }

    public float getSpecularIntensity()
    {
        return specularIntensity;
    }

    public float getSpecularPower()
    {
        return specularPower;
    }
    
    // Setters

    public void setTexture(Texture texture)
    {
        this.texture = texture;
    }

    public void setColor(Vector3f color)
    {
        this.color = color;
    }

    public void setSpecularIntensity(float specularIntensity)
    {
        this.specularIntensity = specularIntensity;
    }

    public void setSpecularPower(float specularPower)
    {
        this.specularPower = specularPower;
    }
}
