package POWJA;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;


public class LevelEditorScene extends Scene
{
    private String vertexShaderSrc = "#version 330 core\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "layout (location = 1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos,1.0);\n" +
            "}";

    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}";


    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
            // position               // color
             0.5f,-0.5f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f, // Bottom right 0
            -0.5f, 0.5f, 0.0f,       0.0f, 1.0f, 0.0f, 1.0f, // Top left     1
             0.5f, 0.5f, 0.0f,       1.0f, 0.0f, 1.0f, 1.0f, // Top right    2
            -0.5f,-0.5f, 0.0f,       1.0f, 1.0f, 0.0f, 1.0f, // Bottom left  3
    };
    // Must be in counter-clockwise order ( Nguoc chieu kim dong ho)
    private int[] elementArray = {
            /*
                    x        x


                    x        x
             */
            2, 1, 0, // Top right triangle
            0, 1, 3 // bottom left triangle
    };

    private int vaoID, vboID, eboID; //buffer for shape related objects such as array, vertex, element

    public LevelEditorScene()
    {
        System.out.println("Inside editor scene");
    }

    @Override
    public void init()
    {
        System.out.println("Initializing Shader");
        //Compile and link shaders//
        //First: Vertex Shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexShaderSrc);//pass the shader source to GPU
        glCompileShader(vertexID);

        if(glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE)
        {
            int lenLog = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaul.glsl' \n\tVertexShader Failed to compile");
            System.out.println(glGetShaderInfoLog(vertexID,lenLog));
            assert false : "";
        }


        //Second: fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentShaderSrc);// Pass the shader source to the GPU
        glCompileShader(fragmentID);

        if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE)
        {
            int lenLog = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, lenLog));
            assert false : "";
        }

        //Last: Link shaders
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        if(glGetProgrami(shaderProgram,GL_LINK_STATUS) == GL_FALSE)
        {
            int lenLog = glGetProgrami(shaderProgram,GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'default.glsl' \n\tLinking Shaders in Program Failed");
            System.out.println(glGetProgramInfoLog(shaderProgram,lenLog));
            assert false:"";
        }


        //- Generate VAO, VBO, and EBO buffer objects://
        // +.create VAO:
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // +.create VBO & pass buffer to it:
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length); //create fit size array buffer
        vertexBuffer.put(vertexArray).flip();//put arr into buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        glBufferData(GL_ARRAY_BUFFER,vertexBuffer,GL_STATIC_DRAW);

        // +.create EBO & pass buffer to it:
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length); //create fit size array buffer
        elementBuffer.put(elementArray).flip();//put arr into buffer
        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,elementBuffer,GL_STATIC_DRAW);

        //- Specify for GPU://
        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;

        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
        //

        System.out.println("Shader initialized successfully");
    }
    @Override
    public void update(float dt)
    {
        // Bind shader program
        glUseProgram(shaderProgram);
        // Bind the VAO that we're using
        glBindVertexArray(vaoID);
        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 1);
        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        glUseProgram(0);
    }
}
