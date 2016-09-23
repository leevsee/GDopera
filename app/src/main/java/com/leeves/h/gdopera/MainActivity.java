package com.leeves.h.gdopera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.leeves.h.gdopera.adapter.OperaGridViewAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private long exitTime = 0;
    private GridView mOperaGridView;
    private List<OperaInfo> mOperaInfos;
    private static final String OPEARURL = "http://www.xiangshengw.com/xiqu/yj/list_558_1.html";
    public static final String FOLDER = Environment.getExternalStorageDirectory() + File.separator + "GDOpera" + File.separator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_main);
        mOperaGridView = (GridView) findViewById(R.id.opera_gridView);
        mOperaInfos = new ArrayList<>();
        new RequestOperaDataTask().execute(OPEARURL);
        //点击进入video
        mOperaGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, mOperaInfos.get(position).getOperavideoUrl(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse(mOperaInfos.get(position).getOperavideoUrl());
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    /**
     * Function: 在主activity,两次退出程序
     *
     * @param keyCode
     * @param event
     * @return null
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Function：异步获取图片，标题，并显示出来
     * Created by h on 2016/9/7.
     *
     * @author Leeves
     */
    class RequestOperaDataTask extends AsyncTask<String, Integer, List<OperaInfo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "正在加载，请稍后。。。", Toast.LENGTH_LONG).show();
            Log.i("_____________", "onPreExecute");
        }

        @Override
        protected List<OperaInfo> doInBackground(String... params) {
//            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
//            if (databaseHelper.queryPictureData()) {
//                Log.i("方法doInBackground——", "无数据库，返回数据为ture");
//                return mOperaInfos;
//            } else {
//                Log.i("方法doInBackground——", "有数据库，返回数据为false");
//                downPicture(requestData(params[0]));
//                databaseHelper.insertPictureData(mOperaInfos);
//                return mOperaInfos;
//            }
            return requestData(params[0]);
        }

        @Override
        protected void onPostExecute(List<OperaInfo> result) {
            super.onPostExecute(result);
            OperaGridViewAdapter operaGridViewAdapter = new OperaGridViewAdapter(MainActivity.this, result);
            mOperaGridView.setAdapter(operaGridViewAdapter);
            operaGridViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Function 解析html，获得title，videoUrl，imageBitmap，并存在List中
     *
     * @param urlString 传入url
     * @return List<OperaInfo>
     */
    private List<OperaInfo> requestData(String urlString) {
        try {
            //第三方jar包：jsoup
            org.jsoup.nodes.Document doc = Jsoup.connect(OPEARURL).timeout(10000).get();
            Elements urlVidoElements = doc.select("div.index_middle_c ul li a");
//            Elements urlTitleElements = doc.select("div.index_middle_c ul li a b");
//            Elements imgElements = doc.select("div.index_middle_c ul li a > img");
            Elements imgElements = urlVidoElements.select("noscript img");
            int count = urlVidoElements.size();
            Log.i("count+++++++++++++++++", "count =" + count);
            for (int i = 0; i < count; i++) {
                Element titleElement = null;
                Element vidoElement = urlVidoElements.get(i);
                Element imgElement = imgElements.get(i);
                titleElement = vidoElement.select("b").first();
                String title;
                if (titleElement != null) {
                    title = vidoElement.text();
                } else {
                    title = vidoElement.attr("title");
                }
                String videoUrl = "http://www.xiangshengw.com" + vidoElement.attr("href");
                String imgUrl = "http://www.xiangshengw.com" + imgElement.attr("src");
                String pictureName = "";
                if (imgUrl.indexOf("1-") > 0) {
                    pictureName = imgUrl.substring(imgUrl.indexOf("1-"));
                }
                mOperaInfos.add(new OperaInfo(title, videoUrl, null, pictureName, imgUrl, FOLDER + pictureName));
            }
            return mOperaInfos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function 通过图片url，返回Btmap
     *
     * @param pictureUrl 图片url
     * @return bitmap
     */
    private Bitmap getPictureBitmap(String pictureUrl) {
        URL url;
        try {
            url = new URL(pictureUrl);
            InputStream is = url.openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function:List<OperaInfo> operaInfoList，把图片下载到文件夹中，并判断是否存在
     *
     * @param operaInfoList Operainfo数组
     */
    private void downPicture(List<OperaInfo> operaInfoList) {
        if (operaInfoList != null) {
            try {
                File file = new File(FOLDER);
                Log.i("文件名是——————", FOLDER);
                //判断文件夹是否存在
                if (!file.exists()) {
                    file.mkdir();
                }
                URL url;
                URLConnection urlConnection;
                InputStream inputStream = null;
                OutputStream outputStream = null;
                String pictureUrl;
                String pictureUri;
                //遍历operaInfoList，下载图片
                for (int i = 0; i < operaInfoList.size(); i++) {
                    pictureUrl = operaInfoList.get(i).getPictureUrl();
                    pictureUri = operaInfoList.get(i).getPictureUri();
                    //判断图片文件是否存在
                    File pictureFile = new File(pictureUri);
                    Log.i("for——————", pictureUri + "            " + i + pictureUrl);
                    if (!pictureFile.exists()) {
                        url = new URL(pictureUrl);
                        urlConnection = url.openConnection();
                        inputStream = urlConnection.getInputStream();
                        outputStream = new FileOutputStream(pictureUri);
                    }
                    if (inputStream != null) {
                        inputStream.close();
                        outputStream.close();
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
