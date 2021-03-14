package com.example.geolocalizacao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.TextViewCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private  static final String[] PERMISSOES = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int REQUEST_CODE = 1;

    private  GoogleApiClient googleApiClient;
    private Location ultimaLocalizacao;
    private AppCompatTextView tv_latitude;
    private AppCompatTextView tv_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int read = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(read == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, PERMISSOES, REQUEST_CODE);
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tv_latitude = findViewById(R.id.tv_latitude);
        this.tv_longitude = findViewById(R.id.tv_longitude);
        
        if(googleApiClient == null) {
            googleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        int read = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        ultimaLocalizacao = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (ultimaLocalizacao != null) {
            String latitude = String.valueOf(ultimaLocalizacao.getLatitude());
            String longitude = String.valueOf(ultimaLocalizacao.getLongitude());

            this.tv_latitude.setText(latitude);
            this.tv_longitude.setText(longitude);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();

    }
}