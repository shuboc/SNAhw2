package helper;

public class MyNode {
	public static double threshold=0.3;
	
	private int id;
	private boolean state=false;

	private double loading=0.0;
	
	public MyNode(MyNode node1){
		this.id=node1.id;
		this.state=node1.state;
		this.loading=node1.loading;
	}

	public MyNode(int id){
		this.id=id;
	}
	
	public double getLoading() {
		return loading;
	}

	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}

	public void addLoading(MyLink link){
		loading+=link.getWeight();
	}

	public int getId() {
		return id;
	}
}
