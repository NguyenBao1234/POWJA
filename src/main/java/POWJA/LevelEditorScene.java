package POWJA;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene
{
    private boolean bChangingScene = false;
    private float transitionTime = 2;
    public LevelEditorScene()
    {
        Window.getWindow().r = 0;
        Window.getWindow().g = 0.8f;
        Window.getWindow().b = 0.5f;
        System.out.println("Inside editor scene");
    }

    @Override
    public void update(float dt)
    {
        if(!bChangingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) bChangingScene = true;
        if(bChangingScene && transitionTime > 0)
        {
            transitionTime -= dt;
            Window.getWindow().r -= dt * 5;
            Window.getWindow().g -= dt * 5;
            Window.getWindow().b -= dt * 5;
        }
        else if (bChangingScene)
        {
            Window.UpdateScene(1);
            bChangingScene = false;
        }
    }
}
