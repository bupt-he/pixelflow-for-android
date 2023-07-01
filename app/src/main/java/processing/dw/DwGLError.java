/**
 * 
 * PixelFlow | Copyright (C) 2016 Thomas Diewald - http://thomasdiewald.com
 * 
 * A Processing/Java library for high performance GPU-Computing (GLSL).
 * MIT License: https://opensource.org/licenses/MIT
 * 
 */



package processing.dw;


import android.opengl.GLES30;

public class DwGLError {
  
  public static boolean DEBUG_OUT = false;
  
  public static int ERROR_CODE = 0;
  
  public static boolean debug( String debug_note) {
    ERROR_CODE = GLES30.glGetError();
    boolean has_error = (ERROR_CODE != GLES30.GL_NO_ERROR);
    return has_error;
  }
  
  
  public static boolean FBO(int[] handle_fbo){
    int rval = ERROR_CODE = GLES30.glCheckFramebufferStatus(handle_fbo[0]);
    System.out.println("glCheckFramebufferStatus = "+rval);
    if( rval == GLES30.GL_FRAMEBUFFER_COMPLETE ){
      return true;
    } else {
      
      
      if( rval == GLES30.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS         ) System.out.println("FBO-ERROR: GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS        ");
      // TODO: 2023/7/1  没有被定义
//      if( rval == GLES30.GL_FRAMEBUFFER_INCOMPLETE_FORMATS            ) System.out.println("FBO-ERROR: GL_FRAMEBUFFER_INCOMPLETE_FORMATS           ");
//      if( rval == GL2ES2.GL_FRAMEBUFFER_UNDEFINED                     ) System.out.println("FBO-ERROR: GL_FRAMEBUFFER_UNDEFINED                    ");
      if( rval == GLES30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT         ) System.out.println("FBO-ERROR: GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT        ");
      if( rval == GLES30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT ) System.out.println("FBO-ERROR: GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
//      if( rval == GL2ES2.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER        ) System.out.println("FBO-ERROR: GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER       ");
//      if( rval == GL2ES2.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER        ) System.out.println("FBO-ERROR: GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER       ");
      if( rval == GLES30.GL_FRAMEBUFFER_UNSUPPORTED                   ) System.out.println("FBO-ERROR: GL_FRAMEBUFFER_UNSUPPORTED                  ");
      if( rval == GLES30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE        ) System.out.println("FBO-ERROR: GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE       ");
      if( rval == GLES30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE        ) System.out.println("FBO-ERROR: GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE       ");
//      if( rval == GL2ES2.GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS      ) System.out.println("FBO-ERROR: GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS     ");
      System.out.println("FBO-ERROR: unknown errorcode");
    }
    
    return false;
  }
}
