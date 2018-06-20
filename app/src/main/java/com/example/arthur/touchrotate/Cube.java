package com.example.arthur.touchrotate;

/**
 * Created by Arthur on 7/21/2016.
 * Creates simple cube instead of fancy teapot
 */
        import java.nio.ByteBuffer;
        import java.nio.ByteOrder;
        import java.nio.IntBuffer;

        import javax.microedition.khronos.opengles.GL10;

class Cube
{
    public Cube()
    {
        // integer value for coordinates
        // prefix 0x indicates hexadecimal
        // The number 26, in decimal
        int decVal = 26;
        //  The number 26, in hexadecimal
        int hexVal = 0x1a;
        int one =   0x10000;
        // in decimal should be 65536
        int quart = 0x08000;

        // each element of 3 is x, y, z of one vertex
        // Cube has 8 vertices, same as two squares
        int vertices[] = {
                -one, -one, -quart,
                one, -one, -quart,
                one, one, -quart,
                -one, one, -quart,
                -one, -one, quart,
                one, -one, quart,
                one, one, quart,
                -one, one, quart,
        };

        // each vertex is one color,
        // the face coloration is a blend between each corner
        int colors[] = {
                0, 0, 0, one,
                one, 0, 0, one,
                one, one, 0, one,
                0, one, 0, one,
                0, 0, one, one,
                one, 0, one, one,
                one, one, one, one,
                0, one, one, one,
        };

        // A cube has six faces,
        // each face is made up of two triangles
        // there are 36 indices in this list
        byte indices[] = {
                0, 4, 5,  0, 5, 1,
                1, 5, 6,  1, 6, 2,
                2, 6, 7,  2, 7, 3,
                3, 7, 4,  3, 4, 0,
                4, 7, 6,  4, 6, 5,
                3, 0, 1,  3, 1, 2
        };

        // Allocate byte buffer for indice data, 4 bytes for each fixed point number
        // on 32 bit integer
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);

        // Endian
        // Retrieves the native byte order of the underlying platform.
        // performance-sensitive Java code can allocate direct buffers
        //  with the same byte order as the hardware.
        // Native code libraries are often more efficient when such buffers are used
        vbb.order(ByteOrder.nativeOrder());

        // Creates a view of this byte buffer as an int buffer.
        // The content of the new buffer will start at this buffer's current position.
        mVertexBuffer = vbb.asIntBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asIntBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
    }

    public void draw(GL10 gl)
    {
        // glFrontFace — define front- and back-facing polygons for face hiding elimination
        gl.glFrontFace(GL10.GL_CW /*clockwise*/ );

        // glVertexPointer — define an array of vertex data
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
        gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);
    }

    private IntBuffer mVertexBuffer;
    private IntBuffer mColorBuffer;
    private ByteBuffer mIndexBuffer;
}
