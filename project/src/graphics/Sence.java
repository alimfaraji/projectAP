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
import main.JavafxClass;
import source.*;
import javafx.*;
import javafx.application.*;
import javax.swing.text.html.ImageView;
import java.util.Timer;

/**
 * Created by khahsayar on 7/5/2015.
 */
public class Sence {
	public static boolean control1 = true;
	public static boolean control2 = true;
	public static boolean control3 = true;
	public static boolean control4 = true;

	static boolean space = true;
	static boolean space2 = true;
	static boolean space3 = true;
	static boolean space4 = true;

	static GameEngine gameEngine = new GameEngine();
	static Group group;
	static Timer timer;
	static Timer timer2;
	static Timer timer3;
	static Timer timer4;

	// static Group group;
	static Player player;

	public static boolean getControl() {
		return control1;
	}

	public static void setControl(boolean bool) {
		control1 = bool;
	}

	public static void aClick(final Scene scene) {
		System.out.println("ggg");
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {

				doPlayer0Operations(scene, keyEvent);

				doPlayer1Operations(scene, keyEvent);

				doPlayer2Operations(scene, keyEvent);

				doPlayer3Operations(scene, keyEvent);

			}

			private void doPlayer3Operations(final Scene scene, KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.LEFT && control4 == true) {
					try {
						control4 = false;
						Player.getAllPlayers().get(3).move(3);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.RIGHT && control4 == true) {
					try {
						control4 = false;
						Player.getAllPlayers().get(3).move(1);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.DOWN && control4 == true) {
					try {
						control4 = false;
						Player.getAllPlayers().get(3).move(2);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.UP && control4 == true) {
					try {
						control4 = false;
						Player.getAllPlayers().get(3).move(0);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.NUMPAD0 && space4 == true) {
					control4 = false;
					timer4 = Draw.attack4(group, true, 1.0, 1.0);
					space4 = false;
					try {
						Player.getPlayerFromNumber(3).attack(0);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();

					}

					try {
						Player.getPlayerFromNumber(3).attack(1);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						Player.getPlayerFromNumber(3).attack(2);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						Player.getPlayerFromNumber(3).attack(3);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
						System.out.println(bozorgExceptionBase.getClass().getName());
					}

					try {
						Player.getPlayerFromNumber(3).attack(4);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}
				}

				if (keyEvent.getCode() == KeyCode.CONTROL) {
					control4 = false;
					Player.getAllPlayers().get(3).throwFan();
					Draw.drawCell(Player.getPlayerFromNumber(3).getCell(), group);
					Sence.aClick(scene);
					System.out.println("Throwfan2!");
					// System.out.println(Cell.getCell(0,1).getAllFans().size());
				}
			}

			private void doPlayer2Operations(final Scene scene, KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.NUMPAD4 && control3 == true) {
					try {
						control3 = false;
						Player.getAllPlayers().get(2).move(3);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						System.out.println(Player.getAllPlayers().get(1).movePercent());
						System.out.println(Player.getAllPlayers().get(1).getSpeed()
								* (GameEngine.getStaticTime() - Player.getAllPlayers().get(0).getStartTimeForMoving()));
						System.out.println(bozorgExceptionBase.getClass().getSimpleName());
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.NUMPAD6 && control3 == true) {
					try {
						control3 = false;
						Player.getAllPlayers().get(2).move(1);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						System.out.println(Player.getAllPlayers().get(1).movePercent());
						System.out.println(Player.getAllPlayers().get(1).getSpeed()
								* (GameEngine.getStaticTime() - Player.getAllPlayers().get(0).getStartTimeForMoving()));
						System.out.println(bozorgExceptionBase.getClass().getSimpleName());
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.NUMPAD5 && control3 == true) {
					try {
						control3 = false;
						Player.getAllPlayers().get(2).move(2);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						System.out.println(Player.getAllPlayers().get(1).movePercent());
						System.out.println(Player.getAllPlayers().get(1).getSpeed()
								* (GameEngine.getStaticTime() - Player.getAllPlayers().get(0).getStartTimeForMoving()));
						System.out.println(bozorgExceptionBase.getClass().getSimpleName());
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.NUMPAD8 && control3 == true) {
					try {
						control3 = false;
						Player.getAllPlayers().get(2).move(0);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						System.out.println(Player.getAllPlayers().get(1).movePercent());
						System.out.println(Player.getAllPlayers().get(1).getSpeed()
								* (GameEngine.getStaticTime() - Player.getAllPlayers().get(0).getStartTimeForMoving()));
						System.out.println(bozorgExceptionBase.getClass().getSimpleName());
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.ENTER && space3 == true) {
					control3 = false;
					timer3 = Draw.attack3(group, true, 1.0, 1.0);
					space3 = false;
					try {
						Player.getPlayerFromNumber(2).attack(0);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();

					}

					try {
						Player.getPlayerFromNumber(2).attack(1);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						Player.getPlayerFromNumber(2).attack(2);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						Player.getPlayerFromNumber(2).attack(3);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
						System.out.println(bozorgExceptionBase.getClass().getName());
					}

					try {
						Player.getPlayerFromNumber(2).attack(4);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}
				}

				if (keyEvent.getCode() == KeyCode.NUMPAD7) {
					control3 = false;
					Player.getAllPlayers().get(2).throwFan();
					Draw.drawCell(Player.getPlayerFromNumber(2).getCell(), group);
					Sence.aClick(scene);
					System.out.println("Throwfan2!");
					// System.out.println(Cell.getCell(0,1).getAllFans().size());
				}
			}

			private void doPlayer1Operations(final Scene scene, KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.J && control1 == true) {
					try {
						control2 = false;
						Player.getAllPlayers().get(1).move(3);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {

						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.L && control1 == true) {
					try {
						control2 = false;
						Player.getAllPlayers().get(1).move(1);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.K && control1 == true) {
					try {
						control2 = false;
						Player.getAllPlayers().get(1).move(2);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {

						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.I && control1 == true) {
					try {
						control2 = false;
						Player.getAllPlayers().get(1).move(0);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {

						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.P && space2 == true) {
					control2 = false;
					timer2 = Draw.attack2(group, true, 1.0, 1.0);
					space2 = false;
					try {
						Player.getPlayerFromNumber(1).attack(0);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						Player.getPlayerFromNumber(1).attack(1);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						Player.getPlayerFromNumber(1).attack(2);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						Player.getPlayerFromNumber(1).attack(3);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						Player.getPlayerFromNumber(1).attack(4);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}
				}

				if (keyEvent.getCode() == KeyCode.O) {
					control2 = false;
					Player.getAllPlayers().get(1).throwFan();
					Draw.drawCell(Player.getPlayerFromNumber(1).getCell(), group);
					Sence.aClick(scene);
					System.out.println("Throwfan2!");
					// System.out.println(Cell.getCell(0,1).getAllFans().size());
				}
			}

			private void doPlayer0Operations(final Scene scene, KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.A && control1 == true) {
					try {
						control1 = false;
						Player.getAllPlayers().get(0).move(3);
						Sence.aClick(scene);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.D && control1 == true) {
					try {
						control1 = false;
						Player.getAllPlayers().get(0).move(1);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.S && control1 == true) {
					try {
						System.out.println("nonononono");
						control1 = false;
						Player.getAllPlayers().get(0).move(2);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.W && control1 == true) {
					try {
						control1 = false;
						Player.getAllPlayers().get(0).move(0);
						Sence.aClick(scene);
						System.out.println("DOROST");
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						Sence.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.SPACE && space == true) {
					control1 = false;
					Timer timer1 = Draw.attack1(group, true, 1.0, 1.0);
					timer = timer1;
					space = false;
					try {
						player.attack(0);

					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						player.attack(1);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						player.attack(2);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						player.attack(3);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}

					try {
						player.attack(4);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}
				}

				if (keyEvent.getCode() == KeyCode.SHIFT) {
					control1 = false;
					Player.getAllPlayers().get(0).throwFan();
					Draw.drawCell(Player.getPlayerFromNumber(0).getCell(), group);
					Sence.aClick(scene);
					System.out.println("Throwfan!");
				}
			}
		});
	}

	public static void bClick(Scene scene) {
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
					imageView1.setFitHeight(Draw.length);
					imageView1.setFitWidth(Draw.length);
					group.getChildren().add(imageView1);
					Draw.imageView1 = imageView1;
					space = true;
				}

				if (keyEvent.getCode() == KeyCode.P) {
					timer2.cancel();
					javafx.scene.image.ImageView imageView2 = Draw.imageView2;
					group.getChildren().remove(imageView2);
					double x = imageView2.getX();
					double y = imageView2.getY();
					Image image2 = new Image(new Draw().getClass().getResourceAsStream("c2-a1-r.png"));
					imageView2 = new javafx.scene.image.ImageView(image2);
					imageView2.setX(x);
					imageView2.setY(y);
					imageView2.setFitHeight(Draw.length);
					imageView2.setFitWidth(Draw.length);
					group.getChildren().add(imageView2);
					Draw.imageView2 = imageView2;
					space2 = true;
				}

				if (keyEvent.getCode() == KeyCode.ENTER) {
					timer3.cancel();
					javafx.scene.image.ImageView imageView3 = Draw.imageView3;
					group.getChildren().remove(imageView3);
					double x = imageView3.getX();
					double y = imageView3.getY();
					Image image2 = new Image(new Draw().getClass().getResourceAsStream("c3-a1-r.png"));
					imageView3 = new javafx.scene.image.ImageView(image2);
					imageView3.setX(x);
					imageView3.setY(y);
					imageView3.setFitHeight(Draw.length);
					imageView3.setFitWidth(Draw.length);
					group.getChildren().add(imageView3);
					Draw.imageView3 = imageView3;
					space3 = true;
				}

				if (keyEvent.getCode() == KeyCode.NUMPAD0) {
					timer4.cancel();
					javafx.scene.image.ImageView imageView4 = Draw.imageView4;
					group.getChildren().remove(imageView4);
					double x = imageView4.getX();
					double y = imageView4.getY();
					Image image2 = new Image(new Draw().getClass().getResourceAsStream("c4-a1-r.png"));
					imageView4 = new javafx.scene.image.ImageView(image2);
					imageView4.setX(x);
					imageView4.setY(y);
					imageView4.setFitHeight(Draw.length);
					imageView4.setFitWidth(Draw.length);
					group.getChildren().add(imageView4);
					Draw.imageView4 = imageView4;
					space4 = true;
				}
			}
		});

	}

	public static void setGroup(Group group1) {
		group = group1;
	}

	public static void setPlayer(Player player1) {
		player = player1;
	}

	public static boolean getSpace() {
		return space;
	}

	public static boolean getSpace2() {
		return space2;
	}

	public static boolean getSpace3() {
		return space3;
	}

	public static boolean getSpace4() {
		return space4;
	}
}