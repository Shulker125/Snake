import java.awt.Graphics;

public class Tail {
	private int x, y;
	private int v = 40;
	public int direction = 0;
	public Tail(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void paint(Graphics g) {
		g.fillRect(x, y, 40, 40);
	}
	public void move(int direction) {
		if (direction == 0 && direction != 2 && Head.run) {
			x = Head.getX()-80;
			y = Head.getY();
			this.direction = 0;
			x += v;
		}
		else if (direction == 2 && direction !=0 && Head.run) {
			x = Head.getX()+80;
			y = Head.getY();
			this.direction = 0;
			x -= v;
		}
		else if (direction == 1 && direction != 3 && Head.run) {
			y = Head.getY()-80;
			x = Head.getX();
			this.direction = 0;
			y += v;
		}
		else if (direction == 3 && direction != 1 && Head.run) {
			y = Head.getY()+80;
			x = Head.getX();
			this.direction = 0;
			y -= v;
		}
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getDir() {
		return direction;
	}
	
}
