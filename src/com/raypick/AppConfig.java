package com.raypick;

import com.libs.Matrix4f;

public class AppConfig {
	public static int CurrentObject = 0;
	public static int OldObject = 0;
	public static boolean select = true;
	public static int Status = 0;
	public static Matrix4f M = new Matrix4f();
	public static Matrix4f P = new Matrix4f();
	public static Matrix4f gMatProject = new Matrix4f();
	public static Matrix4f gMatView = new Matrix4f();
	public static Matrix4f gMatModel = new Matrix4f();
	public static int[] gpViewport = new int[4];
	public static float[] gpMatrixProjectArray = new float[16];
	public static float[] gpMatrixViewArray = new float[16];
	public static boolean gbNeedPick = false;
	public static boolean gbTrianglePicked = false;
	public static float gScreenX, gScreenY;

	public static void setTouchPosition(float x, float y) {
		gScreenX = x;
		gScreenY = y;
	}

}
