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
    String charityId = "test";
    private ArrayList<String> eventIDS = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBooked()){
                    String eventId = UUID.randomUUID().toString();
                    String userId;
                    Log.d("Time on click:", selectedTime);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    mAuth = FirebaseAuth.getInstance();
                    userId = mAuth.getCurrentUser().getUid();
                    DatabaseReference eventsRef = database.getReference("Events");
                    DatabaseReference userRef = database.getReference("User");

                    eventsRef.child(eventId).child("Charity ID").setValue(placeHolderCharityId);
                    eventsRef.child(eventId).child("Date").setValue(AddBooking.getDate());
                    eventsRef.child(eventId).child("Time").setValue(selectedTime);
                    eventsRef.child(eventId).child("UserID").setValue(userId);

                    userRef.child(userId).child(eventId).setValue("true");

                    Intent intent = new Intent(SelectTime.this, AddBooking.class);
                    startActivity(intent);
                }
                else{
                    new AlertDialog.Builder(SelectTime.this)
                            .setTitle("Delete entry")
                            .setMessage("This timeslot has already been booked with this charity")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.child("User").child(charityId).child("events").getChildren()){
                    boolean eventID = ds.getValue(boolean.class);
                    if(eventID){
                        eventIDS.add(ds.getKey());
                    }
                }
                for(int i = 0; i < eventIDS.size(); i++){
                    bookedTimes.add(dataSnapshot.child("Event").child(eventIDS.get(i)).child("Time").getValue(String.class));
                    bookedDates.add(dataSnapshot.child("Event").child(eventIDS.get(i)).child("Date").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isBooked(){
        for(int i = 0; i < bookedTimes.size(); i++){
            if(bookedTimes.get(i) == selectedTime && bookedDates.get(i) == AddBooking.getDate()){
                return true;
            }
        }
        return false;
    }
}