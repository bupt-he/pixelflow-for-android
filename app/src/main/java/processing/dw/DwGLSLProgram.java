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
import android.opengl.GLES32;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Stack;


public class DwGLSLProgram {
  
  public DwPixelFlow context;
  public int HANDLE;

  public DwGLSLShader vert;
  public DwGLSLShader geom;
  public DwGLSLShader frag;
 
  public String name;
  
  public DwGLSLProgram(DwPixelFlow context, String vert_path, String geom_path, String frag_path) {
    this.context = context;
    
    this.vert = new DwGLSLShader(context, GLES30.GL_VERTEX_SHADER  , vert_path);
//    this.geom = new DwGLSLShader(context, GLES30.GL_GEOMETRY_SHADER, geom_path);
    this.frag = new DwGLSLShader(context, GLES30.GL_FRAGMENT_SHADER, frag_path);
    this.name = vert.path+"/"+geom.path+"/"+frag.path;
  }
  
  public DwGLSLProgram(DwPixelFlow context, String vert_path, String frag_path) {
    this.context = context;
    
    this.vert = new DwGLSLShader(context, GLES30.GL_VERTEX_SHADER  , vert_path);
    this.frag = new DwGLSLShader(context, GLES30.GL_FRAGMENT_SHADER, frag_path);
    this.name = vert.path+"/"+frag.path;
  }

  public void release(){
    if(vert != null) vert.release();
    if(frag != null) frag.release();
    if(geom != null) geom.release();
    GLES30.glDeleteProgram(HANDLE); HANDLE = 0;
  }
  
  private boolean build(DwGLSLShader shader){
    return (shader != null) && shader.build();
  }
  
  public DwGLSLProgram build() {
    if((build(vert) | build(geom) | build(frag)) || (HANDLE == 0)){
     
      if(HANDLE == 0){
        HANDLE = GLES30.glCreateProgram();
      } else {
        if(vert != null) GLES30.glDetachShader(HANDLE, vert.HANDLE);
        if(geom != null) GLES30.glDetachShader(HANDLE, geom.HANDLE);
        if(frag != null) GLES30.glDetachShader(HANDLE, frag.HANDLE);
      }
      
      if(vert != null) GLES30.glAttachShader(HANDLE, vert.HANDLE);  DwGLError.debug( "DwGLSLProgram.build  1");
      if(geom != null) GLES30.glAttachShader(HANDLE, geom.HANDLE);  DwGLError.debug("DwGLSLProgram.build 2");
      if(frag != null) GLES30.glAttachShader(HANDLE, frag.HANDLE);  DwGLError.debug( "DwGLSLProgram.build 3");

      GLES30.glLinkProgram(HANDLE);
      
  //    gl.glValidateProgram(HANDLE);
      DwGLSLProgram.getProgramValidateStatus( HANDLE);
      DwGLSLProgram.getProgramInfoLog( HANDLE, ">> PROGRAM_INFOLOG: "+name+":\n");
  
      DwGLError.debug( "DwGLSLProgram.build");
      
      uniform_loc.clear();
    }
    return this;
  }


  //////////////////////////////////////////////////////////////////////////////
  //
  // ERROR CHECKING
  //
  //////////////////////////////////////////////////////////////////////////////
  
  
  // Query information
  public static void getProgramInfoLog(int program_id, String info) {
    if(program_id==-1) return;
    
    IntBuffer log_len = IntBuffer.allocate(1);
    GLES30.glGetProgramiv(program_id, GLES30.GL_INFO_LOG_LENGTH, log_len);

    ByteBuffer buffer = ByteBuffer.allocate(log_len.get(0));
    GLES30.glGetProgramInfoLog(program_id);
    
//    buffer.put(log_len.get(0)-1, (byte) ' ');
    String log = Charset.forName("US-ASCII").decode(buffer).toString();
    
    if( log.length() > 1 && log.charAt(0) != 0){
      System.out.println(info);
      System.out.println(log);
    }
  }
  

