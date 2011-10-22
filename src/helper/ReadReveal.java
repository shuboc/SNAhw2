package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadReveal {
	private Scanner reader;
	public ReadReveal(String s) throws IOException{
			reader = new Scanner(new BufferedReader(new FileReader(s)));
	}
	public ArrayList<Integer> getReveal(){
		ArrayList<Integer> array=new ArrayList<Integer>();
		while(reader.hasNextInt()){
			array.add(reader.nextInt());
		}
		return array;
	}
}
