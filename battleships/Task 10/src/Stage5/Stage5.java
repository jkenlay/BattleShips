package Stage5;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

/*

Stage 5:
Checks for sunk ships with game over when one player has lost all their ships.

 */

public class Stage5 {
	static Grid player1Grid;
	static Grid player2Grid;
	public static void main(String[] args) {
		
		try {
			player1Grid = new Grid(3,1,"james");
			player2Grid = new Grid(3,2,"dan");
		} catch (GridException e) {
			e.printStackTrace();
		}
		
		Ship destroyer1 = new Ship(new Point(0,1),new Point(0,2),1);
		Ship destroyer2 = new Ship(new Point(2,1),new Point(2,2),2);
		
		try{
			player1Grid.addShipToGrid(destroyer1);
			player2Grid.addShipToGrid(destroyer2);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
	
		
		int totalnumberofsquaresingrid = player1Grid.getGridTotalSquares();
		System.out.println("Welcome to battleships! Stage 5.");
		System.out.println("There are a total of " + totalnumberofsquaresingrid + " squares in the grid");
		System.out.println("Player 1 will fire first:");
		//2 players, 1 turn each.
		Scanner player2Move = new Scanner(System.in);
		Scanner player1Move = new Scanner(System.in);
		player1Grid.printGrid();
		player2Grid.printGrid();
		while(totalnumberofsquaresingrid>0){
						
			//player 1 x coord
			System.out.println("Player 1, enter x co-ord, then press enter");
			int player1x = player1Move.nextInt();
			
			//player 1 y coord
			System.out.println("Player 1, enter y co-ord, then press enter");
			int player1y = player1Move.nextInt();
			
			String Player1shotResult = player2Grid.shotFiredAt(new Point(player1x,player1y));
			while((Player1shotResult.equalsIgnoreCase("alreadyhit"))||(Player1shotResult.equalsIgnoreCase("outsidegrid"))){
				
					//if its been hit
				if(Player1shotResult.equalsIgnoreCase("alreadyhit")){
					System.out.println("You've already fired at this square, please try again");	
				}else{
					//if its outside the grid
					System.out.println("these coordinates are outside the grid, please try again");
				}
				
				//re-take the shot
				//player 1 x coord
				System.out.println("Player 1, enter x co-ord, then press enter");
				player1x = player1Move.nextInt();
				
				//player 1 y coord
				System.out.println("Player 1, enter y co-ord, then press enter");
				player1y = player1Move.nextInt();
				
				//fire the shot
				Player1shotResult = player2Grid.shotFiredAt(new Point(player1x,player1y));
			}
			System.out.println("Player 1 shooting at: " + player1x+"," + player1y+ "...");
			
			//print result of shot
			System.out.println(Player1shotResult);
			
			//update screen
			player2Grid.printGrid();
			
			
			
			//if the player has lost all their ships
			if(player2Grid.allShipsDestroyed()){
				System.out.println("GAME OVER, PLAYER 1 WINS!!");
				break;
			}
			//game over.
			
			
			//player 2 x coord
			System.out.println("Player 2, enter x co-ord, then press enter");
			int player2x = player2Move.nextInt();
			
			//player 2 y coord
			System.out.println("Player 2, enter y co-ord, then press enter");
			int player2y = player2Move.nextInt();
	
			String Player2shotResult = player1Grid.shotFiredAt(new Point(player2x,player2y));
			while((Player2shotResult.equalsIgnoreCase("alreadyhit"))||(Player2shotResult.equalsIgnoreCase("outsidegrid"))){
				
					//if its already hit
				if(Player2shotResult.equalsIgnoreCase("alreadyhit")){
					System.out.println("You've already fired at this square, please try again");	
				}else{
					//its outside of grid!
					System.out.println("these coordinates are outside the grid, please try again");
				}

				//re-take the shot
				//player 2 x coord
				System.out.println("Player 2, enter x co-ord, then press enter");
				player2x = player2Move.nextInt();
				
				//player 2 y coord
				System.out.println("Player 2, enter y co-ord, then press enter");
				player2y = player2Move.nextInt();

				//fire the shot
				Player2shotResult = player1Grid.shotFiredAt(new Point(player2x,player2y));
			}
			System.out.println("Player 2 shooting at: " + player2x+"," + player2y+ "...");
			
			//print result of shot
			System.out.println(Player2shotResult);
			
			//update screen
			player1Grid.printGrid();
			
			
			//if the player has lost all their ships
			if(player1Grid.allShipsDestroyed()){
				System.out.println("GAME OVER, PLAYER 2 WINS!!");
				break;
			}
			//game over.
			
			totalnumberofsquaresingrid--; //max number of turns left -=1
		}
		System.out.println("End of game");
	}
}

class Ship{
	private int shipID;
	private ArrayList<GridPoint> CoOrds;

	public Ship(Point inputPointFrom, Point inputPointToo,int inputShipID){
		this.shipID = inputShipID;
		CoOrds = new ArrayList<GridPoint>();
		CoOrds.add(new GridPoint(inputPointFrom,inputShipID));
		CoOrds.add(new GridPoint(inputPointToo,inputShipID));	
	}
	
	public int getShipID(){
		return this.shipID;
	}
	
	public ArrayList<GridPoint> getShipCoords(){
		return this.CoOrds;
	}
}

class GridPoint extends Point{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int shipID;
	private boolean destroyed = false;

