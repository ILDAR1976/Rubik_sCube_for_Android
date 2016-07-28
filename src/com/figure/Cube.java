package com.figure;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL11;

import com.model.CubeMatrix;
import com.raypick.AppConfig;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Cube extends figure{
	private int plane[];
	private CubeMatrix CM = null;
	private BlockR[][][] mBlock = null;
	public float[] vertices;
	public byte[] indices;
	
	public Cube(GL11 iGL, Context context, float iX, float iY, float iZ) {
		super(iGL, context, iX, iY, iZ);
		
	  	CM = new CubeMatrix();
	  	//CM.PrintCube();
	  	//CM.Print(CM.Cube3D.Matrix);
	  	plane = new int[3];
	  	
	  	mBlock = this.GetCurrentBlocks();
	  	
	    for (int i = 1; i < 4; i++) {
	      for (int j = 1; j < 4; j++) {
	    	  for (int k = 1; k < 4; k++) {
	    		  
	    		  mBlock[i][j][k] = new BlockR(gl,
	    				  context,
	    				  (float)(i * 2 - 4f),
	    				  (float)(j * 2 - 4f),
	    				  (float)(k * 2 - 4f)
	    				  );
	    	  }
	      }
		}
	  	
	    vertices = mBlock[1][1][1].vertices;
		indices = mBlock[1][1][1].indices;

		
	}
	
	@Override
	public void draw(int Index) {
	    boolean flag = false;
	    gl.glPushMatrix();     
	    MainPoint = GetMove();
	    gl.glTranslatef(MainPoint.x(), MainPoint.y(), MainPoint.z()); 
	    RotationCube();
	      gl.glPushMatrix();
		      if ((plane[0] == -1)||(plane[0] == -2)||(plane[0] == -3)) {
				RotateX();
			  }
		      if ((plane[0] == 1)||(plane[0] == 2)||(plane[0] == 3)) {
				RotateNX();
			  }
	
		      if ((plane[1] == -1)||(plane[1] == -2)||(plane[1] == -3)) {
				RotateY();
			  }
		      if ((plane[1] == 1)||(plane[1] == 2)||(plane[1] == 3)) {
				RotateNY();
			  }
	
		      if ((plane[2] == -1)||(plane[2] == -2)||(plane[2] == -3)) {
				RotateZ();
			  }
		      if ((plane[2] == 1)||(plane[2] == 2)||(plane[2] == 3)) {
				RotateNZ();
			  }
		      boolean GAF = GetActionFlag();
		      //System.out.println(GAF);
		      if ((GAF) && ((plane[0]!=0)|| (plane[1]!=0) || (plane[2]!=0))) {
		    	  plane[1] = -plane[1];
		    	  CM.Rotate(plane);
		    	  for (int i = 0; i < 3; i++) plane[i] = 0;
				  //CM.Print(CM.Cube3D.Matrix);
				  StopRotate();
				
		      }
	          if ((plane[0] != 0)||(plane[1] != 0)||(plane[2] != 0) && !GAF) {StartRotate();};
	  
	          ReleaseRotation();
	          
	          AppConfig.M = GetModelView(gl);
	    	  AppConfig.P = GetProjectionView(gl);

		      ShowMatrix(gl, flag,Index,plane,true);
	      gl.glPopMatrix();
	      
	      gl.glPushMatrix();
 	      	  ShowMatrix(gl, flag,Index,plane,false);
	      gl.glPopMatrix();     
	    
	    gl.glPopMatrix();     
	}
	
	public void ShowMatrix(GL11 gl, boolean flag,int Index,int[] plane,boolean lights)
	{
	      for (int i = 1; i < 4; i++) {
	    	  for (int j = 1; j < 4; j++) {
	    		  for (int k = 1; k < 4; k++) {
	    			    
	    			    if (i * i == plane[0] * plane[0] ) flag = true;
	    			    if (j * j == plane[1] * plane[1] ) flag = true;
	    			    if (k * k == plane[2] * plane[2] ) flag = true;
	    			    
	    			    if ((lights)?flag:!flag) {
		    			    Index = (int)(i * 1000 + j * 100 + k * 10);
		    			    gl.glPushMatrix();
	 	    			    gl.glTranslatef(mBlock[i][j][k].MainPoint.x(),
	 	    			    				mBlock[i][j][k].MainPoint.y(),
	 	    			    				mBlock[i][j][k].MainPoint.z()
	    			    		            );
	 	    			    mBlock[i][j][k].DrawBlock(gl, CM.Cube3D.Matrix[i][j][k].b, Index);
		    			    gl.glPopMatrix();
	    			    }
	    			    flag = false;
	    		  }
	    	  }
		  } 
	}
	
	public void SetPlane(int Plane[])
	{
		if (!GetActionFlag())
		for (int i = 0; i < 3; i++) plane[i] = Plane[i]; 
	}

	public void RotationCube()
	{
        gl.glRotatef(Behavior.AR[0], 1.0f, 0.0f, 0.0f); // rotate about the x-axis
        gl.glRotatef(Behavior.AR[1], 0.0f, 1.0f, 0.0f); // rotate about the y-axis
        gl.glRotatef(Behavior.AR[2], 0.0f, 0.0f, 1.0f); // rotate about the z-axis
	}


}

