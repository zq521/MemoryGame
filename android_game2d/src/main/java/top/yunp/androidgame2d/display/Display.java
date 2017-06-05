package top.yunp.androidgame2d.display;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;

import top.yunp.androidgame2d.events.TouchEvent;

import top.yunp.lib.java.event.EventListenerList;

public abstract class Display {

    void internal_draw(Canvas canvas) {
        canvas.save();
        canvas.translate(x - regX, y - regY);
        canvas.rotate(rotation, regX, regY);
        canvas.scale(scaleX, scaleY, regX, regY);
        draw(canvas);
        canvas.restore();
    }


    public Stage getRoot() {
        if (this instanceof Stage) {
            return (Stage) this;
        }
        return getParent() != null ? getParent().getRoot() : null;
    }


    public float getGlobalX() {
        if (getParent() != null) {
            return x + getParent().getGlobalX();
        }
        return 0;
    }

    public float getGlobalY() {
        if (getParent() != null) {
            return y + getParent().getGlobalY();
        }
        return 0;
    }

    public abstract void draw(Canvas canvas);

    public Container getParent() {
        return parent;
    }


    void internal_setParent(Container parent) {
        this.parent = parent;
    }

    void internal_dispatchTouchEvent(TouchEvent e) {

        if (TouchEvent.TOUCH_MOVE.equals(e.getName())) {
            touchMove.dispatch(e.clone(), this);
        } else if (TouchEvent.TOUCH_DOWN.equals(e.getName())) {
            touchDown.dispatch(e.clone(), this);
        } else if (TouchEvent.TOUCH_UP.equals(e.getName())) {
            touchUp.dispatch(e.clone(), this);
        }

        touch.dispatch(e, this);
    }

    public boolean hitTest(float x, float y) {
        return getBounds().contains(x, y);
    }

    public boolean hitTest(float left, float top, float right, float bottom) {
        return getBounds().intersect(left, top, right, bottom);
    }

    public boolean hitTest(PointF p) {
        return hitTest(p.x, p.y);
    }

    public boolean hitTest(RectF rect) {
        return hitTest(rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean hitTest(Display display) {
        return hitTest(display.getBounds());
    }

    public Matrix getBoundsMatrix() {
        boundsMatrix.reset();
        boundsMatrix.preTranslate(x - regX, y - regY);
        boundsMatrix.preRotate(rotation, regX, regY);
        boundsMatrix.preScale(scaleX, scaleY, regX, regY);
        return boundsMatrix;
    }

    private final Matrix boundsMatrix = new Matrix();

    public abstract RectF getBounds();

    public GameView getGameView() {
        Stage r = getRoot();
        return r != null ? r.getGameView() : null;
    }

    public Context getContext() {
        return getGameView() != null ? getGameView().getContext() : null;
    }

    public boolean isTouchEnable() {
        return touchEnable;
    }

    public void setTouchEnable(boolean touchEnable) {
        this.touchEnable = touchEnable;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getX() {
        return x;
    }


    public void setX(float x) {
        this.x = x;
    }


    public float getY() {
        return y;
    }


    public void setY(float y) {
        this.y = y;
    }


    public float getScaleX() {
        return scaleX;
    }


    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }


    public float getScaleY() {
        return scaleY;
    }


    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }


    public float getRotation() {
        return rotation;
    }


    public void setRotation(float rotation) {
        this.rotation = rotation;
    }


    public float getRegX() {
        return regX;
    }


    public void setRegX(float regX) {
        this.regX = regX;
    }


    public float getRegY() {
        return regY;
    }


    public void setRegY(float regY) {
        this.regY = regY;
    }


    public boolean isVisible() {
        return visible;
    }


    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private float x = 0, y = 0, scaleX = 1, scaleY = 1, rotation = 0, regX = 0, regY = 0;
    public boolean visible = true;
    private Container parent = null;
    private boolean touchEnable = true;
    private float alpha = 1;

    public final EventListenerList<TouchEvent,Display> touch = new EventListenerList<TouchEvent,Display>();
    public final EventListenerList<TouchEvent,Display> touchMove = new EventListenerList<TouchEvent,Display>();
    public final EventListenerList<TouchEvent,Display> touchDown = new EventListenerList<TouchEvent,Display>();
    public final EventListenerList<TouchEvent,Display> touchUp = new EventListenerList<TouchEvent,Display>();

    public  void removeFromParent(){
        if (getParent()!=null){
            getParent().remove(this);
        }
    };
}
