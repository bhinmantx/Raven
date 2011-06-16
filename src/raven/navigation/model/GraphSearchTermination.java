package raven.navigation.model;

import raven.math.graph.SparseGraph;
import raven.systems.RavenObject;
import raven.triggers.Trigger;

public interface GraphSearchTermination<T extends SparseGraph<NavGraphNode<Trigger<?>>, NavGraphEdge>> {
	public boolean isSatisfied(T graph, int target, int currentNodeIndex);

	boolean isSatisfied(T graph, RavenObject target, int currentNodeIndex);
}
