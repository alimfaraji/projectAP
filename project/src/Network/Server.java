package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import bozorg.common.exceptions.NoSavedGameToLoad;
import bozorg.judge.JudgeAbstract;
import source.GameEngine;
import source.Player;

public class Server extends Thread {
	public static int TIME_OUT = 10000;
	private int numOfPlayers;
	private ServerSocket serverSocket;
	private Socket[] socket;
	private GameEngine engine;
	private HashMap<Socket, Integer> players;

	// output netWork
	private ObjectOutputStream[] out;

	// input netWork
	private ObjectInputStream[] in;

	// input and output for save and load:
	private ObjectOutputStream outputForSave = new ObjectOutputStream(new FileOutputStream("saved.txt"));
	private ObjectInputStream inputForSave = new ObjectInputStream(new FileInputStream("saved.txt"));

	/**
	 * save game should be called in GamePanel of Server
	 */
	public void saveGame() {
		try {
			outputForSave.reset();
			outputForSave.writeObject(engine);
			outputForSave.flush();
			//TODO no finished
		} catch (IOException e) {
			// handle exception
		}
	}

	/**
	 * load last saved game,
	 * 
	 * @throws noSavedGameToLoad
	 *             if there's no saved game
	 */
	public void loadGame() throws NoSavedGameToLoad{
		try{
			engine = (GameEngine)inputForSave.readObject();
		}catch(Exception e){
			//handle exception
		}
	}

	public Server(int numOfPlayers, GameEngine engine) throws IOException {
		// when the
		// user of
		// server PC
		// click on a button which lead
		// to network mode sth like
		// joptionpane.inputDialoge ask
		// for the number of players,
		// then a new server will be
		// created .
		this.engine = engine;
		serverSocket = new ServerSocket(0);
		serverSocket.setSoTimeout(TIME_OUT);
		this.numOfPlayers = numOfPlayers;
		socket = new Socket[numOfPlayers];
	}

	public void run() {
		for (int i = 0; i < numOfPlayers; i++) {
			try {
				socket[i] = serverSocket.accept();
				players.put(socket[i], i);
				in[i] = new ObjectInputStream(socket[i].getInputStream());
				out[i] = new ObjectOutputStream(socket[i].getOutputStream());
				out[i].writeInt(i);// write on client the players name
				out[i].flush();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// a thread for update gameEngine in clients
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					for (int i = 0; i < numOfPlayers; i++) {
						try {
							out[i].writeObject(engine);
						} catch (IOException e) {
							// handle later
						}
					}
				}
			}
		}).start();

		// for each player, we set a thread to communicate with server
		for (int i = 0; i < numOfPlayers; i++) {
			CommunicateClient th = new CommunicateClient(engine, players.get(socket[i]), in[i]);
			th.start();
		}

	}
}

class CommunicateClient extends Thread {
	GameEngine engine;
	int player;
	ObjectInputStream in;

	public CommunicateClient(GameEngine engine, int player, ObjectInputStream in) {
		this.engine = engine;
		this.player = player;
		this.in = in;
	}

	public void run() {
		// communicate with client
		while (true) {
			try {
				Object tmp1 = in.read();// TODO not sure about how it works
				System.out.println("testing" + tmp1);
				if (tmp1 instanceof String) {
					System.out.println("tmp is a string");
					// Print the message on screen
				} else {
					System.out.println("tmp is not a string");
					int tmp = (Integer) tmp1;
					switch (tmp) {
					case Client.MOVE_DOWN:
						try {
							engine.movePlayer(engine.getPlayerFromName(player), JudgeAbstract.DOWN);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case Client.MOVE_RIGHT:
						try {
							engine.movePlayer(engine.getPlayerFromName(player), JudgeAbstract.RIGHT);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case Client.MOVE_LEFT:
						try {
							engine.movePlayer(engine.getPlayerFromName(player), JudgeAbstract.LEFT);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case Client.MOVE_UP:
						try {
							engine.movePlayer(engine.getPlayerFromName(player), JudgeAbstract.UP);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case Client.ATTACK_DOWN:
						try {
							engine.attack(engine.getPlayerFromName(player), JudgeAbstract.DOWN);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case Client.ATTACK_LEFT:
						try {
							engine.attack(engine.getPlayerFromName(player), JudgeAbstract.LEFT);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case Client.ATTACK_RIGHT:
						try {
							engine.attack(engine.getPlayerFromName(player), JudgeAbstract.RIGHT);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case Client.ATTACK_UP:
						try {
							engine.attack(engine.getPlayerFromName(player), JudgeAbstract.UP);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case Client.GET_GIFT:
						try {
							engine.getGift(engine.getPlayerFromName(player));
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case Client.PAUSE:
						try {
							if (engine.isPaused())
								engine.setPaused(false, player);
							else
								engine.setPaused(true, player);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case Client.THROW_FAN:
						try {
							engine.throwFan(engine.getPlayerFromName(player));
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case Client.ATTACK:
						try {
							engine.attack(engine.getPlayerFromName(player), JudgeAbstract.NONE);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					// other cases

					}
				}
			} catch (IOException e) {
				// handle exception
			}
		}
	}
}