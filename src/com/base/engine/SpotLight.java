package com.base.engine;

/**
 *
 * @author Stephen Rumpel
 */
public class SpotLight 
{
    public PointLight pointLight;
    public Vector3f direction;
    public float cutoff;

    public SpotLight(PointLight pointLight, Vector3f direction, float cutoff)
    {
        this.pointLight = pointLight;
        this.direction = direction.normalized();
        this.cutoff = cutoff;
    }
    
    // Getters

    public PointLight getPointLight()
    {
        return pointLight;
    }

    public Vector3f getDirection()
    {
        return direction;
    }

    public float getCutoff()
    {
        return cutoff;
    }

    // Setters
    
    public void setPointLight(PointLight pointLight)
    {
        this.pointLight = pointLight;
    }

    public void setDirection(Vector3f direction)
    {
        this.direction = direction.normalized();
    }

    public void setCutoff(float cutoff)
    {
        this.cutoff = cutoff;
    }
}
