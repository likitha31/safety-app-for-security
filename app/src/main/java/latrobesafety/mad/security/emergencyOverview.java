package latrobesafety.mad.security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class emergencyOverview extends AppCompatActivity {

    private static final String TAG = "FIRESTORE";
    String lat,lon;
    Emergency request;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button location;
    TextView name;
    TextView message;
    Button startBtn;
    Button endBtn;
    TextView status = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_overview);
        request = (Emergency) getIntent().getSerializableExtra("Item");

        location = findViewById(R.id.location);
        name = findViewById(R.id.name);
        name.setText("Name       : "+ request.getName());
        message = findViewById(R.id.msg);
        message.setText(request.getMessage());
        status = findViewById(R.id.status);
        status.setText("Status --- " + request.getStatus().toString());
        startBtn = findViewById(R.id.start);
        endBtn = findViewById(R.id.end);
        endBtn.setBackground(ContextCompat.getDrawable(this,R.drawable.buttondisabled));


        final CollectionReference requestRef = db.collection("Request");

        lat = request.getLat();
        lon = request.getLon();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatusOnGoing(requestRef);
                status.setText("Status --- " + request.getStatus().toString());
                endBtn.setBackground(ContextCompat.getDrawable(emergencyOverview.this,R.drawable.button));
                startBtn.setBackground(ContextCompat.getDrawable(emergencyOverview.this,R.drawable.buttondisabled));
                

            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateStatusComplete(requestRef);
                status.setText("Status --- " + request.getStatus().toString());
                endBtn.setBackground(ContextCompat.getDrawable(emergencyOverview.this,R.drawable.buttondisabled));
                //startBtn.setEnabled(false);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(emergencyOverview.this, MapsActivity.class);
                intent.putExtra("LAT",lat);
                intent.putExtra("LONG",lon);
                intent.putExtra("ITEM",request);
                startActivity(intent);
            }
        });
    }

    public void updateStatusOnGoing(CollectionReference requestRef){
        requestRef.whereEqualTo("refNum", request.getRefNum()).get()
        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    DocumentReference documentReference = db.collection("Request").document(document.getId());
                    documentReference.update("status" , "ON_GOING");
                }
            }
        });


    }
    public void updateStatusComplete(CollectionReference requestRef){

        requestRef.whereEqualTo("refNum", request.getRefNum()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference documentReference = db.collection("Request").document(document.getId());
                        documentReference.update("status" , "COMPLETE");
                        Log.d(TAG, document.getId() + " => " + document.getData());

                    }

                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    }

