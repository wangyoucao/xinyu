package com.douban.event.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DouErrorView extends LinearLayout
{
  protected ImageView errorImage;
  protected TextView errorText;

  public DouErrorView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public DouErrorView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public void init()
  {
    LayoutInflater.from(getContext()).inflate(2130903045, this);
    this.errorImage = ((ImageView)findViewById(2131492884));
    this.errorText = ((TextView)findViewById(2131492885));
    setGravity(16);
    setVisible(false);
  }

  public void setVisible(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      setVisibility(0);
      return;
    }
    setVisibility(4);
  }

  public void showErrorView(String paramString)
  {
    setVisible(true);
    this.errorImage.setImageResource(2130837636);
    this.errorText.setText(paramString);
  }

  public void showNullView(String paramString)
  {
    setVisible(true);
    this.errorImage.setImageResource(2130837638);
    this.errorText.setText(paramString);
  }
}

/* Location:           E:\androdproject\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     com.douban.event.view.DouErrorView
 * JD-Core Version:    0.6.2
 */