package source;

import java.util.ArrayList;
import java.util.HashMap;

import bozorg.judge.JudgeAbstract;

public class Cell {
	// Static Part
	private static Cell[][] allCells = new Cell[110][110];
	private static int numOfRows;
	private static int numOfCols;

	public static int getNumOfRows() {
		return numOfRows;
	}

	public static int getNumOfCols() {
		return numOfCols;
	}

	/**
	 * 
	 * @param current
	 *            cell
	 * @param dir
	 * @return null if such cell doesn't exist, cell
	 */
	public static Cell getNextCellFromDir(Cell cell, int dir) {
		if (dir == 0 && !cell.upIsBlocked() && cell.getRow() != 0)
			return Cell.getCell(cell.getRow() - 1, cell.getCol());
		if (dir == 1 && !cell.rightIsBlocked()
				&& cell.getCol() != Cell.getNumOfCols() - 1)
			return Cell.getCell(cell.getRow(), cell.getCol() + 1);
		if (dir == 2 && !cell.downIsBlocked()
				&& cell.getRow() != Cell.getNumOfRows() - 1)
			return Cell.getCell(cell.getRow() + 1, cell.getCol());
		if (dir == 3 && !cell.leftIsBlocked() && cell.getCol() != 0)
			return Cell.getCell(cell.getRow(), cell.getCol() - 1);
		if (dir == 4)
			return cell;
		return null;
	}

	/**
	 * 
	 * @param cell
	 * @param dir
	 * @return the cell in direction
	 */
	public static Cell getNextCellFromDirNoBlockedWay(Cell cell, int dir) {
		if (dir == 0 && cell.getRow() != 0)
			return Cell.getCell(cell.getRow() - 1, cell.getCol());
		if (dir == 1 && cell.getCol() != Cell.getNumOfCols() - 1)
			return Cell.getCell(cell.getRow(), cell.getCol() + 1);
		if (dir == 2 && cell.getRow() != Cell.getNumOfRows() - 1)
			return Cell.getCell(cell.getRow() + 1, cell.getCol());
		if (dir == 3 && cell.getCol() != 0)
			return Cell.getCell(cell.getRow(), cell.getCol() - 1);
		if (dir == 4)
			return cell;
		return null;
	}

	public static void setMapSize(int row, int col) {
		numOfCols = col;
		numOfRows = row;
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @return cell , null if it doens't exist
	 */
	static public Cell getCell(int row, int col) {
		if (row < 0 || col < 0 || row >= numOfRows || col >= numOfCols)
			return null;
		return allCells[row][col];
	}

	// NonStatic Part

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass())
			return false;

		if (this == obj)
			return true;

		Cell tmp = (Cell) obj;
		if (this.getCol() == tmp.getCol() && this.getRow() == tmp.getRow())
			return true;
		return false;
	}

	private int type, wallType;
	private int row, col;
	private boolean up = false, down = false, left = false, right = false;

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean upIsBlocked() {
		return up;
	}

	public boolean downIsBlocked() {
		return down;
	}

	public boolean leftIsBlocked() {
		return left;
	}

	public boolean rightIsBlocked() {
		return right;
	}

	public Cell(int type, int walls, int row, int col) {
		allCells[row][col] = this;
		this.col = col;
		this.row = row;
		this.type = type;
		makeWalls(walls);
	}

	public int getWallType() {
		return wallType;
	}

	private void makeWalls(int walls) {
		this.wallType = walls;
		if (walls % 2 == 1)
			up = true;

		walls /= 2;
		if (walls % 2 == 1)
			right = true;

		walls /= 2;
		if (walls % 2 == 1)
			down = true;

		walls /= 2;
		if (walls % 2 == 1)
			left = true;

	}

	/**
	 * 
	 * @param cell
	 * @return player or fan, null if doesn't exist
	 */
	public Person getPerson() {
		ArrayList<Player> tmpPlayer = Player.getAllPlayers();
		for (Player player : tmpPlayer)
			if (player.isAlive())
				if (player.getCell().equals(this))
					return player;

		ArrayList<Fan> tmpFan = Fan.getAllFans();
		for (Fan fan : tmpFan)
			if (fan.isAlive())
				if (fan.getCell().equals(this))
					return fan;
		return null;
	}

	/**
	 * 
	 * @return all Fans in this cell, may return an empty arrayList
	 */
	public ArrayList<Fan> getAllFans() {
		ArrayList<Fan> ret = new ArrayList<Fan>();
		ArrayList<Fan> allFans = Fan.getAllFans();
		for (Fan fan : allFans)
			if (fan.isAlive())
				if (!fan.isFree())
					if (fan.getCell().equals(this))
						ret.add(fan);
		return ret;
	}

	/**
	 * 
	 * @return all Players in this cell, may return an empty arrayList
	 */
	public ArrayList<Player> getAllPlayers() {
		ArrayList<Player> ret = new ArrayList<Player>();
		ArrayList<Player> allPlayers = Player.getAllPlayers();
		for (Player player : allPlayers)
			if (player.isAlive())
				if (player.getCell().equals(this))
					ret.add(player);
		return ret;
	}

	/**
	 * 
	 * @return all Persons in this cell, may return an empty arrayList
	 */
	public ArrayList<Person> getAllPerson() {
		ArrayList<Person> ret = new ArrayList<Person>();
		 ret.addAll(getAllPlayers());
		 ret.addAll(getAllFans());
		return ret;
	}
}