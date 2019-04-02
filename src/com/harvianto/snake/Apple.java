package com.harvianto.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Apple {
	private Point position;

	public final static Color[] COLOR = new Color[] { Color.RED, Color.GREEN, Color.ORANGE, };
	private Color color;

	public Apple(Dimension size) {
		Random rand = new Random();
		this.position = new Point(rand.nextInt(size.width), rand.nextInt(size.height));
		// this.color = COLOR[rand.nextInt(COLOR.length)];
		this.color = Color.RED;
	}

	public boolean equals(Point p) {
		return position.equals(p);
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(position.x * SnakePanel.SCALE_RATIO, position.y * SnakePanel.SCALE_RATIO, SnakePanel.SCALE_RATIO,
				SnakePanel.SCALE_RATIO);
		g.setColor(c);
	}
}
