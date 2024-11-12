package POWJA;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener
{
    private static KeyListener instance;
    private boolean bKeyPressedArr[] = new boolean[GLFW_KEY_LAST];

    private KeyListener(){}

    public static KeyListener getInstKeyListener()
    {
        if(KeyListener.instance == null) KeyListener.instance = new KeyListener();
        return KeyListener.instance;
    }

    public static void key_callback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) getInstKeyListener().bKeyPressedArr[key] = true;
        else if (action == GLFW_RELEASE) getInstKeyListener().bKeyPressedArr[key] = false;
        //System.out.println("Key: "+key + ", scancode:"+ scancode +", action: "+ action + ", modifiers: " + mods);
        //action : 0-releas, 1-press, 2- hold; mods: 0 = no; 1 = shift; 2 = ctrl; 4 = alt; 8 = window
    }

    public static boolean isKeyPressed(int keyCode) {
        return getInstKeyListener().bKeyPressedArr[keyCode];
    }
}
