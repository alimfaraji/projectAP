package source;

import java.io.Serializable;
import java.util.ArrayList;

import bozorg.common.GameObjectID;
import bozorg.common.exceptions.BozorgExceptionBase;
import bozorg.common.exceptions.CallingDeadPerson;
import bozorg.common.exceptions.DirectionIsBlocked;
import bozorg.common.exceptions.HasStonedBonus;
import bozorg.common.exceptions.IncompleteOperation;
import bozorg.common.exceptions.InvalidGOI;
import bozorg.common.exceptions.InvalidInteger;
import bozorg.common.exceptions.MoreThanOnePlayerWantToGetAGift;
import bozorg.common.exceptions.NoFanToThrow;
import bozorg.common.exceptions.NoGiftToGet;
import bozorg.common.exceptions.NoPersonToAttack;
import bozorg.judge.JudgeAbstract;

public class GameEngine implements Serializable{
	private static float time = 0;
	private Cell JJCell;
	
	//Person class
	private ArrayList<Person> allPerson = null;
	private ArrayList<GameObjectID> allGameObjectIDs = null;

	//Player class
	private ArrayList<Player> allPlayers = null;
	private Player winner;
	
	//Fan class
	private ArrayList<Fan> allFans = null;
	
	//Cell class
	private Cell[][] allCells = null;
	private static int numOfRows;
	private static int numOfCols;
	
	public GameEngine(){
		//Person class
		this.allPerson = Person.getAllPerson();
		this.allGameObjectIDs = Person.getAllGameObjectIDs();
		
		//Player class
		this.allPlayers = Player.getAllPlayers();
		this.winner = Player.getWinner();
		
		//Fan class
		this.allFans = Fan.getAllFans();
		
		//Cell class
		this.allCells = Cell.getAllCells();
		this.numOfCols = Cell.getNumOfCols();
		this.numOfRows = Cell.getNumOfRows();
	}
	
	//TODO
	/**
	 * for network: every client invoke this method every x mls to update static parameters in 
	 * other classes 
	 */
	public void updateStatics(){
		//Person class
		Person.setAllPerson(this.allPerson);
		Person.setAllGameObjectIDs(this.allGameObjectIDs);
		
		//Player class
		Player.setAllPlayers(this.allPlayers);
		Player.setWinner(this.winner);
		
		//Fan class
		Fan.setAllFans(this.allFans);
		
		//Cell class
		Cell.setMapSize(numOfRows, numOfCols);
		Cell.setAllCells(this.allCells);
	}
	
