package gr11.compumovil.udea.edu.co.ingencol;

import android.Manifest;
import android.app.Notification;
import android.content.ContentValues;
import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    HelpDB helpdb;
    Button btnSgte;
    LugaresHistoricos lgHist = new LugaresHistoricos();

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

        ContentValues valores0 = new ContentValues();
        valores0.put(LugaresHistoricos.LugarHistorico.COLUMN_LUGAR_ID, "2");
        valores0.put(LugaresHistoricos.LugarHistorico.COLUMN_NAME, "Las Murallas De Cartagena de Índias");
        valores0.put(LugaresHistoricos.LugarHistorico.COLUMN_DESCRIPTION, "Las murallas fueron concebidas con el fin de proteger a " +
                "Cartagena de Indias de los continuos ataques piratas que sufría. Su construcción se llevó a cabo en etapas, " +
                "comenzando en 1586 Bautista Antonelli, un ingeniero italiano al servicio de la Corona Española. Y se terminó de " +
                "construir dos siglos más tarde. La altura media de ella oscila entre los 6 y los 8 m y está toda construida en roca " +
                "Coralina, propia de la zona. Hoy en día la muralla rodea sólo parcialmente la ciudad vieja, ya que una parte de ésta " +
                "fue derruida por iniciativa de un edil. El estado de lo que queda es bastante aceptable, aunque se hicieron unos " +
                "puentes para que pudieran transitar los autos entre el \"Corralito de Piedra\" y el resto de Cartagena. \n");
        valores0.put(LugaresHistoricos.LugarHistorico.COLUMN_IMAGE, "murallas.jpg");
        valores0.put(LugaresHistoricos.LugarHistorico.COLUMN_COORDENADA_X, "10.4296098");
        valores0.put(LugaresHistoricos.LugarHistorico.COLUMN_COORDENADA_Y, "-75.5470537,17");
        valores0.put(LugaresHistoricos.LugarHistorico.COLUMN_PERSONAJE, "Bautista Antonelli");
        valores0.put(LugaresHistoricos.LugarHistorico.COLUMN_PERS_DESCR, "Fue un ingeniero militar italiano. Nació en Gatteo en 1547 y " +
                "murió en Madrid en 1616. Diseñó y construyó el Fuerte de San Lorenzo, en la desembocadura del Río Chagres, en el Istmo " +
                "de Panamá. La obra se inició en 1598 por orden del Rey Felipe II y se terminó en 1601.\n Bajo el mando del gobernador " +
                "Joan de Texeda, construyó las fortalezas de Los Tres Reyes del Morro y San Salvador de la Punta enLa Habana. Su trabajo" +
                " fue decisivo en la terminación del primer acueducto de La Habana, la Zanja Real.\n Inició la construcción de las " +
                "murallas y fortificaciones de Cartagena de Indias, como el baluarte de Santo Domingo en 1614 entre otras.\n");
        valores0.put(LugaresHistoricos.LugarHistorico.COLUMN_PERS_IMAGE, "bautistaantonelli.jpg");

        // Insert the new row, returning the primary key value of the new row
        Long lugarId0;
        lugarId0 = db.insert(LugaresHistoricos.LugarHistorico.TABLE_NAME,null,valores0);

        ContentValues valores1 = new ContentValues();
        valores1.put(LugaresHistoricos.LugarHistorico.COLUMN_LUGAR_ID, "3");
        valores1.put(LugaresHistoricos.LugarHistorico.COLUMN_NAME, "Puente de occidente");
        valores1.put(LugaresHistoricos.LugarHistorico.COLUMN_DESCRIPTION, "Situado a pocos kilómetros del pueblo de Santa Fe de Antioquia es uno de los puentes colgantes más grandes del mundo.\n" +
                "Patrimonio histórico colombiano, este puente sobre el río Cauca es una de las obras de ingeniería más notables construidas en el siglo XIX.\n" +
                "El puente está soportado por cuatro torres piramidales, dos a cada lado del río, que soportan los cuatro cables de los cuales están suspendidas las péndolas (4 por cada viga) que sostienen el tablero del puente. Los cables están anclados a estructuras en mampostería de ladrillo ubicadas a cada lado de la ribera del Río Cauca. El suelo del puente es de madera.\n");
        valores1.put(LugaresHistoricos.LugarHistorico.COLUMN_IMAGE, "PuenteOcc.jpg");
        valores1.put(LugaresHistoricos.LugarHistorico.COLUMN_COORDENADA_X, "6.57811");
        valores1.put(LugaresHistoricos.LugarHistorico.COLUMN_COORDENADA_Y, "-75.7992487,17");
        valores1.put(LugaresHistoricos.LugarHistorico.COLUMN_PERSONAJE, "José María Villa");
        valores1.put(LugaresHistoricos.LugarHistorico.COLUMN_PERS_DESCR, "José María Villa Villa (Sopetrán, Antioquia, 22 de octubre de1850-Medellín, Antioquia, 3 de diciembre de 1913) fue uningeniero y matemático colombiano que construyó puentes colgantes en varios sitios de Colombia y contribuyó notablemente al desarrollo del país\n");
        valores1.put(LugaresHistoricos.LugarHistorico.COLUMN_PERS_IMAGE, "josemariavilla.jpg");

        // Insert the new row, returning the primary key value of the new row
        Long lugarId1;
        lugarId1 = db.insert(LugaresHistoricos.LugarHistorico.TABLE_NAME,null,valores1);
        //btnSgte = (Button) findViewById(R.id.btnSgte);

//        btnSgte.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MapsActivity.this, LugaresActivity.class);
//                startActivity(intent);
//            }
//        });
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

        final Cursor c = dbr.query(
                LugaresHistoricos.LugarHistorico.TABLE_NAME, projection, null, null, null, null, null
        );

        c.moveToFirst();
        final LatLng coordenadas = new LatLng(c.getDouble(3), c.getDouble(4));
        mMap.addMarker(new MarkerOptions().position(coordenadas).title(c.getString(0)).snippet(c.getString(1)));

        c.move(1);
        final LatLng coordenadas0 = new LatLng(c.getDouble(3), c.getDouble(4));
        mMap.addMarker(new MarkerOptions().position(coordenadas0).title(c.getString(0)).snippet(c.getString(1)));

        c.move(1);
        final LatLng coordenadas1 = new LatLng(c.getDouble(3), c.getDouble(4));
        mMap.addMarker(new MarkerOptions().position(coordenadas1).title(c.getString(0)).snippet(c.getString(1)));
        // Add a marker in Medellin and move the camera 6.245052,-75.6311681,12
        //LatLng medellin = new LatLng(6.245052,-75.6311681)
        //mMap.addMarker(new MarkerOptions().position(medellin).title("Marker in Medellin" + c.getString(0)).snippet(c.getString(1)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordenadas0));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                LatLng latLon = marker.getPosition();
                String titulo = marker.getTitle();
                //Cycle through places array

                Toast.makeText(getApplicationContext(),"Estoy haciendo clic", Toast.LENGTH_LONG);

                if (titulo.equals("Santuario de Monserrate")){
                    Toast.makeText(getApplicationContext(),"Este es Monserrate", Toast.LENGTH_LONG);
                } else if (titulo.equals("Las Murallas De Cartagena de Índias")) {
                    Toast.makeText(getApplicationContext(),"Este es Cartagena", Toast.LENGTH_LONG);
                }


            }
        });
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

            mMap.addMarker(new MarkerOptions().position(myLocation).title("Aqui estamos en este momento"));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
