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
	private Socket socket;
	private GameEngine engine;

	// output
	private ObjectOutputStream out;

	// input
	private ObjectInputStream in;

	public Client(InetAddress inetAddress, int port, GameEngine engine)
			throws IOException {
		this.engine = engine;
		socket = new Socket(inetAddress, port);

		// input and output
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	public void run() {
		
		// upadte engie
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try{
						engine = (GameEngine)in.readObject();
						//update engine in client 
						// it's not completed yet
					}catch(Exception e){
						//handle exception
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
