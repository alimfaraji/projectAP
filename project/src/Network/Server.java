package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import source.GameEngine;

public class Server extends Thread {
	public static int TIME_OUT = 10000;
	private int numOfPlayers;
	private ServerSocket serverSocket;
	private Socket[] socket;
	private GameEngine engine;
	
	//output
	private ObjectOutputStream[] out;
	
	//input
	private ObjectInputStream[] in;

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
				in[i] = new ObjectInputStream(socket[i].getInputStream());
				out[i] = new ObjectOutputStream(socket[i].getOutputStream());
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//a thread for update gameEngine in clients
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					for (int i = 0 ; i < numOfPlayers; i++ ){
						try{
							out[i].writeObject(engine);
						}catch(IOException e){
							//handle later
						}
					}
				}
			}
		}).start();

		// for each player, we set a thread to communicate with server
		for (int i = 0; i < numOfPlayers; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						//communicate with client
						//
					}
				}
			}).start();
		}
		
	}
}
