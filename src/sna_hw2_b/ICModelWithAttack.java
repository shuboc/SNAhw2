package sna_hw2_b;

import java.io.IOException;
import sna_hw2_a.ICModel;

public class ICModelWithAttack extends ICModel {
	public void start(String graphFileName, String revealFileName)throws IOException{
		getGraph(graphFileName);
		getInfectedNodesQueue(revealFileName);
		//int num=0;
		//int target;
		//changeEdgeWeight();
		//for (int i=1;i<=5;i++)
		//while (num<52*i){
		//	target=AttackStrategy.neighborAttack(graph);
		//	graph.removeVertex(map.get(target));
		//}
	}
}