	GridPoint(Point inputPoint,int inputShipID){
		this.shipID=inputShipID;
		this.setLocation(inputPoint.getX(),inputPoint.getY());
	}

	public int getShipID(){
		return this.shipID;
	}
	
	public double getX(){
		return super.getX();
	}
	
	public double getY(){
		return super.getY();
	}
	
	public void setHitTrue(){
		this.destroyed = true;
	}
	
	public boolean getHitStatus(){
		return this.destroyed;
	}
}

class Grid{
	private GridPoint[][] grid;
	private int playerID;
	private String player_name;
	Grid(int dimension, int player_id, String inputplayer_name) throws GridException{
		//dimension must be larger than 3!
		if(dimension>=3){
			grid = new GridPoint[dimension][dimension];
			fillGridWithBlankPoints();
		}else{
		//error
			throw new GridException("Grid size must be > 2");
		}
		this.playerID = player_id;
		this.player_name = inputplayer_name;
	}
	
	public int getGridTotalSquares(){
		return this.grid[0].length*this.grid.length;
	}
	public boolean allShipsDestroyed(){
		boolean toreturn = true;
		for(int i = 0; i<grid[0].length;i++){//for each row in grid
			for(int a = 0; a<grid.length;a++){//for each column in grid
				GridPoint currentGridPoint = grid[i][a]; 
				if(currentGridPoint.getShipID()!=0){ //it is a ship
					if(currentGridPoint.getHitStatus()==false){ //if it hasnt been destroyed then the game isnt over
						toreturn = false;
					}
				}
			}
		}
		
		return toreturn;
	}
	
	public String shotFiredAt(Point strike){
		String toreturn = "";
		for(int i = 0; i<grid[0].length;i++){//row
			for(int a = 0; a<grid.length;a++){  //column
				if((strike.getX()==grid[i][a].getX())&&(strike.getY()==grid[i][a].getY())){ //if its a match
					GridPoint currentPoint = grid[i][a];
					if(currentPoint.getHitStatus()==false){ //if it hasnt been hit
						
						//update the hit status
						currentPoint.setHitTrue();
						
						if(grid[i][a].getShipID()!=0){ //if its a ship
							toreturn = "HIT!!!! BOOM!!!";	
						}else{						   //hitting water
							toreturn ="Miss";
						}
					}else{
						toreturn = "alreadyhit";
					}
				}else{
					//is it outside the grids area?
					if(strike.getX()>=grid[0].length){
						toreturn = "outsidegrid";
					}else if(strike.getY()>=grid.length){
						toreturn = "outsidegrid";
					}
				}
			}
		}
		return toreturn;
	}
	
	private void fillGridWithBlankPoints(){
		for(int i = 0; i<grid[0].length;i++){//row
			for(int a = 0; a<grid.length;a++){  //column
				grid[i][a]= new GridPoint(new Point(i,a),0);        //add a blank point
			}
		}
	}
	
	public void addShipToGrid(Ship inputShip) throws GridException{

		//check the points arent outside the grid
		for(Point p:inputShip.getShipCoords()){
			if(p.getX()>=grid[0].length){
				throw new GridException("Ship outside of grid on X grid at: " + p.getX() + " ship ID: " + inputShip.getShipID());
			}else if(p.getY()>=grid.length){
				throw new GridException("Ship outside of grid on Y grid at: " + p.getY() + " ship ID: " + inputShip.getShipID());
			}
		}
		
		//check there are no ships already on this part of the grid
		for(int i = 0; i<grid[0].length;i++){//for each row in grid
			for(int a = 0; a<grid.length;a++){//for each column in grid
				for(Point p: inputShip.getShipCoords()){//for each coord in the ship
					if(p.getX()==grid[i][a].getX()){ 	  //if the x coords match
						if(p.getY()==grid[i][a].getY()){ 	//if the y coords match
							if(grid[i][a].getShipID()!=0){    //there is a ship here already
								printGrid();
								throw new GridException("Ship already placed on this point on the grid");
							}
						}
					}
				}
			}
		}
		
		for(int i = 0; i<grid[0].length;i++){//for each row in grid
			for(int a = 0; a<grid.length;a++){     //for each column in grid
				for(GridPoint x: inputShip.getShipCoords()){//for each coord in the ship
					if(x.getX()==grid[i][a].getX()){ 	     //if the x coords match
						if(x.getY()==grid[i][a].getY()){       //if the y coords match
							grid[i][a]=x;                        //replace it with the Shippoint as it's on that point on the grid
						}
					}
				}
			}
		}
	}
	
	public void printGrid(){
		System.out.println("\nPlayer "+this.playerID+"'s  grid:");
		for(int i = 0; i<grid[0].length;i++){//for each row in grid
			for(int a = 0; a<grid.length;a++){//for each column in grid
				GridPoint currentGridPoint = grid[i][a]; 
				
				if(currentGridPoint.getShipID()!=0){ //it is a ship
					if(currentGridPoint.getHitStatus()){
						System.out.print("  |   x" + currentGridPoint.getShipID());
					}else{
						System.out.print("  |   s" + currentGridPoint.getShipID());
					}	
				}else{
					if(currentGridPoint.getHitStatus()){
						System.out.print("  |   x ");
					}else{
						System.out.print("  |   o ");
					}
				}
				
				if(a==grid.length-1){
					System.out.print("  |");
				}
			}
			System.out.println("\n");
		}
	}
}
class GridException extends Exception{
	public GridException(String message){
		super(message);
	}
}