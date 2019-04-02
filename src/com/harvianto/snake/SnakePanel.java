package com.harvianto.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class SnakePanel extends JPanel {

	private Snake player1 = new Snake(this);
	private Snake player2 = new Snake(this, new Point(10, 10), Color.BLUE);

	private AppleGenerator apples = null;

	private boolean play = true;
	private boolean pause = false;
	private String winner = "";
	private final static int FPS = 30;

	public final static int SCALE_RATIO = 10;

	private Thread render = new Thread(() -> {
		while (play) {
			if (!pause) {
				update();
				repaint(1000 / FPS);
				try {
					Thread.sleep(1000 / FPS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		repaint();
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
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				player1.setDirection(Direction.LEFT);
				break;
			case KeyEvent.VK_UP:
				player1.setDirection(Direction.UP);
				break;
			case KeyEvent.VK_RIGHT:
				player1.setDirection(Direction.RIGHT);
				break;
			case KeyEvent.VK_DOWN:
				player1.setDirection(Direction.DOWN);
				break;
			case KeyEvent.VK_A:
				player2.setDirection(Direction.LEFT);
				break;
			case KeyEvent.VK_W:
				player2.setDirection(Direction.UP);
				break;
			case KeyEvent.VK_D:
				player2.setDirection(Direction.RIGHT);
				break;
			case KeyEvent.VK_S:
				player2.setDirection(Direction.DOWN);
				break;
			}
		}
	};

	public int getWidthScale() {
		return getWidth() / SCALE_RATIO;
	}

	public int getHeightScale() {
		return getHeight() / SCALE_RATIO;
	}

	public boolean isPlay() {
		return play;
	}

	public boolean isPause() {
		return pause;
	}

	public SnakePanel() {
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(keyListener);
		apples = new AppleGenerator(this);
		render.start();
	}

	public AppleGenerator getApples() {
		return apples;
	}

	private void update() {
		player1.move();
		if (player1.collide(player2)) {
			winner = "Blue";
			play = false;
			return;
		}
		player2.move();
		if (player2.collide(player1)) {
			winner = "Black";
			play = false;
			return;
		}
	}

	@Override
	public void paint(Graphics g) {
		// super.paint(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		apples.draw(g);
		player1.draw(g);
		player2.draw(g);

		if (!winner.equals("")) {
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(winner + " snake Win!", getWidth() / 2 - 60, getHeight() / 2);
		}
	}
}
