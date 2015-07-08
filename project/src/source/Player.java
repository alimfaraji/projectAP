package source;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import bozorg.common.exceptions.BozorgExceptionBase;
import bozorg.common.exceptions.DirectionIsBlocked;
import bozorg.common.exceptions.HasStonedBonus;
import bozorg.common.exceptions.IncompleteOperation;
import bozorg.common.exceptions.MoreThanOnePlayerWantToGetAGift;
import bozorg.common.exceptions.NoGiftToGet;
import bozorg.common.exceptions.NoPersonToAttack;
import bozorg.judge.Judge;
import bozorg.judge.JudgeAbstract;

public class Player extends Person implements Serializable{
	// static part
	private static Player winner = null;
	private static ArrayList<Player> allPlayers = new ArrayList<Player>();

	/**
	 * 
	 * @param //x
	 *            name of a player
	 * @return a player with name "name"
	 */
	public static Player getPlayerFromNumber(int name) {
		ArrayList<Player> allPlayers = Player.getAllPlayers();
		for (Player tmp : allPlayers)
			if (tmp.getName() == name)
				return tmp;
		return null;
	}
	
	public static void setAllPlayers(ArrayList<Player> tmp){
		allPlayers = tmp;
	}

	public static int numberOfAlivePlayers() {
		int cnt = 0;
		for (Player tmp : allPlayers)
			if (!tmp.isAlive())
				cnt++;
		return cnt;
	}

	public static void setWinner(Player player) {
		winner = player;
	}

	public static Player getWinner() {
		return winner;
	}

	public static boolean gameIsOver() {
		return winner == null ? false : true;
	}

	public static ArrayList<Player> getAllPlayers() {
		return allPlayers;
	}

	// non static part

	private int speed, power, vision, health, name;
	private boolean isMoving, isAttacking;
	private int hasRadarBonus, hasSpeedUpBonus, hasStonedBonus, hasJumpBonus;
	private Cell nextCell, startCell;
	private ArrayList<Person> enemy;
	private float startTimeForMoving, startTimeForAttacking;
	private float startTimeForRadarBonus, startTimeForSpeedUpBonus,
			startTimeForStonedBonus, startTimeForJumpBonus, deathTime;
	private int directionOfMove;
	
	public float getStartTimeForMoving(){
		return startTimeForMoving;
	}
	
	public boolean isMoving(){
		return isMoving;
	}
	
	public int directionOfMove(){
		return directionOfMove;
	}
	
