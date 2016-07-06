package TEST;
import NetworkElements.*;
import Drawing.drawChart;

import java.awt.Dimension;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
	int[] systemState; 	//1024 system state;
	
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
			//this.graph.addVertex(temp);      if edges be added, nodes will be added to graph automatically
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
	 * Check if graph is connected
	 * @return
	 * boolean
	 */
	public boolean checkConnectivity(myGraph<Node,Edge> graphTemp){
		BFS = new BFSDistanceLabeler<Node,Edge>();
		BFS.labelDistances(graphTemp, nodes.get(0));
		Set<Node> temp = BFS.getUnvisitedVertices();
		if(temp.isEmpty()){
			System.out.println("Graph is connected, System State is UP");
			return true;
		}
		System.out.println("Graph is not connected, System State is DOWN");
		return false;
	}	
	
	/**
	 * Save edges state + system state into temp[]
	 * @param temp
	 * @return
	 */
	public boolean checkConnectivity(int[] temp){
		BFS = new BFSDistanceLabeler<Node,Edge>();
		myGraph<Node,Edge> tempGraph = new myGraph<Node,Edge>();
		Collection<Edge> tempEdges = this.graph.getEdges();
		for(Edge edge: tempEdges){
			Edge tempEdge = new Edge(edge.getIndex(),edge.getNodeOne(),edge.getNodeTwo());
			tempGraph.addEdge(tempEdge, tempEdge.getNodeOne(), tempEdge.getNodeTwo(), EdgeType.UNDIRECTED);
		}
		
		//(myGraph<Node,Edge>)this.graph.clone();
		System.out.println("TEST 136 temp: " + Arrays.toString(temp) );
		for(int i = 0; i < 10;i++){
			if(temp[i] == 0){
				System.out.println("Edge want to remove: index " + i);
				System.out.println("temp.Edges: " + tempGraph);
				if(!tempGraph.removeEdge(i)){    //edge index from 1 to 10
					System.out.println("No Edge Removed");
				}
				System.out.println("Edges after removed:  " + tempGraph); 
			} 
		}
		long m = 2000000000;
		while(m > 0)
			m--;

		BFS.labelDistances(tempGraph, nodes.get(0));
		Set<Node> tempSet = BFS.getUnvisitedVertices();
		if(tempSet.isEmpty()){
			System.out.println("Graph is connected, System State is UP");
			temp[10] = 1;
			return true;
		}
		System.out.println("Graph is not connected, System State is DOWN");
		temp[10] = 0;
		return false;
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
		double result = 0;
		int count = 0;
		for(int[] itr: this.combinations){   //1024
			double temp = 1.0;
			if(itr[10] == 1){
				count++;
				for(int i = 0; i < 10; i++){
					if(itr[i] != 0){
						temp *= this.p;
					}
					else{
						temp *= (1 - this.p);
					}
				}
			}
			result += temp;
		}
		System.out.println("TEST 186: Count: " + count);
		System.out.println("TEST 187: result: " + result);
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
			int[] temp = new int[11];
			pickCombin(i,0,temp);     // set number of i edges` states to 1, others 0
		}
		System.out.println("Number of Combinations: " + this.combinNum);
	}	
	public void pickCombin(int i,int head,int[] temp){
		if(i == 0){
			if(checkConnectivity(temp) == true){
				temp[10] = 1;
			}
			else{
				temp[10] = 0;
			}
			System.out.println("temp to be added : " + Arrays.toString(temp));
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
		return;
	}
	/**
	 * 
	 * @param k 
	 * get System Reliability with K, repeatedly 5 times and average them
	 * @return result
	 * return averaged reliability
	 */
	public double getReliabilityForK(int k){
		double result = 0;
		for(int i = 0; i < 5; i++){
			int[] ID = new int[k];
			this.getKCombinationID(ID, k);
			System.out.println(Arrays.toString(ID));
			flipSystemState(ID);
			result += getSystemReliability();
			
		}
		System.out.println("Reliability for k: " + result / 5.0);
		return result / 5.0;
	}
	
	/**
	 * get all k combinations ID from int[] ID,
	 * @param ID
	 */
	public void flipSystemState(int[] ID){
		for(int i = 0; i < ID.length; i++){
			int[] temp = this.combinations.get(ID[i]);  // get int[] components state for one system state
			if(temp[10] == 1)
				temp[10] = 0;
			else 
				temp[10] = 1;
		}
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
		//test1.checkConnectivity(test1.graph);
		test1.getReliabilityForK(3);
		
	}
}
