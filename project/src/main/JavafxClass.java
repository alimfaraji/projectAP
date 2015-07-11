package main;

import bozorg.common.exceptions.BozorgExceptionBase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import graphics.*;
import source.*;
import bozorg.judge.*;
/**
 * Created by khahsayar on 7/5/2015.
 */
import source.*;
import source.Cell;
import sun.font.TextLabel;

import java.awt.*;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import Network.Client;
import Network.Server;

public class JavafxClass extends Application {
	static Stage stage1;
	static Group group;
	int numberofplayers = 4;
	boolean isshabake = false;
	Scene scene;
	Judge judge;
	static int number = 0;
	final static Player player = Player.getPlayerFromNumber(0);
	static Player player2;
	static Player player3;
	static Player player4;
	static int row = 10;
	static int col = 10;
	GameEngine gameEngine;
	static Button button;

	public void generateMap(int size, int player) {
		System.out.println("size and players is :" +size + " " + player);
		int cells[][] = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				cells[i][j] = JudgeAbstract.NONE_CELL;
		System.out.println("hi");
		int r = Math.abs(new Random().nextInt()) % size;
		int r2 = Math.abs(new Random().nextInt()) % size;
		System.out.println(r + " " + r2);
		cells[r][r2] = JudgeAbstract.JJ_CELL;
		System.out.println("hi");
		int i = 0;
		while (i < player) {
			int rand = Math.abs(new Random().nextInt()) % size;
			int rand2 = Math.abs(new Random().nextInt()) % size;
			if (cells[rand][rand2] != JudgeAbstract.START_CELL && cells[rand][rand2] != JudgeAbstract.JJ_CELL) {
				cells[rand][rand2] = JudgeAbstract.START_CELL;
				i++;
			}
		}
		for (int j = 0; j < size / 4; j++) {
			int rand = Math.abs(new Random().nextInt())% size;
			int rand2 = Math.abs(new Random().nextInt()) % size;
			if (cells[rand][rand2] == JudgeAbstract.NONE_CELL) {
				int rand3 =Math.abs(new Random().nextInt()) % 6 + 4;
				cells[rand][rand2] = rand3;
			}

		}
		System.out.println("hi");

		int walls[][] = new int[size][size];
		walls[0][0] = 1;
		walls[0][1] = 8;
		int[] players = new int[player];
		for ( i = 0; i < player; i++ )
			players[i] = i;
		gameEngine.loadMap(cells, walls, players);
		row = size;
		col = size;
		Draw.row = row;
		Draw.col = row;
		Draw.length = (double)630/row;

	}

	public void start(Stage stage) throws Exception {

		startMenu(stage);
	}

