package com.iha;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.figure.BlockR;
import com.figure.Cube;
import com.figure.Line;
import com.figure.Triangle;
import com.figure.figure;
import com.frame.DrawObjects;
import com.libs.Matrix4f;
import com.raypick.AppConfig;
import com.raypick.PickFactory;
import com.libs.Ray;
import com.libs.Vector3f;
import com.libs.Vector4f;
import com.model.CubeMatrix;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.opengl.GLSurfaceView.Renderer;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class Rubik extends GLSurfaceView implements Renderer {
	private int CurrentObject = 0;
	private int OldObject = 0;
	private int Direct[] = new int[3];
	private float[] AR = new float[3];
	private ArrayList<DrawObjects> Figures = new ArrayList<DrawObjects>();
	private int[] surface = new int[4];
	private float min = 1000f;
	private Line line = null;
	private BlockR[][][] mBlock = null;
	/** Figures instance */
	private Ray ray;
	private Triangle triangle;
	
	/* Rotation values */
	private float xrot;					//X Rotation
	private float yrot;					//Y Rotation

	/* Rotation speed values */
	private float xspeed;				//X Rotation Speed
	private float yspeed;				//Y Rotation Speed
	
	private float z = -19.0f;			//Depth Into The Screen
	
	private int filter = 0;				//Which texture filter?
	
	/** Is light enabled */
	private boolean light = false;
	/** Is blending enabled ( NEW ) */
	private boolean blend = false;

	/* The initial light values */
	private float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f};
	private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
	private float[] lightPosition = {0.0f, 0.0f, 2.0f, 1.0f};
		
	/* The buffers for our light values */
	private FloatBuffer lightAmbientBuffer;
	private FloatBuffer lightDiffuseBuffer;
	private FloatBuffer lightPositionBuffer;

	/* Variables and factor for the input handler */
	private float oldX;
    private float oldY;
	private final float TOUCH_SCALE = 0.2f;			//Proved to be good for normal rotation
	//First triangle
	private Vector3f v00 = null,v01 = null,v02 = null;
	/** The Activity Context */
	private Context context;
	private static Vector4f location = new Vector4f();
	private Vector3f[] A = new Vector3f[6];
	private Vector3f[] B = new Vector3f[6];
	
	private Cube mCube[][][] = new Cube[4][4][4];
	
	//The picker calculate variables
	private int a,b,c,d,oa,ob,oc,od,Da,Db,Dc,Dd;
	
	public Rubik(Context context) {
		super(context);
	
		//Set this as Renderer
		this.setRenderer(this);
		//Request focus, otherwise buttons won't react
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		
		//
		this.context = context;		
	
		//
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(lightAmbient.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightAmbientBuffer = byteBuf.asFloatBuffer();
		lightAmbientBuffer.put(lightAmbient);
		lightAmbientBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(lightDiffuse.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightDiffuseBuffer = byteBuf.asFloatBuffer();
		lightDiffuseBuffer.put(lightDiffuse);
		lightDiffuseBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(lightPosition.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightPositionBuffer = byteBuf.asFloatBuffer();
		lightPositionBuffer.put(lightPosition);
		lightPositionBuffer.position(0);
		
		triangle = new Triangle();
		line = new Line();
		for (int cc = 0; cc < 6; cc++) {
			  A[cc] = new Vector3f(0f,0f,0f);
			  B[cc] = new Vector3f(0f,0f,0f);
		}
		
		AppConfig.select = true;
	}

	/**
	 * The Surface is created/init()
	 */
	public void onSurfaceCreated(GL10 gl10, EGLConfig config) {		
		GL11 gl = (GL11) gl10;
		//And there'll be light!
		
		gl.glLightfv(GL11.GL_LIGHT0, GL11.GL_AMBIENT, lightAmbientBuffer);		//Setup The Ambient Light
		gl.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuseBuffer);		//Setup The Diffuse Light
		gl.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPositionBuffer);	//Position The Light
		gl.glEnable(GL11.GL_LIGHT0);											//Enable Light 0
	
		//Blending
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1f);				//Full Brightness. 50% Alpha ( NEW )
		gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);		//Set The Blending Function For Translucency ( NEW )
		//gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


		//Settings
		gl.glEnable(GL11.GL_DITHER);				//Disable dithering
		gl.glEnable(GL11.GL_TEXTURE_2D);			//Enable Texture Mapping
		gl.glShadeModel(GL11.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearColor(1.0f, 1.0f, 1.0f, .5f); 	//White Background
		gl.glClearDepthf(10.0f); 					//Depth Buffer Setup
		
		gl.glEnable(GL11.GL_CULL_FACE);
		gl.glCullFace(GL11.GL_BACK);
		
		
		gl.glEnable(GL11.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL11.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		
		//Really Nice Perspective Calculations
		gl.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); 
				
	   AR[0] =  AR[1] =  AR[2] = 0;
	   //AR[0] = AR[1] = 45f;
	   
	   Direct[0] = Direct[1] = Direct[2] = 0;	
	   
	   Figures.add(new Cube(gl, context, 0.0f, 0.0f, 0.0f));
	   //cube = new Cube(gl,context,0f,0f,0f);	
	   //block = new BlockR(gl, context, 0f, 0f, 0f);	
	   mBlock = Figures.get(0).GetCurrentBlocks();
	}

	/**
	 * Here we do our drawing
	 */
	public void onDrawFrame(GL10 gl10) {
		GL11 gl = (GL11) gl10;
		//Clear Screen And Depth Buffer
		gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		gl.glLoadIdentity(); //Reset The Current Modelview Matrix
		
		AppConfig.gMatProject = GetProjectionView(gl);
		AppConfig.gMatProject.fillFloatArray(AppConfig.gpMatrixProjectArray);
		
		PickFactory.update(AppConfig.gScreenX, AppConfig.gScreenY);
		ray = PickFactory.getPickRay();
		
		DrawModel(gl);
		
		PickerUpdate(gl);

		//Change rotation factors
		xrot += xspeed;
		yrot += yspeed;
	}

	/**
	 * If the surface changes, reset the view
	 */
	public void onSurfaceChanged(GL10 gl10, int width, int height) {
		GL11 gl = (GL11) gl10;
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		AppConfig.gpViewport[0] = 0;
		AppConfig.gpViewport[1] = 0;
		AppConfig.gpViewport[2] = width;
		AppConfig.gpViewport[3] = height;
		
		gl.glMatrixMode(GL11.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix

		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);

		//AppConfig.gMatProject.fillFloatArray(AppConfig.gpMatrixProjectArray);
		
		gl.glMatrixMode(GL11.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
	}
	
/* ***** Listener Events ***** */	
	/**
	 * Override the key listener to receive keyUp events.
	 * 
	 * Check for the DPad presses left, right, up, down and middle.
	 * Change the rotation speed according to the presses
	 * or change the texture filter used through the middle press.
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//
		if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			yspeed -= 0.1f;
			
		} else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			yspeed += 0.1f;
			
		} else if(keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			xspeed -= 0.1f;
			
		} else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			xspeed += 0.1f;
			
		} else if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			filter += 1;
			if(filter > 2) {
				filter = 0;
			}
		}

		//We handled the event
		return true;
	}
	
	/**
	 * Override the touch screen listener.
	 * 
	 * React to moves and presses on the touchscreen.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//
		float x = event.getX();
        float y = event.getY();
        
        //If a touch is moved on the screen
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
        	//Calculate the change
        	float dx = x - oldX;
	        float dy = y - oldY;
        	//Define an upper area of 10% on the screen
        	int upperArea = this.getHeight() / 10;
        	
        	//Zoom in/out if the touch move has been made in the upper
        	if(y < upperArea) {
        		z -= dx * TOUCH_SCALE / 2;
        	
        	//Rotate around the axis otherwise
        	} else {        		
    	        xrot += dy * TOUCH_SCALE;
    	        yrot += dx * TOUCH_SCALE;
        	}        
        
        //A press on the screen
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
         	AppConfig.setTouchPosition(x, y);
        }
        
        //Remember the values
        oldX = x;
        oldY = y;
        
        //We handled the event
		return true;
	}

	private Matrix4f GetProjectionView(GL11 gl){
		Matrix4f matrix = new Matrix4f();
		float[] model_matrix = new float[16];
		gl.glGetFloatv(GL11.GL_PROJECTION_MATRIX, model_matrix, 0);	
		//PrintMatrix(model_matrix);
		matrix.fill(model_matrix);
		return matrix;
	}

	public void PrintMatrix(float[] mvm){
	   // Log.i("M",matrixToString(null, "","%10.10f", mvm, 0, 4, 4, false).toString());
	   // Log.i("M","--------------------");
	}
	
	public static StringBuilder matrixToString(StringBuilder sb, final String rowPrefix, final String f,
              final float[] a, final int aOffset, final int rows, final int columns, final boolean rowMajorOrder) {
		  	if(null == sb) {
		  		sb = new StringBuilder();
		  	}
		  	final String prefix = ( null == rowPrefix ) ? "" : rowPrefix;
		  	for(int i=0; i<rows; i++) {
		  		sb.append(prefix).append("[ ");
		  		matrixRowToString(sb, f, a, aOffset, rows, columns, rowMajorOrder, i);
		  		sb.append("]").append(System.getProperty("line.separator"));
		  	}
		  	return sb;
	  }
	  
	public static StringBuilder matrixRowToString(StringBuilder sb, final String f,
              final float[] a, final int aOffset, final int rows, final int columns, final boolean rowMajorOrder, final int row) {
		if(null == sb) {
			sb = new StringBuilder();
		}
		if(rowMajorOrder) {
			for(int c=0; c<columns; c++) {
				sb.append( String.format( f+" ", a[ aOffset + row*columns + c ] ) );
			}
		} else {
			for(int r=0; r<columns; r++) {
				sb.append( String.format( f+" ", a[ aOffset + row + r*rows ] ) );
			}
		}
		return sb;
	}
	
	private Vector3f getVector3f(BlockR cube, int start) {
		return new Vector3f(cube.vertices[3 * start], cube.vertices[3 * start + 1],
				cube.vertices[3 * start + 2]);
	}

	private float distantion(Ray r, Vector3f tv0, Vector3f tv1, Vector3f tv2){
		float out = 0f;
		float dx,dy,dz;
		
		dx = r.mvOrigin.x - (tv0.x + tv1.x + tv2.x) / 3;
		dy = r.mvOrigin.y - (tv0.y + tv1.y + tv2.y) / 3;
		dz = r.mvOrigin.z - (tv0.z + tv1.z + tv2.z) / 3;
		
		out = (float) Math.sqrt(dx * dx + dy * dy + dz * dz );
		
		return out;
	}

	private void DrawModel(GL11 gl){
		gl.glPushMatrix();
		//This is control block 
			gl.glTranslatef(0.0f, 0.0f, z);			//Move z units into the screen
			gl.glScalef(0.8f, 0.8f, 0.8f); 			//Scale the Cube to 80 percent, otherwise it would be too large for the screen
			
			//Rotate around the axis based on the rotation matrix (rotation, x, y, z)
			gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);	//X
			gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);	//Y
			
    		//AppConfig.M = GetModelView(gl);
    		//AppConfig.P = GetProjectionView(gl);
			
    		B[0].x = 0f;
    		
    		for (DrawObjects i:Figures ) {
    	    	i.SetPlane(Direct);
    	    	//Log.i("Direct", "Direct[0] = " + Direct[0] + " Direct[1] = " + Direct[1] + " Direct[2] = " + Direct[2]);
    	    	//Log.i("Current","CurrentObj = " + CurrentObject);
    	    	i.SetAR(new float[]{AR[0],AR[1],AR[2]});
    			i.draw(0);
    			if (i.GetActionFlag()) {Direct[0] = Direct[1] = Direct[2] = 0;}
    		}
    		

		gl.glPopMatrix();
	}

	private void PickerUpdate(GL11 gl){
		gl.glPushMatrix();
		boolean Selected = false;
		
		min = 1000f;
		int CO = 0;
		for (int i = 1; i < 4; i++) {
    	    for (int j = 1; j < 4; j++) {
    		    for (int k = 1; k < 4; k++) {
					Matrix4f cM = new Matrix4f();
					Matrix4f C = new Matrix4f();
    		    	Matrix4f matTrans = new Matrix4f();			
					matTrans.setIdentity();
					
					matTrans.glTranslatef(mBlock[i][j][k].MainPoint.x(),
										  mBlock[i][j][k].MainPoint.y(), 
										  mBlock[i][j][k].MainPoint.z());
					C.set(AppConfig.M);
					//C.mul(AppConfig.gMatProject);
					cM.set(C);
					cM.mul(matTrans);
		
		
			for (int ii = 0; ii < 6; ii++) {
				for (int jj = 0; jj < 2; jj++) {
					if (0 == jj) {
						v00 = Matrix4f.mult(cM, getVector3f(mBlock[i][j][k],mBlock[i][j][k].indices[ii * 6 + 0]));
						v01 = Matrix4f.mult(cM, getVector3f(mBlock[i][j][k],mBlock[i][j][k].indices[ii * 6 + 1]));
						v02 = Matrix4f.mult(cM, getVector3f(mBlock[i][j][k],mBlock[i][j][k].indices[ii * 6 + 2]));
						
						A[0] = v00;
						A[1] = v01;
						A[2] = v02;
						
						//triangle.draw(gl,v00,v01,v02);
						
						gl.glPushMatrix();
							if (ray.intersectTriangle(v00, v01, v02, location)) {
								if (distantion(ray,v00,v01,v02) < min) {
									min = distantion(ray,v00,v01,v02);
									Selected = true;
								}
							};
						gl.glPopMatrix();	
					} else {
						v00 = Matrix4f.mult(cM, getVector3f(mBlock[i][j][k],mBlock[i][j][k].indices[ii * 6 + 3]));
						v01 = Matrix4f.mult(cM, getVector3f(mBlock[i][j][k],mBlock[i][j][k].indices[ii * 6 + 4]));
						v02 = Matrix4f.mult(cM, getVector3f(mBlock[i][j][k],mBlock[i][j][k].indices[ii * 6 + 5]));

						A[3] = v00;
						A[4] = v01;
						A[5] = v02;
						
						//triangle.draw(gl,v00,v01,v02);
						
						gl.glPushMatrix();
							if (ray.intersectTriangle(v00, v01, v02, location)) {
								if (distantion(ray,v00,v01,v02) < min) {
									min = distantion(ray,v00,v01,v02);
									Selected = true;
								}
							};
						gl.glPopMatrix();	
						
					}

				}
				if (Selected && AppConfig.select) {
					for (int cc = 0; cc < 6; cc++)
					  B[cc] = A[cc]; 
					
					int out = 0;
					switch (ii){
						case 0: out = 1;
								break;
						case 1: out = 3;
								break;
						case 2: out = 4;
								break;
						case 3: out = 6;
								break;
						case 4: out = 2;
								break;
						case 5: out = 5;
								break;
					}
					//if (AppConfig.Status != 2)
					  AppConfig.CurrentObject = (int)(i * 1000 + j * 100 + k * 10 + out);
				}
				
				Selected = false;
			}

			
    		    }
    	    }
	    }
		
		
		if ((B[0].x != 0 ) && AppConfig.select) {
			gl.glPushMatrix();
			triangle.draw(gl,B[0],B[1],B[2],1f,0f,0f);
			triangle.draw(gl,B[3],B[4],B[5],1f,0f,0f);
			gl.glPopMatrix();
			gl.glColor4f(1f, 1f, 1f, 1f);
			
			if (AppConfig.CurrentObject != AppConfig.OldObject) onTouchReleased();;
			AppConfig.OldObject = AppConfig.CurrentObject;
		}
		gl.glPopMatrix();	
	}

	public void onTouchReleased() {
		//Log.i("Status", "CurrentObject = " + AppConfig.CurrentObject);
		switch (AppConfig.Status){
		case 0:
			//Select first coordinate
			a = (int)(AppConfig.CurrentObject / 1000);
			b = (int)((AppConfig.CurrentObject - a * 1000) / 100);
			c = (int)((AppConfig.CurrentObject - a * 1000 - b * 100) / 10);
			d = AppConfig.CurrentObject - a * 1000 - b * 100 - c * 10;
			AppConfig.select = true;
			AppConfig.Status = 1;
			//Log.i("Status", "Status = " + AppConfig.Status);
			break;
		case 1:
			//Select second coordinate and calculate direction rotate
			//Select 
			oa = (int)(AppConfig.CurrentObject / 1000);
			ob = (int)((AppConfig.CurrentObject - oa * 1000) / 100);
			oc = (int)((AppConfig.CurrentObject - oa * 1000 - ob * 100) / 10);
			od = AppConfig.CurrentObject - oa * 1000 - ob * 100 - oc * 10;
			
			//Calculate
			Da = a - oa;
			Db = b - ob;
			Dc = c - oc;
			Dd = d - od;
			
			if ((Direct[0] == 0)&&(Direct[1] == 0)&&(Direct[2] == 0))
			{	
				Direct[0] = Direct[1] = Direct[2] = 0;
				
				//System.out.println("ID: \t" + OldID + "\t" + CurrentObject + "\t");
				//System.out.println("Dx: \t" + Da + "\t" + Db + "\t"  + Dc + "\t"  + Dd);
				
				if ((Da!=0)&&(Db==0)&&(Dc==0)){
					if ((Dd == 0)&&(d==1)){
						if (b == c) Direct[1] = (Da>0)?b:-b;
						if (c > b) Direct[1] = (Da>0)?-b:b;
						else Direct[1] = (Da>0)?-c:c;
					}
		
					if ((Dd == 0)&&(d==4)){
						if (b == c) Direct[1] = (Da>0)?b:-b;
						if (c > b) Direct[1] = (Da>0)?-c:c;
						else Direct[1] = (Da>0)?b:-b;
					}
		
					if ((Dd == 0)&&(d==5)){
						if (b == c) Direct[2] = (Da>0)?-b:b;
						if (c > b) Direct[2] = (Da>0)?-b:b;
						else Direct[2] = (Da>0)?c:-c;
					}
					if ((Dd == 0)&&(d==2)){
						if (b == c) Direct[2] = (Da>0)?b:-b;
						if (c > b) Direct[2] = (Da>0)?-c:c;
						else Direct[2] = (Da>0)?-b:b;
					}
				}
				
				if ((Da==0)&&(Db!=0)&&(Dc==0)){
					if ((Dd == 0)&&(d==6)){
						if (a == c) Direct[2] = (Db>0)?-a:a;
						if (c > a) Direct[2] = (Db>0)?c:-c;
						else Direct[2] = (Db>0)?c:-c;
					}
		
					if ((Dd == 0)&&(d==3)){
						if (a == c) Direct[2] = (Db>0)?a:-a;
						if (c > a) Direct[2] = (Db>0)?-c:c;
						else Direct[2] = (Db>0)?-c:c;
					}
					
					if ((Dd == 0)&&(d==1)){
						if (a == c) Direct[0] = (Db>0)?-a:a;
						if (c > a) Direct[0] = (Db>0)?a:-a;
						else Direct[0] = (Db>0)?c:-c;
					}
		
					if ((Dd == 0)&&(d==4)){
						if (a == c) Direct[0] = (Db>0)?-a:a;
						if (c > a) Direct[0] = (Db>0)?-c:c;
						else Direct[0] = (Db>0)?-a:a;
					}
				}
			
				if ((Da==0)&&(Db==0)&&(Dc!=0)){
					if (Dd == 0){
						if (d == 6){
							if (b > a) Direct[1] = (Dc>0)?-b:b;
							else Direct[1] = (Dc>0)?b:-b;
							if (a == b) Direct[1] = (Dc>0)?-a:a;
							//System.out.println(a + "\t" + b);
						}
		
						if (d == 3){
							if (b > a) Direct[1] = (Dc>0)?-b:b;
							else Direct[1] = (Dc>0)?b:-b;
							if (a == b) Direct[1] = (Dc>0)?a:-a;
							//System.out.println(a + "\t" + b);
						}
						
						if (d == 5){
							if (a == b) Direct[0] = (Dc>0)?a:-a;
							if (b > a) Direct[0] = (Dc>0)?-a:a;
							else Direct[0] = (Dc>0)?-b:b;
						}
		
						if (d == 2){
							if (a == b) Direct[0] = (Dc>0)?a:-a;
							if (b > a) Direct[0] = (Dc>0)?b:-b;
							else Direct[0] = (Dc>0)?a:-a;
						}
					}
				}
				
				
				//System.out.println(AR[0] + "\t" + AR[1] + "\t");
				
				Direct[0] *= -1;
				Direct[1] *= -1;
				Direct[2] *= -1;
				
				if ((Direct[0] == 0)&&(Direct[1] == 0)&&(Direct[2] == 0)) {
					AppConfig.Status = 0;
					AppConfig.CurrentObject = 0;
					AppConfig.OldObject = 0;
					AppConfig.select = true;
					//Log.i("Status", "Status = " + AppConfig.Status);
				} else {
					AppConfig.select = false;
					AppConfig.Status = 2;
					//Log.i("Status", "Status = " + AppConfig.Status);
				}
			}	
			break;
		case 2:
			//Rotate release
			AppConfig.select = false;
			break;
		}
	}
	
}
