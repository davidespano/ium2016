package com.example.tutor_ium.esercitazione_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Tutor_IUM on 07/11/2016.
 */

public class LineGraph extends View {
    public LineGraph(Context context) {
        super(context);
    }

    public LineGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        canvas.drawRect(0, 0, canvas.getWidth(),
                        canvas.getHeight(), paint);

        canvas.save();
    }


}
