import java.awt.Color;
import java.awt.Graphics;
// Directions - R:0 D:1 L:2 U:3
public class Head {
	private int x, y;
	private int v = 40;
	public static boolean run = true;
	public static int direction = 0;
	Color newColor = new Color(0, 0, 100);
	public Head(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void paint(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x,  y, 40, 40);
		
	}
	public void move() {
		if (x <= 600 && x >= 0 && direction == 0 && direction != 2) {
			x += v;
		}
		else if (x <= 600 && x >= 0 && direction == 2 && direction !=0) {
			x -= v;
		}
		else if (y >= 100 && y<=700 && direction == 1 && direction != 3) {
			y += v;
		}
		else if (y >= 100 && y <= 700 && direction == 3 && direction != 1) {
			y -= v;
		}
	}
	public void collisionCheck() {
		if (direction == 0 && x == 640) {
			v = 0;
			x = 600;
			run = false;
		}
		else if (direction == 1 && y == 740) {
			v = 0;
			y = 700;
			run = false;
		}
		else if (direction == 2 && x == -40) {
			v = 0;
			x = 0;
			run = false;
		}
		else if (direction == 3 && y == 60) {
			v = 0;
			y = 100;
			run = false;
		}
	}
}
