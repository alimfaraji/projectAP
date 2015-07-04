package source;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import bozorg.judge.JudgeAbstract;

public class Fan extends Person implements Serializable{
	//static
	private static ArrayList<Fan> allFans = new ArrayList<Fan>();
	
	public static void setAllFans(ArrayList<Fan> tmp){
		allFans = tmp;
	}
	
	public static void add(Fan fan){
		allFans.add(fan);
	}
	
	public static ArrayList<Fan> getAllFans(){
		return allFans;
	}
	
	//non static
	
	private Player owner;
	boolean isFree;
	public boolean isFree() {
		return isFree;
	}
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null || this.getClass() != obj.getClass() )
			return false;
		
		if (this == obj)
			return true;
		
		Fan tmpFan = (Fan)obj;
		if (this.getGameObjectID() == tmpFan.getGameObjectID() )
			return true;
		return false;
	}

	public Fan(){
		super();
		allFans.add(this);
		isFree = true;
	}
	
	public Fan(Player player){
		this();
		setOwner(player);
	}

	public HashMap<String, Integer> getInfo(){
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		ret.put(JudgeAbstract.ROW, this.getCell().getRow());
		ret.put(JudgeAbstract.COL, this.getCell().getCol());
		ret.put(JudgeAbstract.OWNER, this.getOwner().getName());
		ret.put(JudgeAbstract.IS_ALIVE, this.isAlive() == true ? 1 : 0);
		return ret;
	}
}
