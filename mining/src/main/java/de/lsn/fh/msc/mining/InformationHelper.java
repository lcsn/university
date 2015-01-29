package de.lsn.fh.msc.mining;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class InformationHelper {

	public static double log(double a) {
		return Math.log(a);
	}
	
	public static double log_to_base(double a, int base) {
		return log(a)/log(base);
	}
	
	public static double information(double c) {
		return log(c)/log(2)*(-1);
	}
	
	public static double information(Fraction p) {
		return log(p.toDouble())/log(2)*(-1);
	}
	
	public static double gain(double z, double a) {
		return z-a;
	}
	
	public static double max(double[] values) {
		return DoubleStream.of(values).max().getAsDouble();
	}
	
	public static double entropy(final Id3Attribute a) {
		int[] freq = a.getPropertyRateMap().values().stream().mapToInt(l -> l.intValue()).toArray();
		double[] fractions = Arrays.stream(freq).mapToDouble(value -> new Fraction(value, a.getValues().size()).toDouble()).toArray();
		double entropyAsDouble = 0.0d;
		for (int i = 0; i < fractions.length; i++) {
			double d = fractions[i]*information(fractions[i]);
			if (Double.isNaN(d)) {
				d = 0.0d;
			}
			entropyAsDouble+=d;
		}
		return entropyAsDouble;
	}
	
}
