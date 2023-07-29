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
import android.util.Log;

import processing.core.PApplet;
import processing.core.PImage;
import processing.dw.DwFilter;
import processing.dw.DwGLTexture;
import processing.dw.DwPixelFlow;
import processing.dw.DwShadertoy;
import processing.dw.MyPApplet;
import processing.test.sketch_230701b.R;


public class Shadertoy_AbstractCorridor extends MyPApplet {
  
  //
  // Shadertoy Demo:   https://www.shadertoy.com/view/MlXSWX
  // Shadertoy Author: https://www.shadertoy.com/user/Shane
  //
  
  DwPixelFlow context;
  DwShadertoy toy;

  DwGLTexture tex_0 = new DwGLTexture();
  DwGLTexture tex_1 = new DwGLTexture();

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
    
    
    // load assets
//    String resImage0 = context.activity.getResources().getResourceName(R.mipmap.abstract_2);
//    String resImage1 = context.activity.getResources().getResourceName(R.mipmap.wood);



    PImage img0 = loadImage(context.activity,R.raw.abstract_2);
    PImage img1 = loadImage(context.activity,R.raw.wood);


    Log.d("heyibin","img0:" + img0);


    // create textures
    tex_0.resize(context, GLES30.GL_RGBA8, img0.width, img0.height, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, GLES30.GL_LINEAR, GLES30.GL_MIRRORED_REPEAT, 4,1);
    tex_1.resize(context, GLES30.GL_RGBA8, img1.width, img1.height, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, GLES30.GL_LINEAR, GLES30.GL_MIRRORED_REPEAT, 4,1);



    // copy images to textures
    DwFilter.get(context).copy.apply(img0, tex_0);
    DwFilter.get(context).copy.apply(img1, tex_1);
    
    // mipmap
    DwShadertoy.setTextureFilter(tex_0, DwShadertoy.TexFilter.MIPMAP);
    DwShadertoy.setTextureFilter(tex_1, DwShadertoy.TexFilter.MIPMAP);
    
    frameRate(60);
  }


  public void draw() {
    
    if(mousePressed){
      toy.set_iMouse(mouseX, height-1-mouseY, mouseX, height-1-mouseY);
    }
    toy.set_iChannel(0, tex_0);
    toy.set_iChannel(1, tex_1);
    toy.apply(this.g);

    String txt_fps = String.format(getClass().getSimpleName()+ "   [size %d/%d]   [frame %d]   [fps %6.2f]", width, height, frameCount, frameRate);
//    surface.setTitle(txt_fps);
  }
  
  
  public static void main(String args[]) {
    PApplet.main(new String[] { Shadertoy_AbstractCorridor.class.getName() });
  }
}