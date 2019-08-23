package me.threedengine.engine.elements;

import java.awt.Color;

import me.threedengine.engine.Camera;
import me.twodengine.engine.Renderer;

public class Origin implements Renderable {

	private Renderer renderer2D;
	private Point3D origin, abs, ord, prof;
	private Point origin2D, abs2D, ord2D, prof2D;
	private boolean moddable = true;

	public Origin(Camera camera, float size)
	{
		this.origin = new Point3D(camera, 0, 0, 0);
		this.abs = new Point3D(camera, size, 0, 0);
		this.ord = new Point3D(camera, 0, size, 0);
		this.prof = new Point3D(camera, 0, 0, size);
	}

	public void setModdable(boolean moddable) { 
		this.moddable = moddable;
	}

	public void translate(float x, float y, float z)
	{
		if (!this.moddable) return;
		this.origin.translate(x, y, z);
		this.abs.translate(x, y, z);
		this.ord.translate(x, y, z);
		this.prof.translate(x, y, z);
	}
	public void rotate(float angleX, float angleY, float angleZ)
	{
		this.origin.rotate(angleX, angleY, angleZ);
		this.abs.rotate(angleX, angleY, angleZ);
		this.ord.rotate(angleX, angleY, angleZ);
		this.prof.rotate(angleX, angleY, angleZ);
	}
	public void scale(float size)
	{
		this.origin.scale(size);
		this.abs.scale(size);
		this.ord.scale(size);
		this.prof.scale(size);
	}

	public void render(Renderer renderer2D, Camera camera, float offsetX, float offsetY)
	{
		this.renderer2D = renderer2D;
		this.origin2D = this.origin.compute(camera, offsetX, offsetY);
		this.abs2D = this.abs.compute(camera, offsetX, offsetY);
		this.ord2D = this.ord.compute(camera, offsetX, offsetY);
		this.prof2D = this.prof.compute(camera, offsetX, offsetY);
		this.connect();
	}

	private void connect()
	{

		// Rouge = X
		this.renderer2D.stroke(new Color(255, 0, 0));
		this.renderer2D.line(this.origin2D.getX(), this.origin2D.getY(), this.abs2D.getX(), this.abs2D.getY());

		// Vert = Y
		this.renderer2D.stroke(new Color(0, 255, 0));
		this.renderer2D.line(this.origin2D.getX(), this.origin2D.getY(), this.ord2D.getX(), this.ord2D.getY());

		// Bleu = Z
		this.renderer2D.stroke(new Color(0, 0, 255));
		this.renderer2D.line(this.origin2D.getX(), this.origin2D.getY(), this.prof2D.getX(), this.prof2D.getY());

		this.renderer2D.stroke(new Color(0));
	}
}
