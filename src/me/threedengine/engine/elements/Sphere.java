package me.threedengine.engine.elements;

import java.util.ArrayList;

import me.threedengine.engine.Camera;
import me.threedengine.engine.utils.Vector3D;

public class Sphere extends Model {

	public Sphere(ArrayList<Point3D> points, ArrayList<Integer[]> faces) {
		super("Sphere", points, faces);

	}

	public static Sphere generate(Camera camera, Vector3D location, float radius)
	{
		ArrayList<Point3D> points = new ArrayList<Point3D>();
		ArrayList<Integer[]> faces = new ArrayList<Integer[]>();

		int cols = 10, rows = 1;
		int alpha = 0;
		for(int j = 0; j < 4; j++)
		{
			for(int i = 0; i < 100; i++)
				points.add(new Point3D(camera, (float) (location.getX() + Math.cos(i * j * Math.PI / (2 * 100)) * radius),
										(float) (location.getY() + Math.sin(i * Math.PI / (2 * 100)) * radius), location.getZ()));
			for(int i = 0; i < 99 * j; i++)
				faces.add(new Integer[] {i, i+1});
		}
		
		/*for(int y = 0; y < rows - 1; y++)
		{
			alpha = 0;
			for(int x = 0; x < cols - 1; x++)
			{
				System.out.println((x + y * cols) + "," + (x + y * cols + cols));
				faces.add(new Integer[] {x + y * cols, x + y * cols + cols});
			}
		}*/

		return new Sphere(points, faces);
	}

}
