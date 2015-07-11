package graphics;

import bozorg.judge.Judge;
import bozorg.judge.JudgeAbstract;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import main.JavafxClass;
import source.*;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by khahsayar on 7/5/2015.
 */

public class Draw {
	static int aghaz = 0;
	static Group group;
	// static boolean black = false;
	public static final double sabet = 50;
	public static boolean dark1 = false;
	public static boolean dark2 = false;
	public static boolean dark3 = false;
	public static boolean dark4 = false;

	public static boolean prison1;
	public static boolean prison2;
	public static boolean prison3;
	public static boolean prison4;

	static GameEngine gameEngine;
	static Player player;
	static Player player1;
	static Player player2;
	static Player player3;
	static Player player4;
	static double x;
	static double y;
	static double width = 740;
	static double hight = 630;
	static public int row = 10;
	static public int col = 10;
	public static double length;
	static Image imagezamin;
	static Image image1;
	static Image image2;
	static Image image3;
	static Image image4;

	static int counter1 = 0;
	static int counter2 = 0;
	static int counter3 = 0;
	static int counter4 = 0;

	public static ImageView imageView1;
	public static ImageView imageView2;
	public static ImageView imageView3;
	public static ImageView imageView4;

	static Timer timer1;
	static Timer timer2;
	static Timer timer3;
	static Timer timer4;

	static Image imageprison;

	static ImageView imageViewprison;
	static ImageView imageViewprison2;
	static ImageView imageViewprison3;
	static ImageView imageViewprison4;

	static ImageView imageViewdark;
	static Image darkimage;
	static Image imageganj;
	static Image imagefan1;
	static Image imagefan2;
	static Image imagefan3;
	static Image imagefan4;

	static Image JJ;

	static Image imagejoon;
	static Image imagehealth;
	static Image imageradar;
	static Image imagespeed;
	static Image imagejump;

	static Joon joon1;
	static Joon joon2;
	static Joon joon3;
	static Joon joon4;

	static {
		length = (double) hight / (double) row;
		darkimage = new Image((new Draw().getClass().getResourceAsStream("black.png")));
		imagezamin = new Image(new Draw().getClass().getResourceAsStream("dessert1.png"));
		imageganj = new Image(new Draw().getClass().getResourceAsStream("bonus.png"));
		imagefan1 = new Image(new Draw().getClass().getResourceAsStream("fan1.png"));
		imagefan2 = new Image(new Draw().getClass().getResourceAsStream("fan2.png"));
		imagefan3 = new Image(new Draw().getClass().getResourceAsStream("fan3.png"));
		imagefan4 = new Image(new Draw().getClass().getResourceAsStream("fan4.png"));
		imageprison = new Image(new Draw().getClass().getResourceAsStream("prisonasli.png"));
		JJ = new Image(new Draw().getClass().getResourceAsStream("JJ.png"));


		player1 = Player.getPlayerFromNumber(0);
		player2 = Player.getPlayerFromNumber(1);
		player3 = Player.getPlayerFromNumber(2);
		player4 = Player.getPlayerFromNumber(3);
		
		

		imagejoon = new Image(new Draw().getClass().getResourceAsStream("joonasli.png"));
		imageradar = new Image(new Draw().getClass().getResourceAsStream("radarasli.png"));
		imagehealth = new Image(new Draw().getClass().getResourceAsStream("healthasli.png"));
		imagespeed = new Image(new Draw().getClass().getResourceAsStream("speedasli.png"));
		imagejump = new Image(new Draw().getClass().getResourceAsStream("jumpasli.png"));

		// chara ina xshoon sabete? age jadval 100 tai shod chi????????????????
		joon1 = new Joon(40, 20, group);
		joon2 = new Joon(180, 20, group);
		joon3 = new Joon(320, 20, group);
		joon4 = new Joon(460, 20, group);

	}

	public static Image getJump() {
		return imagejump;
	}

	public static void drawCell(Cell cell, Group group) {
		boolean black = !JavafxClass.getPlayer().isCellInVision(cell);

		drawOutlineOFCell(cell, group);
		drawBlackCover(cell, group);
		drawBonusBox(cell, group);
		drawFan(cell, group);

		drawPlayer(cell, group);
		drawJJCell(cell, group);

	}
	private static void drawJJCell(Cell cell, Group group){
		boolean black = !JavafxClass.getPlayer().isCellInVision(cell);

		if (gameEngine.getMapCellType(cell.getCol(), cell.getRow(), JavafxClass.getPlayer()) == JudgeAbstract.JJ_CELL) {

			ImageView imageViewbonus = new ImageView(JJ);
			imageViewbonus.setFitHeight(length);
			imageViewbonus.setFitWidth(length);
			imageViewbonus.setX(x );
			imageViewbonus.setY(y);
			group.getChildren().add(imageViewbonus);
			System.out.println("JJ");
		}
		//System.out.println("JJ");

			
	}


