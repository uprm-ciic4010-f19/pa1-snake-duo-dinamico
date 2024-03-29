package Game.GameStates;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

import Game.Entities.Dynamic.Player;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameOverState extends State {

    private int count = 0;
    private UIManager uiManager;

    public GameOverState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);


       
        uiManager.addObjects(new UIImageButton(370, 630, 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));





    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;

            State.setState(handler.getGame().gameState);
        }


    }

    @Override
    public void render(Graphics g) {
    	int fontSize=40;
    	g.setFont(new Font ("TimesRoman",Font.PLAIN,fontSize));
    	
        g.drawImage(Images.gameOver,0,0,handler.getWidth(),handler.getHeight(),null);
        uiManager.Render(g);
        
       
        g.setColor(Color.WHITE);
        g.drawString("Your High Score is:"+Player.highScore, 140, (handler.getHeight()/5));
        
        

    }
}
