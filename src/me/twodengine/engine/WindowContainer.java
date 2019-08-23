package me.twodengine.engine;

import java.util.function.Consumer;

public class WindowContainer implements Runnable {

	private Thread thread;
	private Window window;
	private Renderer renderer;
	
	private boolean running = false;
	private double maxFps = 144;
	private double UPDATE_CAP = 1.0 / this.maxFps;
	
	private int width = 1200, height = 800;
	private float scale = 1f;
	private String title = "2D Engine";
	
	public static String RENDER_EVENT = "RENDER";
	public static String UPDATE_EVENT = "UPDATE";
	
	private Consumer<Renderer> render_event;
	private Consumer<Renderer> update_event;

	public WindowContainer()
	{
		window = new Window(this);
		renderer = new Renderer(this);
	}
	
	public void start()
	{
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop()
	{
		
	}
	
	@Override
	public void run() 
	{
		running = true;
		boolean render = false;
		
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		while(running)
		{
			render = false;
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while(unprocessedTime >= UPDATE_CAP)
			{
				unprocessedTime -= UPDATE_CAP;
				render = true;
				
				// Update
				if(update_event != null)
					update_event.accept(renderer);
				
				if(frameTime >= 1.0)
				{
					frameTime = 0;
					fps = frames;
					frames = 0;
					System.out.println("FPS: " + fps);
				}
			}
			
			if(render)
			{
				// Render
				if(render_event != null)
					render_event.accept(renderer);
				window.update();
				frames++;
			} else 
			{
				try 
				{
					Thread.sleep(1);
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
		dispose();
	}
	
	public void dispose()
	{
		
	}
	
	public void on(String name, Consumer<Renderer> func)
	{
		switch(name.toUpperCase())
		{
		case "RENDER":
		{
			System.out.println("aaaaaaaaa");
			this.render_event = func;
			break;
		}
		case "UPDATE":
		{
			System.out.println("aaaaaaaaa");
			this.update_event = func;
			break;
		}
		}
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Window getWindow() {
		return window;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public double getMaxFps() {
		return maxFps;
	}

	public void setMaxFps(double maxFps) {
		this.maxFps = maxFps;
		this.UPDATE_CAP = 1.0 / this.maxFps;
	}

	
}
