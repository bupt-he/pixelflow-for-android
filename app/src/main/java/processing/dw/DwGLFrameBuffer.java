/**
 * 
 * PixelFlow | Copyright (C) 2016 Thomas Diewald - http://thomasdiewald.com
 * 
 * A Processing/Java library for high performance GPU-Computing (GLSL).
 * MIT License: https://opensource.org/licenses/MIT
 * 
 */




package processing.dw;

import android.annotation.SuppressLint;
import android.opengl.GLES30;
import android.opengl.GLES32;


public class DwGLFrameBuffer {
  

  
  public final int[] HANDLE_fbo = {0};
  public int[] bind_color_attachments = new int[0]; // currently bound rendertargets
  public int[] bind_targets           = new int[0]; // currently bound rendertargets
  
//  public int max_color_attachments;
  public int max_draw_buffers;
//  public int max_bind;
  
  public DwGLFrameBuffer(){
  }

  public boolean isFBO(){
    return  (HANDLE_fbo[0] > 0);
  }
  
  public void release(){
    if(!isFBO()){
      return;
    }

    GLES30.glDeleteFramebuffers(1, HANDLE_fbo, 0);
    HANDLE_fbo[0] = 0;
  }
  

  public void allocate( ){
    if(!isFBO()){

      GLES30.glGenFramebuffers(1, HANDLE_fbo, 0);
      
      int[] buf = new int[1];
//      gl.glGetIntegerv(GL2.GL_MAX_COLOR_ATTACHMENTS, buf, 0);
//      max_color_attachments = buf[0];
      GLES30.glGetIntegerv(GLES30.GL_MAX_DRAW_BUFFERS, buf, 0);
      max_draw_buffers = buf[0];
      
//      max_bind = Math.min(max_draw_buffers, max_color_attachments);
      
//      max_bind = max_draw_buffers;
    }
  }
  
  
  
  private static DwGLFrameBuffer active_fbo = null;
  
