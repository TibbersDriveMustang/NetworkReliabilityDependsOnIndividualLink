package NetworkElements;
import java.util.Collection;

import edu.uci.ics.jung.graph.*;

public class myGraph<V,E> extends UndirectedSparseGraph<V,E> implements Cloneable{
	public Object clone(){  
	    try{  
	        return super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}
}
