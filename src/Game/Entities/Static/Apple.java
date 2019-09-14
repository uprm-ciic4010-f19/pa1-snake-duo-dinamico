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

	public int xCoord;
	public int yCoord;

	public Apple(Handler handler,int x, int y){
		this.handler=handler;
		this.xCoord=x;
		this.yCoord=y;
	}




	public static boolean good() {


		if(Player.isRottedApple()) {


			return false;

		} else 

			return true;





	}

}
