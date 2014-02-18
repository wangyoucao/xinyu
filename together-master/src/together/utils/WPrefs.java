package together.utils;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class WPrefs
{
  static SharedPreferences prefs;

//  public static ArrayList<String> banlist(Context paramContext)
//  {
//    try
//    {
//      ObjectInputStream localObjectInputStream = new ObjectInputStream(paramContext.openFileInput("bl"));
//      ArrayList localArrayList = (ArrayList)localObjectInputStream.readObject();
//      localObjectInputStream.close();
//      return localArrayList;
//    }
//    catch (IOException localIOException)
//    {
//      return null;
//    }
//    catch (ClassNotFoundException localClassNotFoundException)
//    {
//      break label32;
//    }
//    catch (OptionalDataException localOptionalDataException)
//    {
//      break label32;
//    }
//    catch (FileNotFoundException localFileNotFoundException)
//    {
//      label32: break label32;
//    }
//  }

  public static void banlist(Context paramContext, Set<String> paramSet)
  {
    try
    {
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(paramContext.openFileOutput("bl", 0));
      localObjectOutputStream.writeObject(new ArrayList(paramSet));
      localObjectOutputStream.close();
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public static void canChat(Context paramContext, boolean paramBoolean)
  {
    put("can_chat", paramBoolean);
  }

  public static boolean canChat(Context paramContext)
  {
    return prefs(paramContext).getBoolean("can_chat", false);
  }

  public static boolean isRegistered(Context paramContext)
  {
    return prefs(paramContext).getBoolean("registered", false);
  }

  public static void nearbyPushEnabled(Context paramContext, boolean paramBoolean)
  {
    put("nearby_push", paramBoolean);
  }

  public static boolean nearbyPushEnabled(Context paramContext)
  {
    return prefs(paramContext).getBoolean("nearby_push", true);
  }

  public static String nickname(Context paramContext)
  {
    return prefs(paramContext).getString("nickname", null);
  }

  public static String pin(Context paramContext)
  {
    return prefs(paramContext).getString("pin", "1111");
  }

//  public static void pin(Context paramContext, String paramString)
//  {
//    WSecure.pinchanged(paramString);
//    put("pin", paramString);
//  }

  public static void pinenabled(Context paramContext, boolean paramBoolean)
  {
    put("pin_enabled", paramBoolean);
  }

  public static boolean pinenabled(Context paramContext)
  {
    return prefs(paramContext).getBoolean("pin_enabled", true);
  }

  public static SharedPreferences prefs(Context paramContext)
  {
    if (prefs == null)
      prefs = PreferenceManager.getDefaultSharedPreferences(paramContext);
    return prefs;
  }

  public static String puid(Context paramContext)
  {
    return prefs(paramContext).getString("puid", null);
  }

  public static void puid(Context paramContext, String paramString)
  {
    put("puid", paramString);
  }

  public static void purchaseRegistered(Context paramContext, boolean paramBoolean)
  {
    put("pr", paramBoolean);
  }

  public static boolean purchaseRegistered(Context paramContext)
  {
    return prefs(paramContext).getBoolean("pr", false);
  }

  public static void pushSoundEnabled(Context paramContext, boolean paramBoolean)
  {
    put("push_sound", paramBoolean);
  }

  public static boolean pushSoundEnabled(Context paramContext)
  {
    return prefs(paramContext).getBoolean("push_sound", false);
  }

  public static void put(String paramString1, String paramString2)
  {
    prefs.edit().putString(paramString1, paramString2).commit();
  }

  public static void put(String paramString, boolean paramBoolean)
  {
    prefs.edit().putBoolean(paramString, paramBoolean).commit();
  }

  public static void register(Context paramContext, boolean paramBoolean)
  {
    put("registered", paramBoolean);
  }

  public static void reset()
  {
    put("soft", false);
    put("registered", false);
    prefs.edit().remove("ttkey").remove("ttsecret").remove("tttoken").commit();
  }

  public static void setNickname(Context paramContext, String paramString)
  {
    put("nickname", paramString);
  }

  public static void softRegister(Context paramContext)
  {
    put("soft", true);
  }

  public static boolean softRegistered(Context paramContext)
  {
    return prefs(paramContext).getBoolean("soft", false);
  }

  public static String ttkey(Context paramContext)
  {
    return prefs(paramContext).getString("ttkey", null);
  }

  public static void ttkey(Context paramContext, String paramString)
  {
    if (paramString != null)
      put("ttkey", paramString);
  }

//  public static String ttlogin64(Context paramContext)
//  {
//    String str1 = ttkey(paramContext);
//    String str2 = ttsecret(paramContext);
//    if (str1 == null)
//      BugSenseHandler.sendException(new Exception("TTKEY is null"));
//    if (str2 == null)
//      BugSenseHandler.sendException(new Exception("TTSECRET is null"));
//    if ((str1 == null) || (str2 == null))
//      return null;
//    return Base64.encodeToString((str1 + ":" + str2).getBytes(), 2);
//  }

  public static String ttsecret(Context paramContext)
  {
    return prefs(paramContext).getString("ttsecret", null);
  }

  public static void ttsecret(Context paramContext, String paramString)
  {
    if (paramString != null)
      put("ttsecret", paramString);
  }

  public static String tttoken(Context paramContext)
  {
    return prefs(paramContext).getString("tttoken", null);
  }

  public static void tttoken(Context paramContext, String paramString)
  {
    if (paramString != null)
      put("tttoken", paramString);
  }

  public static String tumblrsecret(Context paramContext)
  {
    return prefs(paramContext).getString("tusecret", null);
  }

  public static void tumblrsecret(Context paramContext, String paramString)
  {
    put("tusecret", paramString);
  }

  public static String tumblrtoken(Context paramContext)
  {
    return prefs(paramContext).getString("tutoken", null);
  }

  public static void tumblrtoken(Context paramContext, String paramString)
  {
    put("tutoken", paramString);
  }

  public static String twitsecret(Context paramContext)
  {
    return prefs(paramContext).getString("twsecret", null);
  }

  public static void twitsecret(Context paramContext, String paramString)
  {
    put("twsecret", paramString);
  }

  public static String twittoken(Context paramContext)
  {
    return prefs(paramContext).getString("twtoken", null);
  }

  public static void twittoken(Context paramContext, String paramString)
  {
    put("twtoken", paramString);
  }
}

/* Location:           E:\androdproject\dex2jar-0.0.9.13\whisper_dex2jar.jar
 * Qualified Name:     sh.whisper.data.WPrefs
 * JD-Core Version:    0.6.2
 */