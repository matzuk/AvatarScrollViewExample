package com.matzuk.imagescrollview;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ScrollView;

/**
 * @author EMatsuk
 * 
 * AvatarScrollView is ScrollView with ONLY Top Scretchable (or Cropped) Image
 * 	 for info screens where developer places photo, phones, mails and other details about contact
 * Develeper must initialize AvatarScrollView, ImageView and 
 * 	 must call function AvatarScrollView.setResizableImage(ImageView)
 * So you can call ImageScrollView.enableAnimation(boolean) for on/off animation
 * Attention! Your ImageView must be at top of ScrollView (internal layout), has scaleType = "centerCrop" and layoutWidth = "match_parent"
 *    
 */

public class AvatarScrollView extends ScrollView {
	
	// Animation duration (when user up finger)
	private static final int DURATION = 200;
	// if difference between actual and previous user's points relative to the screen height > DEF_STEP
	// then actual ACTION_MOVE of user finger is not corrected
	private static final float DEF_STEP = 0.15f;
	private ImageView avatar;
	
	// point (y-coordinate) after ACTION_DOWN of user finger 
	private float beginningMovingY;
	// point (y-coordinate) after EACH ACTION_MOVE of user finger
	private float movingY;
	// = viewHeight 
	private float resizableScreenHeight;
	
	// previous point (y-coordinate) after ACTION_MOVE of user finger
	private float prevDifScreenY;
	
	// difference between viewWidth and beginningImageHeight
	private float resizableImageHeight;
	// image height after initialization
	private float beginningImageHeight;
	// max image height for correct cropping
	private float maxImageHeight;
	
	// Screen width (internal layout of ScrollView)
	private float viewWidth;
	// Screen height (internal layout of ScrollView)
	private float viewHeight;
	
	private boolean firstParams = true;
	private boolean animate = true;

	public AvatarScrollView(Context context) {
		super(context);
	}
	
	public AvatarScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public AvatarScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	
	public void setAvatar(ImageView avatar) {
		this.avatar = avatar;
	}
	
	public void enableAnimation(boolean b) {
		animate = b;
	}
	
	private void moveAction() {
		if (avatar == null) {
			throw new RuntimeException("Null Resizable Image!");
		}
		// get beginning height of image
		android.view.ViewGroup.LayoutParams params = avatar.getLayoutParams();
		beginningImageHeight = params.height;
		// screen orientation
		int orientation = getResources().getConfiguration().orientation;
		// get max height of image
		Bitmap bitmap = ((BitmapDrawable)avatar.getDrawable()).getBitmap();
		float coefBitmapXY = (float)bitmap.getHeight() / (float)bitmap.getWidth();
		if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			maxImageHeight = viewWidth * coefBitmapXY;
		} else {
			maxImageHeight = viewHeight * coefBitmapXY;
		}
		resizableImageHeight = maxImageHeight - beginningImageHeight;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// get views width and height
		viewWidth = MeasureSpec.getSize(widthMeasureSpec);
		viewHeight = MeasureSpec.getSize(heightMeasureSpec);		
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    if (firstParams) {
	    	moveAction();
	    	firstParams = false;
	    }
	}
	
	@Override
	public boolean performClick() {
		return super.performClick();
	}
	
	// save point after user finger down
	private void setBeginningPoint(float y) {
		beginningMovingY = y;
		resizableScreenHeight = viewHeight;
		prevDifScreenY = 0;
	}
	
	// if user uses scrollview as simple list
	private boolean standartScrolling() {
		View view = (View) getChildAt(getChildCount()-1);
		if (view.getBottom() > getHeight()) {
			return true;
		} else {
			return false;
		}		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setBeginningPoint(ev.getY());
			return true;
			
		case MotionEvent.ACTION_MOVE:
			movingY = ev.getY();
			float difScreenY = movingY - beginningMovingY;
			float defStep = Math.abs(difScreenY - prevDifScreenY) / resizableScreenHeight;
			if (animate && getScrollY() == 0 && difScreenY > 0) {
				if (prevDifScreenY == 0 || defStep < DEF_STEP) { 
					float stepScreen = difScreenY / resizableScreenHeight;
					float stepImage = stepScreen * resizableImageHeight;
					android.view.ViewGroup.LayoutParams params = avatar.getLayoutParams();
					float newImageHeight = beginningImageHeight + stepImage;
					if (newImageHeight < maxImageHeight) {
						params.height = (int) newImageHeight;
						avatar.setLayoutParams(params);
					}
					prevDifScreenY = difScreenY;
					return true;
				} else {
					setBeginningPoint(ev.getY());
				}
			} else if (getScrollY() > 0) {
				setBeginningPoint(ev.getY());
			}
			if (standartScrolling()) {
				return super.onTouchEvent(ev);
			}
			return true;
			
		case MotionEvent.ACTION_UP:
			android.view.ViewGroup.LayoutParams params = avatar.getLayoutParams();
			if (params.height != beginningImageHeight) {
				CollapseAnimation ani = new CollapseAnimation(avatar, params.height, (int)beginningImageHeight);
	            ani.setDuration(DURATION);
	            avatar.startAnimation(ani);
			} else {
				return super.onTouchEvent(ev);
			}
			return true;
		}
		return false;
	}
	
	private static class CollapseAnimation extends Animation { 
        private int baseHeight;
        private int delta;
        private View view;
        
        private CollapseAnimation(View v, int startHeight, int endHeight) {
            baseHeight = startHeight;
            delta = endHeight - startHeight;
            view = v;
            
            view.getLayoutParams().height = startHeight;
            view.requestLayout();
        }
        
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                int val = baseHeight + (int) (delta * interpolatedTime);
                view.getLayoutParams().height = val;
                view.requestLayout();
            } else {
                int val = baseHeight + delta;
                view.getLayoutParams().height = val;
                view.requestLayout();
            }
        }
    }

}
