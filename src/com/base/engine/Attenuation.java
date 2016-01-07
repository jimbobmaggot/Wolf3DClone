package com.base.engine;

/**
 *
 * @author Stephen Rumpel
 */
public class Attenuation 
{
    public float constant;
    public float linear;
    public float exponent;
    
    public Attenuation(float constant, float linear, float exponent)
    {
        this.constant = constant;
        this.linear = linear;
        this.exponent = exponent;
    }

    // Getters
    
    public float getConstant()
    {
        return constant;
    }

    public float getLinear()
    {
        return linear;
    }

    public float getExponent()
    {
        return exponent;
    }

    // Setters
    
    public void setConstant(float constant)
    {
        this.constant = constant;
    }

    public void setLinear(float linear)
    {
        this.linear = linear;
    }

    public void setExponent(float exponent)
    {
        this.exponent = exponent;
    }

}
