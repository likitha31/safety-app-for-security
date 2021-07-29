package latrobesafety.mad.security;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SecurityCurrentRequests extends AppCompatActivity {

    private static final String TAG = "SecurityCurrentRequests";
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference requestRef  = db.collection("Request");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_current_requests);


       

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        requestRef.orderBy("priority").orderBy("currentDate").addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                ArrayList<Request> requestsList = new ArrayList<>();
                for (QueryDocumentSnapshot document : documentSnapshot) {

                    Long priority = (Long) document.get("priority");

                    if (priority == 1) {
                        Emergency immediateResp = document.toObject(Emergency.class);
                        requestsList.add(immediateResp);
                    } else {
                        PickUp pickup = document.toObject(PickUp.class);
                        requestsList.add(pickup);
                    }
                }
                requestAdapter adapter = new requestAdapter(requestsList, SecurityCurrentRequests.this);
                recyclerView.setAdapter(adapter);
            }

        });
    }
    }