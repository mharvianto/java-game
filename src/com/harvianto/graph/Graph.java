package com.harvianto.graph;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graph extends JPanel {
	
	private Point2D.Double p = null;
	private Point2D.Double speed = null; 
	private Rectangle bound = new Rectangle(0, 0, 500, 500);
	private final double GRAVITY = 10;
	private final double FPS = 30.0;
	
	private Thread render = new Thread(()->{
		while(true) {
			if(p != null && bound.contains(p)) {
				speed.y += GRAVITY / FPS;
				p.x += speed.x;
				p.y += speed.y;
			}
			repaint();
			try {
				Thread.sleep((int)(1000/FPS));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	});
	
	private KeyListener keyListener = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) { }
		
		@Override
		public void keyReleased(KeyEvent e) { }
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				if(p == null) {
					p = new Point2D.Double(0, 300);
					speed = new Point2D.Double(7.0, -7.0);
				}else {
					speed.y -= 5.0;
				}
			}
		}
	};
	
	public Graph() {
		addKeyListener(keyListener);
		setFocusable(true);
		render.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(p != null) {
			g.fillOval((int)p.x, (int)p.y, 10, 10);
		}
//		for (int i = -250; i < 250; i+=5) {
//			Point s = new Point(i-5, (i-5)*(i-5)/200);
//			Point d = new Point(i, i*i/200);
//			g.drawLine(s.x+250, s.y, d.x+250, d.y);
//			s = d;
//		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.add(new Graph());
		frame.setVisible(true);
	}

}
