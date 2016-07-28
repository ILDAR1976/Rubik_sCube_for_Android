package com.figure;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL11;

import com.frame.Behaviories;
import com.frame.DrawObjects;
import com.libs.Matrix4f;
import com.libs.Point;
import com.libs.Point3f;
import com.libs.Vector3f;

import android.content.Context;
import android.opengl.GLU;

public class figure implements DrawObjects	{
        public BlockR[][][] mBlock = null;

	    protected Behaviories Behavior = null;
	    //Ѕлок переменных дл€ обработки позиционировани€ и перемещени€ фигуры
	    public Point3f MainPoint = null;  //√лавна€ точка позиционирующа€ фигуру в сцене
	    public Vector3f MainVector = null; //¬ектор направлени€ движени€ фигуры
	    public Vector3f RotateVector = null; //¬ектор направлени€ вращени€ фигуры
	    
	    protected float g = 0;
	    protected float r = 0;
	    
	    //Ѕлок переменных дл€ обработки отрисовки фигуры
	    protected ArrayList<Vector3f> Vectors3D = null;
	    protected ArrayList<Point3f> Points3D = null;
	    protected ArrayList<Point> Points = null;
	    
	    //Ѕлок переменных дл€ обработки отрисовки текстур фигуры
	    protected int[][] Textures = null;
		protected float[] MainTextureCoords = null;
		
		//Ѕлок переменных дл€ отработки св€зи с контекстом графической сцены
		protected GL11 gl; 
		protected GLU glu = null;
		//protected GLUquadric pObj = null;
		
		// онструкторы
		public figure(GL11 iGL,
					  Context context,
				      float iX, 
				      float iY, 
				      float iZ){
			gl = iGL;
			MainPoint = new Point3f(iX,iY,iZ);
		    Textures = new int[7][3];
			Behavior =  new Behaviories(gl,MainPoint);
			mBlock = new BlockR[4][4][4];
		}

		public figure(GL11 iGL,
			   Context context,
			          Point3f P){
			gl = iGL;
			MainPoint = new Point3f(P.x(),P.y(),P.z());
			mBlock = new BlockR[4][4][4];
			LoadGLTextures(iGL, context);;
		}
		
		//ѕрототип метода дл€ наложени€ и отрисовки текстур фигуры
		public void LoadGLTextures(GL11 gl, Context context){
		};
		
		@Override
		public void ReleaseRotation() {
			Behavior.ReleaseRotation();
		}
		@Override
		public void RotateX() {
			Behavior.RotateX();
		}
		@Override
		public void RotateY() {
			Behavior.RotateY();
		}
		@Override
		public void RotateZ() {
			Behavior.RotateZ();
		}
		@Override
		public void RotateNX() {
			Behavior.RotateNX();
		}
		@Override
		public void RotateNY() {
			Behavior.RotateNY();
		}
		@Override
		public void RotateNZ() {
			Behavior.RotateNZ();
		}
		@Override
		public void RotateEnd() {
			Behavior.RotateEnd();
		}
		@Override
		public void RotateXYZ() {
			Behavior.RotateXYZ();
		}
		@Override
		public float[] GetRotation() {
			return Behavior.GetRotation();
		}
		@Override
		public void SetRotation(float[] RV) {
			Behavior.SetRotation(RV);
		}
		@Override
		public void StartRotate() {
			Behavior.StartRotate();
		}
		@Override
		public void StopRotate() {
			Behavior.StopRotate();
		}

		@Override
		public void draw(int Index) {
		}
		@Override
		public Point3f GetMove() {
			return Behavior.GetMove();
		}
		public Point3f GetMainPoint()
		{
			return(MainPoint);
		}

		@Override
		public void SetPlane(int[] Plane) {
			Behavior.SetPlane(Plane);
			
		}

		@Override
		public boolean GetActionFlag() {
			return Behavior.GetActionFlag();
		}

		@Override
		public void SetAR(float[] fAR) {
			Behavior.SetAR(fAR);
		}

		@Override
		public BlockR[][][] GetCurrentBlocks() {
			return mBlock;
		}

		@Override
		public Matrix4f GetModelView(GL11 gl) {
			Matrix4f matrix = new Matrix4f();
			float[] model_matrix = new float[16];
			gl.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, model_matrix, 0);	
			matrix.fill(model_matrix);
			return matrix;
			
		}

		@Override
		public Matrix4f GetProjectionView(GL11 gl) {
			Matrix4f matrix = new Matrix4f();
			float[] model_matrix = new float[16];
			gl.glGetFloatv(GL11.GL_PROJECTION_MATRIX, model_matrix, 0);	
			matrix.fill(model_matrix);
			return matrix;
		}
}
