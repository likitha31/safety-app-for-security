package latrobesafety.mad.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SecurityOverview extends AppCompatActivity {

    Button currentRequests = null;
    Button requestHistory = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_overview);


        currentRequests = findViewById(R.id.securityRequests);
        requestHistory = findViewById(R.id.requestHistory);


        currentRequests.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SecurityOverview.this, SecurityCurrentRequests.class);
                startActivity(intent);

            }
        });

        requestHistory.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SecurityOverview.this, requestHistory.class);
                startActivity(intent);
            }
        });
    }
}
