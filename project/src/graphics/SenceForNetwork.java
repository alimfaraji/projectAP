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

import Network.Client;

import java.io.IOException;
import java.util.Timer;

/**
 * Created by khahsayar on 7/5/2015.
 */
public class SenceForNetwork {

	public static boolean getcontrol() {
		return Sence.control1;
	}

	public static void setcontrol(boolean bool) {
		Sence.control1 = bool;
	}

	public static void aClick(final Scene scene) {
		System.out.println("ggg");
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				doPlayer0Operations(scene, keyEvent);
			}

			private void doPlayer0Operations(final Scene scene, KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.A && Sence.control1 == true) {
					try {
						if (Client.player == 0 )
							Sence.control1 = false;
						if (Client.player == 1 )
							Sence.control2 = false;
						if (Client.player == 2 )
							Sence.control3 = false;
						if (Client.player == 3 )
							Sence.control4 = false;
						Client.out.reset();
						Client.out.writeInt(Client.MOVE_LEFT);
						Client.out.flush();
						SenceForNetwork.aClick(scene);
					} catch (Exception bozorgExceptionBase) {
						SenceForNetwork.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.D && Sence.control1 == true) {
					try {
						if (Client.player == 0 )
							Sence.control1 = false;
						if (Client.player == 1 )
							Sence.control2 = false;
						if (Client.player == 2 )
							Sence.control3 = false;
						if (Client.player == 3 )
							Sence.control4 = false;
						Client.out.reset();
						Client.out.writeInt(Client.MOVE_RIGHT);
						Client.out.flush();
						SenceForNetwork.aClick(scene);
						System.out.println("DOROST");
					} catch (Exception bozorgExceptionBase) {
						SenceForNetwork.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.S && Sence.control1 == true) {
					try {
						System.out.println("hiiiiiiii");
						System.out.println("player issss " + Client.player);
						if (Client.player == 0 )
							Sence.control1 = false;
						if (Client.player == 1 )
							Sence.control2 = false;
						if (Client.player == 2 )
							Sence.control3 = false;
						if (Client.player == 3 )
							Sence.control4 = false;
						System.out.println("hiiiiiiii");
						Client.out.reset();
						Client.out.writeInt(Client.MOVE_DOWN);
						Client.out.flush();
						System.out.println("hiiiiiiii");
						SenceForNetwork.aClick(scene);
						System.out.println("hiiiiiiii");
						System.out.println("DOROST");
					} catch (Exception bozorgExceptionBase) {
						System.out.println(bozorgExceptionBase);
						SenceForNetwork.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.W && Sence.control1 == true) {
					try {
						if (Client.player == 0 )
							Sence.control1 = false;
						if (Client.player == 1 )
							Sence.control2 = false;
						if (Client.player == 2 )
							Sence.control3 = false;
						if (Client.player == 3 )
							Sence.control4 = false;
						Client.out.reset();
						Client.out.writeInt(Client.MOVE_UP);
						Client.out.flush();
						SenceForNetwork.aClick(scene);
						System.out.println("DOROST");
					} catch (Exception bozorgExceptionBase) {
						SenceForNetwork.aClick(scene);
					}
				}

				if (keyEvent.getCode() == KeyCode.SPACE && Sence.space == true) {
					if (Client.player == 0 )
						Sence.control1 = false;
					if (Client.player == 1 )
						Sence.control2 = false;
					if (Client.player == 2 )
						Sence.control3 = false;
					if (Client.player == 3 )
						Sence.control4 = false;
					Timer timer1 = null;
					if (Client.player == 0)
						timer1 = Draw.attack1(Sence.group, true, 1.0, 1.0);
					if (Client.player == 1)
						timer1 = Draw.attack2(Sence.group, true, 1.0, 1.0);
					if (Client.player == 2)
						timer1 = Draw.attack3(Sence.group, true, 1.0, 1.0);
					if (Client.player == 3)
						timer1 = Draw.attack4(Sence.group, true, 1.0, 1.0);
					Sence.timer = timer1;
					Sence.space = false;
					try {
						Client.out.reset();
						Client.out.writeInt(Client.ATTACK);
						Client.out.flush();
					} catch (Exception bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}
					try {
						Client.out.reset();
						Client.out.writeInt(Client.ATTACK_LEFT);
						Client.out.flush();
					} catch (Exception bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}
					try {
						Client.out.reset();
						Client.out.writeInt(Client.ATTACK_UP);
						Client.out.flush();
					} catch (Exception bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}
					try {
						Client.out.reset();
						Client.out.writeInt(Client.ATTACK_RIGHT);
						Client.out.flush();
					} catch (Exception bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}
					try {
						Client.out.reset();
						Client.out.writeInt(Client.ATTACK_DOWN);
						Client.out.flush();
					} catch (Exception bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}
				}
//				if (keyEvent.getCode() == KeyCode.L && space == true) {
//					Sence.control1 = false;
//					Timer timer1 = null;
//					if (Client.player == 0)
//						timer1 = Draw.attack1(group, true, 1.0, 1.0);
//					if (Client.player == 1)
//						timer1 = Draw.attack2(group, true, 1.0, 1.0);
//					if (Client.player == 2)
//						timer1 = Draw.attack3(group, true, 1.0, 1.0);
//					if (Client.player == 3)
//						timer1 = Draw.attack4(group, true, 1.0, 1.0);
//					timer = timer1;
//					space = false;
//					try {
//						Client.out.reset();
//						Client.out.writeInt(Client.ATTACK_RIGHT);
//						Client.out.flush();
//					} catch (Exception bozorgExceptionBase) {
//						bozorgExceptionBase.printStackTrace();
//					}
//				}
//				if (keyEvent.getCode() == KeyCode.K && space == true) {
//					Sence.control1 = false;
//					Timer timer1 = null;
//					if (Client.player == 0)
//						timer1 = Draw.attack1(group, true, 1.0, 1.0);
//					if (Client.player == 1)
//						timer1 = Draw.attack2(group, true, 1.0, 1.0);
//					if (Client.player == 2)
//						timer1 = Draw.attack3(group, true, 1.0, 1.0);
//					if (Client.player == 3)
//						timer1 = Draw.attack4(group, true, 1.0, 1.0);
//					timer = timer1;
//					space = false;
//					try {
//						Client.out.reset();
//						Client.out.writeInt(Client.ATTACK_DOWN);
//						Client.out.flush();
//					} catch (Exception bozorgExceptionBase) {
//						bozorgExceptionBase.printStackTrace();
//					}
//				}
//				if (keyEvent.getCode() == KeyCode.J && space == true) {
//					Sence.control1 = false;
//					Timer timer1 = null;
//					if (Client.player == 0)
//						timer1 = Draw.attack1(group, true, 1.0, 1.0);
//					if (Client.player == 1)
//						timer1 = Draw.attack2(group, true, 1.0, 1.0);
//					if (Client.player == 2)
//						timer1 = Draw.attack3(group, true, 1.0, 1.0);
//					if (Client.player == 3)
//						timer1 = Draw.attack4(group, true, 1.0, 1.0);
//					timer = timer1;
//					space = false;
//					try {
//						Client.out.reset();
//						Client.out.writeInt(Client.ATTACK_LEFT);
//						Client.out.flush();
//					} catch (Exception bozorgExceptionBase) {
//						bozorgExceptionBase.printStackTrace();
//					}
//				}
//				if (keyEvent.getCode() == KeyCode.I && space == true) {
//					Sence.control1 = false;
//					Timer timer1 = null;
//					if (Client.player == 0)
//						timer1 = Draw.attack1(group, true, 1.0, 1.0);
//					if (Client.player == 1)
//						timer1 = Draw.attack2(group, true, 1.0, 1.0);
//					if (Client.player == 2)
//						timer1 = Draw.attack3(group, true, 1.0, 1.0);
//					if (Client.player == 3)
//						timer1 = Draw.attack4(group, true, 1.0, 1.0);
//					timer = timer1;
//					space = false;
//					try {
//						Client.out.reset();
//						Client.out.writeInt(Client.ATTACK_UP);
//						Client.out.flush();
//					} catch (Exception bozorgExceptionBase) {
//						bozorgExceptionBase.printStackTrace();
//					}
//				}

				if (keyEvent.getCode() == KeyCode.SHIFT) {
					if (Client.player == 0 )
						Sence.control1 = false;
					if (Client.player == 1 )
						Sence.control2 = false;
					if (Client.player == 2 )
						Sence.control3 = false;
					if (Client.player == 3 )
						Sence.control4 = false;
					try {
						Client.out.reset();
						Client.out.writeInt(Client.THROW_FAN);
						Client.out.flush();
					} catch (IOException e) {
						System.out.println(e);
					}
					Draw.drawCell(Player.getPlayerFromNumber(Client.player).getCell(), Sence.group);
					SenceForNetwork.aClick(scene);
					System.out.println("Throwfan!");
				}
				
				if (keyEvent.getCode() == KeyCode.NUMPAD0) {
					if (Client.player == 0 )
						Sence.control1 = false;
					if (Client.player == 1 )
						Sence.control2 = false;
					if (Client.player == 2 )
						Sence.control3 = false;
					if (Client.player == 3 )
						Sence.control4 = false;
					try {
						Client.out.reset();
						Client.out.writeInt(Client.GET_GIFT);
						Client.out.flush();
					} catch (IOException e) {
						System.out.println(e);
					}
					SenceForNetwork.aClick(scene);
					System.out.println("Throwfan!");
				}
				
				if (keyEvent.getCode() == KeyCode.NUMPAD7) {
					if (Client.player == 0 )
						Sence.control1 = false;
					if (Client.player == 1 )
						Sence.control2 = false;
					if (Client.player == 2 )
						Sence.control3 = false;
					if (Client.player == 3 )
						Sence.control4 = false;
					try {
						Client.out.reset();
						Client.out.writeInt(Client.PAUSE);
						Client.out.flush();
					} catch (IOException e) {
						System.out.println(e);
					}
					SenceForNetwork.aClick(scene);
					System.out.println("Throwfan2!");
					// System.out.println(Cell.getCell(0,1).getAllFans().size());
				}
			}
		});
	}

	public static void bClick(Scene scene) {
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.SPACE) {
					Sence.timer.cancel();
					javafx.scene.image.ImageView imageView1 = null;
					if (Client.player == 0 )
						imageView1 = Draw.imageView1;
					if (Client.player == 1 )
						imageView1 = Draw.imageView2;
					if (Client.player == 2 )
						imageView1 = Draw.imageView3;
					if (Client.player == 3 )
						imageView1 = Draw.imageView4;
					
					Sence.group.getChildren().remove(imageView1);
					double x = imageView1.getX();
					double y = imageView1.getY();
					Image image1 = null;
					if (Client.player == 0 )
						image1 = new Image(new Draw().getClass().getResourceAsStream("c1-taajob.png"));
					if (Client.player == 1)
						image1 = new Image(new Draw().getClass().getResourceAsStream("c2-a1-r.png"));
					if (Client.player == 2)
						image1 = new Image(new Draw().getClass().getResourceAsStream("c3-a1-r.png"));
					if (Client.player == 3)
						image1 = new Image(new Draw().getClass().getResourceAsStream("c4-a1-r.png"));
					imageView1 = new javafx.scene.image.ImageView(image1);
					imageView1.setX(x);
					imageView1.setY(y);
					imageView1.setFitHeight(Draw.length);
					imageView1.setFitWidth(Draw.length);
					Sence.group.getChildren().add(imageView1);
					if (Client.player == 0 )
						Draw.imageView1 = imageView1;
					if (Client.player == 1 )
						Draw.imageView2 = imageView1;
					if (Client.player == 2 )
						Draw.imageView3 = imageView1;
					if (Client.player == 3 )
						Draw.imageView4 = imageView1;
					Sence.space = true;
				}
			}
		});

	}

	public static void setGroup(Group group1) {
		Sence.group = group1;
	}

	// public static void setPlayer(Player player1) {
	// player = player1;
	// }
}