	public void startMenu(final Stage stage) {
		Group group1 = new Group();
		Scene scene1 = new Scene(group1);
		stage.setResizable(false);
		stage.setScene(scene1);
		stage.setWidth(636);
		stage.setHeight(709);
		stage.show();
		scene1.setFill(Color.ORANGE);
		Image image = new Image(new Draw().getClass().getResourceAsStream("clashofclans.jpg"));
		ImageView imageView = new ImageView(image);
		imageView.setX(50);
		imageView.setY(80);
		imageView.setFitHeight(300);
		imageView.setFitWidth(500);
		javafx.scene.control.Button soloGameButton = new Button("SOLO");
		soloGameButton.setFont(new Font(25));
		soloGameButton.setLayoutX(320);
		soloGameButton.setLayoutY(400);
		soloGameButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				String nums = JOptionPane.showInputDialog("enter number of players ");
				try {
					numberofplayers = Integer.parseInt(nums);
				} catch (NumberFormatException e) {
					System.out.println(e);
				}
				String size = JOptionPane.showInputDialog("enter size of Map");
				int sizeOf = 0;
				try {
					sizeOf = Integer.parseInt(size);
				} catch (NumberFormatException e) {
					System.out.println(e);
				}
				System.out.println(numberofplayers);
				game(stage, sizeOf);
			}
		});

		Button networkGameButton = new Button("NETWORK");
		networkGameButton.setFont(new Font(25));
		networkGameButton.setLayoutX(100);
		networkGameButton.setLayoutY(400);
		networkGameButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				networkMenu(stage);
				// network mode
				// TODO
			}
		});

		group1.getChildren().add(soloGameButton);
		group1.getChildren().add(networkGameButton);
		group1.getChildren().add(imageView);
	}

	public void networkMenu(final Stage stage) {
		Group group1 = new Group();
		Scene scene1 = new Scene(group1);
		stage.setResizable(false);
		stage.setScene(scene1);
		stage.setWidth(636);
		stage.setHeight(709);
		stage.show();
		scene1.setFill(Color.ORANGE);

		Button playClientButton = new Button("client");
		playClientButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				int port = 0;
				try {
					String portS = JOptionPane.showInputDialog("enter port ");
					port = Integer.parseInt(portS);
				} catch (NumberFormatException e) {
					System.out.println(e);
				}
				clientGame(stage, port);
			}
		});
		playClientButton.setLayoutX(300);
		playClientButton.setLayoutY(400);
		playClientButton.setFont(new Font(40));
		group1.getChildren().add(playClientButton);

		Button playServerButton = new Button("server");
		playServerButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				String nums = JOptionPane.showInputDialog("enter number of players ");
				try {
					numberofplayers = Integer.parseInt(nums);
				} catch (NumberFormatException e) {
					System.out.println(e);
				}
				String size = JOptionPane.showInputDialog("enter size of Map");
				int sizeOf = 0;
				try {
					sizeOf = Integer.parseInt(size);
				} catch (NumberFormatException e) {
					System.out.println(e);
				}
				System.out.println(numberofplayers);
				serverGame(stage, sizeOf);
			}
		});
		playServerButton.setLayoutX(100);
		playServerButton.setLayoutY(400);
		playServerButton.setFont(new Font(40));
		group1.getChildren().add(playServerButton);
	}

	public void setJudge(Judge judge) {
		this.judge = judge;
	}

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void clickForComboBox(Button button, final ComboBox<String> comboBox) {
		button.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				number = Integer.parseInt(comboBox.getSelectionModel().getSelectedItem().toString()) - 1;
				ControlThread.number = number + 1;
				Draw.drawMap(group);

			}
		});
	}

	public static Player getPlayer() {
		return Player.getPlayerFromNumber(number);
	}

	public static void setGameOverMenu(final Stage stage) {
		Group group1 = new Group();
		Scene scene1 = new Scene(group1);
		stage.setResizable(false);
		stage.setScene(scene1);
		stage.setWidth(636);
		stage.setHeight(709);
		stage.show();
		scene1.setFill(Color.ORANGE);

		Text text = new Text("and the winner is : " + Player.getWinner().getName()+1);
		text.setLayoutX(10);
		text.setLayoutY(400);
		text.setFont(new Font(20));
		group1.getChildren().add(text);

	}
	
	TextField serverField = null;
	Server server = null;
	
	

	public void serverGame(final Stage stage, int sizeOfMap) {
		Group group1 = new Group();
		Scene scene1 = new Scene(group1);
		stage.setResizable(false);
		stage.setScene(scene1);
		stage.setWidth(636);
		stage.setHeight(709);
		stage.show();
		scene1.setFill(Color.ORANGE);

		GameEngine engine = new GameEngine();
		setGameEngine(engine);
		server = null;
		try {
			server = new Server(numberofplayers, engine);
		} catch (IOException e) {
			System.out.println("wooooow");
			System.out.println(e);
		}
		final int port = server.getPort();

		generateMap(sizeOfMap, numberofplayers);

		Button playAsClient = new Button("play game as a client");
		playAsClient.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				clientGame(stage, port);
			}
		});
		playAsClient.setLayoutX(100);
		playAsClient.setLayoutY(400);
		playAsClient.setFont(new Font(40));
		group1.getChildren().add(playAsClient);

		// server panel ( for kick)
		JFrame serverFrame = new JFrame("server panel");
		serverFrame.pack();
		serverFrame.setSize(160, 90);
		serverFrame.setPreferredSize(new Dimension(160, 90));
		// serverFrame.setDefaultCloseOperation(JserverFrame.EXIT_ON_CLOSE);
		serverFrame.setVisible(true);
		serverFrame.setResizable(false);

		JPanel serverPanel = new JPanel(new BorderLayout());
		serverFrame.getContentPane().add(serverPanel);
		serverFrame.add(serverPanel, BorderLayout.CENTER);

		JLabel jlabel = new JLabel("select player to kick ");
		serverPanel.add(jlabel, BorderLayout.PAGE_START);

		JLabel showPort = new JLabel("your port is " + server.getPort());
		serverPanel.add(showPort, BorderLayout.PAGE_END);

		serverField = new TextField(10);
		serverPanel.add(serverField, BorderLayout.WEST);

		JButton serverKickButton = new JButton("kick");
		serverKickButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				server.kick(Integer.parseInt(serverField.getText()));
			}
		});
		serverPanel.add(serverKickButton, BorderLayout.EAST);
	}

	TextField fieldForChat = null;
	Client client = null;

	public void clientGame(Stage stage, int port) {
		setGameEngine(new GameEngine());

		try {
			client = new Client("localhost", port, gameEngine);
		} catch (IOException e) {
			System.out.println(e);
			 startMenu(stage);
			 return;
			// TODO
		}

		stage1 = stage;
		// System.out.println("first"+row);
		// player=Player.getPlayerFromNumber(0);
		player2 = Player.getPlayerFromNumber(1);
		player3 = Player.getPlayerFromNumber(2);
		player4 = Player.getPlayerFromNumber(3);

		Draw.setSize(stage.getWidth(), stage.getHeight(), col, row);
		// setsize();
		stage.setWidth(636);
		stage.setHeight(709);
		group = new Group();
		scene = new Scene(group);
		scene.setFill(Color.YELLOWGREEN);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		Sence.setGroup(group);
		Draw.setGameEngine(gameEngine);
		Draw.setGroup(group);
		Draw.drawToolbar();
		button = new Button("Vision!");
		button.setFont(new Font(10));
		button.setLayoutX(550);
		button.setLayoutY(30);

		// HIIIII
		// int players[] = new int[numberofplayers];
		// for (int i = 0; i < numberofplayers; i++ )
		// players[i] = i;
		int[] players = { 0 };
		// TODO
		// int[][] celltype =
		// {
		// { 0 , 0 , 1 , 0 , 3 } ,
		// { 0 , 0 , 3 , 4 , 0 } ,
		// { 9 , 3 , 8 , 5 , 0 } ,
		// { 3 , 5 , 4 , 0 , 6 } ,
		// { 0 , 0 , 0 , 0 , 0 }
		// };
		//
		// int[][] walltype =
		// {
		// { 9 , 5 , 1 , 1 , 3 } ,
		// { 12, 1 , 0 , 0 , 2 } ,
		// { 9 , 0 , 0 , 0 , 2 } ,
		// { 8 , 0 , 0 , 6 , 10} ,
		// { 12, 4 , 4 , 5 , 6 }
		// };
		int celltype[][] = { { 6, 0, 0, 0, 3, 1, 0, 0, 0, 0 }, { 0, 6, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 6, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 7, 0, 0, 7, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 7, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
		int walltype[][] = { { 15, 8, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
		// TODO set loadMapGenerator
		gameEngine.loadMap(celltype, walltype, players);
		row = celltype.length;
		col = celltype[0].length;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				// System.out.println(Cell.getCell(i,j).getCol());
				Draw.drawCell(Cell.getCell(i, j), group);
			}
		}

		Khashi khashi = new Khashi();
		khashi.setRow(row);
		khashi.setCol(col);
		khashi.setStage(stage1);
		khashi.setGroup(group);
		khashi.setEngine(gameEngine);
		khashi.start();

		ListenThreadForNetwork listenThread = new ListenThreadForNetwork();
		listenThread.setScene(scene);
		listenThread.setGroup(group);
		listenThread.setPlayer(player);
		listenThread.start();

		ControlThread controlThread = new ControlThread();
		controlThread.setPlayer(Player.getPlayerFromNumber(0));
		controlThread.setPlayer(player);
		controlThread.setGroup(group);
		controlThread.setGameEngine(gameEngine);
		controlThread.start();
		System.out.println("afteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

		DrawJoonThread drawJoonThread = new DrawJoonThread();
		drawJoonThread.setGroup(group);
		drawJoonThread.start();

		// JavafxClass.setPlayer(Player.getPlayerFromNumber(0));
		// HIII

		// chat
		JFrame frame = new JFrame("chat box");
		frame.pack();
		frame.setSize(600, 200);
		frame.setPreferredSize(new Dimension(600, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);

		JPanel panel = new JPanel(new BorderLayout());
		frame.getContentPane().add(panel);
		frame.add(panel, BorderLayout.PAGE_END);

		fieldForChat = new TextField("enter chat");
		panel.add(fieldForChat, BorderLayout.CENTER);

		JButton chatButton = new JButton("normal Chat");
		chatButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("enter chat");
				client.sendMessage(fieldForChat.getText());
			}
		});
		panel.add(chatButton, BorderLayout.WEST);

		JButton MChatButton = new JButton("multi Chat");
		MChatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Menter chat");
				client.sendFile(fieldForChat.getText());
			}
		});
		panel.add(MChatButton, BorderLayout.EAST);

	}

	public void game(Stage stage, int sizeOfMap) {
		Button button;
		stage1 = stage;
		// System.out.println("first"+row);
		// player=Player.getPlayerFromNumber(0);
		player2 = Player.getPlayerFromNumber(1);
		player3 = Player.getPlayerFromNumber(2);
		player4 = Player.getPlayerFromNumber(3);

		setGameEngine(new GameEngine());
		Draw.setSize(stage.getWidth(), stage.getHeight(), col, row);
		// setsize();
		stage.setWidth(636);
		stage.setHeight(709);
		group = new Group();
		scene = new Scene(group);
		scene.setFill(Color.YELLOWGREEN);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		Sence.setGroup(group);
		Draw.setGameEngine(gameEngine);
		Draw.setGroup(group);
		Draw.drawToolbar();
		button = new Button("Vision!");
		button.setFont(new Font(10));
		button.setLayoutX(550);
		button.setLayoutY(30);
		ComboBox comboBox = null;

		ObservableList<String> options = FXCollections.observableArrayList("1", "2", "3", "4");
		comboBox = new ComboBox(options);
		comboBox.setLayoutY(1);
		comboBox.setLayoutX(550);
		group.getChildren().add(comboBox);
		group.getChildren().add(button);
		clickForComboBox(button, comboBox);

		Button pausebutton = new Button("pause");
		pausebutton.setLayoutX(400);
		pausebutton.setLayoutY(5);
		group.getChildren().add(pausebutton);
		pausebutton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
			boolean pause = true;

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (pause)
					gameEngine.setPaused(true);
				else
					gameEngine.setPaused(false);
				pause = !pause;
			}
		});
		System.out.println("i'm before generate map");
		generateMap(sizeOfMap, numberofplayers);
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				// System.out.println(Cell.getCell(i,j).getCol());
				Draw.drawCell(Cell.getCell(i, j), group);
			}
		}

		Khashi khashi = new Khashi();
		khashi.setRow(row);
		khashi.setCol(col);
		khashi.setStage(stage1);
		khashi.setGroup(group);
		khashi.setEngine(gameEngine);
		khashi.start();

		ListenThread listenThread = new ListenThread();
		listenThread.setScene(scene);
		listenThread.setGroup(group);
		listenThread.setPlayer(player);
		listenThread.start();

		ControlThread controlThread = new ControlThread();
		controlThread.setPlayer(Player.getPlayerFromNumber(0));
		controlThread.setPlayer(player);
		controlThread.setGroup(group);
		controlThread.setGameEngine(gameEngine);
		controlThread.start();
		System.out.println("afteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

		DrawJoonThread drawJoonThread = new DrawJoonThread();
		drawJoonThread.setGroup(group);
		drawJoonThread.start();

		// JavafxClass.setPlayer(Player.getPlayerFromNumber(0));
	}
}

