package me.threedengine.engine.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import me.threedengine.engine.Camera;
import me.threedengine.engine.elements.Model;
import me.threedengine.engine.elements.Point3D;

public class Loader {

	private String url;
	private Camera camera;

	public Loader(String url, Camera camera)
	{
		this.url = url;
		this.camera = camera;
	}

	public Model computeModel()
	{
		File file = new File(this.url);
		Scanner sc;
		try {
			sc = new Scanner(file);
			String line = "";
			
			ArrayList<Point3D> points = new ArrayList<Point3D>();
			ArrayList<Integer[]> faces = new ArrayList<Integer[]>();
			
			while(sc.hasNextLine())
			{
				line = sc.nextLine();
				String[] words = line.split(" ");
				if(words.length > 0)
				{
					if(words[0].equals("v") && words.length == 4)
					{
						// Ajout d'un sommet 3D
						points.add(new Point3D(this.camera, Float.parseFloat(words[1]), Float.parseFloat(words[2]), Float.parseFloat(words[3]) ));
					} else if(words[0].equals("f"))
					{
						// Ajout d'une face
						Integer[] face = new Integer[words.length - 1];
						for(int i = 1; i < words.length; i++)
						{
							face[i - 1] = Integer.parseInt( words[i].split("/")[0] ) - 1;
						}
						faces.add(face);
					}
				}
			}
			return new Model("", points, faces);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Model("Loading Error", new ArrayList<Point3D>(), new ArrayList<Integer[]>());
	}



}