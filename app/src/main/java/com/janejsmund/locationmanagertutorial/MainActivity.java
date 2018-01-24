package com.janejsmund.locationmanagertutorial;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    TextView address, location;
    Button getLocation;
    LocationTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        address = findViewById(R.id.address);
        location = findViewById(R.id.location);
        getLocation = findViewById(R.id.getlocation);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tracker = new LocationTracker(MainActivity.this);

                if(tracker.isLocationEnabled) {

                    double latitude = tracker.getLatitude();
                    double longitude = tracker.getLongitude();

                    location.setText("Your location is Latitude = " + latitude + " Longitude = " + longitude);

                    String strAddress = getCompleteAddressString(latitude, longitude);
                    address.setText(strAddress);
                }
                else {
                    tracker.askToOnLocation();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {

        String strAddress = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> adresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (adresses != null) {

                android.location.Address returnedAddress = adresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {

                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).toString();

                    Log.w(" location address", "No Address returned!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(" location address", "Cannot get address!");
        }
        return strAddress;
    }
}
