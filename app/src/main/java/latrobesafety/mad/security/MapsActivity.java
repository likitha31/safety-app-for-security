package latrobesafety.mad.security;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Emergency request;
    double lat = 0, lon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        request = (Emergency) getIntent().getSerializableExtra("ITEM");

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



        String latt = getIntent().getStringExtra("LAT");
        String longg = getIntent().getStringExtra("LONG");
        lat = Double.parseDouble(latt);
        lon = Double.parseDouble(longg);

        // Add a marker and move the camera
        LatLng user = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(user).title("User Position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 19));
    }

    @Override
    public void onResume(){
        super.onResume();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference requestRef  = db.collection("Request");
        assert request != null;
        requestRef.whereEqualTo("refNum", request.getRefNum()).addSnapshotListener(new EventListener<QuerySnapshot>(){

            @Override
            public void onEvent(QuerySnapshot snapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("query", "Listen failed.", e);
                    return;
                }
                for(QueryDocumentSnapshot doc : snapshot) {
                    String latt = (String) doc.get("lat");
                    String longg = (String) doc.get("lon");
                    lat = Double.parseDouble(latt);
                    lon = Double.parseDouble(longg);
                }

                LatLng user = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(user).title("User Position"));

            }
        });
    }


}