  public static void getProgramValidateStatus(int program_id) {
    if(program_id==-1) return;
    
    IntBuffer log_len = IntBuffer.allocate(1);
    GLES30.glGetProgramiv(program_id, GLES30.GL_VALIDATE_STATUS, log_len);
    
    ByteBuffer buffer = ByteBuffer.allocate(log_len.get(0));
    GLES30.glGetProgramInfoLog(program_id);
    
    String log = Charset.forName("US-ASCII").decode(buffer).toString();
    
    if( log.length() > 1 && log.charAt(0) != 0){
      System.out.println(log);
    }
  }
  
  
  
  
  
  
  
  
  //////////////////////////////////////////////////////////////////////////////
  //
  // BEGIN / END
  //
  //////////////////////////////////////////////////////////////////////////////

  public void begin(){
    build();
    GLES30.glUseProgram(HANDLE);
  }
  
  public void end(){
    clearUniformTextures();
    clearAttribLocations();
    GLES30.glUseProgram(0);
  }
  
  public void end(String error_msg){
    end();
    DwGLError.debug( error_msg);
  }

  
  
  
  
  
  
  
  
  
  //////////////////////////////////////////////////////////////////////////////
  //
  // UNIFORMS
  //
  //////////////////////////////////////////////////////////////////////////////
  
  HashMap<String, Integer> uniform_loc = new HashMap<String, Integer>();
  HashMap<String, Integer> attrib_loc = new HashMap<String, Integer>();

  public boolean LOG_WARNINGS = true;
  
  int warning_count = 0;
  
  private int getUniformLocation(String uniform_name){
    int LOC_name = -1;
    Integer loc = uniform_loc.get(uniform_name);
    if(loc != null){
      LOC_name = loc;
    } else {
      LOC_name = GLES30.glGetUniformLocation(HANDLE, uniform_name);
      if(LOC_name != -1){
        uniform_loc.put(uniform_name, LOC_name);
      }
    }
    if(LOC_name == -1){
      if(LOG_WARNINGS && warning_count < 20){
        System.out.println(name+": uniform location \""+uniform_name+"\" = -1");
        warning_count++;
      }
    }
    return LOC_name;
  }
  
  
  private int getAttribLocation(String attrib_name){
    int LOC_name = -1;
    Integer loc = attrib_loc.get(attrib_name);
    if(loc != null){
      LOC_name = loc;
    } else {
      LOC_name = GLES30.glGetAttribLocation(HANDLE, attrib_name);
      if(LOC_name != -1){
        attrib_loc.put(attrib_name, LOC_name);
      }
    }
    if(LOC_name == -1){
      if(LOG_WARNINGS && warning_count < 20){
        System.out.println(name+": attrib location \""+attrib_name+"\" = -1");
        warning_count++;
      }
    }
    return LOC_name;
  }
  
 
  
  
  
  

  public static class UniformTexture{
    String name = null;;
    int loc = -1;
    int loc_idx = -1;
    int target = -1;
    int handle = -1;
    
    public UniformTexture(String name, int loc, int loc_idx, int handle, int target) {
      this.name = name;
      this.loc = loc;
      this.loc_idx = loc_idx;
      this.target = target;
      this.handle = handle;
    }
  }
  
  public Stack<UniformTexture> uniform_textures = new Stack<>();
  
  public int uniformTexture(String uniform_name, DwGLTexture texture){
    return uniformTexture(uniform_name, texture.HANDLE[0], texture.target);
  }
  public int uniformTexture(String uniform_name, DwGLTexture3D texture){
    return uniformTexture(uniform_name, texture.HANDLE[0], texture.target);
  }
  public int uniformTexture(String uniform_name, int HANDLE_tex){
    return uniformTexture(uniform_name,HANDLE_tex, GLES30.GL_TEXTURE_2D);
  }
  public int uniformTexture(String uniform_name, int HANDLE_tex, int target){
    int loc = getUniformLocation(uniform_name);
    if(loc != -1){
      UniformTexture untex = new UniformTexture(uniform_name, loc, uniform_textures.size(), HANDLE_tex, target);
      uniform_textures.push(untex);

      GLES30.glUniform1i(loc, untex.loc_idx);
      GLES30.glActiveTexture(GLES30.GL_TEXTURE0 + untex.loc_idx);
      GLES30.glBindTexture(untex.target, untex.handle);
    }
    return uniform_textures.size();
  }
  
