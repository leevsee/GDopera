package com.leeves.h.gdopera.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.leeves.h.gdopera.OperaInfo;

import java.util.List;

/**
 * Function：创建数据库
 * Created by h on 2016/9/12.
 *
 * @author Leeves
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "picture_title.db";
    public static final String PICTURE_TABLE_NAME = "picture";
    public static final String PICTURE_NAME = "pictureName";
    public static final String PICTURE_TITLE = "pictureTitle";
    public static final String PICTURE_VIDEO_URL = "videoUrl";
    public static final String PICTURE_URI = "picturePath";
    public static final int VERSION = 2;
    public static final boolean HASDATA = true;
    public static final boolean NODATA = false;

    private List<OperaInfo> mOperaInfos;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PICTURE_TABLE_NAME + "(" + PICTURE_NAME + " varchar(20) not null , " + PICTURE_TITLE + " varchar(60) not null , " + PICTURE_URI + " varchar(60) not null , " + PICTURE_VIDEO_URL + " varchar(60) not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Fucntion:传入List<OperaInfo>，遍历插入数据
     *
     * @param operaInfoList
     */
    public void insertPictureData(List<OperaInfo> operaInfoList) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        long rowNumber = 0;
        for (int i = 0; i < operaInfoList.size(); i++) {
            contentValues.put(PICTURE_NAME, operaInfoList.get(i).getPictureName());
            contentValues.put(PICTURE_TITLE, operaInfoList.get(i).getOperaTitle());
            contentValues.put(PICTURE_URI, operaInfoList.get(i).getPictureUri());
            contentValues.put(PICTURE_VIDEO_URL, operaInfoList.get(i).getOperavideoUrl());
            rowNumber = sqLiteDatabase.insert(DatabaseHelper.PICTURE_TABLE_NAME, null, contentValues);
            if (rowNumber != -1) {
                Log.i("方法insertPictureData", "插入数据，第几条"+i);
            }
        }
    }


    public boolean queryPictureData() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(PICTURE_TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getCount();
            for (int i = 0; i < count; i++) {
                String pictureName = cursor.getString(cursor.getColumnIndexOrThrow(PICTURE_NAME));
                String pictureTile = cursor.getString(cursor.getColumnIndexOrThrow(PICTURE_TITLE));
                String videoUrl = cursor.getString(cursor.getColumnIndexOrThrow(PICTURE_VIDEO_URL));
                String picturePath = cursor.getString(cursor.getColumnIndexOrThrow(PICTURE_URI));
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                mOperaInfos.add(new OperaInfo(pictureTile, videoUrl, bitmap, pictureName, null, null));
            }
            return HASDATA;
        } else {
            return NODATA;
        }
    }
}
