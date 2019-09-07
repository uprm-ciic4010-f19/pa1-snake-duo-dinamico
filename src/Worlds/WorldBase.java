package Worlds;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.Tail;
import Game.Entities.Static.Apple;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.util.LinkedList;


/**
 * Created by AlexVR on 7/2/2018.
 */
public abstract class WorldBase {

    //How many pixels are from left to right
    //How many pixels are from top to bottom
    //Must be equal
    public int GridWidthHeightPixelCount;

    //automatically calculated, depends on previous input.
    //The size of each box, the size of each box will be GridPixelsize x GridPixelsize.
    public int GridPixelsize; ///changed

    public Player player;

    protected Handler handler;


    public Boolean appleOnBoard;
    protected Apple apple;
    public Boolean[][] appleLocation;


    public Boolean[][] playerLocation;

    public LinkedList<Tail> body = new LinkedList<>();


    public WorldBase(Handler handler){
        this.handler = handler;

        appleOnBoard = false;


    }
    public void tick(){



    }

    public void render(Graphics g){

    	int fontSize=16;
    	g.setFont(new Font ("TimesRoman",Font.PLAIN,fontSize));
    	
    	
        for (int i = 0; i <= 600; i = i + GridPixelsize) {

        	
            g.setColor(Color.white);
            g.drawLine(0, i, handler.getWidth() , i);
            g.drawLine(i,0,i,handler.getHeight() -200 );
            
            g.drawString("Score:"+Player.score, 17, 640);
            g.setColor(Color.BLACK);
            g.fillRoundRect(473, 624, 103, 21, 12, 21);
            g.setColor(Color.GREEN);
           
            
            

        }



    }

}
