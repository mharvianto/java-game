package com.harvianto.snake;

import javax.swing.JFrame;

public class SnakeFrame {

	private JFrame frame = null;
	
	public SnakeFrame() {
		frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.add(new SnakePanel());
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new SnakeFrame();
	}

}
