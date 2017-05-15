package hiroki11x.gallaryimageloader_sample;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    private Uri m_uri;
    private float viewWidth;
    private static final int REQUEST_CHOOSER = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        calculateDisplaySize();
        setViews();
    }

    private void calculateDisplaySize() {
        // ウィンドウマネージャのインスタンス取得
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        // ディスプレイのインスタンス生成
        Display disp = wm.getDefaultDisplay();
        viewWidth = disp.getWidth();
    }

    private void setViews() {
        Button button = (Button) findViewById(R.id.buttonPanel);
        button.setOnClickListener(button_onClick);
    }

    private View.OnClickListener button_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showGallery();
        }
    };

    private void showGallery() {

        //カメラの起動Intentの用意
        String photoName = System.currentTimeMillis() + ".jpg";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, photoName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        m_uri = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, m_uri);

        // ギャラリー用のIntent作成
        Intent intentGallery;
        if (Build.VERSION.SDK_INT < 19) {
            intentGallery = new Intent(Intent.ACTION_GET_CONTENT);
            intentGallery.setType("image/*");
        } else {
            intentGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intentGallery.addCategory(Intent.CATEGORY_OPENABLE);
            intentGallery.setType("image/jpeg");
        }
        Intent intent = Intent.createChooser(intentCamera, "画像の選択");
        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intentGallery});
        startActivityForResult(intent, REQUEST_CHOOSER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHOOSER) {
            if (resultCode != RESULT_OK) {// キャンセル時
                return;
            }
            Uri resultUri = (data != null ? data.getData() : m_uri);
            if (resultUri == null) {// 取得失敗
                return;
            }

            // ギャラリーへスキャンを促す
            MediaScannerConnection.scanFile(
                    this,
                    new String[]{resultUri.getPath()},
                    new String[]{"image/jpeg"},
                    null
            );

            // 画像を設定
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageURI(resultUri);
            int orientation = getOrientation(resultUri);
            if (orientation > 0 && orientation < 9) {
                setMatrix(imageView, orientation, (int) viewWidth);
            }
        }
    }

    public int getOrientation(Uri uri) {
        //dataからfilepathへの変換
        ContentResolver cr = getContentResolver();
        String[] columns = {MediaStore.Images.ImageColumns.ORIENTATION};
        Cursor c = cr.query(uri, columns, null, null, null);
        c.moveToFirst();

        textView.setText("orientation: " + c.getInt(0));
        return c.getInt(0);
    }


    public static void setMatrix(ImageView view, int orientation, int width) {
        view.setScaleType(ImageView.ScaleType.MATRIX);
        int wOrg = view.getWidth();
        int hOrg = view.getHeight();
        LayoutParams lp = view.getLayoutParams();

        float factor;
        Matrix mat = new Matrix();
        mat.reset();
        switch (orientation) {
            case 1://only scaling
                factor = (float) width / (float) wOrg;
                mat.preScale(factor, factor);
                lp.width = (int) (wOrg * factor);
                lp.height = (int) (hOrg * factor);
                break;
            case 2://flip vertical
                factor = (float) width / (float) wOrg;
                mat.postScale(factor, -factor);
                mat.postTranslate(0, hOrg * factor);
                lp.width = (int) (wOrg * factor);
                lp.height = (int) (hOrg * factor);
                break;
            case 3://rotate 180
                mat.postRotate(180, wOrg / 2f, hOrg / 2f);
                factor = (float) width / (float) wOrg;
                mat.postScale(factor, factor);
                lp.width = (int) (wOrg * factor);
                lp.height = (int) (hOrg * factor);
                break;
            case 4://flip horizontal
                factor = (float) width / (float) wOrg;
                mat.postScale(-factor, factor);
                mat.postTranslate(wOrg * factor, 0);
                lp.width = (int) (wOrg * factor);
                lp.height = (int) (hOrg * factor);
                break;
            case 5://flip vertical rotate270
                mat.postRotate(270, 0, 0);
                factor = (float) width / (float) hOrg;
                mat.postScale(factor, -factor);
                lp.width = (int) (hOrg * factor);
                lp.height = (int) (wOrg * factor);
                break;
            case 6://rotate 90
                mat.postRotate(90, 0, 0);
                factor = (float) width / (float) hOrg;
                mat.postScale(factor, factor);
                mat.postTranslate(hOrg * factor, 0);
                lp.width = (int) (hOrg * factor);
                lp.height = (int) (wOrg * factor);
                break;
            case 7://flip vertical, rotate 90
                mat.postRotate(90, 0, 0);
                factor = (float) width / (float) hOrg;
                mat.postScale(factor, -factor);
                mat.postTranslate(hOrg * factor, wOrg * factor);
                lp.width = (int) (hOrg * factor);
                lp.height = (int) (wOrg * factor);
                break;
            case 8://rotate 270
                mat.postRotate(270, 0, 0);
                factor = (float) width / (float) hOrg;
                mat.postScale(factor, factor);
                mat.postTranslate(0, wOrg * factor);
                lp.width = (int) (hOrg * factor);
                lp.height = (int) (wOrg * factor);
                break;
        }
        view.setLayoutParams(lp);
        view.setImageMatrix(mat);
        view.invalidate();
    }

}