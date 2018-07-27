package com.itcse.zoomableradar.library;

import android.support.annotation.NonNull;
import android.view.ScaleGestureDetector;

public class OnPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

  @NonNull
  private final ZoomableRadarLayout zoomableRadarLayout;
  float startingSpan;
  float endSpan;
  float startFocusX;
  float startFocusY;

  OnPinchListener(@NonNull final ZoomableRadarLayout zoomableRadarLayout) {
    this.zoomableRadarLayout = zoomableRadarLayout;
  }

  public boolean onScaleBegin(ScaleGestureDetector detector) {
    startingSpan = detector.getCurrentSpan();
    startFocusX = detector.getFocusX();
    startFocusY = detector.getFocusY();
    return true;
  }

  public boolean onScale(ScaleGestureDetector detector) {
    final float zoomLevel = detector.getCurrentSpan()*40;// * detector.getCurrentSpan();
    zoomableRadarLayout.scale(zoomLevel/startingSpan , startFocusX, startFocusY);
    return true;
  }

  public void onScaleEnd(ScaleGestureDetector detector) {
    zoomableRadarLayout.restore();
  }
}