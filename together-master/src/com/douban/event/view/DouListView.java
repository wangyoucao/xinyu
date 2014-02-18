package com.douban.event.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DouListView extends ListView
{
  protected boolean changed;
  protected boolean hasMore;
  protected boolean isLoading;
  public ListLoadListener loadListener;
  protected View loadMoreView;
  protected boolean noMoreResult;

  public DouListView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public DouListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public DouListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  public void addListLoadListener(ListLoadListener paramListLoadListener)
  {
    this.loadListener = paramListLoadListener;
  }

  public int getListCount()
  {
    if (getAdapter() != null)
      return getAdapter().getCount();
    return 0;
  }

  public void init()
  {
    this.hasMore = false;
    this.noMoreResult = false;
    this.isLoading = false;
    setScrollingCacheEnabled(true);
    setScrollbarFadingEnabled(true);
    setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        int i = DouListView.this.getFirstVisiblePosition();
        int j = DouListView.this.getLastVisiblePosition();
        int k = DouListView.this.getCount();
        if ((i != 0) && (j == k - 1) && (!DouListView.this.noMoreResult) && (!DouListView.this.isLoading) && (!DouListView.this.hasMore))
        {
          DouListView.this.hasMore = true;
          if ((DouListView.this.loadListener != null) && (!DouListView.this.noMoreResult))
          {
            DouListView.this.loadListener.loadMoreResult();
            DouListView.this.isLoading = true;
          }
        }
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
      }
    });
    setupListFooter();
  }

  public boolean isHasMore()
  {
    return this.hasMore;
  }

  public boolean isLoading()
  {
    return this.isLoading;
  }

  public boolean isNoMoreResult()
  {
    return this.noMoreResult;
  }

  public void removeListLoadListener()
  {
    this.loadListener = null;
  }

  public void restoreLoadStatus()
  {
    this.isLoading = false;
    this.hasMore = false;
  }

  public void setHasMore(boolean paramBoolean)
  {
    this.hasMore = paramBoolean;
  }

  public void setLoading(boolean paramBoolean)
  {
    this.isLoading = paramBoolean;
  }

  public void setNoMoreResult(boolean paramBoolean)
  {
    this.noMoreResult = paramBoolean;
  }

  public void setupListFooter()
  {
    ListLoadListener localListLoadListener = this.loadListener;
    View localView = null;
    if (localListLoadListener != null)
      localView = this.loadListener.makeLoadMoreView();
    this.loadMoreView = localView;
    if (this.loadMoreView == null)
    {
      this.loadMoreView = LayoutInflater.from(getContext()).inflate(2130903058, null);
      this.loadMoreView.setVisibility(8);
      addFooterView(this.loadMoreView, null, false);
    }
  }

  public void updateListFooter(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      this.loadMoreView.setVisibility(0);
      return;
    }
    this.loadMoreView.setVisibility(8);
  }
}

/* Location:           E:\androdproject\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     com.douban.event.view.DouListView
 * JD-Core Version:    0.6.2
 */