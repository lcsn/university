package de.lsn.fh.msc.mining;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class Scratch {

	public static void main(String[] args) {
		
//		boolean[] all = { false, true };
//		for (boolean a : all) {
//			for (boolean b: all) {
//				boolean c = a ^ b;
//				System.out.println(a + " ^ " + b + " = " + c);
//			}
//		}
//		System.out.println();
//		System.out.println();
//		System.out.println("true ^ true && true\t= "+(true ^ true && true));
//		System.out.println("true ^ true && false\t= "+(true ^ true && false));
//		System.out.println("true ^ false && true\t= "+(true ^ false && true));
//		System.out.println("true ^ false && false\t= "+(true ^ false && false));
//		System.out.println("false ^ true && true\t= "+(false ^ true && true));
//		System.out.println("false ^ true && false\t= "+(false ^ true && false));
//		System.out.println("false ^ false && true\t= "+(false ^ false && true));
//		System.out.println("false ^ false && false\t= "+(false ^ false && false));
		
		CSV csv = null;
		try {
			 csv = CSV.read("C:/STUDIUM/DATAMINING/trainingsdaten_tennis_spielen.csv", true);
			 csv.print();
			 ID3.process(csv, new String("spielen".getBytes(), "UTF-8"));
//			 csv = CSV.read("C:/STUDIUM/DATAMINING/trainingsdaten_id3_0.csv", true);
//			 csv.print();
//			 ID3.process(csv, new String("Kuendigung".getBytes(), "UTF-8"));
//			 csv = CSV.read("C:/STUDIUM/DATAMINING/trainingsdaten_id3_1 - Kopie.csv", true);
//			 csv.print();
//			 ID3.process(csv, new String("Kuendigung".getBytes(), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean targetAvailable(String target, String[] headers) {
		String s = Arrays.stream(headers).filter(t -> t.equals(target)).findFirst().orElse("");
		return StringUtils.isNotEmpty(s);
	}
	
}




