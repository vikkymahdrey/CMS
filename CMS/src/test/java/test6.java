import java.util.Arrays;

public class test6 {
	public static void main(String[] args) {
	    //short b=(short) 32769;
		String b = "\b1";
		int[] a;
		a=new int[2];
		a[0]=2;
		a[1]=1;
		Arrays.sort(a);		
	    System.out.println("Value print"+Arrays.binarySearch(a, 1));
		//System.out.println("Value print"+a[1]);
	}
}
