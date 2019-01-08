package Algo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Coords.MyCoords;
import Geom.Point3D;
import Robot.Play;
import types.Game;

class pathTest {
	private MyCoords m=new MyCoords();
	private String file_name = "data/Ex4_OOP_example6.csv";
    Play playTest=new Play(file_name);
    Game game=new Game(playTest);
    
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAdj() {
		Point3D pointpackman=game.getFruit().get(0).getP();
		System.out.println(game.getFruit().get(0).getP());
		pointpackman=m.add(pointpackman, new Point3D(1,1,0));
		playTest.setInitLocation(pointpackman.y(), pointpackman.x());
		System.out.println(pointpackman);
		game.update(playTest);
		path p = new path(game,1283 ,583);
		distance expected=new distance();
		expected.getPath().add(new Point3D(750.0,46.0,0.0));
		expected.getPath().add(new Point3D(749.0,47.0,0.0));
		distance actual=p.shortPath();
		System.out.println(actual.getPath().get(0)+","+actual.getPath().get(1));
		assertTrue(expected.getPath().get(0).equals(actual.getPath().get(0))&&(expected.getPath().get(1).equals(actual.getPath().get(1))));
		
	}

}
