package graphics;

import bozorg.judge.Judge;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import source.*;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by khahsayar on 7/5/2015.
 */

public class Draw {
    static Player player;
    static double x;
    static double y;
    static double width=600;
    static double hight=600;
    static  int row=3;
    static int col=3;
    static double tool;
    static Image imagezamin;
    static Image image1=new Image(new Draw().getClass().getResourceAsStream("c1-taajob.png"));
    static Image image2=new Image(new Draw().getClass().getResourceAsStream("c2-a1-r.png"));
    static Image image3;
    static Image image4;

    static int counter1=0;
    static int counter2=0;
    static int counter3=0;
    static int counter4=0;

    static ImageView imageView1;
    static ImageView imageView2;
    static ImageView imageView3;
    static ImageView imageView4;

    static Timer timer1;
    static Timer timer2;
    static Timer timer3;
    static Timer timer4;


    static {
        tool = (double) hight / (double) row;

    }


    public static void drawCell(Cell cell,Group group) {

         x =  ((cell.getCol()) * tool);
         y =  ((cell.getRow()) * tool);
        imagezamin = new Image(new Draw().getClass().getResourceAsStream("dessert1.jpg"));
        ImageView imageView = new ImageView(imagezamin);
        group.getChildren().add(imageView);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(tool);
        imageView.setFitHeight(tool);
        Line line1 = new Line();
        Line line2 = new Line();
        Line line3 = new Line();
        Line line4 = new Line();
        line1.setStartX(x + tool);
        line1.setStartY(y + tool);
        line1.setEndX(x + tool);
        line1.setEndY(y);
        line1.setStrokeWidth(3);
        line1.setStroke(Color.SADDLEBROWN);

        line2.setStartX(x + tool);
        line2.setStartY(y);
        line2.setEndX(x);
        line2.setEndY(y);
        line2.setStrokeWidth(3);
        line2.setStroke(Color.SADDLEBROWN);

        line3.setStartX(x);
        line3.setStartY(y);
        line3.setEndX(x);
        line3.setEndY(y + tool);
        line3.setStrokeWidth(3);
        line3.setStroke(Color.SADDLEBROWN);

        line4.setStartX(x);
        line4.setStartY(y + tool);
        line4.setEndX(x + tool);
        line4.setEndY(y + tool);
        line4.setStrokeWidth(3);
        line4.setStroke(Color.SADDLEBROWN);

        if (!cell.rightIsBlocked())
            group.getChildren().add(line1);
        if (!cell.upIsBlocked())
            group.getChildren().add(line2);
        if (!cell.leftIsBlocked())
            group.getChildren().add(line3);
        if (!cell.downIsBlocked())
            group.getChildren().add(line4);
        // System.out.println(cell.getAllPlayers().size());
        if (cell.getAllPlayers().size() != 0 && cell.getAllPlayers() != null) {
//System.out.println("salam");
            player = cell.getAllPlayers().get(0);
            if (player.getName() == Judge.SAMAN) {
                System.out.println("adam");
//                Image image1 = new Image(new Draw().getClass().getResourceAsStream("c1-taajob.png"));
//                ImageView imageView1 = new ImageView(image1);
//                 imageView1=new ImageView(image1);
//                imageView1.setFitHeight(tool / 2);
//                imageView1.setFitWidth(tool / 2);
//                imageView1.setX(x);
//                imageView1.setY(y);
//                group.getChildren().add(imageView1);
                attack1(group,true,x,y);
            }

            if (player.getName() == 1) {
               System.out.println("player2");
                attack2(group, true, x, y);
            }
            if(player.getName()==2)
                attack3(group,true,x,y);
            if(player.getName()==3)
                attack4(group,true,x,y);
        }



    }

     public static void setSize(double width1,double hight1,int col1,int row1){
        row=row1;
        col=col1;
        width=width1;
        hight=hight1;
    }

