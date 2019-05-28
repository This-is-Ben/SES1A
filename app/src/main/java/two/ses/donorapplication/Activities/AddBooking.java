package two.ses.donorapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import two.ses.donorapplication.R;

public class AddBooking extends AppCompatActivity {

    private static String date;
    CalendarView calendarView;
    Button confirmDateBtn;

    public static String getDate() {
        return date;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);

        confirmDateBtn = (Button) findViewById(R.id.confirmDateBtn);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

       calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month+=1;
                date = dayOfMonth + "/" + month + "/" + year;
                Log.d("'date' on Day Change:", date);
            }
        });
        confirmDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("'date' on Selection", date);
                Intent intent = new Intent(AddBooking.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
