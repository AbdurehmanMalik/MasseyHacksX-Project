import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener{
 Timer timer;
 Random rand;
 Player p1;
 Player p2;
 ArrayList<Block> p1Blocks;
 ArrayList<Block> p2Blocks;
 ArrayList<Cannon> p1Cannons;
 ArrayList<Cannon> p2Cannons;
 ArrayList<Spike> p1Spikes;
 ArrayList<Spike> p2Spikes;
 ArrayList<Projectile> p1Projectiles;
 ArrayList<Projectile> p2Projectiles;
 int offsetX, offsetY;
 boolean[] keys;
 
 public GamePanel(){
  setPreferredSize(new Dimension(512, 768)); //each player's screen is stacked over the other
  
  keys = new boolean[1000];
  rand = new Random();
  
  p1 = Player.loadPlayer(0);
  p2 = Player.loadPlayer(0);
  p1Blocks = Block.loadBlocks(p1);
  p2Blocks = Block.loadBlocks(p2);
  p1Cannons = Cannon.loadCannons(p1);
  p2Cannons = Cannon.loadCannons(p2);
  p1Spikes = Spike.loadSpikes(p1);
  p2Spikes = Spike.loadSpikes(p2);
  p1Projectiles = new ArrayList<Projectile>();
  p2Projectiles = new ArrayList<Projectile>();
  offsetX = 0;
  offsetY = 0;
  
  timer = new Timer(20, this);
  timer.start();
  setFocusable(true);
  requestFocus();
  addKeyListener(this);
  addMouseListener(this);
 }
 
 public void move(){
   p1.advanceFrame();
   p2.advanceFrame();
   for(Projectile p : p1Projectiles){
     p.move();
   }
   for(Projectile p : p2Projectiles){
     p.move();
   }
   p1Projectiles = Projectile.processBlockCollisions(p1Projectiles, p1Blocks);
   p1Projectiles = Projectile.processSpikeCollisions(p1Projectiles, p1Spikes);
   p2Projectiles = Projectile.processBlockCollisions(p2Projectiles, p2Blocks);
   p2Projectiles = Projectile.processSpikeCollisions(p2Projectiles, p2Spikes);
   if(keys[KeyEvent.VK_LEFT]){
     p1.move(Player.LEFT);
   }
   if(keys[KeyEvent.VK_RIGHT]){
     p1.move(Player.RIGHT);
   }
   if(keys[KeyEvent.VK_UP]){
     p1.jump();
   }
   if(keys[KeyEvent.VK_SHIFT]){
     p1.dash();
   }
   if(keys[KeyEvent.VK_DOWN]){
     p1.dive();
   }
   if(keys[KeyEvent.VK_S]){
     p2.move(Player.LEFT);
   }
   if(keys[KeyEvent.VK_F]){
     p2.move(Player.RIGHT);
   }
   if(keys[KeyEvent.VK_E]){
     p2.jump();
   }
   if(keys[KeyEvent.VK_X]){
     p2.dash();
   }
   if(keys[KeyEvent.VK_D]){
     p2.dive();
   }
   p1.processBlockCollisions(p1Blocks);
   p2.processBlockCollisions(p2Blocks);
   p1.processSpikeCollisions(p1Spikes);
   p2.processSpikeCollisions(p2Spikes);
   p1Projectiles = p1.processProjCollisions(p1Projectiles);
   p2Projectiles = p2.processProjCollisions(p2Projectiles);
   p1 = p1.checkFinish();
   p2 = p2.checkFinish();
   p1.processDashs();
   p2.processDashs();
   p1.processBlockCollisions(p1Blocks);
   p2.processBlockCollisions(p2Blocks);
   p1.processSpikeCollisions(p1Spikes);
   p2.processSpikeCollisions(p2Spikes);
   p1Projectiles = p1.processProjCollisions(p1Projectiles);
   p2Projectiles = p2.processProjCollisions(p2Projectiles);
   p1 = p1.checkFinish();
   p2 = p2.checkFinish();
   p1.processJumps();
   p2.processJumps();
   p1.processBlockCollisions(p1Blocks);
   p2.processBlockCollisions(p2Blocks);
   p1Projectiles = p1.processProjCollisions(p1Projectiles);
   p2Projectiles = p2.processProjCollisions(p2Projectiles);
   p1.processProjCollisions(p1Projectiles);
   p2.processProjCollisions(p2Projectiles);
   p1 = p1.checkFinish();
   p2 = p2.checkFinish();
   p1.processFalls();
   p2.processFalls();
   p1.processBlockCollisions(p1Blocks);
   p2.processBlockCollisions(p2Blocks);
   p1.processSpikeCollisions(p1Spikes);
   p2.processSpikeCollisions(p2Spikes);
   p1Projectiles = p1.processProjCollisions(p1Projectiles);
   p2Projectiles = p2.processProjCollisions(p2Projectiles);
   p1 = p1.checkFinish();
   p2 = p2.checkFinish();
   p1.checkFalling(p1Blocks, p1Cannons);
   p2.checkFalling(p2Blocks, p2Cannons);
   p1.checkDiving(p1Blocks, p1Cannons);
   p2.checkDiving(p2Blocks, p2Cannons);
 }
 
 public void shoot(){
   for(Cannon c : p1Cannons){
     if(rand.nextInt(100) == 0){
       p1Projectiles.add(c.shoot());
     }
   }
   for(Cannon c : p2Cannons){
     if(rand.nextInt(100) == 0){
       p2Projectiles.add(c.shoot());
     }
   }
 }
 
 @Override
 public void actionPerformed(ActionEvent e){
  if(!p1.getVictory() && !p2.getVictory()){
     p1Blocks = Block.loadBlocks(p1);
     p2Blocks = Block.loadBlocks(p2);
     p1Cannons = Cannon.loadCannons(p1);
     p2Cannons = Cannon.loadCannons(p2);
     p1Spikes = Spike.loadSpikes(p1);
     p2Spikes = Spike.loadSpikes(p2);
     move();
     shoot();
  }
  else{
    if(keys[KeyEvent.VK_ESCAPE]){
      p1 = Player.loadPlayer(0);
      p2 = Player.loadPlayer(0);
      p1Blocks = Block.loadBlocks(p1);
      p2Blocks = Block.loadBlocks(p2);
      p1Cannons = Cannon.loadCannons(p1);
      p2Cannons = Cannon.loadCannons(p2);
      p1Spikes = Spike.loadSpikes(p1);
      p2Spikes = Spike.loadSpikes(p2);
      p1Projectiles = new ArrayList<Projectile>();
      p2Projectiles = new ArrayList<Projectile>();
    }
  }
  repaint();
 }

 @Override
 public void keyPressed(KeyEvent e){
  keys[e.getKeyCode()] = true;
 }

 @Override
 public void keyReleased(KeyEvent e){
  keys[e.getKeyCode()] = false;  
 }

 @Override
 public void keyTyped(KeyEvent e){}

 @Override
 public void mouseClicked(MouseEvent e){}

 @Override
 public void mouseEntered(MouseEvent e){}

 @Override
 public void mouseExited(MouseEvent e){}
 
 @Override
 public void mousePressed(MouseEvent e){}
 
 @Override
 public void mouseReleased(MouseEvent e){}


 @Override
 public void paint(Graphics g){
   offsetX = (getWidth() - 512) / 2;
   offsetY = (getHeight() - 768) / 2;
   
   g.setColor(Color.GRAY);
   g.fillRect(0, 0, getWidth(), getHeight());
   
   if(p1.getVictory()){
     g.drawImage(Assets.P1_VICTORY, offsetX, offsetY, null);
   }
   else if(p2.getVictory()){
     g.drawImage(Assets.P2_VICTORY, offsetX, offsetY, null);
   }
   else{
     g.setColor(Color.BLACK);
     g.fillRect(offsetX, offsetY, 512, 768);
     
     g.drawImage(Assets.PLAYER_SPRITES[p1.getFrame()], p1.getRect().x + offsetX, p1.getRect().y + offsetY, null);
     for(Block b : p1Blocks){
       g.drawImage(Assets.BLOCK, b.getRect().x + offsetX, b.getRect().y + offsetY, null);
     }
     for(Cannon c : p1Cannons){
       if(c.getDirection() == Cannon.UP){
         g.drawImage(Assets.CANNON_UP, c.getRect().x + offsetX, c.getRect().y + offsetY, null);
       }
       else if(c.getDirection() == Cannon.DOWN){
         g.drawImage(Assets.CANNON_DOWN, c.getRect().x + offsetX, c.getRect().y + offsetY, null);
       }
       else if(c.getDirection() == Cannon.RIGHT){
         g.drawImage(Assets.CANNON_RIGHT, c.getRect().x + offsetX, c.getRect().y + offsetY, null);
       }
       else if(c.getDirection() == Cannon.LEFT){
         g.drawImage(Assets.CANNON_LEFT, c.getRect().x + offsetX, c.getRect().y + offsetY, null);
       }
     }
     for(Projectile p : p1Projectiles){
       g.drawImage(Assets.SHOT, p.getRect().x + offsetX, p.getRect().y + offsetY, null);
     }
     for(Spike s : p1Spikes){
       g.drawImage(Assets.SPIKES, s.getRect().x + offsetX, s.getRect().y + offsetY, null);
     }
     for(int y = 0; y < 12; y ++){
       for(int x = 0; x < 16; x ++){
         if(Levels.levels[p1.getLevel()][y][x] == Levels.LEVEL_END){
           g.drawImage(Assets.FLAG, x * 32 + offsetX, y * 32 + offsetY, null);
         }
       }
     }
     
     g.drawImage(Assets.PLAYER_SPRITES[p2.getFrame()], p2.getRect().x + offsetX, p2.getRect().y + 384 + offsetY, null);
     for(Block b : p2Blocks){
       g.drawImage(Assets.BLOCK, b.getRect().x + offsetX, b.getRect().y + 384 + offsetY, null);
     }
     for(Cannon c : p2Cannons){
       if(c.getDirection() == Cannon.UP){
         g.drawImage(Assets.CANNON_UP, c.getRect().x + offsetX, c.getRect().y + 384 + offsetY, null);
       }
       else if(c.getDirection() == Cannon.DOWN){
         g.drawImage(Assets.CANNON_DOWN, c.getRect().x + offsetX, c.getRect().y + 384 + offsetY, null);
       }
       else if(c.getDirection() == Cannon.RIGHT){
         g.drawImage(Assets.CANNON_RIGHT, c.getRect().x + offsetX, c.getRect().y + 384 + offsetY, null);
       }
       else if(c.getDirection() == Cannon.LEFT){
         g.drawImage(Assets.CANNON_LEFT, c.getRect().x + offsetX, c.getRect().y + 384 + offsetY, null);
       }
     }
     for(Projectile p : p2Projectiles){
       g.drawImage(Assets.SHOT, p.getRect().x + offsetX, p.getRect().y + 384 + offsetY, null);
     }
     for(Spike s : p2Spikes){
       g.drawImage(Assets.SPIKES, s.getRect().x + offsetX, s.getRect().y + 384 + offsetY, null);
     }
     for(int y = 0; y < 12; y ++){
       for(int x = 0; x < 16; x ++){
         if(Levels.levels[p2.getLevel()][y][x] == Levels.LEVEL_END){
           g.drawImage(Assets.FLAG, x * 32 + offsetX, y * 32 + 384 + offsetY, null);
         }
       }
     }
   }
 }
    
 public static void main(String[] arguments) {
   new Game0();
 }

}