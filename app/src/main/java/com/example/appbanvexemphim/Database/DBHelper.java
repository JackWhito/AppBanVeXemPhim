package com.example.appbanvexemphim.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.appbanvexemphim.PreLogin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper {
    protected static final String DATABASE_NAME = "ltdd.db";
    protected  static final String DB_PATH_SUFFIX = "/databases/";
    protected Context context;
    public DBHelper(Context context){
        this.context = context;
        processSQLite();
    }

    private void processSQLite() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()){
            try{
                CopyDatabaseFromAsset();
                Toast.makeText(context, "Sao chep thanh cong", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void CopyDatabaseFromAsset() {
        try{
            InputStream dbInputStream =context.getAssets().open(DATABASE_NAME);
            String outputStream = getPathDatabaseSystem();
            File file = new File(context.getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if (!file.exists()){
                file.mkdir();
            }
            OutputStream dbOutputStream = new FileOutputStream(outputStream);
            byte[] buffer = new byte[1024];
            int length;
            while((length=dbInputStream.read(buffer))>0){
                dbOutputStream.write(buffer,0,length);
            }
            dbOutputStream.flush();
            dbOutputStream.close();
            dbInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPathDatabaseSystem() {
        return context.getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }
}
