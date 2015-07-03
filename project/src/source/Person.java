package source;

import java.util.ArrayList;
import java.util.HashMap;

import bozorg.common.GameObjectID;

public abstract class Person {
	//static part
	private static ArrayList<Person> allPerson = new ArrayList<Person>();
	private static ArrayList<GameObjectID> allGameObjectIDs = new ArrayList<GameObjectID>();
	
	public static ArrayList<GameObjectID> getAllGameObjectIDs(){
		return allGameObjectIDs;
	}

	public static void addGameObjectID(GameObjectID id){
		allGameObjectIDs.add(id);
	}
	
	public static boolean isValidGOI(GameObjectID id){
		return allGameObjectIDs.contains(id);
	}
	
	public static Person getPersonFromGOI(GameObjectID id){
		for (Person tmp : allPerson)
			if (tmp.getGameObjectID().equals(id))
				return tmp;
		return null;
	}

	//nonStatic part
	private Cell cell;
	private boolean isAlive;
	private GameObjectID gameObjectID;
	
	public Person(){
		this.allPerson.add(this);
		gameObjectID = GameObjectID.create(this.getClass());
		allGameObjectIDs.add(gameObjectID);
		isAlive = true;
	}
	
	public void setCell(Cell cell){
		this.cell = cell;
	}
	
	public Cell getCell(){
		return cell;
	}
	
	public GameObjectID getGameObjectID() {
		return gameObjectID;
	}

	public void setGameObjectID(GameObjectID gameObjectID) {
		this.gameObjectID = gameObjectID;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	abstract public HashMap<String, Integer> getInfo();
}
