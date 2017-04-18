package com.example.emily.a362first;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

<<<<<<< HEAD
=======

>>>>>>> 07e31ae5eeb086b7c23209ef55ad04fd09fb84bd
    NodeList nodelist;
    EditText title, link;
    boolean running = false;
    SensorManager sensorManager;
    TextView pedomSteps, textview;
    String URL = "https://www.quotesdaddy.com/feed/tagged/Inspirational";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedomSteps = (TextView) findViewById(R.id.stepcounter);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        textview = (TextView) findViewById(R.id.quote);
        new DownloadXML().execute(URL);
        Button btnWeather = (Button) findViewById(R.id.yourButtonsId);
        btnWeather.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://weather.com/weather/today/l/USCA0408:1:US"));
                startActivity(intent);
            }
        });
    }//end onCreate


    private class DownloadXML extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... Url) {
            try {
                URL url = new URL(Url[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                nodelist = doc.getElementsByTagName("item");
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            for (int temp = 0; temp < nodelist.getLength(); temp++) {
                Node nNode = nodelist.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    // Set the texts into TextViews from item nodes
                    // Get the channel
                    textview.setText(textview.getText() +
                            getNode("title", eElement) + "\n");
                }
            }
        }
    }

    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        //if you unrsiter the hardware will stop detecting steps
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            pedomSteps.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void openStopwatch(View view) {
        Intent intent = new Intent(this, Stopwatch.class);
        startActivity(intent);
    }

    public void openToDO(View view){
        Intent intent = new Intent(this, TDLIST.class);
        startActivity(intent);
    }

    public void openAlarmClock(View view) {
        Intent intent = new Intent(this, AlarmClock.class);
        startActivity(intent);
    }



    public void OpenDiary(View view)
    {
        Intent intent = new Intent(this, Diary.class);
        intent.putExtra("Main", "no");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode) {
            case 1:
                if(resultCode == Activity.RESULT_CANCELED)
                {

                }


        }
    }



/*
    public void Cancel(View view) {
        Intent intent = new Intent(this, FoodEntry.class);
        setResult(Diary.RESULT_CANCELED, intent);
        finish();
    }
*/

    public void openWeather(View view) {

    }

}
