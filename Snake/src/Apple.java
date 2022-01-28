import java.awt.Color;
import java.awt.Graphics;

public class Apple {
	private static int x, y;
	Color newColor = new Color(255, 0, 0);
	public Apple(int x, int y){
		this.x = x;
		this.y = y;
	}
	public static int getX() {
		return x;
	}
	public static int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void paint(Graphics g) {
		g.setColor(newColor);
		g.fillOval(x, y, 40, 40);
	}
}
