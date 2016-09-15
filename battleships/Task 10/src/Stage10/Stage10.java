package Stage10;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*

Stage 10:
Implementation of a GUI

 */

class Loserscreen{
	Loserscreen(){
		  JFrame frame = new JFrame();
		  JLabel youwin = new JLabel("YOU LOSE",SwingConstants.CENTER);
		  youwin.setFont(new Font("Serif", Font.PLAIN, 14));
		  frame.setLocationRelativeTo(null);
		  ImageIcon icon = new ImageIcon("mrfreeze.jpg");
		  JLabel label = new JLabel(icon);
		  frame.setLayout(new BorderLayout());
		  frame.add(youwin,BorderLayout.NORTH);
		  frame.add(label,BorderLayout.CENTER);
		  
		  frame.setDefaultCloseOperation
		         (JFrame.EXIT_ON_CLOSE);
		  frame.pack();
		  frame.setVisible(true);
	}
}

class Winnerscreen{
	Winnerscreen(){
		  JFrame frame = new JFrame();
		  JLabel youwin = new JLabel("YOU WIN",SwingConstants.CENTER);
		  youwin.setFont(new Font("Serif", Font.PLAIN, 14));
		  frame.setLocationRelativeTo(null);
		  ImageIcon icon = new ImageIcon("mrfreeze.jpg");
		  JLabel label = new JLabel(icon);
		  frame.setLayout(new BorderLayout());
		  frame.add(youwin,BorderLayout.NORTH);
		  frame.add(label,BorderLayout.CENTER);
		  
		  frame.setDefaultCloseOperation
		         (JFrame.EXIT_ON_CLOSE);
		  frame.pack();
		  frame.setVisible(true);
	}
}

public class Stage10 extends JFrame{
	static Scanner player2Move;
	static Scanner player1Move;
	static Grid player1Grid;
	static Grid player2Grid;
	static int gridsize = 12;
	static ArrayList<Point> AIShotsToFireAt;
	static Point AIShot = null;
	
	private JFrame mainFrame;
	private JPanel sea;
	private JLabel headerLabel;
	private static boolean playermoved = false;
	private static Point playerShot;
	private static JPanel panelOne;

	private void prepareGUI(){
		
		  mainFrame = new JFrame("Battleships");
		  mainFrame.setSize(1600, 800);
		  mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		  mainFrame.setLocationRelativeTo(null);
		  mainFrame.setVisible(true);
		  mainFrame.setLayout(new BorderLayout());
		  
		  JPanel container = new JPanel();
		  
          panelOne = new JPanel();
	      JPanel panelTwo = new JPanel();
	      JPanel consolePanel = new JPanel();
		  
		  panelOne.setLayout(new GridLayout(gridsize,gridsize+1));
		  panelTwo.setLayout(new GridLayout(gridsize,gridsize+1));
		  consolePanel.setLayout(new GridLayout(1,2));
		  
		  //adds first grid
		  for(int i =gridsize-1; i >= 0; i--){
			 for(int p = 0; p < gridsize; p++){
			     String buttontext = ""+p+","+i;
			     panelOne.add(new JButton(buttontext));
			 }  
		  }
		  
		  //adds second grid
		  for(int i =gridsize-1; i >= 0; i--){
			 for(int p = 0; p < gridsize; p++){
			     String buttontext = ""+p+","+i;
			     JButton tempBtn = new JButton(buttontext);
			     tempBtn.setBackground(Color.LIGHT_GRAY);
			     tempBtn.setForeground(Color.LIGHT_GRAY);
			     panelTwo.add(tempBtn);
			     //adds action listener
			     tempBtn.addActionListener(new ActionListener() {
			    	    @Override
			    	    public void actionPerformed(ActionEvent e) {
			    	        JButton button = (JButton) e.getSource();
			    	        
			    	        String coords = button.getText();
			    	        System.out.println(coords);
			    	        
			    	        //takes the shot + if it hits
			    	        if(playerOneTakesShot(coords)){
			    	        	button.setBackground(Color.RED);
			    	        }else{//shot was a miss
			    	        	button.setBackground(Color.BLUE);
			    	        }
			    	        button.setEnabled(false);	
			    	        
			    	    }
		    	});
			 }  
		  }
		  
		  //consoles + title
		  JLabel player1Title = new JLabel("Player 1 title");
		  JLabel player2Title = new JLabel("Opponent");
		  
		  JLabel player1console = new JLabel("player 1 Console");
		  JLabel player2console = new JLabel("player 2 Console");	  
		  
		  consolePanel.add(player1Title);  
		  consolePanel.add(player1console);
		  consolePanel.add(player2Title);
		  consolePanel.add(player2console);
		  
		  container.setLayout(new GridLayout(1,2));
          container.add(panelOne);
          container.add(panelTwo);
	        
          mainFrame.add(container, BorderLayout.CENTER);
          mainFrame.add(consolePanel,BorderLayout.NORTH);
	}
	
	public Stage10(){
		prepareGUI();
	}
	
	public static void setPlayerMoved(boolean input){
		playermoved=input;
	}
	
