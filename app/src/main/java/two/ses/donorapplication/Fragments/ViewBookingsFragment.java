package two.ses.donorapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import two.ses.donorapplication.EventListAdapter;
import two.ses.donorapplication.Model.Event;
import two.ses.donorapplication.R;

/**
 * Class: ViewBookingsFragment
 * Extends: {@link Fragment}
 * Description:
 * <p>
 * This fragment's job will be that to display bookings, and be able to edit that
 * information (either edit it in this fragment or a new fragment, up to you!)
 * <p>

 */
public class ViewBookingsFragment extends Fragment {
    // Note how Butter Knife also works on Fragments, but here it is a little different
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.delete)
    Button deleteBtn;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference uEventsRef,eventsRef;
    private FirebaseAuth mAuth;
    private ArrayList<Event> events = new ArrayList<Event>();
    private ArrayList<String> eventIDS = new ArrayList<String>();
    private int index;

    public ViewBookingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle("View Bookings");
        //Get database instances
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uEventsRef = mFirebaseDatabase.getReference("User").child(mAuth.getCurrentUser().getUid()).child("events");
        eventsRef = mFirebaseDatabase.getReference("Events");
        if(eventIDS.size() > 0) {
            events.clear();
            eventIDS.clear();
        }
        getEventID();
        getEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_bookings, container, false);

        // Note how we are telling butter knife to bind during the on create view method
        ButterKnife.bind(this, v);

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Now that the view has been created, we can use butter knife functionality
        deleteBtn.setEnabled(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                deleteBtn.setEnabled(true);
                index = position;
            }
        });
    }

    private void getEventID() {
        uEventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Boolean eventID = ds.getValue(Boolean.class);
                    if (eventID) {
                        eventIDS.add(ds.getKey());
                    }
                }
                for (int i =0; i < eventIDS.size(); i++){
                    Log.i("Events are: ", eventIDS.get(i));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getEvents() {
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    if (eventIDS.contains(ds.getKey())) {
                        Log.i("Events are:", ds.getKey() + ds.getValue());
                        Event event = new Event(ds.getKey(),
                                ds.child("Date").getValue(String.class),
                                ds.child("Time").getValue(String.class),
                                ds.child("Donor Name").getValue(String.class),
                                ds.child("Charity Name").getValue(String.class));
                        events.add(event);

                        String record = "Date: " + event.getDate() + "\n"
                                + "Time: " + event.getTime() + "\n"
                                + "User: " + event.getUserID() + "\n"
                                + "Charity: " + event.getCharityID();

                        Log.d("TAG", "Value is: " + record);
                }
                }
                EventListAdapter adapter = new EventListAdapter(getContext(), R.layout.listview_event_data, events);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @OnClick(R.id.delete)
    public void deleteEvent() {
        uEventsRef.child(eventIDS.get(index)).setValue(false);
        events.remove(index);
        eventIDS.remove(index);
        EventListAdapter adapter = new EventListAdapter(getContext(), R.layout.listview_event_data, events);
        listView.setAdapter(adapter);
    }
}
