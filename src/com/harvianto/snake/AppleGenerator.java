package com.harvianto.snake;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

public class AppleGenerator {
	private Vector<Apple> listApples = new Vector<Apple>();

	private SnakePanel panel;

	private Thread th = new Thread(() -> {
		while (panel.isPlay()) {
			try {
				Thread.sleep(1000);
				listApples.add(new Apple(new Dimension(panel.getWidthScale(), panel.getHeightScale())));
			} catch (InterruptedException | ConcurrentModificationException ex) {
				ex.printStackTrace();
			}
		}
	});

	public AppleGenerator(SnakePanel panel) {
		this.panel = panel;
		th.start();
	}

	public boolean collide(Point p) {
		try {
			Iterator<Apple> it = listApples.iterator();
			while (it.hasNext()) {
				Apple apple = (Apple) it.next();
				if (apple.equals(p)) {
					listApples.remove(apple);
					return true;
				}
			}
		} catch (ConcurrentModificationException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public void draw(Graphics g) {
		try {
			Iterator<Apple> it = listApples.iterator();
			while (it.hasNext()) {
				Apple apple = (Apple) it.next();
				apple.draw(g);
			}
		} catch (ConcurrentModificationException ex) {
			ex.printStackTrace();
		}
	}
}
