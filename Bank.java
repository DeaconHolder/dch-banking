package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Bank {
	
	public static void main(String[] args) {
		System.out.println("Welcome to DCH Banks!");
		Member member = null; //Create variable for the user
		while (member == null) {
			member = findMember(); //Create the user's account;
		}
		System.out.println(member.getUsername());
		System.out.println(member.getPassword());
		System.out.println("How can we help you today?");
		//double money = 0.00;
		home(member); //Go to the home menu
	}
	
	/**
	 * Method that either creates or finds an existing account with the bank
	 * @return the user's account
	 */
	public static Member findMember() {
		System.out.println("1. Sign Up\n2. Login");
		Scanner keyboard = new Scanner(System.in); //Read user response
		boolean flag = true; //Variable to determine validity
		int num = 0; //Manipulate user response
		while (flag) { //loop while invalid
			System.out.print("Selected Option: ");
			String user = keyboard.nextLine(); //Input from user
			System.out.println("");
			try {
				num = Integer.parseInt(user);
				try {
					if (num < 1 || num > 2) {
						throw new RangeException(); //If the response is invalid throw an exception
					}
					flag = false;
				} catch(RangeException e) {
					System.out.println("\nThe entered number does not lie between 1-2\n\n");
				}
			} catch(NumberFormatException e) {
				System.out.println("Please enter a valid number (1-2)");
			}
		}
		
		Member member = null; //Declare user account
		switch (num) {
		case 1: //user opted to sign up
			member = signUp();
			break;
		case 2: //user opted to login
			member = login();
			break;
		}
		return member;
		
	}
	
	/**
	 * Create an account for the user
	 * @return the users brand new account
	 */
	public static Member signUp() {
		Scanner s = new Scanner(System.in); //Recieve input from user
		System.out.print("Please enter your full name: ");
		String username = s.nextLine(); //read the user's name
		System.out.println("");
		System.out.print("Please enter your password: ");
		String password = s.nextLine(); //read the user's password
		System.out.println("");
		
		File file = new File("src/Main/members.txt"); //open file
		String tempUser = "";
		String tempPass = "";
		Scanner readFile = null;
		try {
			readFile = new Scanner(file); //try to open the file
		} catch (FileNotFoundException e) {
			System.out.println("File could not be opened");
		}
		boolean dupli = false; //variable to flag any duplicate accounts
		while (readFile.hasNext()) { //read the current file
			tempUser = readFile.nextLine();
			tempPass = readFile.nextLine();
			String junk = readFile.nextLine();
			//If the file found a duplicate then flag it
			if (tempUser.equals(username) && tempPass.equals(password)) {
				dupli = true;
				break;
			}
		}
		Member newMember = null; //initialize an account for the user
		//If the user is not a duplicate make the user's account and save their info to the file
		if (!dupli) {
			newMember = new Member(username, password, 0.00);
			try {
				FileWriter myWriter = new FileWriter("src/Main/members.txt", true);
				BufferedWriter bufferWriter = new BufferedWriter(myWriter);
				bufferWriter.write(username);
				bufferWriter.write("\n");
				bufferWriter.write(password);
				bufferWriter.write("\n");
				bufferWriter.write("0");
				bufferWriter.write("\n");
				bufferWriter.close();
				myWriter.close();
			} catch (IOException e) {
				System.out.println("Could not open the save file");
			}
		} else {
			System.out.println("This person already has an account. Try logging in!"); //Notify user this account exists and they can login
		}
		return newMember;
	}
	
	/**
	 * Find the information of a returning member
	 * @return the member information that was found
	 */
	public static Member login() {
		Scanner keyboard = new Scanner(System.in); //read user input
		System.out.print("Enter your full name: ");
		String tempUser = keyboard.nextLine();
		System.out.println("");
		System.out.print("Enter your password: ");
		String tempPass = keyboard.nextLine();
		System.out.println("");
		
		Member foundMember = null;
		boolean flag = false; //variable to identify if the member has been found
		Scanner source = null;
		String username = "";
		String password = "";
		String tempMoney = "";
		double money = 0.00;
		File file = new File("src/Main/members.txt");	
		try {
			source = new Scanner(file);
			while (source.hasNextLine()) { //While the file still has lines iterate through
				username = source.nextLine();
				password = source.nextLine();
				tempMoney = source.nextLine();
				//if the user matches what was searched for end the loop
				if (username.equals(tempUser) && password.equals(tempPass)) {
					flag = true;
					money = Double.parseDouble(tempMoney);
					break;
				}
			}	
		} catch (FileNotFoundException e) {
			System.out.println("Could not open the file");
		}
		//If found create the member, otherwise notify the user nobody was found
		if (flag) {
			foundMember = new Member(username, password, money);
		} else {
			System.out.println("We were unable to find anybody under your information. Try signing up!");
		}
		return foundMember;
	}
	
	/**
	 * Ask the user what action they would like to take
	 * @param member the user
	 */
	public static void home(Member member) {
		//Get user input
		Scanner keyboard = new Scanner(System.in);
		boolean flag = true; //variable to check validity
		int num = 0;
		while(flag) { //loop while waiting for a valid amount
			System.out.println("Please select one of the following options (1-5):");
			System.out.println("1. Check Balance");
			System.out.println("2. Deposit Money");
			System.out.println("3. Withdraw Money");
			System.out.println("4. Transfer Money");
			System.out.println("5. Exit The Application");
			System.out.print("Selected Option: ");
			String user = keyboard.nextLine();
			try {
				num = Integer.parseInt(user);
				try {
					//If entered number is not in range 
					if (num < 1 || num > 5) {
						throw new RangeException();
					}
					flag = false;
				} catch(RangeException e) {
					System.out.println("\nThe entered number does not lie between 1-4\n\n");
				}
			} catch(NumberFormatException e) {
				System.out.println("\nPlease enter a valid number (1-4)\n\n");
			} 
		}
		//determine which input the user follows
		switch (num) {
		case 1:
			checkBalance(member);
		case 2:
			depositMoney(member);
		case 3:
			withdrawMoney(member);
		case 4:
			transferMoney(member);
		default:
			System.out.println("Thank you for using our services today!");
			System.exit(0);
		}
	}
	
	/**
	 * check how much money is in the user's account
	 */
	public static void checkBalance(Member member) {
		System.out.println("\n\nYou currently have $" + String.format("%.2f", member.getMoney()) + " in your account\n\n");
		home(member); //return home
	}
	
	/**
	 * Add money to the user's account
	 * @param member is the user
	 */
	public static void depositMoney(Member member) {
		Scanner deposit = new Scanner(System.in);
		System.out.println("\n\nHow much money would you like to deposit?");
		String user = deposit.nextLine(); //Value of user money to input
		double num;
		try {
			num = Double.parseDouble(user);
			try {
				if (num <= 0) {
					throw new RangeException();
				}
				member.setMoney(member.getMoney() + num); //Add money to user's account
				
				//Update the file so it reflects the change in the user's amount of money
				String filePath = "src/Main/members.txt";
				File file = new File(filePath);
				Scanner s = null;
				try {
					s = new Scanner(file); //create scanner of the file
				} catch (FileNotFoundException e) {
					System.out.println("Could not open the file");
				}
				int lineNumber = 2;
				//Loop while looking for the user to make changes to
				while (s.hasNext()) {
					String username = s.nextLine();
					String password = s.nextLine();
					String junk = s.nextLine();
					//Break loop when user to update has been found
					if (username.equals(member.getUsername()) && password.equals(member.getPassword())) {
						break;
					}
					lineNumber += 3;
				}
				//Update the money amount of the user
				String newContent = "" + member.getMoney();
				try {
					Path path = Paths.get(filePath);
					List<String> lines = Files.readAllLines(path);
					lines.set(lineNumber, newContent);
					Files.write(path,  lines, StandardCharsets.UTF_8);
				} catch (IOException e) {
					System.out.println(e);
				}
				System.out.println("$" + String.format("%.2f", num) + " has been added to your account\n\n");
			} catch(RangeException e) {
				System.out.println("\nThe entered number is an invalid deposit amount so no deposit was made.\n");
			}
		} catch(NumberFormatException e) {
			System.out.println("\nThe value entered was not a number so no deposit was made.\n");
		}
		home(member); //return home
	}
	
	/**
	 * Remove money from the user's account
	 * @param member is the user
	 */
	public static void withdrawMoney(Member member) {
		Scanner withdraw = new Scanner(System.in);
		System.out.println("\n\nHow much money would you like to withdraw?");
		String user = withdraw.nextLine(); //how much money the user wants to remove
		double num;
		try {
			num = Double.parseDouble(user);
			try {
				//Ensure the entered amount is removable
				if (num <= 0 || num > member.getMoney()) {
					throw new RangeException();
				}
				member.setMoney(member.getMoney() - num); //remove money
				String filePath = "src/Main/members.txt";
				File file = new File(filePath);
				Scanner s = null;
				try {
					s = new Scanner(file);
				} catch (FileNotFoundException e) {
					System.out.println("Could not open the file");
				}
				int lineNumber = 2;
				//Loop until the user is found in the file
				while (s.hasNext()) {
					String username = s.nextLine();
					String password = s.nextLine();
					String junk = s.nextLine();
					if (username.equals(member.getUsername()) && password.equals(member.getPassword())) {
						break;
					}
					lineNumber += 3;
				}
				//Update the file to the correct amount of money
				String newContent = "" + member.getMoney();
				try {
					Path path = Paths.get(filePath);
					List<String> lines = Files.readAllLines(path);
					lines.set(lineNumber, newContent);
					Files.write(path,  lines, StandardCharsets.UTF_8);
				} catch (IOException e) {
					System.out.println(e);
				}
				System.out.println("$" + String.format("%.2f", num) + " has been removed from your account\n\n");
			} catch(RangeException e) {
				System.out.println("\nThe amount attempted to be withdrawn exceeds the balance your account contains. Your withdrawal has been denied.\n");
			}
		} catch(NumberFormatException e) {
			System.out.println("\nThe value entered was not a number so no withdraw was made.\n");
		}
		home(member); //return home
	}
	
	/**
	 * Transfer money from one user to another
	 * @param member is the user
	 */
	public static void transferMoney(Member member) {
		Scanner user = new Scanner(System.in);
		boolean flag = true;
		Scanner s = null;
		File file = new File("src/Main/members.txt");
		try {
			s = new Scanner(file);
		} catch(FileNotFoundException e) {
			System.out.println("File could not be opened");
		}
		String recipient = "";
		String data = "";
		String otherMoney = "";
		//Verify the person money is transferred to exists
		int line = -1;
		while (flag) {
			System.out.println("Please enter the full name of the person you would like to transfer money to. ('Quit' to return to home):");
			recipient = user.nextLine();
			while(s.hasNextLine() && !recipient.equals(data) && !recipient.equals("Quit")) {
				data = s.nextLine();
				String junk = s.nextLine();
				otherMoney = s.nextLine();
				line += 3;
			}
			//If the member was found exit otherwise ask again or quit
			if (data.equals(recipient)) {
				flag = false;
				break;
			} else if(recipient.equals("Quit")) {
				System.out.println("You have successfully quit the transfer menu.");
				home(member);
			} else {
				//Reset the scanner
				try {
					s = new Scanner(file);
				} catch (FileNotFoundException e) {
					System.out.println("The File was not found");
				}
				line = -1;
				System.out.println("The entered user was not found.");
			}
		}
		
		
		
		//Update their line
		//Update the file to the correct amount of money
		boolean invalid = true;
		String newContent = "";
		double num = 0;
		while (invalid) {
			//How much money would you like to send them.
			System.out.println("How much money would you like to send " + recipient + "?");
			try {
				String amount = user.nextLine();
				num = Double.parseDouble(amount);
				if (num <= 0 || num > member.getMoney()) {
					throw new RangeException();
				}
				newContent = "" + (Double.parseDouble(otherMoney) + num);
				invalid = false;
			} catch (NumberFormatException e) {
				System.out.println("You have entered an invalid amount");
			} catch (RangeException e) {
				System.out.println("You have selected to transfer either less than 0 or more money than is in your account. Please enter a valid amount.");
			}
		}
		String filePath = "src/Main/members.txt";
		try {
			Path path = Paths.get(filePath);
			List<String> lines = Files.readAllLines(path);
			lines.set(line, newContent);
			Files.write(path,  lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println(e);
		}
		//Update user line
		member.setMoney(member.getMoney() - num);
		newContent = "" + member.getMoney();
		//Reset the scanner
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("The File was not found");
		}
		int lineNumber = 2;
		//Loop until the user is found in the file
		while (s.hasNext()) {
			String username = s.nextLine();
			String password = s.nextLine();
			String junk = s.nextLine();
			if (username.equals(member.getUsername()) && password.equals(member.getPassword())) {
				break;
			}
			lineNumber += 3;
		}
		
		try {
			Path path = Paths.get(filePath);
			List<String> lines = Files.readAllLines(path);
			lines.set(lineNumber, newContent);
			Files.write(path,  lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println(e);
		}
		System.out.println(String.format("$%.2f", num) + " has successfully been transferred to " + recipient + ".");
		home(member);
	}
	
}
