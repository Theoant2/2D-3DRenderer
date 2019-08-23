package me.twodengine.engine;

import java.awt.Color;
import java.awt.image.DataBufferInt;

public class Renderer {

	private int pW, pH;
	private int[] pixels;
	private Color strokeColor = new Color(0);

	public Renderer(WindowContainer wc)
	{
		this.pW = wc.getWidth();
		this.pH = wc.getHeight();
		this.pixels = ((DataBufferInt) wc.getWindow().getImage().getRaster().getDataBuffer()).getData();
	}

	public void clear()
	{
		for(int i = 0; i < this.pixels.length; i++)
		{
			this.pixels[i] = 0xFFFFFFFF;
		}
	}

	public void stroke(Color c)
	{
		this.strokeColor = c;
	}
	public void stroke(int rgb)
	{
		this.strokeColor = new Color(rgb);
	}

	public void line(float x1, float y1, float x2, float y2)
	{
		final double x_ = x2 - x1;
		final double y_ = y2 - y1;
		final double hyp = Math.hypot(x_, y_);
		int x, y;
		for(int i = 0; i < hyp; i++)
		{
			x = (int) Math.floor(x1 + i * (x_ / hyp));
			y = (int) Math.floor(y1 + i * (y_ / hyp));
			if(x + y * this.pW < this.pixels.length && x + y * this.pW >= 0)
				this.pixels[x + y * this.pW] = this.strokeColor.getRGB();
		}
	}

	public int getWidth() {
		return pW;
	}

	public int getHeight() {
		return pH;
	}
}
