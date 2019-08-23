package me.threedengine.engine.utils;

import java.util.ArrayList;

import me.threedengine.engine.Camera;
import me.threedengine.engine.elements.Model;
import me.threedengine.engine.elements.Point3D;

public class NoisedLandscape {

	public static Model generate(Camera camera, int width, int height)
	{
		int cols = width, rows = height;
		ArrayList<Point3D> points = new ArrayList<Point3D>();
		ArrayList<Integer[]> faces = new ArrayList<Integer[]>();

		for(int y = 0; y < rows; y++)
		{
			for(int x = 0; x < cols; x++)
			{
				points.add(new Point3D(camera, x * 2, (float) ImprovedNoise.noise(x * 0.1, y * 0.1, 0) * 6, y * 2));
			}
		}

		for(int y = 0; y < rows - 1; y++)
		{
			for(int x = 0; x < cols - 1; x++)
			{
				faces.add(new Integer[]{x + y * cols, x + y * cols + 1, x + y * cols + cols, x + y * cols + cols + 1});
			}
		}

		return new Model("Noised Landscape", points, faces);
	}
}
