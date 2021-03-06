/* Assignment: Project 4 - TSP – Genetic Algorithm
** Name: Chris Del Fattore
** Email: crdelf01@cardmail.louisville.edu
** Description: Implementation of a Genetic algorithm for TSP Problem
*/
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Genetic {
	public static Map<Integer,Point> points; //map of points
	public static Map<Integer,Map<Integer,Double>> edgeLengths; //map of edge lengths for all points
	public static Random rnd; //Random number
	public static ArrayList<Integer> drawArray; //array of the points in order to be draw on the GUI

	public static String main(String[] args) throws IOException {

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

		//Print out the edge lengths
		/*for(Integer i : edgeLengths.keySet()) {
			for(Integer j : edgeLengths.get(i).keySet()){
				System.out.println(i + "-" + j + " " + edgeLengths.get(i).get(j));
			}
		}*/

		/*for(int i = 0;i < path.length; i++){
			System.out.println(path[i]);
		}*/

		rnd = new Random();

		//Create 4 random array of integers
		List<int[]> populations = new ArrayList<int[]>();
		populations.add(shuffle(path));
		//To prevent generating duplicate arrays, shuffle the previous generated path
		
		//List of Paths with distances
		List<Path> lPaths = new ArrayList<Path>();
		//create 4 arrays of random intergers
		for(int i = 1; i <= 4; i++){
			populations.add(shuffle(populations.get(i-1)));
			Path tmp = new Path(populations.get(i-1));
			lPaths.add(tmp);

		}

		//order the lPaths array in order to find the best parents
		//lpaths will now be empty
		//popSize will be the number of paths to carry on through each generation
		int popSize = Integer.valueOf(args[3]);
		ArrayList<Path> orderPaths = sortPathList(lPaths,popSize);
		String strInit = "The inital path distance is " + orderPaths.get(0).dist + ".";
		
		/*for(Path p : orderPaths){
			System.out.println(p.patha + " " + p.dist);
		}
		System.out.println();*/

		//do k generations of cross overs and mutations
		//args array gets input from the GUI.java class
		int k = Integer.valueOf(args[1]);
		int numCrossNodes = Integer.valueOf(args[2]);
		int mutateRate = Integer.valueOf(args[4]);
		for(int i = 0; i < k; i++){
			int initOrderPathSize = orderPaths.size()-1;
			//j represents the smaller paths in the array, only use the first half of these
			//could change this to a fixed number
			for(int j = 0; j <= initOrderPathSize / 2; j++){ 
				//h represents all of the nodes in the list,
				for(int h = 0; h <= initOrderPathSize; h++){ 
					if(h != j)	{ //if to prevent combining the same path with itself
						if(!compareArrayList(orderPaths.get(j),orderPaths.get(h))){
							//Create a child path by calling the create child path.
							Path tmp = createChildPath(orderPaths.get(j),orderPaths.get(h),numCrossNodes,mutateRate);
							//add the new path to orderPaths, will sort the entire list after the for loop terminates
							orderPaths.add(tmp);	
						}
					}
				}
			}
			//sort the orderPaths list so the smaller length paths are at the beginning of the list
			orderPaths = sortPathList(orderPaths,popSize);

			/*for(Path p : orderPaths){
				System.out.println(p.patha + " " + p.dist);
			}
			System.out.println();*/
			//System.out.println(orderPaths.get(0).dist);
			


		}
		//draw array is the path with the shortest distance, this path will be draw on the iframe in GUI.java
		drawArray = orderPaths.get(0).patha;

		//System.out.println("Generation: " + k + " " + orderPaths.get(0).dist);
		//return the intial path length and the smallest path length.
		String strFinal = "The final path distance at generation " + k + " is " + orderPaths.get(0).dist + ".";
		return strInit + "\n" + strFinal + "\n";
	}

	//return true if they are the same list
	public static Boolean compareArrayList(Path a, Path b){
		for(int i = 0; i < a.patha.size();i++){
			if(a.patha.get(i) != b.patha.get(i)) {
				return false;
			}
		}
		return true;
	}

	//Method to compute distance
	//Takes to points as parameters and computes the distance between them.
	//Uses distance formula
	public static double computeDistance(Point a, Point b){
		return Math.sqrt( ((a.x - b.x) * (a.x - b.x )) + ((a.y - b.y ) * (a.y - b.y ) ) );
	}

	//fisher-yates algorithm to shuffle array
	//this will be used to generate the k random populations needed for the start of the genetic algorithm
	public static int[] shuffle(int[] arra) {
		int[] arr = new int[arra.length];
		for(int i = 0;i < arra.length;i++){
			arr[i] = arra[i];
		}
		for(int i = arr.length - 1; i > 0; i--){
			int index = rnd.nextInt(i + 1);
			int a = arr[index];
			arr[index] = arr[i];
			arr[i] = a;
		}
		return arr;
	}

	//simply get the map of points
	public static Map<Integer,Point> getPoints(){
		return points;
	}
	//get the array to be draw on the JFrame
	public static ArrayList<Integer> getDrawArray(){
		return drawArray;
	}
	//converts an array to a string
	public static String arrayToString (int[] a){
		String str = "";
		for(int i = 0;i < a.length; i++){
			str += a[i]+"-";
		}
		return str;
	}
	//takes an array of int s and converts it to a array of Integer Objects
	public static Integer[] toObj(int[] primArray){
		Integer[] fin = new Integer[primArray.length];
		for(int i = 0; i < primArray.length; i++){
			fin[i] = Integer.valueOf(primArray[i]);
		}
		return fin;
	}

	public static ArrayList<Path> sortPathList(List<Path> arrayList, int pop){
		//order the arrayList array in order to find the best parents
		ArrayList<Path> orderPaths = new ArrayList<Path>();
		while(arrayList.size() > 0){
			int indexShortest = -1;
			double disShortest = Double.MAX_VALUE;
			for(int i = 0; i < arrayList.size(); i++){
				if(arrayList.get(i).dist < disShortest){
					indexShortest = i;
					disShortest = arrayList.get(i).dist;
				}
			}
			orderPaths.add(arrayList.get(indexShortest));
			arrayList.remove(indexShortest);
		}
		//keep only the k smallest paths, pop is determined by the population size entered in the text box in GUI.java
		int popSize = pop;
		if(orderPaths.size() > popSize) {
			ArrayList<Path> finalPaths = new ArrayList<Path>();
			for(int i = 0; i < popSize; i++){
			finalPaths.add(orderPaths.get(i));
			}	
			return finalPaths;
		}
		else {
			return orderPaths;
		}

		
	}

	//method used to create child paths
	public static Path createChildPath(Path two, Path one, int numCrossNodes, int mutateRate){
		//array to hold the child path
		//points from path one and two will be used to populate the child arraylist
		ArrayList<Integer> child = new ArrayList<Integer>();
		//xn is the amount of Crossover Nodes (nodes = points)
		//grab the nodes starting at a random integer startIndex
		int startIndex = rnd.nextInt(one.patha.size() - numCrossNodes);
		//System.out.println(startIndex);
		for(int i = startIndex; i < one.patha.size(); i++) {
			child.add(one.patha.get(i));
		}

		int i = 0;
		//Add the nodes at a random index - crossIndex
		int crossIndex = rnd.nextInt(child.size() - 1);
		//System.out.println(crossIndex);
		while(child.size() < points.size()){ //needs to equal size of input
			if(!child.contains(two.patha.get(i))) {
				child.add(crossIndex, two.patha.get(i));
			}
			i++;
		}
		Path tmp = new Path(child);
		
		//Mutate Rate
		//muNum is a random number if this number is between one and the mutate rate
		int muNum = (int)(Math.random() * (100));
		
		//if mutateRate = 100, the it will always mutate
		if(muNum >= 0 && muNum < mutateRate ){
			//System.out.println(muNum + " is less than " + mutateRate);
			tmp.swap();	
		}
		else {
			//System.out.println(muNum + " is not less than " + mutateRate);
		}		
		//System.out.println(tmp.patha + " " + tmp.dist);
		//childPaths.add(tmp);
		return tmp;

	}

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

//objected used to represent a path
class Path {
	ArrayList<Integer> patha;
	double dist;
	
	//construct a path using a array of int
	Path(int[] arrayPath){
		this.patha = new ArrayList<Integer>(Arrays.asList(Genetic.toObj(arrayPath)));
		this.dist = calcPathDistance();
	}
	//Construct using and Arraylist of Integer objects
	Path(ArrayList<Integer> arrayPath){
		this.patha = new ArrayList<Integer>(arrayPath);
		this.dist = calcPathDistance();
	}

	//swap simulates the mutation
	void swap(){
		//will generate any random number between 0 and the size of points
		int swapIndexA = (int)(Math.random() * (patha.size() - 1));
		int swapIndexB = (int)(Math.random() * (patha.size() - 1));
		//if the are the same make swapIndexB slightly bigger
		if(swapIndexA == swapIndexB){
			swapIndexB = swapIndexB + 1;
		}
		//Swap the points in the arraylist
		Collections.swap(this.patha, swapIndexA, swapIndexB);
		this.dist = calcPathDistance();
	}

	//method used to calculate the paths distance
	double calcPathDistance(){
		double finDis = 0.0;
		for(int i = 0; i < patha.size(); i++){
			if(i+1 < this.patha.size()){
				//System.out.println(Genetic.computeDistance(Genetic.points.get(patha.get(i)),Genetic.points.get(patha.get(i+1))));
				finDis += Genetic.computeDistance(Genetic.points.get(patha.get(i)),Genetic.points.get(patha.get(i+1)));	
			}
			else {
				//System.out.println(Genetic.computeDistance(Genetic.points.get(patha.get(0)),Genetic.points.get(patha.get(patha.size() - 1))));
				finDis += Genetic.computeDistance(Genetic.points.get(patha.get(0)),Genetic.points.get(patha.get(patha.size() - 1)));	
			}
		}
		//System.out.println(finDis);
		return finDis;
	}
}