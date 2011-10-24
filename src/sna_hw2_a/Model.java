package sna_hw2_a;

import helper.AttackMethod;
import helper.AttackStrategy;
import helper.MyLink;
import helper.MyNode;
import helper.ReadGraph;
import helper.ReadReveal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

public abstract class Model {

	protected DirectedGraph<MyNode,MyLink> graph = new DirectedSparseGraph<MyNode,MyLink>(); 	
	protected Map<Integer, MyNode> map = new TreeMap<Integer, MyNode>();
	protected ArrayList<MyNode> hasInfected= new ArrayList<MyNode>();
	protected ArrayList<MyNode> infectedNodesQueue = new ArrayList<MyNode>();
	
	public Map<Integer, MyNode> getMap() {
		return map;
	}

	public ArrayList<MyNode> getHasInfected() {
		return hasInfected;
	}

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
	
	// get Initial infected nodes  (also initial hasInfected)
	public void getInfectedNodesQueue(String s) throws IOException{
		ReadReveal reveal= new ReadReveal(s);
		ArrayList<Integer> infectedArray=reveal.getReveal();
		for (MyNode vTemp:getGraph().getVertices()){
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
	
	//Return the ratio of the infected
	public double diffuse(){
		while (!infectedNodesQueue.isEmpty()){
			MyNode vTemp=infectedNodesQueue.get(0);
			if (graph==null){
				System.out.println("fuck1");
			}
			if (vTemp==null){
				System.out.println("fuck2");
			}
			if (!graph.containsVertex(vTemp)){
				System.out.println("fuck3");
			}
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
		return (double)((double)this.hasInfected.size()/(double)this.graph.getVertexCount());		
	}
	
	public void init(String graphFileName, String revealFileName)throws IOException{
		getGraph(graphFileName);
		getInfectedNodesQueue(revealFileName);
		changeEdgeWeight();
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
		System.out.println(getGraph().getVertexCount());
		System.out.println(hasInfected.size());
	}
	// default constructor
	public Model(){
		
	}
	// copy constructor
	public Model(Model model){
		graph=new DirectedSparseGraph<MyNode,MyLink>();
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
		//System.out.println(123);
	}
	
	public List<Integer> attack(AttackMethod method, double ratio){
		// num is # removed nodes
		// method is attack method
			
		List<Integer> list = new ArrayList<Integer>();
		
		int num = (int) (ratio * this.graph.getVertexCount());	
		switch (method){
			case NeighborAttack:{					
				for (int i=0;i<num;i++){
					int index=AttackStrategy.neighborAttack(getGraph());
					MyNode node=map.get(index);
					getGraph().removeVertex(node);
				}
				//TODO
				return list;
			}
			
			case TopKDegreeAttack:{
				return AttackStrategy.topKDegree(this, num);
			}
				
			case MyTopKInfluentialAttack:{
				return AttackStrategy.myTopKInfluential(this, num);
			}			
				
			case InfluencialAttack:{
				int index=-1;
				for (int i=0;i<num;i++){
					index=AttackStrategy.influencialAttack(this);
					MyNode node=map.get(index);
					graph.removeVertex(node);
				}
				//TODO
				return list;
			}
				
			case GreedyTopKDegreeAttack:{
				return AttackStrategy.greedyTopKDegree(this, num);
			}			
				
			case FriendsOfPatientsAttack:{
				return AttackStrategy.saveFriendsOfPatients(this, num);
			}			
				
			default:
				return list;
		}
		
	}

	public DirectedGraph<MyNode,MyLink> getGraph() {
		return graph;
	}

	public void setGraph(DirectedGraph<MyNode,MyLink> graph) {
		this.graph = graph;
	}
}
	