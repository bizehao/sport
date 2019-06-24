package com.sport.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.sport.R;
import com.sport.utilities.GlobalUtil;
import com.sport.utilities.SharePreferencesUtil;
import timber.log.Timber;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * User: bizehao
 * Date: 2019-05-09
 * Time: 下午5:16
 * Description:
 */
public class MyLineView extends View {

    private Context mContext;
    private Event event; //事件

    private Paint mPaint;
    private Paint mLinePaint; //画线的笔 目的地
    private Paint mIntLinePaint; //画间隔线的笔
    private Paint mDesPaint; //画当前目的地的笔

    private Path mPath; //路径

    private float defWidth = ViewGroup.LayoutParams.MATCH_PARENT; //默认宽度 dp
    private float defHeight = 40; //默认高度 dp

    private int CircleColor;//圆的的颜色
    private int aimsCount; //目标的数目
    private float lineWidth; //横线的宽度

    public MyLineView(Context context) {
        this(context, null);
    }

    public MyLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyLineView);
        initAttrs(typedArray);
        initOther();
        initPaint();
    }

    //初始化属性
    private void initAttrs(TypedArray typedArray) {
        CircleColor = typedArray.getColor(R.styleable.MyLineView_lineColor, Color.BLUE);
        typedArray.recycle();
    }

    //初始化一些别的属性
    private void initOther() {
        lineWidth = 15f;
        aimsCount = 50;
    }

    //初始化画笔
    private void initPaint() {
        mPath = new Path();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(CircleColor);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(CircleColor);
        mLinePaint.setStrokeCap(Paint.Cap.SQUARE);
        mLinePaint.setStyle(Paint.Style.STROKE);

        mIntLinePaint = new Paint();
        mIntLinePaint.setAntiAlias(true);
        mIntLinePaint.setColor(CircleColor);
        mIntLinePaint.setStrokeCap(Paint.Cap.SQUARE);
        mIntLinePaint.setStrokeWidth(5f);
        mIntLinePaint.setStyle(Paint.Style.STROKE);

        mDesPaint = new Paint();
        mDesPaint.setAntiAlias(true);
        mDesPaint.setColor(CircleColor);
        mDesPaint.setStrokeCap(Paint.Cap.SQUARE);
        mDesPaint.setStrokeWidth(5f);
        mDesPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = getMeasureSize(widthMeasureSpec, 0);
        int heightSize = getMeasureSize(heightMeasureSpec, 1);
        setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final float paddingStart = getPaddingStart(); //内边距 左
        final float paddingEnd = getPaddingEnd(); //内边距 右
        final float paddingTop = getPaddingTop(); //内边距 上
        final float paddingBottom = getPaddingBottom(); //内边距 下

        float width = getWidth(); //控件的宽
        float height = getHeight(); //控件的高

        float lineLength = width - paddingStart - paddingEnd;

        //mLinePaint.setStrokeWidth(height/3);

        mLinePaint.setStrokeWidth(Math.min(lineWidth, height / 3));
        canvas.drawLine(paddingStart, height * 3 / 4, width - paddingEnd, height * 3 / 4, mLinePaint);

        float interval = lineLength / (aimsCount - 1); //间隔

        float intervalX = 0;

        for (int i = 0; i < aimsCount; i++) {
            intervalX = i * interval + paddingStart;
            canvas.drawLine(intervalX, height * 3 / 4, intervalX, height / 2, mIntLinePaint);
        }

        mPath.moveTo(paddingStart, height / 2);
        mPath.lineTo(paddingStart, 10f);
        mPath.lineTo(paddingStart + interval, height * 1 / 4);
        mPath.lineTo(paddingStart, height * 1 / 4);
        canvas.drawPath(mPath, mDesPaint);


        /*float radius = Math.min(width, height) / 2;
        canvas.drawCircle(width / 2, height / 2, radius, mPaint);*/
    }

    public interface Event{
        void onClickListener(View view);
    }

    public void addOnClickListener(Event event){
        event.onClickListener(this);
    }

    /**
     * 获取规格大小
     *
     * @param measureSpec    //测量规格
     * @param weightOrHeight 0 表示宽  1 表示高
     * @return
     */
    private int getMeasureSize(int measureSpec, int weightOrHeight) {

        int result = 0; //默认大小

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            float def = 0;
            if (weightOrHeight == 0) {
                def = defWidth;
            } else {
                def = defHeight;
            }
            result = (int) GlobalUtil.INSTANCE.dp2px(mContext, def);
        }
        return result;
    }
}
