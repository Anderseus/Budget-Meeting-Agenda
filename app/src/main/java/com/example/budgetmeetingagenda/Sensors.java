package com.example.budgetmeetingagenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Sensors extends Activity implements SensorEventListener {

    TextView accelX;
    TextView accelY;
    TextView accelZ;

    TextView oriX;
    TextView oriY;
    TextView oriZ;

    TextView velX;
    TextView velY;
    TextView velZ;

    TextView posX;
    TextView posY;
    TextView posZ;

    TextView gyroX;
    TextView gyroY;
    TextView gyroZ;

    double positionX = 0.0;
    double positionY = 0.0;
    double positionZ = 0.0;

    double prevOriX = 0.0;
    double prevOriY = 0.0;
    double prevOriZ = 0.0;

    int rotatedX = 0;
    int rotatedY = 0;
    int rotatedZ = 0;

    int count = 1;
    int count2 = 1;

    int num = 0;

    long time1;
    long time2;

    ArrayList<Float> accelerationsX = new ArrayList<Float>();
    ArrayList<Float> accelerationsY = new ArrayList<Float>();
    ArrayList<Float> accelerationsZ = new ArrayList<Float>();

    ArrayList<Float> orientationsX = new ArrayList<Float>();
    ArrayList<Float> orientationsY = new ArrayList<Float>();
    ArrayList<Float> orientationsZ = new ArrayList<Float>();

    ArrayList<Float> averagesX = new ArrayList<Float>();
    ArrayList<Float> averagesY = new ArrayList<Float>();
    ArrayList<Float> averagesZ = new ArrayList<Float>();

    ArrayList<Float> positionsX = new ArrayList<Float>();
    ArrayList<Float> positionsY = new ArrayList<Float>();
    ArrayList<Float> positionsZ = new ArrayList<Float>();

    SensorManager sensorManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        Button button_01 = (Button)findViewById(R.id.button);
        Button button_02 = (Button)findViewById(R.id.button2);

        button_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sensors.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        button_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////
                // WRITE TO FILE HERE
                ////
                finish();
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        accelX = (TextView) findViewById(R.id.textView01);
        accelY = (TextView) findViewById(R.id.textView02);
        accelZ = (TextView) findViewById(R.id.textView03);

        oriX = (TextView) findViewById(R.id.textView04);
        oriY = (TextView) findViewById(R.id.textView05);
        oriZ = (TextView) findViewById(R.id.textView06);

        velX = (TextView) findViewById(R.id.textView07);
        velY = (TextView) findViewById(R.id.textView08);
        velZ = (TextView) findViewById(R.id.textView09);

        posX = (TextView) findViewById(R.id.textView10);
        posY = (TextView) findViewById(R.id.textView11);
        posZ = (TextView) findViewById(R.id.textView12);

//        gyroX = (TextView) findViewById(R.id.textView13);
//        gyroY = (TextView) findViewById(R.id.textView14);
//        gyroZ = (TextView) findViewById(R.id.textView15);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener( this ,
                sensorManager.getDefaultSensor( Sensor.TYPE_LINEAR_ACCELERATION ) ,
                SensorManager.SENSOR_DELAY_GAME );
        sensorManager.registerListener( this ,
                sensorManager.getDefaultSensor( Sensor.TYPE_ORIENTATION ) ,
                SensorManager.SENSOR_DELAY_GAME );
        sensorManager.registerListener( this ,
                sensorManager.getDefaultSensor( Sensor.TYPE_GYROSCOPE ) ,
                SensorManager.SENSOR_DELAY_GAME );
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        sensorManager.unregisterListener( this ,
                sensorManager.getDefaultSensor( Sensor.TYPE_LINEAR_ACCELERATION ));
        sensorManager.unregisterListener( this ,
                sensorManager.getDefaultSensor( Sensor.TYPE_ORIENTATION ));
        sensorManager.unregisterListener( this ,
                sensorManager.getDefaultSensor( Sensor.TYPE_GYROSCOPE ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sensors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if( count == 1 )
        {
            time1 = System.currentTimeMillis();
        }
        synchronized (this)
        {
            switch( sensorEvent.sensor.getType() )
            {
                case Sensor.TYPE_LINEAR_ACCELERATION:
//                    if( sensorEvent.values[0] <= 0.15 && sensorEvent.values[0] >= -0.15 ) sensorEvent.values[0] = 0;
//                    if( sensorEvent.values[1] <= 0.15 && sensorEvent.values[1] >= -0.15 ) sensorEvent.values[1] = 0;
//                    if( sensorEvent.values[2] <= 0.15 && sensorEvent.values[2] >= -0.15 ) sensorEvent.values[2] = 0;
                    accelX.setText( "Acceleration X: " + Float.toString( sensorEvent.values[0] ));
                    accelY.setText( "Acceleration Y: " + Float.toString( sensorEvent.values[1] ));
                    accelZ.setText( "Acceleration Z: " + Float.toString( sensorEvent.values[2] ));

                    accelerationsX.add( sensorEvent.values[0] );
                    accelerationsY.add( sensorEvent.values[1] );
                    accelerationsZ.add( sensorEvent.values[2] );
                    break;

                case Sensor.TYPE_ORIENTATION:
                    if( sensorEvent.values[0] <= 1 && prevOriX >= 359 ) rotatedX += 1;
                    if( sensorEvent.values[0] >= 359 && prevOriX <= 1 ) rotatedX -= 1;
                    if( sensorEvent.values[1] <= 1 && prevOriY >= 359 ) rotatedY += 1;
                    if( sensorEvent.values[1] >= 359 && prevOriY <= 1 ) rotatedY -= 1;
                    if( sensorEvent.values[2] <= 1 && prevOriZ >= 359 ) rotatedZ += 1;
                    if( sensorEvent.values[2] >= 359 && prevOriZ <= 1 ) rotatedZ -= 1;

                    oriX.setText( "Orientation X: " + Float.toString( sensorEvent.values[0] + rotatedX * 360 ));
                    oriY.setText( "Orientation Y: " + Float.toString( sensorEvent.values[1] + rotatedY * 360 ));
                    oriZ.setText( "Orientation Z: " + Float.toString( sensorEvent.values[2] + rotatedZ * 360 ));

                    orientationsX.add( sensorEvent.values[0] );
                    orientationsY.add( sensorEvent.values[1] );
                    orientationsZ.add( sensorEvent.values[2] );

                    prevOriX = sensorEvent.values[0];
                    prevOriY = sensorEvent.values[1];
                    prevOriZ = sensorEvent.values[2];
                    break;

//                case Sensor.TYPE_GYROSCOPE:
//                    gyroX.setText( "Gyroscope X: " + Float.toString( sensorEvent.values[0] ));
//                    gyroY.setText( "Gyroscope Y: " + Float.toString( sensorEvent.values[1] ));
//                    gyroZ.setText( "Gyroscope Z: " + Float.toString( sensorEvent.values[2] ));
//                break;

            }
        }

        synchronized (this)
        {
            if( accelerationsX.size() >= 20 && num + 20 < accelerationsX.size() )
            {
                averagesX.add( calculateAverage( accelerationsX.subList( num , num + 19 )));
                averagesY.add( calculateAverage( accelerationsY.subList( num , num + 19 )));
                averagesZ.add( calculateAverage( accelerationsZ.subList( num , num + 19 )));

                num += 1;
            }
            if( count2 == 100 )
            {


//                Log.d( "" , "X: " + orientationsX.toString() );
//                Log.d( "" , "Y: " + orientationsY.toString() );
//                Log.d( "" , "Z: " + orientationsZ.toString() );
//
//                orientationsX.clear();
//                orientationsY.clear();
//                orientationsZ.clear();

//                Log.d( "" , "X: " + accelerationsX.toString() );
//                Log.d( "" , "Y: " + accelerationsY.toString() );
//                Log.d( "" , "Z: " + accelerationsZ.toString());

                try {
                    String name = "data.txt";
                    int num = 1;
                    File file;
                    while( true ) {
                        file = new File(((Context) this).getExternalFilesDir(null), name );
                        if (!file.exists())
                        {
                            file.createNewFile();
                            break;
                        }
                        else
                        {
                            name = "data" + num + ".txt";
                            num += 1;
                        }
                    }
                    BufferedWriter writer = new BufferedWriter( new FileWriter( file , true ) );
                    writer.write(String.valueOf(positionsX));
                    writer.write(String.valueOf(positionsY));
                    writer.write(String.valueOf(positionsZ));
                    writer.close();

                    MediaScannerConnection.scanFile((Context) (this), new String[]{file.toString()}, null, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                accelerationsX.clear();
                accelerationsY.clear();
                accelerationsZ.clear();

//                Log.d( "" , "X: " + averagesX.toString() );
//                Log.d( "" , "Y: " + averagesY.toString() );
//                Log.d( "" , "Z: " + averagesZ.toString());

                averagesX.clear();
                averagesY.clear();
                averagesZ.clear();

                Log.d( "" , "X: " + positionsX.toString());
                Log.d( "" , "X: " + positionsX.toString() );
                Log.d( "" , "X: " + positionsX.toString() );

                positionsX.clear();
                positionsY.clear();
                positionsZ.clear();

                count2 = 0;
                num = 0;
            }
            if( count == 10 )
            {
                time2 = System.currentTimeMillis();
                long num = time2 - time1;
                double dt = (double) num;
//                Log.d( "" , "" + num );
                dt /= 100.0;
                DecimalFormat df = new DecimalFormat( "#.00000000" );
                switch( sensorEvent.sensor.getType() )
                {
                    case Sensor.TYPE_LINEAR_ACCELERATION:
                        velX.setText( "Velocity X: " + df.format( sensorEvent.values[0] * dt ));
                        velY.setText( "Velocity Y: " + df.format( sensorEvent.values[1] * dt ));
                        velZ.setText( "Velocity Z: " + df.format( sensorEvent.values[2] * dt ));
//                        Log.d( "" , "" +
//                                " X: " + df.format( sensorEvent.values[0] * dt ) +
//                                " Y: " + df.format( sensorEvent.values[1] * dt ) +
//                                " Z: " + df.format( sensorEvent.values[2] * dt ) +
//                                " dT: " + dt );

                        positionX += sensorEvent.values[0] * dt;
                        positionY += sensorEvent.values[1] * dt;
                        positionZ += sensorEvent.values[2] * dt;

                        posX.setText( "Position X: " + df.format( positionX ));
                        posY.setText( "Position Y: " + df.format( positionY ));
                        posZ.setText( "Position Z: " + df.format( positionZ ));

                        positionsX.add((float) positionX);
                        positionsY.add((float) positionY);
                        positionsZ.add((float) positionZ);
                }
                count = 1;
                count2++;
            }
            else
            {
                count++;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public Float calculateAverage( List<Float> Lst )
    {
        float total = 0.f;

        for( Float obj : Lst )
        {
            total += obj;
        }

        total /= Lst.size();
        return total;
    }
}
