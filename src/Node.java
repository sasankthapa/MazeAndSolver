
public class Node {
	
	public boolean leftWall;
	public boolean rightWall;
	public boolean upWall;
	public boolean downWall;
	
	public int color;
	
	public boolean visited;
	
	public Node() {
		color=0;
		visited=false;
		leftWall=true;
		rightWall=true;
		upWall=true;
		downWall=true;
	}
	
	public void changeColor() {
		color++;
	}
}
