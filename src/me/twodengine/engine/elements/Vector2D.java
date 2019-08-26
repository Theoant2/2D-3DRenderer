package me.twodengine.engine.elements;

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
	
	public void incX() { this.x++; }
	public void incY() { this.y++; }

	public void decX() { this.x--; }
	public void decY() { this.y--; }

	public void addX(float x) { this.x += x; }
	public void addY(float y) { this.y += y; }
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void equalsTo(Vector2D other)
	{
		this.x = other.x;
		this.y = other.y;
	}

	public String toString()
	{
		return this.x + "," + this.y + ",";
	}
}
