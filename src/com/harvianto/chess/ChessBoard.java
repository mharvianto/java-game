package com.harvianto.chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessBoard extends JPanel {

	private final int N = 8;
	private final int SIZE = 500 / 8;
	private final Rectangle BOUND = new Rectangle(0, 0, 500, 500);
	
	private Point hover = null;
	private Point selected = null;
	
	private MouseMotionListener mouseMotionListener = new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			Point p = e.getPoint();
			if(BOUND.contains(p)) {
				p.x /= SIZE;
				p.y /= SIZE;
				hover = (Point)p.clone();
			}else {
				hover = null;
			}
			repaint();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) { }
	};
	
	private MouseListener mouseListener = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) { }
		
		@Override
		public void mousePressed(MouseEvent e) { }
		
		@Override
		public void mouseExited(MouseEvent e) { }
		
		@Override
		public void mouseEntered(MouseEvent e) { }
		
		@Override
		public void mouseClicked(MouseEvent e) {
			Point p = e.getPoint();
			if(BOUND.contains(p)) {
				Point select = new Point(p.x / SIZE, p.y / SIZE);
				if(selected == null) {
					selected = select;
				} else {
					if(selected.equals(select)) {
						selected = null;
					} else {
						//move
					}
				}
				repaint();
			}
		}
	};

	public ChessBoard() {
		addMouseMotionListener(mouseMotionListener);
		addMouseListener(mouseListener);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if ((i + j) % 2 == 0) {
					g.setColor(Color.WHITE);
				}else {
					g.setColor(Color.BLACK);
				}
				if(hover != null && hover.x == i && hover.y == j) {
					g.setColor(Color.GREEN);
				}
				if(selected != null && selected.equals(new Point(i, j))) {
					g.setColor(Color.red);
				}
				g.fillRect(i*SIZE, j*SIZE, SIZE, SIZE);
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 530);
		frame.add(new ChessBoard());
		frame.setVisible(true);
	}

}
