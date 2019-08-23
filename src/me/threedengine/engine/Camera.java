package me.threedengine.engine;

import me.threedengine.engine.utils.Matrices;
import me.threedengine.engine.utils.Vector3D;

public class Camera {

	private Vector3D location;
	private Vector3D rotation;
	
	private float[][] extrinsic_matrix;
	
	private float canvasWidth, canvasHeight;
	
	public Camera(float x, float y, float z, float canvasWidth, float canvasHeight)
	{
		this.location = new Vector3D(x, y, z);
		this.rotation = new Vector3D(0, 0, 0);
		
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		
	}
	
	public void translate(float x, float y, float z) { this.location.add(x, y, z); }
	public void translate(Vector3D translation) { this.location.add(translation); }
	public void rotate(float x, float y, float z) {	this.rotation.add(x, y, z); }
	public void rotate(Vector3D rotation) {	this.rotation.add(rotation); }
	
	public float[][] toMatrix()
	{
		float[][] matrix = new float[][] {
			{1, 0, 0, this.getX()},
			{0, 1, 0, this.getY()},
			{0, 0, 1, this.getZ()},
			{0, 0, 0, 1}
		};
		
		return Matrices.applyRotationMatrix(matrix, this.rotation);
	}
	
	public float[][] generateExtrinsicMatrix()
	{
		try
		{
			//System.out.println(this.rotation);
			float[][] identity = new float[][] {
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
			};
			float[][] R = Matrices.applyRotationMatrix(identity, this.rotation);
			R[0][3] = this.location.getX();
			R[1][3] = this.location.getY();
			R[2][3] = this.location.getZ();
			
			float[][] result = Matrices.invert(R);
			/*float[][] R_t = Matrices.transpose(R);
			
			float[][] R_tC = Matrices.matmult(R_t, new float[][] {{this.getX()}, {this.getY()}, {this.getZ()}, {1}});
			float[][] minus_R_tC = Matrices.matmult(new float[][] {{-1, 0, 0, 0},
												{0, -1, 0, 0},
												{0, 0, -1, 0},
												{0, 0, 0, -1}}, R_tC);
			float[][] result = new float[][]{
				{R_t[0][0], R_t[0][1], R_t[0][2], minus_R_tC[0][0]},
				{R_t[1][0], R_t[1][1], R_t[1][2], minus_R_tC[1][0]},
				{R_t[2][0], R_t[2][1], R_t[2][2], minus_R_tC[2][0]},
				{0, 0, 0, 1}
			};
			
			float[][] left = new float[][]{
				{R_t[0][0], R_t[0][1], R_t[0][2], 0},
				{R_t[1][0], R_t[1][1], R_t[1][2], 0},
				{R_t[2][0], R_t[2][1], R_t[2][2], 0},
				{0, 0, 0, 1}
			};
			
			float[][] right = new float[][]{
				{1, 0, 0, -this.getX()},
				{0, 1, 0, -this.getY()},
				{0, 0, 1, -this.getZ()},
				{0, 0, 0, 1}
			};
			
			float[][] result = Matrices.matmult(left, right);*/
			
			this.extrinsic_matrix = result;
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return this.extrinsic_matrix;
		
	}
	
	public Vector3D getRotation() {
		return this.rotation;
	}
	public float getRotationX() {
		return this.rotation.getX();
	}
	public float getRotationY() {
		return this.rotation.getY();
	}
	public float getRotationZ() {
		return this.rotation.getZ();
	}
	
	public Vector3D getPosition() {
		return this.location;
	}
	public float getX() {
		return this.location.getX();
	}
	public float getY() {
		return this.location.getY();
	}
	public float getZ() {
		return this.location.getZ();
	}
	
	public float[][] getExtrinsicMatrix() {
		return this.extrinsic_matrix;
	}

	public float getCanvasWidth() {
		return canvasWidth;
	}

	public float getCanvasHeight() {
		return canvasHeight;
	}
}
