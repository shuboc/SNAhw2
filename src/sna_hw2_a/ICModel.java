package sna_hw2_a;

import helper.MyNode;

import java.util.Random;


public class ICModel extends Model{

	public ICModel(){}
	public ICModel(Model model){
		super(model);
	}
	
	private Random rand= new Random();
	
	public void changeEdgeWeight(){};
	public void tryInfect(MyNode node1, MyNode node2){
		// set p of ICModel =0.8;
		double p=0.8;
		if (rand.nextDouble()<p){
			node2.setState(true);
		}
	}
}
