package bozorg.judge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import source.Cell;
import source.Fan;
import source.GameEngine;
import source.Person;
import source.Player;

import bozorg.common.GameObjectID;
import bozorg.common.exceptions.BozorgExceptionBase;
import bozorg.common.exceptions.CallingDeadPerson;
import bozorg.common.exceptions.DirectionIsBlocked;
import bozorg.common.exceptions.HasStonedBonus;
import bozorg.common.exceptions.ImpossibleToAttack;
import bozorg.common.exceptions.IncompleteOperation;
import bozorg.common.exceptions.InvalidGOI;
import bozorg.common.exceptions.InvalidInteger;
import bozorg.common.exceptions.NoFanToThrow;
import bozorg.common.exceptions.NoGiftToGet;
import bozorg.common.exceptions.NoPersonToAttack;

public class Judge extends JudgeAbstract {
	private GameEngine engine;
	
	public Judge(){
		engine = new GameEngine();
	}

	// map functions
	/**
	 * loads map for initializing
	 */
	public ArrayList<GameObjectID> loadMap(int[][] cellsType,
			int[][] wallsType, int[] players) {
		return engine.loadMap(cellsType, wallsType, players);
	}

	public int getMapWidth() {
		return engine.getMapWidth();
	}

	public int getMapHeight() {
		return engine.getMapHeight();
	}

	public int getMapCellType(int col, int row) {
		return engine.getMapCellType(col, row);
	}

	public int getMapCellType(int col, int row, GameObjectID id) {
		Player player = (Player) Player.getPersonFromGOI(id);
		return engine.getMapCellType(col, row, player);
	}

	public int getMapWallType(int col, int row) {
		return engine.getMapWallType(col, row);
	}

	public int getMapWallType(int col, int row, GameObjectID id) {
		Player player = (Player) Player.getPersonFromGOI(id);
		return engine.getMapWallType(col, row, player);
	}

	public void setup() {
		// TODO
	}

	// logic functions
	public void movePlayer(GameObjectID id, int direction)
			throws BozorgExceptionBase {
		if (!Person.isValidGOI(id))
			throw new InvalidGOI();

		Player player = (Player) Player.getPersonFromGOI(id);
		engine.movePlayer(player, direction);
	}

	// TODO
	public void attack(GameObjectID id, int direction)
			throws BozorgExceptionBase {
		if (!Person.isValidGOI(id))
			throw new InvalidGOI();

		Player player = (Player) Player.getPersonFromGOI(id);
		
		engine.attack(player, direction);
	}

	public GameObjectID throwFan(GameObjectID id) throws BozorgExceptionBase {
		if (!Person.isValidGOI(id))
			throw new InvalidGOI();

		Player player = (Player) Player.getPersonFromGOI(id);
		
		Fan fan = engine.throwFan(player);
		
		return fan.getGameObjectID();
	}

	public void getGift(GameObjectID id) throws BozorgExceptionBase {
		if (!Person.isValidGOI(id))
			throw new InvalidGOI();
		Player player = (Player) Player.getPersonFromGOI(id);
		engine.getGift(player);
	}

	// AI functions. these functions will never be used in judge
	public void AIByStudents(GameObjectID player) {
		try {
			ArrayList<String> cells = getVision(player);
			int[] rows = new int[cells.size()];
			int[] cols = new int[cells.size()];
			int currentRow = 0, currentCol = 0;
			for (int i = 0; i < cells.size(); i++) {
				String[] tmp = cells.get(i).split(",");
				rows[i] = Integer.parseInt(tmp[0]);
				cols[i] = Integer.parseInt(tmp[1]);
				currentRow += rows[i];
				currentCol += cols[i];

			}
			currentRow = currentRow / cells.size();
			currentCol = currentCol / cells.size();
			for (int i = 0; i < cells.size(); i++) {
				if (getMapCellType(cols[i], rows[i], player) == JudgeAbstract.JJ_CELL) {
					if (currentCol > cols[i])
						movePlayer(player, JudgeAbstract.LEFT);
					else if (currentCol < cols[i])
						movePlayer(player, JudgeAbstract.RIGHT);
					else if (currentRow > rows[i])
						movePlayer(player, JudgeAbstract.UP);
					else if (currentRow < rows[i])
						movePlayer(player, JudgeAbstract.DOWN);
					return;
				}
			}
			movePlayer(player, new Random().nextInt() % 4);

		} catch (Exception e) {

		}

	}

	// get info
	public ArrayList<GameObjectID> getEveryThing() {// will never be used in
													// judge
		// TODO
		return null;
	}

