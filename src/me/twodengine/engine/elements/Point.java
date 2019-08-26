package me.twodengine.engine.elements;

public class Point {

	private float x, y, w;

	public Point(float x, float y)
	{ 
		this.x = x;
		this.y = y; 
		this.w = 1;
	}

	public void mult(float coef)
	{
		this.x *= coef;
		this.y *= coef;
	}

	public float[][] toMatrix()
	{
		return new float[][]{{this.x}, {this.y}, {this.w}};
	}

	public void translate(float x, float y)
	{
		this.x += x;
		this.y += y;
	}

	public float getX() { return this.x; }
	public float getY() { return this.y; }

	public String toString() { return this.x + "," + this.y; }
}
