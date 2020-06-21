package com.example.customview.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.customview.R;

/**
 * description 自定义组合控件
 * 使用步骤：
 * 1、继承自LinearLayout/RelativeLayout，实现前3个构造方法，确保统一入口
 * 2、把其他的子view加载进来
 * 3、使用时候将该类名copy references然后在布局中使用即可
 * 4、处理数据，处理事件
 * 5、暴露接口
 *
 * create by xiaocai on 2020/6/21
 */
public class InputNumberView extends RelativeLayout {

    private static final String TAG = "InputNumberView";
    //当前的数值为0
    private int mCurrentNumber = 0;
    private View mMinusBtn;
    private View mPlusBtn;
    private EditText mNumberEt;
    private OnNumberNumberChangeListener mOnNumberNumberChangeListener = null;
    private int mMax;
    private int mMin;
    private int mStep;
    private boolean mDisable;
    private int mResourceId;

    public InputNumberView(Context context) {
        this(context, null);
    }

    public InputNumberView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        //获取相关属性
        initAttrs(context, attrs);

        //设置事件
        sertUpEvent();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputNumberView);
        mMax = typedArray.getInt(R.styleable.InputNumberView_max, 0);
        mMin = typedArray.getInt(R.styleable.InputNumberView_min, 0);
        mStep = typedArray.getInt(R.styleable.InputNumberView_step, 1);
        mDisable = typedArray.getBoolean(R.styleable.InputNumberView_disable, false);
        mResourceId = typedArray.getResourceId(R.styleable.InputNumberView_btnBackground, -1);
        Log.d(TAG, "mMax----->" + mMax);
        Log.d(TAG, "mMin----->" + mMin);
        Log.d(TAG, "mStep----->" + mStep);
        Log.d(TAG, "mDisable----->" + mDisable);
        Log.d(TAG, "mResourceId----->" + mResourceId);
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
    }

    public int getMin() {
        return mMin;
    }

    public void setMin(int min) {
        mMin = min;
    }

    public int getStep() {
        return mStep;
    }

    public void setStep(int step) {
        mStep = step;
    }

    public boolean isDisable() {
        return mDisable;
    }

    public void setDisable(boolean disable) {
        mDisable = disable;
    }

    public int getResourceId() {
        return mResourceId;
    }

    public void setResourceId(int resourceId) {
        mResourceId = resourceId;
    }

    private void sertUpEvent() {
        //-
        mMinusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //在-到上限的时候设置可以+
                mPlusBtn.setEnabled(true);
                mCurrentNumber -= mStep;
                if (mCurrentNumber <= mMin) {
                    mCurrentNumber = mMin;
                    Log.d(TAG, "超出下限");
                    //设置button为不可点击
                    mMinusBtn.setEnabled(!mDisable);
                }
                updateText();
            }
        });

        //+
        mPlusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNumber += mStep;
                //在+到上限的时候设置可以-
                mMinusBtn.setEnabled(true);
                if (mCurrentNumber >= mMax) {
                    mCurrentNumber = mMax;
                    Log.d(TAG, "超出上限");
                    //设置button为不可点击
                    mPlusBtn.setEnabled(!mDisable);
                }
                updateText();
            }
        });
    }

    /**
     * 更新数字
     */
    private void updateText() {
        mNumberEt.setText(mCurrentNumber + "");
        //更新数据，让listen监听数据变化
        if (mOnNumberNumberChangeListener != null) {
            mOnNumberNumberChangeListener.onNumberChange(mCurrentNumber);
        }
    }


    private void initView(Context context) {
        //将子view加载进来
        //inflate中的参数：布局文件，根布局（此处的根布局就是当前这个布局即this）,attach（true：把从布局中加载的view绑定到当前布局中去；false：需要自己手动添加一下）
        //attach:true的情况（默认为true,可以不填内容）
        LayoutInflater.from(context).inflate(R.layout.input_number_view, this, true);
        //attach:false
//        View view = LayoutInflater.from(context).inflate(R.layout.input_number_view, this, false);
//        this.addView(view);

        //找到控件
        mMinusBtn = findViewById(R.id.minus_btn);
        mPlusBtn = findViewById(R.id.plus_btn);
        mNumberEt = findViewById(R.id.number);

        //初始化控件值
        updateText();
    }

    /**
     * 暴露方法让外部去设置数值
     *
     * @return
     */
    public int getNumber() {
        return mCurrentNumber;
    }

    public void setNumver(int number) {
        mCurrentNumber = number;
        updateText();
    }

    public void setOnNumberNumberChangeListener(OnNumberNumberChangeListener listener) {
        this.mOnNumberNumberChangeListener = listener;
    }

    public interface OnNumberNumberChangeListener {
        void onNumberChange(int value);
    }

}
