package com.cs2013.hjfa.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class CoordinatorMenu extends FrameLayout {

	private static final float TOUCH_SLOP_SENSITIVITY = 1.0f;
	private View mMenuView;
	private MainView mMainView;
	private ViewDragHelper mViewDragHelper = null;// 本质上是Scroller

	private static final int MENU_CLOSED = 1;
    private static final int MENU_OPENED = 2;
    private int mMenuState = MENU_CLOSED;//菜单的状态
	
    private static final int MENU_MARGIN_RIGHT = 64;
	private int mMenuWidth;//菜单的宽度
	
	private int mMenuOffset;//菜单的偏移量
	private static final int MENU_OFFSET = 128;
	
	private int mDragOrientation;
    private static final int LEFT_TO_RIGHT = 3;
    private static final int RIGHT_TO_LEFT = 4;
    
    private static final float SPRING_BACK_VELOCITY = 1500;
    private static final int SPRING_BACK_DISTANCE = 80;
    private int mSpringBackDistance;
	
	private String mShadowOpacity="00";//用于设置透明度，随着main变化而变化
	private int mScreenWidth;
	private int mScreenHeight;
	private String TAG="Log";

	public CoordinatorMenu(Context context) {
		this(context, null);
	}

	public CoordinatorMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CoordinatorMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context,attrs,defStyleAttr);
		//获取屏幕的密度
		final float density=getResources().getDisplayMetrics().density;
		mScreenHeight=getResources().getDisplayMetrics().heightPixels;
		mScreenWidth=getResources().getDisplayMetrics().widthPixels;
		mSpringBackDistance=(int) (SPRING_BACK_DISTANCE*density+0.5f);
		mMenuOffset=(int) (MENU_OFFSET*density+0.5f);
		mMenuWidth=mScreenWidth-(int)(MENU_MARGIN_RIGHT*density+0.5f);
		
		mViewDragHelper = ViewDragHelper.create(this,// 当前页面的事件
				TOUCH_SLOP_SENSITIVITY,// 页面的敏感度
				new CoordinatorCallback()// 触发之后的回调事件
				);
	}

	//加载完布局文件后调用
	@Override
	protected void onFinishInflate() {
		mMenuView = getChildAt(0);// 第一个子View在底层，作为Menu
		mMainView = (MainView) getChildAt(1);// 第二个子View在顶层作为Main
		mMainView.setParent(this);
	}

	/**
	 * ViewGroup中拦截触摸事件，交给我们的主角ViewDragHelper处理
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return mViewDragHelper.shouldInterceptTouchEvent(ev);
	}

	/**
	 * View中拦截触摸事件，交给我们的主角ViewDragHelper处理
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 将触摸事件传递给ViewDragHelper，此操作必不可少
		mViewDragHelper.processTouchEvent(event);
		return true;
	}

	/**
	 * 滑动过程中调用
	 */
	@Override
	public void computeScroll() {
		if (mViewDragHelper.continueSettling(true)) {
			// 处理刷新，实现平滑移动
			ViewCompat.postInvalidateOnAnimation(this);
		}
		//获取当前菜单的状态
		if (mMainView.getLeft()==0) {
			mMenuState=MENU_CLOSED;
		}else if(mMainView.getLeft()==mMenuWidth){
			mMenuState=MENU_OPENED;
		}
	}
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		 Log.d(TAG, "onLayout: ");
		super.onLayout(changed, left, top, right, bottom);
		MarginLayoutParams menuParams=(MarginLayoutParams) mMenuView.getLayoutParams();
		menuParams.width=mMenuWidth;
		mMenuView.setLayoutParams(menuParams);
		if (mMenuState==MENU_OPENED) {//判断菜单的状态为打开的话
			//保持打开的位置
			mMenuView.layout(0, 0, mMenuWidth, bottom);
			mMainView.layout(mMenuWidth, 0, mMenuWidth+mScreenWidth, bottom);
			return;
		}
		mMenuView.layout(-mMenuOffset, top, mMenuWidth - mMenuOffset, bottom);
	}
	
	
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		final int restoreCount = canvas.save();//保存画布当前的剪裁信息

        final int height = getHeight();
        final int clipLeft = 0;
        int clipRight = mMainView.getLeft();
        if (child == mMenuView) {
            canvas.clipRect(clipLeft, 0, clipRight, height);//剪裁显示的区域
        }
		boolean result=super.drawChild(canvas, child, drawingTime);//完成原有的子view：menu和main的绘制
		//恢复画布之前保存的剪裁信息
        //以正常绘制之后的view
        canvas.restoreToCount(restoreCount);
        
        int shadowLeft = mMainView.getLeft();
        Log.d(TAG, "drawChild: shadowLeft: " + shadowLeft);
        final Paint shadowPaint = new Paint();
        Log.d(TAG, "drawChild: mShadowOpacity: " + mShadowOpacity);
        shadowPaint.setColor(Color.parseColor("#" + mShadowOpacity + "777777"));
        shadowPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(shadowLeft, 0, mScreenWidth, mScreenHeight, shadowPaint);
        return result;
	}
	
	 public boolean isOpened() {
	        return mMenuState == MENU_OPENED;
	    }
	
	 @Override
	    protected Parcelable onSaveInstanceState() {
	        final Parcelable superState = super.onSaveInstanceState();
	        final CoordinatorMenu.SavedState ss = new CoordinatorMenu.SavedState(superState);
	        ss.menuState = mMenuState;
	        return ss;
	    }

	    @Override
	    protected void onRestoreInstanceState(Parcelable state) {
	        if (!(state instanceof CoordinatorMenu.SavedState)) {
	            super.onRestoreInstanceState(state);
	            return;
	        }

	        final CoordinatorMenu.SavedState ss = (CoordinatorMenu.SavedState) state;
	        super.onRestoreInstanceState(ss.getSuperState());

	        if (ss.menuState == MENU_OPENED) {
	            openMenu();
	        }
	    }
	    /**
		 * 打开菜单
		 */
		public void openMenu() {
			mViewDragHelper.smoothSlideViewTo(mMainView, mMenuWidth, 0);//将mMainView滑动
			ViewCompat.postInvalidateOnAnimation(CoordinatorMenu.this);
		}
		
		/**
		 * 关闭菜单
		 */
		public void closeMenu() {
			mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);//将mMainView左滑动
			ViewCompat.postInvalidateOnAnimation(CoordinatorMenu.this);
		}
	private class CoordinatorCallback extends ViewDragHelper.Callback {

		/**
		 * 告诉ViewDragHelper对哪个子View进行拖动滑动
		 */
		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			// 侧滑菜单默认是关闭的
			// 用户必定只能先触摸到的上层的主界面
			return mMainView == child||mMenuView==child;//当前用于可以触摸到menu，然后让main处理
		}

		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
			if (capturedChild==mMenuView) {//当触摸到的view是menu
				mViewDragHelper.captureChildView(mMainView, activePointerId);//交给main处理
			}
		}
		@Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }
		/**
		 * 进行水平方向的滑动
		 */
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// 通常返回left即可，left指代此view的左边缘的位置
			if (left < 0) {
				left = 0;// 初始的位置在屏幕的左边缘
			} else if (left > mMenuWidth) {
				left = mMenuWidth;// 最远的位置就是菜单完全展开后的menu的宽度
			}
			return left;
		}

		/**
		 * 当菜单关闭，从左向右滑动main的时候，小于一定距离松开手，需要让它回弹到最左边，否则直接打开菜单
		 * 当菜单完全打开，从右向左滑动main的时候，小于一定距离松开手，需要让它回弹到最右边，否则直接关闭菜单
		 */
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {//当View的位置改变时调用，也就是拖动的时候
			 Log.d(TAG, "onViewPositionChanged: dx:" + dx);
			//代表距离上一个滑动时间间隔的滑动距离
			if (dx>0) {//正
				mDragOrientation=LEFT_TO_RIGHT;//从左向右
			}else if(dx<0){//负
				mDragOrientation=RIGHT_TO_LEFT;//从有向左
			}
			//y = kx + d,mMenuOffset与mMenuWidth之间的线性关系
			float scale = (float) (mMenuWidth - mMenuOffset) / (float) mMenuWidth;
            int menuLeft = left - ((int) (scale * left) + mMenuOffset);
            mMenuView.layout(menuLeft, mMenuView.getTop(),
                    menuLeft + mMenuWidth, mMenuView.getBottom());
            //改变mShadowOpacity，用于设置滑动时的透明度渐变
            float showing = (float) (mScreenWidth - left) / (float) mScreenWidth;
            int hex = 255 - Math.round(showing * 255);
            if (hex < 16) {
                mShadowOpacity = "0" + Integer.toHexString(hex);
            } else {
                mShadowOpacity = Integer.toHexString(hex);
            }
		}

		/**
		 * 收松开后，View私访后调用
		 */
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			 Log.e(TAG, "onViewReleased: xvel: " + xvel);
			if (mDragOrientation==LEFT_TO_RIGHT) {//从左向右滑动
				if (xvel > SPRING_BACK_VELOCITY ||mMainView.getLeft()<mSpringBackDistance) {
					closeMenu();//关闭菜单
				}else{
					openMenu();//打开菜单
				}
			}else if(mDragOrientation==RIGHT_TO_LEFT){//从右向左滑动
				if (xvel < -SPRING_BACK_VELOCITY||mMainView.getLeft()<(mMenuWidth-mSpringBackDistance)) {
					closeMenu();//向右滑动小于指定的距离，回弹
				}else{
					openMenu();
				}
			}
		}

	}
	 protected static class SavedState extends AbsSavedState {
	        int menuState;

	        SavedState(Parcel in, ClassLoader loader) {
	            super(in);
	            menuState = in.readInt();
	        }

	        SavedState(Parcelable superState) {
	            super(superState);
	        }

	        @Override
	        public void writeToParcel(Parcel dest, int flags) {
	            super.writeToParcel(dest, flags);
	            dest.writeInt(menuState);
	        }

	        public static final Creator<CoordinatorMenu.SavedState> CREATOR = ParcelableCompat.newCreator(
	                new ParcelableCompatCreatorCallbacks<CoordinatorMenu.SavedState>() {
	                    @Override
	                    public CoordinatorMenu.SavedState createFromParcel(Parcel in, ClassLoader loader) {
	                        return new CoordinatorMenu.SavedState(in, loader);
	                    }

	                    @Override
	                    public CoordinatorMenu.SavedState[] newArray(int size) {
	                        return new CoordinatorMenu.SavedState[size];
	                    }
	                });
	    }
}
