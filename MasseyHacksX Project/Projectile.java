import java.awt.Rectangle;
import java.util.ArrayList;

public class Projectile{
  public static final Vector UP = new Vector(0, -1), DOWN = new Vector(0, 1), RIGHT = new Vector(1, 0), LEFT = new Vector(-1, 0);
  private Rectangle rect;
  private Vector direction;
  private int spd;
  
  public static ArrayList<Projectile> processBlockCollisions(ArrayList<Projectile> ps, ArrayList<Block> blocks){
    ArrayList<Projectile> tmp = ps;
    for(int i = 0; i < tmp.size(); i ++){
      for(Block b : blocks){
        if(tmp.get(i).getRect().intersects(b.getRect())){
          ps.remove(i);
          break;
        }
      }
    }
    return ps;
  }
  
  public static ArrayList<Projectile> processSpikeCollisions(ArrayList<Projectile> ps, ArrayList<Spike> spikes){
    ArrayList<Projectile> tmp = ps;
    for(int i = 0; i < tmp.size(); i ++){
      for(Spike s : spikes){
        if(tmp.get(i).getRect().intersects(s.getRect())){
          ps.remove(i);
          break;
        }
      }
    }
    return ps;
  }
  
  public Projectile(int x, int y, Vector direction){
    rect = new Rectangle(x, y, 32, 32);
    this.direction = direction;
    spd = 4;
  }
  
  public Rectangle getRect(){
    return rect;
  }
  
  public void move(){
    rect.x += direction.getX() * spd;
    rect.y += direction.getY() * spd;
  }
}