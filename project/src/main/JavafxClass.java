package main;

import javafx.application.Application;
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
public class JavafxClass extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

    Group group;
    Scene scene;
    Judge judge;
    int row=3;
    int col=3;
    GameEngine gameEngine;
    public void start(Stage stage) throws Exception {
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
      int celltype[][]={{0,3,0},{0,0,3},{3,0,3}};
        int walltype[][]={{3,3,3},{3,3,3},{3,3,3}};
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

    }

    public void setJudge(Judge judge){
        this.judge=judge;
    }
    public void setGameEngine(GameEngine gameEngine){
        this.gameEngine=gameEngine;
    }


}