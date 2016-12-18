package tech.shutu.androidpainterdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author : raomengyang on 2016/12/8.
 */

public class DrawingCanvasView extends View implements IDrawingView {

    private Canvas mLayerCanvas;
    private Paint mBrushPaint;
    private Paint mErasePaint;
    private Bitmap mLayerBitmap;
    private int mEraseColor;
    public float strokeProgress = 30;
    private boolean isEraseMode = false;
    private boolean enablePainter = false;

    private float lastX, lastY;
    private Path pencilPath;

    public DrawingCanvasView(Context context) {
        this(context, null);
    }

    public DrawingCanvasView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawingCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mBrushPaint = new Paint();
        mBrushPaint.setAntiAlias(true);
        mBrushPaint.setColor(Color.BLACK);
        mBrushPaint.setStyle(Paint.Style.STROKE);
        mBrushPaint.setStrokeCap(Paint.Cap.ROUND);
        mErasePaint = new Paint();
        mErasePaint.setAntiAlias(true);
        mErasePaint.setColor(Color.TRANSPARENT);
        mErasePaint.setStyle(Paint.Style.STROKE);
        mErasePaint.setStrokeCap(Paint.Cap.SQUARE);
        mEraseColor = Color.WHITE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCanvas(canvas);
    }

    private void drawCanvas(Canvas canvas) {
        if (mLayerBitmap != null && !mLayerBitmap.isRecycled()) {
            canvas.drawBitmap(mLayerBitmap, 0, 0, null);
        }
        mBrushPaint.setStrokeWidth(strokeProgress);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int act = event.getAction();

        if (act == MotionEvent.ACTION_DOWN) {
            pencilPath = new Path();
            pencilPath.setLastPoint(x, y);
        } else if (act == MotionEvent.ACTION_MOVE) {
            float mx = (x + lastX) / 2;
            float my = (y + lastY) / 2;
            pencilPath.quadTo(lastX, lastY, mx, my);
            mLayerCanvas.drawPath(pencilPath, mBrushPaint);
        } else if (act == MotionEvent.ACTION_UP) {
            pencilPath.lineTo(x + 0.1f, y + 0.1f);
            mLayerCanvas.drawPath(pencilPath, mBrushPaint);
            pencilPath = null;
        }

        lastX = x;
        lastY = y;

        invalidate();
        if (enablePainter) return true;
        else return super.onTouchEvent(event);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLayerBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mLayerCanvas = new Canvas(mLayerBitmap);
    }

    public void setStrokeWidth(float strokeProgress) {
        this.strokeProgress = strokeProgress;
    }

    private void setBrushColor(int color) {
        mBrushPaint.setColor(color);
    }

    void clearCanvas(Canvas cv) {
        cv.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    @Override
    public void clear() {
        mLayerCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    @Override
    public void erase() {
        isEraseMode = !isEraseMode;
    }

    @Override
    public void redo() {

    }

    @Override
    public void undo() {

    }

    @Override
    public void pickColor(int color) {
        setBrushColor(color);
    }

    public boolean isEnablePainter() {
        return enablePainter;
    }

    public void setEnablePainter(boolean enablePainter) {
        this.enablePainter = enablePainter;
    }

    public Bitmap getmLayerBitmap() {
        return mLayerBitmap;
    }

    public Canvas getmLayerCanvas() {
        return mLayerCanvas;
    }

    public void releaseAllResource() {
        if (mLayerBitmap != null && !mLayerBitmap.isRecycled()) {
            mLayerBitmap.recycle();
            mLayerBitmap = null;
        }
    }
}