	public static void main(String[] args) {
		
		Stage10 mainWindow = new Stage10();
	
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
		//player1Grid.printGrid();
		//player2Grid.printOpposingGrid();
		
		AIShotsToFireAt = new ArrayList<Point>();
		
		//add ships
		addPlayer1Ships();
		addAIShips();
		
		updateShipsOnPlayersMap();
		
		//main game loop
		playermoved = false;
		while(totalnumberofsquaresingrid>0){
			
			System.out.println("heree");
			
			while(playermoved==false){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
			System.out.println("Player 1 taken a shot");

			//player2Grid.printGrid();
			//player2Grid.printOpposingGrid();
			
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			//AI takes a shot
			AITakesShot();
			
			//print results end of round
			player1Grid.printGrid();
			
			//if the player has lost all their ships
			if(player1Grid.allShipsDestroyed()){
				Loserscreen test1 = new Loserscreen();
				System.out.println("GAME OVER, AI WINS!!");
				//System.exit(0);
			}
			
			//if the player has lost all their ships
			if(player2Grid.allShipsDestroyed()){
				Winnerscreen test1 = new Winnerscreen();
				System.out.println("GAME OVER, PLAYER 1 WINS!!");
				//System.exit(0);
			}
			
			totalnumberofsquaresingrid--; //max number of turns left -=1
			setPlayerMoved(false);
		}
		
		System.out.println("End of game");
	}
	
	public static void updateShipsOnPlayersMap(){
		for (Component c : panelOne.getComponents()) {
		    if (c instanceof JButton) {
		       //((JButton)c).setText("eeee");
		    	String[] buttoncoords = ((JButton)c).getText().split(",");
		    	Point currentPoint = new Point(Integer.parseInt(buttoncoords[0]),Integer.parseInt(buttoncoords[1]));
		  
		    	GridPoint[][] tempgrid = player1Grid.getGrid();
		    	
		    	for(int i = 0; i<tempgrid[0].length;i++){//for each row in grid
					for(int a = 0; a<tempgrid.length;a++){//for each column in grid
						if(currentPoint.getX()==tempgrid[i][a].getX()){//if x matches
							if(currentPoint.getY()==tempgrid[i][a].getY()){//if y matches
								if(tempgrid[i][a].getShipID()!=0){
									//its a ship!
									((JButton)c).setBackground(Color.DARK_GRAY);	
								}
							}
						}
					}
				}
		    }
		}
	}
	public static void updateAIShotGUI(Point shotCoord){
		MainLoop:
		for (Component c : panelOne.getComponents()) {
		    if (c instanceof JButton) {
		    	String[] buttoncoords = ((JButton)c).getText().split(",");
		    	Point buttonCoords = new Point(Integer.parseInt(buttoncoords[0]),Integer.parseInt(buttoncoords[1]));
		    	
		    	GridPoint[][] tempgrid = player1Grid.getGrid();
		    	
		    	for(int i = 0; i<tempgrid[0].length;i++){//for each row in grid
					for(int a = 0; a<tempgrid.length;a++){//for each column in grid
						if(shotCoord.getX()==tempgrid[i][a].getX()){//if x matches
							if(shotCoord.getY()==tempgrid[i][a].getY()){//if y matches
								System.out.println("Found match");
								System.out.println("Coords are: " + shotCoord.getX() + "," + shotCoord.getY());
								if(tempgrid[i][a].getShipID()!=0){
									//its a ship!
									if((buttonCoords.getX()==shotCoord.getX())&&(buttonCoords.getY()==shotCoord.getY())){
										((JButton)c).setBackground(Color.RED);
										((JButton)c).setEnabled(false);
										System.out.println("aaaa");
										break MainLoop;	
									}
								}else{
									//its water!!
									if((buttonCoords.getX()==shotCoord.getX())&&(buttonCoords.getY()==shotCoord.getY())){
										((JButton)c).setBackground(Color.BLUE);
										((JButton)c).setEnabled(false);
										System.out.println("bbbb");
										break MainLoop;
	
									}
								}
							}
						}
					}
				}
		    }
		}
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
			AIShot.setLocation(player2y,player2x);
			Player2shotResult = player1Grid.shotFiredAt(AIShot);
		}
		System.out.println("AI shooting at: " + player2y+"," + player2x+ "..." + Player2shotResult);
		System.out.println("AISHOT IS: " + AIShot.getX() + "," + AIShot.getY());
		
		updateAIShotGUI(AIShot);
	}
	
	public static boolean playerOneTakesShot(String inputcoords){
		boolean hit = false;

		String[] coords = inputcoords.split(",");
		int player1y = Integer.parseInt(coords[0]);
		int player1x = Integer.parseInt(coords[1]);
		String Player1shotResult = player2Grid.shotFiredAt(new Point(Integer.parseInt(coords[0]),Integer.parseInt(coords[1])));
		
		System.out.println("Player 1 shooting at: " + player1x+"," + player1y+ "...");
		
		//print result of shot
		System.out.println(Player1shotResult);
		
		//break out the loop in game
		setPlayerMoved(true);
		
		if(Player1shotResult.equalsIgnoreCase("HIT!!!! BOOM!!!")){
			hit = true;
		}
		return hit;
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
					player1Grid.addShipToGrid(new Ship(new Point(firstxcoord,firstycoord),new Point(firstxcoord+shipsize,firstycoord),(i+1)));	

				}else{//east/west facing ship
							//i is current ship id
					player1Grid.addShipToGrid(new Ship(new Point(firstxcoord,firstycoord),new Point(firstxcoord,firstycoord+shipsize),(i+1)));
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
	public GridPoint[][] getGrid(){
		return this.grid;
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