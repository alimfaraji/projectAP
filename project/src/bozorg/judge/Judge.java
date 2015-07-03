package bozorg.judge;

import java.util.ArrayList;
import java.util.HashMap;

import source.Cell;
import source.Fan;
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
	private static float time = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// map functions
	public ArrayList<GameObjectID> loadMap(int[][] cellsType,
			int[][] wallsType, int[] players) {

		Cell.setMapSize(cellsType.length, cellsType[0].length);

		// constructing cells
		for (int i = 0; i < cellsType.length; i++)
			for (int j = 0; j < cellsType[0].length; j++) {
				Cell cell = new Cell(cellsType[i][j], wallsType[i][j], i, j);
			}

		int k = 0;
		for (int i = 0; i < cellsType.length; i++)
			for (int j = 0; j < cellsType[0].length; j++) {
				if (cellsType[i][j] == START_CELL) {
					if (k == 0) {
						Player tmp = new Player(0, 2, 5, 3, Cell.getCell(
								i, j));//set Player
						for (int c = 0; c < 100; c++) // set fans For Players
							new Fan(tmp);
					}
					if (k == 1) {
						Player tmp = new Player(1, 3, 1, 3, Cell.getCell(
								i, j));
						for (int c = 0; c < 5; c++)
							new Fan(tmp);
					}
					if (k == 2) {
						Player tmp = new Player(2, 2, 4, 6, Cell.getCell(
								i, j));
					}
					if (k == 3) {
						Player tmp = new Player(3, 2, 5, 3, Cell.getCell(
								i, j));
						for (int c = 0; c < 10; c++)
							new Fan(tmp);
					}
					k++;// next player
				}
			}
		return null;
	}

	public int getMapWidth() {
		return Cell.getNumOfRows();
	}

	public int getMapHeight() {
		return Cell.getNumOfCols();
	}

	public int getMapCellType(int col, int row) {
		return Cell.getCell(row, col).getType();
	}

	public int getMapCellType(int col, int row, GameObjectID id) {
		ArrayList<Player> players = Player.getAllPlayers();
		Player player = (Player)Player.getPersonFromGOI(id);

		Cell cell = Cell.getCell(row, col);
		if (!player.isCellInVision(cell))
			return DARK_CELL;

		int tmp = Cell.getCell(row, col).getType();
		if (tmp == SPEEDUP_CELL || tmp == RADAR_CELL || tmp == STONE_CELL
				|| tmp == JUMP_CELL || tmp == FAN_CELL || tmp == HOSPITAL_CELL)
			return BONUS_CELL;

		return tmp;
	}

	public int getMapWallType(int col, int row) {
		return Cell.getCell(row, col).getWallType();
	}

	public int getMapWallType(int col, int row, GameObjectID id) {
		ArrayList<Player> players = Player.getAllPlayers();
		Player player = (Player)Player.getPersonFromGOI(id);
		Cell cell = Cell.getCell(row, col);
		if (!player.isCellInVision(cell))
			return XXXX_WALL;

		return Cell.getCell(row, col).getWallType();
	}

	public void setup() {
		// TODO
	}

	// logic functions
	public void movePlayer(GameObjectID id, int direction)
			throws BozorgExceptionBase {
		if(!Person.isValidGOI(id))
			throw new InvalidGOI();
		if (direction >= 5)
			throw new InvalidInteger();
		
		Player player = (Player)Player.getPersonFromGOI(id);
		if (!player.isAlive())
			throw new CallingDeadPerson();
		
		try{
			player.move(direction);
		}catch(HasStonedBonus e){
			return;
		}catch(DirectionIsBlocked e){
			throw e;
		}catch(IncompleteOperation e){
			throw e;
		}
	}

	//TODO
	public void attack(GameObjectID id, int direction)
			throws BozorgExceptionBase {
		
		if(!Person.isValidGOI(id))
			throw new InvalidGOI();
		if (direction >= 5)
			throw new InvalidInteger();
		
		Player player = (Player)Player.getPersonFromGOI(id);
		if (!player.isAlive())
			throw new CallingDeadPerson();
		
		try{
			player.attack(direction);
		}catch(HasStonedBonus e){
			return;
		}catch(DirectionIsBlocked e){
			throw e;
		}catch(IncompleteOperation e){
			throw e;
		}catch(NoPersonToAttack e){
			throw e;
		}
	}

	public GameObjectID throwFan(GameObjectID id)
			throws BozorgExceptionBase {
		if(!Person.isValidGOI(id))
			throw new InvalidGOI();
		
		Player player = (Player)Player.getPersonFromGOI(id);
		if (!player.isAlive())
			throw new CallingDeadPerson();
		
		Fan fan = player.throwFan();
		if (fan == null)
			throw new NoFanToThrow();

		return fan.getGameObjectID();
	}

	public void getGift(GameObjectID id) throws BozorgExceptionBase {
		if(!Person.isValidGOI(id))
			throw new InvalidGOI();
		Player player = (Player)Player.getPersonFromGOI(id);
		
		if (!player.isAlive())
			throw new CallingDeadPerson();
		
		try{
			player.getGift();
		}catch(BozorgExceptionBase e){
			throw e;
		}
	}

	// AI functions. these functions will never be used in judge
	public void AIByStudents(GameObjectID player) {
		// TODO
	}

	// get info
	public ArrayList<GameObjectID> getEveryThing() {// will never be used in
													// judge
		// TODO
		return null;
	}

	public ArrayList<String> getVision(GameObjectID id)
			throws BozorgExceptionBase {
		if(!Person.isValidGOI(id))
			throw new InvalidGOI();
		
		Player player = (Player)Player.getPersonFromGOI(id);
		if (!player.isAlive())
			throw new CallingDeadPerson();
		
		ArrayList<Cell> cells = player.getVisionArrayCells();
		ArrayList<String> ret = new ArrayList<String>();
		for (Cell tmp : cells)
			ret.add(tmp.getRow()+","+tmp.getCol());
		return ret;
	}

	public ArrayList<GameObjectID> getPlayersInVision(GameObjectID id) {
		Player player = (Player)Player.getPersonFromGOI(id);
		ArrayList<GameObjectID> ret = new ArrayList<GameObjectID>();
		ArrayList<Player> playersInVision = player.getPlayersInVision();
		for (Player tmp : playersInVision)
			ret.add(tmp.getGameObjectID());
		return ret;
	}

	public ArrayList<GameObjectID> getFans(GameObjectID id)
			throws BozorgExceptionBase {
		if(!Person.isValidGOI(id))
			throw new InvalidGOI();
		Player player = (Player)Player.getPersonFromGOI(id);
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
		if(!Person.isValidGOI(id))
			throw new InvalidGOI();
		return Person.getPersonFromGOI(id).getInfo();
	}
	
	private void checkForFinish(){
		ArrayList<Player> players = Player.getAllPlayers();
		int numOfPlayersInJJ = 0;
		for (Player tmp : players){
			if (tmp.getCell().getType() == JudgeAbstract.JJ_CELL )
				numOfPlayersInJJ++;
		}
		if (numOfPlayersInJJ == 1){
			for (Player tmp : players){
				if (tmp.getCell().getType() == JudgeAbstract.JJ_CELL){
					Player.setWinner(tmp);
				}
			}
		}
	}

	// Controller functions
	public void next50milis() {
		time = +0.050f;
		checkForFinish();
		checkCurrentOperations();
	}

	public void startTimer() {// will never be used in judge
		// TODO
	}

	public void pauseTimer() {
		// TODO
	}

	public float getTime() {
		return time;
	}
	static public float getStaticTime(){
		return time;
	}

	private void checkCurrentOperations() {
		ArrayList<Player> players = Player.getAllPlayers();
		for (Player player : players) {
			if (player.isAlive())
				player.continueCurrentOperation();
		}
	}


	// Judge cheat functions
	public void updateInfo(GameObjectID id, String infoKey, Integer infoValue)
			throws BozorgExceptionBase {
		if(!Person.isValidGOI(id))
			throw new InvalidGOI();
		Person person = Person.getPersonFromGOI(id);
		if (person.getClass().equals(Player.getAllPlayers().get(0).getClass())){
			Player player = (Player)person;
			if (infoKey == JudgeAbstract.ROW )
				player.setCell(Cell.getCell(infoValue, player.getCell().getCol()));
			if (infoKey == JudgeAbstract.COL)
				player.setCell(Cell.getCell(player.getCell().getRow(), infoValue));
			if (infoKey == JudgeAbstract.SPEED)
				player.setSpeed(infoValue);
			if (infoKey == JudgeAbstract.NAME)
				player.setName(infoValue);
			if (infoKey == JudgeAbstract.IS_WINNER){
				if (infoValue == JudgeAbstract.WINS)
					Player.setWinner(player);
				if (infoValue == JudgeAbstract.LOST)
					player.setAlive(false);
				if (infoValue == JudgeAbstract.NOT_FINISHED){
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
		if (person.getClass().equals(Fan.getAllFans().get(0).getClass())){
			Fan fan = (Fan)person;
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