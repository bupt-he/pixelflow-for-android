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

public class Mad {
  
  public DwPixelFlow context;
  
  public Mad(DwPixelFlow context){
    this.context = context;
  }
  
  
  public void apply(PGraphicsOpenGL src, PGraphicsOpenGL dst, float[] mad) {
    Texture tex_src = src.getTexture(); if(!tex_src.available())  return;
    Texture tex_dst = dst.getTexture(); if(!tex_dst.available())  return;
       
    context.begin();
    context.beginDraw(dst);
    apply(tex_src.glName, dst.width, dst.height, mad);
    context.endDraw();
    context.end("Mad.apply");
  }
  
  public void apply(PGraphicsOpenGL src, DwGLTexture dst, float[] mad) {
    Texture tex_src = src.getTexture();
    if(!tex_src.available()) 
      return;
       
    context.begin();
    context.beginDraw(dst);
    apply(tex_src.glName, dst.w, dst.h, mad);
    context.endDraw();
    context.end("Mad.apply");
  }
  
  public void apply(DwGLTexture src, PGraphicsOpenGL dst, float[] mad) {
    Texture tex_src = dst.getTexture();
    if(!tex_src.available()) 
      return;
       
    context.begin();
    context.beginDraw(dst);
    apply(tex_src.glName, dst.width, dst.height, mad);
    context.endDraw();
    context.end("Mad.apply");
  }
  
  
  public void apply(DwGLTexture src, DwGLTexture dst, float[] mad) {
    context.begin();
    context.beginDraw(dst);
    apply(src.HANDLE[0], dst.w, dst.h, mad);
    context.endDraw();
    context.end("Mad.apply");
  }
  
  DwGLSLProgram shader;
  public void apply(int tex_handle, int w, int h, float[] mad){
    if(shader == null) shader = context.createShader(DwPixelFlow.SHADER_DIR+"mad.frag");
    shader.begin();
    shader.uniform2f     ("wh_rcp", 1f/w, 1f/h);
    shader.uniformTexture("tex", tex_handle);
    shader.uniform2f    ("mad", mad[0], mad[1]);
    shader.drawFullScreenQuad();
    shader.end();
  }
  
  
  
  
  
  
  
  
  
  
  
  
}
