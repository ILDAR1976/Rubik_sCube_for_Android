package com.model;

public class Cube3DMatrix {
	public block Matrix[][][];
	public Cube3DMatrix()
	{
		Matrix = new block[4][4][4];
		inicialize();
	}
	private void inicialize()
	{
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					Matrix[i][j][k] = new block(0,0,0);
				}
			}
		}
		
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				for (int k = 1; k < 4; k++) {
					if ((i > 0) && (j > 0) && (k > 0)) 
					{
					    switch (i){
					    case 1: 
					    	Matrix[i][j][k].b[1] = 3;
					    	break;
					    case 2: 
					    	Matrix[i][j][k].b[1] = 0;
					    	break;
					    case 3: 
					    	Matrix[i][j][k].b[1] = 4;
					    	break;
					    }
					    
					    switch (j){
					    case 1: 
					    	Matrix[i][j][k].b[2] = 5;
					    	break;
					    case 2: 
					    	Matrix[i][j][k].b[2] = 0;
					    	break;
					    case 3: 
					    	Matrix[i][j][k].b[2] = 6;
					    	break;
					    }

					    switch (k){
					    case 1: 
					    	Matrix[i][j][k].b[3] = 1;
					    	break;
					    case 2: 
					    	Matrix[i][j][k].b[3] = 0;
					    	break;
					    case 3: 
					    	Matrix[i][j][k].b[3] = 2;
					    	break;
					    }

					}
				}
			}
		}
	}
}
