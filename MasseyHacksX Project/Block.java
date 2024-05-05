import java.awt.Rectangle;
import java.util.ArrayList;

public class Block{
  private Rectangle rect;
  
  public Block(int x, int y, int l, int w){
    rect = new Rectangle(x, y, l, w);
  }
  
  public static ArrayList<Block> loadBlocks(Player p){
    ArrayList<Block> ans = new ArrayList<Block>();
    for(int y = 0; y < 12; y ++){
      for(int x = 0; x < 16; x ++){
        if(Levels.levels[p.getLevel()][y][x] == Levels.BLOCK){
          ans.add(new Block(x * 32, y * 32, 32, 32));
        }
      }
    }
    return ans;
  }
  
  public Rectangle getRect(){
    return rect;
  }
}