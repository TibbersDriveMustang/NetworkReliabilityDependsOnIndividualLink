package NetworkElements;

public class Node {
	private int id;
	public Node(int id){
		this.id = id;
	}
	public int getID(){
		return this.id;
	}
	public String toString(){
		return "" + this.id;
	}
}
