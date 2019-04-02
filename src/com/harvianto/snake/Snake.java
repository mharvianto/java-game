package com.harvianto.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Snake {
	private ConcurrentLinkedQueue<Point> position = new ConcurrentLinkedQueue<Point>();
	private Point tail = null;

	private Direction direction = Direction.RIGHT;

	private Color color;

	private SnakePanel panel = null;

	public Snake(SnakePanel panel) {
		this(panel, new Point[] { new Point(5, 6), new Point(6, 6), new Point(6, 7) }, Color.BLACK);
	}

	public Snake(SnakePanel panel, Point point, Color color) {
		this.panel = panel;
		this.color = color;
		Point[] p = new Point[3];
		for (int i = 0; i < 3; i++) {
			Point node = (Point) point.clone();
			node.y += i;
			tail = p[i] = node;
		}
		this.position = new ConcurrentLinkedQueue<Point>(Arrays.asList(p));
	}

	public Snake(SnakePanel panel, Point[] position, Color color) {
		this.panel = panel;
		this.color = color;
		for (Point p : position) {
			this.position.add(tail = p);
		}
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void move() {
		Point node = (Point) tail.clone();
		switch (direction) {
		case LEFT:
			node.x += panel.getWidthScale() - 1;
			break;
		case UP:
			node.y += panel.getHeightScale() - 1;
			break;
		case RIGHT:
			node.x += 1;
			break;
		case DOWN:
			node.y += 1;
			break;
		}
		if (panel.getWidthScale() != 0) {
			node.x %= panel.getWidthScale();
		}
		if (panel.getHeightScale() != 0) {
			node.y %= panel.getHeightScale();
		}
		position.add(tail = node);
		if (!panel.getApples().collide(tail)) {
			position.poll();
		}
	}

	public Color getColor() {
		return color;
	}

	public boolean collide(Snake snake) {
		for (Point p1 : position) {
			for (Point p2 : snake.position) {
				if (p1.equals(p2)) {
					return true;
				}
			}
		}
		return false;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(color);
		for (Point p : position) {
			g.fillRect(p.x * SnakePanel.SCALE_RATIO, p.y * SnakePanel.SCALE_RATIO, SnakePanel.SCALE_RATIO,
					SnakePanel.SCALE_RATIO);
		}
		g.setColor(c);
	}
}
