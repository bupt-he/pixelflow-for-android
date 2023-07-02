/**
 * 
 * PixelFlow | Copyright (C) 2017 Thomas Diewald - www.thomasdiewald.com
 * 
 * https://github.com/diwi/PixelFlow.git
 * 
 * A Processing/Java library for high performance GPU-Computing.
 * MIT License: https://opensource.org/licenses/MIT
 * 
 */





package processing.dw;


import android.opengl.GLES30;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.dwutil.DwUtils;
import processing.opengl.PGraphics3D;
import processing.opengl.PShader;

public class DwScreenSpaceGeometryBuffer {
  
  String dir = DwPixelFlow.SHADER_DIR+"render/skylight/";
  
  DwPixelFlow context;
  public PApplet papplet;
  public PGraphics3D pg_geom;
  
  public DwSceneDisplay scene_display;
  public PShader shader;
  
  public DwScreenSpaceGeometryBuffer(DwPixelFlow context, DwSceneDisplay scene_display){
    this.context = context;
    this.papplet = context.papplet;
    this.scene_display = scene_display;
    
    String[] src_frag = context.utils.readASCIIfile(dir+"geometryBuffer.frag");
    String[] src_vert = context.utils.readASCIIfile(dir+"geometryBuffer.vert");

    this.shader = new PShader(papplet, src_vert, src_frag);
//    this.shader = papplet.loadShader(dir+"geometryBuffer.frag", dir+"geometryBuffer.vert");
    
//    resize(context.papplet.width, context.papplet.height); // TODO
  }
  
  public boolean resize(int w, int h){
    boolean[] resized = {false};
    pg_geom = DwUtils.changeTextureSize(papplet, pg_geom, w, h, 0, resized);
    
    if(resized[0]){
      DwUtils.changeTextureFormat(pg_geom, GLES30.GL_RGBA16F, GLES30.GL_RGBA, GLES30.GL_FLOAT, GLES30.GL_LINEAR, GLES30.GL_CLAMP_TO_EDGE);
    }
    
    return resized[0];
  }
  
  public void updateMatrices(PGraphics3D pg_src){
    DwUtils.copyMatrices(pg_src, pg_geom);
  }

  public void update(PGraphics3D pg_src){
    
    resize(pg_src.width, pg_src.height);
    
    pg_geom.beginDraw();
    updateMatrices(pg_src);
    pg_geom.blendMode(PConstants.REPLACE);
    pg_geom.clear();
    pg_geom.shader(shader);
    pg_geom.noStroke();
    scene_display.display(pg_geom);
    pg_geom.endDraw();
  }
  
}
