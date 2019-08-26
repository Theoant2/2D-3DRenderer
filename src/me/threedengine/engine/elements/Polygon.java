package me.threedengine.engine.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

import me.threedengine.engine.Camera;
import me.twodengine.engine.Renderer;
import me.twodengine.engine.elements.Point;

public class Polygon implements Renderable, Drawable, Showable {


	private Point3D[] points;
	private Point[] points2D;

	public Polygon(Point3D ... points)
	{
		this.points = points;
		this.points2D = new Point[this.points.length];
	}
	public Polygon(Point ... points)
	{;
		this.points2D = new Point[points.length];
		for(int i = 0; i < points.length; i++)
			this.points2D[i] = points[i];
	}

	public void draw()
	{
		
	}

	public void floodFill(Renderer renderer) {

		Stack<Point> callStack = new Stack<>();
		callStack.add(new Point(this.points[0].getX(), this.points[0].getY()));
		while(!callStack.isEmpty()) {
			Point point = callStack.pop();
			if(this.isInside(point.getX(), point.getY())) {
				if(renderer.getRGB(point.getX(), point.getY()) != renderer.getFillColor().getRGB() /*&&
                            mImage.getRGB(point.X, point.Y) != BOUNDARY_RGB*/) {
					renderer.setRGB(point.getX(), point.getY(), renderer.getFillColor().getRGB());
					callStack.add(new Point(point.getX() + 1, point.getY()));
					callStack.add(new Point(point.getX() - 1, point.getY()));
					callStack.add(new Point(point.getX(), point.getY() + 1));
					callStack.add(new Point(point.getX(), point.getY() - 1));
				}
			}
		}
	}

	private boolean isInside(float x, float y) {
		boolean result = false;
		int i,j;
		for (i = 0, j = this.points.length - 1; i < this.points.length; j = i++) {
			if ((this.points[i].getY() > y) != (this.points[j].getY() > y) &&
					(x < (this.points[j].getX() - this.points[i].getX()) * (y - this.points[i].getY()) /
							(this.points[j].getY() - this.points[i].getY()) + this.points[i].getX())) {
				result = !result;
			}
		}
		return result;
	}

	public static Polygon[] parsePolygons(Point3D[] ... points)
	{
		Polygon[] polygons = new Polygon[points.length];
		for(int i = 0; i < points.length; i++)
			polygons[i] = new Polygon(points[i]);
		return polygons;
	}
	public static Polygon[] parsePolygons(ArrayList<Point3D[]> points)
	{
		Polygon[] polygons = new Polygon[points.size()];
		for(int i = 0; i < points.size(); i++)
			polygons[i] = new Polygon(points.get(i));
		return polygons;
	}
	public static Polygon[] parsePolygons(Camera camera, ArrayList<Point3D> points, ArrayList<Integer[]> faces)
	{
		Polygon[] polygons = new Polygon[faces.size()];
		for(int i = 0; i < faces.size(); i++)
		{
			Point3D[] _points = new Point3D[faces.get(i).length];
			for(int j = 0; j < faces.get(i).length; j++)
			{
				_points[j] = new Point3D(camera, points.get(faces.get(i)[j]).getX(), 
										 		 points.get(faces.get(i)[j]).getY(), 
										 		 points.get(faces.get(i)[j]).getZ());
			}
			polygons[i] = new Polygon(_points);
		}
		return polygons;
	}
	@Override
	public void render(Renderer renderer2d, Camera camera, float offsetX, float offsetY) {
		for(int i = 0; i < this.points.length; i++)
		{
			this.points2D[i] = this.points[i].projectPerspective2D(camera);
			this.points2D[i].translate(offsetX, offsetY);
		}
	}
	@Override
	public void translate(float x, float y, float z) {
		for(Point3D point : this.points) point.translate(x, y, z);
	}
	@Override
	public void rotate(float x, float y, float z) {
		for(Point3D point : this.points) point.rotate(x, y, z);
	}
	@Override
	public void scale(float size) {
		for(Point3D point : this.points) point.scale(size);
	}
	@Override
	public void connect(Renderer renderer2D) {
		for (int i = 0; i < points2D.length - 1; i++) {
			renderer2D.line(points2D[i].getX(), points2D[i].getY(), points2D[i + 1].getX(), points2D[i + 1].getY());
		}
		if(points2D.length != 2) renderer2D.line(points2D[points2D.length - 1].getX(), points2D[points2D.length - 1].getY(), points2D[0].getX(), points2D[0].getY());

	}

	public Point3D[] getPoints3D() { return this.points; }
	public Point[] getPoints2D() { return this.points2D; }
	
	public float getMaxX()
	{
		return Collections.max(new ArrayList<Point3D>(Arrays.asList(this.points)), Comparator.comparing(point -> point.getY())).getX();
	}
	public float getMinX()
	{
		return Collections.min(new ArrayList<Point3D>(Arrays.asList(this.points)), Comparator.comparing(point -> point.getY())).getX();
	}

	public float getMaxY()
	{
		return Collections.max(new ArrayList<Point3D>(Arrays.asList(this.points)), Comparator.comparing(point -> point.getY())).getY();
	}
	public float getMinY()
	{
		return Collections.min(new ArrayList<Point3D>(Arrays.asList(this.points)), Comparator.comparing(point -> point.getY())).getY();
	}

	public float getMaxZ()
	{
		return Collections.max(new ArrayList<Point3D>(Arrays.asList(this.points)), Comparator.comparing(point -> point.getY())).getZ();
	}
	public float getMinZ()
	{
		return Collections.min(new ArrayList<Point3D>(Arrays.asList(this.points)), Comparator.comparing(point -> point.getY())).getZ();
	}
}
