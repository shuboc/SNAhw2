package helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sna_hw2_a.ICModel;
import sna_hw2_a.LTModel;
import sna_hw2_a.Model;
import sna_hw2_a.MyModel;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;



public class AttackStrategy {
	
	public static int neighborAttack(DirectedGraph<MyNode,MyLink> graph){
		// target[0]  target node
		// target[1]  # neighbors infected
		// target[2]  # neighbors
		int[] target = new int[3];
		for (MyNode vTemp:graph.getVertices()){
			int num=0;
			int neighborCount=graph.getNeighborCount(vTemp);
			if (vTemp.isState()==false){
				
				for (MyNode neighbor:graph.getNeighbors(vTemp)){
					if (neighbor.isState()==true){
						num++;
					}
				}
				
				if (num>target[1] || (num==target[1] && neighborCount>target[2])){
					target[0]=vTemp.getId();
					target[1]=num;
					target[2]=graph.getNeighborCount(vTemp);
				}
			}
		}
		return target[0];
	}
	
	public static List<Integer> topKDegree(Model model, int removeCount){
		
		//Sort from small to large
		List<MyNode> degreeList = new ArrayList<MyNode>();
		for(MyNode node : model.getGraph().getVertices()){
			node.setMeasure(model.getGraph().degree(node));
			degreeList.add(node);			
		}
		Collections.sort(degreeList);  
		
		//Remove the top-k-degree nodes
		List<Integer> immuneList = new ArrayList<Integer>();
		int count = 0;
		for(int i=degreeList.size()-1; i>=0; i--){
			MyNode node = degreeList.get(i);
			if( node.isState() == false ){
				immuneList.add(node.getId());
				MyNode toRemove = model.getMap().get(node.getId());
				model.getGraph().removeVertex(toRemove);
				count++;
			}
			if(count > removeCount) break;
		}
		
		//Return the index list of top-k-degree nodes
		return immuneList;
		
	}
	
	
	public static int influencialAttack(Model model){
		int target=-1;
		if (model instanceof ICModel){
			ICModel icmodel;
			int num=20000;
			for (MyNode vTemp:model.getGraph().getVertices()){
				if (vTemp.isState()==false){
					icmodel=new ICModel(model);
					icmodel.getGraph().removeVertex(icmodel.getMap().get(vTemp.getId()));
					icmodel.diffuse();
			
					if (num > icmodel.getHasInfected().size()){
						num = icmodel.getHasInfected().size();
						target=vTemp.getId();
					}	
				}
			}
			return target;
		}
		else if (model instanceof LTModel){
			LTModel ltmodel;
			int num=20000;
			for (MyNode vTemp:model.getGraph().getVertices()){
				if (vTemp.isState()==false){
					ltmodel=new LTModel(model);
					ltmodel.getGraph().removeVertex(ltmodel.getMap().get((vTemp).getId()));
					ltmodel.diffuse();
					if (num > ltmodel.getHasInfected().size()){
						num = ltmodel.getHasInfected().size();
						target=vTemp.getId();
					}	
				}
			}
			return target;
		}
		else if (model instanceof MyModel){
			MyModel mymodel;
			int num=20000;
			for (MyNode vTemp:model.getGraph().getVertices()){
				if (vTemp.isState()==false){
					mymodel=new MyModel(model);
					mymodel.getGraph().removeVertex(mymodel.getMap().get((vTemp).getId()));
					mymodel.diffuse();
					if (num > mymodel.getHasInfected().size()){
						num = mymodel.getHasInfected().size();
						target=vTemp.getId();
					}	
				}
			}
			return target;	
		}
		// if no return , then return -1
		return -1;
	}	

	
	
	public static List<Integer> greedyTopKDegree(Model model, int removeNum){
		
		List<Integer> immuneList = new ArrayList<Integer>();	
		int removeCount = 0;
		while(removeCount < removeNum){
			int maxDegree = 0;
			MyNode maxDegreeNode = null;
			for(MyNode node : model.getGraph().getVertices()){
				if(node.isState() == false && model.getGraph().degree(node) > maxDegree){
					maxDegree = model.getGraph().degree(node);
					maxDegreeNode = node;
				}
			}
			model.getGraph().removeVertex(maxDegreeNode);
			immuneList.add(maxDegreeNode.getId());
			removeCount++;
		}
		
		return immuneList;
		
		/*
		for(MyNode node : model.getGraph().getVertices()){
			
			//If a node is healthy => Test how does its removal affect the diffusion
			if(node.isState() == false){
				
				Model testModel = null;
				if(model instanceof ICModel) testModel = new ICModel(model);
				else if(model instanceof LTModel) testModel = new LTModel(model);
				else if(model instanceof MyModel) testModel = new MyModel(model);
				
				Integer nodeID = node.getId();
				MyNode toRemove = testModel.getMap().get(nodeID);
				testModel.getGraph().removeVertex(toRemove);
				
				testModel.diffuse();
				int infectedCount = testModel.getHasInfected().size();
				
				node.setMeasure(infectedCount);
				nodeList.add(node);
			}
		}
		
		Collections.sort(nodeList);*/
		
					
		
	}
	
	public static List<Integer> saveFriendsOfPatients(Model model, int removeNum){
		
		List<MyNode> infectedList = new ArrayList<MyNode>();
		for(MyNode node : model.getGraph().getVertices()){
			if(node.isState() == true){
				int degree = model.getGraph().degree(node);
				node.setMeasure(degree);
				infectedList.add(node);
			}
		}
		
		Collections.sort(infectedList);
		
		int removeCount = 0;
		List<Integer> immuneList = new ArrayList<Integer>();
		Graph<MyNode, MyLink> graph = model.getGraph();
		for(int i=infectedList.size()-1; i>=0; i--){			
			MyNode infectedNode = infectedList.get(i);
			if(graph.containsVertex(infectedNode)){
				for( MyNode neighbor : graph.getSuccessors(infectedNode) ){
					if(neighbor.isState() == false){
						graph.removeVertex(neighbor);						
						removeCount++;
						immuneList.add(neighbor.getId());
						if(removeCount >= removeNum) break;
					}
				}
			}
			if(removeCount >= removeNum) break;
		}
						
		return immuneList;
	}
	
	
}
