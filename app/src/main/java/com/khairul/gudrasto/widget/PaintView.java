package com.khairul.gudrasto.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.khairul.gudrasto.common.Common;
import com.khairul.gudrasto.utils.FloodFill;

public class PaintView extends View {

    Bitmap bitmap;

    public PaintView(Context context) {
        super(context);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //smooth fill color
//        if (bitmap == null) {
            //no smooth just add 2 line below, and u can delete all if above
            Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), Common.PICTURE_SELECTED);
            bitmap = Bitmap.createScaledBitmap(srcBitmap, w, h, false);

//            for (int i=0; i<bitmap.getWidth();i++){
//                for (int j=0; j<bitmap.getHeight();j++){
//                    int alpha = 255 - brightness(bitmap.getPixel(i,j));
//
//                    if (alpha < 200){
//                        bitmap.setPixel(i,j, Color.WHITE);
//                    }else {
//                        bitmap.setPixel(i,j,Color.BLACK);
//                    }
//                }
//            }
//        }
    }

    private int brightness(int color) {
        return (color >> 16) & 0xff;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0,0,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        paint((int)event.getX(),(int)event.getY());

        return true;
    }

    private void paint(int x, int y){
        int targetColor = bitmap.getPixel(x,y);

//        for smooth colored image
//        if (targetColor != Color.BLACK){
//            no smooth just add 2 line below, and u can delete it
            FloodFill.floodfill(bitmap, new Point(x, y), targetColor, Common.COLOR_SELECTED);
            invalidate();
//        }


    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}
