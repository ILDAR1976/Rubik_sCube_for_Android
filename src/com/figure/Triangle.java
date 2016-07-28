package com.figure;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11;

import com.libs.Vector3f;

public class Triangle {

    private FloatBuffer mFVertexBuffer;
    private ByteBuffer mIndexBuffer;

    public Triangle() {

	float vertices[] = {
		-0.5f, -0.29f, -1f, 
		0.5f, -0.29f, -1f, 
		0f, 0.58f, -1f 
    }; 

	byte indices[] = { 0, 1, 2 };

	mFVertexBuffer = makeFloatBuffer(vertices);

	mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
	mIndexBuffer.put(indices);
	mIndexBuffer.position(0);
    }
    
    public Triangle(Vector3f v0, Vector3f v1, Vector3f v2) {

    	float vertices[] = {
    		v0.x, v0.y, v0.z, 
    		v1.x, v1.y, v1.z, 
    		v2.x, v2.y, v2.z 
        }; 

    	byte indices[] = { 0, 1, 2 };

    	mFVertexBuffer = makeFloatBuffer(vertices);

    	mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
    	mIndexBuffer.put(indices);
    	mIndexBuffer.position(0);
	
    }

    public void draw(GL11 gl) {
    	gl.glPushMatrix();
    		gl.glDisable(GL11.GL_TEXTURE_2D);
    		gl.glColor4f(1f, 1f, 1f, 0.8f);
    	  	gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
    	    
    		gl.glVertexPointer(3, GL11.GL_FLOAT, 0, mFVertexBuffer);
	    	gl.glDrawElements(GL11.GL_TRIANGLES, 3, GL11.GL_UNSIGNED_BYTE, mIndexBuffer);
	     	gl.glDisableClientState(GL11.GL_VERTEX_ARRAY);
	        gl.glEnable(GL11.GL_TEXTURE_2D);
	    gl.glPopMatrix();
    }

    public void draw(GL11 gl,Vector3f v0,Vector3f v1,Vector3f v2) {
    	set(v0,v1,v2);
    	gl.glPushMatrix();
	    	gl.glDisable(GL11.GL_TEXTURE_2D);
	    	gl.glColor4f(1f, 1f, 1f, 0.7f);
	    	gl.glEnable(GL11.GL_BLEND);
			gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			gl.glDisable(GL11.GL_DEPTH_TEST);
	    	
		  	gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		  	gl.glVertexPointer(3, GL11.GL_FLOAT, 0, mFVertexBuffer);
	    	gl.glDrawElements(GL11.GL_TRIANGLES, 3, GL11.GL_UNSIGNED_BYTE, mIndexBuffer);
	     	gl.glDisableClientState(GL11.GL_VERTEX_ARRAY);
			
	     	gl.glEnable(GL11.GL_DEPTH_TEST);
			gl.glDisable(GL11.GL_BLEND);
	     	gl.glEnable(GL11.GL_TEXTURE_2D);
    	gl.glPopMatrix();
    }
    
    public void draw(GL11 gl,Vector3f v0,Vector3f v1,Vector3f v2,float r, float g, float b) {
    	set(v0,v1,v2);
    	gl.glPushMatrix();
	    	gl.glDisable(GL11.GL_TEXTURE_2D);
	    	gl.glColor4f(r, g, b, 0.7f);
	    	gl.glEnable(GL11.GL_BLEND);
			gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			gl.glDisable(GL11.GL_DEPTH_TEST);
		  	
			gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		  	gl.glVertexPointer(3, GL11.GL_FLOAT, 0, mFVertexBuffer);
	    	gl.glDrawElements(GL11.GL_TRIANGLES, 3, GL11.GL_UNSIGNED_BYTE, mIndexBuffer);
	     	gl.glDisableClientState(GL11.GL_VERTEX_ARRAY);
	    	
	     	gl.glEnable(GL11.GL_DEPTH_TEST);
			gl.glDisable(GL11.GL_BLEND);
	     	gl.glEnable(GL11.GL_TEXTURE_2D);
    	gl.glPopMatrix();
    }

    
    private static FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
	return fb;
    }

    public void set(Vector3f v0, Vector3f v1, Vector3f v2) {

    	float vertices[] = {
    		v0.x, v0.y, v0.z, 
    		v1.x, v1.y, v1.z, 
    		v2.x, v2.y, v2.z 
        }; 
    	byte indices[] = { 0, 1, 2 };

    	mFVertexBuffer = makeFloatBuffer(vertices);

    	mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
    	mIndexBuffer.put(indices);
    	mIndexBuffer.position(0);
 	
    }

}
