package com.libs;

public class Point3f {

	public float[] p = new float[3];

	public Point3f() {
		p[0] = p[1] = p[2] = 0;
	}

	public Point3f( float x, float y, float z ) {
		p[0] = x;
		p[1] = y;
		p[2] = z;
	}

	public Point3f( Vector3f V ) {
		p[0] = V.x;
		p[1] = V.y;
		p[2] = V.z;
	}

	public void copy( Point3f P ) {
		p[0] = P.p[0];
		p[1] = P.p[1];
		p[2] = P.p[2];
	}

	public float x() { return p[0]; }
	public float y() { return p[1]; }
	public float z() { return p[2]; }
	
	public Point3f plus(Point3f Pt){
		return new Point3f(x() + Pt.p[0],y() + Pt.p[1],z() + Pt.p[2]);
	}
	
	public Point3f minus(Point3f Pt){
		return new Point3f(x() - Pt.p[0],y() - Pt.p[1],z() - Pt.p[2]);
	}

	// used to pass coordinates directly to OpenGL routines
	public float[] get() { return p; }

	// return the difference between two given points
	static public Vector3f diff( Point3f a, Point3f b ) {
		return new Vector3f( a.x()-b.x(), a.y()-b.y(), a.z()-b.z() );
	}

	// return the sum of the given point and vector
	static public Point3f sum( Point3f a, Vector3f b ) {
		return new Point3f( a.x()+b.x, a.y()+b.y, a.z()+b.z );
	}

	// return the difference between the given point and vector
	static public Point3f diff( Point3f a, Vector3f b ) {
		return new Point3f( a.x()-b.x, a.y()-b.y, a.z()-b.z );
	}

	static Point3f average( Point3f a, Point3f b ) {
		// return new Point3f( Vector3f.mult( Vector3f.sum( new Vector3f(a), new Vector3f(b) ), 0.5f ) );
		return new Point3f( (a.x()+b.x())*0.5f, (a.y()+b.y())*0.5f, (a.z()+b.z())*0.5f );
	}
}

