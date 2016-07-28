package com.model;

public class block {
	public int b[];
	
	public block(int x,int y,int z)
	{
		b = new int[7];
		b[0] = 0;
		b[1] = x;
		b[2] = y;
		b[3] = z;
		b[4] = 0;
		b[5] = 0;
		b[6] = 0;
	}
}
