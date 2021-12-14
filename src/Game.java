import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

//enum for the state of the cell
enum StateCell {
	O, //state Letter O
	X, //state Letter X
	E; //state Empty
}

//enum for the state of board
enum StateBoard {
	InProgress, //game is still in progress
	Draw, //game ended as draw
	WinX, //game ended and X won
	WinO; //game ended and O won
}

public class Game { //Begin Class
	private StateBoard stateB;
	private StateCell cells [][];
	private int n, m, k ;
	private int countTurns;
	private StateCell StoreBoard [][][] ;
	private int index;
	
	//if n,m,k will be taken from the user
	Game (int n, int m, int k){
		this.n = n;
		this.m = m;
		this.k = k;
		
		cells = new StateCell [n][m];
		stateB = StateBoard.InProgress;
		countTurns = 0;
		StoreBoard = new StateCell [1+n*m][n][m];
		for(int i = 0 ; i < n; i ++) {
			for(int j = 0; j < m;j++){
				cells[i][j] = StateCell.E;
				StoreBoard[countTurns][i][j] = StateCell.E;
			}	
		}
	}
	
	//n=m=k=3
	Game (){
		this (3,3,3);
	
	}
	
	//n,m will be taken from the user
	Game (int n, int m){
		this (n,m,3);
	}
	
	//setters and getters for private variables
	public void Setn(int n) {
		this.n = n;
	}
	
	public int Getn () {
		return n;
	}
	
	public void Setm(int m) {
		this.m = m;
	}
	
	public int Getm () {
		return m;
	}
	
	public void Setk(int k) {
		this.k = k;
	}
	
	public int Getk () {
		return k;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void SetCountTurns(int CountTurns) {
		countTurns = CountTurns;
	}
	
	public int GetCountTurns () {
		return countTurns;
	}
	
	public StateBoard GetBoardState () {
		return stateB;
	}
	
	//print board
	void print () {
		for(int i =0; i <n; i++) {
			for (int j =0; j<m; j++) {
				if(cells[i][j] == StateCell.X) {
					System.out.print('X');
				}
				else if (cells[i][j] == StateCell.O) {
					System.out.print('O');
				}
				else {
					System.out.print(" ");
				}
				if(j != m-1) {
				System.out.print(" | ");
				}
			}
			System.out.println();
			System.out.print("-");
			for (int l = 0; l<m; l++) {
				System.out.print("- -");
			}
			System.out.println(" ");
		}
		System.out.println("Guide for indexing:");
		printWithIndex();
	}
	
	//To print the indexing guide for the user
	public void printWithIndex() {
		int count =0;
		for(int i =0; i <n; i++) {
			for (int j =0; j<m; j++) {
				System.out.print(count);
				count++;
				if(j != m-1) {
					System.out.print(" | ");}
			}
			System.out.println();
			System.out.print("-");
			for (int l = 0; l<m; l++) {
				System.out.print("- -");
			}
			System.out.println(" ");
		}
	}
	
	//who is the next player to play?
	public boolean nextPlayer () {
		if (countTurns% 2 == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int nextPlayerINT () {
		if (countTurns% 2 == 0) {
			return 1;
		}
		else {
			return 2;
		}
	}
	
		
	//function to handle positioning the indexes and replay
	public void play() {
		String choice;
		boolean continueLoop = true;
		if(cells[((index/m))][(index)%m] == StateCell.E) {
			if(nextPlayer()) {
				cells[(index/m)][(index)%m] = StateCell.O;}
			else {
				cells[(index/m)][(index)%m] = StateCell.X;}	
		}
		else {
			System.out.println("Please enter a new index as this one is taken by the other player or 'replay' if you want to replay from a certain index");
			Scanner S = new Scanner (System.in);
			choice = S.next();
			
			if(choice.equals("replay") || choice.equals("Replay")) {
				System.out.println("What count turn do you want to replay from?");
				do {
					try {
						index = S.nextInt();
						setIndex(index);
						GameReplay();
						continueLoop = false;
						}
					catch (InputMismatchException inputMismatchException)
					{
						System.err.printf("%nException: %s%n", inputMismatchException);
						System.out.printf("You must enter integers. Please try again. %n%n");
						S.nextLine();
					}
					catch(NumberFormatException numberFormatExceptio){
						System.err.printf("%nException: %s%n", numberFormatExceptio);
						System.out.printf("Please try again. %n%n");
						S.nextLine();
					}
				}while(continueLoop);
			}
			else if (Integer.parseInt(choice) < (n*m)){
				index = Integer.parseInt(choice);
				while(cells[index/m][index%m] != StateCell.E) {
					System.out.println("This index is already taken. Choose another one");
					index = S.nextInt();
				}
				play ();
			}
		}

			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					StoreBoard[countTurns][i][j] = cells [i][j];
				}
			}
			countTurns++;
	}
	
	//function to handle the game replay where it stores the board to be able to restore it in a specific index
	public void GameReplay() {
		while((index > n*m) || (index > countTurns) || (index < 0)) {
			Scanner S = new Scanner (System.in);
			System.out.println("This index is out of range, please enter a valid index");
			index = S.nextInt();
		}
		for (int i =0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				cells[i][j] = StoreBoard[index][i][j];
			}
		}
		countTurns = index+1;
		
	}

	//check winning vertically
	public boolean CheckWinVertical() {
		for(int i =0; i<n; i++) {
			for (int j =0 ; j<m-(k-1); j++) {
				if(cells[i][j] == StateCell.E) {
							
				}
				else {
					boolean winningState = true;
					for(int p = 1; p<k;p++) {
						if(cells[i][j] != cells[i][j+p]) {
							winningState = false;
						}
					}
					if(winningState == true) {
						return true;
					}
				}
			}
		}
		return false;
	}
				
