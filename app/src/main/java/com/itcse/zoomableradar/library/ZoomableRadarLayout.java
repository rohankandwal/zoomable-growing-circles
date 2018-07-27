package com.itcse.zoomableradar.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;

public class ZoomableRadarLayout extends RelativeLayout {
  private final int width = 50, height = 50;
  int strokeWidth = 4;  // or whatever
  private float mScaleFactor = 1;
  private float mPivotX;
  private float mPivotY;
  private Context context;
  private Paint myPaint;

  public ZoomableRadarLayout(Context context) {
    super(context);
    this.context = context;
    setTouchListener();
  }

  private void setTouchListener() {
    myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    myPaint.setStyle(Paint.Style.STROKE);
    myPaint.setStrokeWidth(strokeWidth);
    myPaint.setColor(0xffff0000);   //color.RED

    final ScaleGestureDetector scaleGestureDetector =
      new ScaleGestureDetector(context, new OnPinchListener(this));
    setOnTouchListener(new OnTouchListener() {

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
      }
    });
  }

  public ZoomableRadarLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    setTouchListener();
  }

  public ZoomableRadarLayout(
    Context context, AttributeSet attrs, int defStyle
  ) {
    super(context, attrs, defStyle);
    this.context = context;
    setTouchListener();
  }

  protected void dispatchDraw(Canvas canvas) {
    Paint myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    myPaint.setStyle(Paint.Style.STROKE);
    int strokeWidth = 4;  // or whatever
    myPaint.setStrokeWidth(strokeWidth);
    myPaint.setColor(0xffff0000);   //color.RED
    float radius = (float) (0.5 * (width + height) * 2.5);
    for (int i = 1; i <= 51; i=i+10) {
      canvas.drawCircle(canvas.getWidth() / 2, canvas.getWidth() / 2, (radius) + mScaleFactor + i,
        myPaint);
    }
    canvas.save();
    super.dispatchDraw(canvas);
    canvas.restore();
  }

  public void scale(float scaleFactor, float pivotX, float pivotY) {
    mScaleFactor = scaleFactor;
    mPivotX = pivotX;
    mPivotY = pivotY;
    this.invalidate();
  }

  public void restore() {
    mScaleFactor = 1;
    this.invalidate();
  }

  private class RippleView extends View {

    public RippleView(Context context) {
      super(context);
      this.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
      int radius = (Math.min(getWidth(), getHeight())) / 2;
      canvas.drawCircle(radius, radius, radius - strokeWidth, myPaint);
    }
  }
}
