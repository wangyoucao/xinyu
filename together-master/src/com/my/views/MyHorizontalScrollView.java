package com.my.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class MyHorizontalScrollView extends HorizontalScrollView
{
  protected boolean canLoading;
  protected boolean isLoading;
  protected LinearLayout mContainer;
  protected ScrollOnLoadMore mLoadMoreImp;

  public MyHorizontalScrollView(Context paramContext)
  {
    this(paramContext, null);
  }

  public MyHorizontalScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public MyHorizontalScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  public ScrollOnLoadMore getLoadMore()
  {
    return this.mLoadMoreImp;
  }

  public void init()
  {
    this.canLoading = true;
  }

  public void loadMore()
  {
    if ((this.mLoadMoreImp != null) && (!this.isLoading))
    {
      this.isLoading = true;
      this.mLoadMoreImp.loadMore();
      this.isLoading = false;
    }
  }

  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((getScrollX() + getWidth() >= computeHorizontalScrollRange()) && (!this.isLoading))
      loadMore();
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setLoadDisable()
  {
    this.canLoading = false;
  }

  public void setLoadMore(ScrollOnLoadMore paramScrollOnLoadMore)
  {
    this.mLoadMoreImp = paramScrollOnLoadMore;
  }

  public static abstract interface ScrollOnLoadMore
  {
    public abstract void loadMore();
  }
}

/* Location:           E:\androdproject\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     com.douban.event.view.MyHorizontalScrollView
 * JD-Core Version:    0.6.2
 */