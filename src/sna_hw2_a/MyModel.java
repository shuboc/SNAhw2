package sna_hw2_a;

import helper.MyLink;
import helper.MyNode;
import java.util.Random;


public class MyModel extends Model{

	private Random rand = new Random();
	
	public void changeEdgeWeight(){
		for (MyNode vTemp:graph.getVertices()){
			double weight=1.0/graph.getPredecessorCount(vTemp);
			for (MyLink edge:graph.getInEdges(vTemp)){
				edge.setWeight(weight);
			}
		}
	}
	
	public void tryInfect(MyNode node1, MyNode node2){
		double p1=1.0/3;
		double p2=2.0/3;
		double randNum=rand.nextDouble();
		if (randNum<p1){}
		else if (randNum>=p1 && randNum<p2){
			node2.addLoading(graph.findEdge(node1, node2));
		}
		else if (randNum>p2){
			node2.addLoading(graph.findEdge(node1, node2));
			node2.addLoading(graph.findEdge(node1, node2));
		}
		
		if (node2.getLoading()>=MyNode.threshold){
			node2.setState(true);
		}
	}
	
}
