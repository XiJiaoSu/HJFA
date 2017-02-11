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
	private ViewDragHelper mViewDragHelper = null;// ��������Scroller

	private static final int MENU_CLOSED = 1;
    private static final int MENU_OPENED = 2;
    private int mMenuState = MENU_CLOSED;//�˵���״̬
	
    private static final int MENU_MARGIN_RIGHT = 64;
	private int mMenuWidth;//�˵��Ŀ��
	
	private int mMenuOffset;//�˵���ƫ����
	private static final int MENU_OFFSET = 128;
	
	private int mDragOrientation;
    private static final int LEFT_TO_RIGHT = 3;
    private static final int RIGHT_TO_LEFT = 4;
    
    private static final float SPRING_BACK_VELOCITY = 1500;
    private static final int SPRING_BACK_DISTANCE = 80;
    private int mSpringBackDistance;
	
	private String mShadowOpacity="00";//��������͸���ȣ�����main�仯���仯
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
		//��ȡ��Ļ���ܶ�
		final float density=getResources().getDisplayMetrics().density;
		mScreenHeight=getResources().getDisplayMetrics().heightPixels;
		mScreenWidth=getResources().getDisplayMetrics().widthPixels;
		mSpringBackDistance=(int) (SPRING_BACK_DISTANCE*density+0.5f);
		mMenuOffset=(int) (MENU_OFFSET*density+0.5f);
		mMenuWidth=mScreenWidth-(int)(MENU_MARGIN_RIGHT*density+0.5f);
		
		mViewDragHelper = ViewDragHelper.create(this,// ��ǰҳ����¼�
				TOUCH_SLOP_SENSITIVITY,// ҳ������ж�
				new CoordinatorCallback()// ����֮��Ļص��¼�
				);
	}

	//�����겼���ļ������
	@Override
	protected void onFinishInflate() {
		mMenuView = getChildAt(0);// ��һ����View�ڵײ㣬��ΪMenu
		mMainView = (MainView) getChildAt(1);// �ڶ�����View�ڶ�����ΪMain
		mMainView.setParent(this);
	}

	/**
	 * ViewGroup�����ش����¼����������ǵ�����ViewDragHelper����
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return mViewDragHelper.shouldInterceptTouchEvent(ev);
	}

	/**
	 * View�����ش����¼����������ǵ�����ViewDragHelper����
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// �������¼����ݸ�ViewDragHelper���˲����ز�����
		mViewDragHelper.processTouchEvent(event);
		return true;
	}

	/**
	 * ���������е���
	 */
	@Override
	public void computeScroll() {
		if (mViewDragHelper.continueSettling(true)) {
			// ����ˢ�£�ʵ��ƽ���ƶ�
			ViewCompat.postInvalidateOnAnimation(this);
		}
		//��ȡ��ǰ�˵���״̬
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
		if (mMenuState==MENU_OPENED) {//�жϲ˵���״̬Ϊ�򿪵Ļ�
			//���ִ򿪵�λ��
			mMenuView.layout(0, 0, mMenuWidth, bottom);
			mMainView.layout(mMenuWidth, 0, mMenuWidth+mScreenWidth, bottom);
			return;
		}
		mMenuView.layout(-mMenuOffset, top, mMenuWidth - mMenuOffset, bottom);
	}
	
	
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		final int restoreCount = canvas.save();//���滭����ǰ�ļ�����Ϣ

        final int height = getHeight();
        final int clipLeft = 0;
        int clipRight = mMainView.getLeft();
        if (child == mMenuView) {
            canvas.clipRect(clipLeft, 0, clipRight, height);//������ʾ������
        }
		boolean result=super.drawChild(canvas, child, drawingTime);//���ԭ�е���view��menu��main�Ļ���
		//�ָ�����֮ǰ����ļ�����Ϣ
        //����������֮���view
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
		 * �򿪲˵�
		 */
		public void openMenu() {
			mViewDragHelper.smoothSlideViewTo(mMainView, mMenuWidth, 0);//��mMainView����
			ViewCompat.postInvalidateOnAnimation(CoordinatorMenu.this);
		}
		
		/**
		 * �رղ˵�
		 */
		public void closeMenu() {
			mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);//��mMainView�󻬶�
			ViewCompat.postInvalidateOnAnimation(CoordinatorMenu.this);
		}
	private class CoordinatorCallback extends ViewDragHelper.Callback {

		/**
		 * ����ViewDragHelper���ĸ���View�����϶�����
		 */
		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			// �໬�˵�Ĭ���ǹرյ�
			// �û��ض�ֻ���ȴ��������ϲ��������
			return mMainView == child||mMenuView==child;//��ǰ���ڿ��Դ�����menu��Ȼ����main����
		}

		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
			if (capturedChild==mMenuView) {//����������view��menu
				mViewDragHelper.captureChildView(mMainView, activePointerId);//����main����
			}
		}
		@Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }
		/**
		 * ����ˮƽ����Ļ���
		 */
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// ͨ������left���ɣ�leftָ����view�����Ե��λ��
			if (left < 0) {
				left = 0;// ��ʼ��λ������Ļ�����Ե
			} else if (left > mMenuWidth) {
				left = mMenuWidth;// ��Զ��λ�þ��ǲ˵���ȫչ�����menu�Ŀ��
			}
			return left;
		}

		/**
		 * ���˵��رգ��������һ���main��ʱ��С��һ�������ɿ��֣���Ҫ�����ص�������ߣ�����ֱ�Ӵ򿪲˵�
		 * ���˵���ȫ�򿪣��������󻬶�main��ʱ��С��һ�������ɿ��֣���Ҫ�����ص������ұߣ�����ֱ�ӹرղ˵�
		 */
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {//��View��λ�øı�ʱ���ã�Ҳ�����϶���ʱ��
			 Log.d(TAG, "onViewPositionChanged: dx:" + dx);
			//���������һ������ʱ�����Ļ�������
			if (dx>0) {//��
				mDragOrientation=LEFT_TO_RIGHT;//��������
			}else if(dx<0){//��
				mDragOrientation=RIGHT_TO_LEFT;//��������
			}
			//y = kx + d,mMenuOffset��mMenuWidth֮������Թ�ϵ
			float scale = (float) (mMenuWidth - mMenuOffset) / (float) mMenuWidth;
            int menuLeft = left - ((int) (scale * left) + mMenuOffset);
            mMenuView.layout(menuLeft, mMenuView.getTop(),
                    menuLeft + mMenuWidth, mMenuView.getBottom());
            //�ı�mShadowOpacity���������û���ʱ��͸���Ƚ���
            float showing = (float) (mScreenWidth - left) / (float) mScreenWidth;
            int hex = 255 - Math.round(showing * 255);
            if (hex < 16) {
                mShadowOpacity = "0" + Integer.toHexString(hex);
            } else {
                mShadowOpacity = Integer.toHexString(hex);
            }
		}

		/**
		 * ���ɿ���View˽�ú����
		 */
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			 Log.e(TAG, "onViewReleased: xvel: " + xvel);
			if (mDragOrientation==LEFT_TO_RIGHT) {//�������һ���
				if (xvel > SPRING_BACK_VELOCITY ||mMainView.getLeft()<mSpringBackDistance) {
					closeMenu();//�رղ˵�
				}else{
					openMenu();//�򿪲˵�
				}
			}else if(mDragOrientation==RIGHT_TO_LEFT){//�������󻬶�
				if (xvel < -SPRING_BACK_VELOCITY||mMainView.getLeft()<(mMenuWidth-mSpringBackDistance)) {
					closeMenu();//���һ���С��ָ���ľ��룬�ص�
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
