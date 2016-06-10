package gr11.compumovil.udea.edu.co.ingencol;

import android.Manifest;
import android.app.Notification;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    HelpDB helpdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }

        helpdb = new HelpDB(getApplicationContext());
        SQLiteDatabase db = helpdb.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(LugaresHistoricos.LugarHistorico.COLUMN_LUGAR_ID, "1");
        valores.put(LugaresHistoricos.LugarHistorico.COLUMN_NAME, "Santuario de Monserrate");
        valores.put(LugaresHistoricos.LugarHistorico.COLUMN_DESCRIPTION, "El cerro de Monserrate forma parte del cordón montañoso " +
                "que bordea el costado oriental de la Sabana de Bogotá, en la cordillera oriental de los Andes. Se encuentra a una " +
                "altitud de 3200 metros sobre el nivel del mar, y se puede acceder tanto en teleférico como en funicular. También se " +
                "puede subir a pie, peregrinando mientras se van rezando las estaciones del Viacrucis, representadas con esculturas.");
        valores.put(LugaresHistoricos.LugarHistorico.COLUMN_IMAGE, "CM.jpg");
        valores.put(LugaresHistoricos.LugarHistorico.COLUMN_COORDENADA_X, "4.6053536");
        valores.put(LugaresHistoricos.LugarHistorico.COLUMN_COORDENADA_Y, "-74.0555891,21");
        valores.put(LugaresHistoricos.LugarHistorico.COLUMN_PERSONAJE, "Pedro Lugo de Albarracín");
        valores.put(LugaresHistoricos.LugarHistorico.COLUMN_PERS_DESCR, "Fué un importante escultor y toreuta colombiano del siglo XVII," +
                " que se dedicó a la creación de imágenes devocionales de la Pasión de Cristo, de las cuales la más célebre es la imagen" +
                " del Cristo Caído, mejor conocida como el Señor de Monserrate en el epónimo santuario del cerro bogotano.");
        valores.put(LugaresHistoricos.LugarHistorico.COLUMN_PERS_IMAGE, "pedrolugo.jpg");

        // Insert the new row, returning the primary key value of the new row
        Long lugarId;
        lugarId = db.insert(LugaresHistoricos.LugarHistorico.TABLE_NAME,null,valores);
        Toast.makeText(getApplicationContext(), "Se guardó el lugar: " + lugarId, Toast.LENGTH_LONG).show();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //mMap.setMyLocationEnabled(true);
            return;
        } else {
            //Mostrar error
        }

        SQLiteDatabase dbr = helpdb.getReadableDatabase();
        String[] argsel = {"1"};
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                LugaresHistoricos.LugarHistorico.COLUMN_NAME,
                LugaresHistoricos.LugarHistorico.COLUMN_DESCRIPTION,
                LugaresHistoricos.LugarHistorico.COLUMN_IMAGE,
                LugaresHistoricos.LugarHistorico.COLUMN_COORDENADA_X,
                LugaresHistoricos.LugarHistorico.COLUMN_COORDENADA_Y,
                LugaresHistoricos.LugarHistorico.COLUMN_PERSONAJE,
                LugaresHistoricos.LugarHistorico.COLUMN_PERS_DESCR,
                LugaresHistoricos.LugarHistorico.COLUMN_PERS_IMAGE

        };

        Cursor c = dbr.query(
                LugaresHistoricos.LugarHistorico.TABLE_NAME, projection,                               // The columns to return
                LugaresHistoricos.LugarHistorico.COLUMN_LUGAR_ID+"=?", argsel, null, null, null
        );

        c.moveToFirst();

        // Add a marker in Medellin and move the camera 6.245052,-75.6311681,12
        LatLng medellin = new LatLng(6.245052,-75.6311681);
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(medellin).title("Marker in Medellin" + c.getString(0)).snippet(c.getString(1)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(medellin));
    }

    protected void onStart(){
        mGoogleApiClient.connect();
        super.onStart();
        Action viewAction = Action.newAction(Action.TYPE_VIEW, "Maps Page", Uri.parse("http://host/path"),
                Uri.parse("android-app://gr11.compumovil.udea.edu.co.ingencol/http/host/path"));
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);

    }

    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
        Action viewAction = Action.newAction(Action.TYPE_VIEW, "Maps Page", Uri.parse("http://host/path"),
                Uri.parse("android-app://gr11.compumovil.udea.edu.co.ingencol/http/host/path"));
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            return;
        }

        Location mLastLocation  = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null){
            //Llevando mi posición actual al mapa
            LatLng myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            //Colocando la camara en mi posicion
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
            mMap.addMarker(new MarkerOptions().position(myLocation).title("Aqui estoy"));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
