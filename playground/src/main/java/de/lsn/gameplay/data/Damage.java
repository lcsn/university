package de.lsn.gameplay.data;

public enum Damage {

//	D20(0), 	//   0-20 : 20
//	D40(20), 	//  20-45 : 25
//	D60(45), 	//  45-70 : 25
//	D80(70), 	//  70-85 : 15
//	D100(85), 	//  85-95 : 10
//	D200(95);	// 95-100 : 5
	
	D20(0), 	//    0-5 : 5
	D40(5), 	//   5-25 : 20
	D60(25), 	//  25-50 : 25
	D80(50), 	//  50-75 : 25
	D100(75), 	//  75-95 : 20
	D200(95);	// 95-100 : 5
	
	int sup;
	
	private Damage(int sup) {
		this.sup = sup;
	}
	
	public static Damage getMultiplicator(int value) {
		Damage d = D20;
		if(value <= D40.val()) {
			d = D20;
		}
		else if (value <= D60.val()) {
			d = D40;
		}
		else if (value <= D80.val()) {
			d = D60;
		}
		else if (value <= D100.val()) {
			d = D80;
		}
		else if (value <= D200.val()) {
			d = D100;
		}
		else {
			d = D200;
		}
		return d;
	}
	
	public int val() {
		return this.sup;
	}
	
	public String toString() {
		return this.name().toUpperCase();
	}
	
}