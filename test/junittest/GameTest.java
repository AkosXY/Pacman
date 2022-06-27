package junittest;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pacman.Directions;
import pacman.Game;
import pacman.Ghost;
import pacman.Pacman;

public class GameTest {
	Game testGame;
	Pacman testPlayer;
	Directions testDirection;
	Ghost testGhost;
	
	@Before
	public void setUp() {
		testGame = new Game();
		testPlayer = new Pacman();
		testDirection = new Directions();
		testGhost = new Ghost();
	}
	
	@Test
	public void spawGhostsTest() {
		double result = Game.ghosts.size();
		Assert.assertEquals(Game.numOfGhosts, result, 0);
	}
	
	@Test
	public void testPlayerLife() {
		testPlayer.setLife(10);
		Assert.assertEquals(10, testPlayer.getLife(), 0);
		for(int i = 10; i > 0; i--) {
			
			Assert.assertEquals(i, testPlayer.getLife(), 0);
			testPlayer.decLife();
		}
		
		
	}
	
	
	@Test(expected = FileNotFoundException.class)
	public void updateScoreTest() throws IOException {
		
		String nonExistingFile = new String("iDoNotExist.txt");
		testGame.updateScores(nonExistingFile);
	}
	
	
	
	@Test
	public void testPacmanReset() {
		testPlayer.reset();
		int resultX = testPlayer.getX();
		int resultY = testPlayer.getY();
		
		Assert.assertEquals(10 * Game.block, resultX, 0);
		Assert.assertEquals(10 * Game.block, resultY, 0);

		Assert.assertSame(false, Pacman.down);
		Assert.assertSame(false, Pacman.up);
		Assert.assertSame(false, Pacman.left);
		Assert.assertSame(false, Pacman.right);
	}
	
	@Test
	public void testGhostReset() {
		for(int i = 0; i < Game.ghosts.size(); i++) {
			Game.ghosts.get(i).reset();
			int resultX = Game.ghosts.get(i).getX();
			int resultY = Game.ghosts.get(i).getY();
			
			Assert.assertEquals(7 * Game.block, resultX, 0);
			Assert.assertEquals(5 * Game.block, resultY, 0);

			Assert.assertEquals(0, Game.ghosts.get(i).getDX(), 0);
			Assert.assertEquals(0, Game.ghosts.get(i).getDY(), 0);
		}
	}
	
	
	
	@Test
	public void testDirectionsX() {
		int resultX = Directions.dxmethod();
		Assert.assertEquals(0, resultX, 1);
	}
	
	@Test
	public void testDirectionsY() {
		int resultY = Directions.dymethod();
		Assert.assertEquals(0, resultY, 1);
	}
	
	
	@Test
	public void moveGhostTest() {
		for(int i = 0; i < Game.ghosts.size(); i++) {
			Game.ghosts.get(i).reset();
			Game.ghosts.get(i).moveGhost();

			int resultX = Game.ghosts.get(i).getX();
			int resultY = Game.ghosts.get(i).getY();
					
			Assert.assertEquals(7*24, resultX, Game.ghosts.get(i).getSpeed());
			Assert.assertEquals(5*24, resultY, Game.ghosts.get(i).getSpeed());
			
			Assert.assertEquals(0, Game.ghosts.get(i).getDX(), 0);
			Assert.assertEquals(0, Game.ghosts.get(i).getDY(), 0);
				
		}
		
	}
	
	
	
	@Test
	public void initMazeTest() {
		testGame.initMaze();
		for (int i = 0; i < Game.field ; i++)
        	for (int j = 0; j < Game.field; j++)
        		Assert.assertEquals(testGame.levelData[i][j], Game.screenData[i][j]);
		
	}
	
	@Test
	public void continueLevelTest() {
		testPacmanReset();
		testGhostReset();
		
	}
	
	
	@Test
	public void randDirectionTest() {
		for(int i = 0;i < 100; i++) {
			int result = testGhost.randDirection();
			Assert.assertEquals(0, result, 1);
		}
	}
	

	
}
