package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import graphics.*;
import source.*;
import bozorg.judge.*;
/**
 * Created by khahsayar on 7/5/2015.
 */
import source.*;

import java.util.Timer;
import java.util.TimerTask;



public class JavafxClass extends Application {
    static Stage stage1;
    static Group group;
    Scene scene;
    Judge judge;
    static int row=3;
    static int col=3;
    GameEngine gameEngine;
    public void start(Stage stage) throws Exception {
        stage1=stage;
       // System.out.println("first"+row);
        setGameEngine(new GameEngine());
        Draw.setSize(stage.getWidth(),stage.getHeight(),col,row);
       // setsize();
        stage.setWidth(617);
        stage.setHeight(640);
        group=new Group();
        scene=new Scene(group);
        stage.setScene(scene);
        stage.show();
        Sence.setGroup(group);

//        Cell cell1=new Cell(0,1,3,3);
//        Cell cell2=new Cell(0,1,1,1);
//        Cell cell3=new Cell(0,1,1,3);
//        Cell cell4=new Cell(0,1,3,1);
//        Cell cell5=new Cell(0,1,2,1);
//        Cell cell6=new Cell(0,1,1,2);
//        Cell cell7=new Cell(0,1,2,2);
//        Cell cell8=new Cell(0,1,3,2);
//        Cell cell9=new Cell(0,1,2,3);

       // Player player=new Player(1,0,0,0,Cell.getCell(1,1));
        //System.out.println(cell2.getAllPlayers().get(0).getName());
        //Draw.drawCell(Cell.getCell(2,2),group);
//        Draw.drawCell(cell2,group);
//        Draw.drawCell(cell3,group);
//        Draw.drawCell(cell4,group);
//        Draw.drawCell(cell5,group);
//        Draw.drawCell(cell6,group);
//        Draw.drawCell(cell7,group);
//        Draw.drawCell(cell8,group);
//        Draw.drawCell(cell9,group);

        int players[]={judge.SAMAN,1,2,3};
      int celltype[][]={{0,0,3},{0,0,3},{3,0,3}};
        int walltype[][]={{0,0,0},{0,0,0},{0,0,0}};
        gameEngine.loadMap(celltype,walltype,players);

       // System.out.println(row);

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
               // System.out.println(Cell.getCell(i,j).getCol());
                Draw.drawCell(Cell.getCell(i, j), group);
            }
        }
    //Cell.getCell(2,2);
      //  System.out.println("heh");
    //System.out.println(Cell.getCell(1,1).getCol());

        Khashi khashi=new Khashi();
        khashi.setRow(row);
        khashi.setCol(col);
        khashi.setStage(stage1);
        khashi.setGroup(group);
        khashi.setEngine(gameEngine);
        khashi.start();
        ListenThread listenThread = new ListenThread();
        listenThread.setScene(scene)    ;
        listenThread.setGroup(group);
        listenThread.start();
        ControlThread controlThread=new ControlThread();
        controlThread.setPlayer(Player.getPlayerFromNumber(0));
        controlThread.start();
        System.out.println("afteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

    }

    public void setJudge(Judge judge){
        this.judge=judge;
    }
    public void setGameEngine(GameEngine gameEngine){
        this.gameEngine=gameEngine;
    }


  //  public static void main(final String[] args){

    //   Khashi khashi=new Khashi();
      //  khashi.setRow(row);
       // khashi.setCol(col);
     //   khashi.setStage(stage1);
     //   khashi.setGroup(group);

//        launch(args);
 //       khashi.start();



   // }


     public static void main(String[] args){
        launch(args);
     }
}

class Khashi extends Thread{
   static Stage stage;
    public static Integer row1;
    public static Integer col1;
    static Group group;
   static GameEngine gameEngine1;
    public void run(){

        System.out.println("salama");
        Sence.aClick(stage.getScene());
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //System.out.println("r");
                     //  if(Sence.getControl()) {
                           for (int i = 0; i < row1; i++) {
                               for (int j = 0; j < col1; j++) {
                                   //System.out.println("lll");
                                   if (Cell.getCell(i, j).getAllPlayers() != null && Cell.getCell(i, j).getAllPlayers().size() != 0) {
                                       if (Cell.getCell(i, j).getAllPlayers().get(0).getName() == Judge.SAMAN)
                                           Draw.move1(group, Cell.getCell(i, j).getAllPlayers().get(0));
                                   }

                               }
                           }
                  //     }
                        if(gameEngine1!=null)
                        gameEngine1.next50milis();

                    }
                });
            }
        },1000,50);
    }
  public void setRow(int row){
      row1=row;
  }
    public void setCol(int col){
        col1=col;
    }
    public void setStage(Stage stage1){
     stage=stage1;
    }
    public void setGroup(Group group1){
        group=group1;
    }

     public void setEngine(GameEngine gameEngine){
          gameEngine1=gameEngine;
     }


}


class ListenThread extends Thread {
    private Scene scene1;
    private Group group;
    public void run() {

        Sence.aClick(scene1);
       Sence.setGroup(group);
        Sence.bClick(scene1);
        System.out.println("finishhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
    }

    public void setScene(Scene scene) {
        scene1=scene;
    }

    public void setGroup(Group group1){
        group=group1;
    }
}


class ControlThread extends Thread{
    Player player1;
    public void run(){
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(!player1.isMoving())
                            Sence.setControl(true);


                    }
                });
            }
        },10,40);

    }
    public void setPlayer(Player player){
        player1=player;
    }
}