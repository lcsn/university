package de.lsn.client;

import java.util.Scanner;

import de.lsn.resource.UIPhase;

public class UI {

	private static boolean runnable = true;

	private static String mainMenu = "[1] READ\n" + "[2] WRITE\n" + "[0] EXIT"
			+ ": ";
	private static String readMenu = "[3] MAIN" + ": ";
	private static String writeMenu = "[3] MAIN" + ": ";

	private static Scanner scan = new Scanner(System.in);

	private static UIPhase lastPhase = UIPhase.MAIN_MENU;

	private static void showUI(UIPhase phase) {
		UIPhase newPhase = phase;
		while (runnable)
			switch (phase) {
			case MAIN_MENU:
				newPhase = UIPhase.stringToPhase(promptMainMenu());
				lastPhase = phase;
				showUI(newPhase);
				break;
			case READ_MENU:
				System.out.println("READ MENU");
				newPhase = UIPhase.stringToPhase(promptReadMenu());
				lastPhase = phase;
				showUI(newPhase);
				break;
			case WRITE_MENU:
				System.out.println("WRITE MENU");
				newPhase = UIPhase.stringToPhase(promptWriteMenu());
				lastPhase = phase;
				showUI(newPhase);
				break;
			case EXIT:
				runnable = false;
				scan.close();
				break;
			default:
				System.err.println("UNKNOW COMMAND!");
				showUI(lastPhase);
				break;
			}
	}

	private static int promptMainMenu() {
		System.out.println(mainMenu);
		int readVal = scan.nextInt();
		int res = -1;
		if (isValid(readVal, UIPhase.MAIN_MENU)) {
			res = readVal;
		}
		return res;
	}

	private static int promptReadMenu() {
		System.out.println(readMenu);
		int readVal = scan.nextInt();
		int res = -1;
		if (isValid(readVal, UIPhase.READ_MENU)) {
			res = readVal;
		}
		return res;
	}

	private static int promptWriteMenu() {
		System.out.println(writeMenu);
		int readVal = scan.nextInt();
		int res = -1;
		if (isValid(readVal, UIPhase.WRITE_MENU)) {
			res = readVal;
		}
		return res;
	}

	private static boolean isValid(int value, UIPhase phase) {
		boolean res = false;
		switch (phase) {
		case MAIN_MENU:
			if(1 == value){
				res = true;
			}
			else if (2 == value) {
				res = true;
			}
			else if (0 == value) {
				res = true;
			}
			break;
		case READ_MENU:
			if(3 == value){
				res = true;
			}
//			else if (2 == value) {
//				res = true;
//			}
//			else if (0 == value) {
//				res = true;
//			}
			break;
		case WRITE_MENU:
			if(3 == value){
				res = true;
			}
//			else if (2 == value) {
//				res = true;
//			}
//			else if (0 == value) {
//				res = true;
//			}
			break;
		default:
			System.err.println("A serious error occured!");
			break;
		}
		return res;
	}

	public static void main(String[] args) {
		showUI(UIPhase.MAIN_MENU);
	}

}
