package Stage9;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*

Stage 9:
Players can only see their own grid with shots taken.

 */

public class Stage9 {
	static Scanner player2Move;
	static Scanner player1Move;
	static Grid player1Grid;
	static Grid player2Grid;
	static int gridsize = 12;
	static ArrayList<Point> AIShotsToFireAt;
	static Point AIShot = null;
	public static void main(String[] args) {
		
		//create players
		try {
			player1Grid = new Grid(gridsize,1,"james");
			player2Grid = new Grid(gridsize,2,"dan");
		} catch (GridException e){
			e.printStackTrace();
		}
		
		//prepare
		int totalnumberofsquaresingrid = player1Grid.getGridTotalSquares();
		System.out.println("Welcome to battleships! Stage 9.");
		System.out.println("There are a total of " + totalnumberofsquaresingrid + " squares in the grid");
		System.out.println("Player 1 will fire first:");
		
		//2 players, 1 turn each.
		player2Move = new Scanner(System.in);
		player1Move = new Scanner(System.in);
		player1Grid.printGrid();
		player2Grid.printOpposingGrid();
		
		AIShotsToFireAt = new ArrayList<Point>();
		
		//add ships
		addPlayer1Ships();
		addAIShips();
		
		while(totalnumberofsquaresingrid>0){
						
			//player 1 takes a shot
			playerOneTakesShot();
	
			//player2Grid.printGrid();
			player2Grid.printOpposingGrid();
			
			//AI takes a shot
			AITakesShot();
			
			//print results end of round
			player1Grid.printGrid();
			
			//if the player has lost all their ships
			if(player1Grid.allShipsDestroyed()){
				System.out.println("GAME OVER, AI WINS!!");
				System.exit(0);
			}
			
			//if the player has lost all their ships
			if(player2Grid.allShipsDestroyed()){
				System.out.println("GAME OVER, PLAYER 1 WINS!!");
				System.exit(0);
			}
			
			totalnumberofsquaresingrid--; //max number of turns left -=1
		}
		
		System.out.println("End of game");
	}
	
	public static void AITakesShot(){
		//AI:
		//AI Number generator
		Random myRandomGen = new Random();
		
		//random x and y integers for the first random point
		int player2y = 0;
		int player2x = 0;
		int randomIndex = 0;
		
		//if it hit last time
		String Player2shotResult ="";
	
		player2y = myRandomGen.nextInt(gridsize-1);
		player2x = myRandomGen.nextInt(gridsize-1);
		AIShot = new Point(player2x,player2y);
			
		//fire the shot
		Player2shotResult = player1Grid.shotFiredAt(AIShot);
			
		while((Player2shotResult.equalsIgnoreCase("alreadyhit"))||(Player2shotResult.equalsIgnoreCase("outsidegrid"))){
				
			//if its already hit
			if(Player2shotResult.equalsIgnoreCase("alreadyhit")){
				System.out.println("ai hit the same place.");
			}else{
			//its outside of grid!
				System.out.println("ai shot outside grid");
			}

			player2y = myRandomGen.nextInt(gridsize-1);
			player2x = myRandomGen.nextInt(gridsize-1);

			//fire the shot
			Player2shotResult = player1Grid.shotFiredAt(new Point(player2x,player2y));
			
		}
		System.out.println("AI shooting at: " + player2y+"," + player2x+ "..." + Player2shotResult);
		
		
			
		
		
//			while((Player2shotResult.equalsIgnoreCase("alreadyhit"))||(Player2shotResult.equalsIgnoreCase("outsidegrid"))){
//					
//				//if its already hit
//				if(Player2shotResult.equalsIgnoreCase("alreadyhit")){
//					System.out.println("AI Shots to take before removal ");
//					for(Point x: AIShotsToFireAt){
//						System.out.println(x);
//					}
//					System.out.println("ai hit the same place. Removing index " + randomIndex);
//					AIShotsToFireAt.remove(AIShotsToFireAt.get(randomIndex));
//					System.out.println("AI Shots to take AFTER removal ");
//					for(Point x: AIShotsToFireAt){
//						System.out.println(x);
//					}
//				}else{
//					//its outside of grid!
//					System.out.println("AI Shots to take before removal ");
//					for(Point x: AIShotsToFireAt){
//						System.out.println(x);
//					}
//					System.out.println("ai shot outside grid, removing index: " + randomIndex);
//					AIShotsToFireAt.remove(AIShotsToFireAt.get(randomIndex));
//					
//					System.out.println("AI Shots to take AFTER removeal ");
//					for(Point x: AIShotsToFireAt){
//						System.out.println(x);
//					}
//				}
//				if(AIShotsToFireAt.size()==0){
//					System.out.println("Array is 0");
//					break;
//				}
//				//if it did hit, go through shots to fire
//				randomIndex = myRandomGen.nextInt(AIShotsToFireAt.size());
//				
//				//fire the shot
//				Player2shotResult = player1Grid.shotFiredAt(AIShotsToFireAt.get(randomIndex));
//					
//				if(Player2shotResult.equalsIgnoreCase("HIT!!!! BOOM!!!")){
//					AIShotsToFireAt.remove(AIShotsToFireAt.get(randomIndex));
//					break;
//				}
//				
//				System.out.println("AI Shots to take: ");
//				for(Point x: AIShotsToFireAt){
//					System.out.println(x);
//				}
//				System.out.println("Next shot at: " + AIShotsToFireAt.get(randomIndex) + " at index " + randomIndex);
//			}
		//fire the shot
		//Player2shotResult = player1Grid.shotFiredAt(AIShotsToFireAt.get(randomIndex));
				
//			if(Player2shotResult.equalsIgnoreCase("Miss")){
//				//remove this shot from the list of shots to continue with
//				AIShotsToFireAt.remove(AIShotsToFireAt.get(randomIndex));
//			}
//				
//			System.out.println("AI Shots to take: ");
//			for(Point x: AIShotsToFireAt){
//				System.out.println(x);
//			}
//		}else{
//		
//			//AI
//			if(Player2shotResult.equalsIgnoreCase("HIT!!!! BOOM!!!")){
//				//AI HIT a ship, find out surrounding points and add them to the list.
//				
//				Point northOfShot = new Point((int)AIShot.getX()+1,(int)AIShot.getY());
//				Point southOfShot = new Point((int)AIShot.getX(),(int)AIShot.getY()+1);
//				
//				Point eastOfShot = new Point((int)AIShot.getX()-1,(int)AIShot.getY());
//				Point westOfShot = new Point((int)AIShot.getX(),(int)AIShot.getY()-1);
//				
//				AIShotsToFireAt.add(northOfShot);
//				AIShotsToFireAt.add(southOfShot);
//				AIShotsToFireAt.add(eastOfShot);
//				AIShotsToFireAt.add(westOfShot);
//			}

	}
	
