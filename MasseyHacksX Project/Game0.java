import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game0 extends JFrame{
 GamePanel game;
  
    public Game0() {
      super("Basic Game Setup");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      game= new GamePanel();
      add(game);
      pack();
      setVisible(true);
    }    
    public static void main(String[] arguments) {
      new Game0();  
    }
}
