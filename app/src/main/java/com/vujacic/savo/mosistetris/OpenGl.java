package com.vujacic.savo.mosistetris;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.vujacic.savo.mosistetris.Game.MyGLRenderer;
import com.vujacic.savo.mosistetris.Game.MyGLSurfaceView;

public class OpenGl extends Activity {
    private GLSurfaceView gLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
//        gLView = new MyGLSurfaceView(this);
//        setContentView(gLView);

        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new MyGLRenderer());
        setContentView(view);
    }
}
