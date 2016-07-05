package TEST;
import NetworkElements.*;
import Drawing.drawChart;

import java.awt.Dimension;
import java.util.*;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.algorithms.shortestpath.BFSDistanceLabeler;

public class test {
	static double systemReliabilities[];
	int numNodes;
	int numEdges;
	int combinNum;
	double p;						//edge reliability
	myGraph<Node,Edge> graph;		
	ArrayList<Integer> indexList;		//d[i] to calculate p[i]
	List<Node> nodes;
	List<Edge> edges;
	int[] studentID;
	drawChart barChart;     //bar chart to show system reliabilities depends on p
	List<int[]> combinations;		//combinations of component states
	
	BFSDistanceLabeler<Node,Edge> BFS;
	
	public test(int num, double p){
		numNodes = num;
		this.p = p;
		numEdges = numNodes * (numNodes - 1);
		studentID = new int[]{2,0,2,1,2,2,1,1,3,7};
		graph = new myGraph<Node, Edge>();
		indexList = new ArrayList<Integer>();
		indexList.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10));  //index from 1 to 10
		combinations = new ArrayList<int[]>();
		Collections.shuffle(indexList);
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		this.addNumberOfNodes();
		this.addEdges();
		System.out.println(this.graph.getEdgeCount() + " Undirected Edges Created");
	}
	//di
	public int getIDDigit(int index){
		return studentID[index];
	}
	
	public int randomIndex(){
		int index = this.indexList.remove(this.indexList.size() - 1);
		return index;
	}
	
	//start from 0
	public void addNumberOfNodes(){
		for(int i = 0; i < this.numNodes; i++){
			Node temp = new Node(i);
			this.graph.addVertex(temp);
			this.nodes.add(temp);
		}
		System.out.println(this.numNodes + " Nodes Created");
	}
	/**
	 * Add one edge to this.edges & this.graph
	 * @param nodeOne
	 * @param nodeTwo
	 */
	public void addOneEdge( Node nodeOne, Node nodeTwo){
		int tempIndex = this.randomIndex();
		Edge temp = new Edge(tempIndex,nodeOne,nodeTwo,setReliability(tempIndex - 1));
		System.out.println("Add " + temp);
		this.edges.add(temp);
		this.graph.addEdge(temp, nodeOne, nodeTwo, EdgeType.UNDIRECTED);
	}
	/**
	 * loop to add all edges
	 */
	public void addEdges(){
		for(int i = 0; i < this.numNodes; i++){
			for(int j = i + 1; j < this.numNodes; j++){
				this.addOneEdge(this.nodes.get(i), this.nodes.get(j));
			}
		}
	}
	/**
	 * Check if graph is connected
	 * @return
	 * boolean
	 */
	public boolean checkConnectivity(){
		BFS = new BFSDistanceLabeler<Node,Edge>();
		BFS.labelDistances(graph, nodes.get(0));
		Set<Node> temp = BFS.getUnvisitedVertices();
		if(temp.isEmpty())
			return false;
		return true;
	}
	
	/**
	 * get all k combinations ID from int[] ID,
	 * @param ID
	 */
	public void changeEdgeState(int[] ID){
		for(int i = 0; i < ID.length; i++){
			int[] temp = this.combinations.get(ID[i]);  // get int[] components state for one system state
			for(int j = 0; j < this.numEdges; j++){
				if(temp[j] == 1){
					this.edges.get(i).flipState();
					asda
				}
			}
		}
	}
	
	/**
	 * Set p for each edge
	 * @param index
	 * index of edge
	 * @return
	 * reliability of the index edge
	 */
	public double setReliability(int index){
		double temp = Math.ceil(this.studentID[index]/3.0);
		double pi = Math.pow(this.p, temp);
		return pi;
	}
	/**
	 * for 20 edge components
	 * @param p
	 * preset p for unique-p-system
	 * @return
	 */
	public double getSystemReliability(double p){
		this.p = p;
		double allEdgesOfNodeNotWork = Math.pow(1 - this.p, this.numNodes - 1);
		double result = 1 - (1 - Math.pow(1 - allEdgesOfNodeNotWork, this.numNodes));
		System.out.println("System Reliability" + "(p = " + this.p + ") = " + result);
		return result;
	}
	/**
	 * get System Reliability of Non-unique-p-system
	 * @return
	 * (double)System Reliability
	 */
	public double getSystemReliability(){
		double result;
		return result;
	}
	
	public void showGraph(){
		CircleLayout temp = new CircleLayout(this.graph);
		temp.setRadius(380);
		BasicVisualizationServer vs = new BasicVisualizationServer(temp,new Dimension(1000,800));
		vs.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
	    JFrame frame = new JFrame("Graph");
	    frame.getContentPane().add(vs);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
	
	public void showReliabilityGraph(double[] systemRel){
		barChart = new drawChart("System Reliability", systemRel);
		barChart.centerChart();
		barChart.setVisible(true);
	}
//Combination Generating Module	
	/**
	 * set combinations to Combinations<int[]>, each int[] will represent one system state, for each component 1 flip , 0 remain
	 */
	public void setCombinations(){
		
		for(int i = 0; i <= 10; i++){
			int[] temp = new int[10];
			pickCombin(i,0,temp);     // set number of i edges` states to 1, others 0
		}
		System.out.println("Combination numbers: " + this.combinNum);
	}	
	public void pickCombin(int i,int head,int[] temp){
		if(i == 0){
			System.out.println(Arrays.toString(temp));
			this.combinations.add(temp);
			this.combinNum++;
			return;
		}
		for(int j = head; j < 10; j++){
			int[] temp2 = temp.clone();
			temp2[j] = 1;
			pickCombin(i - 1, j + 1,temp2);
		}
	}
//*********************	
	/**
	 * 
	 * @param ID
	 * the random ID for Combinations[ID]
	 * @param k
	 * how many IDs want
	 * @return
	 */
	public void getKCombinationID(int[] ID, int k){
		HashSet<Integer> temp = new HashSet<Integer>();
		while(k > 0){
			int rand = (int)(Math.random() * 1024);
			if(!temp.contains(rand)){
				temp.add(rand);
				ID[k - 1] = rand; 
				k--;
			}
		}
	}
	/**
	 * 
	 * @param k 
	 * get System Reliability with K, repeatedly 5 times and average them
	 * @return result
	 * return averaged reliability
	 */
	public double getReliabilityForK(int k){
		double result;
		for(int i = 0; i < 5; i++){
			int[] ID = new int[k];
			this.getKCombinationID(ID, k);
			changeEdgeState(ID);
			//
		}
		return result;
	}
	
	public static void main(String args[]){
		test test1 = new test(5,0.85);  //(numOfNodes,p) = (5,2)
		test.systemReliabilities = new double[20];
		for(int i = 0; i < 20; i++){
			double p = (i + 1) * 0.05;
			systemReliabilities[i] = test1.getSystemReliability(p);
		}
		//test1.showGraph();
		test1.showReliabilityGraph(test.systemReliabilities);
		//fix p = 0.9
		test1.getSystemReliability(0.9);
		//pick k combinations randomly and fix the corresponding system condition
		test1.setCombinations();
		
	}
}
