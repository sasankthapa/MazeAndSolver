import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class Maze extends JPanel implements Runnable{
	
	public Node[][] nodes;
	public int size;
	
	public static boolean solved=false;
	
	public Maze(int n) {
		nodes=new Node[n][n];
		size=n;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				nodes[i][j]=new Node();
			}
		}
	}
	
	public void solveMaze(int x, int y, int endX, int endY ) {
		
		if(x==endX && y==endY) {
			solved=true;
			return;
		}
		if(nodes[x][y].color==0) {
			nodes[x][y].changeColor();
			if(!nodes[x][y].downWall) {
				solveMaze(x, y+1, endX, endY);
			}
			if(solved) return;
			if(!nodes[x][y].upWall) {
				solveMaze(x, y-1, endX, endY);
			}
			if(solved) return;
			if(!nodes[x][y].leftWall) {
				solveMaze(x-1, y, endX, endY);
			}if(solved) return;
			if(!nodes[x][y].rightWall) {
				solveMaze(x+1, y, endX, endY);
			}if(solved) return;
			nodes[x][y].changeColor();
		}
	}
	
	Random rnd=new Random();
	
	private void breakWalls(int x, int y) {
		nodes[x][y].visited=true;
		int wall=rnd.nextInt(4);
		switch (wall) {
		case 0:
			if(destroyUpWall(x, y)) {
				breakWalls(x, y-1);
			}
			break;
		case 1:
			if(destroyRightWall(x, y)) {
				breakWalls(x+1, y);
			}
			break;
		case 2:
			if(destroyDownWall(x, y)) {
				breakWalls(x, y+1);
			}
			break;
		case 3:
			if(destroyLeftWall(x, y)) {
				breakWalls(x-1, y);
			}
			break;
		}
		if(x!=0 && !nodes[x-1][y].visited) {
			destroyLeftWall(x, y);
			breakWalls(x-1, y);
		}
		if(x!=size-1 && !nodes[x+1][y].visited) {
			destroyRightWall(x, y);
			breakWalls(x+1, y);
		}
		if(y!=0 && !nodes[x][y-1].visited) {
			destroyUpWall(x, y);
			breakWalls(x, y-1);
		}
		if(y!=size-1 && !nodes[x][y+1].visited) {
			destroyDownWall(x, y);
			breakWalls(x, y+1);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.white);
		for(int y=0;y<size;y++) {
			for(int x=0;x<size;x++) {
				Node currentNode=nodes[x][y];
				switch(currentNode.color) {
				case 0:
					g.setColor(Color.BLUE);
					break;
				case 1:
					g.setColor(Color.RED);
					break;
				case 2:
					g.setColor(Color.CYAN);
					break;
				}
				if((x==0&& y==0)||(x==size-1 && y==size-1)) {
					g.setColor(Color.green);
				}
			    g.drawOval(50+x*50, 50+y*50, 10, 10);
			    
			    g.setColor(Color.BLACK);
				if(currentNode.downWall) {
					g.drawLine(25+x*50, 75+y*50, 75+x*50, 75+y*50);
				}
				if(currentNode.leftWall) {
					g.drawLine(25+x*50, 25+y*50, 25+x*50, 75+y*50);
				}
				if(currentNode.rightWall) {
					g.drawLine(75+x*50, 25+y*50, 75+x*50, 75+y*50);
				}
				if(currentNode.upWall) {
					g.drawLine(25+x*50, 25+y*50, 75+x*50, 25+y*50);
				}
			}
		}
	}
	
	public boolean destroyRightWall(int x, int y) {
		if(x==size-1) {
			System.out.println("Edge, cannot destroy");
			breakWalls(x, y);
			return false;
		}
		if(nodes[x+1][y].visited) return false;
		nodes[x][y].rightWall=false;
		nodes[x+1][y].leftWall=false;
		return true;
	}
	
	public boolean destroyLeftWall(int x, int y) {
		if(x==0) {
			System.out.println("Edge, cannot destroy");
			breakWalls(x, y);
			return false;
		}
		if(nodes[x-1][y].visited) return false;
		nodes[x][y].leftWall=false;
		nodes[x-1][y].rightWall=false;
		return true;
	}
	
	public boolean destroyUpWall(int x, int y) {
		if(y==0) {
			System.out.println("Edge, cannot destroy");
			breakWalls(x, y);
			return false;
		}
		if(nodes[x][y-1].visited) return false;
		nodes[x][y].upWall=false;
		nodes[x][y-1].downWall=false;
		return true;
	}
	
	public boolean destroyDownWall(int x, int y) {
		if(y==size-1) {
			System.out.println("Edge, cannot destroy");
			breakWalls(x, y);
			return false;
		}
		if(nodes[x][y+1].visited) return false;
		nodes[x][y].downWall=false;
		nodes[x][y+1].upWall=false;
		return true; 
	}

	@Override
	public void run() {
		breakWalls(0, 0);
		solveMaze(0,0,9,9);
	}
}
