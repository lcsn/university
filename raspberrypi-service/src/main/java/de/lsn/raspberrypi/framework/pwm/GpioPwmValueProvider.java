package de.lsn.raspberrypi.framework.pwm;

import java.util.concurrent.ConcurrentHashMap;

public class GpioPwmValueProvider {

	private static final Long[] DEFAULT_CYCLES = {1000l, 0l};

	private static GpioPwmValueProvider instance;
	
	private int granularity = 10;
	private ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Long[]>> valueMap = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Long[]>>();
	
	public GpioPwmValueProvider() {
//		provide();
	}
	
	public int getGranularity() {
		return granularity;
	}
	
	public void setGranularity(int granularity) {
		this.granularity = granularity<1?1:granularity;
	}
	
	public static GpioPwmValueProvider getInstance() {
		if (null == instance) {
			instance = new GpioPwmValueProvider();
		}
		return instance;
	}
	
	public void provide() {
		for (int i = granularity; i <= 100; i+=granularity) {
			for (int j = 1; j <= 100; j++) {
				ConcurrentHashMap<Integer, Long[]> dutyMap;
				if (valueMap.containsKey(i)) {
					dutyMap = valueMap.get(i);
				}
				else {
					dutyMap = new ConcurrentHashMap<Integer, Long[]>();
					valueMap.put(i, dutyMap);
				}
				long frequency = frequency(j);
				long dutycycle = dutycycle(i, (int)frequency);
				dutyMap.put(j, new Long[]{dutycycle/1000, (frequency-dutycycle)/1000});
			}
		}
	}
	
	public synchronized Long[] getValue(int duty, int freq) {
		Long[] result = DEFAULT_CYCLES;
		if (valueMap.containsKey(duty)) {
			if (valueMap.get(duty).containsKey(freq)) {
				result = valueMap.get(duty).get(freq);
			}
		}
		return result;
	}
	
	public long dutycycle(int duty, int freq) {
		if (duty<1||freq<1) {
			return 0l;
		}
		else {
			return (long)((float)(freq*duty)/100);
		}
	}

	public long frequency(int freq) {
		if (freq<1) {
			return 0l;
		}
		else {
			return (long)((float)(1000000/freq));
		}
	}
	
	public void print(int duty, int freq) {
		Object[] values = getValue(duty, freq);
		System.out.println("Up: "+values[0]+", Down: "+values[1]);
	}
	
	public static void main(String[] args) {
		
		GpioPwmValueProvider.getInstance().provide();
		
//		1hz
		System.out.println("1hz");
		GpioPwmValueProvider.getInstance().print(100, 1);
		GpioPwmValueProvider.getInstance().print(80, 1);
		GpioPwmValueProvider.getInstance().print(50, 1);
		GpioPwmValueProvider.getInstance().print(20, 1);
		
//		2hz
		System.out.println("2hz");
		GpioPwmValueProvider.getInstance().print(100, 2);
		GpioPwmValueProvider.getInstance().print(80, 2);
		
//		10hz
		System.out.println("10hz");
		GpioPwmValueProvider.getInstance().print(100, 10);
		GpioPwmValueProvider.getInstance().print(80, 10);
		
//		20hz
		System.out.println("20hz");
		GpioPwmValueProvider.getInstance().print(100, 20);
		GpioPwmValueProvider.getInstance().print(80, 20);

//		40hz
		System.out.println("40hz");
		GpioPwmValueProvider.getInstance().print(100, 40);
		GpioPwmValueProvider.getInstance().print(80, 40);
		GpioPwmValueProvider.getInstance().print(20, 40);

//		1hz, granularity 5 
		System.out.println("Granularity to 5");
		System.out.println("1hz");
		GpioPwmValueProvider.getInstance().setGranularity(5);
		GpioPwmValueProvider.getInstance().provide();
		GpioPwmValueProvider.getInstance().print(95, 1);
		GpioPwmValueProvider.getInstance().print(75, 1);
		GpioPwmValueProvider.getInstance().print(25, 1);
	}
	
}
