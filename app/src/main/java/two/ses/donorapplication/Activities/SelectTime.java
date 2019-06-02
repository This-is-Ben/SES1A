package two.ses.donorapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import two.ses.donorapplication.Model.User;
import two.ses.donorapplication.R;

public class SelectTime extends AppCompatActivity {

    Spinner timeSpinner;
    String selectedTime;
    Button selectTimeBtn;
    private FirebaseAuth mAuth;
    String placeHolderCharityId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference usersRef = database.getReference("User");
    private ArrayList<String> bookedTimes = new ArrayList<String>();
    private ArrayList<String> bookedDates = new ArrayList<String>();
    private ArrayList<String> bookedNames = new ArrayList<String>();
    String charityId = "test";
    private ArrayList<String> eventIDS = new ArrayList<String>();
    private String charityName, date, donorName;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        charityName = intent.getStringExtra("Charity");
        date = intent.getStringExtra("Date");
        setContentView(R.layout.activity_select_time);
        final Spinner timeSpinner = findViewById(R.id.timeSpinner);
        final String[] times = new String[]{"9:00am", "10:00am", "11:00am", "12:00pm", "1:00pm", "2:00pm", "3:00pm", "4:00pm", "5:00pm"};
        super.onCreate(savedInstanceState);
        selectTimeBtn = (Button) findViewById(R.id.selectTimeBtn);
        ArrayAdapter<String> adaptor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, times);
        timeSpinner.setAdapter(adaptor);

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = times[position];
                Log.d("selected_Time Variable:", selectedTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAuth = FirebaseAuth.getInstance();
                String userID = mAuth.getCurrentUser().getUid();
                mUser = dataSnapshot.child(userID).getValue(User.class);
                for(DataSnapshot ds : dataSnapshot.child("User").child(userID).child("events").getChildren()){
                    boolean eventID = ds.getValue(boolean.class);
                    if(eventID){
                        eventIDS.add(ds.getKey());
                    }
                }
                for(int i = 0; i < eventIDS.size(); i++){
                    bookedTimes.add(dataSnapshot.child("Events").child(eventIDS.get(i)).child("Time").getValue(String.class));
                    bookedDates.add(dataSnapshot.child("Events").child(eventIDS.get(i)).child("Date").getValue(String.class));
                    bookedNames.add(dataSnapshot.child("Events").child(eventIDS.get(i)).child("Charity Name").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBooked()){
                    String eventId = UUID.randomUUID().toString();
                    String userId;
                    donorName = mUser.getName();
                    Log.d("Time on click:", selectedTime);
                    Log.d("Donor Name:", donorName);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    mAuth = FirebaseAuth.getInstance();
                    userId = mAuth.getCurrentUser().getUid();
                    DatabaseReference eventsRef = database.getReference("Events");
                    DatabaseReference userRef = database.getReference("User");
                    eventsRef.child(eventId).child("Charity ID").setValue(placeHolderCharityId);
                    eventsRef.child(eventId).child("Date").setValue(date);
                    eventsRef.child(eventId).child("Time").setValue(selectedTime);
                    eventsRef.child(eventId).child("UserID").setValue(userId);
                    eventsRef.child(eventId).child("Charity Name").setValue(charityName);
                    eventsRef.child(eventId).child("Donor Name").setValue(donorName);
                    userRef.child(userId).child("events").child(eventId).setValue(true);

                    Intent intent = new Intent(SelectTime.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    new AlertDialog.Builder(SelectTime.this)
                            .setTitle("Apologies")
                            .setMessage("This timeslot has already been booked with this charity")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })

                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
    }

    private boolean isBooked(){
        for(int i = 0; i < bookedTimes.size(); i++){
            if(bookedTimes.get(i).equals(selectedTime) && bookedDates.get(i).equals(date) && bookedNames.get(i).equals(charityName)){
                return true;
            }
        }
        return false;
    }
}