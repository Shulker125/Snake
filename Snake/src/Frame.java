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
	Apple apple = new Apple(520, 380);
	Random rndX = new Random();
	Random rndY = new Random();
	public int score = 0;
	public int countRun = 0; 
	public int lastPosX = apple.getX();
	public int lastPosY = apple.getY();
	public int[][] possibleXY = {{0, 40, 80, 120, 160, 200, 240, 280, 320, 360, 400, 440, 480, 520, 560, 600}, {100, 140, 180, 220, 260, 300, 340, 380, 420, 460, 500, 540, 580, 620, 660, 700}};
	public boolean turn = true;
	public void paint(Graphics g) {
		int randomX = rndX.nextInt(16);
		int randomY = rndY.nextInt(16);
		super.paintComponent(g);
		for (int i = 0; i < head.size(); i++) {
			if (head.get(i).getX() == apple.getX()) {
				if (head.get(i).getY() == apple.getY()) {
					score++;
					head.add(new Head(getBackX(), getBackY()));
					apple.setX(possibleXY[0][randomX]);
					apple.setY(possibleXY[1][randomY]);
					while (apple.getX() == lastPosX && apple.getY() == lastPosY && countRun > 0) {
						randomX = rndX.nextInt(16);
						randomY = rndY.nextInt(16);
						apple.setX(possibleXY[0][randomX]);
						apple.setY(possibleXY[1][randomY]);
						
					}
					lastPosX = apple.getX();
					lastPosY = apple.getY();
					countRun++;
				}
			}
		}
	    apple.paint(g);
	    for(int i = 0; i < head.size(); i++) {
	    	head.get(i).paint(g);
	    }
	    g.setColor(Color.black);
	    g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.drawRect(0, 100, 640, 640);
		g.drawString("Score: "+score, 250, 80);
		for (int i = 0, x = 40, y = 140; i < 15; i++, x+=40, y+=40) {
			g.drawLine(x, 100, x, 740);
			g.drawLine(0, y, 640, y);
		}
	}
	public static void main(String[] arg) {
		Frame f = new Frame();
		
;	}
	public Frame() {
		head.add(new Head(80, 380));
		head.add(new Head(40, 380));
		JFrame f = new JFrame("Snake");
		f.setSize(new Dimension(659, 788));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		Timer t = new Timer(150, this);
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
			if (key == 87 && h.direction != 1 && turn && run) {
				h.direction = 3;
				turn = false;
			}
			else if (key == 83 && h.direction != 3 && turn && run) {
				h.direction = 1;
				turn = false;
			}
			else if (key == 68 && h.direction != 2 && turn && run) {
				h.direction = 0;
				turn = false;
			}
			else if (key == 65 && h.direction != 0 && turn && run) {
				h.direction = 2;
				turn = false;
			}
		}
		
	} 
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == 87) {
			turn = true;
		}
		else if (key == 83) {
			turn = true;
		}
		else if (key == 68) {
			turn = true;
		}
		else if (key == 65) {
			turn = true;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		head.get(head.size()-1).setX(getFrontX());
		head.get(head.size()-1).setY(getFrontY());
		head.add(0, head.remove(head.size()-1));
		for (Head h : head) {
			h.collisionCheck();
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
	//need to be coding differently, doesn't get the last tail direction properly
	public int getBackX() {
		int end = head.size()-1;
		if (head.get(end).direction == 0) {
			return head.get(end).getX()-40;
		}
		else if (head.get(end).direction == 2) {
			return head.get(end).getX()+40;
		}
		return head.get(end).getX();
	}
	public int getBackY() {
		int end = head.size()-1;
		if (head.get(end).direction == 1) {
			return head.get(end).getY()-40;
		}
		else if (head.get(end).direction == 3) {
			return head.get(end).getY()+40;
		}
		return head.get(end).getY();
	}
}
