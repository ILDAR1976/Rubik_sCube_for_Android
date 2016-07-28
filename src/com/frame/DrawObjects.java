package com.frame;

import javax.microedition.khronos.opengles.GL11;

import com.figure.BlockR;
import com.libs.Matrix4f;
import com.libs.Point3f;

public interface DrawObjects extends 
    iMovies,
    iRotations {
	public void draw(int Index);
	public Point3f GetMainPoint();
	public void SetPlane(int Plane[]);
	public BlockR[][][] GetCurrentBlocks();
	public Matrix4f GetModelView(GL11 gl);
	public Matrix4f GetProjectionView(GL11 gl);

}
