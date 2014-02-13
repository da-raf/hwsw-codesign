package com.hwswcodesign.legoapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.lang.Math;

// class that paints the controlling elements on the view and calculates finger position relative to the steering wheel

public class ControllingView extends View implements View.OnTouchListener {

	public static float factor = 0.75f;
	private Paint paint;
	boolean firstPaint, not_pressed;
	float center[];
	float x, y;
	float cross_size;
	double f;
	final DashPathEffect dash = new DashPathEffect(new float[] { 20, 20 }, 0);

	public ControllingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnTouchListener(this);

		paint = new Paint();
		center = new float[2];
		firstPaint = true;
		not_pressed = true;
	}

	//get onTouch info and whether the finger is lifted or not
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		x = event.getX();
		y = event.getY();
		if (event.getActionMasked() == MotionEvent.ACTION_UP)
			not_pressed = true;
		else
			not_pressed = false;

		invalidate();
		return true;
	}

	//paints the canvas
	protected void onDraw(Canvas canvas) {
		if (firstPaint) {

			center[0] = getWidth() / 2.0f;
			center[1] = getHeight() / 2.0f;

			cross_size = factor * getWidth() / 2;
		}

		f = Math.sqrt(Math.pow((center[0] - x), 2)
				+ Math.pow((center[1] - y), 2));

		paint.setColor(Color.WHITE);
		paint.setTextSize(50);

		// Text showing x, y values, relative to cross center
		if (not_pressed) {
			canvas.drawText("x: " + Float.toString(0), 10, 100, paint);
			canvas.drawText("y: " + Float.toString(0), 10, 200, paint);
		} else {
			if (f <= cross_size) {
				canvas.drawText("x: " + Float.toString(x - center[0]), 10, 100,
						paint);
				canvas.drawText("y: " + Float.toString(center[1] - y), 10, 200,
						paint);
			} else {
				float v1 = (float) ((x - center[0]) * (1 / f));
				float v2 = (float) ((y - center[1]) * (1 / f));
				canvas.drawText(
						"x: "
								+ Float.toString((center[0] + cross_size * v1)
										- center[0]), 10, 100, paint);
				canvas.drawText(
						"y: "
								+ Float.toString(center[1]
										- (center[1] + cross_size * v2)), 10,
						200, paint);
			}
		}

		paint.setStrokeWidth(10);
		// vertical line:
		canvas.drawLine(center[0], center[1] - cross_size, center[0], center[1]
				+ cross_size, paint);
		// horizontal line:
		canvas.drawLine(center[0] - cross_size, center[1], center[0]
				+ cross_size, center[1], paint);

		// circles:
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(center[0], center[1], cross_size, paint);
		paint.setPathEffect(dash);
		canvas.drawCircle(center[0], center[1], cross_size * 0.25f, paint);
		canvas.drawCircle(center[0], center[1], cross_size * 0.5f, paint);
		canvas.drawCircle(center[0], center[1], cross_size * 0.75f, paint);
		paint.setPathEffect(null);

		// touchpoint, depending on finger position/finger lifted:
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.CYAN);

		if (not_pressed) {
			canvas.drawCircle(center[0], center[1], 40, paint);
		} else {
			if (f <= cross_size) {
				canvas.drawCircle(x, y, 40, paint);
			} else {
				float v1 = (float) ((x - center[0]) * (1 / f));
				float v2 = (float) ((y - center[1]) * (1 / f));
				canvas.drawCircle(center[0] + cross_size * v1, center[1]
						+ cross_size * v2, 40, paint);
			}
		}

		super.onDraw(canvas);
	}

}