class Khashi extends Thread {
	static Stage stage;
	public static Integer row1;
	public static Integer col1;
	static Group group;
	static boolean drawmap = false;
	static GameEngine gameEngine1;

	public void run() {
		Sence.aClick(stage.getScene());
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// System.out.println("r");
						// if(Sence.getControl()) {
						for (int i = 0; i < row1; i++) {
							for (int j = 0; j < col1; j++) {
								// System.out.println("lll");
								if (Cell.getCell(i, j).getAllPlayers() != null
										&& Cell.getCell(i, j).getAllPlayers().size() != 0) {
									for (int k = 0; k < Cell.getCell(i, j).getAllPlayers().size(); k++) {
										if (Cell.getCell(i, j).getAllPlayers().get(k).isAlive()) {
											if (Cell.getCell(i, j).getAllPlayers().get(k).getName() == Judge.SAMAN) {
												Draw.move1(group, Cell.getCell(i, j).getAllPlayers().get(k));
											}
											if (Cell.getCell(i, j).getAllPlayers().get(k).getName() == 1) {
												Draw.move2(group, Cell.getCell(i, j).getAllPlayers().get(k));
											}
											if (Cell.getCell(i, j).getAllPlayers().get(k).getName() == 2) {
												Draw.move3(group, Cell.getCell(i, j).getAllPlayers().get(k));
											}
											if (Cell.getCell(i, j).getAllPlayers().get(k).getName() == 3) {
												Draw.move4(group, Cell.getCell(i, j).getAllPlayers().get(k));
											}
										}
									}
								}

							}
						}
						// }
						if (gameEngine1 != null && !gameEngine1.isPaused())
							gameEngine1.next50milis();
						if(Player.gameIsOver()){
							JavafxClass.setGameOverMenu(stage);
						}
					}
				});
			}
		}, 1000, 50);
	}

	public static void setDrawmap(boolean bool) {
		drawmap = bool;
	}

	public void setRow(int row) {
		row1 = row;
	}

	public void setCol(int col) {
		col1 = col;
	}

	public void setStage(Stage stage1) {
		stage = stage1;
	}

	public void setGroup(Group group1) {
		group = group1;
	}

	public void setEngine(GameEngine gameEngine) {
		gameEngine1 = gameEngine;
	}
}

