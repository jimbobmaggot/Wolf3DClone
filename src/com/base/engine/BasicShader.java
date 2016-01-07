package com.base.engine;

/**
 *
 * @author Stephen Rumpel
 */
public class BasicShader extends Shader
{
    private static final BasicShader instance = new BasicShader();
    
    public static BasicShader getInstance()
    {
        return instance;
    }
    
    public BasicShader()
    {
        super();
        
        addVertexShaderFromFile("basicVertex.vs");
        addFragmentShaderFromFile("basicFragment.fs");
        compileShader();
        
        addUniform("transform");
        addUniform("color");
    }
    
    @Override
    public void updateuniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material)
    {
        if(material.getTexture() != null)
        {
            material.getTexture().bind();
        }
        else
        {
            RenderUtil.unbindTextures();
        }
        
        setUniform("transform", projectedMatrix);
        setUniform("color", material.getColor());
    }
}