	private static void drawPlayer(Cell cell, Group group) {
		boolean black = !JavafxClass.getPlayer().isCellInVision(cell);

		if (cell.getAllPlayers().size() != 0 && cell.getAllPlayers() != null && black == false) {
			for (int i = 0; i < cell.getAllPlayers().size(); i++) {
				// bonus
				player = cell.getAllPlayers().get(i);

				// if (gameEngine.getMapCellType(cell.getCol(), cell.getRow())
				// == 0) ;
				// bonus finish

				if (aghaz < 10 && player.isAlive()) {
					// player = cell.getAllPlayers().get(0);
					if (player.getName() == Judge.SAMAN) {
						player = cell.getAllPlayers().get(0);
						image1 = new Image(new Draw().getClass().getResourceAsStream("c1-taajob.png"));
						imageView1 = new ImageView(image1);
						imageView1.setY(sabet + player.getCell().getRow() * length);
						imageView1.setX(player.getCell().getCol() * length);
						imageView1.setFitWidth(length);
						imageView1.setFitHeight(length);
						group.getChildren().add(imageView1);
						// imageView1.setY(player.getCell().getRow()*tool);
						// imageView1.setFitHeight(tool/2);
						// imageView1.setFitWidth(tool/2);
						// group.getChildren().add(imageView1);
					}

					if (player.getName() == 1) {
						image2 = new Image(new Draw().getClass().getResourceAsStream("c2-a1-r.png"));
						imageView2 = new ImageView(image2);
						imageView2.setY(sabet + player.getCell().getRow() * length);
						imageView2.setX(player.getCell().getCol() * length);
						imageView2.setFitWidth(length);
						imageView2.setFitHeight(length);
						group.getChildren().add(imageView2);
						// imageView1.setY(player.getCell().getRow()*tool);
						// imageView1.setFitHeight(tool/2);
						// imageView1.setFitWidth(tool/2);
						// group.getChildren().add(imageView1);
					}

					if (player.getName() == 2) {
						image3 = new Image(new Draw().getClass().getResourceAsStream("c3-a1-r.png"));
						imageView3 = new ImageView(image3);
						imageView3.setY(sabet + player.getCell().getRow() * length);
						imageView3.setX(player.getCell().getCol() * length);
						imageView3.setFitWidth(length);
						imageView3.setFitHeight(length);
						group.getChildren().add(imageView3);
						// imageView1.setY(player.getCell().getRow()*tool);
						// imageView1.setFitHeight(tool/2);
						// imageView1.setFitWidth(tool/2);
						// group.getChildren().add(imageView1);
					}

					if (player.getName() == 3) {
						image4 = new Image(new Draw().getClass().getResourceAsStream("c4-a1-r.png"));
						imageView4 = new ImageView(image4);
						imageView4.setY(sabet + player.getCell().getRow() * length);
						imageView4.setX(player.getCell().getCol() * length);
						imageView4.setFitWidth(length);
						imageView4.setFitHeight(length);
						group.getChildren().add(imageView4);
						// imageView1.setY(player.getCell().getRow()*tool);
						// imageView1.setFitHeight(tool/2);
						// imageView1.setFitWidth(tool/2);
						// group.getChildren().add(imageView1);
					}

					aghaz++;

					// player = cell.getAllPlayers().get(0);
					if (player.getName() == Judge.SAMAN) {
						// attack1(group,true,x,y);
						// System.out.println("okeyeokeye");
						Draw.move1(group, player);
						// attack1(group,true,x,y);
						// System.out.println("adam");
						// Image image1 = new Image(new
						// Draw().getClass().getResourceAsStream("c1-taajob.png"));
						// ImageView imageView1 = new ImageView(image1);
						// imageView1=new ImageView(image1);
						// imageView1.setFitHeight(tool / 2);
						// imageView1.setFitWidth(tool / 2);
						// imageView1.setX(x);
						// imageView1.setY(y);
						// group.getChildren().add(imageView1);
						// attack1(group,true,x,y);
					}

					if (player.getName() == 1) {
						System.out.println("player2");
						// attack2(group, true, x, y);
						move2(group, player);
					}
					if (player.getName() == 2)
						move3(group, player);
					// System.out.println(player.getName());
					if (player.getName() == 3) {
						System.out.println(player.getName());
						move4(group, player);
					}
				}
			}

		}
	}

	private static void drawFan(Cell cell, Group group) {
		boolean black = !JavafxClass.getPlayer().isCellInVision(cell);
		if (cell.getAllFans() != null && black == false && cell.getAllFans().size() != 0) {
			for (int i = 0; i < cell.getAllFans().size(); i++) {
				if (cell.getAllFans().get(i).isAlive()) {
					int a = cell.getAllFans().get(i).getOwner().getName();
					if (a == 0) {
						ImageView imageViewfans = new ImageView(imagefan1);
						imageViewfans.setFitHeight(length / 2 + 10);
						imageViewfans.setFitWidth(length / 2 + 10);
						imageViewfans.setX(x + length / 4);
						imageViewfans.setY(y + length / 4);
						group.getChildren().add(imageViewfans);
						System.out.println("fans!");
					}
					if (a == 1) {
						ImageView imageViewfans = new ImageView(imagefan2);
						imageViewfans.setFitHeight(length / 2 + 10);
						imageViewfans.setFitWidth(length / 2 + 10);
						imageViewfans.setX(x + length / 4);
						imageViewfans.setY(y + length / 4);
						group.getChildren().add(imageViewfans);
						System.out.println("fans!");
					}
					if (a == 2) {
						ImageView imageViewfans = new ImageView(imagefan3);
						imageViewfans.setFitHeight(length / 2 + 10);
						imageViewfans.setFitWidth(length / 2 + 10);
						imageViewfans.setX(x + length / 4);
						imageViewfans.setY(y + length / 4);
						group.getChildren().add(imageViewfans);
						System.out.println("fans!");
					}
					if (a == 3) {
						ImageView imageViewfans = new ImageView(imagefan4);
						imageViewfans.setFitHeight(length / 2 + 10);
						imageViewfans.setFitWidth(length / 2 + 10);
						imageViewfans.setX(x + length / 4);
						imageViewfans.setY(y + length / 4);
						group.getChildren().add(imageViewfans);
						System.out.println("fans!");
					}
				}
			}
		}
	}

