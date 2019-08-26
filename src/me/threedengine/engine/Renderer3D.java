package me.threedengine.engine;

import java.util.ArrayList;

import me.threedengine.engine.elements.Cube;
import me.threedengine.engine.elements.Drawable;
import me.threedengine.engine.elements.Origin;
import me.threedengine.engine.elements.Point3D;
import me.threedengine.engine.elements.Renderable;
import me.threedengine.engine.elements.Showable;
import me.threedengine.engine.elements.Sphere;
import me.threedengine.engine.utils.Loader;
import me.threedengine.engine.utils.Vector3D;
import me.twodengine.engine.Renderer;

public class Renderer3D {
	private Renderer renderer2D;
	private ArrayList<Renderable> elements;
	private float offsetX, offsetY;

	private Origin origin;
	
	private Camera camera;

	Renderer3D(Renderer renderer2D, float offsetX, float offsetY)
	{
		this.renderer2D = renderer2D;
		this.camera = new Camera(0, 0, 0, renderer2D.getWidth(), renderer2D.getHeight());
		this.elements = new ArrayList<Renderable>();
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.origin = new Origin(this.camera, 20);
	}

	public Renderable get(int i) { return this.elements.get(i - 1); }

	public void drawOrigin()
	{
		this.elements.add(this.origin);
	}

	public int createPoint(float x, float y, float z)
	{
		Point3D point = new Point3D(this.camera, x, y, z);
		this.elements.add(point);
		return this.elements.size();
	}
	public int createCube(float x, float y, float z, float size)
	{
		Cube cube = new Cube(this.camera, x, y, z, size);
		this.elements.add(cube);
		return this.elements.size();
	}
	public int createSphere(int x, int y, int z, int radius)
	{
		Sphere sphere = Sphere.generate(this.camera, new Vector3D(x, y, z), radius);
		this.elements.add(sphere);
		return this.elements.size();
	}
	public int createModel(String url)
	{
		Loader loader = new Loader(url, this.camera);
		this.elements.add(loader.computeModel());
		return this.elements.size();
	}

	public int addElement(Renderable element)
	{
		this.elements.add(element);
		return this.elements.size();
	}

	public void rotate(float angleX, float angleY, float angleZ)
	{
		for (int i = 0; i < this.elements.size(); i++)
		{
			this.elements.get(i).rotate(angleX, angleY, angleZ);
		}
	}

	public void translate(float x, float y, float z)
	{
		for (int i = 0; i < this.elements.size(); i++)
		{
			if(this.elements.get(i) instanceof Origin) continue;
			this.elements.get(i).translate(x, y, z);
		}
	}

	public void scale(float size)
	{
		for (int i = 0; i < this.elements.size(); i++)
		{
			this.elements.get(i).scale(size);
		}
	}

	public void render()
	{
		this.camera.generateExtrinsicMatrix();
		for (int i = 0; i < this.elements.size(); i++)
		{
			this.elements.get(i).render(this.renderer2D, this.camera, this.offsetX, this.offsetY);
		}
	}
	
	public void connect()
	{
		for (int i = 0; i < this.elements.size(); i++)
		{
			if(this.elements.get(i) instanceof Showable)
				((Showable) this.elements.get(i)).connect(this.renderer2D);
		}
	}
	
	public void draw()
	{
		for (int i = 0; i < this.elements.size(); i++)
		{
			if(this.elements.get(i) instanceof Drawable)
				((Drawable) this.elements.get(i)).draw();
		}
	}

	public int getElementsSize()
	{
		return this.elements.size();
	}

	public Camera getCamera() {
		return camera;
	}
	
	
}
