package com.shailendra.photonotesgallery.viewer;

/**
 * Created by shailendra on 21-05-2016.
 */
import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class SimpleGestureFilter extends SimpleOnGestureListener {

    public final static int SWIPE_UP = 1;
    public final static int SWIPE_DOWN = 2;
    public final static int SWIPE_LEFT = 3;
    public final static int SWIPE_RIGHT = 4;

    public final static int MODE_TRANSPARENT = 0;
    public final static int MODE_SOLID = 1;
    public final static int MODE_DYNAMIC = 2;

    private final static int ACTION_FAKE = -13; //just an unlikely number
    private int swipe_Min_Distance = 30;
    private int swipe_Max_Distance = 50;
    private int swipe_Min_Velocity = 30;
    private int swipe_Min_Distance_vol = 100;
    private int swipe_Max_Distance_vol = 350;
    private int swipe_Min_Velocity_vol = 100;
    public int xDistance1;
    public int yDistance1;

    private int mode = MODE_DYNAMIC;
    private boolean running = true;
    private boolean tapIndicator = false;

    private Activity context;
    private GestureDetector detector;
    private SimpleGestureListener listener;

    public SimpleGestureFilter(Activity context, SimpleGestureListener sgl) {

        this.context = context;
        this.detector = new GestureDetector(context, this);
        this.listener = sgl;
    }

    public void onTouchEvent(MotionEvent event) {

        if (!this.running)
            return;

        boolean result = this.detector.onTouchEvent(event);

        if (this.mode == MODE_SOLID)
            event.setAction(MotionEvent.ACTION_CANCEL);
        else if (this.mode == MODE_DYNAMIC) {

            if (event.getAction() == ACTION_FAKE)
                event.setAction(MotionEvent.ACTION_UP);
            else if (result)
                event.setAction(MotionEvent.ACTION_CANCEL);
            else if (this.tapIndicator) {
                event.setAction(MotionEvent.ACTION_DOWN);
                this.tapIndicator = false;
            }

        }
        //else just do nothing, it's Transparent
    }

    public void setMode(int m) {
        this.mode = m;
    }

    public int getxDist()
    {
        return this.xDistance1;
    }
    public int getyDist()
    {
        return this.yDistance1;
    }

    public int getMode() {
        return this.mode;
    }

    public void setEnabled(boolean status) {
        this.running = status;
    }

    public void setSwipeMaxDistance(int distance) {
        this.swipe_Max_Distance = distance;
        this.swipe_Max_Distance_vol = distance;
    }

    public void setSwipeMinDistance(int distance) {
        this.swipe_Min_Distance = distance;
        this.swipe_Min_Distance_vol = distance;
    }

    public void setSwipeMinVelocity(int distance) {
        this.swipe_Min_Velocity = distance;
        this.swipe_Min_Distance_vol = distance;
    }

    public int getSwipeMaxDistance() {
        return this.swipe_Max_Distance;

    }

    public int getSwipeMinDistance() {
        return this.swipe_Min_Distance;
    }

    public int getSwipeMinVelocity() {
        return this.swipe_Min_Velocity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {

        final float xDistance = Math.abs(e1.getX() - e2.getX());
        final float yDistance = Math.abs(e1.getY() - e2.getY());
         xDistance1= (int)xDistance;
         yDistance1= (int)yDistance;

        if (xDistance > this.swipe_Max_Distance_vol || yDistance > this.swipe_Max_Distance_vol)
            return false;


        velocityX = Math.abs(velocityX);
        velocityY = Math.abs(velocityY);
        boolean result = false;
        if (velocityY > this.swipe_Min_Velocity_vol && yDistance > this.swipe_Min_Distance_vol) {
            if (e1.getY() > e2.getY()) // bottom to up
                this.listener.onSwipe(SWIPE_UP);
            else
                this.listener.onSwipe(SWIPE_DOWN);

            result = true;
        }

      /*  else if (velocityX > this.swipe_Min_Velocity && xDistance > this.swipe_Min_Distance) {
            if (e1.getX() > e2.getX()) // right to left
                this.listener.onSwipe(SWIPE_LEFT);
            else
                this.listener.onSwipe(SWIPE_RIGHT);

            result = true;
        }*/

        return result;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        this.tapIndicator = true;
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent arg) {
        this.listener.onDoubleTap();
        ;
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent arg) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent arg) {

        if (this.mode == MODE_DYNAMIC) {        // we owe an ACTION_UP, so we fake an
            arg.setAction(ACTION_FAKE);      //action which will be converted to an ACTION_UP later.
            this.context.dispatchTouchEvent(arg);
        }

        return false;
    }

    static interface SimpleGestureListener {
        void onSwipe(int direction);

        void onDoubleTap();
    }
}
