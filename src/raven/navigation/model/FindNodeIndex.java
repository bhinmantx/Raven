package raven.navigation.model;
import raven.game.model.BaseGameEntity;
import raven.game.model.RavenBot;
import raven.math.graph.GraphEdge;
import raven.math.graph.SparseGraph;
import raven.triggers.*;
public class FindNodeIndex {
	private SparseGraph<NavGraphNode<Trigger<RavenBot>>, NavGraphEdge> navGraph = new SparseGraph<NavGraphNode<Trigger<RavenBot>>, NavGraphEdge>();
	 public static boolean isSatisfied(GraphEdge G, int target, int CurrentNodeIdx)
	  {
	    return CurrentNodeIdx == target;
	  }

	//--------------------------- FindActiveTrigger ------------------------------

	//the search will terminate when the currently examined graph node
	//is the same as the target node.
	
	
	//public static boolean isSatisfied(Trigger<BaseGameEntity> G, int target, int CurrentNodeIdx ){ }
	

	  //template <class graph_type>
	  public static boolean isSatisfied(SparseGraph<NavGraphNode<Trigger<RavenBot>>, NavGraphEdge> G, int target, int CurrentNodeIdx){
	  boolean satisfied = false;

	    //get a reference to the node at the given node index
	  NavGraphNode<Trigger<RavenBot>> node = G.getNode(CurrentNodeIdx);

	    //if the extrainfo field is pointing to a giver-trigger, test to make sure 
	    //it is active and that it is of the correct type.
	    if ((node.extraInfo()!= null) && 
	         node.extraInfo().isActive() && 
	        (node.extraInfo().entityType().equals(target)))
	    {    
	      satisfied = true;
	    }

	    return satisfied;
	  }
	};



