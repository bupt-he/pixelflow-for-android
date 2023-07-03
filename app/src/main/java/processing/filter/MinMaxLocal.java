/**
 * 
 * PixelFlow | Copyright (C) 2017 Thomas Diewald (www.thomasdiewald.com)
 * 
 * src - www.github.com/diwi/PixelFlow
 * 
 * A Processing/Java library for high performance GPU-Computing.
 * MIT License: https://opensource.org/licenses/MIT
 * 
 */



package processing.filter;


import processing.dw.DwGLSLProgram;
import processing.dw.DwGLTexture;
import processing.dw.DwPixelFlow;


/**
 * 
 * 
 * Min/Max filter
 * 
 * @author Thomas
 *
 */
public class MinMaxLocal {
  
  
  static public enum Mode{
    MIN, MAX
  }
  
  public DwPixelFlow context;
  
  public DwGLSLProgram shader_min;
  public DwGLSLProgram shader_max;
  
  public MinMaxLocal(DwPixelFlow context){
    this.context = context;
    
    this.shader_min = context.createShader((Object)(this+"_MIN"), DwPixelFlow.SHADER_DIR+"MinMaxLocal.frag");
    this.shader_max = context.createShader((Object)(this+"_MAX"), DwPixelFlow.SHADER_DIR+"MinMaxLocal.frag");
    
    shader_min.frag.setDefine("MODE", 0);
    shader_max.frag.setDefine("MODE", 1);
  }
  

  public void apply(DwGLTexture dst, DwGLTexture texA, DwGLTexture texB, Mode mode){
    
    DwGLSLProgram shader = (Mode.MIN == mode) ? shader_min : shader_max;
    
    context.begin();
    context.beginDraw(dst);
    shader.begin();
    shader.uniform2f("wh_rcp", 1f/dst.w, 1f/dst.h);
    shader.uniformTexture("texA", texA);
    shader.uniformTexture("texB", texB);
    shader.drawFullScreenQuad();
    shader.end();
    context.endDraw();
    context.end("MinMax.apply");
  }
  
}