  public void bindFBO(){
    if(active_fbo != this){
      GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, HANDLE_fbo[0]);
      active_fbo = this;
    }
  }
  
  public void unbindFBO(){
    if(active_fbo != null){
      GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);
      active_fbo = null;
    }
  }

  
  // textures must be of the same size
  @SuppressLint("NewApi")
  public void bind(int ... HANDLE_tex){
    
    if(IS_ACTIVE){
      unbind(); // unbind, in case of bind() is called consecutively
    }
    
    int count = HANDLE_tex.length;  
    if(count > max_draw_buffers){
      System.out.println("WARNING: DwGLFrameBuffer.bind(...) number of textures exceeds max limit: "+count+" > "+max_draw_buffers);
      count = max_draw_buffers;
    }
    bind_color_attachments = new int[count];
    bind_targets           = new int[count];
    
    bindFBO();
    for(int i = 0; i < count; i++){
      bind_color_attachments[i] = GLES30.GL_COLOR_ATTACHMENT0 + i;
      bind_targets          [i] = GLES30.GL_TEXTURE_2D;
      GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, bind_color_attachments[i], GLES30.GL_TEXTURE_2D, HANDLE_tex[i], 0);
    }

    GLES30.glDrawBuffers(bind_color_attachments.length, bind_color_attachments, 0);
    IS_ACTIVE = true;
  }
  
  
  public boolean IS_ACTIVE = false;
  
   
  
  public void bind(DwGLTexture ... tex){
   
    if(IS_ACTIVE){
      unbind(); // unbind, in case of bind() is called consecutively
    }
    
    int count = tex.length;  
    if(count > max_draw_buffers){
      System.out.println("WARNING: DwGLFrameBuffer.bind(...) number of textures exceeds max limit: "+count+" > "+max_draw_buffers);
      count = max_draw_buffers;
    }
    bind_color_attachments = new int[count];
    bind_targets           = new int[count];
    
    bindFBO();
    for(int i = 0; i < count; i++){
      bind_color_attachments[i] = GLES30.GL_COLOR_ATTACHMENT0 + i;
      bind_targets          [i] = tex[i].target;
      GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, bind_color_attachments[i], tex[i].target, tex[i].HANDLE[0], 0);
    }


    GLES30.glDrawBuffers(bind_color_attachments.length, bind_color_attachments, 0);
    IS_ACTIVE = true;
  }
  
  public void bind(DwGLTexture3D tex, int ... layer){
    int num_layers = layer.length;
    DwGLTexture3D[] tex_ = new DwGLTexture3D[num_layers];
    for(int i = 0; i < num_layers; i++){
      tex_[i] = tex;
    }
    bind(tex_, layer);
  }
  
  @SuppressLint("NewApi")
  public void bind(DwGLTexture3D[] tex, int[] layer){
   
    if(IS_ACTIVE){
      unbind(); // unbind, in case of bind() is called consecutively
    }

    int count = tex.length;  
    if(count > max_draw_buffers){
      System.out.println("WARNING: DwGLFrameBuffer.bind(...) number of textures exceeds max limit: "+count+" > "+max_draw_buffers);
      count = max_draw_buffers;
    }
    bind_color_attachments = new int[count];
    bind_targets           = new int[count];
    
    bindFBO();
    for(int i = 0; i < count; i++){
      bind_color_attachments[i] = GLES30.GL_COLOR_ATTACHMENT0 + i;
      bind_targets          [i] = tex[i].target;
      GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, bind_color_attachments[i], tex[i].target, tex[i].HANDLE[0], 0);
    }

    GLES30.glDrawBuffers(bind_color_attachments.length, bind_color_attachments, 0);
    IS_ACTIVE = true;
  }
  
  
  
  
  /**
   *  GL3 glFramebufferTexture
   * @param tex
   */
  @SuppressLint("NewApi")
  public void bind(DwGLTexture3D ... tex){
   
    if(IS_ACTIVE){
      unbind(); // unbind, in case of bind() is called consecutively
    }
    
    int count = tex.length;  
    if(count > max_draw_buffers){
      System.out.println("WARNING: DwGLFrameBuffer.bind(...) number of textures exceeds max limit: "+count+" > "+max_draw_buffers);
      count = max_draw_buffers;
    }
    bind_color_attachments = new int[count];
    bind_targets           = null;
    
    bindFBO();
    for(int i = 0; i < count; i++){
      bind_color_attachments[i] = GLES30.GL_COLOR_ATTACHMENT0 + i;
      GLES32.glFramebufferTexture(GLES30.GL_FRAMEBUFFER, bind_color_attachments[i], tex[i].HANDLE[0], 0);
    }

    GLES30.glDrawBuffers(bind_color_attachments.length, bind_color_attachments, 0);
    IS_ACTIVE = true;
  }
  
  
  
  
  
  @SuppressLint("NewApi")
  public void unbind(){
    for(int i = 0; i < bind_color_attachments.length; i++){
      if(bind_targets == null)
      {
        GLES32.glFramebufferTexture(GLES30.GL_FRAMEBUFFER, bind_color_attachments[i], 0, 0);
      }
      else 
      {

        GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, bind_color_attachments[i], bind_targets[i], 0, 0);
//        if(   bind_targets[i] == GLES30.GL_TEXTURE_2D_ARRAY
//           || bind_targets[i] == GLES30.GL_TEXTURE_3D){
//          GLES30.glFramebufferTexture3D(GLES30.GL_FRAMEBUFFER, bind_color_attachments[i], bind_targets[i], 0, 0, 0);
//        } else {
//          GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, bind_color_attachments[i], bind_targets[i], 0, 0);
//        }
      }
    }
    bind_color_attachments = new int[0];
    unbindFBO();
    IS_ACTIVE = false;
  }
  
  public boolean isActive(){
    return IS_ACTIVE;
  }
  
  
  public void clearTexture(float r, float g, float b, float a, DwGLTexture ... tex){
    bind(tex);
    int w = tex[0].w();
    int h = tex[0].h();
    GLES30.glViewport(0, 0, w, h);
    GLES30.glColorMask(true, true, true, true);
    GLES30.glClearColor(r,g,b,a);
    GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
    unbind();
    DwGLError.debug("DwGLFrameBuffer.clearTexture");
  }
  
  
  
  public void clearTexture(float r, float g, float b, float a, DwGLTexture3D[] tex, int[] layer){
    bind(tex, layer);
    int w = tex[0].w();
    int h = tex[0].h();
    GLES30.glViewport(0, 0, w, h);
    GLES30.glColorMask(true, true, true, true);
    GLES30.glClearColor(r,g,b,a);
    GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
    unbind();
    DwGLError.debug( "DwGLFrameBuffer.clearTexture");
  }
  
  
//  public void clearTexture(float v, DwGLTexture ... tex){
//    clearTexture(v,v,v,v,tex);
//  }
//  public void clearTexture(float v, DwGLTexture3D[] tex, int[] layer){
//    clearTexture(v,v,v,v,tex);
//  }



  
  
  
  
  public void setRenderBuffer(int HANDLE_rbo, boolean depth, boolean stencil){
    boolean is_active = isActive();
    if(!is_active) bindFBO();
  
    if(depth  ) GLES30.glFramebufferRenderbuffer(GLES30.GL_FRAMEBUFFER, GLES30.GL_DEPTH_ATTACHMENT  , GLES30.GL_RENDERBUFFER, HANDLE_rbo);
    if(stencil) GLES30.glFramebufferRenderbuffer(GLES30.GL_FRAMEBUFFER, GLES30.GL_STENCIL_ATTACHMENT, GLES30.GL_RENDERBUFFER, HANDLE_rbo);

    if(!is_active) unbind();
  }
  
}
