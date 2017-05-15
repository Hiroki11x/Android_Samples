package hiroki11x.androidwearaccerarationdetectorsample;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by hirokinaganuma on 16/02/27.
 */
public class ThreadUtils {
    private static Activity mActivity;

    public static void setActivity(Activity activity){
        mActivity = activity;
    }

    public static void runOnNewThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(mActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        },mActivity);
    }

    public static void runOnUiThread(Runnable runnable,Activity activity) {
        activity.runOnUiThread(runnable);
    }

}
