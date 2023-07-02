/**
 * 
 * PixelFlow | Copyright (C) 2016 Thomas Diewald - http://thomasdiewald.com
 * 
 * A Processing/Java library for high performance GPU-Computing (GLSL).
 * MIT License: https://opensource.org/licenses/MIT
 * 
 */


package processing.filter;



import processing.dw.DwGLSLProgram;
import processing.dw.DwGLTexture;
import processing.dw.DwPixelFlow;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.Texture;

public class RGBL {
  
  public DwPixelFlow context;
  
  public DwGLSLProgram shader;
  
  public float[] luminance = {0.2989f, 0.5870f, 0.1140f};
//  public float[] luminance = {0.2126f, 0.7152f, 0.0722f};
//  public float[] luminance = {0.3333f, 0.3333f, 0.3333f}; // rgb average
  
  public RGBL(DwPixelFlow context){
    this.context = context;
  }
  
  public void apply(PGraphicsOpenGL src, PGraphicsOpenGL dst) {
    Texture tex_src = src.getTexture(); if(!tex_src.available())  return;
    Texture tex_dst = dst.getTexture(); if(!tex_dst.available())  return;

    context.begin();
    context.beginDraw(dst);
    apply(tex_src.glName, dst.width, dst.height);
    context.endDraw();
    context.end("RGBL.apply");
  }
  
  public void apply(PGraphicsOpenGL src, DwGLTexture dst) {
    Texture tex_src = src.getTexture();
    if(!tex_src.available()) 
      return;
       
    context.begin();
    context.beginDraw(dst);
    apply(tex_src.glName, dst.w, dst.h);
    context.endDraw();
    context.end("RGBL.apply");
  }
  
  
  public void apply(DwGLTexture src, DwGLTexture dst) {
    context.begin();
    context.beginDraw(dst);
    apply(src.HANDLE[0], dst.w, dst.h);
    context.endDraw();
    context.end("RGBL.apply");
  }
  

  public void apply(int tex_handle, int w, int h){
    if(shader == null) shader = context.createShader(DwPixelFlow.SHADER_DIR+"Filter/RGBL.frag");
    shader.begin();
    shader.uniform2f     ("wh_rcp" , 1f/w, 1f/h);
    shader.uniformTexture("tex", tex_handle);
    shader.uniform3fv("luminance", 1, luminance);
    shader.drawFullScreenQuad();
    shader.end();
  }
  
  
}
