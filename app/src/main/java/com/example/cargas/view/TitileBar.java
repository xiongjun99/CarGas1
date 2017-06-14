package com.example.cargas.view;

import com.example.cargas.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Totoy
 *
 */
public class TitileBar extends RelativeLayout {


	public TitileBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TitileBar(Context context) {
		this(context, null);
	}

	public TitileBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		initView(context, attrs);
	}
	
	private void initView(Context context, AttributeSet attrs){
		
		TextView mLeftText = new TextView(context);
		ImageView mRightImage = new ImageView(context);
		
		//获取自定义的属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
		String leftText = typedArray.getString(R.styleable.TitleBar_leftText);
		float leftTextSize = typedArray.getDimension(R.styleable.TitleBar_leftTextSize, 16);
		int leftTextColor = typedArray.getColor(R.styleable.TitleBar_leftTextColor, 0);
		int leftMargin =  (int) typedArray.getDimension(R.styleable.TitleBar_leftTextMarginLeft, 0);
		int topMargin =  (int) typedArray.getDimension(R.styleable.TitleBar_leftTextMarginTop, 0);
		int bottomMargin =  (int) typedArray.getDimension(R.styleable.TitleBar_leftTextMarginBottom, 0);
		
		Drawable rightImage = typedArray.getDrawable(R.styleable.TitleBar_rightSrc);
		int rightImageRightMargin =  (int) typedArray.getDimension(R.styleable.TitleBar_rightSrcMarginRight, 0);
		int rightImageTopMargin =  (int) typedArray.getDimension(R.styleable.TitleBar_rightSrcMarginTop, 0);
		int rightImageBottomMargin =  (int) typedArray.getDimension(R.styleable.TitleBar_rightSrcMarginBottom, 0);
		
		//设置textView
		LayoutParams params1= new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
		params1.leftMargin = leftMargin;
		params1.topMargin = topMargin;
		params1.bottomMargin = bottomMargin;
		mLeftText.setText(leftText);
		mLeftText.setTextSize(50);
		mLeftText.setTextColor(leftTextColor);
		addView(mLeftText, params1);//将textview添加进去
		
		//设置Imageview
		LayoutParams params2= new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
		params2.rightMargin = rightImageRightMargin;
		params2.topMargin = rightImageTopMargin;
		params2.bottomMargin = rightImageBottomMargin;
		mRightImage.setImageDrawable(rightImage);
		mRightImage.setLayoutParams(params2);
		addView(mRightImage, params2);
		
	}
}
