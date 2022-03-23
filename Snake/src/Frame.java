import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame; 
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener{
	public boolean run = true;
	ArrayList<Head> head = new ArrayList<Head>();
	JFrame f = new JFrame("Snake");
	Apple apple = new Apple(520, 380);
	Random rndX = new Random();
	Random rndY = new Random();
	Timer t = new Timer(150, this);
	Point p = MouseInfo.getPointerInfo().getLocation();
	public int transparent = 0;
	Color c2 = new Color(0, 0, 0, transparent);
	public int score = 0;
	public int countRun = 0; 
	public int highscore = 0;
	public int lastPosX = apple.getX();
	public int lastPosY = apple.getY();
	public int[][] possibleXY = {{0, 40, 80, 120, 160, 200, 240, 280, 320, 360, 400, 440, 480, 520, 560, 600}, {100, 140, 180, 220, 260, 300, 340, 380, 420, 460, 500, 540, 580, 620, 660, 700}};
	public boolean turn = true;
	public boolean win = false;
	public void paint(Graphics g) {
		int randomX = rndX.nextInt(16);
		int randomY = rndY.nextInt(16);
		int winX = f.getX(), winY = f.getY();
		p.x -= winX;
		p.y -= winY;
		super.paintComponent(g);
		collectApple(randomX, randomY);
	    apple.paint(g);
	    for(int i = head.size()-1; i >= 0; i--) {
	    	if (i == 0) {
	    		head.get(i).paint(g, Color.black);
	    	}
	    	else {
	    		head.get(i).paint(g, Color.green);
	    	}
	    }
	    if (!run) {
	    	gameOverDisplay(g, new Color(0, 0, 0, transparent));
	    	if (transparent < 255) {
				transparent += 15;
			}
	    }
	    paintWin(g);
	    g.setColor(Color.black);
	    g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.drawRect(0, 100, 640, 640);
		g.drawString("Score: "+score, 250, 85);
		if (highscore > 0) {
			g.drawString("Highscore: "+highscore, 210, 40);
		}
		for (int i = 0, x = 40, y = 140; i < 15; i++, x+=40, y+=40) {
			g.drawLine(x, 100, x, 740);
			g.drawLine(0, y, 640, y);
		}
		if (!run || win) {
			if (p.getX() >= 200 && p.getX() <= 440 && p.getY() >= 370 && p.getY() <= 410){
				gameOverDisplay(g, new Color(200, 34, 22, transparent));
			}
		}
		p = MouseInfo.getPointerInfo().getLocation();
	}
	public static void main(String[] arg) {
		Frame f = new Frame();
		
	}
	public Frame() {
		head.add(new Head(80, 380));
		head.add(new Head(40, 380));
		f.setSize(new Dimension(659, 788));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		t.setDelay(150);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		// w = 87, a = 65, s = 83, d = 68
		for (Head h : head) {
			if ((key == 87 || key == 38) && h.direction != 1 && turn && run) {
				h.direction = 3;
				turn = false;
			}
			else if ((key == 83 || key == 40) && h.direction != 3 && turn && run) {
				h.direction = 1;
				turn = false;
			}
			else if ((key == 68 || key == 39) && h.direction != 2 && turn && run) {
				h.direction = 0;
				turn = false;
			}
			else if ((key == 65 || key == 37) && h.direction != 0 && turn && run) {
				h.direction = 2;
				turn = false;
			}
		}
		
	} 
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == 1) {
			System.out.println(e.getX() + "," + e.getY()); //320, 410
		}
		if (e.getButton() == 1 && !run || win) {
			if (e.getX() >= 200 && e.getX() <= 440 && e.getY() >= 370 && e.getY() <= 410){
				reset();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
		
		if (run && !win) {
			head.get(head.size()-1).setX(getFrontX());
			head.get(head.size()-1).setY(getFrontY());
			head.add(0, head.remove(head.size()-1));
			turn = true;
		}
		for (Head h : head) {
			h.collisionCheck();
			checkCollisionTail();
			setFalse();
			if (!h.run) {
				run = false;
			}
		}
		
	}
	public int getFrontX() {
		if (head.get(0).direction == 0) {
			return head.get(0).getX()+40;
		}
		else if (head.get(0).direction == 2){
			return head.get(0).getX()-40;
		}
		return head.get(0).getX();
	}
	public int getFrontY() {
		if (head.get(0).direction == 1) {
			return head.get(0).getY()+40;
		}
		else if (head.get(0).direction == 3){
			return head.get(0).getY()-40;
		}
		return head.get(0).getY();
	}
	public void checkCollisionTail() {
		for (int i = 1; i < head.size(); i++) {
			if (head.get(i).getX() == head.get(0).getX()) {
				if (head.get(i).getY() == head.get(0).getY()) {
					head.get(0).run = false;
				}
			}
		}
	}
	public void setFalse() {
		for (Head h : head) {
			if (!h.run) {
				h.run = false;
			}
		}
	}
	public void reset() {
		head.clear();
		if (score > highscore) {
			highscore = score;
		}
		score = 0;
		head.add(new Head(80, 380));
		head.add(new Head(40, 380));
		for (Head h : head) {
			h.run = true;
			h.direction = 0;
		}
		transparent = 0;
		apple.setX(520);
		apple.setY(380);
		t.setDelay(150);
		run = true;
		win = false;
	}
	public void moveApple(int randX, int randY) {
		while ((isOnSnake() || isInSamePlace()) && !win){
			randX = rndX.nextInt(16);
			randY = rndY.nextInt(16);
			apple.setX(possibleXY[0][randX]);
			apple.setY(possibleXY[1][randY]);
		}
		
	}
	public boolean isOnSnake() {
		for (int i = 1; i < head.size(); i++) {
			if (head.get(i).getX() == apple.getX() && head.get(i).getY() == apple.getY()) {
				return true;
			}
		}
		return false;
		
	}
	public boolean isInSamePlace() {
		if (apple.getX() == lastPosX && apple.getY() == lastPosY) {
			return true;
		}
		return false;
	}
	public void gameOverDisplay(Graphics g, Color newColor) {
		t.setDelay(25);
		Color c = new Color(150, 150, 150, transparent);
		c2 = newColor;
		g.setColor(c);
		g.fillRect(200, 340, 240, 80);
		g.setColor(c2);
    	g.setFont(new Font("Arial", Font.PLAIN, 40));
    	g.drawString("Game Over!", 213, 375);
		
	}
	public void collectApple(int x, int y) {
		if (head.get(1).getX() == apple.getX()) {
			if (head.get(1).getY() == apple.getY()) {
				score++;
				head.add(new Head(head.get(head.size()-1).getX(), head.get(head.size()-1).getY()));
				moveApple(x, y);
				lastPosX = apple.getX();
				lastPosY = apple.getY();
			}
		}
	}
	public void paintWin(Graphics g) {
		if (head.size() >= 257 && run) {
	    	g.setColor(Color.black);
	    	g.setFont(new Font("Arial", Font.PLAIN, 40));
	    	g.drawString("You Win!", 220, 375);
	    	win = true;
	    }
	}
}
