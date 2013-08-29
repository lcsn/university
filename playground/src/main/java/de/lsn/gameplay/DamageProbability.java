package de.lsn.gameplay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.lsn.gameplay.data.Damage;

public class DamageProbability {

	public static void main(String[] args) {

		HashMap<Damage, List<BigDecimal>> probabilityMap = new HashMap<Damage, List<BigDecimal>>();
		int n = 20000;
		BigDecimal b = new BigDecimal(n);
		
		for (int i = 1; i < n; i++) {
			System.out.println("DRAW "+ i);
			Object[][] data = new Object[n][2];
			HashMap<Damage, Result> damageMap = new HashMap<Damage, Result>();
			for (int j = 1; j < n; j++) {
				Integer r = new Random().nextInt(100) + 1;
				Damage d = Damage.getMultiplicator(r);
				data[j][0] = d;
				data[j][1] = r;
			}
			
			for (int k = 1; k < n; k++) {
				Damage d = (Damage) data[k][0];
				Result res;
				if(damageMap.containsKey(d)) {
					res = damageMap.get(d);
					damageMap.remove(d);
					damageMap.put(d, res.inc());
				}
				else {
					res = new Result();
					damageMap.put(d, res.inc());
				}
				res.add(new Long((Integer) data[k][1]));
			}
			
			for (Damage d : damageMap.keySet()) {
				Result res = damageMap.get(d);
				System.out.println(res.getCount()+" x "+d+" of "+n);
				
				List<BigDecimal> probability;
				if(probabilityMap.containsKey(d)) {
					probability = probabilityMap.get(d);
				}
				else {
					probability = new ArrayList<BigDecimal>();
					probabilityMap.put(d, probability);
				}
				
				BigDecimal a = new BigDecimal(res.getCount());
				BigDecimal tmp = a.divide(b).multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
				probability.add(tmp);
			}
		}
		
		for (Damage d : probabilityMap.keySet()) {
//			System.out.println(d);
			
			List<BigDecimal> probabilities = probabilityMap.get(d);
			BigDecimal median = BigDecimal.ZERO;
			for (BigDecimal bd : probabilities) {
				median = median.add(bd);
//				System.out.print(bd+"@");
			}
			median = median.divide(new BigDecimal(probabilities.size()), 2, RoundingMode.HALF_UP).divide(BigDecimal.TEN.multiply(BigDecimal.TEN));
			System.out.println("Median for " + d + " : " + median);
		}
		
	}

}

class Result {
	
	private Long count = 0l;
	private List<Long> values = new ArrayList<Long>();

	public Result() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getCount() {
		return count;
	}
	
	public void add(Long val) {
		this.values.add(val);
	}

	public Result inc() {
		this.count++;
		return this;
	}
	
	public void print() {
		for (Long l	: values) {
			System.out.print(l+", ");
		}
	}
	
}
