package NetworkElements;
import java.util.Collection;

import edu.uci.ics.jung.graph.*;

public class myGraph<V,E> extends UndirectedSparseGraph<V,E> implements Cloneable{
	public myGraph(){
	}
	
	/**
	 * Copy Constructor
	 */
	public myGraph(myGraph<V,E> another){
		//this.edges = another.edges;
		this.vertices = another.vertices;
	}
	
	public Object clone(){  
	    try{  
	        return super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}
}
