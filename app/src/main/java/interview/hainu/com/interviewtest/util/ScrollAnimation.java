package interview.hainu.com.interviewtest.util;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by haijun on 2016/5/8.
 */
public class ScrollAnimation extends Animation {
    private static final String TAG = "ScrollAnimation";
    private View view;
    private int totalValue;
    private int startScrollX;
    private int targetScrollX;

    public ScrollAnimation(View view,int targetScrollX) {
        this.view = view;
        this.targetScrollX = targetScrollX;

        startScrollX = view.getScrollX();
        totalValue = this.targetScrollX-startScrollX;
        Log.i(TAG,"totalValue:"+totalValue);
        //根据滑动的x的总的变化值确定动画的持续时间
        int time = Math.abs(totalValue);
        setDuration(time);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        int currentX = (int) (startScrollX+totalValue*interpolatedTime);
        view.scrollTo(currentX,0);
    }
}
