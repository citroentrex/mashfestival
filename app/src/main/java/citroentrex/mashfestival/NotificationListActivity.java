package citroentrex.mashfestival;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.EventLog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
//import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class NotificationListActivity extends AppCompatActivity {
    //private FirebaseListAdapter<EventDetail> adapter_;
    private ArrayList<EventDetail> arrayList = new ArrayList<>();
    private DatabaseReference fireBase;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        fireBase = FirebaseDatabase.getInstance().getReference();
        final ArrayAdapter<EventDetail> arrayAdapter = new ArrayAdapter<EventDetail>(this, R.layout.event_detail, arrayList);

        listView = (ListView) findViewById(R.id.list_of_events);
        listView.setAdapter(arrayAdapter);

        fireBase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                EventDetail value = dataSnapshot.getValue(EventDetail.class);
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       // adapter_.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
      //  adapter_.stopListening();
    }

    private void displayEventDetailsList() {
      //  ListView eventDetailsList = (ListView)findViewById(R.id.list_of_events);


        //eventDetailsList.setAdapter(adapter_);
    }
}
