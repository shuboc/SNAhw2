package sna_hw2_a;

import helper.MyLink;
import helper.MyNode;

public class LTModel extends Model{

	public void changeEdgeWeight(){
		for (MyNode vTemp:graph.getVertices()){
			double weight=1.0/graph.getPredecessorCount(vTemp);
			for (MyLink edge:graph.getInEdges(vTemp)){
				edge.setWeight(weight);
			}
		}
	}
	
	public void tryInfect(MyNode node1,MyNode node2){
		node2.addLoading(graph.findEdge(node1, node2));
		if (node2.getLoading()>=MyNode.threshold){
			node2.setState(true);
		}
	}
}
