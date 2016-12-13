package com.example.n9420924.android_download_web_content;

//This app need previlidge connec to INTERNET-- do it in AndroidManifest.xml

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
public class MainActivity extends AppCompatActivity {
    ImageView downlaodImg;

    public void downloadImage(View v) {

        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;
        try {
            myImage = task.execute("http://images2.fanpop.com/images/photos/4600000/bart_simpson-the-simpson-kids-4637772-369-508.jpg").get();
            downlaodImg.setImageBitmap(myImage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i("Interaction", "Button tapped");
    }

//    Class to run download image. Also using thread in AsysncTask
    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... urls) {
            try {

                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return myBitmap;


            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


    }


    // class help to run in background (Thread)
    public class DownloadTask extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... urls) {
            //Log.i("URL", urls[0]);
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null; //urlConnection as webbrower use to open URL and fetch the content

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in  = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                //the final value will be -1
                while (data != -1) {
                    char current = (char) data;

                    result += current;//result will be a big String, keeping all data of current URL

                    data = reader.read();

                }
                return result;
            }
            catch (Exception e) {
                e.printStackTrace();
                return "Fail";
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downlaodImg = (ImageView) findViewById(R.id.imageView);
        DownloadTask task = new DownloadTask();

        String result = null;
        try {

            result = task.execute("https://www.ecowebhosting.co.uk/").get();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace(); //
        }

        Log.i("Contents of URL ", result);

    }
}
