package me.threedengine.engine.elements;

import java.awt.Color;
import java.util.ArrayList;

import me.threedengine.engine.Camera;
import me.threedengine.engine.utils.ImprovedNoise;

public class NoisedLandscape extends Model {

	public NoisedLandscape(ArrayList<Point3D> points, ArrayList<Integer[]> faces) {
		super("Noised Landscape", points, faces);
	}
	
	@Override
	protected void connect(Integer ... face)
	{
		super.renderer2D.stroke(new Color(0));
		for (int i = 0; i < face.length - 1; i++) {
			super.renderer2D.stroke(Color.HSBtoRGB(super.points.get(face[face.length - 1]).getY() / (super.maxY * 2f), 1, 1));
			super.renderer2D.line(super.points2D[face[i]].getX(), super.points2D[face[i]].getY(), super.points2D[face[i + 1]].getX(), super.points2D[face[i + 1]].getY());
		}
		super.renderer2D.line(super.points2D[face[face.length - 1]].getX(), super.points2D[face[face.length - 1]].getY(), super.points2D[face[0]].getX(), super.points2D[face[0]].getY());
	}

	public static enum DetailedLevel {
		LINE(),
		TABLE(),
		TRIANGLE(),
		SQUARE()
	};

	public static NoisedLandscape generate(Camera camera, int width, int height, DetailedLevel detailedLevel)
	{
		int cols = width, rows = height;
		ArrayList<Point3D> points = new ArrayList<Point3D>();
		ArrayList<Integer[]> faces = new ArrayList<Integer[]>();

		for(int y = 0; y < rows; y++)
		{
			for(int x = 0; x < cols; x++)
			{
				points.add(new Point3D(camera, x * 2, (float) ImprovedNoise.noise(x * 0.1, y * 0.1, 0) * 10, y * 2));
			}
		}

		for(int y = 0; y < rows - 1; y++)
		{
			for(int x = 0; x < cols - 1; x++)
			{
				switch(detailedLevel)
				{
				case LINE:
				{
					faces.add(new Integer[]{x + y * cols, x + y * cols + 1});
					break;
				}
				case TABLE:
				{
					faces.add(new Integer[]{x + y * cols, x + y * cols + cols});
					faces.add(new Integer[]{x + y * cols, x + y * cols + 1});
					break;
				}
				case TRIANGLE:
				{
					faces.add(new Integer[]{x + y * cols, x + y * cols + 1, x + y * cols + cols});
					break;
				}
				case SQUARE:
				{
					faces.add(new Integer[]{x + y * cols, x + y * cols + 1, x + y * cols + cols, x + y * cols + cols + 1});
					break;
				}
				default:
				{
					faces.add(new Integer[]{x + y * cols, x + y * cols + 1});
					break;
				}
				}
			}
		}

		return new NoisedLandscape(points, faces);
	}
}
