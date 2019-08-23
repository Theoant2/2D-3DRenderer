package me.threedengine.engine.elements;

import me.threedengine.engine.Camera;
import me.twodengine.engine.Renderer;

public interface Renderable<T> {
	public void render(Renderer renderer2D, Camera camera, float offsetX, float offsetY);
	public void translate(float x, float y, float z);
	public void rotate(float x, float y, float z);
	public void scale(float size);
}
