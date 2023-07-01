package processing.dw;

import android.opengl.GLES30;



import processing.core.PApplet;
import processing.dwutil.DwUtils;
import processing.opengl.PGL;
import processing.opengl.PShader;

public class DwGeometryShader extends PShader {
  
  public int glGeometry;
  public String geometrySource;
  public String[] src_geom;
  public String filename_geom;

  public DwGeometryShader(PApplet papplet, String filename_vert, String filename_geom, String filename_frag) {
    super(papplet, filename_vert, filename_frag);
    
    this.filename_geom = filename_geom;
    
    this.src_geom = papplet.loadStrings(filename_geom);
    for(int i = 0; i < src_geom.length; i++){
      src_geom[i] += DwUtils.NL;
    }
  }
  
  
  public DwGeometryShader(PApplet papplet, String[] src_vert, String[] src_geom, String[] src_frag) {
    super(papplet, src_vert, src_frag);
    this.src_geom = src_geom;
    
    for(int i = 0; i < src_geom.length; i++){
      src_geom[i] += DwUtils.NL;
    }
  }

  // TODO: 2023/7/1  需要进行处理
  @Override
  protected void setup(){
//    PGL pjogl =  pgl;
//    
//    glGeometry = GLES30.glCreateShader(GLES30.GL_GEOMETRY_SHADER);
//    GLES30.glShaderSource(glGeometry, src_geom.length, src_geom, (int[]) null, 0);
//    GLES30.glCompileShader(glGeometry);
//    DwGLSLShader.getShaderInfoLog(glGeometry, GLES30.GL_GEOMETRY_SHADER+" ("+filename_geom+")");
//
//    pgl.attachShader(glProgram, glGeometry);
  }
}