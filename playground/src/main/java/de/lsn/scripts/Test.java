package de.lsn.scripts;

public class Test {

	public static void main(String[] args) {

		int size = 10;
		int a = size/4;
		
		int center_x = 1;
		int center_y = 1;
		
		double x;
		double y;
		
		// pointy
		System.out.println("Pointy top");		
		System.out.println("Height: " + a * 2);
		System.out.println("Width: " + Math.sqrt(3)/2*a*2);
		System.out.println("Horiz. Distance: " + Math.sqrt(3)/2*a*2);
		System.out.println("Vert. Distance: " + 0.75*a*2);

		for (int i = 0; i < 6; i++) {
			x = center_x + a * Math.cos(2*Math.PI/6*(i+0.5));
			y = center_y + a * Math.sin(2*Math.PI/6*(i+0.5));
			System.out.println(i+": "+x+"|"+y);
		}

		System.out.println();
		
		//flat
		System.out.println("Flat top");
		System.out.println("Height: " + Math.sqrt(3)/2*a*2);
		System.out.println("Width: " + a * 2);
		System.out.println("Horiz. Distance: " + 0.75*a*2);
		System.out.println("Vert. Distance: " + Math.sqrt(3)/2*a*2);
		
		for (int i = 0; i < 6; i++) {
			x = center_x + a * Math.cos(2*Math.PI/6 * i);
			y = center_y + a * Math.sin(2*Math.PI/6 * i);
			System.out.println(i+": "+x+"|"+y);
		}
	}
	
}