	private static void drawBonusBox(Cell cell, Group group) {
		boolean black = !JavafxClass.getPlayer().isCellInVision(cell);

		if (gameEngine.getMapCellType(cell.getCol(), cell.getRow(), JavafxClass.getPlayer()) == JudgeAbstract.BONUS_CELL
				&& black == false) {

			ImageView imageViewbonus = new ImageView(imageganj);
			imageViewbonus.setFitHeight(length / 2);
			imageViewbonus.setFitWidth(length / 2);
			imageViewbonus.setX(x + length / 4);
			imageViewbonus.setY(y + length / 4);
			group.getChildren().add(imageViewbonus);
			System.out.println("bonus");
		}
	}

	private static void drawBlackCover(Cell cell, Group group) {
		boolean black = !JavafxClass.getPlayer().isCellInVision(cell);
		if (black) {
			imageViewdark = new ImageView(darkimage);
			imageViewdark.setX(x);
			imageViewdark.setY(y);
			imageViewdark.setFitHeight(length);
			imageViewdark.setFitWidth(length);
			group.getChildren().add(imageViewdark);
			System.out.println("dark");
		}
	}

	private static void drawOutlineOFCell(Cell cell, Group group) {
		boolean black = !JavafxClass.getPlayer().isCellInVision(cell);

		x = ((cell.getCol()) * length);
		y = (50 + (cell.getRow()) * length);
		ImageView imageView = new ImageView(imagezamin);
		// ??? in group o children o image view chian
		group.getChildren().add(imageView);
		imageView.setX(x);
		imageView.setY(y);
		imageView.setFitWidth(length);
		imageView.setFitHeight(length);
		Line line1 = new Line();
		Line line2 = new Line();
		Line line3 = new Line();
		Line line4 = new Line();
		line1.setStartX(x + length);
		line1.setStartY(y + length);
		line1.setEndX(x + length);
		line1.setEndY(y);
		line1.setStrokeWidth(3);
		line1.setStroke(Color.SADDLEBROWN);

		line2.setStartX(x + length);
		line2.setStartY(y);
		line2.setEndX(x);
		line2.setEndY(y);
		line2.setStrokeWidth(3);
		line2.setStroke(Color.SADDLEBROWN);

		line3.setStartX(x);
		line3.setStartY(y);
		line3.setEndX(x);
		line3.setEndY(y + length);
		line3.setStrokeWidth(3);
		line3.setStroke(Color.SADDLEBROWN);

		line4.setStartX(x);
		line4.setStartY(y + length);
		line4.setEndX(x + length);
		line4.setEndY(y + length);
		line4.setStrokeWidth(3);
		line4.setStroke(Color.SADDLEBROWN);

		if (!black) {
			if (cell.rightIsBlocked()) {
				group.getChildren().add(line1);
				System.out.println("right");
			}
			if (cell.upIsBlocked())
				group.getChildren().add(line2);
			if (cell.leftIsBlocked())
				group.getChildren().add(line3);
			if (cell.downIsBlocked())
				group.getChildren().add(line4);
		}
	}

	public static void setSize(double width1, double hight1, int col1, int row1) {
		row = row1;
		col = col1;
		width = width1;
		hight = hight1;
	}

