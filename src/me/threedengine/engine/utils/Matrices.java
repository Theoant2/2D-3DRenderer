package me.threedengine.engine.utils;

public class Matrices {

	public static float[][] matmult(float[][] m1, float[][] m2) throws Exception
	{
		if (m1[0].length != m2.length)
		{
			throw(new Exception("Multiplication impossible (" + m1.length + "," + m1[0].length + ") x (" + m2.length + "," + m2[0].length + ")"));
		}
		int rows = m1.length;
		int cols = m2[0].length;
		float[][] result = new float[rows][cols];

		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				float sum = 0;
				for (int k = 0; k < m1[0].length; k++)
				{
					sum += m1[i][k] * m2[k][j];
				}
				result[i][j] = sum;
			}
		}
		return result;
	}

	public static float[][] add(float matrix1[][], float matrix2[][]) throws Exception 
	{ 
		if(matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length)
		{
			throw(new Exception("Addition de matrices impossible (" + matrix1.length + "," + matrix1[0].length + ") + (" + matrix2.length + "," + matrix2[0].length + ")"));
		}
		float[][] result = new float[matrix1.length][matrix1[0].length];
		for (int i = 0; i < matrix1.length; i++) 
			for (int j = 0; j < matrix1[0].length; j++) 
				result[i][j] = matrix1[i][j] + matrix2[i][j]; 
		return result;
	} 

	public static float[][] transpose(float matrix[][]) throws Exception 
	{ 
		if(matrix.length != matrix[0].length)
		{
			throw(new Exception("Transposition de matrice impossible (" + matrix.length + "," + matrix[0].length + ")"));
		}
		float[][] result = new float[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++) 
			for (int j = 0; j < matrix[0].length; j++) 
				result[i][j] = matrix[j][i]; 
		return result;
	} 

	public static float[][] applyRotationMatrix(float[][] entry, float x, float y, float z)
	{
		try
		{
			float[][] rotateX = new float[][]{
				{1, 0, 0, 0}, 
				{0, (float) Math.cos(x), (float) -Math.sin(x), 0}, 
				{0, (float) Math.sin(x), (float) Math.cos(x), 0}, 
				{0, 0, 0, 1}
			};
			float[][] rotatedX = Matrices.matmult(rotateX, entry);

			float[][] rotateY = new float[][]{
				{(float) Math.cos(y), 0, (float) Math.sin(y), 0}, 
				{0, 1, 0, 0}, 
				{(float) -Math.sin(y), 0, (float) Math.cos(y), 0}, 
				{0, 0, 0, 1}
			};
			float[][] rotatedY = Matrices.matmult(rotateY, rotatedX);

			float[][] rotateZ = new float[][]{
				{(float) Math.cos(z), (float) -Math.sin(z), 0, 0}, 
				{(float) Math.sin(z), (float) Math.cos(z), 0, 0}, 
				{0, 0, 1, 0}, 
				{0, 0, 0, 1}
			};
			float[][] rotatedZ = Matrices.matmult(rotateZ, rotatedY);

			return rotatedZ;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static float[][] applyRotationMatrix(float[][] entry, Vector3D rotation)
	{
		return Matrices.applyRotationMatrix(entry, rotation.getX(), rotation.getY(), rotation.getZ());
	}

	public static void println(float[][] matrix)
	{
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length - 1; j++)
			{
				System.out.print(matrix[i][j] + " , ");
			}
			System.out.print(matrix[i][matrix[i].length - 1]);
			System.out.println();
		}
		System.out.println();
	}

	// Pris d'Internet
	public static float[][] invert(float a[][]) 
	{
		int n = a.length;
		float x[][] = new float[n][n];
		float b[][] = new float[n][n];
		int index[] = new int[n];
		for (int i=0; i<n; ++i) 
			b[i][i] = 1;

		// Transform the matrix into an upper triangle
		gaussian(a, index);

		// Update the matrix b[i][j] with the ratios stored
		for (int i=0; i<n-1; ++i)
			for (int j=i+1; j<n; ++j)
				for (int k=0; k<n; ++k)
					b[index[j]][k]
							-= a[index[j]][i]*b[index[i]][k];

		// Perform backward substitutions
		for (int i=0; i<n; ++i) 
		{
			x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
			for (int j=n-2; j>=0; --j) 
			{
				x[j][i] = b[index[j]][i];
				for (int k=j+1; k<n; ++k) 
				{
					x[j][i] -= a[index[j]][k]*x[k][i];
				}
				x[j][i] /= a[index[j]][j];
			}
		}
		return x;
	}

	// Method to carry out the partial-pivoting Gaussian
	// elimination.  Here index[] stores pivoting order.

	public static void gaussian(float a[][], int index[]) 
	{
		int n = index.length;
		float c[] = new float[n];

		// Initialize the index
		for (int i=0; i<n; ++i) 
			index[i] = i;

		// Find the rescaling factors, one from each row
		for (int i=0; i<n; ++i) 
		{
			float c1 = 0;
			for (int j=0; j<n; ++j) 
			{
				float c0 = Math.abs(a[i][j]);
				if (c0 > c1) c1 = c0;
			}
			c[i] = c1;
		}

		// Search the pivoting element from each column
		int k = 0;
		for (int j=0; j<n-1; ++j) 
		{
			double pi1 = 0;
			for (int i=j; i<n; ++i) 
			{
				double pi0 = Math.abs(a[index[i]][j]);
				pi0 /= c[index[i]];
				if (pi0 > pi1) 
				{
					pi1 = pi0;
					k = i;
				}
			}

			// Interchange rows according to the pivoting order
			int itmp = index[j];
			index[j] = index[k];
			index[k] = itmp;
			for (int i=j+1; i<n; ++i) 	
			{
				float pj = a[index[i]][j]/a[index[j]][j];

				// Record pivoting ratios below the diagonal
				a[index[i]][j] = pj;

				// Modify other elements accordingly
				for (int l=j+1; l<n; ++l)
					a[index[i]][l] -= pj*a[index[j]][l];
			}
		}
	}
}
