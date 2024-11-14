package POWJA;
import Utilities.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
    private int Width,Height;
    private String Title;
    private long glfwWindowAdress;
    private static Window window = null;

    public float r = 1, g = 0.7f, b= 0.5f, a;
    private static Scene currentScene;
    private Window()
    {
        this.Width = 1280;
        this.Height = 720;
        this.Title = "POWJA engine";
    }
    public static void UpdateScene(int SceneIndex)
    {
        switch (SceneIndex)
        {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                break;
            default:
                assert false : "Unknown Scene:'" + SceneIndex +"'";
                break;
        }
    }
    public static Window getWindow()
    {
        if(Window.window == null) Window.window = new Window();
        return Window.window;
    }

    public void run()
    {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();
    }

    public void init()
    {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW. Every thing just create without specific state
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE);


        //create actual GLFW Window
        glfwWindowAdress = glfwCreateWindow(this.Width,this.Height,this.Title,NULL,NULL);
        if(glfwWindowAdress == NULL) throw new IllegalStateException("Failed to create glfwWindow");
        //setup for created GLFW Window:
        glfwSetCursorPosCallback(glfwWindowAdress, MouseListener::cursor_position_callback);
        glfwSetMouseButtonCallback(glfwWindowAdress,MouseListener::mouse_button_callback);
        glfwSetScrollCallback(glfwWindowAdress, MouseListener::scroll_callback);
        glfwSetKeyCallback(glfwWindowAdress,KeyListener::key_callback);

        glfwMakeContextCurrent(glfwWindowAdress); //make the OpenGL context current
        glfwSwapInterval(1); //enable v-sync
        glfwShowWindow(glfwWindowAdress);
           /*This line is critical for LWJGL's interoperation with GLFW's
           OpenGL context, or any context that is managed externally.
           LWJGL detects the context that is current in the current thread,
           creates the GLCapabilities instance and makes the OpenGL
           bindings available for use.*/
        GL.createCapabilities();

        UpdateScene(0);

        System.out.println("Success Initialized Window");

    }

    public void loop()
    {
        float beginTime = Time.getTime();
        float endTime;
        float dt =-1 ;


        while (!glfwWindowShouldClose(glfwWindowAdress))
        {
            // Poll events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindowAdress);

            if(dt > 0) currentScene.update(dt);
            endTime = Time.getTime();
            dt = endTime-beginTime;
            beginTime = endTime;

        }
    }
}
