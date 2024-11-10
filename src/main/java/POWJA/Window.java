package POWJA;
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

    private Window()
    {
        this.Width = 1280;
        this.Height = 720;
        this.Title = "POWJA engine";
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
        glfwMakeContextCurrent(glfwWindowAdress); //make the OpenGL context current
        glfwSwapInterval(1); //enable v-sync
        glfwShowWindow(glfwWindowAdress);

        System.out.println("Initialize Success");

    }

    public void loop()
    {
         /*This line is critical for LWJGL's interoperation with GLFW's
           OpenGL context, or any context that is managed externally.
           LWJGL detects the context that is current in the current thread,
           creates the GLCapabilities instance and makes the OpenGL
           bindings available for use.*/
        GL.createCapabilities();
        while (!glfwWindowShouldClose(glfwWindowAdress))
        {
            // Poll events
            glfwPollEvents();

            glClearColor(0, 0.7f, 0.8f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwSwapBuffers(glfwWindowAdress);

        }
    }
}
