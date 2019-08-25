package me.threedengine.engine.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.IntStream;

import me.threedengine.engine.Camera;
import me.threedengine.engine.utils.Vector2D;
import me.twodengine.engine.Renderer;

public class Model implements Renderable {

	protected Renderer renderer2D;
	protected ArrayList<Point3D> points;
	protected Point[] points2D;
	protected ArrayList<Integer[]> faces;
	private String name;
	private boolean centering = false;
	private Vector2D centeringV = new Vector2D(0, 0);

	protected float minX, maxX,
	minY, maxY,
	minZ, maxZ;

	public Model(String name, ArrayList<Point3D> points, ArrayList<Integer[]> faces)
	{
		this.name = name;
		this.points = points;
		this.faces = faces;
		this.points2D = new Point[this.points.size()];

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
		if(this.centering)
		{
			float xmin = this.points.stream().min((first, second) -> Double.compare(first.getX(), second.getX())).get().getX();
			float xmax = this.points.stream().max((first, second) -> Double.compare(first.getX(), second.getX())).get().getX();

			float ymin = this.points.stream().min((first, second) -> Double.compare(first.getY(), second.getY())).get().getY();
			float ymax = this.points.stream().max((first, second) -> Double.compare(first.getY(), second.getY())).get().getY();
			this.centeringV = new Vector2D((xmax - xmin), (ymax - ymin));
			this.centering = false;
		}
		this.renderer2D = renderer2D;
		IntStream.range(0, this.points.size()).parallel().forEach(i -> {
			this.points2D[i] = this.points.get(i).projectPerspective2D(camera);
			this.points2D[i].translate(offsetX - centeringV.getX(), offsetY - centeringV.getX());
		});
		/*for (int i = 0; i < this.points.size(); i++) {
			this.points2D[i] = this.points.get(i).projectPerspective2D(camera);
			this.points2D[i].translate(offsetX - centeringV.getX(), offsetY - centeringV.getX());
		}*/
		for (Integer[] face : this.faces) this.connect(face);
	}

	public void translate(float x, float y, float z) 
	{
		//if(this.name == "Noised Landscape") System.out.println(this.points.get(0));
		for (Point3D point : this.points) point.translate(x, y, z);
		//if(this.name == "Noised Landscape") System.out.println(this.points.get(0));
	}

	public void rotate(float angleX, float angleY, float angleZ)
	{
		for (Point3D point : this.points) point.rotate(angleX, angleY, angleZ);
	}
	public void scale(float size)
	{
		for (Point3D point : this.points) point.scale(size);
	}


	protected void connect(Integer ... face)
	{
		this.renderer2D.stroke(new Color(0));
		for (int i = 0; i < face.length - 1; i++) {
			this.renderer2D.line(points2D[face[i]].getX(), points2D[face[i]].getY(), points2D[face[i + 1]].getX(), points2D[face[i + 1]].getY());
		}
		if(face.length != 2) this.renderer2D.line(points2D[face[face.length - 1]].getX(), points2D[face[face.length - 1]].getY(), points2D[face[0]].getX(), points2D[face[0]].getY());
	}

	public void setCentering(boolean centering) {
		this.centering = centering;
	}

	protected void addPoint(Point3D point) { this.points.add(point); }

	protected void addFace(Integer[] face) { this.faces.add(face); }

	private float getMaxX()
	{
		return Collections.max(this.points, Comparator.comparing(point -> point.getY())).getX();
	}
	private float getMinX()
	{
		return Collections.min(this.points, Comparator.comparing(point -> point.getY())).getX();
	}

	private float getMaxY()
	{
		return Collections.max(this.points, Comparator.comparing(point -> point.getY())).getY();
	}
	private float getMinY()
	{
		return Collections.min(this.points, Comparator.comparing(point -> point.getY())).getY();
	}

	private float getMaxZ()
	{
		return Collections.max(this.points, Comparator.comparing(point -> point.getY())).getZ();
	}
	private float getMinZ()
	{
		return Collections.min(this.points, Comparator.comparing(point -> point.getY())).getZ();
	}
}
