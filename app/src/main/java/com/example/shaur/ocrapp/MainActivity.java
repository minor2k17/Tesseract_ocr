package com.example.shaur.ocrapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;

    Button button;
    ImageView image;
    TextView result;
    Bitmap bitmap;
    private static final int GALLERY = 4;

    private static String TESSBASE_PATH= Environment.getRootDirectory().getPath();
    private static final String DEFAULT_LANGUAGE = "eng";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        image = (ImageView) findViewById(R.id.image);
        result = (TextView) findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, GALLERY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.setImageBitmap(bitmap);


            final TessBaseAPI baseApi = new TessBaseAPI();

            baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
            baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
            baseApi.setVariable(TessBaseAPI.VAR_SAVE_BLOB_CHOICES, TessBaseAPI.VAR_TRUE);

            baseApi.setImage(bitmap);
            String outputText = baseApi.getUTF8Text();
            baseApi.end();

            Log.i("TEXT:",outputText);
            if(DEFAULT_LANGUAGE.equalsIgnoreCase("eng")){
                outputText = outputText.replaceAll("[^a-zA-Z0-9]+", " ");
            }
            result.setText(outputText);



        }
    }
}

