class ListenThreadForNetwork extends Thread {
	private Scene scene1;
	private Group group;
	private Player player;

	public void run() {

		SenceForNetwork.aClick(scene1);
		Sence.setGroup(group);
		Sence.setPlayer(player);
		SenceForNetwork.bClick(scene1);
		System.out.println("finishhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
	}

	public void setScene(Scene scene) {
		scene1 = scene;
	}

	public void setGroup(Group group1) {
		group = group1;
	}

	public void setPlayer(Player player1) {
		player = player1;
	}
}

class ListenThread extends Thread {
	private Scene scene1;
	private Group group;
	private Player player;

	public void run() {

		Sence.aClick(scene1);
		Sence.setGroup(group);
		Sence.setPlayer(player);
		Sence.bClick(scene1);
		System.out.println("finishhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
	}

	public void setScene(Scene scene) {
		scene1 = scene;
	}

	public void setGroup(Group group1) {
		group = group1;
	}

	public void setPlayer(Player player1) {
		player = player1;
	}
}

class ControlThread extends Thread {
	int dir1;

	int counter1 = 0;
	int counter2 = 0;
	int counter3 = 0;
	int counter4 = 0;

	boolean avalie = false;
	boolean avalie2 = false;
	boolean avalie3 = false;
	boolean avalie4 = false;
	boolean avalie1 = avalie2;

	boolean control1 = false;
	boolean control2 = false;
	boolean control3 = false;
	boolean control4 = false;

	Group group;
	Player player1;
	Player player2;
	Player player3;
	Player player4;

	static int number = 1;

	int counterbonus1 = 0;
	int counterbonus2 = 0;
	int counterbonus3 = 0;
	int counterbonus4 = 0;

	GameEngine gameEngine;

