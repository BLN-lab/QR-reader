package com.example.qrreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Writer extends AppCompatActivity {

    Button output,save;
    ImageView img;
    EditText edit;
    OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer);
        output=findViewById(R.id.Print);
        img=findViewById(R.id.Img);
        edit=findViewById(R.id.EditText);
        save= findViewById(R.id.Save);
        output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_txt=edit.getText().toString().trim();
                MultiFormatWriter write=new MultiFormatWriter();
                try {
                    BitMatrix matrix=write.encode(s_txt, BarcodeFormat.QR_CODE,400,400);
                    BarcodeEncoder encode=new BarcodeEncoder();
                    Bitmap bitmap=encode.createBitmap(matrix);
                    img.setImageBitmap(bitmap);
                    InputMethodManager manager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(edit.getApplicationWindowToken(),0);
                }catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable=(BitmapDrawable) img.getDrawable();
                Bitmap bitmap=drawable.getBitmap();
                File path= Environment.getExternalStorageDirectory();
                File dir=new File(path.getAbsolutePath()+"/Demo/");
                dir.mkdir();
                File file=new File(dir,System.currentTimeMillis()+".jpg");
                try{
                    outputStream=new FileOutputStream(file);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}