import java.util.*;
public class Path {
	public static List<Integer> patha;
	public static double dist;
	
	public Path(int[] arrayPath, double dis){
		//patha = new ArrayList<Integer>();
		this.patha = Arrays.asList(arrayPath);
		this.dist = dis;
	}

	public static void setDistance(double dis){
		this.dist = dis;
	}
}