package Junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.Test;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

//import org.junit.Test;

//import org.junit.Test;

import source.Cell;
import source.Fan;
import source.Person;
import source.Player;

import bozorg.common.GameObjectID;
import bozorg.common.exceptions.BozorgExceptionBase;
import bozorg.judge.Judge;
import bozorg.judge.JudgeAbstract;

public class Test {
	Judge judge = new Judge();
	int[] players = { 0, 2 };
	int[][] cellsType = { { 0, 6, 0, 0, 3 }, { 7, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0 }, { 0, 9, 0, 0, 0 }, { 3, 0, 4, 0, 5 } };
	int[][] wallsType = { { 9, 5, 5, 5, 3 }, { 8, 1, 1, 3, 10 },
			{ 10, 8, 0, 2, 10 }, { 10, 12, 4, 6, 10 }, { 12, 5, 5, 5, 6 } };
	
//	@org.junit.Test
//	public void getGiftTest(){
//		 ArrayList<GameObjectID> allPlayers = getPlayerGOI();
//		 GameObjectID saman = allPlayers.get(0);
//		 GameObjectID reza = allPlayers.get(1);
//		 Player samanPlayer = (Player)Person.getPersonFromGOI(saman);
//		 Player rezaPlayer = (Player)Person.getPersonFromGOI(reza);
//		 ArrayList<String> S;
//		 try{
//			 S =  judge.getVision(saman);
//			 for (int i = 0 ; i< S.size(); i ++ )
//				 System.out.println(S.get(i));
//		 }catch(BozorgExceptionBase e){
//			 
//		 }
//		 S = judge.getPlayersInVision(reza);
//		 for (int i = 0; i < S.size(); i ++)
//			 System.out.println(S.get(i));
//	}

	// @org.junit.Test
	// public void AttackTest() {
	// ArrayList<GameObjectID> allPlayers = getPlayerGOI();
	// GameObjectID saman = allPlayers.get(0);
	// GameObjectID reza = allPlayers.get(1);

	// try {
	// judge.movePlayer(saman, JudgeAbstract.DOWN);
	// next();
	// judge.movePlayer(saman, JudgeAbstract.DOWN);
	// next();
	// judge.movePlayer(saman, JudgeAbstract.DOWN);
	// next();
	// judge.movePlayer(saman, JudgeAbstract.DOWN);
	// next();
	// judge.movePlayer(saman, JudgeAbstract.LEFT);
	// next();
	// judge.movePlayer(saman, JudgeAbstract.LEFT);
	// next();
	// judge.movePlayer(saman, JudgeAbstract.LEFT);
	// next();
	// // judge.movePlayer(saman, JudgeAbstract.LEFT);
	// } catch (BozorgExceptionBase e) {
	// System.out.println("oops");
	// }
	//
	// assertEquals(4, Person.getPersonFromGOI(saman).getCell().getRow());
	// assertEquals(1, Person.getPersonFromGOI(saman).getCell().getCol());
	// Player rezaPlayer = (Player) Person.getPersonFromGOI(reza);

	// for (int i = 0 ; i < 100 ; i++ ){
	// try{
	// judge.attack(saman, JudgeAbstract.LEFT);
	// next();
	// System.out.println(rezaPlayer.getHealth());
	// if (Player.gameIsOver()){
	// System.out.println("game is over");
	// System.out.println(Player.getWinner().getName());
	// break;
	// }
	// }catch(BozorgExceptionBase e){
	// System.out.println("oops 2");
	// }
	//
	// }
	// try {
	// judge.throwFan(saman);
	// next();
	// System.out.println("1");
	// judge.movePlayer(saman, JudgeAbstract.RIGHT);
	// next();
	// System.out.println("2");
	// judge.attack(reza, JudgeAbstract.RIGHT);
	// next();
	// System.out.println("3");
	// }catch(BozorgExceptionBase e){
	// System.out.println("oops");
	// }
	//
	// }

	private void next() {
		for (int i = 0; i < 1000; i++)
			judge.next50milis();
	}

	ArrayList<GameObjectID> allGOI;

	ArrayList<GameObjectID> getPlayerGOI() {
		ArrayList<GameObjectID> ret = new ArrayList<GameObjectID>();
		for (GameObjectID tmp : allGOI) {
			if (Person.getPersonFromGOI(tmp) instanceof Player) {
				ret.add(tmp);
			}
		}
		return ret;
	}

	ArrayList<GameObjectID> getFanGOI() {
		ArrayList<GameObjectID> ret = new ArrayList<GameObjectID>();
		for (GameObjectID tmp : allGOI)
			if (Person.getPersonFromGOI(tmp) instanceof Fan)
				ret.add(tmp);
		return ret;
	}

	@Before
	public void createMapTest() {

		judge.loadMap(cellsType, wallsType, players);
		allGOI = Person.getAllGameObjectIDs();
		// loaded
	}

	// @org.junit.Test
	// public void getCellTypeTest(){
	// for (int i = 0 ; i < 5 ; i++ )
	// for (int j = 0 ; j < 5 ; j++ )
	// assertEquals(cellsType[i][j], judge.getMapCellType(j, i));
	// }

	// @org.junit.Test
	// public void getMapCellTypeTest(){
	// ArrayList<GameObjectID> playerGOI = getPlayerGOI();
	// GameObjectID saman = playerGOI.get(0);
	// assertEquals(0, judge.getMapCellType(4, 3, saman));
	// assertEquals(2, judge.getMapCellType(4, 4, saman));
	//
	// GameObjectID reza = playerGOI.get(1);
	// assertEquals(1, judge.getMapCellType(2, 2, reza));
	// }

	// @org.junit.Test
	// public void getMapWallsTypeTest(){
	// for (int i = 0 ;i < 5 ; i ++ )
	// for (int j = 0 ; j < 5 ; j++ )
	// assertEquals(wallsType[i][j], judge.getMapWallType(j,i));
	// }
	//
	// @org.junit.Test
	// public void getMapWallsType2Test(){
	//
	// }
	//
	// @org.junit.Test
	// public void moveTest(){
	// ArrayList<GameObjectID> playerGOI = getPlayerGOI();
	// GameObjectID saman = playerGOI.get(0);
	// try{
	// judge.movePlayer(saman, JudgeAbstract.DOWN);
	// }catch(BozorgExceptionBase e){
	// fail();
	// }
	// for (int i = 0 ; i < 1000 ; i++ )
	// judge.next50milis();
	// Player S = (Player)Person.getPersonFromGOI(saman);
	// assertEquals(1, S.getCell().getRow());
	// assertEquals(4, S.getCell().getCol());
	//
	// try{
	// judge.movePlayer(saman, JudgeAbstract.RIGHT);
	// }catch(BozorgExceptionBase e){
	// System.out.println("dorost");
	// }
	//
	// }

}
