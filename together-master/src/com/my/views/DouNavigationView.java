package com.my.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DouNavigationView extends LinearLayout
  implements View.OnClickListener
{
  protected RelativeLayout leftContainer;
  private View leftNavView;
  private final RelativeLayout mContentLayout;
  private DouNavigationOnClickListener mOnClickedListener;
  protected RelativeLayout rightContainer;
  private View rightNavView;
  protected RelativeLayout titleContainer;
  private TextView titleTextView;
  private View titleView;

  public DouNavigationView(Context paramContext)
  {
    this(paramContext, null);
  }

  public DouNavigationView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-1, -1);
    this.mContentLayout = new RelativeLayout(getContext());
    this.mContentLayout.setLayoutParams(localLayoutParams1);
    this.mContentLayout.setBackgroundResource(2130837630);
    addView(this.mContentLayout);
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -1);
    localLayoutParams2.addRule(9);
    this.leftContainer = new RelativeLayout(getContext());
    this.leftContainer.setVisibility(8);
    this.leftContainer.setBackgroundResource(2130837524);
    this.mContentLayout.addView(this.leftContainer, localLayoutParams2);
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-2, -1);
    localLayoutParams3.addRule(11);
    this.rightContainer = new RelativeLayout(getContext());
    this.rightContainer.setBackgroundResource(2130837525);
    this.rightContainer.setVisibility(8);
    this.mContentLayout.addView(this.rightContainer, localLayoutParams3);
    RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(-1, -1);
    localLayoutParams4.addRule(0, this.rightContainer.getId());
    localLayoutParams4.addRule(1, this.leftContainer.getId());
    this.titleContainer = new RelativeLayout(getContext());
    this.titleContainer.setLayoutParams(localLayoutParams4);
    this.mContentLayout.addView(this.titleContainer);
    setBackgroundColor(-9789264);
  }

  public void addNavigationOnClickListener(DouNavigationOnClickListener paramDouNavigationOnClickListener)
  {
    this.mOnClickedListener = paramDouNavigationOnClickListener;
  }

  public void onClick(View paramView)
  {
    if (paramView == this.rightContainer)
      if (this.mOnClickedListener != null)
        this.mOnClickedListener.onRightNavigationClicked(this);
    while ((paramView != this.leftContainer) || (this.mOnClickedListener == null))
      return;
    this.mOnClickedListener.onLeftNavigationClicked(this);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
  }

  public void removeNavigationOnClickListener()
  {
    this.mOnClickedListener = null;
  }

  public void setLeftNavItem(View paramView)
  {
    if (this.leftNavView != null)
      this.leftContainer.removeView(this.leftNavView);
    this.leftContainer.setVisibility(0);
    this.leftNavView = paramView;
    if (this.leftNavView != null)
    {
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
      localLayoutParams.addRule(13);
      this.leftContainer.setOnClickListener(this);
      this.leftContainer.addView(this.leftNavView, localLayoutParams);
    }
  }

  public void setRightNavItem(View paramView)
  {
    if (this.rightNavView != null)
      this.rightContainer.removeView(this.rightNavView);
    this.rightContainer.setVisibility(0);
    this.rightNavView = paramView;
    if (this.rightNavView != null)
    {
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
      localLayoutParams.addRule(13);
      this.rightContainer.setOnClickListener(this);
      this.rightContainer.addView(this.rightNavView, localLayoutParams);
    }
  }

  public void setTitle(String paramString)
  {
    this.titleTextView = new TextView(getContext());
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
    this.titleTextView.setLayoutParams(localLayoutParams);
    this.titleTextView.setTextSize(24.0F);
    this.titleTextView.setTextColor(-1);
    this.titleTextView.setText(paramString);
    setTitleView(this.titleTextView);
  }

  public void setTitleView(View paramView)
  {
    if (this.titleView != null)
      this.titleContainer.removeView(this.titleView);
    this.titleView = paramView;
    updateTitleView();
  }

  public void updateTitleView()
  {
    if (this.titleView != null)
    {
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
      localLayoutParams.addRule(13);
      this.titleContainer.addView(this.titleView, localLayoutParams);
    }
    this.mContentLayout.requestLayout();
  }
}

/* Location:           E:\androdproject\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     com.douban.event.view.DouNavigationView
 * JD-Core Version:    0.6.2
 */