package me.threedengine.engine.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.stream.IntStream;

import me.threedengine.engine.Camera;
import me.threedengine.engine.utils.Vector2D;
import me.twodengine.engine.Renderer;

public class Model implements Renderable {

	private Renderer renderer2D;
	private String name;
	private ArrayList<Point3D> points;
	private Point[] points2D;
	private ArrayList<Integer[]> faces;
	private boolean centering = false;
	private Vector2D centeringV = new Vector2D(0, 0);

	public Model(String name, ArrayList<Point3D> points, ArrayList<Integer[]> faces)
	{
		this.name = name;
		this.points = points;
		this.faces = faces;
		this.points2D = new Point[this.points.size()];
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


	private void connect(Integer ... face)
	{
		this.renderer2D.stroke(new Color(0));
		for (int i = 0; i < face.length - 1; i++) {
			this.renderer2D.line(points2D[face[i]].getX(), points2D[face[i]].getY(), points2D[face[i + 1]].getX(), points2D[face[i + 1]].getY());
		}
		this.renderer2D.line(points2D[face[face.length - 1]].getX(), points2D[face[face.length - 1]].getY(), points2D[face[0]].getX(), points2D[face[0]].getY());
	}

	public void setCentering(boolean centering) {
		this.centering = centering;
	}
}
