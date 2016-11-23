package com.example.view;

import java.lang.ref.WeakReference;

import com.example.demomask.R;
import com.example.demomask.R.styleable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

public abstract class BaseBoard extends ImageView {
	private static final String TAG = BaseBoard.class.getSimpleName();

	protected Context mContext;

	private static final Xfermode sXfermode = new PorterDuffXfermode(
			PorterDuff.Mode.DST_IN);
	private Bitmap mMaskBitmap;
	private Paint mPaint;
	private WeakReference<Bitmap> mWeakBitmap;
	private int size = 18;
	private int colum=4,row=4;
	public BaseBoard(Context context) {
		super(context);
		sharedConstructor(context,null);
	}

	public BaseBoard(Context context, AttributeSet attrs) {
		super(context, attrs);
		sharedConstructor(context,attrs);
	}

	public BaseBoard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		sharedConstructor(context,attrs);
	}

	private void sharedConstructor(Context context, AttributeSet attrs) {
		if(attrs!=null){
			TypedArray a = context.obtainStyledAttributes(attrs,  R.styleable.CustomShapeImageView);  
			colum = a.getInt(R.styleable.CustomShapeImageView_colum, colum);
			row = a.getInt(R.styleable.CustomShapeImageView_row, row);
		}
		mContext = context;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	@Override
	public void invalidate() {
		mWeakBitmap = null;
		if (mMaskBitmap != null) {
			mMaskBitmap.recycle();
		}
		super.invalidate();
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
			@Override  
            public void onGlobalLayout() { 
				BaseBoard.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            	size = getItemWidth();
            	BaseBoard.this.invalidate();
            }   
        });
		super.onAttachedToWindow();
	}
	
	protected int getItemWidth(){
		Log.i(TAG,"getItemWidth = "+getMeasuredWidth());
		return getWidth()/(colum+2);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (!isInEditMode()) {
			for (int x = 1; x <= row; x++)
				for (int y = 1; y <= colum; y++) {
					canvas.drawBitmap(getCompositeBitmap(), 0.0f + y * size,0.0f+ x * size, mPaint);
				}
		} else {
			super.onDraw(canvas);
		}
	}
	
	protected Bitmap getCompositeBitmap() {
		Bitmap bitmap = mWeakBitmap != null ? mWeakBitmap.get() : null;
		// Bitmap not loaded.
		if (bitmap == null || bitmap.isRecycled()) {
			Drawable drawable = getDrawable();
			if (drawable != null) {
				bitmap = Bitmap.createBitmap(size, size,
						Bitmap.Config.ARGB_8888);
				Canvas bitmapCanvas = new Canvas(bitmap);
				drawable.setBounds(0, 0, size, size);
				drawable.draw(bitmapCanvas);

				// If mask is already set, skip and use cached mask.
				if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
					mMaskBitmap = getBitmap(size,size);
				}

				// Draw Bitmap.
				mPaint.reset();
				mPaint.setFilterBitmap(false);
				mPaint.setXfermode(sXfermode);
				bitmapCanvas.drawBitmap(mMaskBitmap, 0.0f, 0.0f, mPaint);
				mWeakBitmap = new WeakReference<Bitmap>(bitmap);
			}
			return bitmap;
		}

		// Bitmap already loaded.
		mPaint.setXfermode(null);
		// mPaint.setShader(null);
		return bitmap;

	}
	
	protected int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	public abstract Bitmap getBitmap(int width, int height);

}
