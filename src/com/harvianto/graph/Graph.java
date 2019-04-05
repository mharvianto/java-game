package com.harvianto.graph;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Graph extends JPanel {

	private Point2D.Double p = null;
	private Point2D.Double speed = null;
	private Rectangle bound = new Rectangle(0, 0, 500, 500);
	private final double GRAVITY = 10;
	private final double FPS = 30.0;

	private Thread render = new Thread(() -> {
		while (true) {
			if (p != null) {
				if (bound.contains(p)) {
					speed.y += GRAVITY / FPS;
					p.x += speed.x;
					p.y += speed.y;
				} else {
					p = null;
				}
			}
			repaint();
			try {
				Thread.sleep((int) (1000 / FPS));
			} catch (InterruptedException e) {
			}
		}
	});

	private KeyListener keyListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (p == null) {
					p = new Point2D.Double(0, 300);
					speed = new Point2D.Double(5.0, -5.0);
				} else {
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
		bound.width = getWidth();
		bound.height = getHeight();
		if (p != null) {
			g.fillOval((int) p.x, (int) p.y, 20, 20);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.add(new Graph());
		frame.setVisible(true);
	}

}
