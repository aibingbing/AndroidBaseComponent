package com.aibb.android.base.mvp;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.Fragment;

import com.aibb.android.base.mvp.annotation.MvpPresenterInject;
import com.aibb.android.base.mvp.annotation.MvpPresenterVariable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
public class PresenterProvider {

    private PresenterStore mPresenterStore = new PresenterStore<>();
    private Activity mActivity;
    private Fragment mFragment;
    private Class<?> mClass;

    public static PresenterProvider inject(Activity activity) {
        return new PresenterProvider(activity, null);
    }

    public static PresenterProvider inject(Fragment fragment) {
        return new PresenterProvider(null, fragment);
    }

    private PresenterProvider(Activity activity, Fragment fragment) {
        if (activity != null) {
            this.mActivity = activity;
            mClass = this.mActivity.getClass();
        }
        if (fragment != null) {
            this.mFragment = fragment;
            mClass = this.mFragment.getClass();
        }
        resolvePresenterInject();
        resolvePresenterVariable();
    }

    private <P extends BaseMvpPresenter> PresenterProvider resolvePresenterInject() {
        MvpPresenterInject mvpPresenterInject = mClass.getAnnotation(MvpPresenterInject.class);
        if (mvpPresenterInject != null) {
            Class<P>[] classes = (Class<P>[]) mvpPresenterInject.values();
            for (Class<P> clazz : classes) {
                String canonicalName = clazz.getCanonicalName();
                try {
                    mPresenterStore.put(canonicalName, clazz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    private <P extends BaseMvpPresenter> PresenterProvider resolvePresenterVariable() {
        for (Field field : mClass.getDeclaredFields()) {
            //获取字段上的注解
            Annotation[] anns = field.getDeclaredAnnotations();
            if (anns.length < 1) {
                continue;
            }
            if (anns[0] instanceof MvpPresenterVariable) {
                String canonicalName = field.getType().getName();
                P presenterInstance = (P) mPresenterStore.get(canonicalName);
                if (presenterInstance != null) {
                    try {
                        field.setAccessible(true);
                        field.set(mFragment != null ? mFragment : mActivity, presenterInstance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return this;
    }

    private static Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to Application. You can't request PresenterProviders before onCreate call.");
        }
        return application;
    }

    private static Activity checkActivity(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new IllegalStateException("Can't create PresenterProviders for detached fragment");
        }
        return activity;
    }

    public <P extends BaseMvpPresenter> P getPresenter(int index) {
        MvpPresenterInject mvpPresenterInject = mClass.getAnnotation(MvpPresenterInject.class);
        if (mvpPresenterInject == null) {
            return null;
        }
        if (mvpPresenterInject.values().length == 0) {
            return null;
        }
        if (index >= 0 && index < mvpPresenterInject.values().length) {
            String key = mvpPresenterInject.values()[index].getCanonicalName();
            BaseMvpPresenter presenter = mPresenterStore.get(key);
            if (presenter != null) {
                return (P) presenter;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public PresenterStore getPresenterStore() {
        return mPresenterStore;
    }

    public static class PresenterStore<P extends BaseMvpPresenter> {
        private static final String DEFAULT_KEY = "PresenterStore.DefaultKey";
        private HashMap<String, P> mMap = new HashMap<>();

        public final void put(String key, P presenter) {
            P oldPresenter = mMap.put(DEFAULT_KEY + ":" + key, presenter);
            if (oldPresenter != null) {
                oldPresenter.destroy();
            }
        }

        public final P get(String key) {
            return mMap.get(DEFAULT_KEY + ":" + key);
        }

        public final void destroy() {
            for (P presenter : mMap.values()) {
                presenter.destroy();
            }
            mMap.clear();
        }

        public int getSize() {
            return mMap.size();
        }

        public HashMap<String, P> getMap() {
            return mMap;
        }
    }
}
