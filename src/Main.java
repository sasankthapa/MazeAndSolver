import java.awt.Color;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		Maze maze=new Maze(20);
		JFrame f = new JFrame("Maze");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  //	f.add(M);
		
	  	f.add(maze);
	  	f.setSize(1000,1000);
	 	f.setVisible(true);
	 	maze.run();
	}
}
