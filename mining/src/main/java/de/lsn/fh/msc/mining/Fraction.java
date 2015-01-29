package de.lsn.fh.msc.mining;

public class Fraction {
	
	int numerator;
	int denominator;
	
	public Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public double toDouble() {
		if (arithmeticCheck(getNumerator()) && arithmeticCheck(getDenominator())) {
			return new Double(getNumeratorAsDouble()/getDenominatorAsDouble());
		}
		return 0.0d;
	}
	
	private boolean arithmeticCheck(int val) {
		boolean result = false;
		if (0 != val) {
			result = true;
		}
		return result;
	}

	public double getNumeratorAsDouble() {
		return new Double(getNumerator());
	}
	
	public int getNumerator() {
		return numerator;
	}
	
	public double getDenominatorAsDouble() {
		return new Double(getDenominator());
	}
	
	public int getDenominator() {
		return denominator;
	}
	
}
