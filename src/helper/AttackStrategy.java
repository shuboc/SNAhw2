package helper;


import edu.uci.ics.jung.graph.DirectedGraph;

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
	
}
