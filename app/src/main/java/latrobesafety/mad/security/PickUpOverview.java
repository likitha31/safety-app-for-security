package latrobesafety.mad.security;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class PickUpOverview extends AppCompatActivity {

    private static final String TAG = "FIRESTORE";
    private PickUp request = null;
    private FirebaseFirestore db;
    private Button cancelBtn = null;
    private Button startBtn = null;
    private TextView info = null;
    private TextView time = null;
    private TextView building = null;
    private TextView name = null;
    private TextView message = null;
    TextView status = null;
    private String minute;
    private String hour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_overview);

        request = (PickUp) getIntent().getSerializableExtra("Item");

        db = FirebaseFirestore.getInstance();
        final CollectionReference requestRef = db.collection("Request");


        building = findViewById(R.id.building);
        building.setText("Building   : "+ request.getBuilding());
        name = findViewById(R.id.name);
        name.setText("Name       : "+ request.getName());
        time = findViewById(R.id.time);
        time.setText("Time         : "+ request.getHour() + "." + request.getMinute());
        message = findViewById(R.id.msg);

        status = findViewById(R.id.status);
        status.setText("Status --- " + request.getStatus().toString());

        startBtn = findViewById(R.id.start);

        cancelBtn = findViewById(R.id.end);
        //cancelBtn.setEnabled(false);
        cancelBtn.setBackground(ContextCompat.getDrawable(this,R.drawable.buttondisabled));

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateStatusComplete(requestRef);
                status.setText("Status --- " + request.getStatus().toString());
                //cancelBtn.setEnabled(false);
                cancelBtn.setBackground(ContextCompat.getDrawable(PickUpOverview.this,R.drawable.buttondisabled));

            }
        });

        //Firebase initialization
        db = FirebaseFirestore.getInstance();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatusOnGoing(requestRef);
                status.setText("Status --- " + request.getStatus().toString());
                //startBtn.setEnabled(false);
                cancelBtn.setBackground(ContextCompat.getDrawable(PickUpOverview.this,R.drawable.button));
                //cancelBtn.setEnabled(true);
                startBtn.setBackground(ContextCompat.getDrawable(PickUpOverview.this,R.drawable.buttondisabled));


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
