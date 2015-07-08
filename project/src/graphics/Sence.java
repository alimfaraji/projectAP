package graphics;
import bozorg.common.exceptions.BozorgExceptionBase;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import source.*;
import javafx.*;
import javafx.application.*;
import javax.swing.text.html.ImageView;
import java.util.Timer;

/**
 * Created by khahsayar on 7/5/2015.
 */
public class Sence {
  static boolean control1=true;
    static boolean space=true;
    static GameEngine gameEngine=new GameEngine();
    static Group group;
    static Timer timer;
    //static Group group;
    public static boolean getControl(){
        return control1;
    }
    public static void setControl(boolean bool){
        control1=bool;
    }


    public static void aClick(final Scene scene){
          System.out.println("ggg");
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.A && control1==true) {
                    // System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaclick");
                    try {
                        control1 = false;
                        Player.getAllPlayers().get(0).move(3);
                        Sence.aClick(scene);
                        System.out.println("DOROST");
                    } catch (BozorgExceptionBase bozorgExceptionBase) {
                        System.out.println(Player.getAllPlayers().get(0).movePercent());
                        System.out.println(Player.getAllPlayers().get(0).getSpeed() * (GameEngine.getStaticTime() - Player.getAllPlayers().get(0).getStartTimeForMoving()));
                        System.out.println(bozorgExceptionBase.getClass().getSimpleName());
                        Sence.aClick(scene);
                    }
                }

                if (keyEvent.getCode() == KeyCode.D && control1==true) {
                    // System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaclick");
                    try {
                        control1 = false;
                        Player.getAllPlayers().get(0).move(1);
                        Sence.aClick(scene);
                        System.out.println("DOROST");
                    } catch (BozorgExceptionBase bozorgExceptionBase) {
                        System.out.println(Player.getAllPlayers().get(0).movePercent());
                        System.out.println(Player.getAllPlayers().get(0).getSpeed() * (GameEngine.getStaticTime() - Player.getAllPlayers().get(0).getStartTimeForMoving()));
                        System.out.println(bozorgExceptionBase.getClass().getSimpleName());
                        Sence.aClick(scene);
                    }
                }

                if (keyEvent.getCode() == KeyCode.S && control1==true) {
                    // System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaclick");
                    try {
                        control1 = false;
                        Player.getAllPlayers().get(0).move(2);
                        Sence.aClick(scene);
                        System.out.println("DOROST");
                    } catch (BozorgExceptionBase bozorgExceptionBase) {
                        System.out.println(Player.getAllPlayers().get(0).movePercent());
                        System.out.println(Player.getAllPlayers().get(0).getSpeed() * (GameEngine.getStaticTime() - Player.getAllPlayers().get(0).getStartTimeForMoving()));
                        System.out.println(bozorgExceptionBase.getClass().getSimpleName());
                        Sence.aClick(scene);
                    }
                }

                if (keyEvent.getCode() == KeyCode.W && control1==true) {
                    // System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaclick");
                    try {
                        control1 = false;
                        Player.getAllPlayers().get(0).move(0);
                        Sence.aClick(scene);
                        System.out.println("DOROST");
                    } catch (BozorgExceptionBase bozorgExceptionBase) {
                        System.out.println(Player.getAllPlayers().get(0).movePercent());
                        System.out.println(Player.getAllPlayers().get(0).getSpeed() * (GameEngine.getStaticTime() - Player.getAllPlayers().get(0).getStartTimeForMoving()));
                        System.out.println(bozorgExceptionBase.getClass().getSimpleName());
                        Sence.aClick(scene);
                    }
                }


                if (keyEvent.getCode() == KeyCode.SPACE && space==true ) {
                    // System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaclick");
                    control1 = false;
                    Timer timer1=Draw.attack1(group,true,1.0,1.0);
                    timer=timer1;
                    space=false;
                }
            }
        });
    }

    public static void bClick(Scene scene){
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    timer.cancel();
                    javafx.scene.image.ImageView imageView1 = Draw.imageView1;
                    group.getChildren().remove(imageView1);
                    double x = imageView1.getX();
                    double y = imageView1.getY();
                    Image image1 = new Image(new Draw().getClass().getResourceAsStream("c1-taajob.png"));
                    imageView1 = new javafx.scene.image.ImageView(image1);
                    imageView1.setX(x);
                    imageView1.setY(y);
                    imageView1.setFitHeight(Draw.tool / 2);
                    imageView1.setFitWidth(Draw.tool / 2);
                    group.getChildren().add(imageView1);
                    Draw.imageView1=imageView1;
                    space = true;
                }
            }
        });


    }

    public static void setGroup(Group group1){
        group=group1;
    }
}
