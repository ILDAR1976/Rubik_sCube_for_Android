package com.iha;

import android.app.Activity;
import android.os.Bundle;

/**
 * The initial Android Activity, setting and initiating
 * the OpenGL ES Renderer Class @see Lesson08.java
 * 
 * @author Savas Ziplies (nea/INsanityDesign)
 */
public class Run extends Activity {

	/** Our own OpenGL View overridden */
	private Rubik rubik;

	/**
	 * Initiate our @see Lesson08.java,
	 * which is GLSurfaceView and Renderer
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Initiate our Lesson with this Activity Context handed over
		rubik = new Rubik(this);
		//Set the lesson as View to the Activity
		setContentView(rubik);
	}

	/**
	 * Remember to resume our Lesson
	 */
	@Override
	protected void onResume() {
		super.onResume();
		rubik.onResume();
	}

	/**
	 * Also pause our Lesson
	 */
	@Override
	protected void onPause() {
		super.onPause();
		rubik.onPause();
	}

}