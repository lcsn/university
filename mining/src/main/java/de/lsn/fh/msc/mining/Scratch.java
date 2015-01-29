package de.lsn.fh.msc.mining;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class Scratch {

	public static void main(String[] args) {
		CSV csv = null;
		try {
			 csv = CSV.read("C:/STUDIUM/DATAMINING/trainingsdaten_tennis_spielen.csv", true);
			 csv.print();
			 ID3.process(csv, new String("spielen".getBytes(), "UTF-8"));
//			 csv = CSV.read("C:/STUDIUM/DATAMINING/trainingsdaten_id3_0.csv", true);
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




