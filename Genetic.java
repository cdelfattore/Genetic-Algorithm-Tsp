/* Assignment: Project 4 - TSP – Genetic Algorithm
** Name: Chris Del Fattore
** Email: crdelf01@cardmail.louisville.edu
** Description: 
*/
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Genetic {
	public static Map<Integer,Point> points; //map of points
	public static Map<Integer,Map<Integer,Double>> edgeLengths; //map of edge lengths for all points

	public static void main(String[] args) throws IOException {

		//The Below list is used to store the point information from the input file
		points = new HashMap<Integer,Point>();

		//Takes the filename as a parameter. File contains points and the x and y cooridnates.
		String filename = args[0];

		//BufferedReader used to read input from a file
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		//pattern is the regular expression used to parse throught the input file and find the point number and the point's x and y value.
		//The pattern will find all of the points in the file
		String pattern = "(?m)^\\d+\\s\\d+\\.\\d+\\s\\d+\\.\\d+";
		Pattern r = Pattern.compile(pattern);

		String value = null;

		//the below while loop with go through the file line by line and see if a match has been made with the regular expression.
		//If a match is made, the line is parsed, retrieving the piont name, x and y coordinate values
		//the points are saved in the points list.
		while((value = reader.readLine()) != null){
			Matcher m = r.matcher(value);
			if(m.find()) {
				//add the point to the List of points
				Point p = new Point(Integer.parseInt(value.split(" ")[0]), Double.parseDouble(value.split(" ")[1]), Double.parseDouble(value.split(" ")[2]));
				points.put(p.name,p);
			}
		}

		/*for(Integer p : points.keySet()){
			System.out.println(points.get(p).name + " " + points.get(p).x + " " + points.get(p).y);	
		}*/

		//Map to store the edgeLengths for all of the edges
		edgeLengths = new HashMap<Integer,Map<Integer,Double>>();

		//simple array of intergers used to create random populations
		int[] path = new int[points.size()];

		//Iterate through all of the points
		//create all the permutaions of edges
		//find their distance
		for(Integer a : points.keySet()){
			//System.out.println(a + " " + points.get(a).x + " " + points.get(a).y);
			path[a-1] = a;
			Map<Integer,Double> listDoubles = new HashMap<Integer,Double>();
			for(Integer b : points.keySet()){
				if(a==b) {
					listDoubles.put(b,Double.MAX_VALUE);
				}
				else {
					listDoubles.put(b,computeDistance(points.get(a),points.get(b)));	
				}
			}
			edgeLengths.put(a,listDoubles);
		}

		/*for(Integer i : edgeLengths.keySet()) {
			for(Integer j : edgeLengths.get(i).keySet()){
				System.out.println(i + "-" + j + " " + edgeLengths.get(i).get(j));
			}
		}*/

		/*for(int i = 0;i < path.length; i++){
			System.out.println(path[i]);
		}*/

		//Create k-randomly generated states
		int[] foo = shuffle(path);
		/*for(int i = 0;i < foo.length; i++){
			System.out.print(foo[i] + " ");
		}
		System.out.println();*/

		
		//need to define a cross over method

		//need to define a mutation method		

		//need to check if paths are the same at some point
		//String tmp = Arrays.toString(foo);
		//System.out.println(arrayToString(foo));
	}

	//Method to compute distance
	//Takes to points as parameters and computes the distance between them.
	//Uses distance formula
	public static double computeDistance(Point a, Point b){
		return Math.sqrt( ((a.x - b.x) * (a.x - b.x )) + ((a.y - b.y ) * (a.y - b.y ) ) );
	}

	//fisher-yates algorithm to shuffle array
	//this will be used to generate the k random populations needed for the start of the genetic algorithm
	public static int[] shuffle (int[] arr) {
		Random rnd = new Random();
		for(int i = arr.length - 1; i > 0; i--){
			int index = rnd.nextInt(i + 1);
			int a = arr[index];
			arr[index] = arr[i];
			arr[i] = a;
		}
		return arr;
	}

	//method to see if the paths are equal
	/*public static void pathsEqual(int[] a, int[] b){
		//String strA = 
		int[] newA = 
	}

	public static String arrayToString (int[] a){
		String str = "";
		for(int i = 0;i < a.length; i++){
			str += a[i]+"-";
		}
		return str;
	}*/
}

//Object used to represent a single point
//Point Stores the Name, X and Y Value
//with methods to retrieve the name, x and y value
//and a method to set the name.
//Turns out there is a java class called point
class Point {
	int name;
	double x, y;
	//constructor
	Point(int name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	//needed when converting a number to a letter and vise versa
	void setName(int a) {
		this.name = a;
	}
}