package com.douban.event.view;

import android.view.View;

public abstract interface ListLoadListener
{
  public abstract void loadMoreResult();

  public abstract View makeLoadMoreView();
}

/* Location:           E:\androdproject\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     com.douban.event.view.ListLoadListener
 * JD-Core Version:    0.6.2
 */