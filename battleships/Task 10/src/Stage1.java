import java.awt.Point;
import java.util.ArrayList;

/*
Create the battleships game!
This project is to create a digital version of the popular board game known as battleships. Battleships is a two player game with 2 phases. 
In the first phase the player’s ships are placed on the board.
In the second phase each player takes it in turns to select grid squares on the board in an attempt to find and destroy their opponent’s ships. 
Once one player has lost all of their ships the game is over and the player who still has ships on the board is the winner.

Each player has a number of ships including: 2 patrol boats (1 x 2), 2 battleships (1 x 3), 1 submarine (1 x 3), 1 destroyer (1 x 4) and 1 carrier (1 x 5).
There are a number of rules that players must follow: 
•	2 Ships cannot occupy the same space on the board. 
•	If a player scores a ‘hit’ on their opponent, then they get a second shot.
•	Ships cannot be moved once they have been placed.

Advice
Battleships is a seemingly simple strategy game but without careful planning it can be easy to become “lost” in the project. 
It is recommended that you attempt to complete the project in a set of stages where with each stage you increase the level of complexity. 
Remember that as the complexity of your project increases you may find that you wish to go back to a previous version in some cases so it is highly advised that you create versioned copies of your project at each stage. 
An advised set of stages are:
Stage 1:
A 3 x 3 grid with one ship that is 2 pieces long is placed on in the grid.
Stage 2:
A 3 x 3 grid with 2 ships that are 2 pieces long and placed on the grid with validation to ensure legal placement.
Stage 3:
Two 3 x 3 grids with 2 ships where players take alternating turns taking shots at the other grid.
Stage 4:
Differentiation between ‘hits’ and ‘misses’ implemented.
Stage 5:
Checks for sunk ships with game over when one player has lost all their ships.
Stage 6:
Two 12 x 12 grids with all 7 ships placed in valid locations. 
Stage 7:
Players can select the placement of their ships on the grid during phase 1. 
Stage 8:
Implementation of an AI player.
Stage 9:
Players can only see their own grid with shots taken.
Stage 10:
Implementation of a GUI has been attempted.
 */

/*	Stage 1:
  
	A 3 x 3 grid with one ship that is 2 pieces long is placed on in the grid.

 */
public class Stage1 {

static Grid playerAGrid = new Grid(3);
static Grid playerBGrid = new Grid(3);

	public static void main(String[] args) {
	
		Ship destroyer1 = new Ship(new Point(0,0),new Point(0,1));
		playerAGrid.addShipToGrid(destroyer1);
		playerAGrid.printGrid();
		
	}
}

class Ship extends Point{
	static int id_counter = 0;
	private int shipID;
	private boolean destroyed;
	ArrayList<Point> coOrds;
	Point addressFrom;
	Point addressToo;
	Ship(Point input_addressFrom, Point input_addressToo){
		this.shipID = id_counter;
		id_counter++;
		coOrds = new ArrayList<Point>();
		this.addressFrom = input_addressFrom;
		this.addressToo = input_addressToo;
		organiseAddresses();
	}
	
	private void organiseAddresses(){
		//find out coordinates of the ship and put them in order
		//in this case, since were only doing 2 spot ships, just add them anyways
		coOrds.add(addressFrom);
		coOrds.add(addressToo);
	}
	
	public ArrayList<Point> getShipCoords(){
		return this.coOrds;
	}
	
	public String toString(){
		return "ship";
	}
}

class Grid{
	private Point[][] grid;
	Grid(int dimension){
		//dimension must be larger than 3!
		if(dimension>=3){
			grid = new Point[dimension][dimension];
			fillGridWithBlankPoints();
		}else{
		//error
		
		}
	}
	
	private void fillGridWithBlankPoints(){
		for(int i = 0; i<grid[0].length;i++){//row
			for(int a = 0; a<grid.length;a++){//column
				grid[i][a]= new Point(i,a);
			}
		}
	}
	
	public void addShipToGrid(Ship inputShip){
		//iterate through grip and add ship
		
		ArrayList<Point> shipsCoords = inputShip.getShipCoords(); 
				
		for(int i = 0; i<grid[0].length;i++){//for each row in grid
			for(int a = 0; a<grid.length;a++){//for each column in grid
				for(Point x: shipsCoords){     //for each coord in the ship
					if(x.getX()==grid[i][a].getX()){ //if the x coords match
						if(x.getY()==grid[i][a].getY()){ //if the y coords match
							grid[i][a]=inputShip;             //replace it with the ship as it's on that point on the grid
							//System.out.println("added ship at point: " + x.toString());
						}
					}
				}
			}
		}
	}
	
	public void printGrid(){
		for(int i = 0; i<grid[0].length;i++){//for each row in grid
			for(int a = 0; a<grid.length;a++){//for each column in grid
				//System.out.print("| o ");
				//System.out.println(grid[i][a].getClass().getSimpleName());
				if(grid[i][a].getClass().getSimpleName()=="Ship"){
					System.out.print("| s ");
				}else{
					System.out.print("| o ");
				}
				if(a==grid.length-1){
					System.out.print("|");
				}
			}
			System.out.println("\n");
		}
	}
}
