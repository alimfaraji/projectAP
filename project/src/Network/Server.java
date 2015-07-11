package Network;

import java.io.BufferedInputStream;
import java.io.*;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
	private CommunicateClient[] threadForCommunicate;
	private MultimediaChatServer[] mcs;
	private NormalChatServer[] ncs;
	private ObjectOutputStream[] out;
	private ObjectInputStream[] in;

	// input and output for save and load:
	private ObjectOutputStream outputForSave = new ObjectOutputStream(new FileOutputStream("saved.txt"));
	private ObjectInputStream inputForSave = new ObjectInputStream(new FileInputStream("saved.txt"));

	
	//load and save functions
	/**
	 * save game should be called in GamePanel of Server
	 */
	public void saveGame() {
		try {
			outputForSave.reset();
			outputForSave.writeObject(engine);
			outputForSave.flush();
			// TODO no finished
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
	public void loadGame() throws NoSavedGameToLoad {
		try {
			engine = (GameEngine) inputForSave.readObject();
		} catch (Exception e) {
			// handle exception
		}
	}
	
	// end of load and save functions

	public Server(int numOfPlayers, GameEngine engine) throws IOException {
		players = new HashMap<>();
		this.engine = engine;
		serverSocket = new ServerSocket(0);
		serverSocket.setSoTimeout(TIME_OUT);
		this.numOfPlayers = numOfPlayers;
		socket = new Socket[numOfPlayers];
		out = new ObjectOutputStream[numOfPlayers];
		in = new ObjectInputStream[numOfPlayers];
		threadForCommunicate = new CommunicateClient[numOfPlayers];
		mcs = new MultimediaChatServer[numOfPlayers];
		ncs = new NormalChatServer[numOfPlayers];
	}
	
	public int getPort(){
		return serverSocket.getLocalPort();
	}
	
	/**
	 * not sure , not tested
	 * @param player
	 */
	public void kick(int player){
		out[player] = null;
		mcs[player] = null;
		ncs[player] = null;
	}

	public void run() {
		for (int i = 0; i < numOfPlayers; i++) {
			try {
				boolean a = false;
				while(true){
					System.out.println("i'm in server");
					if(a)
						break;
				}
				mcs[i] = new MultimediaChatServer(serverSocket.accept());//multiChat
				ncs[i] = new NormalChatServer(serverSocket.accept(), i);//normalChat

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
							out[i].reset();
							out[i].writeObject(engine);
							out[i].flush();
						} catch (IOException e) {
							// if one client disconnect
							// or server kick out a client
							try {
								engine.setPaused(true);
								mcs[i] = new MultimediaChatServer(serverSocket.accept());//multiChat
								ncs[i] = new NormalChatServer(serverSocket.accept(), i);//normalChat
								System.out.println("client timed out");
								socket[i] = serverSocket.accept();
								players.put(socket[i], i);
								out[i] = new ObjectOutputStream(socket[i].getOutputStream());
								in[i] = new ObjectInputStream(socket[i].getInputStream());
								out[i].writeInt(i);// write on client the
													// players name
								out[i].flush();
								threadForCommunicate[i].setIn(in[i]);
								engine.setPaused(false);
							} catch (Exception ex) {
								//handleExceptions
							}
						}
					}
				}
			}
		}).start();

		// for each player, we set a thread to communicate with server
		for (int i = 0; i < numOfPlayers; i++) {
			threadForCommunicate[i] = new CommunicateClient(engine, players.get(socket[i]), in[i]);
			threadForCommunicate[i].start();

			ncs[i].start();
			mcs[i].start();
		}

	}
}

class CommunicateClient extends Thread {
	GameEngine engine;
	int player;
	ObjectInputStream in;

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	public CommunicateClient(GameEngine engine, int player, ObjectInputStream in) {
		this.engine = engine;
		this.player = player;
		this.in = in;

	}

	public void run() {
		// communicate with client
		while (true) {
			try {
				int tmp = in.readInt();
				switch (tmp) {
				case Client.MOVE_DOWN:
					try {
						System.out.println("byyyyyyyyyyyyy");
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
			} catch (IOException e) {
				// handle exception
			}
		}
	}
}

class NormalChatServer extends Thread {
	private BufferedWriter bw;
	int player;
	String chatString;
	Socket socket = null;
	ObjectInputStream input = null;

	public NormalChatServer(Socket socket, int player) {
		this.player = player;
		this.socket = socket;
	}

	public String getChatString() {
		return chatString;
	}

	public void run() {
		try {
			bw = new BufferedWriter(new FileWriter("file" + player));
		} catch (Exception e) {

		}
		try {
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// handle excetion
		}

		while (true) {
			try {
				chatString = input.readUTF();
				bw.write(chatString + "\n");
				bw.close();
				bw = new BufferedWriter(new FileWriter("file" + player, true));
			} catch (IOException e) {
				// handle exception
			}
		}
	}
}

class MultimediaChatServer extends Thread {
	private String fileOutput = "gotfile";
	Socket clientSocket = null;
	InputStream is = null;

	public void setNameForFile(String s) {
		this.fileOutput = s;
	}

	public MultimediaChatServer(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		while (true) {
			byte[] aByte = new byte[1];
			int bytesRead;

			try {
				is = clientSocket.getInputStream();
			} catch (IOException ex) {
				// Do exception handling
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			if (is != null) {
				FileOutputStream fos = null;
				BufferedOutputStream bos = null;
				try {
					// ask to enter an file name with GUI
					// read the file name from GUI
					fos = new FileOutputStream(fileOutput);
					bos = new BufferedOutputStream(fos);
					bytesRead = is.read(aByte, 0, aByte.length);

					do {
						baos.write(aByte);
						bytesRead = is.read(aByte);
					} while (bytesRead != -1);

					bos.write(baos.toByteArray());
					bos.flush();
					bos.close();
					// clientSocket.close();
				} catch (IOException ex) {
					// Do exception handling
				}
			}
		}
	}
}