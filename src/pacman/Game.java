package pacman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Game extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
    private boolean inGame = false;

    private Color mazeColor;
    public static int block = 24;
    public static int field = 15;
    public static int SCREEN_SIZE = field * block;
   
    public static int numOfGhosts = 3;
    
    public String name = "default";
    private int difficulty = 1;
    
	private Image pacmanUp, pacmanLeft, pacmanRight, pacmanDown,pacmanHealth,ghost;
	 
	public static int[][] screenData;
    public final short levelData[][] = {
    		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
    		{0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0},
    		{0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
    		{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
    		{0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0},
    		{0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0},
    		{0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0},
    		{0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0},
    		{0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0},
    		{0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
    		{0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0},
    		{0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0},
    		{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
  
    };
    
    private Timer timer;
    
    Pacman player = new Pacman(); 
    public static ArrayList<Ghost> ghosts = new ArrayList<>();
   

    public Game() {
    	
        loadImages();
        
        addKeyListener(this);
        setFocusable(true);
        setBackground(Color.black);
        
        spawnGhosts();
      
        screenData = new int[field][field];
        mazeColor = new Color(42, 96, 160);
        
        timer = new Timer(40, this);
        timer.start();
        
        
    }
   
    
    private void gamePlay(Graphics g) throws IOException {

        Graphics2D g2d = (Graphics2D) g;

        drawMaze(g2d);
        drawScore(g2d,"highscore.txt");

        if (inGame) {
            	
                player.movePacman();
                drawPlayer(g2d);
                
                for (int i = 0; i < ghosts.size(); i++) {
                	ghosts.get(i).moveGhost();
                	drawGhost(g2d,ghosts.get(i));
    			}
                
                checkMaze();
                checkHit();
                
            
        } else {
            drawScreen(g2d);
   
        }

    }
    
    public static void drawScreen(Graphics2D g2d) {

        g2d.setColor(new Color(42, 96, 160));
        g2d.setFont(new Font("Arial", Font.PLAIN, 25));
        
        g2d.setColor(Color.black);
        g2d.drawRect(2, 2, Game.SCREEN_SIZE, Game.SCREEN_SIZE);
        g2d.fillRect(50, 97, 260, 22);
        String start = "Press space to start";
        g2d.setColor(new Color(241,220,92));
        g2d.drawString(start, (Game.SCREEN_SIZE / 2 - 110), 117);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        g2d.setColor(Color.white);
        g2d.drawString("Press 1 / 2 / 3 to change difficulty", (10), (Game.SCREEN_SIZE - 30));

        g2d.drawString("Press 4 to change name", (10), (Game.SCREEN_SIZE - 5));
    }

    

    public void drawScore(Graphics2D g,String fileName) throws IOException {

        int i;
        String s;
        
        // draw score
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(new Color(241,220,92));
        s = "Score: " + player.getScore();
        g.drawString(s, SCREEN_SIZE / 2 + 100, SCREEN_SIZE + 25);

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.setColor(Color.white);
        
        //drawScoreboard:
        BufferedReader in = new BufferedReader(new FileReader(fileName));
    	String line;
    	String inName[] = new String[11];
    	String inScore[] = new String[11];
    	
    	int n = 0;
    	int offset = 0;
    	while((line = in.readLine()) != null)
    	{
    	    String[] input = line.split(" ");
    	    inScore[n] = input[0];
    	    inName[n] = input[1];
    	    g.drawString(inScore[n], SCREEN_SIZE+25, 110 + offset);
    	    g.drawString(inName[n], SCREEN_SIZE+100, 110 + offset);
    	    n++;
    	    offset += 22;
    	} 
    	in.close();
    	g.setFont(new Font("Arial", Font.PLAIN, 20));
    	g.drawString("Score:", SCREEN_SIZE +25, 85);
    	g.drawString("Name:", SCREEN_SIZE +100, 85);
    	g.setFont(new Font("Arial", Font.PLAIN, 25));
    	g.drawString("High Scores:", SCREEN_SIZE +20, 50);
        
        //drawHealth:
        for (i = 0; i < player.getLife(); i++) {
            g.drawImage(pacmanHealth, i * 28 + 8, SCREEN_SIZE + 5, this);
        }
        
        // drawDifficulity
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("Difficulty level: "+Integer.toString(difficulty), 390, Game.SCREEN_SIZE +25);
        g.drawString("Name: "+name, 390, Game.SCREEN_SIZE );     
        
        
    }
    
    private void drawPlayer(Graphics2D gd) {

        if (Pacman.left) {
        	gd.drawImage(pacmanLeft, player.getX(), player.getY(), this);
        } else if (Pacman.right) {
        	gd.drawImage(pacmanRight, player.getX(), player.getY(),  this);
        } else if (Pacman.up) {
        	gd.drawImage(pacmanUp, player.getX(), player.getY(),  this);
        } else {
        	gd.drawImage(pacmanDown, player.getX(), player.getY(), this);
        }
   }
    
    private void drawGhost(Graphics2D gd, Ghost gh) {
        	gd.drawImage(ghost, gh.getX(), gh.getY(), this);
   }
    
    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        short j = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += block) {
            for (x = 0; x < SCREEN_SIZE; x += block) {
                g2d.setColor(Color.white);
                g2d.setStroke(new BasicStroke(2));

              //If pellet
                if (screenData[i % field][j % field] == 1) {
                	   g2d.fillRect(x + 11, y + 11, 2, 2);
                }
               
               //If field
                g2d.setColor(mazeColor);
                if (screenData[i % field][j % field] == 0) { 
                	g2d.drawRect(x, y, 24,24);
                	g2d.fillRect(x+3, y+3 , 18, 18);
                }
              
               j++;
                
            }
            i++;
        }
    }
    

    private void checkMaze() {

    	int numOfPellets = 0;
    
    	for (int i = 0; i< (field); i++ )
    		for (int j = 0; j< (field); j++ )
        {
        	if (levelData[i][j] == 1)
        		numOfPellets++;
        }
          	
    	boolean cleared = true;
            if (player.getScore() % numOfPellets !=0) {
            	cleared = false;
            }
        //pellets eaten
        if (cleared) {          
            initMaze();
            continueLevel();
        }
    }
    

    private void checkHit() throws IOException  {
    	
    	for (int i = 0; i < ghosts.size(); i++) {
    		if (player.getX() > (ghosts.get(i).getX() -12) && player.getX() < (ghosts.get(i).getX() +12 )
    	    		&& player.getY() > (ghosts.get(i).getY() -12) &&player.getY() < (ghosts.get(i).getY() +12 ) )
    	    	die();
    		
    	}
    }
    
    
    private void die() throws IOException  {

        player.decLife();

        if (player.getLife() == 0) {
            inGame = false;
            player.setLife(3);
            
            updateScores("highscore.txt");

        }
        else 
        	continueLevel();
    }
    
    public void spawnGhosts() {
        for(int i = 0; i < numOfGhosts; i++)
        	ghosts.add(new Ghost());
    }

    
    public void updateScores(String filename) throws IOException {
    	//Read in
    	BufferedReader in = new BufferedReader(new FileReader(filename));
    	String line;
    	String inName[] = new String[11];
    	String inScore[] = new String[11];
    	
    	int n = 0;
    	while((line = in.readLine()) != null)
    	{
    	    String[] input = line.split(" ");
    	    inScore[n] = input[0];
    	    inName[n] = input[1];
    	    n++;
    	}
 
    	in.close();
    	inName[10] = name;
    	System.out.println(name);
    	inScore[10] = player.getScoreStr();
    	
    	//Updatelist:
    	for(int i = 0; i < 10 ; i++) {
    		for(int j = i+1; j < 11 ; j++) {
    			if (Integer.valueOf(inScore[i]) < Integer.valueOf(inScore[j])) {
				
				String tmpScore = inScore[i];
				String tmpName = inName[i];
				
				inScore[i] = inScore[j];
				inName[i] = inName[j];
				
				inScore[j] = tmpScore;
				inName[j] = tmpName;

				}
			}
		
		}
    
    	//Write back
    	PrintWriter writer = new PrintWriter("highscore.txt");
    	for(int j = 0; j < 10 ; j++)
    		writer.println(inScore[j]+" "+inName[j]);
    	writer.close();
    }
    
   
    

    private void continueLevel() {
        player.reset();
        for (int i = 0; i < ghosts.size(); i++) {
        	ghosts.get(i).reset();
		}
 
    }  

    public void initMaze() {
    	player.setScore();
        for (int i = 0; i < field ; i++)
        	for (int j = 0; j < field; j++)
        		screenData[i][j] = levelData[i][j];

        continueLevel();
       
    }



    private void loadImages() {
        pacmanUp = new ImageIcon("src/pacman/images/up.gif").getImage();
        pacmanDown = new ImageIcon("src/pacman/images/down.gif").getImage();
        pacmanLeft = new ImageIcon("src/pacman/images/left.gif").getImage();
        pacmanRight = new ImageIcon("src/pacman/images/right.gif").getImage();
        pacmanHealth = new ImageIcon("src/pacman/images/heart2.png").getImage();
        ghost = new ImageIcon("src/pacman/images/ghost.gif").getImage();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
			gamePlay(g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
   

	@Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
	

	@Override
	public void keyPressed(KeyEvent e) {
		if (inGame) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            	Pacman.left = true;
            	Pacman.up = Pacman.down = Pacman.right = false;
            	
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            	Pacman.right = true;
            	Pacman.up = Pacman.down = Pacman.left = false;
            	
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            	Pacman.up = true;
            	Pacman.right = Pacman.down = Pacman.left = false;
            	
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            	Pacman.down = true;
            	Pacman.right = Pacman.up = Pacman.left = false;
            } 
            else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            	player.setLife(3);
            	inGame = false;
            	
            } 
                
            //not inGame:
        } else {
        	//start game
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                inGame = true;
                initMaze();
            }
            //difficulity 1
            else if (e.getKeyCode() == KeyEvent.VK_1) {
            	player.setSpeed(3);
            	 for (int i = 0; i < ghosts.size(); i++) 
                 	ghosts.get(i).setSpeed(3);
            	 
            	difficulty = 1;
            } 
            //difficulity 2
            else if (e.getKeyCode() == KeyEvent.VK_2) {
            	player.setSpeed(4);
            	 for (int i = 0; i < ghosts.size(); i++) 
                  	ghosts.get(i).setSpeed(3);
          		
            	difficulty = 2;
            } 
            //difficulity 3
            else if (e.getKeyCode() == KeyEvent.VK_3) {
            	player.setSpeed(6);
            	 for (int i = 0; i < ghosts.size(); i++) 
                  	ghosts.get(i).setSpeed(4);
          
            	difficulty = 3;
            	
            	
            }
          //Player name
            else if (e.getKeyCode() == KeyEvent.VK_4) {
        		name = inputName();
             }
                
        }
		
	}

	public String inputName() {
		String response;
		response = (String) JOptionPane.showInputDialog(null,"What's your name?","input",JOptionPane.PLAIN_MESSAGE, null, null, name);
		if(response == null)
			response = "default";
		return response;
	}
	
	

	@Override
	public void keyReleased(KeyEvent e) {		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}
	
	public static void main(String[] args) {

		Game game = new Game();
	    JFrame frame = new JFrame();
	    frame.setTitle("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(580, 440);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(game);      
    
}


}

