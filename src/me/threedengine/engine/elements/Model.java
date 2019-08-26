package me.threedengine.engine.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.IntStream;

import me.threedengine.engine.Camera;
import me.twodengine.engine.Renderer;
import me.twodengine.engine.elements.Point;
import me.twodengine.engine.elements.Vector2D;

public class Model implements Renderable, Drawable, Showable {

	protected Renderer renderer2D;
	protected Polygon[] polygons;
	protected ArrayList<Integer[]> faces;
	private String name;
	private boolean centering = false;
	private Vector2D centeringV = new Vector2D(0, 0);
	
	protected float minX, maxX,
					minY, maxY,
					minZ, maxZ;
	
	// Changer le constructeur pour public Model(String name, ArrayList<Polygon> polygons)
	// Pour eviter la conversion (Polygon.parsePolygons) à chaque frames
	// Et se sera plus pratique pour le futur (i.e. Triangle extends Polygon)
	
	public Model(String name, Polygon[] polygons)
	{
		this.name = name;
		this.polygons = polygons;
		
		try {
			this.minX = this.getMinX();
			this.maxX = this.getMaxX();
			this.minY = this.getMinY();
			this.maxY = this.getMaxY();
			this.minZ = this.getMinZ();
			this.maxZ = this.getMaxZ();
		} catch(Exception e) {}
	}

	public void render(Renderer renderer2D, Camera camera, float offsetX, float offsetY)
	{
		this.renderer2D = renderer2D;
		/*IntStream.range(0, this.points.size()).parallel().forEach(i -> {
			this.points2D[i] = this.points.get(i).projectPerspective2D(camera);
			this.points2D[i].translate(offsetX - centeringV.getX(), offsetY - centeringV.getX());
		});*/
		for (int i = 0; i < this.polygons.length; i++) {
			this.polygons[i].render(renderer2D, camera, offsetX, offsetY);
		}
	}

	public void translate(float x, float y, float z) 
	{
		//if(this.name == "Noised Landscape") System.out.println(this.points.get(0));
		for (Polygon polygon : this.polygons) polygon.translate(x, y, z);
		//if(this.name == "Noised Landscape") System.out.println(this.points.get(0));
	}

	public void rotate(float angleX, float angleY, float angleZ)
	{
		for (Polygon polygon : this.polygons) polygon.rotate(angleX, angleY, angleZ);
	}
	public void scale(float size)
	{
		for (Polygon polygon : this.polygons) polygon.scale(size);
	}


	public void connect(Renderer renderer2D)
	{
		for(int i = 0; i < this.polygons.length; i++)
			this.polygons[i].connect(renderer2D);
	}
	
	public void draw() {
		System.out.println("dqdzqddqzd");
		for(Polygon polygon : this.polygons) polygon.floodFill(this.renderer2D);
	}

	public void setCentering(boolean centering) {
		this.centering = centering;
	}

	public float getMaxX()
	{
		return Collections.max(new ArrayList<Polygon>(Arrays.asList(this.polygons)), Comparator.comparing(polygon -> polygon.getMaxY())).getMaxX();
	}
	public float getMinX()
	{
		return Collections.max(new ArrayList<Polygon>(Arrays.asList(this.polygons)), Comparator.comparing(polygon -> polygon.getMaxY())).getMinX();
	}

	public float getMaxY()
	{
		return Collections.max(new ArrayList<Polygon>(Arrays.asList(this.polygons)), Comparator.comparing(polygon -> polygon.getMaxY())).getMaxY();
	}
	public float getMinY()
	{
		return Collections.max(new ArrayList<Polygon>(Arrays.asList(this.polygons)), Comparator.comparing(polygon -> polygon.getMaxY())).getMinY();
	}

	public float getMaxZ()
	{
		return Collections.max(new ArrayList<Polygon>(Arrays.asList(this.polygons)), Comparator.comparing(polygon -> polygon.getMaxY())).getMaxZ();
	}
	public float getMinZ()
	{
		return Collections.max(new ArrayList<Polygon>(Arrays.asList(this.polygons)), Comparator.comparing(polygon -> polygon.getMaxY())).getMinZ();
	}
}
