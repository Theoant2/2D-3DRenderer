package me.threedengine.engine.elements;

import java.awt.Color;
import java.util.ArrayList;

import me.threedengine.engine.Camera;
import me.threedengine.engine.utils.ImprovedNoise;
import me.twodengine.engine.Renderer;

public class NoisedLandscape extends Model {

	public NoisedLandscape(Polygon[] polygons) {
		super("Noised Landscape", polygons);
	}
	
	@Override
	public void connect(Renderer renderer2D)
	{
		for(int i = 0; i < super.polygons.length; i++)
		{
			for(int j = 0; j < super.polygons[i].getPoints2D().length - 1; j++)
			{
				renderer2D.stroke(Color.HSBtoRGB(super.polygons[i].getPoints3D()[super.polygons[i].getPoints3D().length - 1].getY() / (super.maxY * 2f), 1, 1));
				renderer2D.line(super.polygons[i].getPoints2D()[j].getX(), super.polygons[i].getPoints2D()[j].getY(),
								super.polygons[i].getPoints2D()[j + 1].getX(), super.polygons[i].getPoints2D()[j + 1].getY());
			}
			renderer2D.line(super.polygons[i].getPoints2D()[super.polygons[i].getPoints2D().length - 1].getX(),
							super.polygons[i].getPoints2D()[super.polygons[i].getPoints2D().length - 1].getY(),
							super.polygons[i].getPoints2D()[0].getX(),
							super.polygons[i].getPoints2D()[0].getY());
		}
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
					faces.add(new Integer[]{x + y * cols, x + y * cols + 1, x + y * cols + cols + 1, x + y * cols + cols});
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

		return new NoisedLandscape(Polygon.parsePolygons(camera, points, faces));
	}
}
