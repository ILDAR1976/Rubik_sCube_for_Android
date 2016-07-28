package com.figure;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL11;

public class Line {
	
	public Line(){
	}; 
	
	private FloatBuffer newFloatBuffer(int numElements) {
		ByteBuffer bb = ByteBuffer.allocateDirect(numElements * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.position(0);
		return fb;
	}
	public void draw(GL11 gl,float x, float y, float z, float ex, float ey, float ez,float r, float g, float b) {
		gl.glPushMatrix();
		gl.glDisable(GL11.GL_TEXTURE_2D);

		gl.glDisable(GL11.GL_DEPTH_TEST);
		gl.glLineWidth(2.0f);
		gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		FloatBuffer fb = newFloatBuffer(3 * 2);
		fb.put(new float[] { x, y, z, ex, ey, ez });
		fb.position(0);


		gl.glColor4f(r, g, b, 0.6f);
		gl.glVertexPointer(3, GL11.GL_FLOAT, 0, fb);

		gl.glDrawArrays(GL11.GL_LINES, 0, 2);
	
		gl.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		gl.glLineWidth(1.0f);
		gl.glEnable(GL11.GL_DEPTH_TEST);
		gl.glEnable(GL11.GL_TEXTURE_2D);
		gl.glPopMatrix();

	}

}
