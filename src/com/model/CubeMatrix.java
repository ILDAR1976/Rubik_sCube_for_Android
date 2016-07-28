package com.model;

public class CubeMatrix {
	public Cube3DMatrix Cube3D; 
	public int plane1[][];
	public int plane2[][];
	public int plane3[][];
	public int plane4[][];
	public int plane5[][];
	public int plane6[][];
	public block CutedBlock[][];
	public block BufferPlane[][];
	public int cm[];
	
	public CubeMatrix()
	{
		Cube3D = new Cube3DMatrix();
		CutedBlock = new block[4][4];
		InitMatrix4x4(CutedBlock);
		cm = new int[3];
		BufferPlane = new block[4][4];
		InitMatrix4x4(BufferPlane);
		inicialize();
	}

    public void Transform(int Plane[][],int f)
    {
        int a = 0; 
        int b = 0;
        int Rotate[][] = new int[2][2];
        int Buffer[][] = new int[3][3];
        
        switch (f*f){
        	case 1:
            	Rotate = new int[][]{ { 0,-1},
                                      { 1, 0}};
        		break;
           	case 4:
            	Rotate = new int[][]{ { 0, 1},
                                      {-1, 0}};
        		break;
           	case 9:
            	Rotate = new int[][]{ {-1, 0},
                                      { 0, 1}};
        		break;
           	case 16:
            	Rotate = new int[][]{ { 1, 0},
                                      { 0,-1}};
        		break;
        }
		
        if (f < 0) {
			a =  3;
			b = -1;
		} else {
			a = -1;
			b =  3;
		}
		
        CopyPlate(Plane,Buffer);
		Plane[Rotate[0][0] + Rotate[0][1]+a][Rotate[1][0] + Rotate[1][1]+b] = Buffer[0][0];
		Plane[Rotate[0][0] + Rotate[0][1]*2+a][Rotate[1][0] + Rotate[1][1]*2+b] = Buffer[0][1];
		Plane[Rotate[0][0] + Rotate[0][1]*3+a][Rotate[1][0] + Rotate[1][1]*3+b] = Buffer[0][2];
		
		Plane[Rotate[0][0]*2 + Rotate[0][1]+a][Rotate[1][0]*2 + Rotate[1][1]+b] = Buffer[1][0];
		Plane[Rotate[0][0]*2 + Rotate[0][1]*2+a][Rotate[1][0]*2 + Rotate[1][1]*2+b] = Buffer[1][1];
		Plane[Rotate[0][0]*2 + Rotate[0][1]*3+a][Rotate[1][0]*2 + Rotate[1][1]*3+b] = Buffer[1][2];
		
		Plane[Rotate[0][0]*3 + Rotate[0][1]+a][Rotate[1][0]*3 + Rotate[1][1]+b] = Buffer[2][0];
		Plane[Rotate[0][0]*3 + Rotate[0][1]*2+a][Rotate[1][0]*3 + Rotate[1][1]*2+b] = Buffer[2][1];
		Plane[Rotate[0][0]*3 + Rotate[0][1]*3+a][Rotate[1][0]*3 + Rotate[1][1]*3+b] = Buffer[2][2];
	
    }
	public void CopyPlate(int a[][],int b[][])
	{
		for (int i = 0;i<3;i++)
			for(int j = 0;j<3;j++)
				b[i][j] = a[i][j];
	}

	public void Copy(block a[][],block b[][])
	{
		for (int i = 0;i<4;i++)
			for(int j = 0;j<4;j++) {
				b[i][j] = a[i][j];
			}
	}
	public void LeftRotatePlane(block Plane[][],block BufferPlane[][],int cm[])
	{
        int Rotate[][] = new int[][]{ {0,-1},
	                                  {1, 0},};
		int aa = 0;
		int bb = 0;
	    Copy(Plane,BufferPlane);
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				if (cm[0] != 0) {
					Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j] = BufferPlane[i][j]; 
				    aa = Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[2];
				    bb = Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[3];
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[2] = bb;
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[3] = aa;
				};

