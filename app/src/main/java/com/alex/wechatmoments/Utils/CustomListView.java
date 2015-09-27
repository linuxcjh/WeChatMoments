package com.alex.wechatmoments.Utils;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alex.wechatmoments.R;

/**
 * Pull down and up ListView
 */
public class CustomListView extends ListView implements OnScrollListener {

    private float mInitialMotionY, mLastMotionY, moveY_1, moveY_2;
    private boolean mIsBeingDragged = false;
    private int mTop = 0;
    /**
     * Scroll animation
     */
    private Interpolator mScrollAnimationInterpolator;
    /**
     * Smooth scroll runnable
     */
    private SmoothScrollRunnable mCurrentSmoothScrollRunnable;
    /**
     * Refresh listener
     */
    private OnRefreshListener mOnRefreshListener;
    /**
     * Load more listener
     */
    private OnLoadMoreListener mOnLoadMoreListener;
    /**
     * Circle rotate layout
     */
    private RotateLayout rotateLayout;

    private boolean isRefreshing = false, isLoadMore = false;

    public CustomListView(Context context) {
        super(context);
        init();
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public CustomListView getInstance() {
        return this;
    }

    void init() {
        getInstance().setOnScrollListener(this);

    }

    private View headerView;

    public void setPullHeaderView(View v) {
        headerView = v;
        addHeaderView(v);
        if (mTop == 0) {
            mTop = 1080 / 5;
        }
        pullEvent(0);
        if (null == mScrollAnimationInterpolator) {
            mScrollAnimationInterpolator = new DecelerateInterpolator();
        }
    }

    private View footerView;
    private ProgressBar footProgessBar;
    private TextView footTv;


    public void setPullFooterView(View v) {
        footerView = v;
        footProgessBar = (ProgressBar) footerView.findViewById(R.id.id_progressBar);
        footTv = (TextView) footerView.findViewById(R.id.id_loadTv);
        footerView.setVisibility(View.GONE);
        addFooterView(v);
    }

    public View getPullHeaderView() {
        return headerView;
    }

    public void setPullHeaderViewHeight(int height) {
        mTop = height;
        if (headerView != null) {
            pullEvent(0);
        }
    }

    public void setRotateLayout(RotateLayout rotateLayout) {
        this.rotateLayout = rotateLayout;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                if (isRefreshing || isLoadMore) {
                    return super.onTouchEvent(event);
                }
                if (isFirstItemVisible()) {
                    mLastMotionY = event.getY();
                    moveY_1 = event.getY();
                    if (moveY_1 != moveY_2) {
                        float rotate = moveY_2 - moveY_1;
                        moveY_2 = moveY_1;
                        if (mLastMotionY - mInitialMotionY > 0) {
                            mIsBeingDragged = true;
                            rotateLayout.reset();
                            rotateLayout.roate(rotate / 3);
                            pullEvent(mLastMotionY - mInitialMotionY);
                            return super.onTouchEvent(newMotionEvent(event));
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                if (isRefreshing || isLoadMore) {
                    return super.onTouchEvent(event);
                }
                if (isFirstItemVisible()) {
                    mLastMotionY = mInitialMotionY = event.getY();
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                if (mIsBeingDragged) {
                    mIsBeingDragged = false;
                    mLastMotionY = event.getY();
                    if (mLastMotionY - mInitialMotionY > mTop) {
                        OnRefreshing();
                        if (null != mOnRefreshListener) {
                            mOnRefreshListener.onRefresh(CustomListView.this);
                        }
                    } else {
                        onCompleteRefresh();
                    }

                }
//			 rotateLayout.toup();
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    private MotionEvent newMotionEvent(MotionEvent ev) {
        return MotionEvent.obtain(ev.getDownTime(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_CANCEL, ev.getX(), ev.getY(), 0);

    }

    public final void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    public final void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }

    /**
     * Simple Listener to listen for any callbacks to Refresh.
     *
     * @author Chris Banes
     */
    public interface OnRefreshListener<V extends View> {

        /**
         * onRefresh will be called for both a Pull from start, and Pull from
         * end
         */
        void onRefresh(CustomListView refreshView);

    }

    /**
     * Simple Listener to listen for any callbacks to Refresh.
     *
     * @author Chris Banes
     */
    public interface OnLoadMoreListener<V extends View> {

        /**
         * onRefresh will be called for both a Pull from start, and Pull from
         * end
         */
        void onLoadMore(CustomListView refreshView);

    }

    public void onCompleteRefresh() {
        if (isRefreshing) {
            isRefreshing = false;
        }
        rotateLayout.toup();
        if (isLoadMore) {
            isLoadMore = false;
        }
        mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(
                headerView.getPaddingTop() + mTop, 0, 300,
                new OnSmoothScrollFinishedListener() {
                    @Override
                    public void onSmoothScrollFinished() {

                    }
                });
        postDelayed(mCurrentSmoothScrollRunnable, 20);
    }

    public void onCompleteLoadMore() {
        footProgessBar.setVisibility(View.GONE);
        footTv.setText("无更多数据");
        if (isRefreshing) {
            isRefreshing = false;
        }
//        rotateLayout.toup();
        if (isLoadMore) {
            isLoadMore = false;
        }
        mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(
                headerView.getPaddingTop() + mTop, 0, 300,
                new OnSmoothScrollFinishedListener() {
                    @Override
                    public void onSmoothScrollFinished() {

                    }
                });
        postDelayed(mCurrentSmoothScrollRunnable, 20);
    }

    /**
     *
     */
    private void OnRefreshing() {


        footerView.setVisibility(View.GONE);


        isRefreshing = true;
        rotateLayout.rotateAnimation();
        mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(
                headerView.getPaddingTop() + mTop, mTop, 300,
                new OnSmoothScrollFinishedListener() {
                    @Override
                    public void onSmoothScrollFinished() {

                    }
                });
        postDelayed(mCurrentSmoothScrollRunnable, 20);
    }

    final class SmoothScrollRunnable implements Runnable {
        private final Interpolator mInterpolator;
        private final int mScrollToY;
        private final int mScrollFromY;
        private final long mDuration;
        private OnSmoothScrollFinishedListener mListener;

        private boolean mContinueRunning = true;
        private long mStartTime = -1;
        private int mCurrentY = -1;

        public SmoothScrollRunnable(int fromY, int toY, long duration,
                                    OnSmoothScrollFinishedListener listener) {
            mScrollFromY = fromY;
            mScrollToY = toY;
            mInterpolator = mScrollAnimationInterpolator;
            mDuration = duration;
            mListener = listener;
        }

        @Override
        public void run() {

            /**
             * Only set mStartTime if this is the first time we're starting,
             * else actually calculate the Y delta
             */
            if (mStartTime == -1) {
                mStartTime = System.currentTimeMillis();
            } else {

                /**
                 * We do do all calculations in long to reduce software float
                 * calculations. We use 1000 as it gives us good accuracy and
                 * small rounding errors
                 */
                long normalizedTime = (1000 * (System.currentTimeMillis() - mStartTime))
                        / mDuration;
                normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);

                final int deltaY = Math.round((mScrollFromY - mScrollToY)
                        * mInterpolator
                        .getInterpolation(normalizedTime / 1000f));
                mCurrentY = mScrollFromY - deltaY;
                pullEvent(mCurrentY);
            }

            // If we're not at the target Y, keep going...
            if (mContinueRunning && mScrollToY != mCurrentY) {
                ViewCompat.postOnAnimation(CustomListView.this, this);
            } else {
                if (null != mListener) {
                    mListener.onSmoothScrollFinished();
                }
            }
        }

        public void stop() {
            mContinueRunning = false;
            removeCallbacks(this);
        }
    }

    static interface OnSmoothScrollFinishedListener {
        void onSmoothScrollFinished();
    }

    private void pullEvent(float newScrollValue) {
        // scrollTo(0, (int) newScrollValue + mTop);
        headerView.setPadding(0, (int) newScrollValue - mTop, 0, 0);
    }

    private boolean isFirstItemVisible() {
        final Adapter adapter = getAdapter();
        if (null == adapter || adapter.isEmpty()) {
            return true;
        } else {
            if (getFirstVisiblePosition() <= 1) {
                final View firstVisibleChild = getChildAt(0);
                if (firstVisibleChild != null) {
                    return firstVisibleChild.getTop() >= getTop();
                }
            }
        }
        return false;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount
                && totalItemCount > 0 && !isRefreshing && !isLoadMore) {

        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            if (view.getLastVisiblePosition() == view.getCount() - 1
                    && !isRefreshing && !isLoadMore) {
                if (mOnLoadMoreListener != null) {
                    isLoadMore = true;
                    footerView.setVisibility(View.VISIBLE);
                    footProgessBar.setVisibility(View.VISIBLE);
                    footTv.setText("Loading");
                    mOnLoadMoreListener.onLoadMore(getInstance());
                }
            }
        }
    }
}