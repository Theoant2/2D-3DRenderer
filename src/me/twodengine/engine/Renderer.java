package me.twodengine.engine;

import java.awt.Color;
import java.awt.image.DataBufferInt;
import java.util.Stack;

import me.threedengine.engine.elements.Polygon;
import me.twodengine.engine.elements.Point;
import me.twodengine.engine.elements.Vector2D;

public class Renderer {

	private int pW, pH;
	private int[] pixels;
	private Color strokeColor = new Color(0);
	private Color fillColor = new Color(255);
	private Color bufferColor = new Color(0);
	private boolean canFill = false;

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

	public void fill(Color c)
	{
		this.fillColor = c;
		if(this.fillColor.getRed() != 255 || this.fillColor.getGreen() != 255 || this.fillColor.getBlue() != 255)
		{
			canFill = true;
		} else canFill = false;
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
			if(Math.round(x1) + Math.round(y1) * this.pW < this.pixels.length && Math.round(x1) + Math.round(y1) * this.pW >= 0)
				this.pixels[Math.round(x1) + Math.round(y1) * this.pW] = this.strokeColor.getRGB();
		}
	}

	public void triangle(float x1, float y1,
			float x2, float y2,
			float x3, float y3)
	{
		// Bug à corriger ici
		if(canFill)
		{
			this.bufferColor = strokeColor;
			this.stroke(fillColor);
			
			float dx1, dx2, dx3;
			if (y2-y1 > 0) dx1=(x2-x1)/(y2-y1); else dx1=0;
			if (y3-y1 > 0) dx2=(x3-x1)/(y3-y1); else dx2=0;
			if (y3-y2 > 0) dx3=(x3-x2)/(y3-y2); else dx3=0;

			Vector2D S = new Vector2D(0, 0);
			Vector2D E = new Vector2D(0, 0);
			Vector2D A = new Vector2D(0, 0);
			if(dx1 > dx2) {
				for(;S.getY()<=x2;S.incY(),E.incY(),S.addX(dx2),E.addX(dx1))
					this.line(S.getX(), S.getY(), E.getX(), S.getY());
				E.setX(x2);
				E.setY(y2);
				for(;S.getY()<=y3;S.incY(),E.incY(),S.addX(dx2),E.addX(dx3))
					this.line(S.getX(), S.getY(), E.getX(), S.getY());
			} else {
				for(;S.getY()<=y2;S.incY(),E.incY(),S.addX(dx1),E.addX(dx2))
					this.line(S.getX(), S.getY(), E.getX(), S.getY());
				S.setX(x2);
				S.setY(y2);
				for(;S.getY()<=y3;S.incY(),E.incY(),S.addX(dx3),E.addX(dx2))
					this.line(S.getX(), S.getY(), E.getX(), S.getY());
			}
			
			this.stroke(this.bufferColor);
		} else {
			this.line(x1, y1, x2, y2);
			this.line(x2, y2, x3, y3);
			this.line(x3, y3, x1, y1);
		}
	}

	public void triangle(Vector2D v1, Vector2D v2, Vector2D v3)
	{
		this.triangle(v1.getX(), v1.getY(), v2.getX(), v2.getY(), v3.getX(), v3.getY());
	}

	public void polygon(Point ... points)
	{
		new Polygon(points).connect(this);
	}
	
	public void filledPolygon(Point ... points)
	{
		new Polygon(points).floodFill(this);
	}
	
	public int getRGB(float x, float y)
	{
		if(Math.round(x) >= 0 && Math.round(x) <= this.pW &&
		   Math.round(y) >= 0 && Math.round(y) <= this.pH)
		return new Color(this.pixels[Math.round(x) + Math.round(y) * this.pW]).getRGB();
		return new Color(0).getRGB();
	}
	public void setRGB(float x, float y, int rgb)
	{
		if(Math.round(x) >= 0 && Math.round(x) <= this.pW &&
	       Math.round(y) >= 0 && Math.round(y) <= this.pH)
		this.pixels[Math.round(x) + Math.round(y) * this.pW] = rgb;
	}
	
	public int getWidth() {
		return pW;
	}

	public int getHeight() {
		return pH;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public Color getFillColor() {
		return fillColor;
	}
}
