package com.infogoer.helicopterone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation walkLeft;
    private Animation walkRight;

    private AnimationManager animationManager;

    public Rect getRectangle() {
        return rectangle;
    }

    public RectPlayer(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue);
        Bitmap walk1Img = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk1);
        Bitmap walk2Img = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk2);
        idle = new Animation(new Bitmap[]{idleImg}, 2f);
        walkRight = new Animation(new Bitmap[]{walk1Img, walk2Img}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1Img = Bitmap.createBitmap(walk1Img, 0, 0, walk1Img.getWidth(), walk1Img.getHeight(), m, false);
        walk2Img = Bitmap.createBitmap(walk2Img, 0, 0, walk2Img.getWidth(), walk2Img.getHeight(), m, false);
        walkLeft = new Animation(new Bitmap[]{walk1Img, walk2Img}, 0.5f);

        animationManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});
    }


    @Override
    public void draw(Canvas canvas) {
        //Paint paint = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle, paint);
        animationManager.draw(canvas, rectangle);

    }

    @Override
    public void update() {
        animationManager.update();
    }

    public void update(Point point) {
        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2,
                point.y - rectangle.height()/2,
                point.x + rectangle.width()/2,
                point.y + rectangle.height()/2);

        int state = 0;
        if(rectangle.left-oldLeft > 5)  {
            state = 1;
        } else if (rectangle.left-oldLeft < -5) {
            state = 2;
        }
        animationManager.playAnim(state);
        animationManager.update();

    }
}
