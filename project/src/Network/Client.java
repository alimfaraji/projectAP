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
	public final static int PAUSE = 9;
	public final static int THROW_FAN = 10;
	public final static int ATTACK = 11;
	private Socket socket;
	private GameEngine engine;
	private int player;
	private String chatString;// should synch with gamePanel

	public void setChatString(String s){
		chatString = s;
	}
	
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

//	public static void main(String[] args) {
//		new Client(inetAddress, port, new GameEngine());
//	}
	
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
		
		//for chat:
		while (true){
			if (chatString != null && chatString != "" ){
				try{
					out.reset();
					out.writeUTF(chatString);
					out.flush();
					chatString = "";
				}catch(IOException e){
					//handle exception
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			out.reset();
			switch (e.getKeyCode()) {
			case KeyEvent.VK_KP_UP:
				out.writeInt(MOVE_UP);
				out.flush();
				break;
			case KeyEvent.VK_KP_DOWN:
				out.writeInt(MOVE_DOWN);
				out.flush();
				break;
			case KeyEvent.VK_KP_LEFT:
				out.writeInt(MOVE_LEFT);
				out.flush();
				break;
			case KeyEvent.VK_KP_RIGHT:
				out.writeInt(MOVE_RIGHT);
				out.flush();
				break;
			case KeyEvent.VK_W:
				out.writeInt(ATTACK_UP);
				out.flush();
				break;
			case KeyEvent.VK_A:
				out.writeInt(ATTACK_LEFT);
				out.flush();
				break;
			case KeyEvent.VK_S:
				out.writeInt(ATTACK_DOWN);
				out.flush();
				break;
			case KeyEvent.VK_D:
				out.writeInt(ATTACK_RIGHT);
				out.flush();
				break;
			case KeyEvent.VK_ESCAPE:
				out.writeInt(PAUSE);
				out.flush();
				break;
			case KeyEvent.VK_ENTER:
				out.writeInt(GET_GIFT);
				out.flush();
				break;
			case KeyEvent.VK_SHIFT:
				out.writeInt(THROW_FAN);
				out.flush();
				break;
			case KeyEvent.VK_SPACE:
				out.writeInt(ATTACK);
				out.flush();
			}
		} catch (IOException ex) {
			// handle
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}