	public void run() {
		player1 = Player.getPlayerFromNumber(0);
		player2 = Player.getPlayerFromNumber(1);
		player3 = Player.getPlayerFromNumber(2);
		player4 = Player.getPlayerFromNumber(3);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (player1.isMoving()) {
							control1 = false;
							avalie = true;
							Khashi.setDrawmap(false);
						}
						if (!player1.isMoving()) {
							if (control1)
								Sence.setControl(true);
							if (control1 == false) {

								try {
									if (gameEngine.getMapCellType(player1.getCell().getCol(),
											player1.getCell().getRow()) == 4) {
										DrawJoonThread.setIsdrawspeed(0);

									}
									if (gameEngine.getMapCellType(player1.getCell().getCol(),
											player1.getCell().getRow()) == 5) {
										DrawJoonThread.setIsdrawradar(0);
									}
									if (gameEngine.getMapCellType(player1.getCell().getCol(),
											player1.getCell().getRow()) == 7) {
										DrawJoonThread.setIsdrawjump(0);
									}
									if (gameEngine.getMapCellType(player1.getCell().getCol(),
											player1.getCell().getRow()) == 9) {
										DrawJoonThread.setIsdrawhealth(0);
									}
									player1.getGift();
									Draw.drawCell(player1.getCell(), group);
									// Draw.drawCell(player1.getCell(),group);
									System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooo");
								} catch (BozorgExceptionBase bozorgExceptionBase) {
									bozorgExceptionBase.printStackTrace();
									control1 = true;
								}

								if (player1.getHasStonedBonus() > 0 && counter1 == 0) {
									counter1++;
									// Draw.setPrison1(true);
									// Draw.drawCell(player1.getCell(),group);
								}
								// Draw.drawCell(player1.getCell(),group);

							}
							if (counter1 != 0)
								counter1++;
							if (counter1 == 5) {
								Draw.setPrison1(true);
								Draw.drawCell(player1.getCell(), group);
								counter1 = -1;
							}
							// if(counter1==0){
							// if(player1.getHasStonedBonus()==0)
							// Draw.setPrison1(false);
							// }

							// System.out.println(gameEngine.getMapCellType(1,
							// 1, player1));
						}
						if (player1.getHasStonedBonus() == 0)
							Draw.setPrison1(false);

						if ((!player1.isAttacking()) && (Sence.getSpace() == false)) {
							try {
								player1.attack(0);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player1.attack(1);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player1.attack(2);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player1.attack(3);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player1.attack(4);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}
						}

						if (JavafxClass.getPlayer().isCellInVision(player1.getCell())) {
							Draw.dark1 = false;
						}

						// System.out.println(Player.getPlayerFromNumber(0).getHealth());

						if (number == 1) {
							if (!JavafxClass.getPlayer().isMoving() && avalie) {
								System.out.println("avalie");
								avalie = false;
								System.out.println(JavafxClass.getPlayer().getName());
								// player1.updateCellsInVision();
								ArrayList<Cell> arrayList = JavafxClass.getPlayer().getNewNonDarkCells();
								for (int i = 0; i < arrayList.size(); i++) {
									Draw.drawCell(arrayList.get(i), group);
								}
								ArrayList<Cell> arrayList2 = JavafxClass.getPlayer().getNewDarkCells();
								for (int i = 0; i < arrayList2.size(); i++) {
									Draw.drawCell(arrayList2.get(i), group);
								}
								JavafxClass.getPlayer().updateCellsInVision();
								// Khashi.setDrawmap(true);
								// DrawMapThread drawMapThread=new
								// DrawMapThread();
								// drawMapThread.setGroup(group);
								// drawMapThread.start();
								// Draw.drawMap(group);
							}
						}
						if (number == 2) {
							if (!JavafxClass.getPlayer().isMoving() && avalie2) {
								System.out.println("avalie2");
								avalie2 = false;
								// player1.updateCellsInVision();
								// System.out.println("av");
								ArrayList<Cell> arrayList = JavafxClass.getPlayer().getNewNonDarkCells();
								for (int i = 0; i < arrayList.size(); i++) {
									Draw.drawCell(arrayList.get(i), group);
								}
								ArrayList<Cell> arrayList2 = JavafxClass.getPlayer().getNewDarkCells();
								for (int i = 0; i < arrayList2.size(); i++) {
									Draw.drawCell(arrayList2.get(i), group);
								}
								JavafxClass.getPlayer().updateCellsInVision();
							}
						}
						if (number == 3) {
							if (!JavafxClass.getPlayer().isMoving() && avalie3) {
								System.out.println("avalie3");
								avalie3 = false;
								// player1.updateCellsInVision();
								ArrayList<Cell> arrayList = JavafxClass.getPlayer().getNewNonDarkCells();
								for (int i = 0; i < arrayList.size(); i++) {
									Draw.drawCell(arrayList.get(i), group);
								}
								ArrayList<Cell> arrayList2 = JavafxClass.getPlayer().getNewDarkCells();
								for (int i = 0; i < arrayList2.size(); i++) {
									Draw.drawCell(arrayList2.get(i), group);
								}
								JavafxClass.getPlayer().updateCellsInVision();
							}
						}
						if (number == 4) {
							if (!JavafxClass.getPlayer().isMoving() && avalie4) {
								System.out.println("avalie4");
								avalie4 = false;
								// player1.updateCellsInVision();
								ArrayList<Cell> arrayList = JavafxClass.getPlayer().getNewNonDarkCells();
								for (int i = 0; i < arrayList.size(); i++) {
									Draw.drawCell(arrayList.get(i), group);
								}
								ArrayList<Cell> arrayList2 = JavafxClass.getPlayer().getNewDarkCells();
								for (int i = 0; i < arrayList2.size(); i++) {
									Draw.drawCell(arrayList2.get(i), group);
								}
								JavafxClass.getPlayer().updateCellsInVision();
							}
						}

						// System.out.println(Player.getPlayerFromNumber(0).getHealth());
						// System.out.println(Player.getPlayerFromNumber(1).getHealth());

						// player2

						if (player2.isMoving()) {
							control2 = false;
							avalie2 = true;
						}
						if (!player2.isMoving()) {
							if (control2)
								Sence.control2 = true;
							if (control2 == false) {

								try {
									if (gameEngine.getMapCellType(player2.getCell().getCol(),
											player2.getCell().getRow()) == 4) {
										DrawJoonThread.setIsdrawspeed(0);

									}
									if (gameEngine.getMapCellType(player2.getCell().getCol(),
											player2.getCell().getRow()) == 5) {
										DrawJoonThread.setIsdrawradar(0);
									}
									if (gameEngine.getMapCellType(player2.getCell().getCol(),
											player2.getCell().getRow()) == 7) {
										DrawJoonThread.setIsdrawjump(0);
									}
									if (gameEngine.getMapCellType(player2.getCell().getCol(),
											player2.getCell().getRow()) == 9) {
										DrawJoonThread.setIsdrawhealth(0);
										System.out.println("health");
									}
									player2.getGift();
									Draw.drawCell(player2.getCell(), group);
									// Draw.drawCell(player1.getCell(),group);
									System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooo");
								} catch (BozorgExceptionBase bozorgExceptionBase) {
									bozorgExceptionBase.printStackTrace();
									control2 = true;
								}

								if (player2.getHasStonedBonus() > 0 && counter2 == 0) {
									counter2++;
									// Draw.setPrison1(true);
									// Draw.drawCell(player1.getCell(),group);
								}
								// Draw.drawCell(player1.getCell(),group);

							}
							if (counter2 != 0)
								counter2++;
							if (counter2 == 15) {
								Draw.prison2 = true;
								Draw.drawCell(player2.getCell(), group);
								counter2 = -1;
							}
							// if(counter1==0){
							// if(player1.getHasStonedBonus()==0)
							// Draw.setPrison1(false);
							// }

							// System.out.println(gameEngine.getMapCellType(1,
							// 1, player1));
						}
						if (player2.getHasStonedBonus() == 0)
							Draw.prison2 = false;

						if ((!player2.isAttacking()) && (Sence.getSpace2() == false)) {
							try {
								player2.attack(0);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player2.attack(1);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player2.attack(2);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player2.attack(3);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player2.attack(4);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}
						}

						if (JavafxClass.getPlayer().isCellInVision(player2.getCell())) {
							Draw.dark2 = false;
						}
						if (JavafxClass.getPlayer().isCellInVision(player3.getCell())) {
							Draw.dark3 = false;
						}
						if (JavafxClass.getPlayer().isCellInVision(player4.getCell())) {
							Draw.dark4 = false;
						}
						// System.out.println(Player.getPlayerFromNumber(1).getHealth());

						// if(!player2.isMoving() && avalie2 ){
						// System.out.println("avalie2");
						// avalie2=false;
						// //player1.updateCellsInVision();
						// ArrayList<Cell>
						// arrayList=player2.getNewNonDarkCells();
						// for(int i=0;i<arrayList.size();i++){
						// Draw.drawCell(arrayList.get(i),group);
						// }
						// ArrayList<Cell> arrayList2=player2.getNewDarkCells();
						// for(int i=0;i<arrayList2.size();i++){
						// Draw.drawCell(arrayList2.get(i),group);
						// }
						// player2.updateCellsInVision();
						// // Khashi.setDrawmap(true);
						//// DrawMapThread drawMapThread=new DrawMapThread();
						//// drawMapThread.setGroup(group);
						//// drawMapThread.start();
						// //Draw.drawMap(group);
						// }

						// player3
						if (player3.isMoving()) {
							control3 = false;
							avalie3 = true;
							Khashi.setDrawmap(false);
						}
						if (!player3.isMoving()) {
							if (control3)
								Sence.control3 = true;
							if (control3 == false) {

								try {
									if (gameEngine.getMapCellType(player3.getCell().getCol(),
											player3.getCell().getRow()) == 4) {
										DrawJoonThread.setIsdrawspeed(0);

									}
									if (gameEngine.getMapCellType(player3.getCell().getCol(),
											player3.getCell().getRow()) == 5) {
										DrawJoonThread.setIsdrawradar(0);
									}
									if (gameEngine.getMapCellType(player3.getCell().getCol(),
											player3.getCell().getRow()) == 7) {
										DrawJoonThread.setIsdrawjump(0);
									}
									if (gameEngine.getMapCellType(player3.getCell().getCol(),
											player3.getCell().getRow()) == 9) {
										DrawJoonThread.setIsdrawhealth(0);
									}
									player3.getGift();
									Draw.drawCell(player3.getCell(), group);
									// Draw.drawCell(player1.getCell(),group);
									System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooo");
								} catch (BozorgExceptionBase bozorgExceptionBase) {
									bozorgExceptionBase.printStackTrace();
									control3 = true;
								}

								if (player3.getHasStonedBonus() > 0 && counter3 == 0) {
									counter3++;
									// Draw.setPrison1(true);
									// Draw.drawCell(player1.getCell(),group);
								}
								// Draw.drawCell(player1.getCell(),group);

							}
							if (counter3 != 0)
								counter3++;
							if (counter3 == 5) {
								Draw.prison3 = true;
								Draw.drawCell(player3.getCell(), group);
								counter3 = -1;
							}
							// if(counter1==0){
							// if(player1.getHasStonedBonus()==0)
							// Draw.setPrison1(false);
							// }

							// System.out.println(gameEngine.getMapCellType(1,
							// 1, player1));
						}
						if (player3.getHasStonedBonus() == 0)
							Draw.prison3 = false;

						if ((!player3.isAttacking()) && (Sence.getSpace3() == false)) {
							try {
								player3.attack(0);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player3.attack(1);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player3.attack(2);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player3.attack(3);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player3.attack(4);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}
						}
						// System.out.println(Player.getPlayerFromNumber(0).getHealth());

						// if(!player3.isMoving() && avalie3 ){
						// System.out.println("avalie");
						// avalie=false;
						// //player1.updateCellsInVision();
						// ArrayList<Cell>
						// arrayList=player3.getNewNonDarkCells();
						// for(int i=0;i<arrayList.size();i++){
						// Draw.drawCell(arrayList.get(i),group);
						// }
						// ArrayList<Cell> arrayList2=player3.getNewDarkCells();
						// for(int i=0;i<arrayList2.size();i++){
						// Draw.drawCell(arrayList2.get(i),group);
						// }
						// player3.updateCellsInVision();
						// // Khashi.setDrawmap(true);
						//// DrawMapThread drawMapThread=new DrawMapThread();
						//// drawMapThread.setGroup(group);
						//// drawMapThread.start();
						// //Draw.drawMap(group);
						// }

						// System.out.println(Player.getPlayerFromNumber(0).getHealth());
						// System.out.println(Player.getPlayerFromNumber(1).getHealth());

						// player4
						if (player4.isMoving()) {
							control4 = false;
							avalie4 = true;
							Khashi.setDrawmap(false);
						}
						if (!player4.isMoving()) {
							if (control4)
								Sence.control4 = true;
							if (control4 == false) {
								// System.out.println("health");
								try {
									if (gameEngine.getMapCellType(player4.getCell().getCol(),
											player4.getCell().getRow()) == 4) {
										DrawJoonThread.setIsdrawspeed(0);

									}
									if (gameEngine.getMapCellType(player4.getCell().getCol(),
											player4.getCell().getRow()) == 5) {
										DrawJoonThread.setIsdrawradar(0);
									}
									if (gameEngine.getMapCellType(player4.getCell().getCol(),
											player4.getCell().getRow()) == 7) {
										DrawJoonThread.setIsdrawjump(0);
									}
									if (gameEngine.getMapCellType(player4.getCell().getCol(),
											player4.getCell().getRow()) == 9) {
										DrawJoonThread.setIsdrawhealth(0);
										System.out.println("health");
									}
									player4.getGift();
									Draw.drawCell(player4.getCell(), group);
									// Draw.drawCell(player1.getCell(),group);
									System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooo");
								} catch (BozorgExceptionBase bozorgExceptionBase) {
									bozorgExceptionBase.printStackTrace();
									control4 = true;
								}

								if (player4.getHasStonedBonus() > 0 && counter4 == 0) {
									counter4++;
									// Draw.setPrison1(true);
									// Draw.drawCell(player1.getCell(),group);
								}
								// Draw.drawCell(player1.getCell(),group);

							}
							if (counter4 != 0)
								counter4++;
							if (counter4 == 5) {
								Draw.prison4 = true;
								Draw.drawCell(player4.getCell(), group);
								counter4 = -1;
							}
							// if(counter1==0){
							// if(player1.getHasStonedBonus()==0)
							// Draw.setPrison1(false);
							// }

							// System.out.println(gameEngine.getMapCellType(1,
							// 1, player1));
						}
						if (player4.getHasStonedBonus() == 0)
							Draw.prison4 = false;

						if ((!player4.isAttacking()) && (Sence.getSpace4() == false)) {
							try {
								player4.attack(0);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player4.attack(1);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player4.attack(2);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player4.attack(3);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}

							try {
								player4.attack(4);
							} catch (BozorgExceptionBase bozorgExceptionBase) {
								bozorgExceptionBase.printStackTrace();
							}
						}
						// System.out.println(Player.getPlayerFromNumber(0).getHealth());

						// if(!player4.isMoving() && avalie4 ){
						// System.out.println("avalie");
						// avalie4=false;
						// //player1.updateCellsInVision();
						// ArrayList<Cell>
						// arrayList=player4.getNewNonDarkCells();
						// for(int i=0;i<arrayList.size();i++){
						// Draw.drawCell(arrayList.get(i),group);
						// }
						// ArrayList<Cell> arrayList2=player4.getNewDarkCells();
						// for(int i=0;i<arrayList2.size();i++){
						// Draw.drawCell(arrayList2.get(i),group);
						// }
						// player4.updateCellsInVision();
						// // Khashi.setDrawmap(true);
						//// DrawMapThread drawMapThread=new DrawMapThread();
						//// drawMapThread.setGroup(group);
						//// drawMapThread.start();
						// //Draw.drawMap(group);
						// }

						// System.out.println(Player.getPlayerFromNumber(0).getHealth());
						// System.out.println(Player.getPlayerFromNumber(1).getHealth());

						// player2

					}
				});
			}
		}, 1, 20);

	}

	public void setPlayer(Player player) {
		player1 = player;
	}

	public void setDir1(int dir) {
	}

	public void setGameEngine(GameEngine gameEngine1) {
		gameEngine = gameEngine1;
	}

	public void setGroup(Group group1) {
		group = group1;
	}
}

