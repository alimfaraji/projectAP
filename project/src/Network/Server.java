package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server extends Thread {
	public static int TIME_OUT = 10000;
	private int port;
	private int numOfPlayers;
	private ServerSocket serverSocket;
	private Socket[] socket;

	public Server(int numOfPlayers) throws IOException {
		// when the
		// user of
		// server PC
		// click on a button which lead
		// to network mode sth like
		// joptionpane.inputDialoge ask
		// for the number of players,
		// then a new server will be
		// created .don't know how we determine "port".

		serverSocket = new ServerSocket(0);
		port = serverSocket.getLocalPort();
		serverSocket.setSoTimeout(TIME_OUT);
		this.numOfPlayers = numOfPlayers;
		socket = new Socket[numOfPlayers];
	}

	public void run() {
		for (int i = 0; i < numOfPlayers; i++) {
			try {
				socket[i] = serverSocket.accept();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
