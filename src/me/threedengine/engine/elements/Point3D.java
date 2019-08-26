package me.threedengine.engine.elements;

import me.threedengine.engine.Camera;
import me.threedengine.engine.utils.Matrices;
import me.threedengine.engine.utils.Vector3D;
import me.twodengine.engine.Renderer;
import me.twodengine.engine.elements.Point;

public class Point3D implements Renderable {

	  private float x, y, z, w;
	  private Point point2D;
	  private Vector3D translation_vector, rotation_vector;
	  private float scaleValue;
	  private Camera camera;

	  public Point3D(Camera camera, float x, float y, float z)
	  { 
	    this.x = x;
	    this.y = y; 
	    this.z = z; 
	    this.w = 1;
	    
	    this.translation_vector = new Vector3D(0, 0, 0);
	    this.rotation_vector = new Vector3D(0, 0, 0);
	    this.scaleValue = 1;
	    this.camera = camera;
	  }

	  public void mult(float coef)
	  {
	    this.x *= coef;
	    this.y *= coef;
	    this.z *= coef;
	  }

	  public float[][] toMatrix()
	  {
	    return new float[][]{{this.x}, {this.y}, {this.z}, {this.w}};
	  }
	  public float[][] toMatrix(float offsetX, float offsetY, float offsetZ)
	  {
	    return new float[][]{{this.x + offsetX}, {this.y + offsetY}, {this.z + offsetZ}, {this.w}};
	  }
	  public float[][] toMatrix(Vector3D offset)
	  {
	    return new float[][]{{this.x + offset.getX()}, {this.y + offset.getY()}, {this.z + offset.getZ()}, {this.w}};
	  }

	  public void fromMatrix(float[][] matrix)
	  {
	    this.x = matrix[0][0];
	    this.y = matrix[1][0];
	    this.z = matrix[2][0];
	    this.w = matrix[3][0];
	  }

	  public void render(Renderer renderer2D, Camera camera, float offsetX, float offsetY)
	  {
	    System.out.println("Utiliser: public Point compute(double offsetX, double offsetY)");
	  }

	  public Point compute(Camera camera, float offsetX, float offsetY)
	  {
	    this.point2D = this.projectPerspective2D(camera);
	    this.point2D.translate(offsetX, offsetY);
	    return this.point2D;
	  }

	  public Point projectPerspective2D(Camera camera)
	  {
		try
		{
			final float fov = 80;
		    final float NearZ = -1;
		    final float FarZ = 1;
		    
		    /*float[][] translation = new float[][]{
		      {1, 0, 0, 0}, 
		      {0, 1, 0, 0}, 
		      {0, 0, 1, 0}, 
		      {this.translation_vector.x, this.translation_vector.y, this.translation_vector.z, 1}
		    };
		    float[][] translated = matmult(translation, this.toMatrix());
		    println(translated[0][0], translated[1][0], translated[2][0], translated[3][0]);*/
		    float[][] camered = Matrices.matmult(camera.getExtrinsicMatrix(), this.toMatrix(this.translation_vector));
		    float[][] rotated = Matrices.applyRotationMatrix(camered, 
		    												 this.rotation_vector);
		    
		    float[][] scaler = new float[][]{
		      {this.scaleValue, 0, 0, 0}, 
		      {0, this.scaleValue, 0, 0}, 
		      {0, 0, this.scaleValue, 0}, 
		      {0, 0, 0, 1}
		    };
		    float[][] scaled = Matrices.matmult(scaler, rotated);
		    
		    float[][] projection = new float[][]{
		      {(float) (1 / (Math.tan(Math.toRadians(fov / 2.0f)))), 0, 0, 0}, 
		      {0, (float) (1 / Math.tan(Math.toRadians(fov / 2.0f))), 0, 0}, 
		      {0, 0, (-NearZ - FarZ) / (NearZ - FarZ), (2.0f * FarZ * NearZ) / (NearZ - FarZ)}, 
		      {0, 0, 1, 0}
		    };

		    float[][] projected = Matrices.matmult(projection, scaled);
		    
		    //float[][] result = Matrices.add(camera.getExtrinsicMatrix(), projected);
		    
		    //Point point = new Point(result[0][0], result[1][0]);
		    Point point = new Point(projected[0][0], projected[1][0]);
		    return point;
		} catch(Exception e) {
			e.printStackTrace();
		}
	    return null;
	  }

	  public void translate(float x, float y, float z)
	  {
	    this.translation_vector.add(x, y, z);
	  }

	  public void rotateX(float angle)
	  {
	    this.rotation_vector.add(angle, 0, 0);
	  }
	  public void rotateY(float angle)
	  {
	    this.rotation_vector.add(0, angle, 0);
	  }
	  public void rotateZ(float angle)
	  {
	    this.rotation_vector.add(0, 0, angle);
	  }

	  public void rotate(float angleX, float angleY, float angleZ)
	  {
	    this.rotateX(angleX); 
	    this.rotateY(angleY); 
	    this.rotateZ(angleZ);
	  }

	  public void scale(float size)
	  {
	    this.scaleValue += size;
	  }
	  
	  public float getX() { return this.x; }
	  public float getY() { return this.y; }
	  public float getZ() { return this.z; }
	  
	  public String toString() { return this.x + "," + this.y + "," + this.z; }
	}