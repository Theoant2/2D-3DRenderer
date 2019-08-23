package me.threedengine.engine.utils;

public class Vector2D {

	private float x, y;
	
	public Vector2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public void add(float x, float y)
	{
		this.x += x;
		this.y += y;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public String toString()
	{
		return this.x + "," + this.y + ",";
	}
}
