package interview.hainu.com.interviewtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import interview.hainu.com.interviewtest.util.ScrollAnimation;

/**
 * Created by haijun on 2016/5/8.
 */
public class SlideMenu  extends FrameLayout{
    private static final String TAG = "SlideMenu";
    private View contentView,menuView;
    private int menuWidth;
    private ScrollAnimation scrollAnimation;
    private Scroller scroller ;

    public SlideMenu(Context context) {
        super(context);
        init();
    }


    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        menuView = getChildAt(1);
        menuWidth = menuView.getLayoutParams().width;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        menuView.layout(-menuWidth,0,0,menuView.getMeasuredHeight());
        //Log.i(TAG,"l:"+l+"t:"+t+"r:"+r+"b:"+b);
        contentView.layout(0,0,r,b);
    }

    int doumX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                doumX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int dletaX = moveX-doumX;
                //getScrollX()获取正在滑动的X的值
                int newScrollX = getScrollX() - dletaX; //滑动的X,滑动时是以底边框，所以为负值表示往右滑
                if (newScrollX<-menuWidth){
                    newScrollX = -menuWidth;
                }
                if (newScrollX>0){
                    newScrollX = 0;
                }

                scrollTo(newScrollX,0);
                doumX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX()>-menuWidth/2){
                    //关掉动画
                    //scrollAnimation = new ScrollAnimation(this,-menuWidth);
                    //scrollTo(-menuWidth,0);
                    scroller.startScroll(getScrollX(),0,0-getScrollX(),0,400);
                    invalidate();
                }
                else {
                    //开启动画
                    //scrollAnimation = new ScrollAnimation(this,0);
                    //scrollTo(0,0);
                    scroller.startScroll(getScrollX(),0,-menuWidth-getScrollX(),0,400);
                    invalidate();
                }
                //startAnimation(scrollAnimation);
                break;
        }


        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }


}
