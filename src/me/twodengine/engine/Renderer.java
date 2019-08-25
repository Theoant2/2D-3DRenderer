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
		final float x_ = x2 - x1;
		final float y_ = y2 - y1;
		float n = 0;
		if (Math.abs(x_) > Math.abs(y_))
			n = (float) Math.abs(x_);
		else
			n = (float) Math.abs(y_);
		final float Xincrement = x_ / n;
		final float Yincrement = y_ / n;
		for(int i = 0; i < n; i++)
		{
			x1 += Xincrement;
			y1 += Yincrement;
			this.pixels[Math.round(x1) + Math.round(y1) * this.pW] = this.strokeColor.getRGB();
		}
		
	}

	public int getWidth() {
		return pW;
	}

	public int getHeight() {
		return pH;
	}
}
