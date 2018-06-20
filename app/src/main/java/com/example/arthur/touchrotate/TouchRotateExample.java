package com.example.arthur.touchrotate;
//package com.example.TouchRotateExample;

        import javax.microedition.khronos.egl.EGLConfig;
        import javax.microedition.khronos.opengles.GL10;
        import android.os.Bundle;
        import android.app.Activity;
        import android.content.Context;
        import android.opengl.GLSurfaceView;
        import android.view.MotionEvent;

public class TouchRotateExample extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLSurfaceView = new TouchSurfaceView(this);
        setContentView(mGLSurfaceView);
        mGLSurfaceView.requestFocus();
        mGLSurfaceView.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    private GLSurfaceView mGLSurfaceView;
}

class TouchSurfaceView extends GLSurfaceView {

    public TouchSurfaceView(Context context) {
        super(context);
        mRenderer = new CubeRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data.
        // To allow the triangle to rotate automatically, this line is commented out:
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override public boolean onTrackballEvent(MotionEvent e) {
        mRenderer.mAngleX += e.getX() * TRACKBALL_SCALE_FACTOR;
        mRenderer.mAngleY += e.getY() * TRACKBALL_SCALE_FACTOR;
        requestRender();
        return true;
    }

    @Override public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
                mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR;
                requestRender();
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    private class CubeRenderer implements GLSurfaceView.Renderer {
        public CubeRenderer() {

            mCube = new Cube();
            mTeapot = new Teapot();
            mTeapot1 = new TeapotQuarter(-1, -1);
            mTeapot2 = new TeapotQuarter(-1, 1);
            mTeapot3 = new TeapotQuarter(1, -1);
            mTeapot4 = new TeapotQuarter(1, 1);


            mPost = new Post();
        }

        // called every time frame is refreshed
        public void onDrawFrame(GL10 gl) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef(0, 0, -3.0f);
            // Rotates an object in a counterclockwise direction
            // about the vector (x,y,z).
            gl.glRotatef(mAngleX, 0, 1, 0);
            gl.glRotatef(mAngleY, 1, 0, 0);

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

            //mCube.draw(gl);
            mTeapot.draw(gl);
            mTeapot1.draw(gl);
            mTeapot2.draw(gl);
            mTeapot3.draw(gl);
            mTeapot4.draw(gl);
            mPost.draw(gl);
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {

            /*x, y lower left corner of the viewport rectangle, in pixels. The initial value is (0,0).
            width, height of the viewport.
            When a GL context is first attached to a window, width and height are set to the dimensions of that window.

            glViewport specifies the affine transformation of x and y from
            normalized device coordinates to window coordinates.
            Let x nd y nd be normalized device coordinates.
            Then the window coordinates x w y w are computed as follows:

            x w = x nd + 1 ⁢ width 2 + x
            y w = y nd + 1 ⁢ height 2 + y
            Viewport width and height are silently clamped to a range
            that depends on the implementation.
            To query this range, call glGet with argument GL_MAX_VIEWPORT_DIMS.
            */
            gl.glViewport(0, 0, width, height);

            // window aspect ratio
            float ratio = (float) width / height;

            // GL_PROJECTION
            // Applies subsequent matrix operations to the projection matrix stack.
            gl.glMatrixMode(GL10.GL_PROJECTION);

            gl.glLoadIdentity();

            float windowsize = 1;
            // https://www.khronos.org/opengles/sdk/1.1/docs/man/glFrustum.xml
            // The ES version takes GLfloat arguments,
            // while the desktop OpenGL version takes GLdouble.
            gl.glFrustumf(-ratio * windowsize,//left
                    ratio * windowsize,//right
                    -1 * windowsize, // bottom
                    1 * windowsize, // top
                    1, // near
                    10 * windowsize// far
            );
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            gl.glDisable(GL10.GL_DITHER);
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                    GL10.GL_FASTEST);

            gl.glClearColor(0, 0, 0, 0);
            //gl.glEnable(GL10.GL_CULL_FACE);
            gl.glShadeModel(GL10.GL_SMOOTH);
            gl.glEnable(GL10.GL_DEPTH_TEST);
        }
        private Cube mCube;
        private Teapot mTeapot;
        private TeapotQuarter mTeapot1;
        private TeapotQuarter mTeapot2;
        private TeapotQuarter mTeapot3;
        private TeapotQuarter mTeapot4;
        private Post mPost;
        public float mAngleX;
        public float mAngleY;
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final float TRACKBALL_SCALE_FACTOR = 36.0f;
    private CubeRenderer mRenderer;
    private float mPreviousX;
    private float mPreviousY;
}