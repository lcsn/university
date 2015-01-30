package de.lsn.fh.msc.mining;


public class Logical {


	public static boolean proof(ITask t, Boolean... args) {
		return (boolean) t.eval(args[0], args[1]);
	}
	
	public static boolean proof(ITask1 t, Boolean... args) {
		return (boolean) t.eval(args[0], args[1], args[2]);
	}
	
	public static void main(String[] args) {
		System.out.println(proof((a, b) -> a||(a^b), true, true));
		System.out.println(proof((a, b) -> a||(a^b), true, false));
		System.out.println(proof((a, b) -> a||(a^b), false, true));
		System.out.println(proof((a, b) -> a||(a^b), false, false));
		
		System.out.print("\n\n");
		
		System.out.println(proof((a, b, c) -> a&&b^c, true, true, true));
		System.out.println(proof((a, b, c) -> a&&b^c, true, true, false));
		System.out.println(proof((a, b, c) -> a&&b^c, true, false, true));
		System.out.println(proof((a, b, c) -> a&&b^c, true, false, false));
		System.out.println(proof((a, b, c) -> a&&b^c, false, true, true));
		System.out.println(proof((a, b, c) -> a&&b^c, false, true, false));
		System.out.println(proof((a, b, c) -> a&&b^c, false, false, true));
		System.out.println(proof((a, b, c) -> a&&b^c, false, false, false));
	}
	
}

class LogicTask {

	public boolean eval(ITask t, boolean a, boolean b) {
		return (Boolean) t.eval(a, b);
	}
	
	public boolean eval(ITask1 t, boolean a, boolean b, boolean c) {
		return (Boolean) t.eval(a, b, c);
	}
	
}

@FunctionalInterface
interface ITask {
	boolean eval(boolean a, boolean b);
}

@FunctionalInterface
interface ITask1 {
	boolean eval(boolean a, boolean b, boolean c);
}
