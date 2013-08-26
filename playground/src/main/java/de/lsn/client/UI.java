package de.lsn.client;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;

import de.lsn.resource.Constants;
import de.lsn.resource.UIPhase;

public class UI {

	private static boolean runnable = true;
	
	private static String url = Constants.firebaseURL;

	private static String mainMenu = "[1] READ\n" + "[2] WRITE\n" + "[EXIT] EXIT" + ": ";
	private static String readMenu = "[1] SEND GET TO " + url + "\n" + "[RET] RETURN" + ": ";
	private static String writeMenu = "[1] SEND PUT TO " + url + "\n" + "[RET] RETURN" + ": ";

	private static Scanner scan = new Scanner(System.in);

	private static UIPhase parentPhase = UIPhase.MAIN_MENU;

	private static void startUp() {
		showUI(UIPhase.MAIN_MENU);
	}
	
	private static void showUI(UIPhase phase) {
		UIPhase newPhase = phase;
		while (runnable)
			switch (phase) {
			case MAIN_MENU:
				newPhase = UIPhase.stringToPhase(promptMainMenu());
				parentPhase = phase;
				showUI(newPhase);
				break;
			case READ_MENU:
				System.out.println("READ MENU");
				parentPhase = UIPhase.MAIN_MENU;
				newPhase = UIPhase.stringToPhase(promptReadMenu());
				showUI(newPhase);
				break;
			case SEND_GET:
				System.out.println("SEND_GET not yet implemented!");
				parentPhase = UIPhase.READ_MENU;
				newPhase = parentPhase;
				newPhase = parentPhase;
				showUI(newPhase);
				break;
			case WRITE_MENU:
				System.out.println("WRITE MENU");
				parentPhase = UIPhase.MAIN_MENU;
				newPhase = UIPhase.stringToPhase(promptWriteMenu());
				showUI(newPhase);
				break;
			case SEND_PUT:
				System.out.println("SEND_PUT not yet implemented!");
				parentPhase = UIPhase.WRITE_MENU;
				newPhase = parentPhase;
				showUI(newPhase);
				break;
			case RETURN:
				showUI(parentPhase);
				break;
			case EXIT:
				runnable = false;
				scan.close();
				break;
			default:
				System.err.println("UNKNOW COMMAND!");
				showUI(parentPhase);
				break;
			}
	}

	private static String promptMainMenu() {
		System.out.println(mainMenu);
		String readVal = scan.next();
		String res = "-1";
		if (isValid(readVal)) {
			res = readVal;
		}
		return res;
	}

	private static String promptReadMenu() {
		System.out.println(readMenu);
		String readVal = scan.next();
		String res = "-1";
		if (isValid(readVal)) {
			res = readVal;
			if (NumberUtils.isNumber(readVal)) {
				res = "1"+readVal;
			}
		}
		return res;
	}

	private static String promptWriteMenu() {
		System.out.println(writeMenu);
		String readVal = scan.next();
		String res = "-1";
		if (isValid(readVal)) {
			res = readVal;
			if (NumberUtils.isNumber(readVal)) {
				res = "2"+readVal;
			}
		}
		return res;
	}

	private static boolean isValid(String value) {
		boolean res = false;
		Pattern p = Pattern.compile("[0-9]");
		Matcher matcher;
		matcher = p.matcher(String.valueOf((value)));
		if(isReturn(value) || isExit(value) || matcher.matches()){
			res = true;
		}
		return res;
	}

	private static boolean isReturn(String value) {
		return UIPhase.RETURN.stringValue().startsWith(value);
	}

	private static boolean isExit(String value) {
		return UIPhase.EXIT.stringValue().startsWith(value);
	}
	
	public static void main(String[] args) {
		startUp();
	}

}
