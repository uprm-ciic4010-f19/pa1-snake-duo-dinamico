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
            
            g.setColor(Color.GREEN);
            g.drawString("Score:"+Player.score, 17, 640);
         
           
            g.setColor(Color.GREEN);
            g.drawString("ESC: Pause Game", 17, 670);
            
            g.setColor(Color.GREEN);
            g.drawString("W,A,S,D: Move Cobra", 17, 700);
           
            g.setColor(Color.GREEN);
            g.drawString("-, +: Speed", 17, 730);
         
            g.setColor(Color.GREEN);
            g.drawString("N: Grow Snake", 17, 760);
           
            g.setColor(Color.GREEN);
            g.drawString("H: Hell Difficulty!", 17, 790);
            
            g.setColor(Color.GREEN);
            g.drawString("G: Normal Difficulty", 200, 640);
        }



    }

}
