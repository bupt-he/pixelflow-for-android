/**
 * 
 * PixelFlow | Copyright (C) 2016 Thomas Diewald - http://thomasdiewald.com
 * 
 * A Processing/Java library for high performance GPU-Computing (GLSL).
 * MIT License: https://opensource.org/licenses/MIT
 * 
 */


package processing.dw;



public interface DwCollisionObject {

  public void resetCollisionPtr();
  public void update(DwCollisionObject othr);

  public float x();
  public float y();
  public float z();
  public float rad();
  public float radCollision();
  public int getCollisionCount();
}