	public ArrayList<String> getVision(GameObjectID id)
			throws BozorgExceptionBase {
		if (!Person.isValidGOI(id))
			throw new InvalidGOI();

		Player player = (Player) Player.getPersonFromGOI(id);
		if (!player.isAlive())
			throw new CallingDeadPerson();

		ArrayList<Cell> cells = player.getVisionArrayCells();
		ArrayList<String> ret = new ArrayList<String>();
		for (Cell tmp : cells)
			ret.add(tmp.getRow() + "," + tmp.getCol());
		return ret;
	}
	
	//TODO : dead person ??
	public ArrayList<GameObjectID> getPlayersInVision(GameObjectID id) {
		Player player = (Player) Player.getPersonFromGOI(id);
		ArrayList<GameObjectID> ret = new ArrayList<GameObjectID>();
		ArrayList<Player> playersInVision = player.getPlayersInVision();
		for (Player tmp : playersInVision)
			ret.add(tmp.getGameObjectID());
		return ret;
	}

	public ArrayList<GameObjectID> getFans(GameObjectID id)
			throws BozorgExceptionBase {
		if (!Person.isValidGOI(id))
			throw new InvalidGOI();
		Player player = (Player) Player.getPersonFromGOI(id);
		if (!player.isAlive())
			throw new CallingDeadPerson();
		ArrayList<Fan> allFans = player.getFans();
		ArrayList<GameObjectID> ret = new ArrayList<GameObjectID>();
		for (Fan tmp : allFans)
			ret.add(tmp.getGameObjectID());
		return ret;
	}

	public HashMap<String, Integer> getInfo(GameObjectID id)
			throws BozorgExceptionBase {
		if (!Person.isValidGOI(id))
			throw new InvalidGOI();
		return Person.getPersonFromGOI(id).getInfo();
	}

	// Controller functions
	public void next50milis() {
		engine.next50milis();
	}

	public void startTimer() {// will never be used in judge
		engine.startTimer();
		// TODO
	}

	public void pauseTimer() {
		engine.pauseTimer();
		// TODO
	}

	public float getTime() {
		return engine.getTime();
	}

	static public float getStaticTime() {
		return GameEngine.getStaticTime();
	}

	// Judge cheat functions
	public void updateInfo(GameObjectID id, String infoKey, Integer infoValue)
			throws BozorgExceptionBase {
		if (!Person.isValidGOI(id))
			throw new InvalidGOI();
		Person person = Person.getPersonFromGOI(id);
		if (person.getClass().equals(Player.getAllPlayers().get(0).getClass())) {
			Player player = (Player) person;
			if (infoKey == JudgeAbstract.ROW)
				player.setCell(Cell.getCell(infoValue, player.getCell()
						.getCol()));
			if (infoKey == JudgeAbstract.COL)
				player.setCell(Cell.getCell(player.getCell().getRow(),
						infoValue));
			if (infoKey == JudgeAbstract.SPEED)
				player.setSpeed(infoValue);
			if (infoKey == JudgeAbstract.NAME)
				player.setName(infoValue);
			if (infoKey == JudgeAbstract.IS_WINNER) {
				if (infoValue == JudgeAbstract.WINS)
					Player.setWinner(player);
				if (infoValue == JudgeAbstract.LOST)
					player.setAlive(false);
				if (infoValue == JudgeAbstract.NOT_FINISHED) {
					player.setAlive(true);
					Player.setWinner(null);
				}
			}
			if (infoKey == JudgeAbstract.POWER)
				player.setPower(infoValue);
			if (infoKey == JudgeAbstract.VISION)
				player.setVision(infoValue);
			if (infoKey == JudgeAbstract.FANS)
				player.addFans(infoValue);
			if (infoKey == JudgeAbstract.IS_ALIVE)
				player.setAlive(infoValue == JudgeAbstract.ALIVE ? true : false);
			if (infoKey == JudgeAbstract.HEALTH)
				player.setHealth(infoValue);
		}
		if (person.getClass().equals(Fan.getAllFans().get(0).getClass())) {
			Fan fan = (Fan) person;
			if (infoKey == JudgeAbstract.ROW)
				fan.setCell(Cell.getCell(infoValue, fan.getCell().getCol()));
			if (infoKey == JudgeAbstract.COL)
				fan.setCell(Cell.getCell(fan.getCell().getRow(), infoValue));
			if (infoKey == JudgeAbstract.OWNER)
				fan.setOwner(Player.getPlayerFromNumber(infoValue));
			if (infoKey == JudgeAbstract.IS_ALIVE)
				fan.setAlive(infoValue == JudgeAbstract.ALIVE ? true : false);
		}
	}

}