				if (cm[1] != 0) {
					Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j] = BufferPlane[i][j]; 
				    aa = Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[1];
				    bb = Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[3];
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[1] = bb;
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[3] = aa;
				};

				if (cm[2] != 0) {
					Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j] = BufferPlane[i][j]; 
				    aa = Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[1];
				    bb = Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[2];
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[1] = bb;
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j+4][Rotate[1][0]*i + Rotate[1][1]*j].b[2] = aa;
				};
				

			}
		}

	}
	public void RightRotatePlane(block Plane[][],block BufferPlane[][],int cm[])
	{
		int Rotate[][] = new int[][]{ { 0, 1},
                                      {-1, 0},};		
  		int aa = 0;
        int bb = 0;
        //Print2(Plane);
		
        Copy(Plane,BufferPlane);
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				if (cm[0] != 0) {
					Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4] = BufferPlane[i][j]; 
				    aa = Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[2];
				    bb = Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[3];
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[2] = bb;
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[3] = aa;
				}

				if (cm[1] != 0) {
					Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4] = BufferPlane[i][j]; 
				    aa = Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[1];
				    bb = Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[3];
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[1] = bb;
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[3] = aa;
				}

				if (cm[2] != 0) {
					Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4] = BufferPlane[i][j]; 
				    aa = Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[1];
				    bb = Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[2];
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[1] = bb;
				    Plane[Rotate[0][0]*i + Rotate[0][1]*j][Rotate[1][0]*i + Rotate[1][1]*j+4].b[2] = aa;
				}
			}
		}
		//Print2(Plane);
	}
    public void CutSwapLayer(block a[][][],block b[][],final int cm[],int f)
	{
		for (int i = 0;i<4;i++)
			for(int j = 0;j<4;j++)
				for(int k = 0;k<4;k++)
				{
					if ((i > 0) && (j > 0) && (k > 0)) {
						if ((cm[2]*cm[2] == 1) && (k == 1))
							if (f == 0) b[i][j] = a[i][j][k]; else a[i][j][k] = b[i][j];
						if ((cm[2]*cm[2] == 4) && (k == 2))
							if (f == 0) b[i][j] = a[i][j][k]; else a[i][j][k] = b[i][j];
						if ((cm[2]*cm[2] == 9) && (k == 3))
							if (f == 0) b[i][j] = a[i][j][k]; else a[i][j][k] = b[i][j];

						if ((cm[0]*cm[0] == 1) && (i == 1))
							if (f == 0) b[j][k] = a[i][j][k]; else a[i][j][k] = b[j][k];
						if ((cm[0]*cm[0] == 4) && (i == 2))
							if (f == 0) b[j][k] = a[i][j][k]; else a[i][j][k] = b[j][k];
						if ((cm[0]*cm[0] == 9) && (i == 3))
							if (f == 0) b[j][k] = a[i][j][k]; else a[i][j][k] = b[j][k];
						
						if ((cm[1]*cm[1] == 1) && (j == 1))
							if (f == 0) b[i][k] = a[i][j][k]; else a[i][j][k] = b[i][k];
						if ((cm[1]*cm[1] == 4) && (j == 2))
							if (f == 0) b[i][k] = a[i][j][k]; else a[i][j][k] = b[i][k];
						if ((cm[1]*cm[1] == 9) && (j == 3))
							if (f == 0) b[i][k] = a[i][j][k]; else a[i][j][k] = b[i][k];
					}
				}
	//Print2(b);	
	}
	public void Rotate(int icm[])
	{
		CutSwapLayer(Cube3D.Matrix, CutedBlock, icm, 0);
		
		if ((icm[0] < 0) || (icm[1] < 0) || (icm[2] < 0))
			LeftRotatePlane(CutedBlock,BufferPlane,icm);
		else
			RightRotatePlane(CutedBlock,BufferPlane,icm);
		
		CutSwapLayer(Cube3D.Matrix, CutedBlock, icm, 1);
		
	}
	private void fullmatrix(int plane[][],int Color)
	{
		for (int i = 0;i<3;i++)
			for (int j = 0;j<3;j++)
				plane[i][j] = Color; 
	}
	private void InitMatrix4x4(block plane[][])
	{
		for (int i = 0;i<4;i++)
			for (int j = 0;j<4;j++)
				plane[i][j] = new block(0,0,0); 
	}
	private void inicialize()
	{
		block RotateMatrix[][] = new block[4][4];
		InitMatrix4x4(RotateMatrix);
		
		plane1 = new int[3][3];
		fullmatrix(plane1,0);
		plane2 = new int[3][3];
		fullmatrix(plane2,0);
		plane3 = new int[3][3];
		fullmatrix(plane3,0);
		plane4 = new int[3][3];
		fullmatrix(plane4,0);
		plane5 = new int[3][3];
		fullmatrix(plane5,0);
		plane6 = new int[3][3];
		fullmatrix(plane6,0);
	}
	public void PrintCube()
	{
		int VeiwMatrix[][] = new int[12][9];
		
		ProektCube3D(Cube3D.Matrix);
		
		for (int i = 0;i<12;i++) 
			for (int j = 0;j<9;j++)
				VeiwMatrix[i][j] = 0;
		
		for (int i = 0;i<3;i++) 
			for (int j = 0;j<3;j++)
				VeiwMatrix[i][j + 3] = plane1[i][j];
		
		for (int i = 0;i<3;i++) 
			for (int j = 0;j<3;j++)
				VeiwMatrix[i + 3][j] = plane2[i][j];
		
		for (int i = 0;i<3;i++) 
			for (int j = 0;j<3;j++)
				VeiwMatrix[i + 3][j + 3] = plane3[i][j];
		
		for (int i = 0;i<3;i++) 
			for (int j = 0;j<3;j++)
				VeiwMatrix[i + 3][j + 6] = plane4[i][j];
		
		for (int i = 0;i<3;i++) 
			for (int j = 0;j<3;j++)
				VeiwMatrix[i + 6][j + 3] = plane5[i][j];
		
		for (int i = 0;i<3;i++) 
			for (int j = 0;j<3;j++)
				VeiwMatrix[i + 9][j + 3] = plane6[i][j];
		
		for (int i = 0;i<12;i++)
			{
			for (int j = 0;j<9;j++) 
				switch (VeiwMatrix[i][j]){
				
					case 0:
						System.out.print(" ");
						break;
					default:
						System.out.print(VeiwMatrix[i][j]);
						break;
				};
			System.out.print("\n");	
			}
	}
	public void ProektCube3D(block aa[][][])
	{
		for (int i = 0;i<4;i++)
			for (int j = 0;j<4;j++)
				for (int k = 0;k<4;k++)
				{
					if ((i == 1) && (k>0) && (j>0))
					  plane1[j-1][k-1] = aa[i][j][k].b[1];
					if ((i == 3) && (k>0) && (j>0))
					  plane5[j-1][k-1] = aa[i][j][k].b[1];

					if ((j == 1) && (k>0) && (i>0))
					  plane2[i-1][k-1] = aa[i][j][k].b[2];
					if ((j == 3) && (k>0) && (i>0))
					  plane4[i-1][k-1] = aa[i][j][k].b[2];

					if ((k == 1) && (i>0) && (j>0))
						  plane3[i-1][j-1] = aa[i][j][k].b[3];
					if ((k == 3) && (i>0) && (j>0))
					      plane6[i-1][j-1] = aa[i][j][k].b[3];
				}
		Transform(plane1, -1);
		Transform(plane2, 4);
		
		Transform(plane5, 4);
		Transform(plane5, -1);
		
		Transform(plane6, -3);
	}
	public void Print2(block plane[][])
	{
		for (int i = 0;i<4;i++)
		{
			for (int j = 0;j<4;j++)
			{
				if ((i > 0)&&(j >0))
				System.out.print(i + "-" + j + "(" + plane[i][j].b[1] + "-" + plane[i][j].b[2] + "-" + plane[i][j].b[3] + ")" + " ");
			}
			System.out.print("\n");
		}
	}
	public void Print(block A[][][])
	{
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				for (int k = 1; k < 4; k++) {
					System.out.print( "x" + i + "y" + j + "z" + k +
							          "(" + A[i][j][k].b[1] + " " +
							                A[i][j][k].b[2] + " " +     
							                A[i][j][k].b[3] + ") " );
				}
				System.out.print("\n");
			}
			System.out.print("\n");
		}
	}
}
