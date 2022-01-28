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
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame; 
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener{
	public boolean run = true;
	Head head = new Head(80, 380);
	Tail tail = new Tail(40, 380);
	Apple apple = new Apple(520, 380);
	Random rndX = new Random();
	Random rndY = new Random();
	public int x = 0;
	public int y = 100;
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
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.drawRect(0, 100, 640, 640);
		g.drawString("Score: "+score, 250, 80);
		for (int i = 0, x = 40, y = 140; i < 15; i++, x+=40, y+=40) {
			g.drawLine(x, 100, x, 740);
			g.drawLine(0, y, 640, y);
		}
		if (head.getX() == apple.getX()) {
			if (head.getY() == apple.getY()) {
				score++;
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
	    apple.paint(g);
	    head.paint(g);
	    tail.paint(g);
		
	}
	public static void main(String[] arg) {
		Frame f = new Frame();
;	}
	public Frame() {
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
		if (key == 87 && head.direction != 1 && turn && run) {
			head.direction = 3;
			turn = false;
		}
		else if (key == 83 && head.direction != 3 && turn && run) {
			head.direction = 1;
			turn = false;
		}
		else if (key == 68 && head.direction != 2 && turn && run) {
			head.direction = 0;
			turn = false;
		}
		else if (key == 65 && head.direction != 0 && turn && run) {
			head.direction = 2;
			turn = false;
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
		head.move();
		tail.move(head.direction);
		head.collisionCheck();
		if (!head.run) {
			run = false;
		}
	}
}
