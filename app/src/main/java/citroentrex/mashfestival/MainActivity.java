package citroentrex.mashfestival;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.Query;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<EventDetail> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        } else {
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();
            displayEventDetailsList();
        }

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                myRef.push().setValue(new EventDetail("test", 0));
            }
        });

    }


    @Override
    protected  void onActivityResult(int requestCode, int resultCode,
                                     Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(this,
                        resultCode,
                        Toast.LENGTH_LONG)
                        .show();

                // Close the app
                finish();
            }
        } else
        {
            Toast.makeText(this,
                    resultCode,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }

    private void displayEventDetailsList() {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_events);

        Query query = FirebaseDatabase.getInstance().getReference();

        FirebaseListOptions<EventDetail> options = new FirebaseListOptions.Builder<EventDetail>()
                .setQuery(query, EventDetail.class)
                .setLayout(R.layout.event_detail)
                .build();

        adapter = new FirebaseListAdapter<EventDetail>( options ) {
            @Override
            protected void populateView(View v, EventDetail model, int position) {
                // Get references to the views of message.xml
                TextView eventName = (TextView)v.findViewById(R.id.event_name);
                TextView eventTime = (TextView)v.findViewById(R.id.event_time);
                // Set their text
                eventName.setText(model.getEventName());
                eventTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getEventTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }


}
