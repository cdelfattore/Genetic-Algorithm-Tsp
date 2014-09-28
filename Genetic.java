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
	public static Random rnd; //Random number
	//public static Int popSize;

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

		//Print out the edge lengths
		/*for(Integer i : edgeLengths.keySet()) {
			for(Integer j : edgeLengths.get(i).keySet()){
				System.out.println(i + "-" + j + " " + edgeLengths.get(i).get(j));
			}
		}*/

		/*for(int i = 0;i < path.length; i++){
			System.out.println(path[i]);
		}*/

		//Create k-randomly generated states
		/*int[] foo = shuffle(path);
		for(int i = 0;i < foo.length; i++){
			System.out.print(foo[i] + " ");
		}
		System.out.println();*/

		rnd = new Random();

		//Create 4 random sets of strings
		List<int[]> populations = new ArrayList<int[]>();
		populations.add(shuffle(path));
		//To prevent generating duplicate arrays, shuffle the previous generated path
		//int[] prev = new int[points.size()];
		//List of Paths with distances
		List<Path> lPaths = new ArrayList<Path>();
		//4 can be any number
		for(int i = 1; i <= 4; i++){
			populations.add(shuffle(populations.get(i-1)));
			//System.out.println(populations.get(i));
			Path tmp = new Path(populations.get(i-1));
			lPaths.add(tmp);

		}

		//order the lPaths array in order to find the best parents
		//lpaths will now be empty
		//popSize will be the number of paths to carry on through each generation
		int popSize = Integer.valueOf(args[3]);
		ArrayList<Path> orderPaths = sortPathList(lPaths,popSize);
		System.out.println("Inital Path: " + orderPaths.get(0).dist);
		//Print to check if paths are different and inder from smallest to greatest
		
		/*for(Path p : orderPaths){
			System.out.println(p.patha + " " + p.dist);
		}
		System.out.println();*/

		//do k generations of cross overs and mutations
		//System.out.println(args[1]);
		int k = Integer.valueOf(args[1]);
		int numCrossNodes = Integer.valueOf(args[2]);
		int mutateRate = Integer.valueOf(args[4]);
		for(int i = 0; i < k; i++){
			int initOrderPathSize = orderPaths.size()-1;
			for(int j = 0; j < initOrderPathSize; j++ /*= j + 2*/){
				Path tmp = createChildPath(orderPaths.get(j),orderPaths.get(j+1),numCrossNodes,mutateRate);
				orderPaths.add(tmp);
			}

			orderPaths = sortPathList(orderPaths,popSize);

			/*for(Path p : orderPaths){
				System.out.println(p.patha + " " + p.dist);
			}
			System.out.println();*/
			//System.out.println("Generation: " + i + " " + orderPaths.get(0).dist);
			//System.out.println();

		}
		System.out.println("Generation: " + k + " " + orderPaths.get(0).dist);
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

	//method to see if the paths are equal
	/*public static void pathsEqual(int[] a, int[] b){
		//String strA = 
		int[] newA = 
	}*/

	public static String arrayToString (int[] a){
		String str = "";
		for(int i = 0;i < a.length; i++){
			str += a[i]+"-";
		}
		return str;
	}

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
		//keep only the 20 smallest paths
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

	public static Path createChildPath(Path one, Path two, int numCrossNodes, int mutateRate){
		//select the 4th, 5th, 6th node to be crossover
		ArrayList<Integer> child = new ArrayList<Integer>();
		
		//ArrayList<Path> bestParents = new ArrayList<Path>();
		//xn is the amount of Crossover Nodes
		int xn = numCrossNodes;
		for(int i = 0; i < one.patha.size() - xn; i++) {
			child.add(one.patha.get(i));
		}

		//for(int i = 0; i < 3; i++) {
		int i = 0;
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
		//System.out.println(muNum);
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

class Path {
	ArrayList<Integer> patha;
	double dist;
	
	Path(int[] arrayPath){
		this.patha = new ArrayList<Integer>(Arrays.asList(Genetic.toObj(arrayPath)));
		this.dist = calcPathDistance();
	}

	Path(ArrayList<Integer> arrayPath){
		this.patha = new ArrayList<Integer>(arrayPath);
		this.dist = calcPathDistance();
	}

	void setDistance(double dis){
		this.dist = dis;
	}

	//swap simulates the mutation
	void swap(){
		//will generate any random number between 0 and the size of points
		int swapIndexA = (int)(Math.random() * (patha.size() - 1));
		int swapIndexB = (int)(Math.random() * (patha.size() - 1));
		if(swapIndexA == swapIndexB){
			swapIndexB = swapIndexB + 1;
		}
		Collections.swap(this.patha, swapIndexA, swapIndexB);
		this.dist = calcPathDistance();
	}

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