	//check winning horizontally
	public boolean CheckWinHorizontal() {
		for(int i =0; i<n-(k-1); i++) {
			for (int j =0 ; j<m; j++) {
				if(cells[i][j] == StateCell.E) {
							
				}
				else {
					boolean winningState = true;
					for(int p = 1; p<k;p++) {
						if(cells[i][j] != cells[i+p][j]) {
								winningState = false;
						}
					}
					if(winningState == true) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//check winning diagonal right	
	public boolean CheckWinDiagonalR() {
		for(int i =0; i<n-(k-1); i++) {
			for (int j = 0; j<m-(k-1); j++) {
				if(cells[i][j] == StateCell.E) {
							
				}
				else {
					boolean winningState = true;
					for(int p = 1; p<k;p++) {
						if(cells[i][j] != cells[i+p][j+p]) {
							winningState = false;
						}
					}
					if(winningState == true) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//check winning diagonal left		
	public boolean CheckWinDiagonalL() { 
		for(int i =k-1; i<n; i++) {
			for (int j =0; j<m-(k-1); j++) {
				if(cells[i][j] == StateCell.E) {
							
				}
				else {
					boolean winningState = true;
					for(int p = 1; p<k;p++) {
						if(cells[i][j] != cells[Math.abs(i-p)][j+p]) {
							winningState = false;
						}
					}
					if(winningState == true) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//set board state according to the winning functions result to know who won or if it is a draw
	public void setBoardState() {
		if(CheckWinHorizontal()) {
			if(nextPlayer()) {
				stateB = StateBoard.WinX;
			}else {
				stateB = StateBoard.WinO;
			}
		}else if(CheckWinVertical()) {
			if(nextPlayer()) {
				stateB = StateBoard.WinX;
			}else {
				stateB = StateBoard.WinO;
			}
		}else if(CheckWinDiagonalR()) {
			if(nextPlayer()) {
				stateB = StateBoard.WinX;
			}else {
				stateB = StateBoard.WinO;
			}
		}else if(CheckWinDiagonalL()) {
			if(nextPlayer()) {
				stateB = StateBoard.WinX;
			}else {
				stateB = StateBoard.WinO;
			}
		}else {
			if(countTurns == (n*m)) {
				stateB = StateBoard.Draw;
			}
		}
	}
	
	//prompts to the user who won and stops the game
	public void Win() {
		setBoardState();
		if (stateB == StateBoard.WinO) {
			System.out.println("Player 1 won the game");
			System.exit(0);
		}else if(stateB == StateBoard.WinX) {
			System.out.println("Player2 won the game");
			System.exit(0);
		}else if(stateB == StateBoard.Draw){
			System.out.println("The game ended with a draw");
			System.exit(0);
		}
	}
		

	//generate the index when it is the computer's turn	
	public int generate_index() {
		Random rand = new Random();
		for(int p = k; p > 1;p--)
		{
			//check if this move means winning
			for(int i = 0; i < n; i++)
				for(int j = 0; j < m; j++)
					if(cells[i][j] == StateCell.E)
					{
						cells[i][j] = StateCell.X;
						if((CheckWinHorizontal()) ||(CheckWinVertical()) || (CheckWinDiagonalR()) || (CheckWinDiagonalR()))
							{
							cells[i][j] = StateCell.E;
							return ((i * m) + j);
							}
						else
							cells[i][j] = StateCell.E;
					}
			//check if this move means losing
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					if(cells[i][j] == StateCell.E)
					{
						cells[i][j] = StateCell.O;
						if((CheckWinHorizontal()) ||(CheckWinVertical()) || (CheckWinDiagonalR()) || (CheckWinDiagonalR()))
						{
							cells[i][j] = StateCell.E;
							return ((i * m) + j);
						}
						else
							cells[i][j] = StateCell.E;
					}
				}
			}
		}
		//generate the random index
		int random_index = rand.nextInt((n*m)-1);
		while(cells[(random_index)/m][(random_index)%m] != StateCell.E)
		{
			random_index = rand.nextInt((n*m)-1)+1;
		}
	return random_index;		
	}	
		
	//function to handle the Auto Mode
	public void AutoPlay() {
		Scanner S = new Scanner (System.in);
		String choice;
		System.out.println("It is player 1 turn. Player 1 plays with O");
		index = 0;
		play();
		print();
		Win();
			
		while(stateB == StateBoard.InProgress) {
			System.out.println("It is player 2 turn. Player 2 plays with X. If you want to replay write 'Replay'");
			choice = S.next();
			while(Integer.parseInt(choice) >= (n*m)) {
				System.out.println("out of index output. please re-enter index");
				choice = S.next();
			}
			if(choice.equals("Replay") || choice.equals("replay")) {
				System.out.println("What count turn do you want to replay from?");
				index = S.nextInt();
				//while (index > )
				setIndex(index);
				GameReplay();
				print();
				countTurns++;
				System.out.println("Enter the index you want to play in");
				index = S.nextInt();
				while(cells[index/m][index%m] != StateCell.E) {
					System.out.println("This index is already taken. Choose another one");
					index = S.nextInt();
				}
				play();
				print();
				Win();
			}
			else if (Integer.parseInt(choice) < (n*m)) {
				index = Integer.parseInt(choice);
				while(cells[index/m][index%m] != StateCell.E) {
					System.out.println("This index is already taken. Choose another one");
					index = S.nextInt();
				}
				play();
				print();
				Win();
			}
			
			System.out.println("It is player 1 turn. Player 1 plays with O");
			index = generate_index();
			while(cells[index/m][index%m] != StateCell.E) {
				index = generate_index();
			}
			play();
			print();
			Win();
		}
	}
	
//End class		
}
