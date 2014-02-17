package com.my.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class CreatePreviewImageView extends ImageView
{
  public CreatePreviewImageView(Context paramContext)
  {
    super(paramContext);
  }

  public CreatePreviewImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public CreatePreviewImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = View.MeasureSpec.getMode(paramInt1);
    int m = View.MeasureSpec.getMode(paramInt1);
    Log.v("W", "OnMeasure: " + i + "w " + j + "h");
    Log.v("OnMeasure", "Spec " + k + " " + m);
    if ((j > 920) && (i > 640))
    {
      float f1 = j / 920.0F;
      float f2 = i / 640.0F;
      float f3 = f2;
      if (f1 < f2)
        f3 = f1;
      j = (int)(920.0F * f3);
      i = (int)(640.0F * f3);
    }
    if ((i == 0) && (j == 0))
    {
      i = 920;
      j = 640;
      setMeasuredDimension(920, 640);
    }
     Log.v("TextImage", "Set w" + i + " h" + j);
   
      if (i == 0)
      {
        i = (int)(0.6956522F * j);
        setMeasuredDimension(i, j);
      }
      else if (j == 0)
      {
        j = (int)(i / 0.6956522F);
        setMeasuredDimension(i, j);
      }
      else if ((i >= 640) && (j >= 920))
      {
        setMeasuredDimension(i, j);
      }
      else if (i / 640.0F > j / 920.0F)
      {
        i = (int)(0.6956522F * j);
        setMeasuredDimension(i, j);
      }
      else
      {
        j = (int)(i / 0.6956522F);
        setMeasuredDimension(i, j);
      }
    }
  
}

/* Location:           E:\androdproject\dex2jar-0.0.9.13\whisper_dex2jar.jar
 * Qualified Name:     sh.whisper.ui.CreatePreviewImageView
 * JD-Core Version:    0.6.2
 */