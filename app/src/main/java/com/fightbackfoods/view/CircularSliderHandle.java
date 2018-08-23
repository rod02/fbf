package com.fightbackfoods.view;

import android.support.v4.view.ViewPager;

public class CircularSliderHandle implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private int mCurrentPosition;

    private CurrentPageListener currentPageListener;

    CircularSliderHandle(final ViewPager viewPager) {
        mViewPager = viewPager;
    }

    void setCurrentPageListener(CurrentPageListener currentPageListener) {
        this.currentPageListener = currentPageListener;
    }

    @Override
    public void onPageSelected(final int position) {
        mCurrentPosition = position;
        currentPageListener.onCurrentPageChanged(mCurrentPosition);
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
        final int currentPage = mViewPager.getCurrentItem();
        if (currentPage == mViewPager.getAdapter().getCount()-1 || currentPage == 0){
            int previousState = mCurrentPosition;
            mCurrentPosition = state;
            if (previousState == 1 && mCurrentPosition == 0){
                if(previousState==(mViewPager.getAdapter().getCount()-1))
                    mViewPager.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(0, true);

                        }
                    }, 10000);
                else
                     mViewPager.setCurrentItem(currentPage == 0 ? mViewPager.getAdapter().getCount()-1 : 0);
            }
        }
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
    }

    interface CurrentPageListener {
        void onCurrentPageChanged(int currentPosition);
    }
}
