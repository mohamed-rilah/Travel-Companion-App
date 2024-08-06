package com.example.travelcompanionapp;

import static com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.travelcompanionapp.model.LocationViewModel;
import com.example.travelcompanionapp.placesAPI.SingletonData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.travelcompanionapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationViewModel model;
    public static final String TAG = "MR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Places API Code from provided example on Blackboard
        try {
            ApplicationInfo applicationInfo =
                    getApplication().getPackageManager()
                            .getApplicationInfo(getApplication().getPackageName(), PackageManager.GET_META_DATA);

            String apiKey = applicationInfo.metaData.getString("com.google.android.geo.API_KEY");
            SingletonData.getInstance().setApiKey(apiKey);
            String placesKey = applicationInfo.metaData.getString("PLACES.API_KEY");
            SingletonData.getInstance().setPlacesKey(placesKey);

        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.setLocationFragment, R.id.mapFragment, R.id.placesFragment, R.id.weatherFragment, R.id.itineraryFragment,
                R.id.createItineraryFragment, R.id.selectingNewLocation, R.id.viewItineraryFragment )
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        model = new ViewModelProvider(this).get(LocationViewModel.class);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // To check whether permissions have been granted by the user
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Launching permission dialogue & process result of asking for location permission
            ActivityResultLauncher<String[]> locationPermissionRequest =
                    registerForActivityResult(new ActivityResultContracts
                                    .RequestMultiplePermissions(), result -> {
                                Boolean fineLocationGranted = result.getOrDefault(
                                        android.Manifest.permission.ACCESS_FINE_LOCATION, false);
                                Boolean coarseLocationGranted = result.getOrDefault(
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION, false);
                                if (fineLocationGranted != null && fineLocationGranted) {
                                    Log.i(TAG, "Precise location access granted.");
                                    getLastLocation();
                                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                    Log.i(TAG, "Only approximate location access granted.");
                                    getLastLocation();
                                } else {
                                    Log.i(TAG, "No location access granted.");
                                }
                            }
                    );

            locationPermissionRequest.launch(new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            });

        } else {
            Log.i(TAG, "Location permissions already granted.");
            getLastLocation();
        }
    }

    // Gets the users last known location
    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getCurrentLocation(PRIORITY_BALANCED_POWER_ACCURACY, null)
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            model.setCurrentLocation(location);
                            Log.i(TAG, "We got a location: (" + location.getLatitude() + ", " + location.getLongitude() + ")");
                        } else {
                            Log.i(TAG, "We failed to get a last location");
                        }
                    }
                });
    }

}