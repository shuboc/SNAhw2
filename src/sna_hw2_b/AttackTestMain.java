package sna_hw2_b;

import helper.AttackMethod;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import sna_hw2_a.ICModel;
import sna_hw2_a.LTModel;
import sna_hw2_a.Model;
import sna_hw2_a.MyModel;


public class AttackTestMain {

	public static void main(String[] args) {
		
		try {
		
			BufferedWriter ICWriter = new BufferedWriter(new FileWriter("IC.txt"));
			BufferedWriter LTWriter = new BufferedWriter(new FileWriter("LT.txt"));
			BufferedWriter MYWriter = new BufferedWriter(new FileWriter("MY.txt"));
		
			for(double ratio = 0.01; ratio <=0.05; ratio += 0.01){
		
				Model   icmodel1 = new ICModel();
				LTModel ltmodel1 = new LTModel();
				MyModel mymodel1 = new MyModel();
											
				BufferedWriter ICBlockWriter = new BufferedWriter(new FileWriter("ic_" + String.valueOf(ratio) + "_ca-GrQc.txt"));
				BufferedWriter LTBlockWriter = new BufferedWriter(new FileWriter("lt_" + String.valueOf(ratio) + "_ca-HepTh.txt"));
				BufferedWriter MYBlockWriter = new BufferedWriter(new FileWriter("my_" + String.valueOf(ratio) + "_ca-HepPh.txt"));
				
				icmodel1.init("ca-GrQc.txt","GrQc_reveal.txt");
				ltmodel1.init("ca-HepTh.txt","HepTh_reveal.txt");
				mymodel1.init("ca-HepPh.txt","HepPh_reveal.txt");
				
				List<Integer> ICBlockList = icmodel1.attack(AttackMethod.GreedyTopKDegreeAttack, ratio);
				List<Integer> LTBlockList = ltmodel1.attack(AttackMethod.GreedyTopKDegreeAttack, ratio);
				List<Integer> MYBlockList = mymodel1.attack(AttackMethod.GreedyTopKDegreeAttack, ratio);
				
				for(Integer i : ICBlockList){
					ICBlockWriter.write(String.valueOf(i));
					ICBlockWriter.newLine();
				}
				for(Integer i : LTBlockList){
					LTBlockWriter.write(String.valueOf(i));
					LTBlockWriter.newLine();
				}
				for(Integer i : MYBlockList){
					MYBlockWriter.write(String.valueOf(i));
					MYBlockWriter.newLine();
				}
				
				ICBlockWriter.close();
				LTBlockWriter.close();
				MYBlockWriter.close();
				
				double icInfecedSize = icmodel1.diffuse();
				double ltInfecedSize = ltmodel1.diffuse();
				double myInfecedSize = mymodel1.diffuse();
				
				ICWriter.write(String.valueOf(ratio));
				ICWriter.write(" ");
				ICWriter.write(String.valueOf(icInfecedSize));
				ICWriter.newLine();
				
				LTWriter.write(String.valueOf(ratio));
				LTWriter.write(" ");
				LTWriter.write(String.valueOf(ltInfecedSize));
				LTWriter.newLine();
				
				MYWriter.write(String.valueOf(ratio));
				MYWriter.write(" ");
				MYWriter.write(String.valueOf(myInfecedSize));
				MYWriter.newLine();
				 
			}
			
			ICWriter.close();
			LTWriter.close();
			MYWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
