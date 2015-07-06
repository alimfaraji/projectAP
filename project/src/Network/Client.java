package Network;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import source.GameEngine;

public class Client extends Thread implements KeyListener {
	public final static int MOVE_RIGHT = 0;
	public final static int MOVE_UP = 1;
	public final static int MOVE_LEFT = 2;
	public final static int MOVE_DOWN = 3;
	public final static int ATTACK_RIGHT = 4;
	public final static int ATTACK_UP = 5;
	public final static int ATTACK_LEFT = 6;
	public final static int ATTACK_DOWN = 7;
	public final static int GET_GIFT = 8;
	public Socket socket;
	public GameEngine engine;
	public int player;

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		player = player;
	}

	// output
	private ObjectOutputStream out;

	// input
	private ObjectInputStream in;

	public Client(InetAddress inetAddress, int port, GameEngine engine) throws IOException {
		this.engine = engine;
		socket = new Socket(inetAddress, port);
		// input and output
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		try {
			this.player = in.readInt();
		} catch (IOException e) {
			// handle it
		}
	}

	public void run() {

		// upadte engie
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						engine = (GameEngine) in.readObject();
						// update engine in client
						// it's not completed yet
					} catch (Exception e) {
						// handle exception
					}
				}
			}
		}).start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
