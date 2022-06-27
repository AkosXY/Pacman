package pacman;

public class Pacman {
	 private int PACMAN_SPEED = 3 ;
	 private  int x, y, p_dx, p_dy;
	 public static boolean left, right, up, down;
	 private static int life = 3, score = 0;
	
	 
	 public void setScore()
	 {
		score = 0;
	 }
	 
	 public int getScore()
	 {
		return score;
	 }
	 
	 public String getScoreStr() {
		 return Integer.toString(score);
	 }
	 
	 public void setSpeed(int speed)
	 {
		PACMAN_SPEED = speed;
	 }
	 
	 public void setLife(int inc) {
		 life = inc;
	 }
	 
	 public void decLife() {
		 life -= 1;
	 }
	 
	 
	 public int getLife() {
		 return life;
	 }
	 


	 public  int getX()
	 {
		 return x;
	 }
	 public  int getY()
	 {
		 return y;
	 }
	 
	 
	 public void movePacman() {

	        int cp = 0;
	        int tmp_left = 0;
	        int tmp_right = 0;
	        int tmp_up = 0;
	        int tmp_down = 0;
	        
	        //get direction pacman is facing
	        int tmp_dx = Directions.dxmethod();
	        int tmp_dy = Directions.dymethod();

	        if (x % Game.block == 0 && y % Game.block == 0) {
	        	//Rows 
	            int i = (x / Game.block + Game.field *  (y / Game.block))/15;
	            
	        	//Collumns
	            int j = (x / Game.block + Game.field *  (y / Game.block))%15;
	            
	            
	            
	            cp = Game.screenData[i][j];
	            //Surroundings
	            tmp_left = Game.screenData[i][j-1];
	            tmp_right = Game.screenData[i][j+1];
	            tmp_up = Game.screenData[i-1][j];
	            tmp_down = Game.screenData[i+1][j]; 
	            
	            //Eat
	            if (cp == 1) {
	            	Game.screenData[i][j] = (cp = 3);
	                score++;
	            }
	            //if not on wall, dont change direction
	            if (cp != 0) {
	                    p_dx = tmp_dx;
	                    p_dy = tmp_dy;
	           }

	           // Left collide
	            if (((p_dx == -1 && p_dy == 0 && tmp_left == 0))) {
                   p_dx = 0;
                   p_dy = 0;
               }
	          
	         // Right collide
	            if (((p_dx == 1 && p_dy == 0 && tmp_right == 0))) {
                   p_dx = 0;
                   p_dy = 0;
               }
	           
	         // Up collide
	            if (((p_dx == 0 && p_dy == -1 && tmp_up == 0))) {
                   p_dx = 0;
                   p_dy = 0;
               }
	          
	         // Down collide
	            if (((p_dx == 0 && p_dy == 1 && tmp_down == 0))) {
                   p_dx = 0;
                   p_dy = 0;
               }
	           	           
	            
	        }
	        // Actual movement
	        x = x + PACMAN_SPEED * p_dx;
	        y = y + PACMAN_SPEED * p_dy;
	        
	    }

	    
	    
	    public void reset() {
	    	
	    	x = 10 * Game.block;
	        y = 10 * Game.block;
	        p_dx = 0;
	        p_dy = 0;
	        left = false;
	        right = false;
	        up = false;
	        down = false;
	        
	       
	    }
}