	// map functions
	/**
	 * loads map for initializing
	 */
	public ArrayList<GameObjectID> loadMap(int[][] cellsType,
			int[][] wallsType, int[] players) {

		Cell.setMapSize(cellsType.length, cellsType[0].length);

		// constructing cells
		for (int i = 0; i < cellsType.length; i++)
			for (int j = 0; j < cellsType[0].length; j++) {
				Cell cell = new Cell(cellsType[i][j], wallsType[i][j], i, j);
				if (cellsType[i][j] == JudgeAbstract.JJ_CELL)
					JJCell = cell;
			}

		int k = 0;
		for (int i = 0; i < cellsType.length; i++)
			for (int j = 0; j < cellsType[0].length; j++) {
				if (cellsType[i][j] == JudgeAbstract.START_CELL) {
					if (players[k] == 0) {
						Player tmp = new Player(0, 2, 5, 3, Cell.getCell(i, j));// set
																				// Player
						for (int c = 0; c < 100; c++)
							// set fans For Players
							new Fan(tmp);
					}
					if (players[k] == 1) {
						Player tmp = new Player(1, 3, 1, 3, Cell.getCell(i, j));
						for (int c = 0; c < 5; c++)
							new Fan(tmp);
					}
					if (players[k] == 2) {
						Player tmp = new Player(2, 2, 4, 6, Cell.getCell(i, j));
					}
					if (players[k] == 3) {
						Player tmp = new Player(3, 2, 5, 3, Cell.getCell(i, j));
						for (int c = 0; c < 10; c++)
							new Fan(tmp);
					}
					k++;
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

	/**
	 * see JudgeAbstract for different kinds of types
	 * 
	 * @param col
	 * @param row
	 * @param player
	 * @return
	 */
	public int getMapCellType(int col, int row, Player player) {
		ArrayList<Player> players = Player.getAllPlayers();

		Cell cell = Cell.getCell(row, col);
		if (!player.isCellInVision(cell))
			return JudgeAbstract.DARK_CELL;

		int tmp = Cell.getCell(row, col).getType();
		if (tmp == JudgeAbstract.SPEEDUP_CELL
				|| tmp == JudgeAbstract.RADAR_CELL
				|| tmp == JudgeAbstract.STONE_CELL
				|| tmp == JudgeAbstract.JUMP_CELL
				|| tmp == JudgeAbstract.FAN_CELL
				|| tmp == JudgeAbstract.HOSPITAL_CELL)
			return JudgeAbstract.BONUS_CELL;

		return tmp;
	}

	/**
	 * see JudgeAbstract for different kinds of types
	 * 
	 * @param col
	 * @param row
	 * @return
	 */
	public int getMapWallType(int col, int row) {
		return Cell.getCell(row, col).getWallType();
	}

	/**
	 * not tested see JudgeAbstract for different kinds of types
	 * 
	 * @param col
	 * @param row
	 * @param id
	 * @return
	 */
	public int getMapWallType(int col, int row, Player player) {
		ArrayList<Player> players = Player.getAllPlayers();
		Cell cell = Cell.getCell(row, col);
		if (!player.isCellInVision(cell))
			return JudgeAbstract.XXXX_WALL;

		return Cell.getCell(row, col).getWallType();
	}

	public void setup() {
		// TODO
	}

	// logic functions
	public void movePlayer(Player player, int direction)
			throws BozorgExceptionBase, InvalidInteger, CallingDeadPerson,
			DirectionIsBlocked, IncompleteOperation {
		if (direction >= 5)
			throw new InvalidInteger();

		if (!player.isAlive())
			throw new CallingDeadPerson();

		try {
			player.move(direction);
		} catch (HasStonedBonus e) {
			// return;
			throw e;
		} catch (DirectionIsBlocked e) {
			throw e;
		} catch (IncompleteOperation e) {
			throw e;
		}
	}

	// TODO
	/**
	 * halate khas ra check nakardim
	 * 
	 * @param player
	 * @param direction
	 * @throws BozorgExceptionBase
	 */
	public void attack(Player player, int direction)
			throws BozorgExceptionBase, InvalidInteger, CallingDeadPerson,
			DirectionIsBlocked, IncompleteOperation, NoPersonToAttack {
		if (direction >= 5)
			throw new InvalidInteger();

		if (!player.isAlive())
			throw new CallingDeadPerson();

		try {
			player.attack(direction);
		} catch (HasStonedBonus e) {
			return;
		} catch (DirectionIsBlocked e) {
			throw e;
		} catch (IncompleteOperation e) {
			throw e;
		} catch (NoPersonToAttack e) {
			throw e;
		}
	}

	/**
	 * 
	 * @param player
	 * @return the thrown?? fan
	 * @throws BozorgExceptionBase
	 *             ( NoFanToThrow ) if there's no fan to throw
	 */
	public Fan throwFan(Player player) throws BozorgExceptionBase,
			CallingDeadPerson, NoFanToThrow {
		if (!player.isAlive())
			throw new CallingDeadPerson();

		Fan fan = player.throwFan();
		if (fan == null)
			throw new NoFanToThrow();

		return fan;
	}

	/**
	 * speed o fan o hospital are unchecked
	 * 
	 * @param id
	 * @throws BozorgExceptionBase
	 */
	public void getGift(Player player) throws BozorgExceptionBase,
			CallingDeadPerson, MoreThanOnePlayerWantToGetAGift, NoGiftToGet {
		if (!player.isAlive())
			throw new CallingDeadPerson();
		try {
			player.getGift();
		} catch (BozorgExceptionBase e) {
			throw e;
		}
	}

	private void checkForFinish() {
		ArrayList<Player> players = Player.getAllPlayers();
		int numOfPlayersInJJ = 0;
		for (Player tmp : players) {
			if (tmp.getCell().getType() == JudgeAbstract.JJ_CELL)
				numOfPlayersInJJ++;
		}
		if (numOfPlayersInJJ == 1) {
			for (Player tmp : players) {
				if (tmp.getCell().getType() == JudgeAbstract.JJ_CELL) {
					Player.setWinner(tmp);
				}
			}
		}
	}

	// Controller functions
	public void next50milis() {

		if ((time % 10) > 5) {
			JJCell.setType(JudgeAbstract.NONE_CELL);
		}// JJcell ba dore tanavobe 5 s Noncell ya jj cell khahad bood
		time += +0.050f;

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

	static public float getStaticTime() {
		return time;
	}

	private void checkCurrentOperations() {
		ArrayList<Player> players = Player.getAllPlayers();
		for (Player player : players) {
			player.continueCurrentOperation();
			if (player.isAlive() == false && player.isHisTimeForRevive())
				player.revive();

		}
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
