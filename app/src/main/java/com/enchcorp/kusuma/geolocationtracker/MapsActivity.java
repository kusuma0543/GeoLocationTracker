package com.enchcorp.kusuma.geolocationtracker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    private TrackGps gps;
    int PERMISSION_ALL = 1;
    Double lat,lng;
    ListView list29;
    private Button picka;
int PLACE_PICKER_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        list29=(ListView)findViewById(R.id.listview);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
picka=(Button) findViewById(R.id.pick);
picka.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
        Intent inkl;
        try
        {
            inkl=builder.build(MapsActivity.this);
            startActivityForResult(inkl,PLACE_PICKER_REQUEST);

        }catch (GooglePlayServicesNotAvailableException e)
        {e.printStackTrace();

        }catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }


    }
});



            String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .build();

        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                            mMap.clear();
                LatLng sydneys = place.getLatLng();
                mMap.addMarker(new MarkerOptions()
                        .position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ourloc
                        )));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydneys,6.5f));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
                mMap.setMaxZoomPreference(15.5f);
                mMap.setMinZoomPreference(6.5f);

            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(),"An error occurred: " + status,Toast.LENGTH_SHORT).show();

            }
        });

    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==PLACE_PICKER_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {
                Place placem=PlacePicker.getPlace(data,this);
                String address=String.format("Place:%s",placem.getAddress());
                mMap.clear();
                LatLng sydneys = placem.getLatLng();
                mMap.addMarker(new MarkerOptions()
                        .position(placem.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ourloc
                        )));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydneys,6.5f));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
                mMap.setMaxZoomPreference(15.5f);
                mMap.setMinZoomPreference(6.5f);

            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

//    public class MovieAdap extends ArrayAdapter {
//
//        private List<GeoLocate> movieModelList;
//        private int resource;
//        Context context;
//        private LayoutInflater inflater;
//        MovieAdap(Context context, int resource, List<GeoLocate> objects) {
//            super(context, resource, objects);
//            movieModelList = objects;
//            this.context =context;
//            this.resource = resource;
//            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        }
//        @Override
//        public int getViewTypeCount() {
//            return 1;
//        }
//        @Override
//        public int getItemViewType(int position) {
//            return position;
//        }
//        @Override
//        public View getView(final int position, View view, ViewGroup parent) {
//            final ViewHolder holder;
//            if(view == null){
//                view = inflater.inflate(resource,null);
//                holder = new ViewHolder();
//                holder.textid=(TextView) view.findViewById(R.id.textView);
//                holder.textname=(TextView) view.findViewById(R.id.textView2);
//                view.setTag(holder);
//            }
//            else {
//                holder = (ViewHolder) view.getTag();
//            }
//            GeoLocate ccitac=movieModelList.get(position);
//            holder.textid.setText(ccitac.getGlati());
//            holder.textname.setText(ccitac.getGlongi());
//            lat=Double.parseDouble(ccitac.getGlati());
//            lng=Double.parseDouble(ccitac.getGlongi());
//
//            mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
//                    .title(ccitac.getGname())
//                    .icon(BitmapDescriptorFactory
//                            .fromResource(R.drawable.destloc)));
//
//
//
//
//
//
//          //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),6.5f));
//
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
//            mMap.setMaxZoomPreference(14.5f);
//            mMap.setMinZoomPreference(6.5f);
//
//
//
//            // TrackGPS location;
//
//            mMap.getUiSettings().setZoomControlsEnabled(true);
//            return view;
//        }
//        class ViewHolder{
//            public TextView textid,textname;
//        }
//    }
//    public class kilomilo extends AsyncTask<String,String, List<GeoLocate>> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//        @Override
//        protected List<GeoLocate> doInBackground(String... params) {
//            HttpURLConnection connection = null;
//            BufferedReader reader = null;
//            try {
//                URL url = new URL(params[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                InputStream stream = connection.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(stream));
//                StringBuilder buffer = new StringBuilder();
//                String line = "";
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line);
//                }
//                String finalJson = buffer.toString();
//                JSONObject parentObject = new JSONObject(finalJson);
//                JSONArray parentArray = parentObject.getJSONArray("result");
//                List<GeoLocate> milokilo = new ArrayList<>();
//                Gson gson = new Gson();
//                for (int i = 0; i < parentArray.length(); i++) {
//                    JSONObject finalObject = parentArray.getJSONObject(i);
//                    GeoLocate catego = gson.fromJson(finalObject.toString(), GeoLocate.class);
//                    milokilo.add(catego);
//                }
//                return milokilo;
//            } catch (JSONException | IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//                try {
//                    if (reader != null) {
//                        reader.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(final List<GeoLocate> movieMode) {
//            super.onPostExecute(movieMode);
//            if (movieMode!=null)
//            {
//                MovieAdap adapter = new MovieAdap(getApplicationContext(), R.layout.listsn, movieMode);
//                list29.setAdapter(adapter);
//
//
//            }
//            else
//            {
//                Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        gps = new TrackGps(MapsActivity.this);

        Double lat = gps.getLatitude();
        Double lng=gps.getLongitude();
        LatLng sydney = new LatLng(lat,lng);
        float zoomLevel =10;
      //  new kilomilo().execute(global_url.URLGEO+"?glati="+lat+"&glongi="+lng);

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        List<Address> addresses  = null;
        try {
            addresses = geocoder.getFromLocation(lat,lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ourloc
        )));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,6.5f));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
        mMap.setMaxZoomPreference(15.5f);
        mMap.setMinZoomPreference(6.5f);


        mMap.addCircle(new CircleOptions()
                .center(new LatLng(lat,lng))
                .radius(1000)
             .fillColor(Color.argb(20, 255, 0, 255))
                .strokeColor(Color.BLUE)
                .strokeWidth(2.0f));


    }



    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}