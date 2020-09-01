package com.aibb.android.base.mvp;

import android.util.Log;

/**
 * 传统模式下支持栏加载的fragment: 控制onHiddenChanged+setUserVisibleHint两个方法
 * <p>
 * Androidx.Fragment 中使用FragmentTransaction.setMaxLifecycle()控制懒加载的方式参考：https://github.com/AndyJennifer/AndroidxLazyLoad
 */
public abstract class BaseLazyMvpFragment extends BaseMvpFragment {

    private final String TAG = this.getClass().getSimpleName();

    /**
     * 是否执行懒加载
     */
    private boolean isLoaded = false;

    /**
     * 当前Fragment是否对用户可见
     */
    private boolean isVisibleToUser = false;

    /**
     * 因为当使用ViewPager+Fragment形式会调用该方法时，setUserVisibleHint会优先Fragment生命周期函数调用，
     * 所以这个时候就,会导致在setUserVisibleHint方法执行时就执行了懒加载，
     * 而不是在onResume方法实际调用的时候执行懒加载。所以需要这个变量
     */
    private boolean isCallResume = false;

    /**
     * 是否调用了setUserVisibleHint方法。处理show+add+hide模式下，默认可见 Fragment 不调用
     * onHiddenChanged 方法，进而不执行懒加载方法的问题。
     */
    private boolean isCallUserVisibleHint = false;

    @Override
    public void onResume() {
        super.onResume();
        isCallResume = true;
        if (!isCallUserVisibleHint) isVisibleToUser = !isHidden();
        judgeLazyInit();
    }

    private void judgeLazyInit() {
        if (!isLoaded && isVisibleToUser && isCallResume) {
            lazyInit();
            Log.d(TAG, "lazyInit:!!!!!!!");
            isLoaded = true;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisibleToUser = !hidden;
        judgeLazyInit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoaded = false;
        isVisibleToUser = false;
        isCallUserVisibleHint = false;
        isCallResume = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setAllowEnterTransitionOverlap(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        isCallUserVisibleHint = true;
        judgeLazyInit();
    }

    public abstract void lazyInit();
}
