import java.awt.Rectangle;
import java.util.ArrayList;

public class Player{
  public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
//  public static final Vector UP = new Vector(0, -1), DOWN = new Vector(0, 1), RIGHT = new Vector(1, 0), LEFT = new Vector(-1, 0);
  private Rectangle rect;
//  private Vector pos, startPos, lastMove, direction;
  private int level, diveCD, dashCD, diveCount, dashCount, jumpCount, frame, frameCD, startX, startY, preX, preY, direction;
  private boolean isDiving, isDashing, isFalling, victory;
  
  public Player(int x, int y, int level){
    rect = new Rectangle(x, y, 32, 32);
    startX = x;
    startY = y;
    preX = x;
    preY = y;
    direction = RIGHT;
    this.level = level; //indice in Levels.levels
    diveCD = 0;
    dashCD = 0;
    diveCount = 0;
    dashCount = 0;
    jumpCount = 0;
    frame = 0;
    frameCD = 8;
    isDiving = false;
    isDashing = false;
    isFalling = false;
    victory = false;
  }
  
  public static Player loadPlayer(int level){
    for(int y = 0; y < 12; y ++){
      for(int x = 0; x < 16; x ++){
        if(Levels.levels[level][y][x] == Levels.PLAYER_START){
          return new Player(x * 32, y * 32, level);
        }
      }
    }
    return null;
  }
  
  public Player checkFinish(){
    for(int y = 0; y < 12; y ++){
      for(int x = 0; x < 16; x ++){
        if(Levels.levels[level][y][x] == Levels.LEVEL_END && rect.intersects(new Rectangle(x * 32, y * 32, 32 ,32))){
          if(level != 4){
            return loadPlayer(level + 1);
          }
          else{
            victory = true;
          }
        }
      }
    }
    return this;
  }
  
  public void jump(){
    if(!isFalling && jumpCount == 0){
      jumpCount = 16;
    }
  }
  
  public void dash(){
    if(!isDashing){
      dashCount = 16;
    }
  }
  
  public void dive(){
    if(!isDiving && isFalling){
      isDiving = true;
    }
  }
  
  public void advanceFrame(){
    if(frameCD == 0){
      if(frame == 3){
        frame = 0;
      }
      else{
        frame ++;
      }
      frameCD = 8;
    }
    else{
      frameCD --;
    }
  }
  
  public void processBlockCollisions(ArrayList<Block> blocks){
    for(Block b : blocks){
      if(rect.intersects(b.getRect())){
        rect.x = preX;
        rect.y = preY;
      }
    }
    if(rect.x < 0 || rect.x > 512 - 32){
      rect.x = preX;
      rect.y = preY;
    }
  }
  
  public void processSpikeCollisions(ArrayList<Spike> spikes){
    for(Spike s : spikes){
      if(rect.intersects(s.getRect())){
        respawn();
      }
    }
  }
  
  public boolean getVictory(){
    return victory;
  }
  
  public ArrayList<Projectile> processProjCollisions(ArrayList<Projectile> ps){
    ArrayList<Projectile> tmp = ps;
    for(int i = 0; i < ps.size(); i ++){
      if(rect.intersects(ps.get(i).getRect())){
        tmp.remove(i);
        if(!isDiving){
          respawn();
        }
        else{
          isDiving = false;
          isFalling = false;
          jump();
        }
      }
    }
    return tmp;
  }
  
  public void checkFalling(ArrayList<Block> blocks, ArrayList<Cannon> cannons){
    isFalling = true;
    for(Block b : blocks){
      if(b.getRect().y == rect.y + 32 && (b.getRect().x > rect.x - 32 && b.getRect().x < rect.x + 32)){
        isFalling = false;
      }
    }
    for(Cannon c : cannons){
      if(c.getRect().y == rect.y + 32 && (c.getRect().x > rect.x - 32 && c.getRect().x < rect.x + 32)){
        isFalling = false;
      }
    }
  }
  
  public void checkDiving(ArrayList<Block> blocks, ArrayList<Cannon> cannons){
    if(isDiving && !isFalling){
      isDiving = false;
      jump();
    }
    if(isDiving && isFalling){
      for(Block b : blocks){
        if(b.getRect().y == rect.y + 34 && (b.getRect().x > rect.x - 32 || b.getRect().x < rect.x + 32)){
          isFalling = false;
          isDiving = false;
          jump();
        }
      }
      for(Cannon c : cannons){
        if(c.getRect().y == rect.y + 34 && (c.getRect().x > rect.x - 32 || c.getRect().x < rect.x + 32)){
          isFalling = false;
          isDiving = false;
          jump();
        }
      }
    }
  }
  
  public void move(int direction){
    preX = rect.x;
    preY = rect.y;
    this.direction = direction;
    if(direction == RIGHT){
      rect.x += 2;
    }
    else if(direction == LEFT){
      rect.x -= 2;
    }
  }
  
  public void respawn(){
    rect.x = startX;
    rect.y = startY;
    dashCD = 0;
    diveCD = 0;
    isDashing = false;
    isDiving = false;
    jumpCount = 0;
    frame = 0;
    frameCD = 0;
    direction = RIGHT;
  }
  
  public void processJumps(){
    if(jumpCount > 0){
      preX = rect.x;
      preY = rect.y;
      rect.y -= 4;
      jumpCount --;
    }
  }
  
  public void processDashs(){
    if(dashCount > 0){
      preX = rect.x;
      preY = rect.y;
      if(direction == RIGHT){
        rect.x += 4;
      }
      else{
        rect.x -= 4;
      }
      dashCount --;
    }
    else{
      isDashing = false;
    }
  }
  
  public void processFalls(){
    if(isFalling && jumpCount == 0){
      preX = rect.x;
      preY = rect.y;
      rect.y += 2;
      if(isDiving){
        rect.y += 2;
      }
    }
  }
  
  public int getJumpCount(){
    return jumpCount;
  }
  public int getLevel(){
    return level;
  }
  public int getFrame(){
    return frame;
  }
  public Rectangle getRect(){
    return rect;
  }
}