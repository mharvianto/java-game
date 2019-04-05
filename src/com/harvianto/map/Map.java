package com.harvianto.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Map extends JPanel {
	private final int PATH = 0;
	private final int WALL = 1;

	private final Color[] COLORS = new Color[] { Color.WHITE, Color.GRAY };

	private final int SIZE = 30;
	private ArrayList<ArrayList<Integer>> maps = new ArrayList<ArrayList<Integer>>();

	private Point player = new Point(1, 1);
	private final Dimension WINDOW = new Dimension(16, 16);
	private Point camera = new Point(0, 0);

	private KeyListener KeyListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_A && getCurrentLeft() == PATH) {
				if (camera.x + 2 >= player.x - 1 && camera.x > 0) {
					camera.x--;
				}
				player.x--;
			} else if (e.getKeyCode() == KeyEvent.VK_W && getCurrentUp() == PATH) {
				if (camera.y + 2 >= player.y - 1 && camera.y > 0) {
					camera.y--;
				}
				player.y--;
			} else if (e.getKeyCode() == KeyEvent.VK_D && getCurrentRight() == PATH) {
				if (camera.x + WINDOW.width - 3 <= player.x + 1 && camera.x + WINDOW.width < maps.size()) {
					camera.x++;
				}
				player.x++;
			} else if (e.getKeyCode() == KeyEvent.VK_S && getCurrentDown() == PATH) {
				if (camera.y + WINDOW.height - 3 <= player.y + 1 && camera.y + WINDOW.height < maps.get(0).size()) {
					camera.y++;
				}
				player.y++;
			}
			repaint();
		}
	};
	
	private int getCurrentLeft() {
		return maps.get(player.x - 1).get(player.y);
	}
	
	private int getCurrentUp() {
		return maps.get(player.x).get(player.y - 1);
	}
	
	private int getCurrentRight() {
		return maps.get(player.x + 1).get(player.y);
	}
	
	private int getCurrentDown() {
		return maps.get(player.x).get(player.y + 1);
	}

	public Map() {
		init();
		addKeyListener(KeyListener);
		setFocusable(true);
		setVisible(true);
	}

	private void init() {
		try {
			Scanner f = new Scanner(new File("map.txt"));
			while(f.hasNextLine()) {
				String[] col = f.nextLine().split(",");
				ArrayList<Integer> arr = new ArrayList<Integer>();
				for (int j = 0; j < col.length; j++) {
					arr.add(Integer.parseInt(col[j]));
				}
				maps.add(arr);
			}
			f.close();
		} catch (FileNotFoundException e) { }
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// background
		for (int i = camera.x; i < camera.x + WINDOW.width; i++) {
			for (int j = camera.y; j < camera.y + WINDOW.height; j++) {
				g.setColor(COLORS[maps.get(i).get(j)]);
				g.fillRect((i - camera.x) * SIZE, (j - camera.y) * SIZE, SIZE, SIZE);
			}
		}
		// character
		g.setColor(Color.BLUE);
		g.fillOval((player.x - camera.x) * SIZE, (player.y - camera.y) * SIZE, SIZE, SIZE);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(480, 502);
		frame.add(new Map());
		frame.setVisible(true);
	}

}
