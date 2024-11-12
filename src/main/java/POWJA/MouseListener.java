package POWJA;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;

public class MouseListener
{
    private static MouseListener instance;
    private double scrollX,scrollY;
    private double xPos, yPos, lastX, lastY;
    private boolean bMouseBtnPressedArr[] = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private boolean bDragging;

    private MouseListener()
    {
        this.xPos = 0; this.yPos = 0; this.lastX = 0; this.lastY = 0;
        this.scrollY = 0; this.scrollX = 0;
    }

    public static MouseListener getInstMouseListener()
    {
        if(MouseListener.instance == null) MouseListener.instance = new MouseListener();
        return instance;
    }

//callback:
    public static void cursor_position_callback(long window, double xpos, double ypos)
    {
        getInstMouseListener().lastY = getInstMouseListener().yPos;
        getInstMouseListener().lastX = getInstMouseListener().xPos;
        getInstMouseListener().xPos = xpos;
        getInstMouseListener().yPos = ypos;
        System.out.println("MouseX: "+ xpos + "; Mouse: " + ypos);
    }

    public static void mouse_button_callback(long window, int button, int action, int mods)
    {
        if(button < getInstMouseListener().bMouseBtnPressedArr.length)
        {
            if (action == GLFW_PRESS) getInstMouseListener().bMouseBtnPressedArr[button] = true;
            else if (action == GLFW_RELEASE)
            {
                getInstMouseListener().bMouseBtnPressedArr[button] = false;
                getInstMouseListener().bDragging = false;
            }
            //System.out.println("MouseClick: "+ button + "; action: " + action + "; mods:"+ mods );
            //mods: 0 = no; 1 = shift; 2 = ctrl; 4 = alt; 8 = window
        }
    }

    public static void scroll_callback(long window, double xoffset, double yoffset)
    {
        getInstMouseListener().scrollX = xoffset;
        getInstMouseListener().scrollY = yoffset;
        System.out.println("ScrollMouseX: "+ xoffset + "; ScrollMouseY: " + yoffset);
    }


    public static void endFrame()
    {
        getInstMouseListener().scrollX = 0;
        getInstMouseListener().scrollY = 0;

        getInstMouseListener().lastX = getInstMouseListener().xPos;
        getInstMouseListener().lastY = getInstMouseListener().yPos;

        System.out.println("EndFrame");
    }

//getter:
    public static float getMouseX() {
        return (float) getInstMouseListener().xPos;
    }
    public static float getMouseY() {
        return (float) getInstMouseListener().yPos;
    }

    public static float getDeltaMouseX() {
        return (float)(getInstMouseListener().lastX - getInstMouseListener().xPos);
    }
    public static float getDeltaMouseY() {
        return (float)(getInstMouseListener().lastY - getInstMouseListener().yPos);
    }

    public static float getScrollX() {
        return (float)getInstMouseListener().scrollX;
    }
    public static float getScrollY() {
        return (float)getInstMouseListener().scrollY;
    }

    public static boolean IsMouseDragging() {
        return getInstMouseListener().bDragging;
    }
}
