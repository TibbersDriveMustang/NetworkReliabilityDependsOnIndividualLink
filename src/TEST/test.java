package TEST;
import NetworkElements.*;

import java.awt.Dimension;
import java.util.*;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class test {
	int numNodes;
	double p;
	myGraph<Node,Edge> graph;
	ArrayList<Integer> indexList;
	List<Node> nodes;
	List<Edge> edges;
	int[] studentID;
	
	public test(int num, double p){
		numNodes = num;
		this.p = p;
		studentID = new int[]{2,0,2,1,2,2,1,1,3,7};
		graph = new myGraph<Node, Edge>();
		indexList = new ArrayList<Integer>();
		indexList.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10));  //index from 1 to 10
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
	
	public void addOneEdge( Node nodeOne, Node nodeTwo){
		int tempIndex = this.randomIndex();
		Edge temp = new Edge(tempIndex,nodeOne,nodeTwo,setReliability(tempIndex - 1));
		System.out.println("Add " + temp);
		this.edges.add(temp);
		this.graph.addEdge(temp, nodeOne, nodeTwo, EdgeType.UNDIRECTED);
	}
	
	public void addEdges(){
		for(int i = 0; i < this.numNodes; i++){
			for(int j = i + 1; j < this.numNodes; j++){
				this.addOneEdge(this.nodes.get(i), this.nodes.get(j));
			}
		}
	}
	
	
	public double setReliability(int index){
		double temp = Math.ceil(this.studentID[index]/3.0);
		double pi = Math.pow(this.p, temp);
		return pi;
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
	
	public static void main(String args[]){
		test test1 = new test(5,2);
		test1.showGraph();
	}
}
