package latrobesafety.mad.security;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class requestHistory extends AppCompatActivity {

        private FirebaseFirestore db;
        private List<Request> requestList = new ArrayList<>();
        private static final String TAG = "SecurityCurrentRequests";
        private CollectionReference requestRef;
        private TextView requestView;
        private ImageView exit;
        private TextView name;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_request_history);

            db = FirebaseFirestore.getInstance();
            requestRef = db.collection("Request");

            exit = findViewById(R.id.arrow);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(requestHistory.this,SecurityOverview.class);
                    startActivity(intent);
                }
            });

        }

        @Override
        protected void onStart(){
            super.onStart();
            db.collection("Request").whereEqualTo("status","COMPLETE").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            List<Request> requestsList = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Long priority = (Long) document.get("priority");

                                    if (priority == 1) {
                                        Emergency immediateResp = document.toObject(Emergency.class);
                                        requestsList.add(immediateResp);
                                    } else {
                                        PickUp pickup = document.toObject(PickUp.class);
                                        requestsList.add(pickup);
                                    }
                                }
                                Log.d(TAG, "onComplete: " + requestsList);
                                ListView listView = (ListView) findViewById(R.id.ListView);
                                RequestAdapterListV requestAdapter = new RequestAdapterListV(requestHistory.this, requestsList);
                                listView.setAdapter(requestAdapter);
                            } else {
                                Log.d("CurrentActivity", "Error getting documents: ", task.getException());
                            }
                        }
                    });


        }

    }

