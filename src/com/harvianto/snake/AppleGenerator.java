package com.harvianto.snake;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.Vector;

public class AppleGenerator {
	private Vector<Apple> listApples = new Vector<Apple>();
	
	private SnakePanel panel;
	
	private Thread th = new Thread(()->{
		while(panel.isPlay()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
			listApples.add(new Apple(new Dimension(panel.getWidthScale(), panel.getHeightScale())));
		}
	});
	
	public AppleGenerator(SnakePanel panel) {
		this.panel = panel;
		th.start();
	}
	
	public synchronized boolean collide(Point p) {
		Iterator<Apple> it = listApples.iterator();
		while (it.hasNext()) {
			Apple apple = (Apple) it.next();
			if(apple.equals(p)) {
				listApples.removeElement(apple);
				return true;
			}
		}
		return false;
	}
	
	public synchronized void draw(Graphics g) {
		for (Apple apple : listApples) {
			apple.draw(g);
		}
	}
}
