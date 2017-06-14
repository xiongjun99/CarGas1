package com.example.cargas.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.example.cargas.R;
import com.king.photo.zoom.PhotoView;

import java.io.File;

public class ShowPicture extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.show_pci);
        String path = getIntent().getStringExtra("url");
        File file = new File(path);


        PhotoView photoView = (PhotoView) findViewById(R.id.photo);

        if (file.exists()) {
            photoView.setImageURI(Uri.fromFile(file));
        }

        findViewById(R.id.img_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
}
