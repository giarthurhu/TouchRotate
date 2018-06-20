package com.example.arthur.touchrotate;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Date;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Arthur on 7/21/2016.
 */


class Teapot {

    static final int COORDS_PER_VERTEX = 3;
    //  float is represented by 32 bits, which equals four bytes.
    // The float data type is a single-precision 32-bit IEEE 754 floating point.
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    // https://sjbaker.org/wiki/index.php?title=The_History_of_The_Teapot
    public Teapot() {

// homework http://www.vis.uky.edu/~ryang/Teaching/CS335-fall09/Progs/HW3-teapot.pdf
/*
        32 - number of patches
        4 4 16 control points in next patch

        1.4 0.0 2.4
        1.4 ‐0.784 2.4
        0.784 ‐1.4 2.4
        0.0 ‐1.4 2.4   

        1.3375 0.0 2.53125 1.3375 ‐0.749 2.53125 0.749 ‐1.3375 2.53125 0.0 ‐1.3375 2.53125   
        1.4375 0.0 2.53125 1.4375 ‐0.805 2.53125 0.805 ‐1.4375 2.53125 0.0 ‐1.4375 2.53125   
        1.5 0.0 2.4 1.5 ‐0.84 2.4 0.84 ‐1.5 2.4 0.0 ‐1.5 2.4   
        4 4
        0.0 ‐1.4 2.4  ‐0.784 ‐1.4 2.4  ‐1.4 ‐0.784 2.4  ‐1.4 0.0 2.4   
        0.0 ‐1.3375 2.53125  ‐0.749 ‐1.3375 2.53125  ‐1.3375 ‐0.749 2.53125  ‐1.3375 0.0 2.53125   
        0 0 1 4375 2 53125 0 805 1 4375 2 53125 1 4375 0 805 2 53125 1 4375 0 0 2 53125
        The first line is the number of patches (32 in this case).

        The second line is the number of control
        points for the next patch (always “4 4”, meaning 4x4=16 points),

        the next four lines are the 16
        control points. Each point is a 3 vector.

        */


        // each element of 3 is x, y, z of one vertex
        // Cube has 8 vertices, same as two squares
        // 59*2 = 118 vertices
        double vertices[] = {
                // z=2.7  0 1 2 3
                0.2000, 0.0000, 2.70000, 0.2000, -0.1120, 2.70000,
                0.1120, -0.2000, 2.70000, 0.0000, -0.2000, 2.70000,
                // z=2.53125 4 5    6 7   8 9    10 11
                1.3375, 0.0000, 2.53125, 1.3375, -0.7490, 2.53125,
                0.7490, -1.3375, 2.53125, 0.0000, -1.3375, 2.53125,
                1.4375, 0.0000, 2.53125, 1.4375, -0.8050, 2.53125,
                0.8050, -1.4375, 2.53125, 0.0000, -1.4375, 2.53125,
                // z= 2.400
                1.5000, 0.0000, 2.40000, 1.5000, -0.8400, 2.40000,
                0.8400, -1.5000, 2.40000, 0.0000, -1.5000, 2.40000,
                // z=1.875
                1.7500, 0.0000, 1.87500, 1.7500, -0.9800, 1.87500,
                0.9800, -1.7500, 1.87500, 0.0000, -1.7500, 1.87500,
                // z=1.35
                2.0000, 0.0000, 1.35000, 2.0000, -1.1200, 1.35000,
                1.1200, -2.0000, 1.35000, 0.0000, -2.0000, 1.35000,
                // z = 0.9
                2.0000, 0.0000, 0.90000, 2.0000, -1.1200, 0.90000,
                1.1200, -2.0000, 0.90000, 0.0000, -2.0000, 0.90000,
                // z= 0.45
                -2.0000, 0.0000, 0.90000, 2.0000, 0.0000, 0.45000,
                2.0000, -1.1200, 0.45000, 1.1200, -2.0000, 0.45000,
                // z = .45-.225
                0.0000, -2.0000, 0.45000, 1.5000, 0.0000, 0.22500,
                1.5000, -0.8400, 0.22500, 0.8400, -1.5000, 0.22500,
                // z = .225-0.15
                0.0000, -1.5000, 0.22500, 1.5000, 0.0000, 0.15000,
                1.5000, -0.8400, 0.15000, 0.8400, -1.5000, 0.15000,

                0.0000, -1.5000, 0.15000, -1.6000, 0.0000, 2.02500,
                -1.6000, -0.3000, 2.02500, -1.5000, -0.3000, 2.25000,
                -1.5000, 0.0000, 2.25000, -2.3000, 0.0000, 2.02500,
                -2.3000, -0.3000, 2.02500, -2.5000, -0.3000, 2.25000,
                -2.5000, 0.0000, 2.25000, -2.7000, 0.0000, 2.02500,
                -2.7000, -0.3000, 2.02500, -3.0000, -0.3000, 2.25000,
                -3.0000, 0.0000, 2.25000, -2.7000, 0.0000, 1.80000,
                -2.7000, -0.3000, 1.80000, -3.0000, -0.3000, 1.80000,
                -3.0000, 0.0000, 1.80000, -2.7000, 0.0000, 1.57500,
                -2.7000, -0.3000, 1.57500, -3.0000, -0.3000, 1.35000,
                -3.0000, 0.0000, 1.35000, -2.5000, 0.0000, 1.12500,
                -2.5000, -0.3000, 1.12500, -2.6500, -0.3000, 0.93750,
                -2.6500, 0.0000, 0.93750, -2.0000, -0.3000, 0.90000,
                -1.9000, -0.3000, 0.60000, -1.9000, 0.0000, 0.60000,
                1.7000, 0.0000, 1.42500, 1.7000, -0.6600, 1.42500,
                1.7000, -0.6600, 0.60000, 1.7000, 0.0000, 0.60000,
                2.6000, 0.0000, 1.42500, 2.6000, -0.6600, 1.42500,
                3.1000, -0.6600, 0.82500, 3.1000, 0.0000, 0.82500,
                2.3000, 0.0000, 2.10000, 2.3000, -0.2500, 2.10000,
                2.4000, -0.2500, 2.02500, 2.4000, 0.0000, 2.02500,
                2.7000, 0.0000, 2.40000, 2.7000, -0.2500, 2.40000,
                3.3000, -0.2500, 2.40000, 3.3000, 0.0000, 2.40000,
                2.8000, 0.0000, 2.47500, 2.8000, -0.2500, 2.47500,
                3.5250, -0.2500, 2.49375, 3.5250, 0.0000, 2.49375,
                2.9000, 0.0000, 2.47500, 2.9000, -0.1500, 2.47500,
                3.4500, -0.1500, 2.51250, 3.4500, 0.0000, 2.51250,
                2.8000, 0.0000, 2.40000, 2.8000, -0.1500, 2.40000,
                3.2000, -0.1500, 2.40000, 3.2000, 0.0000, 2.40000,
                0.0000, 0.0000, 3.15000, 0.8000, 0.0000, 3.15000,
                0.8000, -0.4500, 3.15000, 0.4500, -0.8000, 3.15000,
                0.0000, -0.8000, 3.15000, 0.0000, 0.0000, 2.85000,
                1.4000, 0.0000, 2.40000, 1.4000, -0.7840, 2.40000,
                0.7840, -1.4000, 2.40000, 0.0000, -1.4000, 2.40000,
                0.4000, 0.0000, 2.55000, 0.4000, -0.2240, 2.55000,
                0.2240, -0.4000, 2.55000, 0.0000, -0.4000, 2.55000,
                1.3000, 0.0000, 2.55000, 1.3000, -0.7280, 2.55000,
                0.7280, -1.3000, 2.55000, 0.0000, -1.3000, 2.55000,
                1.3000, 0.0000, 2.40000, 1.3000, -0.7280, 2.40000,
                0.7280, -1.3000, 2.40000, 0.0000, -1.3000, 2.40000,
        };

        int numVertices = vertices.length;

        float floatVertices[] = new float[numVertices];

        for (int i = 0; i < numVertices; i++) {
            floatVertices[i] = (float) vertices[i] * 8000.0f;
        }

        int ivertices[] = new int [numVertices];
        for (int i = 0; i < numVertices; i++) {
            ivertices[i] = (int) ( vertices[i] * 25000.0f);
        }

        // each vertex is one color,
        // the face coloration is a blend between each corner
        int one = 0x10000;
        //RGBA color (i.e. red, green, blue, and alpha
        int colors[] = {
                0, one, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, one, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, one, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
        };

        // convert from quad to triangle faces
        // 24 elements
        // 212 elements
        byte indices[] =

        // https://sjbaker.org/wiki/index.php?title=The_History_of_The_Teapot
        // used excel spreadsheet to convert to triangles
        // RIM
        {
/*
                    102, 103, 104,
                    104, 105, 102,
                    4, 5, 6,
                    6, 7, 4,
                    8, 9, 10,
                    10, 11, 8,
                    12, 13, 14,
                    14, 15, 12,
                //BODY 1 quadrant, need 4 of these */
                /*
                12,15,19,19,16,12,15,14,18,18,19,15,
                16,19,23,23,20,16,19,18,22,22,23,19,
                20,23,27,27,24,20,23,22,26,26,27,23,
                24,27,27,27,24,24,27,26,26,26,27,27,
                24,27,32,32,29,24,27,26,31,31,32,27,
                29,32,36,36,33,29,32,31,35,35,36,32,
                33,36,40,40,37,33,36,35,39,39,40,36,

                14,13,17,17,18,14,13,12,16,16,17,13,
                18,17,21,21,22,18,17,16,20,20,21,17,
                22,21,25,25,26,22,21,20,24,24,25,21,
                26,25,25,25,26,26,25,24,24,24,25,25,
                26,25,30,30,31,26,25,24,29,29,30,25,
                31,30,34,34,35,31,30,29,33,33,34,30,
                35,34,38,38,39,35,34,33,37,37,38,34,

                    /*
                    // 16, 17, 18, 19 LEVEL 2

                /*
                    20, 21, 22, 22, 23, 20, 24, 25, 26, 26, 27, 24,
                    24, 25, 26, 26, 27, 24, 29, 30, 31, 31, 32, 29,
                    33, 34, 35, 35, 36, 33, 37, 38, 39, 39, 40, 37,
                    // lID
                    96, 96, 96, 96, 96, 96, 97, 98, 99, 99, 100, 97,
                    101, 101, 101, 101, 101, 101, 0,1, 2, 2, 3,0,
                    0, 1, 2, 2, 3, 0, 106, 107, 108, 108, 109, 106,
                    110, 111, 112, 112, 113, 110, 114, 115, 116, 116, 117, 114,
                    */
/*HANDLE - need two of these reflected
*/
                46,45,41,46,41,42,44,48,47,44,47,43,
50,49,45,50,45,46,48,52,51,48,51,47,
54,53,49,54,49,50,52,56,55,52,55,51,
54,53,53,54,53,54,56,56,55,56,55,55,
58,57,53,58,53,54,56,60,59,56,59,55,
62,61,57,62,57,58,60,64,63,60,63,59,
65,28,61,65,61,62,64,67,66,64,66,63,

                41,45,44,45,48,44,42,43,46,46,43,47,
                45,49,48,49,52,48,46,47,50,50,47,51,
                49,53,52,53,56,52,50,51,54,54,51,55,
                53,53,56,53,56,56,54,55,54,54,55,55,
                53,57,56,57,60,56,54,55,58,58,55,59,
                57,61,60,61,64,60,58,59,62,62,59,63,
                61,28,64,28,67,64,62,63,65,65,63,66,


/*
// SPOUT
*/

                68,71,75,75,72,68,71,70,74,74,75,71,
                72,75,79,79,76,72,75,74,78,78,79,75,
                76,79,83,83,80,76,79,78,82,82,83,79,
                80,83,83,83,80,80,83,82,82,82,83,83,
                80,83,87,87,84,80,83,82,86,86,87,83,
                84,87,91,91,88,84,87,86,90,90,91,87,
                88,91,95,95,92,88,91,90,94,94,95,91,


                70,69,73,73,74,70,69,68,72,72,73,69,
                74,73,77,77,78,74,73,72,76,76,77,73,
                78,77,81,81,82,78,77,76,80,80,81,77,
                82,81,81,81,82,82,81,80,80,80,81,81,
                82,81,85,85,86,82,81,80,84,84,85,81,
                86,85,89,89,90,86,85,84,88,88,89,85,
                90,89,93,93,94,90,89,88,92,92,93,89,
        };
        mNumIndices = indices.length;//212;

        // Allocate byte buffer for indice data, 4 bytes for each float or fixed point number
        // on 32 bit integer
        //ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        ByteBuffer vbb = ByteBuffer.allocateDirect(ivertices.length * 4);

        // Endian
        // Retrieves the native byte order of the underlying platform.
        // performance-sensitive Java code can allocate direct buffers
        //  with the same byte order as the hardware.
        // Native code libraries are often more efficient when such buffers are used
            vbb.order(ByteOrder.nativeOrder());

        // Creates a view of this byte buffer as an int buffer.
        // The content of the new buffer will start at this buffer's current position.
        //mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer = vbb.asIntBuffer();
        //mVertexBuffer.put(floatVertices);
        mVertexBuffer.put(ivertices);
        mVertexBuffer.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asIntBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
    }

