package Network;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import source.GameEngine;

public class Client extends Thread {
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
	public static int player = 2;
	private MultimediaChatClient mcc = null;
	private NormalChatClient ncc = null;
	
	// output
	public static ObjectOutputStream out;

	// input
	private ObjectInputStream in;

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		player = player;
	}

	public Client(String host, int port, GameEngine engine) throws IOException {
		mcc = new MultimediaChatClient(new Socket(host, port));
		ncc = new NormalChatClient(new Socket(host, port));
		this.engine = engine;
		socket = new Socket(host, port);
	}

	public void run() {
		// upadte engie
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					out = new ObjectOutputStream(socket.getOutputStream());
					in = new ObjectInputStream(socket.getInputStream());
					player = in.readInt();
				} catch (Exception e) {
					System.out.println("exception 1");
				}
				while (true) {
					try {
						System.out.println("i'm reading engine");
						engine = (GameEngine) in.readObject();
						// update engine in client
						// it's not completed yet
					} catch (Exception e) {
						// handle exception
					}
				}
			}
		}).start();
		
		//start Normal chat thread.
		ncc.start();
		
		//start multimedia chat thread. 
		mcc.start();
	}
	
	//Normal chat functions
	public void setChatMessage(String s){
		ncc.setChatString(s);
	}
	
	public void sendMessage(){
		ncc.send();
	}
	
	public void sendMessage(String s){
		ncc.setChatString(s);
		ncc.send();
	}
	//end of Normal chat functions
	
	// multiMedia chat functions
	public void setLocationOfFileToSend(String s){
		mcc.setFileToSend(s);
	}
	
	public void sendFile(){
		mcc.Send();
	}
	
	public void sendFile(String s){
		mcc.setFileToSend(s);
		mcc.Send();
	}
	// end of multimedia chat functions

//	@Override
//	public void keyTyped(KeyEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void keyPressed(KeyEvent e) {
//		try {
//			out.reset();
//			switch (e.getKeyCode()) {
//			case KeyEvent.VK_KP_UP:
//				out.writeInt(MOVE_UP);
//				out.flush();
//				break;
//			case KeyEvent.VK_KP_DOWN:
//				out.writeInt(MOVE_DOWN);
//				out.flush();
//				break;
//			case KeyEvent.VK_KP_LEFT:
//				out.writeInt(MOVE_LEFT);
//				out.flush();
//				break;
//			case KeyEvent.VK_KP_RIGHT:
//				out.writeInt(MOVE_RIGHT);
//				out.flush();
//				break;
//			case KeyEvent.VK_W:
//				out.writeInt(ATTACK_UP);
//				out.flush();
//				break;
//			case KeyEvent.VK_A:
//				out.writeInt(ATTACK_LEFT);
//				out.flush();
//				break;
//			case KeyEvent.VK_S:
//				out.writeInt(ATTACK_DOWN);
//				out.flush();
//				break;
//			case KeyEvent.VK_D:
//				out.writeInt(ATTACK_RIGHT);
//				out.flush();
//				break;
//			case KeyEvent.VK_ESCAPE:
//				out.writeInt(PAUSE);
//				out.flush();
//				break;
//			case KeyEvent.VK_ENTER:
//				out.writeInt(GET_GIFT);
//				out.flush();
//				break;
//			case KeyEvent.VK_SHIFT:
//				out.writeInt(THROW_FAN);
//				out.flush();
//				break;
//			case KeyEvent.VK_SPACE:
//				out.writeInt(ATTACK);
//				out.flush();
//			}
//		} catch (IOException ex) {
//			// handle
//		}
//
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		// TODO Auto-generated method stub
//
//	}
}

class NormalChatClient extends Thread {
	private String chatString;
	private boolean readyToSend = false;
	private ObjectOutputStream outputToServer = null;
	private Socket socket = null;
	
	public NormalChatClient(Socket socket){
		this.socket = socket;
	}
	
	public void setChatString(String s){
		chatString = s;
	}
	
	public void send(){
		readyToSend = true;
	}
	
	public void run(){
		try{
			outputToServer = new ObjectOutputStream(socket.getOutputStream());
		}catch(IOException e){
			//handle exception
		}
		while (true){
			if (readyToSend){
				if (chatString != null && chatString != ""){
					try{
						outputToServer.reset();
						outputToServer.writeUTF(chatString);
						outputToServer.flush();
						chatString = null;
					}catch(IOException e){
						//handle exception
					}
				}
			}
			readyToSend = false;
		}
	}
}

class MultimediaChatClient extends Thread {
	private String fileToSend;
	private boolean readyToSend = false;
	Socket connectionSocket = null;
	BufferedOutputStream outToClient = null;
	
	public MultimediaChatClient(Socket connectionSocket){
		this.connectionSocket = connectionSocket;
	}
	
	public void setFileToSend(String s) {
		fileToSend = s;
	}

	public void Send() {
		readyToSend = true;
	}

	public void run() {
		try{
			outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
		}catch(IOException e){
			//handle exception
		}
		while (true) {
			if (readyToSend == true) {
				if (outToClient != null) {
					File myFile = new File(fileToSend);
					byte[] mybytearray = new byte[(int) myFile.length()];

					FileInputStream fis = null;

					try {
						fis = new FileInputStream(myFile);
					} catch (FileNotFoundException ex) {
						// Do exception handling
					}
					BufferedInputStream bis = new BufferedInputStream(fis);

					try {
						bis.read(mybytearray, 0, mybytearray.length);
						outToClient.write(mybytearray, 0, mybytearray.length);
						outToClient.flush();
						outToClient.close();
//						connectionSocket.close();
					} catch (IOException ex) {
						// Do exception handling
					}
				}
				readyToSend = false;
			}
		}
	}
}