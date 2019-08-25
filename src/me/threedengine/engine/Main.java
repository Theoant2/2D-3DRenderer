package me.threedengine.engine;

import java.util.stream.IntStream;

import me.threedengine.engine.elements.Model;
import me.threedengine.engine.elements.NoisedLandscape;
import me.threedengine.engine.utils.Loader;
import me.twodengine.engine.WindowContainer;

public class Main {

	private static Model model;
	private static NoisedLandscape landscape;
	
	public static void main(String[] args)
	{
		WindowContainer wc = new WindowContainer();
		wc.setMaxFps(240);
		System.out.println(wc.getRenderer());
		Renderer3D renderer3D = new Renderer3D(wc.getRenderer(), wc.getWidth() / 2, wc.getHeight() / 2);
		//model = new Loader("C:\\Users\\Utilisateur\\Documents\\Processing\\Own3D\\lowpolyhouse\\low-poly-mill.obj", renderer3D.getCamera()).computeModel();
		landscape = NoisedLandscape.generate(renderer3D.getCamera(), 100, 100, NoisedLandscape.DetailedLevel.SQUARE);
		renderer3D.drawOrigin();
		renderer3D.addElement(landscape);
		//renderer3D.rotate((float) Math.PI, 0f, 0f);
		renderer3D.rotate((float) Math.PI, 0f, 0f);
		renderer3D.rotate(-0.5f, 0f, 0f);
		renderer3D.translate(-100f, -10f, -100f);
		renderer3D.scale(2.5f);
		//renderer3D.createCube(-15, -15, -15, 30);
		//renderer3D.createSphere(-15, -15, -15, 30);
		wc.on(WindowContainer.RENDER_EVENT, renderer -> {
			renderer.clear();
			renderer3D.render();
			//renderer3D.rotate(0f, 0.01f, 0f);
			//renderer3D.translate(0f, 0f, 0f);
			//renderer3D.getCamera().translate(0f, 0f, 0f);
			
		});
		wc.on(WindowContainer.UPDATE_EVENT, renderer -> {
			renderer3D.getCamera().rotate(0f, 0.001f, 0f);
			//renderer3D.rotate(0f, 0.001f, 0f);
			//renderer3D.translate(0f, 0f, 0f);
			//renderer3D.getCamera().translate(0f, 0f, 0f);
			
		});
		wc.start();
	}
}