    public void draw(GL10 gl) {
        // glFrontFace — define front- and back-facing polygons for face hiding elimination
        gl.glFrontFace(GL10.GL_CW /*clockwise*/);

        // glVertexPointer — define an array of vertex data
        //gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);

        // define an array of colors
        // size number of components per color. Must be 3 or 4. The initial value is 4.
        // type data type of each color component in the array.
        //   Symbolic constants GL_BYTE, GL_UNSIGNED_BYTE, GL_SHORT, GL_UNSIGNED_SHORT,
        //   GL_INT, GL_UNSIGNED_INT, GL_FLOAT, and GL_DOUBLE are accepted. The initial value is GL_FLOAT.
        // stride byte offset between consecutive colors.
        //    If stride is 0, the colors are understood to be tightly packed in the array.
        //    The initial value is 0.
        // pointer to the first component of the first color element in the array.
        //    The initial value is 0.
        gl.glColorPointer(
                4, // size number of components per color. Must be 3 or 4
                GL10.GL_FIXED, // type data type of each color component in the array.
                0, //If stride is 0, the colors are understood to be tightly packed in the array.
                mColorBuffer);

        //  specifies multiple geometric primitives with very few subroutine calls.
        //  Instead of calling a GL function to pass each individual vertex, normal,
        //  texture coordinate, edge flag, or color, you can prespecify separate arrays
        //  of vertices, normals, and so on, and use them to construct a sequence of primitives
        // with a single call to glDrawElements.
/*
            mode
            Specifies what kind of primitives to render.
            Symbolic constants GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP,
            GL_LINES, GL_LINE_STRIP_ADJACENCY, GL_LINES_ADJACENCY,
            GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN, GL_TRIANGLES,
            GL_TRIANGLE_STRIP_ADJACENCY, GL_TRIANGLES_ADJACENCY and
            GL_PATCHES are accepted.

            count
            Specifies the number of elements to be rendered.

            type
            Specifies the type of the values in indices.
            Must be one of GL_UNSIGNED_BYTE, GL_UNSIGNED_SHORT, or GL_UNSIGNED_INT.

            indices
            Specifies a pointer to the location where the indices are stored.

            */
            // 24 elements
        gl.glDrawElements(GL10.GL_TRIANGLES, mNumIndices,
                GL10.GL_UNSIGNED_BYTE, mIndexBuffer);
    }

    //private FloatBuffer mVertexBuffer;
    private int mNumIndices;
    private IntBuffer mVertexBuffer;
    private IntBuffer mColorBuffer;
    private ByteBuffer mIndexBuffer;
}
