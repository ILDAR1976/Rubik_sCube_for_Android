package com.raypick;


import com.libs.Projector;
import com.libs.Ray;
import com.libs.Vector3f;

import android.util.Log;

public class PickFactory {

	private static Ray gPickRay = new Ray();

	public static Ray getPickRay() {
		return gPickRay;
	}

	private static Projector gProjector = new Projector();

	private static float[] gpObjPosArray = new float[4];

	synchronized	public static void update(float screenX, float screenY) {
		AppConfig.gMatView.fillFloatArray(AppConfig.gpMatrixViewArray);
		float openglY = AppConfig.gpViewport[3] - screenY;
		// z = 0 , P0
		gProjector.gluUnProject(screenX, openglY, 0.0f,
				AppConfig.gpMatrixViewArray, 0, AppConfig.gpMatrixProjectArray,
				0, AppConfig.gpViewport, 0, gpObjPosArray, 0);
		// P0
		gPickRay.mvOrigin.set(gpObjPosArray[0], gpObjPosArray[1],
				gpObjPosArray[2]);

	Log.i("coor","screenX=" + screenX + ",screenY=" + screenY);
	Log.i("coor","gpObjPosArray[0]=" + gpObjPosArray[0] + ",gpObjPosArray[1]=" + gpObjPosArray[1] + ",gpObjPosArray[2]" + gpObjPosArray[2]);
		// z = 1 P1
		gProjector.gluUnProject(screenX, openglY, 1.0f,
				AppConfig.gpMatrixViewArray, 0, AppConfig.gpMatrixProjectArray,
				0, AppConfig.gpViewport, 0, gpObjPosArray, 0);
		// P1 - P0
		gPickRay.mvDirection.set(gpObjPosArray[0], gpObjPosArray[1],
				gpObjPosArray[2]);
		gPickRay.mvDirection.sub(gPickRay.mvOrigin);
	
		gPickRay.mvDirection.normalize();
	}

	private final static Object mLock = new Object();
	
	synchronized public static Vector3f to3DPoint(float screenX, float screenY) {
		Vector3f v = new Vector3f();
		synchronized (mLock) {
		AppConfig.gMatView.fillFloatArray(AppConfig.gpMatrixViewArray);
		
		float openglY = AppConfig.gpViewport[3] - screenY;
		// z = 0 , P0
		gProjector.gluUnProject(screenX, openglY, 0.0f,
				AppConfig.gpMatrixViewArray, 0, AppConfig.gpMatrixProjectArray,
				0, AppConfig.gpViewport, 0, gpObjPosArray, 0);
	}
		// P0
		v.set(gpObjPosArray[0], gpObjPosArray[1],
				gpObjPosArray[2]);
		return v;
	}
	
	public static Vector3f D3to2DPoint(float x, float y, float z) {		
		Vector3f v_ret = new Vector3f();
		//synchronized (mLock) {
		AppConfig.gMatView.fillFloatArray(AppConfig.gpMatrixViewArray);

		gProjector.gluProject(x, y, z,
				AppConfig.gpMatrixViewArray, 0, AppConfig.gpMatrixProjectArray,
				0, AppConfig.gpViewport, 0, gpObjPosArray, 0);
		//}
		// 
		v_ret.set(gpObjPosArray[0], AppConfig.gpViewport[3] - gpObjPosArray[1],
				gpObjPosArray[2]);
		return v_ret;
	}
}
