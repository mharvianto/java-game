package com.harvianto.colormatch;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ColorMatch extends JPanel {

	private Color[][] cells = new Color[10][10];
	private final static Color[] COLOR = new Color[] { Color.blue, Color.RED, Color.GREEN, Color.YELLOW, Color.PINK,
			Color.MAGENTA, Color.CYAN, Color.ORANGE };

	private Random rand = new Random();
	private final int N = 10;
	private Dimension cellSize = new Dimension(500, 500);

	private Point selected = null;
	private Point hover = null;
	
	private Rectangle bound = new Rectangle(0, 0, N, N);
	
	private MouseMotionListener mouseMotionListener = new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			Point select = new Point(e.getX() / cellSize.width, e.getY() / cellSize.height);
			if(bound.contains(select)) {
				hover = select;
			} else {
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
			Point select = new Point(e.getX() / cellSize.width, e.getY() / cellSize.height);
			if(bound.contains(select)) {
				if(selected == null) {
					if(cells[select.x][select.y] != null) {
						selected = select;
					}
				} else { 
					if(!select.equals(selected) && cells[select.x][select.y].equals(cells[selected.x][selected.y])) {
						cells[select.x][select.y] = cells[selected.x][selected.y] = null;
					}
					selected = null;
				}
				repaint();
			}
		}
	};

	public ColorMatch() {
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseMotionListener);
		initCells();
		repaint();
	}

	private void initCells() {
		for (int i = 0; i < N / 2; i++) {
			for (int j = 0; j < N; j++) {
				cells[i][j] = cells[i + 5][j] = COLOR[rand.nextInt(COLOR.length)];
			}
		}
		for (int i = 0; i < N * N; i++) {
			int x1 = rand.nextInt(N), y1 = rand.nextInt(N), x2 = rand.nextInt(N), y2 = rand.nextInt(N);
			swap(x1, y1, x2, y2);
		}
	}

	private void swap(int x1, int y1, int x2, int y2) {
		Color temp = cells[x1][y1];
		cells[x1][y1] = cells[x2][y2];
		cells[x2][y2] = temp;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		cellSize.width = getWidth() / N;
		cellSize.height = getHeight() / N;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (cells[i][j] != null) {
					g.setColor(cells[i][j]);
					if(hover != null && hover.equals(new Point(i, j))) {
						g.setColor(g.getColor().darker());
					}
					g.fillRect(i * cellSize.width, j * cellSize.height, cellSize.width, cellSize.height);
				}
			}
		}
		if (selected != null) {
			g.setColor(Color.BLACK);
			g.drawRect(selected.x * cellSize.width, selected.y * cellSize.height, cellSize.width, cellSize.height);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.add(new ColorMatch());
		frame.setVisible(true);
	}

}
