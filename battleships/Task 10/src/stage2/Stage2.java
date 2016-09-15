package stage2;
import java.awt.Point;
import java.util.ArrayList;

/*

Stage 2:

A 3 x 3 grid with 2 ships that are 2 pieces long and placed on the grid with validation to ensure legal placement.

 */

public class Stage2 {
	static Grid playerAGrid;
	static Grid playerBGrid;
	public static void main(String[] args) {
		
		try {
			playerAGrid = new Grid(3);
			playerBGrid = new Grid(3);
		} catch (GridException e) {
			e.printStackTrace();
		}
		
		Ship destroyer1 = new Ship(new Point(0,1),new Point(0,2),0);
		Ship destroyer2 = new Ship(new Point(2,1),new Point(2,2),1);
		
		try{
			playerAGrid.addShipToGrid(destroyer1);
			System.out.println("added ship 1");
			playerAGrid.addShipToGrid(destroyer2);
			System.out.println("added ship 2");
		}catch (Exception e){
			e.printStackTrace();
		}
		playerAGrid.printGrid();
	}
}

class Ship{
	private int shipID;
	private ArrayList<ShipPoint> CoOrds;

	public Ship(Point inputPointFrom, Point inputPointToo,int inputShipID){
		this.shipID = inputShipID;
		CoOrds = new ArrayList<ShipPoint>();
		CoOrds.add(new ShipPoint(inputPointFrom,inputShipID));
		CoOrds.add(new ShipPoint(inputPointToo,inputShipID));	
	}
	
	public int getShipID(){
		return this.shipID;
	}
	
	public ArrayList<ShipPoint> getShipCoords(){
		return this.CoOrds;
	}
}

class ShipPoint extends Point{
	private int shipID;
	private boolean destroyed;

	ShipPoint(Point inputPoint,int inputShipID){
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
}

class Grid{
	private Point[][] grid;
	Grid(int dimension) throws GridException{
		//dimension must be larger than 3!
		if(dimension>=3){
			grid = new Point[dimension][dimension];
			fillGridWithBlankPoints();
		}else{
		//error
			throw new GridException("Grid size must be > 2");
		}
	}
	
	private void fillGridWithBlankPoints(){
		for(int i = 0; i<grid[0].length;i++){//row
			for(int a = 0; a<grid.length;a++){  //column
				grid[i][a]= new Point(i,a);        //add a blank point
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
					if(p.getX()==grid[i][a].getX()){ 		//if the x coords match
						if(p.getY()==grid[i][a].getY()){ 		//if the y coords match
							if(grid[i][a].getClass().getSimpleName().equalsIgnoreCase("ShipPoint")){//there is a ship here already
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
				for(Point x: inputShip.getShipCoords()){//for each coord in the ship
					if(x.getX()==grid[i][a].getX()){ 	     //if the x coords match
						if(x.getY()==grid[i][a].getY()){          //if the y coords match
							grid[i][a]=x;                              //replace it with the Shippoint as it's on that point on the grid
						}
					}
				}
			}
		}
	}
	
	public void printGrid(){
		for(int i = 0; i<grid[0].length;i++){//for each row in grid
			for(int a = 0; a<grid.length;a++){//for each column in grid
				if(grid[i][a].getClass().getSimpleName().equalsIgnoreCase("ShipPoint")){
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
class GridException extends Exception{
	public GridException(String message){
		super(message);
	}
}