package hiroki11x.androidwearaccerarationdetectorsample;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by hirokinaganuma on 16/02/27.
 */
public class GestureDetector implements SensorEventListener {

    private final static int WAITING_TIME = 30;
    private final static int MAX_FRAME_LEN = 30;
    private final static int REC_FRAME_LEN = 20; //2.0sec
    private final static float MIN_THRES = 2.0f;
    private int countDown = WAITING_TIME;
    private Boolean mStarted = false;
    private Boolean mRecStarted = false;
    private SensorManager mSensorManager;
    private Activity mActivity;
    private String accData = String.format("Non Value");;

    private class AccValue {
        AccValue(long timestamp, float[] values) {
            this.timestamp = timestamp;
            this.ax = values[0];
            this.ay = values[1];
            this.az = values[2];
        }
        public float ax,ay,az;
        public long timestamp;
        public float getDelta(AccValue b) {
            return (float)sqrt(pow(b.ax - this.ax,2) +  pow(b.ay - this.ay,2) +  pow(b.az - this.az,2));
        }
        public String getInfo() {
            return String.format("Acc:\n%f, %f, %f", this.ax,this.ay,this.az);
        }
    }

    private AccValue lastAccValue = null;
    private LinkedList<AccValue> mRecData = new LinkedList<AccValue>();

    public GestureDetector(Activity mActivity) {
        this.mActivity = mActivity;
        mSensorManager = (SensorManager) mActivity.getSystemService(mActivity.SENSOR_SERVICE);
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            Sensor s = sensors.get(0);
            mSensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
        }
    }

    //String型の加速度データを返す
    public String getAccData(){
        if(accData==null){
            return "";
        }else {
            return accData;
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()== Sensor.TYPE_ACCELEROMETER ) {
            if (lastAccValue != null) {
//                if (mStarted) {//従来はstart buttonクリックしてたら
                if (mRecStarted == false) {
                    if (lastAccValue.getDelta(new AccValue(event.timestamp, event.values)) > MIN_THRES) {
                        mRecStarted = true;
                        countDown = REC_FRAME_LEN;
                        mRecData.clear();
                        Toast.makeText(mActivity.getApplicationContext(), "Rec start.", Toast.LENGTH_SHORT).show();
                    }
//                    }
                }
            }

            lastAccValue = new AccValue(event.timestamp, event.values);
            accData = lastAccValue.getInfo();
            if (mRecStarted) {
                mRecData.add(lastAccValue);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //ignore
    }

    /**
     * 加速度データのセーブする必要はなし
     * 精度向上のためにとるのはアリだけど
     *
     private void saveAccFile(Context c, String label) throws NoSuchElementException {
     String filename = String.format("%d_%s.csv" , mRecData.getFirst().timestamp, label);
     String sdPath = Environment.getExternalStorageDirectory().getPath();
     File newDir = new File(sdPath, "acc_data");
     if(!newDir.exists()) {
     newDir.mkdir();
     }
     String filePath = newDir.getAbsolutePath() + "/" + filename;

     // create data body
     String str = "";
     if (mRecData.size() > MAX_FRAME_LEN) {
     while (mRecData.size() != MAX_FRAME_LEN) {
     mRecData.removeLast();
     }
     }
     for (AccValue a:mRecData) {
     str += String.format("%f,%f,%f\n", a.ax,a.ay,a.az);
     }

     // write data
     BufferedWriter bw = null;
     try {
     bw = new BufferedWriter(new OutputStreamWriter(
     new FileOutputStream(filePath), "utf-8"));
     bw.write(str);
     bw.close();
     Toast.makeText(c, "File saved", Toast.LENGTH_SHORT).show();
     } catch (Exception e) {
     Log.e("test", e.getLocalizedMessage());
     }
     }

     * */

}

/**
 * Created by hirokinaganuma on 16/02/27.
 */