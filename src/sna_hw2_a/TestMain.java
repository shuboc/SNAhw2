package sna_hw2_a;

import java.io.IOException;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub
		ICModel icmodel=new ICModel();
		LTModel ltmodel=new LTModel();
		MyModel mymodel=new MyModel();
		
		icmodel.start("ca-GrQc.txt","GrQc_reveal.txt");
		ltmodel.start("ca-HepTh.txt","HepTh_reveal.txt");
		mymodel.start("ca-HepPh.txt","HepPh_reveal.txt");
		
		icmodel.output("ic_ca-GrQc.txt");
		ltmodel.output("lt_ca-HepTh.txt");
		mymodel.output("my_ca-HepPh.txt");
		
		icmodel.reportOutcome();
		ltmodel.reportOutcome();
		mymodel.reportOutcome();
		
		
	}

}