class DrawJoonThread extends Thread {
	static Group group;
	static int isdrawspeed = -1;
	static int isdrawjump = -1;
	static int isdrawradar = -1;
	static int isdrawhealth = -1;
	static ImageView imageViewspeed;
	static ImageView imageViewradar;
	static ImageView imageViewhealth;
	static ImageView imageViewjump;

	boolean radar = false;
	boolean health = false;
	boolean jump = false;
	boolean spped = false;

	static {
		imageViewradar = new ImageView(Draw.getRadar());
		imageViewspeed = new ImageView(Draw.getSpeed());
		imageViewhealth = new ImageView(Draw.getHealth());
		imageViewradar.setX(570);
		imageViewradar.setY(1);
		imageViewradar.setFitHeight(50);
		imageViewradar.setFitWidth(50);
	}

	public void run() {

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Draw.drawToolbar();
						// System.out.println("toolbar!");
						// System.out.println((double)
						// Player.getPlayerFromNumber(0).getHealth() / (double)
						// 100);
						if (isdrawhealth >= 10) {
							isdrawhealth = -1;
							group.getChildren().remove(imageViewhealth);
							group.getChildren().remove(imageViewhealth);
							health = false;

						}
						if (isdrawjump >= 10) {
							isdrawjump = -1;
							group.getChildren().remove(imageViewjump);
							jump = false;
						}
						if (isdrawradar >= 14) {
							isdrawradar = -1;
							group.getChildren().remove(imageViewradar);
							// Draw.drawMap(group);
							// ArrayList<Cell>
							// arrayList=JavafxClass.getPlayer().getNewNonDarkCells();
							// for(int i=0;i<arrayList.size();i++){
							// Draw.drawCell(arrayList.get(i),group);
							// }
							// ArrayList<Cell>
							// arrayList2=JavafxClass.getPlayer().getNewDarkCells();
							// for(int i=0;i<arrayList2.size();i++){
							// Draw.drawCell(arrayList2.get(i),group);
							// }
							// JavafxClass.getPlayer().updateCellsInVision();
							radar = false;
						}
						if (isdrawspeed >= 10) {
							isdrawspeed = -1;
							group.getChildren().remove(imageViewspeed);

							spped = false;
						}

						if (isdrawhealth == 0 && !health) {
							drawhealth();
							health = true;
						}
						if (isdrawjump == 0 && !jump) {
							drawjump();
							jump = true;
						}
						if (isdrawradar == 0 && !radar) {
							drawradar();
							radar = true;
						}
						if (isdrawspeed == 0 && !spped) {
							drawspeed();
							spped = true;
						}
						if (isdrawhealth >= 0)
							isdrawhealth++;
						if (isdrawjump >= 0)
							isdrawjump++;
						if (isdrawradar >= 0)
							isdrawradar++;
						if (isdrawspeed >= 0)
							isdrawspeed++;

						for (int i = 0; i < Fan.getAllFans().size(); i++) {
							if (!Fan.getAllFans().get(i).isAlive()) {
								Draw.drawCell(Fan.getAllFans().get(i).getCell(), group);
								Fan.getAllFans().remove(i);
							}
						}
						for (int i = 0; i < Player.getAllPlayers().size(); i++) {
							if (!Player.getAllPlayers().get(i).isAlive()) {
								Draw.drawCell(Player.getAllPlayers().get(i).getCell(), group);
								if (i == 0) {
									group.getChildren().remove(Draw.getImageView1());
								}
								if (i == 1) {
									group.getChildren().remove(Draw.getImageView2());
								}
								if (i == 2) {
									group.getChildren().remove(Draw.getImageView3());
								}
								if (i == 3) {
									group.getChildren().remove(Draw.getImageView4());
								}
							}
						}
					}
				});
			}
		}, 100, 200);
	}

	public void setGroup(Group group1) {
		group = group1;
	}

	public static void drawspeed() {
		System.out.println("drawspeed");
		// imageViewspeed=new ImageView(Draw.getSpeed());
		imageViewspeed.setX(15);
		imageViewspeed.setY(10);
		imageViewspeed.setFitHeight(30);
		imageViewspeed.setFitWidth(30);
		group.getChildren().add(imageViewspeed);
	}

	public static void drawradar() {
		// imageViewradar=new ImageView(Draw.getRadar());
		// imageViewradar.setX(570);
		// imageViewradar.setY(1);
		// imageViewradar.setFitHeight(50);
		// imageViewradar.setFitWidth(50);
		group.getChildren().add(imageViewradar);
	}

	public static void drawhealth() {
		// imageViewhealth=new ImageView(Draw.getHealth());
		imageViewhealth.setX(15);
		imageViewhealth.setY(10);
		imageViewhealth.setFitHeight(30);
		imageViewhealth.setFitWidth(30);
		group.getChildren().add(imageViewhealth);
	}

	public static void drawjump() {
		imageViewjump = new ImageView(Draw.getJump());
		imageViewjump.setX(15);
		imageViewjump.setY(10);
		imageViewjump.setFitHeight(30);
		imageViewjump.setFitWidth(30);
		group.getChildren().add(imageViewjump);
	}

	public static void setIsdrawspeed(int bool) {
		isdrawspeed = bool;
	}

	public static void setIsdrawjump(int bool) {
		isdrawjump = bool;
	}

	public static void setIsdrawradar(int bool) {
		isdrawradar = bool;
	}

	public static void setIsdrawhealth(int bool) {
		isdrawhealth = bool;
	}
}