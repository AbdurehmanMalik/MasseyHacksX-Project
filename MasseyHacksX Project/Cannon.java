import java.awt.Rectangle;
import java.util.ArrayList;

public class Cannon{
  public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
  private Rectangle rect;
  private int direction;
  
  public Cannon(int x, int y, int l, int w, int direction){
    rect = new Rectangle(x, y, l, w);
    this.direction = direction;
  }
  
  public static ArrayList<Cannon> loadCannons(Player p){
    ArrayList<Cannon> ans = new ArrayList<Cannon>();
    for(int y = 0; y < 12; y ++){
      for(int x = 0; x < 16; x ++){
        if(Levels.levels[p.getLevel()][y][x] == Levels.CANNON_UP){
          ans.add(new Cannon(x * 32, y * 32, 32, 32, UP));
        }
        else if(Levels.levels[p.getLevel()][y][x] == Levels.CANNON_DOWN){
          ans.add(new Cannon(x * 32, y * 32, 32, 32, DOWN));
        }
        else if(Levels.levels[p.getLevel()][y][x] == Levels.CANNON_RIGHT){
          ans.add(new Cannon(x * 32, y * 32, 32, 32, RIGHT));
        }
        else if(Levels.levels[p.getLevel()][y][x] == Levels.CANNON_LEFT){
          ans.add(new Cannon(x * 32, y * 32, 32, 32, LEFT));
        }
      }
    }
    return ans;
  }
  
  public Projectile shoot(){
    if(direction == UP){
      return new Projectile(rect.x, rect.y, Projectile.UP);
    }
    else if(direction == DOWN){
      return new Projectile(rect.x, rect.y + 32, Projectile.DOWN);
    }
    else if(direction == RIGHT){
      return new Projectile(rect.x + 32, rect.y, Projectile.RIGHT);
    }
    else{
      return new Projectile(rect.x, rect.y, Projectile.LEFT);
    }
  }
  
  public Rectangle getRect(){
    return rect;
  }
  public int getDirection(){
    return direction;
  }
}