  public void clearUniformTextures(){
    while(!uniform_textures.empty()){
      UniformTexture untex = uniform_textures.pop();
      GLES30.glActiveTexture(GLES30.GL_TEXTURE0 + untex.loc_idx);
      GLES30.glBindTexture(untex.target, 0);
    }
  }
  
  
  

  
  
  
  
  public void uniform1fv(String uniform_name, int count, float[] vec1){
    GLES30.glUniform1fv(getUniformLocation(uniform_name), count, vec1, 0);
  }
  public void uniform2fv(String uniform_name, int count, float[] vec2){
    GLES30.glUniform2fv(getUniformLocation(uniform_name), count, vec2, 0);
  }
  public void uniform3fv(String uniform_name, int count, float[] vec3){
    GLES30.glUniform3fv(getUniformLocation(uniform_name), count, vec3, 0);
  }
  public void uniform4fv(String uniform_name, int count, float[] vec4){
    GLES30.glUniform4fv(getUniformLocation(uniform_name), count, vec4, 0);
  }
  
  
  public void uniformMatrix2fv(String uniform_name, int count, boolean transpose, float[] buffer, int offset){
    GLES30.glUniformMatrix2fv(getUniformLocation(uniform_name), count, transpose, buffer, offset);
  }
  public void uniformMatrix3fv(String uniform_name, int count, boolean transpose, float[] buffer, int offset){
    GLES30.glUniformMatrix3fv(getUniformLocation(uniform_name), count, transpose, buffer, offset);
  }
  public void uniformMatrix4fv(String uniform_name, int count, boolean transpose, float[] buffer, int offset){
    GLES30.glUniformMatrix4fv(getUniformLocation(uniform_name), count, transpose, buffer, offset);
  }
  
  
  public void uniformMatrix2fv(String uniform_name, int count, float[] buffer, int offset){
    GLES30.glUniformMatrix2fv(getUniformLocation(uniform_name), count, false, buffer, offset);
  }
  public void uniformMatrix3fv(String uniform_name, int count, float[] buffer, int offset){
    GLES30.glUniformMatrix3fv(getUniformLocation(uniform_name), count, false, buffer, offset);
  }
  public void uniformMatrix4fv(String uniform_name, int count, float[] buffer, int offset){
    GLES30.glUniformMatrix4fv(getUniformLocation(uniform_name), count, false, buffer, offset);
  }
  
  
  
