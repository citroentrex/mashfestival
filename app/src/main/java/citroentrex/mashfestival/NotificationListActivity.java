package citroentrex.mashfestival;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
//import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class NotificationListActivity extends AppCompatActivity {
    private FirebaseListAdapter<EventDetail> adapter_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            displayEventDetailsList();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter_.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter_.stopListening();
    }
    Query query = FirebaseDatabase.getInstance().getReference();


    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    FirebaseListOptions<EventDetail> options = new FirebaseListOptions.Builder<EventDetail>()
            .setQuery(query, EventDetail.class)
            .setLayout(R.layout.event_detail)
            .build();
    private void displayEventDetailsList() {
        ListView eventDetailsList = (ListView)findViewById(R.id.list_of_events);
        adapter_ = new FirebaseListAdapter<EventDetail>(options) {
            @Override
            protected void populateView(View v, EventDetail model, int position) {
                TextView eventName = (TextView)v.findViewById(R.id.event_name);
                TextView eventTime = (TextView)v.findViewById(R.id.event_time);
                eventName.setText(model.getEventName());
                eventTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getEventTime()));
            }
        };

        eventDetailsList.setAdapter(adapter_);
    }
}
