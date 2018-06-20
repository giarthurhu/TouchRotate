/*

Make Octogon post

Top view
 43211234
4..++++..
3.++++++.
2++++++++
1++++++++
1++++++++
2++++++++
3.++++++.
4..++++..

Height - 0 bottom 4 top
Width - 8

Vertices -
1   -4   2   0
2   -2   4   0
3    2   4   0
4    4   2   0
5    4  -2   0
6    2  -4   0
7   -2  -4   0
8   -4  -2   0

Indices

  43211234

4 ..2++3..
3 .++++++.
2 1++++++4
1 ++++++++
1 ++++++++
2 8++++++5
3 .++++++.
4 ..7++6..

Patches

 ..2++3..
 .++++++.
 1++++++4
 ++++++++
 ++++++++
 8++++++5
 .++++++.
 ..7++6..


1 2 4
 ..2++3..
 .###+++.
 1######4
 ++++++++
 ++++++++
 8++++++5
 .++++++.

2 3 4

 ..2##3..
 .++####.
 1++++++4
 ++++++++
 ++++++++
 8++++++5
 .++++++.


1 5 8

 ..2++3..
 .++++++.
 1++++++4
 ##++++++
 ######++
 8######5
 .++++++.
 ..7++6..

1 4 5

 ..2++3..
 .++++++.
 1######4
 ++++####
 ++++++##
 8++++++5
 .++++++.
 ..7++6..

8 6 7 = 7 5 6
 ..2++3..
 .++++++.
 1++++++4
 ++++++++
 ++++++++
 8++++++5
 .###+++.
 ..7##6..

8 5 6 = 7 4 5
 ..2++3..
 .++++++.
 1++++++4
 ++++++++
 ++++++++
 8######5
 .+++###.
 ..7++6..

 */
package com.example.arthur.touchrotate;

        import java.nio.ByteBuffer;
        import java.nio.ByteOrder;
        import java.nio.FloatBuffer;
        import java.nio.IntBuffer;
        import java.util.Date;

        import javax.microedition.khronos.opengles.GL10;

class Post {

    static final int COORDS_PER_VERTEX = 3;
    //  float is represented by 32 bits, which equals four bytes.
    // The float data type is a single-precision 32-bit IEEE 754 floating point.
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    // https://sjbaker.org/wiki/index.php?title=The_History_of_The_Post
    public Post() {

        // each element of 3 is x, y, z of one vertex
        // Cube has 8 vertices, same as two squares
        // post is octagon

        int vertices[] = {
                /*1*/   -4, 2, 2,
                /*2*/   -2, 4, 2,
                /*3*/    2, 4, 2,
                /*4*/    4, 2, 2,
                /*5*/    4, -2, 2,
                /*6*/    2, -4, 2,
                /*7*/   -2, -4, 2,
                /*8*/   -4, -2, 2,
                /*9*/   -4, 2, 0,
                /*10*/   -2, 4, 0,
                /*11*/    2, 4, 0,
                /*12*/    4, 2, 0,
                /*13*/    4, -2, 0,
                /*14*/    2, -4, 0,
                /*15*/   -2, -4, 0,
                /*16*/   -4, -2, 0,

        };
        mNumVertices = vertices.length;

        int ivertices [] = new int [mNumVertices];
        for (int i = 0; i <mNumVertices; i++ ){
            ivertices [i] = vertices [i] * 9000;
        };



        // each vertex is one color,
        // the face coloration is a blend between each corner
        int one = 0x10000;
        //RGBA color (i.e. red, green, blue, and alpha
        int colors[] = {
                0, 0, 0, one, //1
                one, 0, 0, one, //2
                one, one, 0, one, //3
                0, one, 0, one, //4
                0, 0, one, one, //5
                one, 0, one, one, //6
                one, one, one, one, //7
                0, one, one, one, //8
        };

        // convert from quad to triangle faces
        // 24 elements
        // 212 elements
        byte indices[] = {
            0, 1, 3, //1
            1, 2, 3, //2
            0, 4, 7, //3
            0, 3, 4, //4
            7, 5, 6, //5
            7, 4, 5, //6
                11,9,8,
                11,10,9,
                15,12,8,
                12,11,8,
                14,13,15,
                13,12,15,
                8, 0, 1,
                9, 8, 1,
        };


        mNumIndices = indices.length;


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

        mIndexBuffer = ByteBuffer.allocateDirect(mNumIndices);
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
        gl.glDrawElements(GL10.GL_TRIANGLES, mNumIndices ,
                GL10.GL_UNSIGNED_BYTE, mIndexBuffer);
    }

    private int mNumVertices;
    private int mNumIndices;
    //private FloatBuffer mVertexBuffer;
    private IntBuffer mVertexBuffer;
    private IntBuffer mColorBuffer;
    private ByteBuffer mIndexBuffer;
}