  public void uniform1f(String uniform_name, float v0){
    GLES30.glUniform1f(getUniformLocation(uniform_name), v0);
  }
  public void uniform2f(String uniform_name, float v0, float v1){
    GLES30.glUniform2f(getUniformLocation(uniform_name), v0, v1);
  }
  public void uniform3f(String uniform_name, float v0, float v1, float v2){
    GLES30.glUniform3f(getUniformLocation(uniform_name), v0, v1, v2);
  }
  public void uniform4f(String uniform_name, float v0, float v1, float v2, float v3){
    GLES30.glUniform4f(getUniformLocation(uniform_name), v0, v1, v2, v3);
  }
  
  
  public void uniform1i(String uniform_name, int v0){
    GLES30.glUniform1i(getUniformLocation(uniform_name), v0);
  }
  public void uniform2i(String uniform_name, int v0, int v1){
    GLES30.glUniform2i(getUniformLocation(uniform_name), v0, v1);
  }
  public void uniform3i(String uniform_name, int v0, int v1, int v2){
    GLES30.glUniform3i(getUniformLocation(uniform_name), v0, v1, v2);
  }
  public void uniform4i(String uniform_name, int v0, int v1, int v2, int v3){
    GLES30.glUniform4i(getUniformLocation(uniform_name), v0, v1, v2, v3);
  }
  
  
  
  
  
  
  
  
  
  
  
  

  
  public int attributeArrayBuffer(String attrib_name, int HANDLE_buffer, int size, int type, boolean normalized, int stride, long pointer_buffer_offset){
    int LOC_ATTRIB = getAttribLocation(attrib_name);
    if( LOC_ATTRIB != -1 ){
      GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, HANDLE_buffer);
//      GLES30.glVertexAttribPointer    (LOC_ATTRIB, size, type, normalized, stride, pointer_buffer_offset);
      GLES30.glEnableVertexAttribArray(LOC_ATTRIB);
      vertex_attrib_arrays.push   (LOC_ATTRIB);
    }
    return LOC_ATTRIB;
  }
  
  
  
  public Stack<Integer> vertex_attrib_arrays = new Stack<>();
  public void clearAttribLocations(){
    while(!vertex_attrib_arrays.empty()){
      Integer loc = vertex_attrib_arrays.pop();
      if(loc != -1) GLES30.glDisableVertexAttribArray(loc);
    }
  }
  
  
  
  
  
  
  
  
  
  
  
  
  

  //////////////////////////////////////////////////////////////////////////////
  //
  // DRAWING
  //
  //////////////////////////////////////////////////////////////////////////////
  
  public void scissors(int x, int y, int w, int h){
    GLES30.glEnable(GLES30.GL_SCISSOR_TEST);
    GLES30.glScissor(x,y,w,h);
  }
  
  public void viewport(int x, int y, int w, int h){
    GLES30.glViewport(x,y,w,h);
  }
  
  
  
  public void drawFullScreenQuad(){
    GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
  }
  
  public void drawFullScreenLines(int num_lines, float line_width){
    drawFullScreenLines(num_lines, line_width, true);
  }
  
  public void drawFullScreenLines(int num_lines, float line_width, boolean smooth){
    if(smooth){
      // TODO: 2023/7/1  
//      GLES30.glEnable(GLES30.GL_LINE_SMOOTH);
//      GLES30.glHint(GLES30.GL_LINE_SMOOTH_HINT, GLES30.GL_FASTEST);
//      gl.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_NICEST);
    } else {
//      GLES30.glDisable(GLES30.GL_LINE_SMOOTH);
    }
    GLES30.glLineWidth(line_width);
    GLES30.glDrawArrays(GLES30.GL_LINES, 0, num_lines * 2);
  }
  
  
  public void drawFullScreenPoints(int num_points){
    // TODO: 2023/7/1  
//    GLES30.glEnable(GLES30.GL_PROGRAM_POINT_SIZE);
    GLES30.glDrawArrays(GLES30.GL_POINTS, 0, num_points);
  }
  
  
//  public void drawFullScreenQuads(int num_quads){
////    gl.glDrawArrays(GL2.GL_QUADS, 0, num_quads * 1);
//    GL2ES3 gl2es3 = gl.getGL2ES3();
//    gl2es3.glDrawArraysInstanced(GL2.GL_TRIANGLE_STRIP, 0, 4, num_quads);  //draw #count quads
//  }
  
  
  
  
  
  
  
  
  
  
  
//  @Deprecated
//  public void drawFullScreenPoints(int num_points){
//    drawFullScreenPoints(num_points,true);
//  }
//  
//  @Deprecated
//  public void drawFullScreenPoints(int num_points, boolean smooth){
//    // START ... of the problem, TODO
//    //
//    {
////       gl.glEnable(GL2.GL_VERTEX_PROGRAM_POINT_SIZE);
//      // gl.glEnable(GL2.GL_POINT_SPRITE); // "gl_pointcoord always zero" ... bug, TODO
//       gl.glEnable(GL3.GL_PROGRAM_POINT_SIZE);
//  
//      // gl.getGL2().glTexEnvf(GL2.GL_POINT_SPRITE, GL2.GL_COORD_REPLACE, GL2.GL_TRUE);
//  
////      if(smooth){
////        gl.glEnable(GL2.GL_POINT_SMOOTH);
////        gl.glHint(GL2.GL_POINT_SMOOTH_HINT, GL2.GL_FASTEST);
////      } else {
////        gl.glDisable(GL2.GL_POINT_SMOOTH);
////      }
//    }
//    //
//    // END ... of the problem, TODO
//    
//    DwGLError.debug(gl, "DwGLSLProgram.drawFullScreenPoints");
//    gl.glDrawArrays(GL2.GL_POINTS, 0, num_points);
//  }
  
  


  
  
  

  
}