package de.lsn.raspberrypi.framework.gpio.dev.servo;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Formatter.BigDecimalLayoutForm;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class ServoValueProvider {

//	[1]: PULSE, [2]: CYCLE
//	private static final long[] DEFAULT_PULSE = {50, 20};  

	private static final Long[] DEFAULT_CYCLE = {new Long(1500000), new Long(1000000l)};  

//	private static final Long MIN_CYCLE 	= new Long(500000);		// 0
//	private static final Long NEUTRAL 		= new Long(1500000);  	// 90
//	private static final Long MAX_CYCLE 	= new Long(2500000);	// 180
	
	
	public static final Long[] LEFT 	= {new Long(750000), new Long(19250000)};
	public static final Long[] CENTER 	= {new Long(1500000), new Long(18500000)};
	public static final Long[] RIGHT 	= {new Long(2500000), new Long(17500000)};
	
//	public static final Long[][] LEFT 	= {{0l, 750000l}, {19l, 250000l}};
//	public static final Long[][] CENTER = {{1l, 500000l}, {18l, 500000l}};
//	public static final Long[][] RIGHT 	= {{2l, 500000l}, {17l, 500000l}};
	
	private static final double MIN_CYCLE 	= 2.5d;		// 0
	private static final double NEUTRAL 	= 7.5d;  	// 90
	private static final double MAX_CYCLE 	= 12.5d;	// 180
	
	private static final double INC_PER_DEG = .0555d;	// 180
	
//	private static final Long INC_PER_DEG	= new Long(11111); 
	
//	private HashMap<Integer, Long[]> angleCache = new HashMap<Integer, Long[]>();
	
	private static ServoValueProvider instance;
	
	private int frequency = 50;
	
	public ServoValueProvider() {
	}
	
	public static ServoValueProvider getInstance() {
		if (null == instance) {
			instance = new ServoValueProvider();
		}
		return instance;
	}
	
	public Long[] angleToDuty(int angle) {
		long cycle = (1000/frequency)*1000000;
		double dutycycle = new BigDecimal(INC_PER_DEG*angle+MIN_CYCLE).round(new MathContext(2, RoundingMode.HALF_UP)).doubleValue();
		long duty = (long) (((cycle/100)*dutycycle));
		return new Long[]{duty, cycle-duty};
	}
	
	public void print(int angle) {
		Long[] cycle = angleToDuty(angle);
		System.out.println("On:"+cycle[0]+", Off:"+cycle[1]);
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public static void main(String[] args) {
		
//		Servo servo = new Servo(null, null);
//		ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
//		threadExecutor.execute(servo);
//		servo.adjustAngle(180);
//		servo.adjustAngle(135);
//		servo.adjustAngle(90);
//		servo.adjustAngle(45);
//		servo.adjustAngle(0);
		
		ServoValueProvider.getInstance().print(0);
		ServoValueProvider.getInstance().print(90);
		ServoValueProvider.getInstance().print(180);

		ServoValueProvider.getInstance().setFrequency(50);
		ServoValueProvider.getInstance().print(0);
		ServoValueProvider.getInstance().print(90);
		ServoValueProvider.getInstance().print(180);
		
	}
	
}
