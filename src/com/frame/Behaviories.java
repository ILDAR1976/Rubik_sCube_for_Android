package com.frame;

import javax.microedition.khronos.opengles.GL11;
import com.figure.BlockR;
import com.libs.Matrix4f;
import com.libs.Point3f;
import com.raypick.AppConfig;

import android.util.Log;

public final class Behaviories implements DrawObjects {
	//Блок переменных для ротации фигуры
	public boolean Selected = false;
	protected int Rotate = 0;
	protected float RotateX = 0;
	protected float RotateY = 0;
	protected float RotateZ = 0;
	protected float RotateBuffX = 0;
	protected float RotateBuffZ = 0;
	protected float RotateBuffY = 0;
	protected boolean RotateFlag = false;
	protected GL11 gl =  null;
	protected Point3f MainPoint = null;
	protected boolean ActionComplished = false;
	public float[] AR;
	
	public Behaviories(GL11 iGL,Point3f MP){
		gl = iGL;
		MainPoint = MP;
	}
	
	//Блок методов для вращения фигуры вокруг своих осей
	public void ReleaseRotation(){
		switch (Rotate){
		case 1: RotateXYZ();        // Вращаем против часовой стрелки по X 
		if (RotateFlag) 
			if (RotateX > 90) {
				RotateX = 90;
				ActionComplished = true;
				if (AppConfig.Status == 2) ChangeStatus();
			} else {
				RotateX += 0.6f;
				AppConfig.select = false;
			}
		break;
		case 2: RotateXYZ();        // Вращаем против часовой стрелки по Y
		if (RotateFlag) 
			if (RotateY > 90) {
				RotateY = 90;
				ActionComplished = true;
				if (AppConfig.Status == 2) ChangeStatus();
			} else {
				RotateY += 0.6f;
				AppConfig.select = false;
			}
		break;
		case 3: RotateXYZ();        // Вращаем против часовой стрелки по Z
		if (RotateFlag) 
			if (RotateZ > 90){
				RotateZ = 90;
				ActionComplished = true;
				if (AppConfig.Status == 2) ChangeStatus();
			} else {
				RotateZ += 0.6f;
				AppConfig.select = false;
			}
		break;
		case 4: RotateXYZ();        // Вращаем по часовой стрелке по X
		if (RotateFlag) 
			if (RotateX < -90) {
				RotateX = -90;
				ActionComplished = true;
				if (AppConfig.Status == 2) ChangeStatus();
			} else {
				RotateX -= 0.6f;
				AppConfig.select = false;
			}
		break;
		case 5: RotateXYZ();        // Вращаем по часовой стрелке по Y
		if (RotateFlag) 
			if (RotateY < -90) {
				RotateY = -90;
				ActionComplished = true;
				if (AppConfig.Status == 2) ChangeStatus();
			} else {
				RotateY -= 0.6f;
				AppConfig.select = false;
			}
		break;
		case 6: RotateXYZ();        // Вращаем по часовой стрелке по Z
		if (RotateFlag)
			if (RotateZ < -90) {
				RotateZ = -90;
				ActionComplished = true;
				if (AppConfig.Status == 2) ChangeStatus();
			} else { 
				RotateZ -= 0.6f;
				AppConfig.select = false;
			}
		break;
		}
	}
	public void RotateX(){ Rotate = 1; };
	public void RotateY(){ Rotate = 2; };
	public void RotateZ(){ Rotate = 3; };
	public void RotateNX(){ Rotate = 4; };
	public void RotateNY(){ Rotate = 5; };
	public void RotateNZ(){ Rotate = 6; };
	public void RotateEnd(){ 
		if (RotateFlag) 
			RotateFlag = false; 
		else 
			RotateFlag = true;
	};
	public void StartRotate(){ 
		RotateFlag = true;
		//ActionComplished = false;
	};
	public void StopRotate(){ 
		RotateX = 0;
		RotateY = 0;
		RotateZ = 0;
		
		RotateFlag = false;
		ActionComplished = false;
	};
	public void RotateXYZ(){
		gl.glRotatef(RotateX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(RotateY, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(RotateZ, 0.0f, 0.0f, 1.0f);
	}
		
	@Override
	public void draw(int Index) {
	}

	@Override
	public Point3f GetMove() {
		return MainPoint;
	}

	@Override
	public void SetPlane(int[] Plane) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public float[] GetRotation() {
		float[] M = new float[3];
		M[0] = RotateX;
		M[1] = RotateY;
		M[2] = RotateZ;
		return M;
	}
	
	@Override
	public void SetRotation(float[] RV) {
		RotateX = RV[0];
		RotateY = RV[1];
		RotateZ = RV[2];
	}

	@Override
	public boolean GetActionFlag() {
		return ActionComplished;
	}

	@Override
	public void SetAR(float[] iAR) {
		AR = iAR;
	}

	@Override
	public Point3f GetMainPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockR[][][] GetCurrentBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix4f GetModelView(GL11 gl) {
		return null;
	}

	@Override
	public Matrix4f GetProjectionView(GL11 gl) {
		return null;
	}

	public void ChangeStatus(){
		AppConfig.Status = 0;
		AppConfig.CurrentObject = 0;
		AppConfig.OldObject = 0;
		AppConfig.select = true;
		AppConfig.setTouchPosition(0f, 0f);
		//Log.i("Status", "Status = " + AppConfig.Status);
	};
}
