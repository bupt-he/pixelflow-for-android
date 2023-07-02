/**
 * 
 * PixelFlow | Copyright (C) 2016 Thomas Diewald - http://thomasdiewald.com
 * 
 * A Processing/Java library for high performance GPU-Computing (GLSL).
 * MIT License: https://opensource.org/licenses/MIT
 * 
 */



package processing.filter;


import android.util.Log;

import processing.core.PImage;
import processing.dw.DwGLSLProgram;
import processing.dw.DwGLTexture;
import processing.dw.DwPixelFlow;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.Texture;

public class Copy {
  
  public DwPixelFlow context;
  
  public DwGLSLProgram shader;

  public Copy(DwPixelFlow context){
    this.context = context;
    this.shader = context.createShader(this, DwPixelFlow.SHADER_DIR+"Filter/copy.frag");
    Log.d("heyibin","shader=" + shader);
  }

  public void apply(PGraphicsOpenGL src, PGraphicsOpenGL dst) {
    Texture tex_src = src.getTexture(); if(!tex_src.available())  return;
    Texture tex_dst = dst.getTexture(); if(!tex_dst.available())  return;
       
    context.begin();
    context.beginDraw(dst);
    apply(tex_src.glName, dst.width, dst.height);
    context.endDraw();
    context.end("Copy.apply");
  }
  
  public void apply(PGraphicsOpenGL src, DwGLTexture dst) {
    Texture tex_src = src.getTexture(); if(!tex_src.available())  return;
       
    context.begin();
    context.beginDraw(dst);
    apply(tex_src.glName, dst.w, dst.h);
    context.endDraw();
    context.end("Copy.apply");
  }
  
  
  public void apply(PImage src, DwGLTexture dst) {
    if(!src.parent.g.isGL()){
      return;
    }
    
    PGraphicsOpenGL pogl = (PGraphicsOpenGL) src.parent.g;
    Texture tex_src = pogl.getTexture(src); if(!tex_src.available())  return;
       
    context.begin();
    context.beginDraw(dst);
    apply(tex_src.glName, dst.w, dst.h);
    context.endDraw();
    context.end("Copy.apply");
  }
  
  
  public void apply(DwGLTexture src, PGraphicsOpenGL dst) {
    Texture tex_dst = dst.getTexture(); if(!tex_dst.available())  return;
    
    context.begin();
    context.beginDraw(dst);
    apply(src.HANDLE[0], dst.width, dst.height);
    context.endDraw();
    context.end("Copy.apply");
  }
  
  public void apply(DwGLTexture src, DwGLTexture dst) {
    context.begin();
    context.beginDraw(dst);
    apply(src.HANDLE[0], dst.w, dst.h);
    context.endDraw();
    context.end("Copy.apply");
  }

  private void apply(int tex_handle, int w, int h){
    shader.begin();
    shader.uniform2f     ("wh_rcp", 1f/w, 1f/h);
    shader.uniformTexture("tex"   , tex_handle);
    shader.drawFullScreenQuad();
    shader.end();
  }
  
}
