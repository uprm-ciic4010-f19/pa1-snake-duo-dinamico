package Game.Entities.Static;

import java.awt.Color;
import java.util.Random;

import Game.Entities.Dynamic.Player;
import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private Handler handler;

    private final int MAXSTEPS = 200;
    private static boolean rottened = false;
    private Color color = Color.white;
    
    public int xCoord;
    public int yCoord;

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
    }
  
    public void rot(int steps) {
    	if(steps % MAXSTEPS == 0 && steps != 0) {
    		rotten();
    	}
    }
    
    public void rotten() {
    	rottened = true;
    	color = Color.yellow;
    }
    
    public static boolean good() {
//    	 Random gen = new Random();                                   
//         int norm = 0;
//         int randApple = gen.nextInt(17); 
        	
        	if(Player.isRottedApple()) {
        		
        		
        		return false;
        		
        	} else 
        		
        		return true;
        	
        	
    	
    	
    	
    	//return !rottened;
    }
    
}
