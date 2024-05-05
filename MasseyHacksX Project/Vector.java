/*
 * 
 * 
 * 
 */

public class Vector{
  public static final Vector UP = new Vector(0, -1), DOWN = new Vector(0, 1), RIGHT = new Vector(1, 0), LEFT = new Vector(-1, 0);
  private int x, y;
  
  public Vector(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  public void add(Vector v){
    x += v.x;
    y += v.y;
  }
  
  public void subtract(Vector v){
    x -= v.x;
    y -= v.y;
  }
  
  public void multiply(int n){
    x *= n;
    y *= n;
  }
  
  public int getX(){
    return x;
  }
  public int getY(){
    return y;
  }
  
  public void setX(int x){
    this.x = x;
  }
  public void setY(int y){
    this.y = y;
  }
}