	public static Timer attack1(final Group group, final boolean isright, final double x, final double y) {
		counter1 = 0;
		timer1 = new Timer();
		timer1.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						boolean gat = false;
						double xx, yy;
						if ((counter1 % 4) == 0) {
							if (gat == false) {
								xx = imageView1.getX();
								yy = imageView1.getY();
								gat = true;
							} else {
								xx = imageView1.getX() - 30 / row;
								yy = imageView1.getY() - 45 / row;
							}
							if (isright) {
								image1 = new Image(this.getClass().getResourceAsStream("c1-a1-r.png"));
								group.getChildren().remove(imageView1);
								imageView1 = new ImageView(image1);

								counter1++;
							} else {
								image1 = new Image(this.getClass().getResourceAsStream("c1-a1-l.png"));
								group.getChildren().remove(imageView1);
								imageView1 = new ImageView(image1);

								counter1++;
							}
							imageView1.setFitHeight(length + 90 / row);
							imageView1.setFitWidth(length + 90 / row);
							imageView1.setX(xx);
							imageView1.setY(yy);
							group.getChildren().add(imageView1);
						} else if ((counter1 % 4) == 1) {
							xx = imageView1.getX();
							yy = imageView1.getY();
							if (isright) {
								image1 = new Image(this.getClass().getResourceAsStream("c1-a2-r.png"));
								group.getChildren().remove(imageView1);
								imageView1 = new ImageView(image1);
								counter1++;
							} else {
								image1 = new Image(this.getClass().getResourceAsStream("c1-a2-l.png"));
								group.getChildren().remove(imageView1);
								imageView1 = new ImageView(image1);
								counter1++;
							}
							imageView1.setFitHeight(length + 90 / row);
							imageView1.setFitWidth(length + 90 / row);
							imageView1.setX(xx + 60 / row);
							imageView1.setY(yy);
							group.getChildren().add(imageView1);
						} else if ((counter1 % 4) == 2) {
							xx = imageView1.getX() - 60 / row;
							yy = imageView1.getY();

							if (isright) {
								image1 = new Image(this.getClass().getResourceAsStream("c1-a4-r.png"));
								group.getChildren().remove(imageView1);
								imageView1 = new ImageView(image1);
								counter1++;
							} else {
								image1 = new Image(this.getClass().getResourceAsStream("c1-a4-l.png"));
								group.getChildren().remove(imageView1);
								imageView1 = new ImageView(image1);
								counter1++;
							}
							imageView1.setFitHeight(length + 90 / row);
							imageView1.setFitWidth(length + 90 / row);
							imageView1.setX(xx + 30 / row);
							imageView1.setY(yy + 45 / row);
							group.getChildren().add(imageView1);
						} else if ((counter1 % 4) == 3) {
							xx = imageView1.getX() - 30 / row;
							yy = imageView1.getY() - 45 / row;

							if (isright) {
								image1 = new Image(this.getClass().getResourceAsStream("c1-a4-r.png"));
								group.getChildren().remove(imageView1);
								imageView1 = new ImageView(image1);
								counter1++;
							} else {
								image1 = new Image(this.getClass().getResourceAsStream("c1-a4-l.png"));
								group.getChildren().remove(imageView1);
								imageView1 = new ImageView(image1);
								counter1++;
							}

							imageView1.setFitHeight(length + 90 / row);
							imageView1.setFitWidth(length + 90 / row);
							imageView1.setX(xx + 30 / row);
							imageView1.setY(yy + 45 / row);
							group.getChildren().add(imageView1);
						}

					}
				});
			}
		}, 10, 300);
		return timer1;
	}

	public static Timer attack2(final Group group, final boolean isright, final double x, final double y) {
		timer2 = new Timer();
		// System.out.println("vorood1");
		timer2.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						double xx = imageView2.getX();
						double yy = imageView2.getY();
						if ((counter2 % 2) == 0) {
							// System.out.println("vorood2");
							if (isright) {
								image2 = new Image(this.getClass().getResourceAsStream("c2-a1-r.png"));
								group.getChildren().remove(imageView2);
								imageView2 = new ImageView(image2);
								counter2++;
							} else {
								image2 = new Image(this.getClass().getResourceAsStream("c2-a1-l.png"));
								group.getChildren().remove(imageView2);
								imageView2 = new ImageView(image2);
								counter2++;
							}
						} else if ((counter2 % 2) == 1) {
							if (isright) {
								image2 = new Image(this.getClass().getResourceAsStream("c2-a2-r.png"));
								group.getChildren().remove(imageView2);
								imageView2 = new ImageView(image2);
								counter2++;
							} else {
								image2 = new Image(this.getClass().getResourceAsStream("c2-a2-l.png"));
								group.getChildren().remove(imageView2);
								imageView2 = new ImageView(image2);
								counter2++;
							}
						}

						imageView2.setFitHeight(length);
						imageView2.setFitWidth(length);
						imageView2.setX(xx);
						imageView2.setY(yy);
						group.getChildren().add(imageView2);

					}
				});
			}
		}, 10, 450);
		return timer2;
	}

	public static Timer attack3(final Group group, final boolean isright, final double x, final double y) {
		timer3 = new Timer();

		timer3.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						double x = imageView3.getX();
						double y = imageView3.getY();
						if ((counter3 % 2) == 0) {
							if (isright) {
								image3 = new Image(this.getClass().getResourceAsStream("c3-a1-r.png"));
								group.getChildren().remove(imageView3);
								imageView3 = new ImageView(image3);
								// group.getChildren().add(imageView3);
								counter3++;
							} else {
								image3 = new Image(this.getClass().getResourceAsStream("c3-a1-l.png"));
								group.getChildren().remove(imageView3);
								imageView3 = new ImageView(image3);
								// group.getChildren().add(imageView3);
								counter3++;
							}
							imageView3.setFitHeight(length);
							imageView3.setFitWidth(length);
							imageView3.setX(x);
							imageView3.setY(y);
							group.getChildren().add(imageView3);
						} else if ((counter3 % 2) == 1) {
							if (isright) {
								image3 = new Image(this.getClass().getResourceAsStream("c3-a2-r.png"));
								group.getChildren().remove(imageView3);
								imageView3 = new ImageView(image3);
								// group.getChildren().add(imageView3);
								counter3++;
							} else {
								image3 = new Image(this.getClass().getResourceAsStream("c3-a2-l.png"));
								group.getChildren().remove(imageView3);
								imageView3 = new ImageView(image3);
								// group.getChildren().add(imageView3);
								counter3++;

							}
							imageView3.setFitHeight(length);
							imageView3.setFitWidth(length + 300 / row);
							// imageView3.setX(x-20);
							// imageView3.setY(y+tool/2-10);
							imageView3.setX(x);
							imageView3.setY(y);
							group.getChildren().add(imageView3);
						}

					}
				});
			}
		}, 10, 500);
		return timer3;
	}

	public static Timer attack4(final Group group, final boolean isright, final double x, final double y) {
		timer4 = new Timer();

		timer4.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						double x = imageView4.getX();
						double y = imageView4.getY();
						if ((counter4 % 2) == 0) {
							if (isright) {
								image4 = new Image(this.getClass().getResourceAsStream("c4-a1-r.png"));
								group.getChildren().remove(imageView4);
								imageView4 = new ImageView(image4);
								// group.getChildren().add(imageView4);
								counter4++;
							} else {
								image4 = new Image(this.getClass().getResourceAsStream("c4-a1-l.png"));
								group.getChildren().remove(imageView4);
								imageView4 = new ImageView(image4);
								// group.getChildren().add(imageView4);
								counter4++;
							}

							imageView4.setFitHeight(length);
							imageView4.setFitWidth(length);
							imageView4.setX(x);
							imageView4.setY(y);
							group.getChildren().add(imageView4);
						} else if ((counter4 % 2) == 1) {
							if (isright) {
								image4 = new Image(this.getClass().getResourceAsStream("c4-a2-r.png"));
								group.getChildren().remove(imageView4);
								imageView4 = new ImageView(image4);
								// group.getChildren().add(imageView4);
								counter4++;
							} else {
								image4 = new Image(this.getClass().getResourceAsStream("c4-a2-l.png"));
								group.getChildren().remove(imageView4);
								imageView4 = new ImageView(image4);
								// group.getChildren().add(imageView4);
								counter4++;
							}

							imageView4.setFitHeight(length);
							imageView4.setFitWidth(length);
							imageView4.setX(x);
							imageView4.setY(y);
						}

					}
				});
			}
		}, 10, 500);
		return timer4;
	}

	public static void move1(Group group, Player player) {
		// System.out.println("open");

		if (dark1) {
			System.out.println("nist!");
			group.getChildren().remove(imageView1);
			return;
		}

		// System.out.println(player.isMoving());
		if (!player.isMoving()) {
			if (!JavafxClass.getPlayer().isCellInVision(player.getCell())) {
				System.out.println("nist!");
				group.getChildren().remove(imageView1);
				dark1 = true;
				return;
			}
			// System.out.println("hoora");
			group.getChildren().remove(imageView1);
			// image1=new Image(new
			// Draw().getClass().getResourceAsStream("c1-taajob.png"));
			// imageView1=new ImageView(image1);
			imageView1.setX(player.getCell().getCol() * length);
			imageView1.setY(sabet + player.getCell().getRow() * length);
			// imageView1.setFitHeight(tool/2);
			// imageView1.setFitWidth(tool/2);
			group.getChildren().add(imageView1);
			// System.out.println("lllllllllllllllllllllllllllllllllllllllllll");
		}

		else if (player.directionOfMove() == 3) {
			move1Left(group, player);
		} else if (player.directionOfMove() == 1) {
			move1Right(group, player);
		} else if (player.directionOfMove() == 2) {
			move1Down(group, player);
		} else if (player.directionOfMove() == 0) {
			move1Up(group, player);
		}

		showInPrison1(group);
	}

	private static void showInPrison1(Group group) {
		if (prison1) {
			group.getChildren().remove(imageViewprison);
			// System.out.println("lllllllllllllllllllllllllllllllllllllllllllllllllllllll");
			imageViewprison = new ImageView(imageprison);
			imageViewprison.setX(Draw.getImageView1().getX());
			imageViewprison.setY(Draw.getImageView1().getY());
			imageViewprison.setFitHeight(Draw.getLength());
			imageViewprison.setFitWidth(Draw.getLength());
			group.getChildren().add(imageViewprison);
		}
		if (prison1 == false) {
			group.getChildren().remove(imageViewprison);
			// System.out.println("false");
		}
		// System.out.println(prison1);
	}

	private static void move1Up(Group group, Player player) {
		double xmabda = player.getCell().getCol() * length;
		double ymabda = sabet + player.getCell().getRow() * length;

		group.getChildren().remove(imageView1);
		// imageView1 = new ImageView(new Image(new
		// Draw().getClass().getResourceAsStream("c1-taajob.png")));
		imageView1.setY(ymabda - player.movePercent() * length);
		imageView1.setX(xmabda);
		// imageView1.setFitHeight(tool / 2);
		// imageView1.setFitWidth(tool / 2);
		group.getChildren().add(imageView1);
	}

	private static void move1Down(Group group, Player player) {
		double xmabda = player.getCell().getCol() * length;
		double ymabda = sabet + player.getCell().getRow() * length;

		group.getChildren().remove(imageView1);
		// imageView1 = new ImageView(new Image(new
		// Draw().getClass().getResourceAsStream("c1-taajob.png")));
		imageView1.setY(ymabda + player.movePercent() * length);
		imageView1.setX(xmabda);
		// imageView1.setFitHeight(tool / 2);
		// imageView1.setFitWidth(tool / 2);
		group.getChildren().add(imageView1);
	}

	private static void move1Right(Group group, Player player) {
		double xmabda = player.getCell().getCol() * length;
		double ymabda = sabet + player.getCell().getRow() * length;

		group.getChildren().remove(imageView1);
		// imageView1 = new ImageView(new Image(new
		// Draw().getClass().getResourceAsStream("c1-taajob.png")));
		imageView1.setX(xmabda + player.movePercent() * length);
		imageView1.setY(ymabda);
		// imageView1.setFitHeight(tool / 2);
		// imageView1.setFitWidth(tool / 2);
		group.getChildren().add(imageView1);
	}

	private static void move1Left(Group group, Player player) {
		// shah player.setSpeed(1);
		// System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooo");
		double xmabda = player.getCell().getCol() * length;
		double ymabda = sabet + player.getCell().getRow() * length;
		// System.out.println(">>>>>>>>>>>"+ player.isMoving()
		// +"<<<<<<<<<<<<<<<<");
		group.getChildren().remove(imageView1);
		// imageView1 = new ImageView(new Image(new
		// Draw().getClass().getResourceAsStream("c1-taajob.png")));
		imageView1.setX(xmabda - player.movePercent() * length);
		imageView1.setY(ymabda);
		// imageView1.setFitHeight(tool / 2);
		// imageView1.setFitWidth(tool / 2);
		group.getChildren().add(imageView1);
	}

	public static void setGameEngine(GameEngine gameEngine1) {
		gameEngine = gameEngine1;
	}

	public static ImageView getImageView1() {
		return imageView1;
	}

	public static ImageView getImageView2() {
		return imageView2;
	}

	public static ImageView getImageView3() {
		return imageView3;
	}

	public static ImageView getImageView4() {
		return imageView4;
	}

	public static void setImageView1(ImageView imageView) {
		imageView1 = imageView;
	}

	public static void setImageView2(ImageView imageView) {
		imageView2 = imageView;
	}

	public static void setImageView3(ImageView imageView) {
		imageView3 = imageView;
	}

	public static void setImageView4(ImageView imageView) {
		imageView4 = imageView;
	}

	public static double getLength() {
		return length;
	}

	public static void setPrison1(boolean bool) {
		prison1 = bool;
	}

	public static void drawMap(Group group) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				// System.out.println(Cell.getCell(i,j).getCol());
				Draw.drawCell(Cell.getCell(i, j), group);
			}
		}
	}

	public static void move3(Group group, Player player3) {
		// System.out.println(player2.getCell().getCol());
		// System.out.println(player.getCell().getCol());
		// System.out.println(player.isCellInVision(player2.getCell()));
		if (dark3) {
			System.out.println("nist!");
			group.getChildren().remove(imageView3);
			return;
		}

		if (!player3.isMoving()) {
			if (!JavafxClass.getPlayer().isCellInVision(player3.getCell())) {
				System.out.println("nist!");
				group.getChildren().remove(imageView3);
				dark3 = true;
				return;
			}

			group.getChildren().remove(imageView3);
			// if(imageView3!=null) {
			imageView3.setX(player3.getCell().getCol() * length);
			imageView3.setY(sabet + player3.getCell().getRow() * length);
			group.getChildren().add(imageView3);
			// }

		}

		else if (player3.directionOfMove() == 3) {
			move3Left(group, player3);
		} else if (player3.directionOfMove() == 1) {
			move3Right(group, player3);
		} else if (player3.directionOfMove() == 2) {
			move3Down(group, player3);
		} else if (player3.directionOfMove() == 0) {
			move3Up(group, player3);
		}

		if (prison3) {
			showInPrison3(group);
		}
		if (prison3 == false) {
			group.getChildren().remove(imageViewprison3);
		}
	}

	private static void showInPrison3(Group group) {
		group.getChildren().remove(imageViewprison3);
		imageViewprison3 = new ImageView(imageprison);
		imageViewprison3.setX(Draw.getImageView3().getX());
		imageViewprison3.setY(Draw.getImageView3().getY());
		imageViewprison3.setFitHeight(Draw.getLength());
		imageViewprison3.setFitWidth(Draw.getLength());
		group.getChildren().add(imageViewprison3);
	}

	private static void move3Up(Group group, Player player3) {
		double xmabda = player3.getCell().getCol() * length;
		double ymabda = sabet + player3.getCell().getRow() * length;

		group.getChildren().remove(imageView3);
		imageView3.setY(ymabda - player3.movePercent() * length);
		imageView3.setX(xmabda);
		group.getChildren().add(imageView3);
	}

	private static void move3Down(Group group, Player player3) {
		double xmabda = player3.getCell().getCol() * length;
		double ymabda = sabet + player3.getCell().getRow() * length;

		group.getChildren().remove(imageView3);
		imageView3.setY(ymabda + player3.movePercent() * length);
		imageView3.setX(xmabda);
		group.getChildren().add(imageView3);
	}

	private static void move3Right(Group group, Player player3) {
		double xmabda = player3.getCell().getCol() * length;
		double ymabda = sabet + player3.getCell().getRow() * length;

		group.getChildren().remove(imageView3);
		imageView3.setX(xmabda + player3.movePercent() * length);
		imageView3.setY(ymabda);
		group.getChildren().add(imageView3);
	}

	private static void move3Left(Group group, Player player3) {
		double xmabda = player3.getCell().getCol() * length;
		double ymabda = sabet + player3.getCell().getRow() * length;
		group.getChildren().remove(imageView3);
		imageView3.setX(xmabda - player3.movePercent() * length);
		imageView3.setY(ymabda);
		group.getChildren().add(imageView3);
	}

	public static void move2(Group group, Player player2) {
		// System.out.println(player2.getCell().getCol());
		// System.out.println(player.getCell().getCol());
		// System.out.println(player.isCellInVision(player2.getCell()));
		if (dark2) {
			System.out.println("nist!");
			group.getChildren().remove(imageView2);
			return;
		}

		if (!player2.isMoving()) {
			if (!JavafxClass.getPlayer().isCellInVision(player2.getCell())) {
				System.out.println("nist!");
				group.getChildren().remove(imageView2);
				dark2 = true;
				return;
			}

			group.getChildren().remove(imageView2);
			imageView2.setX(player2.getCell().getCol() * length);
			imageView2.setY(sabet + player2.getCell().getRow() * length);
			group.getChildren().add(imageView2);
		}

		else if (player2.directionOfMove() == 3) {
			move2Left(group, player2);
		} else if (player2.directionOfMove() == 1) {
			move2Right(group, player2);
		} else if (player2.directionOfMove() == 2) {
			move2Down(group, player2);
		} else if (player2.directionOfMove() == 0) {
			move2Up(group, player2);
		}

		if (prison2) {
			showInPrison2(group);
		}
		if (prison2 == false) {
			group.getChildren().remove(imageViewprison2);
		}
	}

	private static void showInPrison2(Group group) {
		group.getChildren().remove(imageViewprison2);
		imageViewprison2 = new ImageView(imageprison);
		imageViewprison2.setX(Draw.getImageView2().getX());
		imageViewprison2.setY(Draw.getImageView2().getY());
		imageViewprison2.setFitHeight(Draw.getLength());
		imageViewprison2.setFitWidth(Draw.getLength());
		group.getChildren().add(imageViewprison2);
	}

	private static void move2Up(Group group, Player player2) {
		double xmabda = player2.getCell().getCol() * length;
		double ymabda = sabet + player2.getCell().getRow() * length;

		group.getChildren().remove(imageView2);
		imageView2.setY(ymabda - player2.movePercent() * length);
		imageView2.setX(xmabda);
		group.getChildren().add(imageView2);
	}

	private static void move2Down(Group group, Player player2) {
		double xmabda = player2.getCell().getCol() * length;
		double ymabda = sabet + player2.getCell().getRow() * length;

		group.getChildren().remove(imageView2);
		imageView2.setY(ymabda + player2.movePercent() * length);
		imageView2.setX(xmabda);
		group.getChildren().add(imageView2);
	}

	private static void move2Right(Group group, Player player2) {
		double xmabda = player2.getCell().getCol() * length;
		double ymabda = sabet + player2.getCell().getRow() * length;

		group.getChildren().remove(imageView2);
		imageView2.setX(xmabda + player2.movePercent() * length);
		imageView2.setY(ymabda);
		group.getChildren().add(imageView2);
	}

	private static void move2Left(Group group, Player player2) {
		double xmabda = player2.getCell().getCol() * length;
		double ymabda = sabet + player2.getCell().getRow() * length;
		group.getChildren().remove(imageView2);
		imageView2.setX(xmabda - player2.movePercent() * length);
		imageView2.setY(ymabda);
		group.getChildren().add(imageView2);
	}

	public static void move4(Group group, Player player2) {
		// System.out.println(player2.getCell().getCol());
		// System.out.println(player.getCell().getCol());
		// System.out.println(player.isCellInVision(player2.getCell()));
		imageView4.setY(sabet + player2.getCell().getRow() * length);
		if (dark4) {
			System.out.println("nist!");
			group.getChildren().remove(imageView4);
			return;
		}

		if (!player2.isMoving()) {
			if (!JavafxClass.getPlayer().isCellInVision(player2.getCell())) {
				System.out.println("nist!");
				group.getChildren().remove(imageView4);
				dark4 = true;
				return;
			}

			group.getChildren().remove(imageView4);
			if (imageView4 != null)
				imageView4.setX(player2.getCell().getCol() * length);
			imageView4.setY(sabet + player2.getCell().getRow() * length);
			group.getChildren().add(imageView4);
		}

		else if (player2.directionOfMove() == 3) {
			move4Left(group, player2);
		} else if (player2.directionOfMove() == 1) {
			move4Right(group, player2);
		} else if (player2.directionOfMove() == 2) {
			move4Down(group, player2);
		} else if (player2.directionOfMove() == 0) {
			move4Up(group, player2);
		}

		if (prison4) {
			showInPrison4(group);
		}
		if (prison4 == false) {
			group.getChildren().remove(imageViewprison4);
		}
	}

	private static void showInPrison4(Group group) {
		group.getChildren().remove(imageViewprison4);
		imageViewprison4 = new ImageView(imageprison);
		imageViewprison4.setX(Draw.getImageView4().getX());
		imageViewprison4.setY(Draw.getImageView4().getY());
		imageViewprison4.setFitHeight(Draw.getLength());
		imageViewprison4.setFitWidth(Draw.getLength());
		group.getChildren().add(imageViewprison4);
	}

	private static void move4Up(Group group, Player player2) {
		double xmabda = player2.getCell().getCol() * length;
		double ymabda = sabet + player2.getCell().getRow() * length;

		group.getChildren().remove(imageView4);
		imageView4.setY(ymabda - player2.movePercent() * length);
		imageView4.setX(xmabda);
		group.getChildren().add(imageView4);
	}

	private static void move4Down(Group group, Player player2) {
		double xmabda = player2.getCell().getCol() * length;
		double ymabda = sabet + player2.getCell().getRow() * length;

		group.getChildren().remove(imageView4);
		imageView4.setY(ymabda + player2.movePercent() * length);
		imageView4.setX(xmabda);
		group.getChildren().add(imageView4);
	}

	private static void move4Right(Group group, Player player2) {
		double xmabda = player2.getCell().getCol() * length;
		double ymabda = sabet + player2.getCell().getRow() * length;

		group.getChildren().remove(imageView4);
		imageView4.setX(xmabda + player2.movePercent() * length);
		imageView4.setY(ymabda);
		group.getChildren().add(imageView4);
	}

	private static void move4Left(Group group, Player player2) {
		double xmabda = player2.getCell().getCol() * length;
		double ymabda = sabet + player2.getCell().getRow() * length;
		group.getChildren().remove(imageView4);
		imageView4.setX(xmabda - player2.movePercent() * length);
		imageView4.setY(ymabda);
		group.getChildren().add(imageView4);
	}

	static boolean bool = true;

	public static void drawToolbar() {
		joon1.setGroup(group);
		joon2.setGroup(group);
		joon3.setGroup(group);
		joon4.setGroup(group);

		if (bool) {
			joon1.draw();
			joon2.draw();
			joon3.draw();
			joon4.draw();
			bool = false;
		}
		if (Player.getPlayerFromNumber(0) != null) {
			joon1.setJoon((double) Player.getPlayerFromNumber(0).getHealth() / (double) 100);
		}
		if (Player.getPlayerFromNumber(1) != null)
			joon2.setJoon((double) Player.getPlayerFromNumber(1).getHealth() / (double) 100);
		if (Player.getPlayerFromNumber(2) != null)
			joon3.setJoon((double) Player.getPlayerFromNumber(2).getHealth() / (double) 100);
		if (Player.getPlayerFromNumber(3) != null)
			joon4.setJoon((double) Player.getPlayerFromNumber(3).getHealth() / (double) 100);
		// joon2.setJoon(player2.getHealth()/100);
		// joon3.setJoon(player3.getHealth()/100);
		// joon4.setJoon(player4.getHealth()/100);

	}

	public static void setGroup(Group group1) {
		group = group1;
	}

	public static Image getSpeed() {
		return imagespeed;
	}

	public static Image getRadar() {
		return imageradar;
	}

	public static Image getHealth() {
		return imagehealth;
	}

}

