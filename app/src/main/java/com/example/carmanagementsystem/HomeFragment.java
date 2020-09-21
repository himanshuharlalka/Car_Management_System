package com.example.carmanagementsystem;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class HomeFragment extends Fragment implements SensorEventListener{
    static float acceleration;
    SensorManager sensorManager;
    Sensor accelerometer;
    static boolean startPressed=false, stopPressed=false, running;
    static Chronometer durationChronometer;
    static ArrayList<Float> calculation = new ArrayList<>();
    static long offset=0;
    LocationManager locationManager;
    LocationListener locationListener;
    static String totalTime, topSpeed, averageSpeed, distance; //
    static ArrayList<String> duration = new ArrayList<String>();
    static ImageView startImageView, stopImageView, sosImageView;
    static TextView countdownTextView,distanceTextView, distanceTitleTextView, durationTitleTextView, speedTextView, speedTitleTextView, avgSpeedTextView, avgSpeedTitleTextView;
    static CountDownTimer countDownTimer, totalDriveTime;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }


    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.home_fragment,container,false);

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location Listener","Accessed");
                LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                Log.i("Latitude",Double.toString(location.getLatitude()));
                Log.i("Longitude",Double.toString(location.getLongitude()));
                Log.i("Speed",Float.toString(location.getSpeed()));
                if(startPressed) {
                    calculation.add(location.getSpeed());
                    float sum = 0;
                    for (int i = 0; i < calculation.size(); i++) {
                        sum = sum + calculation.get(i);

                    }
                    averageSpeed = Float.toString(sum / calculation.size());
                    Log.i("Average Speed", averageSpeed);
                    topSpeed = Float.toString(Collections.max(calculation));
                    Log.i("Top Speed", topSpeed);
                    speedTextView.setText(Float.toString(location.getSpeed()) + "kph");
                    avgSpeedTextView.setText(averageSpeed + "kph");
                    distance = Float.toString((sum / calculation.size()) * ((offset / 1000) / 3600));
                    Log.i("Distance", distance);
                    distanceTextView.setText(distance);
                }




            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(lastKnownLocation!=null) {
                Log.i("Location", lastKnownLocation.toString());
                LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

            }


        }
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(HomeFragment.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        //Declaration of Views******************************************
        startImageView = (ImageView)view.findViewById(R.id.startImageView);
        sosImageView=(ImageView)view.findViewById(R.id.sosImageView);
        stopImageView = (ImageView)view.findViewById(R.id.stopImageView);
        speedTextView=(TextView)view.findViewById(R.id.speedTextView);
        avgSpeedTextView=(TextView)view.findViewById(R.id.avgSpeedTextView);
        speedTitleTextView=(TextView)view.findViewById(R.id.speedTitleTextView);
        avgSpeedTitleTextView=(TextView)view.findViewById(R.id.avgSpeedTitleTextView);
        distanceTextView = (TextView)view.findViewById(R.id.distanceTextView);
        distanceTitleTextView=(TextView)view.findViewById(R.id.distanceTitleTextView);
        durationChronometer=view.findViewById(R.id.durationTextView);
        countdownTextView = (TextView)view.findViewById(R.id.countdownTextView);
        durationTitleTextView =(TextView)view.findViewById(R.id.durationTitleTextView);
        //****************************************************************
        //Visibility of Various TextView when opened for the first time
        distanceTextView.setVisibility(View.INVISIBLE);
        stopImageView.setVisibility(View.INVISIBLE);
        speedTextView.setVisibility(View.INVISIBLE);
        avgSpeedTextView.setVisibility(View.INVISIBLE);
        speedTitleTextView.setVisibility(View.INVISIBLE);
        avgSpeedTitleTextView.setVisibility(View.INVISIBLE);
        distanceTitleTextView.setVisibility(View.INVISIBLE);
        durationChronometer.setVisibility(View.INVISIBLE);
        durationTitleTextView.setVisibility(View.INVISIBLE);
        sosImageView.setVisibility(View.INVISIBLE);
        countdownTextView.setVisibility(View.INVISIBLE);
        //***********************************************************
        startImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Engine on","Pressed");
                startImageView.animate().translationYBy(10000).setDuration(1000); //Animation of Start Stop Button
                startImageView.setEnabled(false);
                countDownTimer = new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        countdownTextView.setVisibility(View.VISIBLE);//CountDown Timer for 3 2 1
                        countdownTextView.setText(Long.toString(millisUntilFinished/1000 + 1));

                    }

                    @Override
                    public void onFinish() {
                        //Setting visibility after timer is over
                        sosImageView.setVisibility(View.VISIBLE);
                        countdownTextView.setVisibility(View.INVISIBLE);
                        distanceTextView.setVisibility(View.VISIBLE);
                        distanceTitleTextView.setVisibility(View.VISIBLE);
                        durationChronometer.setVisibility(View.VISIBLE);
                        durationTitleTextView.setVisibility(View.VISIBLE);
                        speedTextView.setVisibility(View.VISIBLE);
                        avgSpeedTextView.setVisibility(View.VISIBLE);
                        speedTitleTextView.setVisibility(View.VISIBLE);
                        avgSpeedTitleTextView.setVisibility(View.VISIBLE);
                        stopImageView.setVisibility(View.VISIBLE);
                        //*******************************************
                        startDrive();
                        stopImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                stopImageView.setEnabled(false);
                                stopDrive();
                            }
                        });



                    }
                }.start();


                



            }
        });


        return view;
    }
    public void startDrive()
    {

        durationChronometer.setBase(SystemClock.elapsedRealtime());
        Log.i("Drive","Started");
        startPressed=true;
        stopPressed=false;
        running=false;
        if(!running)
        {
            Log.i("Chronometer","Running");
            durationChronometer.setBase(SystemClock.elapsedRealtime()-offset);
            durationChronometer.start();
            running=true;
        }






    }
    public void stopDrive()
    {
        Log.i("Drive","Stopped");
        startPressed=false;
        stopPressed=true;
        if(running)
        {
            Log.i("Chronometer","paused");
            durationChronometer.stop();
            offset=SystemClock.elapsedRealtime() - durationChronometer.getBase();
            running=false;
        }

        Log.i("Time of Drive",Long.toString(offset));
        totalTime = Long.toString(offset);
        offset=0;


    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float x =  event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        acceleration = (float)Math.sqrt((x*x + y*y + z*z));
        Log.i("Accelerometer", Float.toString(acceleration));
        if(acceleration>75)
        {
            Intent intent = new Intent(getActivity(),SMS_Activity.class);
            startActivity(intent);
        }




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

