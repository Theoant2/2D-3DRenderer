package me.twodengine.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window {
	
	private JFrame frame;
	private BufferedImage image;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	
	public Window(WindowContainer wc)
	{
		this.image = new BufferedImage(wc.getWidth(), wc.getHeight(), BufferedImage.TYPE_INT_RGB);
		this.canvas = new Canvas();
		Dimension dimension = new Dimension((int) (wc.getWidth() * wc.getScale()),
				  							(int) (wc.getHeight() * wc.getScale()));
		this.canvas.setPreferredSize(dimension);
		this.canvas.setMaximumSize(dimension);
		this.canvas.setMinimumSize(dimension);
		
		this.frame = new JFrame(wc.getTitle());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());
		this.frame.add(this.canvas, BorderLayout.CENTER);
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
		
		this.canvas.createBufferStrategy(2);
		this.bs = this.canvas.getBufferStrategy();
		this.g = this.bs.getDrawGraphics();
	}
	
	public void update()
	{
		this.g.drawImage(this.image, 0, 0, this.canvas.getWidth(), this.canvas.getHeight(), null);
		this.bs.show();
	}

	public BufferedImage getImage() {
		return this.image;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}
}