	public static void playerOneTakesShot(){
		//player2Grid.printGrid();
		player2Grid.printOpposingGrid();
		
		//player 1 x coord
		System.out.println("Player 1, enter x co-ord, then press enter:\n");
		int player1y = player1Move.nextInt();
		
		//player 1 y coord
		System.out.println("Player 1, enter y co-ord, then press enter:\n");
		int player1x = player1Move.nextInt();
		
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
			System.out.println("Player 1, enter x co-ord, then press enter:\n");
			player1y = player1Move.nextInt();
			
			//player 1 y coord
			System.out.println("Player 1, enter y co-ord, then press enter:\n");
			player1x = player1Move.nextInt();
			
			//fire the shot
			Player1shotResult = player2Grid.shotFiredAt(new Point(player1x,player1y));
		}
		System.out.println("Player 1 shooting at: " + player1x+"," + player1y+ "...");
		
		//print result of shot
		System.out.println(Player1shotResult);

		
	
		
	}
	public static void addAIShips(){
		//AI
		//adds ships in random places on grid 2
		int shipsize = 1;
		for(int i =0; i<7;i++){
			//make random coords that are within the grid and are within the grids area.
			Random myRandomGen = new Random();
					
			//random x and y integers for the first random point
			int firstxcoord = myRandomGen.nextInt(gridsize-1);
			int firstycoord = myRandomGen.nextInt(gridsize-1);
					
			//random numebr for orientation
			int up_or_sideways = myRandomGen.nextInt(2);
				
			if(i==2){
				shipsize++;
			}else if(i==5){
				shipsize++;
			}else if(i==6){
				shipsize++;
			}
					
			try{
				if(up_or_sideways==0){ //north/south facing ship
					player2Grid.addShipToGrid(new Ship(new Point(firstxcoord,firstycoord),new Point(firstxcoord+shipsize,firstycoord),(i+1)));	

				}else{//east/west facing ship
							//i is current ship id
					player2Grid.addShipToGrid(new Ship(new Point(firstxcoord,firstycoord),new Point(firstxcoord,firstycoord+shipsize),(i+1)));
				}
			} catch (Exception e){
				//e.printStackTrace();
						
				//this is bad but not sure what to do, catch the exception and read it? It does work...
				if(i==2){
					shipsize--;
				}else if(i==5){
					shipsize--;
				}else if(i==6){
					shipsize--;
				}
					i--;
				}
			}
		}
	public static void addPlayer1Ships(){
		/*
		  7 ships for each person
		  player 1
		 */
		int shipsize = 1;
		System.out.println("Hi Player 1, we will now run through your ships and their placements.");
		for(int i =0; i<7;i++){
			System.out.println("Player 1: Please enter the x coordinate for ship number: " + (i+1));
			
			//get player 1's input
			Scanner player1Input = new Scanner(System.in);
			int firstycoord = player1Input.nextInt();
			
			System.out.println("Player 1: Please enter the y coordinate for ship number: " + (i+1));
			player1Input = new Scanner(System.in);
			int firstxcoord = player1Input.nextInt();
			
			//number for orientation
			System.out.println("Player 1: Please enter 0 for North facing ship, or 1 for West");
			player1Input = new Scanner(System.in);
			int up_or_sideways = player1Input.nextInt();
			
			if(i==2){
				shipsize++;
			}else if(i==5){
				shipsize++;
			}else if(i==6){
				shipsize++;
			}
			
			try{
				if(up_or_sideways==0){ //north/south facing ship
					player1Grid.addShipToGrid(new Ship(new Point(firstxcoord,firstycoord),new Point(firstxcoord+shipsize,firstycoord),(i+1)));
					System.out.println("Ship add success!:\n");
					player1Grid.printGrid();
				}else if(up_or_sideways==1){                 //east/west facing ship
					//i is current ship id
					player1Grid.addShipToGrid(new Ship(new Point(firstxcoord,firstycoord),new Point(firstxcoord,firstycoord+shipsize),(i+1)));
					System.out.println("Ship add success!:\n");
					player1Grid.printGrid();
				}else{
					System.out.println("Error, 0 or 1 wasnt input for north or west, please try again for this ship:\n");
					i--;
				}
			} catch (Exception e){
				System.out.println("Error:");
				e.printStackTrace();
				
				//this is bad but not sure what to do, catch the exception and read it? It does work...
				System.out.println("\n\nPlease try adding this ship again:");
				if(i==2){
					shipsize--;
				}else if(i==5){
					shipsize--;
				}else if(i==6){
					shipsize--;
				}
				i--;
			}
		}
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
		//System.out.println("Ship made: Distance between 2 points is: ");
		double shiplength = inputPointFrom.distance(inputPointToo.getX(), inputPointFrom.getY());
		//if(shiplength>0.0){
			
			//determine orientation of the ship
			if(inputPointToo.getX() - inputPointFrom.getX()==0){
				//System.out.println("its east/west");
				double numberofPointsToAdd = (inputPointToo.getY() - inputPointFrom.getY())-1;
				//System.out.println("number of points to add"+numberofPointsToAdd);
				for(int i =0; i <numberofPointsToAdd;i++){
					CoOrds.add(new GridPoint(new Point((int)inputPointFrom.getX(),(int)inputPointFrom.getY()+(i+1)),inputShipID));
				}
				//System.out.println("Print ships coords:");
				//printCoOrds();
			}else{
				//System.out.println("its northsouth");	
				double numberofPointsToAdd = (inputPointToo.getX() - inputPointFrom.getX())-1;
				//System.out.println("number of points to add"+numberofPointsToAdd);
				for(int i =0; i <numberofPointsToAdd;i++){
					CoOrds.add(new GridPoint(new Point((int)inputPointFrom.getX()+(i+1),(int)inputPointFrom.getY()),inputShipID));
				}
				//System.out.println("Print ships coords:");
				//printCoOrds();
			
			}
	}
	
	public int getShipID(){
		return this.shipID;
	}
	
	public ArrayList<GridPoint> getShipCoords(){
		return this.CoOrds;
	}
	private void printCoOrds(){
		for(GridPoint x : this.CoOrds){
			System.out.println(x);
		}
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
								//printGrid();
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
	
	public void printOpposingGrid(){
		System.out.println("\nPlayer "+this.playerID+"'s  grid:");
		for(int i = grid[0].length-1; i>=0;i--){//for each row in grid
			for(int a = 0; a<grid.length;a++){//for each column in grid
				GridPoint currentGridPoint = grid[i][a]; 
				if(currentGridPoint.getShipID()!=0){ //it is a ship
					if(currentGridPoint.getHitStatus()){
						System.out.print("  |   x" + currentGridPoint.getShipID());
					}else{
						System.out.print("  |   o ");
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
	
	public void printGrid(){
		System.out.println("\nPlayer "+this.playerID+"'s  grid:");
		for(int i = grid[0].length-1; i>=0;i--){//for each row in grid
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