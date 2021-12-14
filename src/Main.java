import java.util.Scanner;
import java.util.InputMismatchException;
public class Main {
	
	//Main function of the program
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String choice;
		System.out.println("What mode do you want to play in? (Manual OR Auto)");
		Scanner S = new Scanner (System.in);
		choice = S.next();
		
		
		while (!choice.equals("Auto") && !choice.equals("Manual") && !choice.equals("auto") && !choice.equals("manual")) {
			System.out.println("Invalid Answer! What mode do you want to play in? (Manual OR Random)");
			choice = S.next();
		}
		
		if (choice.equals("Auto") || choice.equals("auto")) {
			AutoMode();
		}
		else if (choice.equals("Manual") || choice.equals("manual")) {
			ManualMode();
		}
		S.close();	
	}
	
	//function to take inputs from the user and set the constructor and return it
	public static Game TakeInputs () {
		Scanner S = new Scanner(System.in);
		boolean continueLoop = true;
		int n,m,k;
		Game G;
		do {
			try {
				System.out.println("How many rows do you want?");
				n = S.nextInt();
				while (n<3) {
					System.out.println("Minimum input is 3.How many rows do you want?");
					n = S.nextInt();
				}
		
				System.out.println("How many columns do you want?");
				m = S.nextInt();
				while (m<3) {
					System.out.println("Minimum input is 3.How many columns do you want?");
					m = S.nextInt();
				}
		
				System.out.println("After how many aligned similar times, it is a win?");
				k = S.nextInt();
				while (k<3) {
					System.out.println("Minimum input is 3.After how many aligned similar times, it is a win?");
					k = S.nextInt();
				}
				
				if(n==3 && m==3 && k==3) {
					G = new Game();
				}else if (k == 3) {
					G = new Game (n,m);
				}else {
					G = new Game (n,m,k);
				}
				continueLoop = false;
				return G;
			}
			catch(InputMismatchException inputMismatchException){
				System.err.printf("%nException: %s%n", inputMismatchException);
				System.out.printf("You must enter integers. Please try again. %n%n");
				S.nextLine();
			}
			
		}
		while(continueLoop);
		S.close();
		return null;
	}
	
	//function to handle the Manual Mode
	public static void ManualMode () {
		Game G = TakeInputs();
		Scanner S = new Scanner(System.in);
		int index;
		String choice;
		//boolean continueLoop = true;
		
		//do {
			//try {
				while((G.GetBoardState() == StateBoard.InProgress)) {
			
					System.out.println("Enter any number between 0 and " + ((G.Getm()*G.Getn())-1));
					System.out.println("It is Player " + G.nextPlayerINT() + " Turn, Player1 plays with O, Player2 with X");
					System.out.println("Or do you want to replay? if yes, enter 'Replay'");
					choice = S.next();
		
					if(choice.equals("replay") || (choice.equals("Replay"))) {
						System.out.println("What count turn do you want to replay from?");
						index = S.nextInt();
						G.setIndex(index);
						G.GameReplay();
						G.print();
					}
					else {
						if (Integer.parseInt(choice) < (G.Getn()*G.Getm())){
							index = Integer.parseInt(choice);
							G.setIndex(index);
							G.play();
							G.print();
							G.Win();}
						else {
							System.out.println("Out of Range");
							choice = S.next();
							G.play();
							G.print();
							G.Win();
						}
					}
				}
				//continueLoop = false;
			//}
//			catch(NumberFormatException numberFormatExceptio){
//				System.err.printf("%nException: %s%n", numberFormatExceptio);
//				System.out.printf("Please try again. %n%n");
//				S.nextLine();
//			}
//		}while(continueLoop);
		S.close();
	}
	
	//function to call the Auto mode to the main
	public static void AutoMode() {
		Game G = TakeInputs();
		G.AutoPlay();
	}

}
