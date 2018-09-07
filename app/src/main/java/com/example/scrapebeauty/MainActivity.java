package com.example.scrapebeauty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv2);
        //改变图片大小
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        // 获取要操作的原图
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        // 创建一个副本，相当于和一个原图一样的白纸
        final Bitmap alterBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
        // 创建画笔
        Paint paint = new Paint();
        // 创建画布 把白纸铺到画布
        Canvas canvas = new Canvas(alterBitmap);
        // 开始作画
        canvas.drawBitmap(srcBitmap, new Matrix(), paint);

        iv.setImageBitmap(alterBitmap);
        // 给iv设置一个触摸事件
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 具体判断一下触摸事件
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:  // 移动的事件
                        for (int i = -20; i < 20; ++i) {
                            for (int j = -20; j < 20; ++j) {
                                if (Math.sqrt(i * i + j * j) < 20) {
                                    int x = (int) event.getX();
                                    int y = (int) event.getY();
                                    if (x + i < 0 || x + i >= alterBitmap.getWidth()) // 0到width-1的闭区间
                                        continue;
                                    if (y + j < 0 || y + j >= alterBitmap.getHeight())
                                        continue;
                                    Log.d(TAG, "getX: " + x + " getY:" + y);
                                    alterBitmap.setPixel(x + i, y + j, Color.TRANSPARENT);
                                }
                            }
                        }
                        iv.setImageBitmap(alterBitmap);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
