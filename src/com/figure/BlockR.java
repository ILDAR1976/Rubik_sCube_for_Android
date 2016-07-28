package com.figure;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL11;
import com.iha.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class BlockR extends figure{
	private int[] PlaneColorMask;

	/** The buffer holding the vertices */
	private FloatBuffer vertexBuffer;
	/** The buffer holding the texture coordinates */
	private FloatBuffer textureBuffer;
	/** The buffer holding the indices */
	private ByteBuffer indexBuffer;
	/** The buffer holding the normals */
	private FloatBuffer normalBuffer;

	public float vertices[] = {
			// Vertices according to faces
			-1.0f, -1.0f, 1.0f, //v0
			1.0f, -1.0f, 1.0f, 	//v1
			-1.0f, 1.0f, 1.0f, 	//v2
			1.0f, 1.0f, 1.0f, 	//v3

			1.0f, -1.0f, 1.0f, 	//...
			1.0f, -1.0f, -1.0f, 
			1.0f, 1.0f, 1.0f, 
			1.0f, 1.0f, -1.0f,

			1.0f, -1.0f, -1.0f, 
			-1.0f, -1.0f, -1.0f, 
			1.0f, 1.0f, -1.0f, 
			-1.0f, 1.0f, -1.0f,

			-1.0f, -1.0f, -1.0f, 
			-1.0f, -1.0f, 1.0f, 
			-1.0f, 1.0f, -1.0f, 
			-1.0f, 1.0f, 1.0f,

			-1.0f, -1.0f, -1.0f, 
			1.0f, -1.0f, -1.0f, 
			-1.0f, -1.0f, 1.0f, 
			1.0f, -1.0f, 1.0f,

			-1.0f, 1.0f, 1.0f, 
			1.0f, 1.0f, 1.0f, 
			-1.0f, 1.0f, -1.0f, 
			1.0f, 1.0f, -1.0f, 
								};

	/** The initial normals for the lighting calculations */	
	public float normals[] = {
			//Normals
			0.0f, 0.0f, 1.0f, 						
			0.0f, 0.0f, -1.0f, 
			0.0f, 1.0f, 0.0f, 
			0.0f, -1.0f, 0.0f, 
			
			0.0f, 0.0f, 1.0f, 
			0.0f, 0.0f, -1.0f, 
			0.0f, 1.0f, 0.0f, 
			0.0f, -1.0f, 0.0f,
			
			0.0f, 0.0f, 1.0f, 
			0.0f, 0.0f, -1.0f, 
			0.0f, 1.0f, 0.0f, 
			0.0f, -1.0f, 0.0f,
			
			0.0f, 0.0f, 1.0f, 
			0.0f, 0.0f, -1.0f, 
			0.0f, 1.0f, 0.0f, 
			0.0f, -1.0f, 0.0f,
			
			0.0f, 0.0f, 1.0f, 
			0.0f, 0.0f, -1.0f, 
			0.0f, 1.0f, 0.0f, 
			0.0f, -1.0f, 0.0f,
			
			0.0f, 0.0f, 1.0f, 
			0.0f, 0.0f, -1.0f, 
			0.0f, 1.0f, 0.0f, 
			0.0f, -1.0f, 0.0f,
								};

	/** The initial texture coordinates (u, v) */	
	public float texture[] = {
			//Mapping coordinates for the vertices
			0.0f, 0.0f, 
			0.0f, 1.0f, 
			1.0f, 0.0f, 
			1.0f, 1.0f,

			0.0f, 0.0f,
			0.0f, 1.0f, 
			1.0f, 0.0f,
			1.0f, 1.0f,

			0.0f, 0.0f, 
			0.0f, 1.0f, 
			1.0f, 0.0f, 
			1.0f, 1.0f,

			0.0f, 0.0f, 
			0.0f, 1.0f, 
			1.0f, 0.0f, 
			1.0f, 1.0f,

			0.0f, 0.0f, 
			0.0f, 1.0f, 
			1.0f, 0.0f, 
			1.0f, 1.0f,

			0.0f, 0.0f, 
			0.0f, 1.0f, 
			1.0f, 0.0f, 
			1.0f, 1.0f, 
						};
	
	public byte indices[] = {
			// Faces definition
			0, 1, 3, 0, 3, 2, 		// Face front
			4, 5, 7, 4, 7, 6, 		// Face right
			8, 9, 11, 8, 11, 10, 	// ...
			12, 13, 15, 12, 15, 14, 
			16, 17, 19, 16, 19, 18, 
			20, 21, 23, 20, 23, 22, 
									};

	public byte indices2[][] = {
			// Faces definition
			{0, 1, 3, 0, 3, 2}, 		// Face front
			{4, 5, 7, 4, 7, 6 }, 		// Face right
			{8, 9, 11, 8, 11, 10}, 	// ...
			{12, 13, 15, 12, 15, 14}, 
			{16, 17, 19, 16, 19, 18}, 
			{20, 21, 23, 20, 23, 22}};
	
	public BlockR(GL11 iGL,Context context,float X,float Y,float Z) {
	    super(iGL, context, X, Y, Z);
		LoadGLTextures(gl,context);
	    //
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		//
		byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuf.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
		
		//
		byteBuf = ByteBuffer.allocateDirect(normals.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		normalBuffer = byteBuf.asFloatBuffer();
		normalBuffer.put(normals);
		normalBuffer.position(0);
 	}
	
	@Override
	public void draw(int Index) {
		DrawBlock(gl, PlaneColorMask, Index);
	}
	
	public void Colorer(int b)
	{
		switch (b) {
		case 1:
				gl.glEnable(GL11.GL_TEXTURE_2D);
				gl.glBindTexture(GL11.GL_TEXTURE_2D, Textures[0][0]);
         		break;
		case 2:
				gl.glEnable(GL11.GL_TEXTURE_2D);
     		    gl.glBindTexture(GL11.GL_TEXTURE_2D, Textures[1][0]);
         		break;
		case 3:
				gl.glEnable(GL11.GL_TEXTURE_2D);
				gl.glBindTexture(GL11.GL_TEXTURE_2D, Textures[2][0]);
         		break;
		case 4:
				gl.glEnable(GL11.GL_TEXTURE_2D);
 		    	gl.glBindTexture(GL11.GL_TEXTURE_2D, Textures[3][0]);
         		break;
		case 5:
				gl.glEnable(GL11.GL_TEXTURE_2D);
 		    	gl.glBindTexture(GL11.GL_TEXTURE_2D, Textures[4][0]);
         		break;
		case 6:
			    gl.glEnable(GL11.GL_TEXTURE_2D);
 		    	gl.glBindTexture(GL11.GL_TEXTURE_2D, Textures[5][0]);
         		break;
     	default:
     		    gl.glEnable(GL11.GL_TEXTURE_2D);
 		    	gl.glBindTexture(GL11.GL_TEXTURE_2D, Textures[6][0]);
         		break;
		}
	}
	
	public void DrawBlock(GL11 gl,
						  int[] b,
						  int Index)
	{

		for (int i = 0;i < 6; i++){
	    
	    	//System.out.print((i % 6) + "\t" + b[1]+ "\t" + b[2]+ "\t" + b[3]+  "\n");
			switch (i % 6) {
         	case 0:
         		Colorer(b[3]); //3 - 1
         		break;
         	case 1:
         		Colorer(b[1]); //2 - 2
         		break;
         	case 2:
         		Colorer(b[3]); //1 - 3
         		break;
         	case 3:
         		Colorer(b[1]); //3 - 4
         		break;
         	case 4:
         		Colorer(b[2]); //2 - 5
         		break;
         	case 5:
         		Colorer(b[2]); //1 - 6
         		break;
         	}
	    	Index++;
    		DrawSquare(gl,(i % 6));
	     }
	}
	
	public void SetPlaneColorMask(int[] PCM)
	{
		PlaneColorMask = PCM;
	}

	/**
	 * Load the textures
	 * 
	 * @param gl - The GL Context
	 * @param context - The Activity context
	 */
	@Override
	public void LoadGLTextures(GL11 gl, Context context) {
		Bitmap bitmap = null;

		bitmap = LoadImage(R.drawable.red, context);
		genBindSet(gl, Textures[0], bitmap);

		bitmap = LoadImage(R.drawable.blue, context);
		genBindSet(gl, Textures[1], bitmap);
	
		bitmap = LoadImage(R.drawable.green, context);
		genBindSet(gl, Textures[2], bitmap);

		bitmap = LoadImage(R.drawable.crimson,context);
		genBindSet(gl, Textures[3], bitmap);

		bitmap = LoadImage(R.drawable.white, context);
		genBindSet(gl, Textures[4], bitmap);
		
		bitmap = LoadImage(R.drawable.yellow, context);
		genBindSet(gl, Textures[5], bitmap);

		bitmap = LoadImage(R.drawable.black, context);
		genBindSet(gl, Textures[6], bitmap);
		
	}
	
	/**
	 * Our own MipMap generation implementation.
	 * Scale the original bitmap down, always by factor two,
	 * and set it as new mipmap level.
	 * 
	 * Thanks to Mike Miller (with minor changes)!
	 * 
	 * @param gl - The GL Context
	 * @param bitmap - The bitmap to mipmap
	 */
	private void buildMipmap(GL11 gl, Bitmap bitmap) {
		//
		int level = 0;
		//
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();

		//
		while(height >= 1 || width >= 1) {
			//First of all, generate the texture from our bitmap and set it to the according level
			GLUtils.texImage2D(GL11.GL_TEXTURE_2D, level, bitmap, 0);
			
			//
			if(height == 1 || width == 1) {
				break;
			}

			//Increase the mipmap level
			level++;

			//
			height /= 2;
			width /= 2;
			Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, width, height, true);
			
			//Clean up
			bitmap.recycle();
			bitmap = bitmap2;
		}
	}

	private void genBindSet(GL11 gl,int[] textures, Bitmap bitmap){
		//Generate there texture pointer
		gl.glGenTextures(3, textures, 0);

		//Create Nearest Filtered Texture and bind it to texture 0
		gl.glBindTexture(GL11.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GLUtils.texImage2D(GL11.GL_TEXTURE_2D, 0, bitmap, 0);

		//Create Linear Filtered Texture and bind it to texture 1
		gl.glBindTexture(GL11.GL_TEXTURE_2D, textures[1]);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GLUtils.texImage2D(GL11.GL_TEXTURE_2D, 0, bitmap, 0);

		//Create mipmapped textures and bind it to texture 2
		gl.glBindTexture(GL11.GL_TEXTURE_2D, textures[2]);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
		/*
		 * This is a change to the original tutorial, as buildMipMap does not exist anymore
		 * in the Android SDK.
		 * 
		 * We check if the GL context is version 1.1 and generate MipMaps by flag.
		 * Otherwise we call our own buildMipMap implementation
		 */
		if(gl instanceof GL11) {
			gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
			GLUtils.texImage2D(GL11.GL_TEXTURE_2D, 0, bitmap, 0);
			
		//
		} else {
			buildMipmap(gl, bitmap);
		}		
		
		//Clean up
		bitmap.recycle();
		
	}

	private Bitmap LoadImage(int id, Context context){
		InputStream is = context.getResources().openRawResource(id);
		Bitmap bitmap = null;
		try {
			//BitmapFactory is an Android graphics utility for images
			bitmap = BitmapFactory.decodeStream(is);

		} finally {
			//Always clear and close
			try {
				is.close();
				is = null;
			} catch (IOException e) {
			}
		}
		return bitmap;
	}

	private void DrawSquare(GL11 gl,int Index) {
		//Bind the texture according to the set texture filter
		//gl.glBindTexture(GL11.GL_TEXTURE_2D, Textures[Index][TextureNo]);

		//Enable the vertex, texture and normal state
		
		gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		
		//Set the face rotation
		gl.glFrontFace(GL11.GL_CCW);
		
		//Point to our buffers
		gl.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL11.GL_FLOAT, 0, textureBuffer);
		gl.glNormalPointer(GL11.GL_FLOAT, 0, normalBuffer);
		
		//
		indexBuffer = ByteBuffer.allocateDirect(indices2[Index].length);
		indexBuffer.put(indices2[Index]);
		indexBuffer.position(0);
		
		//Draw the vertices as triangles, based on the Index Buffer information
		gl.glDrawElements(GL11.GL_TRIANGLES, indices2[Index].length, GL11.GL_UNSIGNED_BYTE, indexBuffer);
	
		
		//Disable the client state before leaving
		gl.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		gl.glDisable(GL11.GL_TEXTURE_2D);
	}

	public FloatBuffer getCoordinate(int coord_id) {
		switch (coord_id) {
		case 1:
			return getDirectBuffer(vertices);
		case 2:
			return getDirectBuffer(texture);
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public FloatBuffer getDirectBuffer(float[] buffer) {
		ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer directBuffer = bb.asFloatBuffer();
		directBuffer.put(buffer);
		directBuffer.position(0);
		return directBuffer;
	}

	
}
