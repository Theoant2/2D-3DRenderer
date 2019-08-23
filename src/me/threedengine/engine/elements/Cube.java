package me.threedengine.engine.elements;

import java.awt.Color;

import me.threedengine.engine.Camera;
import me.twodengine.engine.Renderer;

public class Cube implements Renderable {

	private Renderer renderer2D;
	private float x, y, z, size;
	private Point3D[] points;
	private Point[] points2D;

	public Cube(Camera camera, float x, float y, float z, float size)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.size = size;

		this.points = new Point3D[8];
		this.points2D = new Point[8];

		this.points[0] = new Point3D(camera, this.x, this.y, this.z);
		this.points[1] = new Point3D(camera, this.x + this.size, this.y, this.z);
		this.points[2] = new Point3D(camera,this.x + this.size, this.y + this.size, this.z);
		this.points[3] = new Point3D(camera,this.x, this.y + this.size, this.z);

		this.points[4] = new Point3D(camera,this.x, this.y, this.z + this.size);
		this.points[5] = new Point3D(camera,this.x + this.size, this.y, this.z + this.size);
		this.points[6] = new Point3D(camera,this.x + this.size, this.y + this.size, this.z + this.size);
		this.points[7] = new Point3D(camera,this.x, this.y + this.size, this.z + this.size);
	}

	public void translate(float x, float y, float z)
	{
		for (int i = 0; i < this.points.length; i++)
		{
			this.points[i].translate(x, y, z);
		}
	}

	public void rotate(float angleX, float angleY, float angleZ)
	{
		for (int i = 0; i < this.points.length; i++)
		{
			this.points[i].rotate(angleX, angleY, angleZ);
		}
	}

	public void scale(float size)
	{
		for (int i = 0; i < this.points.length; i++)
		{
			this.points[i].scale(size);
		}
	}

	public void render(Renderer renderer2D, Camera camera, float offsetX, float offsetY)
	{
		this.renderer2D = renderer2D;
		for (int i = 0; i < this.points.length; i++)
		{
			Point point2D = this.points[i].projectPerspective2D(camera);
			point2D.translate(offsetX, offsetY);
			this.points2D[i] = point2D;
		}
		this.connect();
	}

	private void connect()
	{
		this.renderer2D.stroke(new Color(0));
		this.renderer2D.line(this.points2D[0].getX(), this.points2D[0].getY(), this.points2D[1].getX(), this.points2D[1].getY());
		this.renderer2D.line(this.points2D[1].getX(), this.points2D[1].getY(), this.points2D[2].getX(), this.points2D[2].getY());
		this.renderer2D.line(this.points2D[2].getX(), this.points2D[2].getY(), this.points2D[3].getX(), this.points2D[3].getY());
		this.renderer2D.line(this.points2D[3].getX(), this.points2D[3].getY(), this.points2D[0].getX(), this.points2D[0].getY());

		this.renderer2D.line(this.points2D[4].getX(), this.points2D[4].getY(), this.points2D[5].getX(), this.points2D[5].getY());
		this.renderer2D.line(this.points2D[5].getX(), this.points2D[5].getY(), this.points2D[6].getX(), this.points2D[6].getY());
		this.renderer2D.line(this.points2D[6].getX(), this.points2D[6].getY(), this.points2D[7].getX(), this.points2D[7].getY());
		this.renderer2D.line(this.points2D[7].getX(), this.points2D[7].getY(), this.points2D[4].getX(), this.points2D[4].getY());

		this.renderer2D.line(this.points2D[0].getX(), this.points2D[0].getY(), this.points2D[4].getX(), this.points2D[4].getY());
		this.renderer2D.line(this.points2D[1].getX(), this.points2D[1].getY(), this.points2D[5].getX(), this.points2D[5].getY());
		this.renderer2D.line(this.points2D[2].getX(), this.points2D[2].getY(), this.points2D[6].getX(), this.points2D[6].getY());
		this.renderer2D.line(this.points2D[3].getX(), this.points2D[3].getY(), this.points2D[7].getX(), this.points2D[7].getY());
	}

}
