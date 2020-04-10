package com.ebrightmoon.commonlogic.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.ebrightmoon.commonlogic.R;

/**
 * Time: 2020-04-09
 * Author:wyy
 * Description:
 */
public class MultiStateView extends FrameLayout {

    private Context mContext;

    private View contentView;
    private View loadingView;
    private View errorView;
    private View emptyView;

    private final int VIEW_STATE_CONTENT = 0;
    private final int VIEW_STATE_ERROR = 1;
    private final int VIEW_STATE_EMPTY = 2;
    private final int VIEW_STATE_LOADING = 3;


    private StateListener listener = null;

    private boolean animateLayoutChanges = false;
    public ViewState viewState = ViewState.CONTENT;

    public MultiStateView(@NonNull Context context) {
        this(context, null);

    }

    public MultiStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MultiStateView);

        int loadingViewResId = typedArray.getResourceId(R.styleable.MultiStateView_loadingView, -1);
        if (loadingViewResId > -1) {
            View inflatedLoadingView = inflater.inflate(loadingViewResId, this, false);
            loadingView = inflatedLoadingView;
            addView(inflatedLoadingView, inflatedLoadingView.getLayoutParams());
        }

        int emptyViewResId = typedArray.getResourceId(R.styleable.MultiStateView_emptyView, -1);
        if (emptyViewResId > -1) {
            View inflatedEmptyView = inflater.inflate(emptyViewResId, this, false);
            emptyView = inflatedEmptyView;
            addView(inflatedEmptyView, inflatedEmptyView.getLayoutParams());
        }

        int errorViewResId = typedArray.getResourceId(R.styleable.MultiStateView_errorView, -1);
        if (errorViewResId > -1) {
            View inflatedErrorView = inflater.inflate(errorViewResId, this, false);
            errorView = inflatedErrorView;
            addView(inflatedErrorView, inflatedErrorView.getLayoutParams());
        }
        int anInt = typedArray.getInt(R.styleable.MultiStateView_viewState, VIEW_STATE_CONTENT);
        if (anInt == VIEW_STATE_ERROR) {
            viewState = ViewState.ERROR;
        } else if (anInt == VIEW_STATE_EMPTY) {
            viewState = ViewState.EMPTY;

            viewState = ViewState.LOADING;
        } else if (anInt == VIEW_STATE_LOADING) {
            viewState = ViewState.LOADING;
        } else {
            viewState = ViewState.CONTENT;
        }
        animateLayoutChanges = typedArray.getBoolean(R.styleable.MultiStateView_animateViewChanges, false);
        typedArray.recycle();
    }




    /**
     * 设置渐变动画
     * @return
     */
    public void setAnimateLayoutChanges(boolean animateLayoutChanges) {
        this.animateLayoutChanges = animateLayoutChanges;
    }

    /**
     * 返回当前View
     *
     * @param state
     * @return
     */
    public View getView(ViewState state) {
        View view = null;
        if (state == ViewState.LOADING) {
            view = loadingView;
        } else if (state == ViewState.EMPTY) {
            view = emptyView;
        } else if (state == ViewState.ERROR) {
            view = errorView;
        } else {
            view = contentView;
        }
        return view;

    }

    /**
     * 切换状态
     *
     * @param view
     * @param
     * @param switchToState
     */
    public void setViewForState(View view, ViewState state, Boolean switchToState) {
        if (state == ViewState.LOADING) {
            if (loadingView != null) removeView(loadingView);
            loadingView = view;
            addView(view);
        } else if (state == ViewState.EMPTY) {
            if (emptyView != null) removeView(emptyView);
            emptyView = view;
            addView(view);
        } else if (state == ViewState.ERROR) {
            if (errorView != null) removeView(errorView);
            errorView = view;
            addView(view);
        } else if (state == ViewState.CONTENT) {
            if (contentView != null) removeView(contentView);
            contentView = view;
            addView(view);
        }

        if (switchToState) viewState = state;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (contentView == null) throw new IllegalArgumentException("Content view is not defined");

        if (viewState == ViewState.CONTENT) {
            setView(ViewState.CONTENT);
        } else {
            contentView.setVisibility(View.GONE);
        }
    }


    /**
     * 设置状态
     *
     * @param previousState
     */
    private void setView(ViewState previousState) {
        if (viewState == ViewState.LOADING) {
            if (loadingView != null) {
                if (contentView != null)
                    contentView.setVisibility(View.GONE);
                if (emptyView != null)
                    emptyView.setVisibility(View.GONE);
                if (errorView != null)
                    errorView.setVisibility(View.GONE);

                if (animateLayoutChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    loadingView.setVisibility(View.VISIBLE);
                }
            }

        } else if (viewState == ViewState.EMPTY) {
            if (emptyView != null) {
                if (contentView != null)
                    contentView.setVisibility(View.GONE);
                if (loadingView != null)
                    loadingView.setVisibility(View.GONE);
                if (errorView != null)
                    errorView.setVisibility(View.GONE);

                if (animateLayoutChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                }
            }

        } else if (viewState == ViewState.ERROR) {
            if (errorView != null) {
                if (contentView != null)
                    contentView.setVisibility(View.GONE);
                if (emptyView != null)
                    emptyView.setVisibility(View.GONE);
                if (loadingView != null)
                    loadingView.setVisibility(View.GONE);

                if (animateLayoutChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    errorView.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (contentView != null) {
                if (errorView != null)
                    errorView.setVisibility(View.GONE);
                if (emptyView != null)
                    emptyView.setVisibility(View.GONE);
                if (loadingView != null)
                    loadingView.setVisibility(View.GONE);


                if (animateLayoutChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    contentView.setVisibility(View.VISIBLE);
                }
            }
        }


    }

    /**
     * Animates the layout changes between [ViewState]
     *
     * @param previousView The view that it was currently on
     */
    private void animateLayoutChange(@Nullable final View previousView) {
        if (previousView == null) {

            (getView(viewState)).setVisibility(View.VISIBLE);
            return;
        }

        ObjectAnimator alpha = ObjectAnimator.ofFloat(previousView, "alpha", 1.0f, 0.0f);
        alpha.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                previousView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                previousView.setVisibility(View.GONE);
                View currentView = getView(viewState);
                if (currentView != null) {
                    currentView.setVisibility(View.VISIBLE);
                }
                ObjectAnimator.ofFloat(currentView, "alpha", 0.0f, 1.0f).setDuration(250L).start();
            }
        });
        alpha.start();
    }

    /**
     * 状态变更
     *
     * @param state
     */
    public void setViewState(ViewState state) {
        if (viewState != state) {
            viewState = state;
            setView(viewState);
            if (listener != null)
                listener.onStateChanged(state);
        }


    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        if (parcelable == null)
            return parcelable;
        else

            return new SavedState(parcelable, viewState);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            super.onRestoreInstanceState(((SavedState) state).getSuperState());
            viewState = ((SavedState) state).getState();
        } else {
            super.onRestoreInstanceState(state);
        }
    }


    @Override
    public void addView(View child) {
        if (isValidContentView(child)) contentView = child;
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (isValidContentView(child)) contentView = child;
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (isValidContentView(child)) contentView = child;
        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) contentView = child;
        super.addView(child, params);
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) contentView = child;
        super.addView(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) contentView = child;

        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if (isValidContentView(child)) contentView = child;
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    /**
     * Checks if the given [View] is valid for the Content View
     *
     * @param view The [View] to check
     * @return
     */
    private boolean isValidContentView(View view) {
        if (contentView != null && contentView != view) {
            return false;
        } else
            return view != loadingView && view != errorView && view != emptyView;
    }

    public void setStateListener(StateListener stateListener) {
        this.listener = stateListener;

    }

    public interface StateListener {
        /**
         * Callback for when the [ViewState] has changed
         *
         * @param viewState The [ViewState] that was switched to
         */
        void onStateChanged(ViewState viewState);
    }

    private class SavedState extends BaseSavedState {

        private ViewState state;
        private Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        public ViewState getState() {
            return state;
        }

        public SavedState(Parcel source) {
            super(source);
            state = (ViewState) source.readSerializable();
        }


        public SavedState(Parcelable superState, ViewState state) {
            super(superState);
            this.state = state;

        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeSerializable(state);
        }
    }

    public enum ViewState {
        CONTENT,
        LOADING,
        ERROR,
        EMPTY
    }
}
