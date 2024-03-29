package Game.Entities.Dynamic;

import Main.GameSetUp;
import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.Entities.Static.Apple;
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

	public static int moveCounter;   //static added
	public boolean ate = false;
	public static boolean rottedApple = false;
	public static int steps = 0;
	public static boolean colorActive = false;


	public static int score= 0;
	public static int highScore = 0;

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

	public static boolean isRottedApple() {
		return rottedApple;
	}

	public static void setRottedApple(boolean rottedApple) {
		Player.rottedApple = rottedApple;
	}

	public void tick(){
		moveCounter++;


		if(steps > 60) {
			rottedApple = true;
			colorActive = true;
		}
		if(ate) {
			steps = 0;
			ate = false;
			colorActive = false;

		}

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

		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ADD)){  //speed up
			if(speed > 1) {
				speed-=2;
			}

		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)){  //slow down
			if(speed < 17) {
				speed+=2;
			}

		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SUBTRACT)){  //slow down
			if(speed < 17) {
				speed+=2;
			}

		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			State.setState(GameSetUp.pauseState);


		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_H)){       // Hell Difficultyspeed up
			speed = 1;

		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_G)){       // Hell Difficultyspeed up
			speed = 9;

		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)){       // Restart Button
			handler.getGame().reStart();
			State.setState(handler.getGame().gameState);

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

			if((handler.getWorld().body.get(i).x == xCoord) && (handler.getWorld().body.get(i).y == yCoord)) {
				kill();
			}    




		}

		switch (direction){
		case "Left":
			if(xCoord==0){
				xCoord = handler.getWorld().GridWidthHeightPixelCount-1;  // 
				steps++;

			}else{
				xCoord--;
				steps++;

			}
			break;
		case "Right":
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				xCoord = 0;
				steps++;
			}else{
				xCoord++;
				steps++;
			}
			break;
		case "Up":
			if(yCoord==0){
				yCoord =handler.getWorld().GridWidthHeightPixelCount-1;
				steps++;

			}else{
				yCoord--;
				steps++;

			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				yCoord = 0;
				steps++;

			}else{
				yCoord++;
				steps++;

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

				if(colorActive == true) {
					g.setColor(Color.YELLOW);
				} else g.setColor(Color.GREEN);

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
			ate = true;

			if(Apple.good()== true) {


				score += (int) Math.sqrt(2*(score +1));

				if(score > highScore) {
					highScore= score;
				}
			}
			else if(Apple.good() == false) {
				score -= (int) Math.sqrt(2*(score +1));
				rottedApple = false;
				if(score < 0) {
					score = 0;
				}



				//handler.getWorld().body.removeLast();
				lenght--;
			}

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
