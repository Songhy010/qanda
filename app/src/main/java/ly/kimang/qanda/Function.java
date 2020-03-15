package ly.kimang.qanda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Function {
    public String readFileAsset(Context ctx, String fileName) {
        String mLine = "", index = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open(fileName), "UTF-8"));
            while ((mLine = reader.readLine()) != null) {
                index += mLine;
            }
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage() + "");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("Error: ", e.getMessage() + "");
                }
            }
        }
        return index;
    }

    public void openActivity(final Context context, final Class<?> frmClass, final HashMap<String, String> hashMap) {
        openActivity(context, frmClass, hashMap, false, false, false);
    }

    public void openActivityFromThread(final Context context, final Class<?> frmClass, final HashMap<String, String> hashMap) {
        openActivity(context, frmClass, hashMap, false, false, true);
    }

    public void openActivity(final Context context, final Class<?> frmClass, final HashMap<String, String> hashMap, final boolean clearTop, final boolean clearAll, final boolean fromThread) {
        try {
            Intent i = new Intent(context, frmClass);
            if (clearTop) {
                if (getAppVersion(context) >= 11) {
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                } else {
                    i = Intent.makeRestartActivityTask(i.getComponent());
                }
            }
            if (clearAll) {
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ((Activity) context).finish();
            }
            if (fromThread) {
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if (hashMap != null) {
                i.putExtra("map", hashMap);
            }
            context.startActivity(i);
            ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (Exception e) {
        }
    }

    public void openActivityForResult(final Context context, final Class<?> frmClass, final HashMap<String, String> hashMap, int code) {
        try {
            Intent i = new Intent(context, frmClass);
            if (hashMap != null) {
                i.putExtra("map", hashMap);
            }
            ((Activity) context).startActivityForResult(i, code);
            ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    public int getAppVersion(final Context context) {
        int version = 0;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
        return version;
    }

    public HashMap<String, String> getIntentHashMap(final Intent intent) {
        try {
            if (intent != null) {
                return (HashMap<String, String>) intent.getSerializableExtra("map");
            }
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
        return null;
    }
    public void openActivity(final Context context, final Class<?> frmClass) {
        openActivity(context, frmClass, null);
    }

}
