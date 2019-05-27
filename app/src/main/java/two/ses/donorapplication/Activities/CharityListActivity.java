package two.ses.donorapplication.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import two.ses.donorapplication.Model.Charity;
import two.ses.donorapplication.R;

public class CharityListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinner;
    private static final String[] categories = { "Name", "Category", "Location" };


    DatabaseReference CharityDataref;
    List<Charity> listofCharities;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charity_search);

        //Spinner spin = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spin.setAdapter(adapter);
        //spin.setOnItemSelectedListener(this);

        //list view
        listofCharities = new ArrayList<>();
        CharityDataref = FirebaseDatabase.getInstance().getReference("Charity");
        listView = (ListView) findViewById(R.id.lvCharities);


    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), "Search by: "+categories[position] ,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }


}