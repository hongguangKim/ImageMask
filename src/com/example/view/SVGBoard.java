package com.example.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.example.demomask.R;
import com.svgandroid.SVG;
import com.svgandroid.SVGParser;

public class SVGBoard extends BaseBoard {
	protected Context mContext;
	protected int svgResId;
	
	public SVGBoard(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,  R.styleable.CustomShapeImageView);
		svgResId = a.getResourceId(R.styleable.CustomShapeImageView_svg_raw_resource, R.raw.shape_star);
		mContext = context;
	}

	@Override
	public Bitmap getBitmap(int width, int height) {
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);

		SVG svg = SVGParser.getSVGFromInputStream(mContext.getResources().openRawResource(svgResId), width, height);
		canvas.drawPicture(svg.getPicture());

		return bitmap;
	}

}
