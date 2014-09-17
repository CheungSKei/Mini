package com.mini.mn.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * ����android:scrollbars="vertical"�����ô�ֱ������
 * 
 * ʵ��ˮƽ���������޸�mScrollX���ɲο�HorizontalScrollView
 * 
 * ����ԭ�� �ڻ���ǰ�Ի�������ƫ�Ʋ���
 * 
 * ������View�Ļ��ƻ��ƣ� |- view.computeScroll()
 * --������mScrollX/Y�����޸ġ������ڻ���ǰ���ã��ɵ���invalite()������ |-
 * canvas.translate(-mScrollX,-mScrollY) --ƫ�ƻ��� |- view.draw() --����
 * 
 * �������ݿ�����View.buildDrawingCache()��ViewGroup.dispatchDraw()->drawChild()���ҵ�.
 * ֱ�Ӳ鿴����������
 * 
 * ���������ࣺ Scroller --��������������ƫ��ֵ.������ο�ScrollView��HorizontalScrollView
 * VelocityTracker --�ٶȼ����ࡣ����flingʱ�İ��¡�̧����������������ٶ�
 * 
 * ScrollTextView--���̽����� 1��onTouchEvent() --ʹ��Scroller���������ƫ��ֵ
 * 2����дcomputeScroll() --��View��mScrollY�����޸ģ� �˴����ƹ�����Χ
 * 
 * ������Χ�� ��Сֵ��0 ���ֵ�������ı��߶�+�ڱ߾�-View�߶ȡ�Ҳ���ǳ�����Ļ���ı��߶�
 */
public class ScrollTextView extends TextView {
	private Scroller mScroller;
	private int mTouchSlop;
	private int mMinimumVelocity;
	private int mMaximumVelocity;

	private float mLastMotionY;
	private boolean mIsBeingDragged;
	private VelocityTracker mVelocityTracker;
	private int mActivePointerId = INVALID_POINTER;

	private static final int INVALID_POINTER = -1;

	public ScrollTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public ScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public ScrollTextView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		final Context cx = getContext();
		// ���ù�������������fling�л��õ�
		mScroller = new Scroller(cx, new DecelerateInterpolator(0.5f));
		final ViewConfiguration configuration = ViewConfiguration.get(cx);
		mTouchSlop = configuration.getScaledTouchSlop();
		mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

	}

	/**
	 * �˷���Ϊ���������޸�mScrollX,mScrollY. �ⷽ���󽫸���mScrollX,mScrollY��ƫ��Canvas��ʵ�����ݹ���
	 */
	@Override
	public void computeScroll() {
		super.computeScroll();

		final Scroller scroller = mScroller;
		if (scroller.computeScrollOffset()) { // ���ڹ�������view��������ǰλ��
			int scrollY = scroller.getCurrY();
			final int maxY = (getLineCount() * getLineHeight()
					+ getPaddingTop() + getPaddingBottom())
					- getHeight();
			boolean toEdge = scrollY < 0 || scrollY > maxY;
			if (scrollY < 0)
				scrollY = 0;
			else if (scrollY > maxY)
				scrollY = maxY;

			/*
			 * �����ͬ�ڣ� mScrollY = scrollY; awakenScrollBars(); //��ʾ��������������xml�����á�
			 * postInvalidate();
			 */
			scrollTo(0, scrollY);
			if (toEdge) // �Ƶ����ˣ�����λ��û�з����仯�����¹���������ʾ
				awakenScrollBars();
		}
	}

	public void fling(int velocityY) {
		final int maxY = (getLineCount() * getLineHeight() + getPaddingTop() + getPaddingBottom())
				- getHeight();

		mScroller.fling(getScrollX(), getScrollY(), 0, velocityY, 0, 0, 0,
				Math.max(0, maxY));

		// ˢ�£��ø��ؼ�����computeScroll()
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		/*
		 * �¼�����ʽ�����Լ�����󽻸����ദ�� PS:��ʽ��ͬ�����ܵ���Ч����ͬ����������������޸ġ�
		 */
		boolean handled = false;
		final int contentHeight = getLineCount() * getLineHeight();
		if (contentHeight > getHeight()) {
			handled = processScroll(ev);
		}

		return handled | super.onTouchEvent(ev);
	}

	private boolean processScroll(MotionEvent ev) {
		boolean handled = false;
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(ev); // �����࣬������flingʱ�����ƶ����ٶ�

		final int action = ev.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			if (!mScroller.isFinished()) {
				mScroller.forceFinished(true);
			}

			mLastMotionY = ev.getY();
			mActivePointerId = ev.getPointerId(0);
			mIsBeingDragged = true;
			handled = true;
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			final int pointerId = mActivePointerId;
			if (mIsBeingDragged && INVALID_POINTER != pointerId) {
				final int pointerIndex = ev.findPointerIndex(pointerId);
				final float y = ev.getY(pointerIndex);
				int deltaY = (int) (mLastMotionY - y);

				if (Math.abs(deltaY) > mTouchSlop) { // �ƶ�����(����������)�������ViewConfiguration���õ�Ĭ��ֵ
					mLastMotionY = y;

					/*
					 * Ĭ�Ϲ���ʱ��Ϊ250ms�����������������������Ч�������� ����ֱ��ʹ��scrollBy(0, deltaY);
					 */
					mScroller.startScroll(getScrollX(), getScrollY(), 0,
							deltaY, 0);
					invalidate();
					handled = true;
				}
			}
			break;
		}
		case MotionEvent.ACTION_UP: {
			final int pointerId = mActivePointerId;
			if (mIsBeingDragged && INVALID_POINTER != pointerId) {
				final VelocityTracker velocityTracker = mVelocityTracker;
				velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
				int initialVelocity = (int) velocityTracker
						.getYVelocity(pointerId);

				if (Math.abs(initialVelocity) > mMinimumVelocity) {
					fling(-initialVelocity);
				}

				mActivePointerId = INVALID_POINTER;
				mIsBeingDragged = false;

				if (mVelocityTracker != null) {
					mVelocityTracker.recycle();
					mVelocityTracker = null;
				}

				handled = true;
			}
			break;
		}
		}
		return handled;
	}

}
