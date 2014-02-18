package event;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.util.Log;

public class EventBus
{
  public static final String APP_LAUNCHED = "app_launched";
  private static HashMap<String, ArrayList<Subscriber>> obs = new HashMap();

  public static void publish(String paramString)
  {
    Log.v("W", "Publishing event " + paramString);
    ArrayList localArrayList = (ArrayList)obs.get(paramString);
    Iterator localIterator=null;
    if (localArrayList != null)
    {
      Log.v("W", "Subscribers not null");
      localIterator = localArrayList.iterator();
    }
   
      if (!localIterator.hasNext())
        return;
      Subscriber localSubscriber = (Subscriber)localIterator.next();
      Log.v("W", "Notifying subscriber");
      localSubscriber.event(paramString);
    
  }

  public static void subscribe(String paramString, Subscriber paramSubscriber)
  {
    Log.v("W", "Subscriber for event " + paramString);
    ArrayList localArrayList = (ArrayList)obs.get(paramString);
    if (localArrayList == null)
    {
      HashMap localHashMap = obs;
      localArrayList = new ArrayList();
      localHashMap.put(paramString, localArrayList);
    }
    if (!localArrayList.contains(paramSubscriber))
    {
      Log.v("W", "added subscriber");
      localArrayList.add(paramSubscriber);
    }
  }

  public static void unsubscribe(String paramString, Subscriber paramSubscriber)
  {
    ArrayList localArrayList = (ArrayList)obs.get(paramSubscriber);
    if (localArrayList != null)
    {
      localArrayList.remove(paramSubscriber);
      if (localArrayList.isEmpty())
        obs.remove(paramString);
    }
  }

  public class Event
  {
    public static final String HIDE_CREATE = "hide_create";
    public static final String HIDE_PIN = "hidepin";
    public static final String HIDE_PIN_RESET = "hidepinreset";
    public static final String PIN_COMPLETE = "pin";
    public static final String PIN_FAILED = "pinfailed";
    public static final String REFRESHING = "refreshing";
    public static final String REFRESH_COMPLETE = "refreshed";
    public static final String REFRESH_REQUEST = "refresh";
    public static final String SHOW_CREATE = "show_create";
    public static final String SHOW_PIN = "showpin";
    public static final String SHOW_PIN_RESET = "showpinreset";

    public Event()
    {
    }
  }
}

/* Location:           E:\androdproject\dex2jar-0.0.9.13\whisper_dex2jar.jar
 * Qualified Name:     sh.whisper.event.EventBus
 * JD-Core Version:    0.6.2
 */