class Joon {
	double x;
	double y;
	Line line1;
	Line line2;
	Line line3;
	Line line4;
	Rectangle rectangle;
	double joon;
	Group group;

	public Joon(double x, double y, Group group) {
		this.x = x;
		this.y = y;
		this.group = group;
		this.joon = joon;
		line1 = new Line();
		line2 = new Line();
		line3 = new Line();
		line4 = new Line();
		line1.setStartX(x);
		line1.setStartY(y);
		line1.setEndX(x);
		line1.setEndY(y + 20);
		line2.setStartX(x);
		line2.setStartY(y + 20);
		line2.setEndX(x + 80);
		line2.setEndY(y + 20);
		line3.setStartX(x + 80);
		line3.setStartY(y + 20);
		line3.setEndX(x + 80);
		line3.setEndY(y);
		line4.setStartX(x + 80);
		line4.setStartY(y);
		line4.setEndX(x);
		line4.setEndY(y);
		line1.setStrokeWidth(3);
		line1.setStroke(Color.BLUE);
		line2.setStrokeWidth(3);
		line2.setStroke(Color.BLUE);
		line3.setStrokeWidth(3);
		line3.setStroke(Color.BLUE);
		line4.setStrokeWidth(3);
		line4.setStroke(Color.BLUE);
		rectangle = new Rectangle();
		rectangle.setX(x + 2);
		rectangle.setY(y + 2);
		rectangle.setWidth(76);
		rectangle.setHeight(16);
		rectangle.setFill(Color.LIGHTSKYBLUE);
	}

	public void setJoon(double joon) {
		this.joon = joon;
		group.getChildren().remove(rectangle);
		rectangle.setWidth(76 * joon);
		group.getChildren().add(rectangle);
	}

	public void setGroup(Group group1) {
		group = group1;
	}

	public void draw() {
		group.getChildren().add(line1);
		group.getChildren().add(line2);
		group.getChildren().add(line3);
		group.getChildren().add(line4);
		group.getChildren().add(rectangle);
	}

}
