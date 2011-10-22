package sna_hw2_b;

import helper.AttackMethod;

import java.io.IOException;

import sna_hw2_a.ICModel;
import sna_hw2_a.LTModel;
import sna_hw2_a.Model;
import sna_hw2_a.MyModel;


public class AttackTestMain {

	public static void main(String[] args) {
		
		Model   icmodel0 = new ICModel();
		LTModel ltmodel0 = new LTModel();
		MyModel mymodel0 = new MyModel();
		
		Model   icmodel1 = new ICModel();
		LTModel ltmodel1 = new LTModel();
		MyModel mymodel1 = new MyModel();
		
		try {
			
			icmodel0.start("ca-GrQc.txt","GrQc_reveal.txt");
			ltmodel0.start("ca-HepTh.txt","HepTh_reveal.txt");
			mymodel0.start("ca-HepPh.txt","HepPh_reveal.txt");
			
			icmodel0.reportOutcome();
			ltmodel0.reportOutcome();
			mymodel0.reportOutcome();
			
			icmodel1.init("ca-GrQc.txt","GrQc_reveal.txt");
			ltmodel1.init("ca-HepTh.txt","HepTh_reveal.txt");
			mymodel1.init("ca-HepPh.txt","HepPh_reveal.txt");
			
			icmodel1.attack(AttackMethod.MyTopKInfluentialAttack);
			ltmodel1.attack(AttackMethod.MyTopKInfluentialAttack);
			mymodel1.attack(AttackMethod.MyTopKInfluentialAttack);
			
			icmodel1.diffuse();
			ltmodel1.diffuse();
			mymodel1.diffuse();
			
			icmodel1.reportOutcome();
			ltmodel1.reportOutcome();
			mymodel1.reportOutcome();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
