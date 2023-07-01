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

public class DwGLInfo {
  
  
  static public int getActiveProgram(){
    int[] currentProgram = new int[1];
    GLES30.glGetIntegerv(GLES30.GL_CURRENT_PROGRAM, currentProgram, 0);
    return currentProgram[0];
  }

  
//  static public void getProfiles(){
//    System.out.println("--------------------------------------------------------------------------------");
//    System.out.println("GLProfile.GL_PROFILE_LIST_ALL");
//    String[] profilelist = GLES30..GL_PROFILE_LIST_ALL;
//    for(int i = 0; i < profilelist.length; i++){
//      System.out.printf("  [%2d] %6s available: %4s\n",i, profilelist[i], GLProfile.isAvailable(profilelist[i]));
//    }
//    System.out.println("--------------------------------------------------------------------------------");
//  }
  
  //http://www.opengl.org/sdk/docs/man/xhtml/glGet.xml
  //http://www.opengl.org/wiki/GLAPI/glGetIntegerv
  static public void getInfoOpenglGL4(){

    String version    = GLES30.glGetString(GLES30.GL_VERSION);
    String vendor     = GLES30.glGetString(GLES30.GL_VENDOR);
    String renderer   = GLES30.glGetString(GLES30.GL_RENDERER);
    String glsl       = GLES30.glGetString(GLES30.GL_SHADING_LANGUAGE_VERSION);
//    String extensions = gl.glGetString(GL4.GL_EXTENSIONS);
    
    System.out.println("--------------------------------------------------------------------------------");
    System.out.println("OPENGL_VERSION:    " + version          );
    System.out.println("OPENGL_VENDOR:     " + vendor           );
    System.out.println("OPENGL_RENDERER:   " + renderer         );
    System.out.println("GLSL_VErSION:      " + glsl             );
//    System.out.println("GL_EXTENSIONS: " + extensions       );
    System.out.println("--------------------------------------------------------------------------------");

//    StringBuilder sb = JoglVersion.getGLInfo(gl, new StringBuilder(), !true);
//    System.out.println(sb);
  }
  
  
  static public void getInfoOpenglExtensions(){
    System.out.println("--------------------------------------------------------------------------------");
    
    String extensions = GLES30.glGetString(GLES30.GL_EXTENSIONS);
    String[] tokens = extensions.split("\\s+");
    System.out.println("EXTENSIONS: "+tokens.length);
    for(int i = 0; i < tokens.length; i++){
      System.out.printf("[%4d] %s\n", i, tokens[i]);
    }
    System.out.println("--------------------------------------------------------------------------------");
  }

}
