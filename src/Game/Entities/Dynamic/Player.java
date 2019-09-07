package Game.Entities.Dynamic;

import Main.GameSetUp;
import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.GameStates.State;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

    public int lenght;
    public boolean justAte;
    private Handler handler;

    public int xCoord;
    public int yCoord;
    public int speed = 9;
    
    public int moveCounter;
    
    public static int score= 0;

    public String direction;//is your first name one?

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        direction= "Right";
        justAte = false;
        lenght= 1;

    }

    public void tick(){
        moveCounter++;
        if(moveCounter>= speed) {
            checkCollisionAndMove();
            moveCounter=0;
        }
        //game controls
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
            if (direction != "Down") 
            	direction = "Up";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
        	if (direction != "Up")
            	direction = "Down";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
        	if (direction != "Right")
            	direction = "Left";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)){
        	if (direction != "Left")
            	direction = "Right";
        }
        //Different controller option 
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)){
            if (direction != "Down")
            	direction = "Up";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)){
            if (direction != "Up")
            	direction = "Down";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)){
            if (direction != "Right")
            	direction = "Left";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)){
            if (direction != "Left")
            	direction="Right";
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){   //adds tail
        	
            Eat(false);
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)){ //speed up
        	if(speed > 1) {
        		speed-=2;
        	}
            ;
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ADD)){  //speed up
        	if(speed > 1) {
        		speed-=2;
        	}
            ;
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)){  //slow down
        	if(speed < 17) {
        		speed+=2;
        	}
            ;
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SUBTRACT)){  //slow down
        	if(speed < 17) {
        		speed+=2;
        	}
            ;
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
        	State.setState(GameSetUp.pauseState);
        	
        	
        }


    }

    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        
        //kills snake if it collides with itself
        for(int i= 0; i< handler.getWorld().body.size(); i++) {
        	if((handler.getWorld().body.get(i).x == xCoord) && (handler.getWorld().body.get(i).y == yCoord)) {
        		kill();
        	}        	
        	
        }
        
        switch (direction){
            case "Left":
                if(xCoord==0){
                    xCoord = handler.getWorld().GridWidthHeightPixelCount-1;  // 
                }else{
                    xCoord--;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    xCoord = 0;
                }else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                	yCoord =handler.getWorld().GridWidthHeightPixelCount-1;
                }else{
                    yCoord--;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    yCoord = 0;
                }else{
                    yCoord++;
                }
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;


        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            Eat(true);
        }

        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }

    }

    public void render(Graphics g,Boolean[][] playeLocation){
        Random r = new Random();
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.setColor(Color.GREEN);

                if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                }

            }
        }


    }

    public void Eat(boolean eatsApple){
    	//if true, add to tail and adds apple to map
    	if(eatsApple) {
    		handler.getWorld().appleLocation[xCoord][yCoord]= false;
    		handler.getWorld().appleOnBoard = false;
    		
    	    score += (int) Math.sqrt(2*(score +1));
    		
    	}
    	
    	
    	//if false, does not add apple to map
    	
        lenght++;
        Tail tail= null;
     
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        } System.out.println("Tu biscochito");
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
    }

    public void kill(){
        lenght = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

                handler.getWorld().playerLocation[i][j]=false;

            }
        }  State.setState(GameSetUp.gameOverState);
        	score= 0;

    }

    public boolean isJustAte() {
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }
}