    public static Timer attack1(final Group group,final boolean isright,final double x,final double y){
        timer1=new Timer();

        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        if((counter1%4)==0){
                            if(isright){
                                image1=new Image(this.getClass().getResourceAsStream("c1-a1-r.png"));
                                group.getChildren().remove(imageView1);
                                imageView1=new ImageView(image1);
                                group.getChildren().add(imageView1);
                                counter1++;
                            }
                             else{
                                image1=new Image(this.getClass().getResourceAsStream("c1-a1-l.png"));
                                group.getChildren().remove(imageView1);
                                imageView1=new ImageView(image1);
                                group.getChildren().add(imageView1);
                                counter1++;
                            }
                            imageView1.setFitHeight(tool / 2);
                            imageView1.setFitWidth(tool / 2);
                            imageView1.setX(x);
                            imageView1.setY(y);
                        }
                        else if((counter1%4)==1){
                            if(isright){
                                image1=new Image(this.getClass().getResourceAsStream("c1-a2-r.png"));
                                group.getChildren().remove(imageView1);
                                imageView1=new ImageView(image1);
                                group.getChildren().add(imageView1);
                                counter1++;
                            }
                            else{
                                image1=new Image(this.getClass().getResourceAsStream("c1-a2-l.png"));
                                group.getChildren().remove(imageView1);
                                imageView1=new ImageView(image1);
                                group.getChildren().add(imageView1);
                                counter1++;
                            }
                            imageView1.setFitHeight(tool / 2);
                            imageView1.setFitWidth(tool / 2);
                            imageView1.setX(x+20);
                            imageView1.setY(y);
                        }
                        else if((counter1%4)==2){
                            if(isright){
                                image1=new Image(this.getClass().getResourceAsStream("c1-a4-r.png"));
                                group.getChildren().remove(imageView1);
                                imageView1=new ImageView(image1);
                                group.getChildren().add(imageView1);
                                counter1++;
                            }
                            else{
                                image1=new Image(this.getClass().getResourceAsStream("c1-a4-l.png"));
                                group.getChildren().remove(imageView1);
                                imageView1=new ImageView(image1);
                                group.getChildren().add(imageView1);
                                counter1++;
                            }
                            imageView1.setFitHeight(tool / 2);
                            imageView1.setFitWidth(tool / 2);
                            imageView1.setX(x+10);
                            imageView1.setY(y+10);
                        }
                        else if((counter1%4)==3){
                            if(isright){
                                image1=new Image(this.getClass().getResourceAsStream("c1-a4-r.png"));
                                group.getChildren().remove(imageView1);
                                imageView1=new ImageView(image1);
                                group.getChildren().add(imageView1);
                                counter1++;
                            }
                            else{
                                image1=new Image(this.getClass().getResourceAsStream("c1-a4-l.png"));
                                group.getChildren().remove(imageView1);
                                imageView1=new ImageView(image1);
                                group.getChildren().add(imageView1);
                                counter1++;
                            }

                            imageView1.setFitHeight(tool / 2);
                            imageView1.setFitWidth(tool / 2);
                            imageView1.setX(x+10);
                            imageView1.setY(y+10);
                        }


                    }
                });
            }
        },1000,300);
    return timer1;
    }






    public static Timer attack2(final Group group,final boolean isright,final double x,final double y){
        timer2=new Timer();
        //System.out.println("vorood1");
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        if((counter2%2)==0){
                            //System.out.println("vorood2");
                            if(isright){
                                image2=new Image(this.getClass().getResourceAsStream("c2-a1-r.png"));
                                group.getChildren().remove(imageView2);
                                imageView2=new ImageView(image2);
                                group.getChildren().add(imageView2);
                                counter2++;
                            }
                            else{
                                image2=new Image(this.getClass().getResourceAsStream("c2-a1-l.png"));
                                group.getChildren().remove(imageView2);
                                imageView2=new ImageView(image2);
                                group.getChildren().add(imageView2);
                                counter2++;
                            }
                        }
                        else if((counter2%2)==1){
                            if(isright){
                                image2=new Image(this.getClass().getResourceAsStream("c2-a2-r.png"));
                                group.getChildren().remove(imageView2);
                                imageView2=new ImageView(image2);
                                group.getChildren().add(imageView2);
                                counter2++;
                            }
                            else{
                                image2=new Image(this.getClass().getResourceAsStream("c2-a2-l.png"));
                                group.getChildren().remove(imageView2);
                                imageView2=new ImageView(image2);
                                group.getChildren().add(imageView2);
                                counter2++;
                            }
                        }

                        imageView2.setFitHeight(tool / 2);
                        imageView2.setFitWidth(tool / 2);
                        imageView2.setX(x+tool/2);
                        imageView2.setY(y);

                    }
                });
            }
        },1000,450);
        return timer2;
    }








    public static Timer attack3(final Group group,final boolean isright,final double x,final double y){
        timer3=new Timer();

        timer3.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        if((counter3%2)==0){
                            if(isright){
                                image3=new Image(this.getClass().getResourceAsStream("c3-a1-r.png"));
                                group.getChildren().remove(imageView3);
                                imageView3=new ImageView(image3);
                                group.getChildren().add(imageView3);
                                counter3++;
                            }
                            else{
                                image3=new Image(this.getClass().getResourceAsStream("c3-a1-l.png"));
                                group.getChildren().remove(imageView3);
                                imageView3=new ImageView(image3);
                                group.getChildren().add(imageView3);
                                counter3++;
                            }
                            imageView3.setFitHeight(tool / 2);
                            imageView3.setFitWidth(tool / 2);
                            imageView3.setX(x);
                            imageView3.setY(y+tool/2);
                        }
                        else if((counter3%2)==1){
                            if(isright){
                                image3=new Image(this.getClass().getResourceAsStream("c3-a2-r.png"));
                                group.getChildren().remove(imageView3);
                                imageView3=new ImageView(image3);
                                group.getChildren().add(imageView3);
                                counter3++;
                            }
                            else{
                                image3=new Image(this.getClass().getResourceAsStream("c3-a2-l.png"));
                                group.getChildren().remove(imageView3);
                                imageView3=new ImageView(image3);
                                group.getChildren().add(imageView3);
                                counter3++;

                            }
                            imageView3.setFitHeight(tool / 2);
                            imageView3.setFitWidth(tool / 2+60);
                            imageView3.setX(x-20);
                            imageView3.setY(y+tool/2-10);
                        }



                    }
                });
            }
        },1000,500);
        return timer3;
    }






    public static Timer attack4(final Group group,final boolean isright,final double x,final double y){
        timer4=new Timer();

        timer4.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        if((counter4%2)==0){
                            if(isright){
                                image4=new Image(this.getClass().getResourceAsStream("c4-a1-r.png"));
                                group.getChildren().remove(imageView4);
                                imageView4=new ImageView(image4);
                                group.getChildren().add(imageView4);
                                counter4++;
                            }
                            else{
                                image4=new Image(this.getClass().getResourceAsStream("c4-a1-l.png"));
                                group.getChildren().remove(imageView4);
                                imageView4=new ImageView(image4);
                                group.getChildren().add(imageView4);
                                counter4++;
                            }

                            imageView4.setFitHeight(tool / 2);
                            imageView4.setFitWidth(tool / 2);
                            imageView4.setX(x+tool/2-20);
                            imageView4.setY(y+tool/2);
                        }
                        else if((counter4%2)==1){
                            if(isright){
                                image4=new Image(this.getClass().getResourceAsStream("c4-a2-r.png"));
                                group.getChildren().remove(imageView4);
                                imageView4=new ImageView(image4);
                                group.getChildren().add(imageView4);
                                counter4++;
                            }
                            else{
                                image4=new Image(this.getClass().getResourceAsStream("c4-a2-l.png"));
                                group.getChildren().remove(imageView4);
                                imageView4=new ImageView(image4);
                                group.getChildren().add(imageView4);
                                counter4++;
                            }

                            imageView4.setFitHeight(tool / 2);
                            imageView4.setFitWidth(tool / 2);
                            imageView4.setX(x+tool/2);
                            imageView4.setY(y+tool/2-20);
                        }



                    }
                });
            }
        },1000,500);
        return timer4;
    }









}