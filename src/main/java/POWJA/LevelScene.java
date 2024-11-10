package POWJA;

import java.awt.event.KeyEvent;

public class LevelScene extends Scene
{
    public LevelScene()
    {
        Window.getWindow().r = 0;
        Window.getWindow().g = 0.7f;
        Window.getWindow().b = 0.8f;
    }
    @Override
    public void update(float dt)
    {
        if(KeyListener.isKeyPressed(KeyEvent.VK_E)) Window.UpdateScene(0);
    }
}
