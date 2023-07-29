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


package processing.shadertoy;


import android.opengl.GLES30;

import processing.core.PApplet;
import processing.core.PImage;
import processing.dw.DwPixelFlow;
import processing.dw.DwShadertoy;
import processing.dwutil.DwUtils;
import processing.opengl.PGraphics2D;
import processing.test.sketch_230701b.R;


public class Shadertoy_AbstractCorridor3 extends PApplet {
  
  //
  // Shadertoy Demo:   https://www.shadertoy.com/view/MlXSWX
  // Shadertoy Author: https://www.shadertoy.com/user/Shane
  //
  
  DwPixelFlow context;
  DwShadertoy toy;

  PGraphics2D pg_0;
  PGraphics2D pg_1;

  public void settings() {
    size(1280, 720, P2D);
    smooth(0);
  }
  
  public void setup() {
//    surface.setResizable(true);
    
    context = new DwPixelFlow(this);
    context.print();
//    context.printGL();
    
    toy = new DwShadertoy(context, "AbstractCorridor.frag");
    String resImage0 = context.activity.getResources().getResourceName(R.mipmap.abstract_2);
    String resImage1 = context.activity.getResources().getResourceName(R.mipmap.wood);

    PImage img0 = loadImage(resImage0);
    PImage img1 = loadImage(resImage1);
    
    pg_0 = (PGraphics2D) createGraphics(img0.width, img0.height, P2D);
    pg_1 = (PGraphics2D) createGraphics(img1.width, img1.height, P2D);
    
    pg_0.beginDraw();
    pg_0.image(img0, 0, 0);
    pg_0.endDraw();
    
    pg_1.beginDraw();
    pg_1.image(img1, 0, 0);
    pg_1.endDraw();

    DwUtils.changeTextureWrap  (pg_0, GLES30.GL_MIRRORED_REPEAT);
    DwUtils.changeTextureFilter(pg_0, GLES30.GL_LINEAR_MIPMAP_LINEAR, GLES30.GL_LINEAR);
    DwUtils.generateMipMaps    (pg_0);
    DwUtils.changeTextureWrap  (pg_1, GLES30.GL_MIRRORED_REPEAT);
    DwUtils.changeTextureFilter(pg_1, GLES30.GL_LINEAR_MIPMAP_LINEAR, GLES30.GL_LINEAR);
    DwUtils.generateMipMaps    (pg_1);
    
    frameRate(60);
  }


  public void draw() {
    
    if(mousePressed){
      toy.set_iMouse(mouseX, height-1-mouseY, mouseX, height-1-mouseY);
    }
    toy.set_iChannel(0, pg_0);
    toy.set_iChannel(1, pg_1);
    toy.apply(this.g);
    
//    image(pg_0, 0, 0);
//    image(pg_1, 0, 0);

    String txt_fps = String.format(getClass().getSimpleName()+ "   [size %d/%d]   [frame %d]   [fps %6.2f]", width, height, frameCount, frameRate);
//    surface.setTitle(txt_fps);
  }
  
  
  public static void main(String args[]) {
    PApplet.main(new String[] { Shadertoy_AbstractCorridor3.class.getName() });
  }
}