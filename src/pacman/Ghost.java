package pacman;
import java.util.Random;

public class Ghost {
	private int GHOST_SPEED = 3;
	private  int x, y, g_dx, g_dy;
	
	
	public void setSpeed(int speed){
		GHOST_SPEED = speed;
	 }
	
	public int getSpeed(){
		return GHOST_SPEED;
	 }
	
	public  int getX(){
		 return x;
	 }
	
	 public  int getY(){
		 return y;
	 }
	 
	 
	 public int getDX(){
		 return g_dx;
	 }
	 
	 public int getDY(){
		 return g_dy;
	 }
	 
	 public int randDirection(){
		 Random rand = new Random();
		 return rand.nextInt(3)-1;
	 }
	 
	 
	 public void moveGhost() {
			
		 int cp = 0;
	     int tmp_left = 0;
	     int tmp_right = 0;
	     int tmp_up = 0;
	     int tmp_down = 0; 
	     
	     int tmp_dy;
	     int tmp_dx = randDirection();
	     if (tmp_dx == 0)
	     		tmp_dy = randDirection();
	     else 
	    	 	tmp_dy = 0;
	     
	     
	     if (x % Game.block == 0 && y % Game.block == 0) {
	        	//Rows
	            int i = (x / Game.block + Game.field * (int) (y / Game.block))/15;
	            //Collumns
	            int j = (x / Game.block + Game.field * (int) (y / Game.block))%15;
	            
	            
	            //System.out.println(i);
	            cp = Game.screenData[i][j];
	            
	            tmp_left = Game.screenData[i][j-1];
	            tmp_right = Game.screenData[i][j+1];
	            tmp_up = Game.screenData[i-1][j];
	            tmp_down = Game.screenData[i+1][j]; 
	            
	            
	                if (cp != 0) {
	                    g_dx = tmp_dx;
	                    g_dy = tmp_dy;
	                }
	            

	           // Left collide
	            if (((g_dx == -1 && g_dy == 0 && tmp_left == 0))) {
                g_dx = 0;
                g_dy = 0;
            }
	          
	         // Right collide
	            if (((g_dx == 1 && g_dy == 0 && tmp_right == 0))) {
                g_dx = 0;
                g_dy = 0;
            }
	           
	         // Up collide
	            if (((g_dx == 0 && g_dy == -1 && tmp_up == 0))) {
                g_dx = 0;
                g_dy = 0;
            }
	          
	         // Down collide
	            if (((g_dx == 0 && g_dy == 1 && tmp_down == 0))) {
                g_dx = 0;
                g_dy = 0;
            }
	           
	           
	            
	        }
	        x = x + GHOST_SPEED * g_dx;
	        y = y + GHOST_SPEED * g_dy;
	        
	    }
	 
	 
	 public void reset() {
	    	
	    	x = 7 * Game.block;
	        y = 5 * Game.block;
	        g_dx = 0;
	        g_dy = 0;
	       
	       
	    }
	
		 
		 
	 }
	 

