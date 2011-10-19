package sna_hw2_a;

import helper.*;
import java.io.*;
import java.util.*;
import edu.uci.ics.jung.graph.*;

public abstract class Model {

	protected Map<Integer, MyNode> map = new TreeMap<Integer, MyNode>();
	protected DirectedGraph<MyNode,MyLink> graph = new DirectedSparseGraph<MyNode,MyLink>(); 
	protected ArrayList<MyNode> hasInfected= new ArrayList<MyNode>();
	protected ArrayList<MyNode> infectedNodesQueue = new ArrayList<MyNode>();
	
	// get Initial Graph
	public void getGraph(String s)throws IOException{
		ReadGraph reader = new ReadGraph(s);
		ArrayList<Integer[]> edges =reader.getEdges();
		for (Integer[] temp: edges){
			MyNode node0 = map.get(temp[0]);
			if(node0 == null){ 
				node0 = new MyNode(temp[0]);
				map.put(temp[0], node0);
			}
			MyNode node1 = map.get(temp[1]);
			if(node1 == null){ 
				node1 = new MyNode(temp[1]);
				map.put(temp[1], node1);
			}
			graph.addEdge(new MyLink(), node0, node1);
			//System.out.println(temp[0]+"   "+temp[1]);
		}
	}
	
	// get Initial infected nodes
	public void getInfectedNodesQueue(String s) throws IOException{
		ReadReveal reveal= new ReadReveal(s);
		ArrayList<Integer> infectedArray=reveal.getReveal();
		for (MyNode vTemp:graph.getVertices()){
			if (infectedArray.contains(vTemp.getId())){
				vTemp.setState(true);
				infectedNodesQueue.add(vTemp);
				hasInfected.add(vTemp);
			}
		}
	}
	
	// the way to diffuse (main difference between modes) 
	public abstract void tryInfect(MyNode node1,MyNode node2);
	public abstract void changeEdgeWeight();
	
	public void diffuse(){
		while (!infectedNodesQueue.isEmpty()){
			MyNode vTemp=infectedNodesQueue.get(0);
			for (MyNode neighborOut:graph.getSuccessors(vTemp)){
				if (neighborOut.isState()==false){
					tryInfect(vTemp,neighborOut);
					// assume neighborOut's state is changed
					if (neighborOut.isState()==true){
						infectedNodesQueue.add(neighborOut);
						hasInfected.add(neighborOut);
					}
				}
			}
			infectedNodesQueue.remove(vTemp);
		}
	}
	
	public void start(String graphFileName, String revealFileName)throws IOException{
		getGraph(graphFileName);
		getInfectedNodesQueue(revealFileName);
		changeEdgeWeight();
		diffuse();		
	}
	public void output(String outFileName)throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
		for (MyNode node:hasInfected){
			writer.write(new Integer(node.getId()).toString());
			writer.newLine();
		}
		if (writer!=null){
			writer.close();
		}
	}
	public void reportOutcome(){
		System.out.println("final outcome:");
		System.out.println(graph.getVertexCount());
		System.out.println(hasInfected.size());
	}
	// default constructor
	public Model(){
		
	}
	// copy constructor
	public Model(Model model){
		graph= new DirectedSparseGraph<MyNode,MyLink>();
		map=new TreeMap<Integer,MyNode>();
		for (MyLink link:model.graph.getEdges()){
			MyNode node1=map.get(model.graph.getSource(link).getId());
			MyNode node2=map.get(model.graph.getDest(link).getId());
			if (node1==null){
				node1=new MyNode(model.graph.getSource(link));
				map.put(model.graph.getSource(link).getId(), node1);
			}
			if (node2==null){
				node2=new MyNode(model.graph.getDest(link));
				map.put(model.graph.getDest(link).getId(), node2);
			}
			graph.addEdge(new MyLink(link.getWeight()), node1, node2);
		}
		for (MyNode vTemp:model.infectedNodesQueue){
			MyNode node=map.get(vTemp.getId());
			infectedNodesQueue.add(node);
			hasInfected.add(node);
		}	
	}
	
	public void Attack(int num,AttackMethod method){
		// num is # removed nodes
		// method is attack method
		for (int i=0;i<num;i++){
			switch (method){
				case NeighborAttack:{
					int index=AttackStrategy.neighborAttack(graph);
					MyNode node=map.get(index);
					graph.removeVertex(node);
				}
				// other methods
			}
		
		}
	}
}
	