	public boolean isHisTimeForRevive(){
		if(GameEngine.getStaticTime() - deathTime>=30)
			return true;
		else
			return false;
	}
	public void revive(){
		health = 100;
		this.setCell(startCell);
		setAlive(true);
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getVision() {
		return vision;
	}

	public void setVision(int vision) {
		this.vision = vision;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setAlive(boolean isAlive) {
		super.setAlive(isAlive);
		if(isAlive==false){
			deathTime=GameEngine.getStaticTime();
		}
		if (isAlive == false) { // kill all fans
			ArrayList<Fan> allFans = this.getFans();
			for (Fan tmp : allFans)
				tmp.setAlive(false);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass())
			return false;

		if (this == obj)
			return true;

		Player tmp = (Player) obj;
		if (this.getName() == tmp.getName())
			return true;
		return false;
	}

	public Player(int name, int speed, int power, int vision, Cell cell) {
		super();
		allPlayers.add(this);
		health = 100;
		this.speed = speed;
		this.power = power;
		this.vision = vision;
		this.name = name;
		super.setCell(cell);
		this.startCell = cell;
	}

	/**
	 * 
	 * @return true if player can move
	 */
	private boolean isFreeForMove() {
		return !this.isMoving;
	}

	/**
	 * 
	 * @return true if player can attack
	 */
	private boolean isFreeForAttack() {
		return !this.isAttacking;
	}

	/**
	 * move to cellDirection
	 * 
	 * @param dir
	 * @throws BozorgExceptionBase
	 *             : directionIsBlocked, IncompleteOperation, hasStonedBonus
	 */
	public void move(int dir) throws BozorgExceptionBase {
		if (hasStonedBonus > 0)
			throw new HasStonedBonus();

		Cell nextCell = Cell.getNextCellFromDir(super.getCell(), dir);
		if (hasJumpBonus > 0) {
			nextCell = Cell
					.getNextCellFromDirNoBlockedWay(super.getCell(), dir);
		}

		if (nextCell == null)
			throw new DirectionIsBlocked();

		if (!isFreeForMove())
			throw new IncompleteOperation();

		this.nextCell = nextCell;
		isMoving = true;
		directionOfMove = dir;
		startTimeForMoving = GameEngine.getStaticTime();
	}

	private void FinishMoving() {
		// System.out.println("hi");
		super.setCell(nextCell);
		this.nextCell = null;
		isMoving = false;
	}
	
	
	/**
	 * 
	 * @return return -1 if it is not moving else a number between 0 and 1
	 */
	public float movePercent(){
		if(!this.isMoving)
			return -1;
		else{
			if(speed * (Judge.getStaticTime() - startTimeForMoving)>=1)
				return 1;
			else
				return speed * (Judge.getStaticTime() - startTimeForMoving);
		}
			
	}

	/**
	 * player.health -= x; player will die if x >= player.health
	 * 
	 * @param power
	 */
	private void lessenHealth(int power) {
		if (health > power)
			health -= power;
		else {
			health = 0;
			this.setAlive(false);
		}
	}

	/**
	 * 
	 * @param dir
	 * @throws BozorgExceptionBase
	 *             : hasStonedBonus, directionIsBlocked, IncompleteOperation
	 */
	public void attack(int dir) throws BozorgExceptionBase {
		if (hasStonedBonus > 0)
			throw new HasStonedBonus();

		Cell nextCell = Cell.getNextCellFromDir(super.getCell(), dir);
		if (nextCell == null)
			throw new DirectionIsBlocked();

		if (!isFreeForAttack())
			throw new IncompleteOperation();
		enemy = nextCell.getAllPerson();
		if (enemy == null || enemy.size() == 0)
			throw new NoPersonToAttack();
		isAttacking = true;
		startTimeForAttacking = GameEngine.getStaticTime();
	}

	private void finishAttacking() {
		for (Person tmp : enemy) {
			if (tmp.equals(this)) // if enemy is this person ( attacking curret
									// cell )
				continue;
			if (tmp.getClass().equals(this.getClass())) {// enemy is a player
				Player tmpEnemy = (Player) tmp;
				tmpEnemy.lessenHealth(this.power);
			} else { // enemy is a Fan
				tmp.setAlive(false);
			}
		}
		enemy = null;
		isAttacking = false;
	}
	
	
	/**
	 * 
	 * @return return -1 if it is not attacking else a number between 0 and 1
	 */
	public float attackPercent(){
		if(!this.isAttacking)
			return -1;
		else{
			if(speed * (Judge.getStaticTime() - startTimeForAttacking)>=1)
				return 1;
			else
				return speed * (Judge.getStaticTime() - startTimeForAttacking);
		}
			
	}
	
	

	/**
	 * may return an empty arrayList
	 * 
	 * @return an arrayList of all alive fans
	 */

	public ArrayList<Fan> getFans() {
		ArrayList<Fan> ret = new ArrayList<Fan>();
		ArrayList<Fan> allFans = Fan.getAllFans();
		for (Fan tmp : allFans)
			if (tmp.isAlive())
				if (this.equals(tmp.getOwner()))
					ret.add(tmp);
		return ret;
	}

	/**
	 * may return an empty arrayList
	 * 
	 * @return an arrayList of all freeFans
	 */
	private ArrayList<Fan> getEmptyFans() {
		ArrayList<Fan> ret = new ArrayList<Fan>();
		ArrayList<Fan> fans = this.getFans();
		for (Fan tmp : fans)
			if (tmp.isFree())
				ret.add(tmp);
		return ret;
	}

	/**
	 * throws a fan in current cell
	 * 
	 * @RETURN null if player doesn't have enough fans, fan otherwise
	 */
	public Fan throwFan() {
		ArrayList<Fan> emptyFans = this.getEmptyFans();
		if (emptyFans.size() == 0)
			return null;
		Fan emptyFan = emptyFans.get(0);
		emptyFan.setFree(false);
		emptyFan.setCell(this.getCell());
		return emptyFan;
	}

	// GIFTS
	public void getGift() throws BozorgExceptionBase {
		if (this.getCell().getType() == JudgeAbstract.RADAR_CELL) {
			if (this.getCell().getAllPlayers().size() != 1)
				throw new MoreThanOnePlayerWantToGetAGift();
			this.getCell().setType(JudgeAbstract.NONE_CELL);
			startHavingRadarBonus();
		} else if (this.getCell().getType() == JudgeAbstract.HOSPITAL_CELL) {
			if (this.getCell().getAllPlayers().size() != 1)
				throw new MoreThanOnePlayerWantToGetAGift();
			this.getCell().setType(JudgeAbstract.NONE_CELL);
			getHospitalBonus();
		} else if (this.getCell().getType() == JudgeAbstract.FAN_CELL) {
			if (this.getCell().getAllPlayers().size() != 1)
				throw new MoreThanOnePlayerWantToGetAGift();
			this.getCell().setType(JudgeAbstract.NONE_CELL);
			getFanBonus();
		} else if (this.getCell().getType() == JudgeAbstract.JUMP_CELL) {
			if (this.getCell().getAllPlayers().size() != 1)
				throw new MoreThanOnePlayerWantToGetAGift();
			this.getCell().setType(JudgeAbstract.NONE_CELL);
			startHavingJumpBonus();
		} else if (this.getCell().getType() == JudgeAbstract.SPEEDUP_CELL) {
			if (this.getCell().getAllPlayers().size() != 1)
				throw new MoreThanOnePlayerWantToGetAGift();
			this.getCell().setType(JudgeAbstract.NONE_CELL);
			startHavingSpeedUpBonus();
		} else if (this.getCell().getType() == JudgeAbstract.STONE_CELL) {
			if (this.getCell().getAllPlayers().size() != 1)
				throw new MoreThanOnePlayerWantToGetAGift();
			this.getCell().setType(JudgeAbstract.NONE_CELL);
			startHavingStonedBonus();
		} else
			throw new NoGiftToGet();
	}

	public void startHavingRadarBonus() {
		startTimeForRadarBonus = GameEngine.getStaticTime();
		hasRadarBonus++;
	}

	public void startHavingSpeedUpBonus() {
		startTimeForSpeedUpBonus = GameEngine.getStaticTime();
		hasSpeedUpBonus++;
		this.speed *= 2;
	}

	public void startHavingJumpBonus() {
		startTimeForJumpBonus = GameEngine.getStaticTime();
		hasJumpBonus++;
	}

	public void startHavingStonedBonus() {
		startTimeForStonedBonus = GameEngine.getStaticTime();
		hasStonedBonus++;
	}

	public void getFanBonus() {
		for (int i = 0; i < 3; i++)
			new Fan(this);
	}

	public void getHospitalBonus() {
		this.setHealth(Math.min(100, this.getHealth() + 20));
	}

	public void destroyRadarBonus() {
		hasRadarBonus--;
	}

	public void destroySpeedUpBonus() {
		hasSpeedUpBonus--;
		this.speed /= 2;
	}

	public void destroyJumpBonus() {
		hasJumpBonus--;
	}

	public void destroyStonedBonus() {
		hasStonedBonus--;
	}

	/**
	 * cell may be null
	 * 
	 * @param cell
	 * @return true if cell is in players vision, false otherwise
	 */
	public boolean isCellInVision(Cell cell) {
		if (cell == null)
			return false;
		if (hasRadarBonus > 0)
			return true;

		// check invision cells
		if (Math.abs(cell.getRow() - this.getCell().getRow()) <= this
				.getVision()
				&& Math.abs(cell.getCol() - this.getCell().getCol()) <= this
						.getVision())
			return true;

		// check fans cells
		ArrayList<Fan> allFans = this.getFans();
		for (Fan tmp : allFans)
			if (tmp.isAlive())
				if (!tmp.isFree())
					if (tmp.getCell().equals(cell))
						return true;

		return false;
	}

	/**
	 * 
	 * may return an empty arrayList
	 * 
	 * @return an arrayList of Cells which player can see
	 */
	public ArrayList<Cell> getVisionArrayCells() {
		ArrayList<Cell> ret = new ArrayList<Cell>();
		if (hasRadarBonus > 0) {
			for (int i = 0; i < Cell.getNumOfRows(); i++)
				for (int j = 0; j < Cell.getNumOfCols(); j++)
					ret.add(Cell.getCell(i, j));
			return ret;
		}

		for (int i = 0; i < Cell.getNumOfRows(); i++)
			for (int j = 0; j < Cell.getNumOfCols(); j++) {
				Cell tmpCell = Cell.getCell(i, j);
				if (this.isCellInVision(tmpCell))
					ret.add(tmpCell);
			}

		return ret;
	}

	/**
	 * may return an empty arrayList
	 * 
	 * @return
	 */
	public ArrayList<Player> getPlayersInVision() {
		ArrayList<Player> ret = new ArrayList<Player>();
		ArrayList<Cell> visionArea = this.getVisionArrayCells();
		for (Player tmpPlayer : allPlayers) {
			if (visionArea.contains(tmpPlayer.getCell()))
				ret.add(tmpPlayer);
		}
		return ret;
	}

	/**
	 * change number of alive fans
	 * 
	 * @param number
	 */
	public void addFans(int number) {
		ArrayList<Fan> allFans = this.getFans();
		if (number == allFans.size())
			return;
		if (allFans.size() < number) {
			while (number != allFans.size()) {
				new Fan(this);
				number--;
			}
			return;
		}
		if (number < allFans.size()) {
			for (int i = number; i < allFans.size(); i++) {
				Fan fan = allFans.get(i);
				fan.setOwner(null);
				// fan.setAlive(false);
			}
			return;
		}
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, Integer> getInfo() {
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		ret.put(JudgeAbstract.ROW, this.getCell().getRow());
		ret.put(JudgeAbstract.COL, this.getCell().getCol());
		ret.put(JudgeAbstract.SPEED, this.getSpeed());
		ret.put(JudgeAbstract.NAME, this.getName());
		ret.put(JudgeAbstract.IS_WINNER,
				!this.gameIsOver() ? JudgeAbstract.NOT_FINISHED : winner
						.getName());
		ret.put(JudgeAbstract.POWER, this.getPower());
		ret.put(JudgeAbstract.VISION, this.getVision());
		ret.put(JudgeAbstract.FANS, this.getFans().size());

		return ret;
	}

	public void continueCurrentOperation() {
		float time = GameEngine.getStaticTime();
		if (isMoving && speed * (time - startTimeForMoving) >= 1) {
			FinishMoving();
		}
		if (isAttacking && speed * (time - startTimeForAttacking) >= 1) {
			finishAttacking();
		}
		if (hasRadarBonus > 0 && (time - startTimeForRadarBonus) >= 3) {
			destroyRadarBonus();
		}
		if (hasSpeedUpBonus > 0 && (time - startTimeForSpeedUpBonus) >= 5) {
			destroySpeedUpBonus();
		}
		if (hasStonedBonus > 0 && (time - startTimeForStonedBonus) >= 3) {
			destroyStonedBonus();
		}
		if (hasJumpBonus > 0 && (time - startTimeForJumpBonus) >= 2) {
			destroyJumpBonus();
		}
	}

}