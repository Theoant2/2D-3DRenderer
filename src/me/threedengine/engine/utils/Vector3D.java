package me.threedengine.engine.utils;

public class Vector3D {

	private float x, y, z;
	
	public Vector3D(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void add(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}
	public void add(Vector3D vector)
	{
		this.x += vector.getX();
		this.y += vector.getY();
		this.z += vector.getZ();
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	public String toString()
	{
		return this.x + "," + this.y + "," + this.z;
	}
}
