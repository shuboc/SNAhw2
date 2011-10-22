package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadGraph {
	Scanner scan = null;
	void getScan(String s) throws IOException{
		scan =new Scanner(new BufferedReader(new FileReader(s)));
	}
	public ReadGraph(String s) throws IOException{
		getScan(s);
	}
	
	public boolean hasNextPair(){
		if (scan.hasNextInt()){
			return true;
		}
		else return false;
	}
	public Integer[] nextPair(){
		Integer[] pair = new Integer[2];
		pair[0]=scan.nextInt();
		pair[1]=scan.nextInt();
		return pair;
	}
	public ArrayList<Integer[]> getEdges(){
		ArrayList<Integer[]> array = new ArrayList<Integer[]>();
		while (hasNextPair()){
			array.add(nextPair());
		}
		return array;
	}
}
