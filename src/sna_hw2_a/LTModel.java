package sna_hw2_a;

import helper.MyLink;
import helper.MyNode;

public class LTModel extends Model{
	
	public LTModel(){}
	public LTModel(Model model){
		super(model);
	}

	public void changeEdgeWeight(){
		for (MyNode vTemp:getGraph().getVertices()){
			double weight=1.0/getGraph().getPredecessorCount(vTemp);
			for (MyLink edge:getGraph().getInEdges(vTemp)){
				edge.setWeight(weight);
			}
		}
	}
	
	public void tryInfect(MyNode node1,MyNode node2){
		node2.addLoading(getGraph().findEdge(node1, node2));
		if (node2.getLoading()>=MyNode.threshold){
			node2.setState(true);
		}
	}
}
