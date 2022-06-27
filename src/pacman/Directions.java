package pacman;


public class Directions extends Pacman{

	private static int dx, dy;
	public static int dxmethod() {
		
		 if (left){
	     	 dx = -1;
	          
	     }
		 if (right){
	     	 dx = 1;
	          
	     }
		 if (up || down){
	     	 dx = 0;
	          
	     }
	     return dx;
	}
	public static int dymethod() {
			 
		if (left || right){
	          dy = 0;
	     }
		
		 if (up){	     	 
	          dy = -1;
	          
	     }
		 if (down){	     	 
	         dy = 1;
	         
	     }
	     return dy;
	}	
	
	
	public static int GetDx() {
		return dx;
		
	}
	public static int GetDy() {
		return dy;
		
	}
	
	

	
}

