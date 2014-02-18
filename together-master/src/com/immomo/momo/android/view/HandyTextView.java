package com.immomo.momo.android.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Canvas;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

public class HandyTextView extends TextView
{
  public HandyTextView(Context paramContext)
  {
    super(paramContext, null);
  }

  public HandyTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public HandyTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected CharSequence b(CharSequence paramCharSequence)
  {
    int i = 0;
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(paramCharSequence);
    Matcher localMatcher = Pattern.compile("(\\[[.[^\\[\\]]]*?\\|et\\|([.[^\\[\\]]]*?\\|)*[.[^\\[\\]]]*?\\])").matcher(paramCharSequence);
    while (true)
    {
      if (!localMatcher.find())
      {
        if (i != 0)
          paramCharSequence = localSpannableStringBuilder;
        return paramCharSequence;
      }
      i = 1;
      getContext();
     // localSpannableStringBuilder.setSpan(new e(localMatcher.group()), localMatcher.start(), localMatcher.end(), 33);
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    try
    {
      super.onDraw(paramCanvas);
      return;
    }
    catch (Throwable localThrowable)
    {
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    try
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
    }
    catch (Exception localException)
    {
      setText("");
      super.onMeasure(paramInt1, paramInt2);
    }
  }

  public void setText(CharSequence paramCharSequence, TextView.BufferType paramBufferType)
  {
    if (paramCharSequence == null)
      paramCharSequence = "";
    try
    {
      super.setText(paramCharSequence, paramBufferType);
      return;
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           E:\andriod开源项目\android反编译\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.immomo.momo.android.view.HandyTextView
 * JD-Core Version:    0.6.2
 */