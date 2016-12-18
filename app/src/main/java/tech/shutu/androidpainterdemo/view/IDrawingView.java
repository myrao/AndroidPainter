package tech.shutu.androidpainterdemo.view;

/**
 * author : raomengyang on 2016/12/9.
 */

public interface IDrawingView {

    void clear();

    void erase();

    void redo();

    void undo();

    void pickColor(int color);
}
