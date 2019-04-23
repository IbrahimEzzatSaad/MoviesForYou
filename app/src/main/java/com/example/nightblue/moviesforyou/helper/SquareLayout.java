package com.example.nightblue.moviesforyou.helper;

/**
 * Created by NightBlue on 30/04/2018.
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

class SquareLayout extends RelativeLayout {
    public SquareLayout(Context context){
        super(context);
    }
    public SquareLayout(Context context, AttributeSet atters){
        super(context,atters);
    }
    public SquareLayout(Context context, AttributeSet attrs, int deStyleAttr){
        super(context, attrs, deStyleAttr);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

    }

}
