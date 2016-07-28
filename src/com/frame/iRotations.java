package com.frame;

public interface iRotations {
	//Ѕлок методов дл€ вращени€ фигуры вокруг своих осей
	public void ReleaseRotation();
	public void RotateX();
	public void RotateY();
	public void RotateZ();
	public void RotateNX();
	public void RotateNY();
	public void RotateNZ();
	public void StartRotate();
	public void StopRotate();
	public void RotateEnd();
	public void RotateXYZ();
	public float[] GetRotation();
	public void SetRotation(float[] RV);
	public void SetAR(float[] AR);
}
