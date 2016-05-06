package interview.hainu.com.interviewtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by haijun on 2016/5/6.
 */
public class MyToggleButton extends View{

    private MyToggleState myToggleState = MyToggleState.Open;  //开光状态
    private Bitmap slidBitmap;
    private Bitmap switchBitmapOn;
    private float currentX;
    private boolean isSliding = false;
    private Bitmap switchBitmapOff;

    public MyToggleButton(Context context) {
        super(context);
    }

    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setToggleState(MyToggleState state) {
        myToggleState = state;
    }

    //定义枚举变量
    public enum MyToggleState{
        Open,Close
    }


    public void setSlidBackgroundResource(int toggle_bg) {
        slidBitmap = BitmapFactory.decodeResource(getResources(), toggle_bg);

    }

    public void setSwitchBackgroundResourceOn(int switchBackgroundOn) {
        switchBitmapOn = BitmapFactory.decodeResource(getResources(), switchBackgroundOn);

    }

    public void setSwitchBackgroundResourceOff(int switchBackgroundOff) {
        switchBitmapOff = BitmapFactory.decodeResource(getResources(), switchBackgroundOff);

    }

    //设置屏幕显示的宽和高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(switchBitmapOn.getWidth(),switchBitmapOn.getHeight());
    }

    //设置显示在屏幕上的样子
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       /* //绘制背景图片
        canvas.drawBitmap(switchBitmapOn,0,0,null);*/

        float left = currentX-slidBitmap.getWidth()/2;

        if (left<0){
            left = 0;
        }
        if (left>switchBitmapOn.getWidth()-slidBitmap.getWidth()){
            left=switchBitmapOn.getWidth()-slidBitmap.getWidth();
        }

        //正在滑动
        if (isSliding){
            if (myToggleState==MyToggleState.Open){
                canvas.drawBitmap(switchBitmapOn,0,0,null);
            }
            else {
                canvas.drawBitmap(switchBitmapOff,0,0,null);
            }
            canvas.drawBitmap(slidBitmap,left,0,null);
        }
        //抬起了，停止滑动
        else{
            //绘制滑动块
            if (myToggleState==MyToggleState.Open){
                canvas.drawBitmap(switchBitmapOn,0,0,null);
                canvas.drawBitmap(slidBitmap,switchBitmapOn.getWidth()-slidBitmap.getWidth(),0,null);
            }
            else {
                canvas.drawBitmap(switchBitmapOff,0,0,null);
                canvas.drawBitmap(slidBitmap,0,0,null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isSliding = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isSliding = false;
                float centerX = switchBitmapOn.getWidth()/2;
                //越过中心位置
                if (currentX>centerX){
                    //open
                    myToggleState  = MyToggleState.Open;
                }
                else {
                    //close
                    myToggleState = MyToggleState.Close;
                }
                if (listener!=null){
                    listener.onStateChange(myToggleState);
                }
                break;
        }

        invalidate();

        //自己处理滑动事件
        return true;
    }

    private OnTaggleStateChangeListener listener;

    public void setOnTaggleStateChangeListener(OnTaggleStateChangeListener listener){
        this.listener = listener;
    }
    public interface OnTaggleStateChangeListener{
        void onStateChange(MyToggleState state);
    }

}
