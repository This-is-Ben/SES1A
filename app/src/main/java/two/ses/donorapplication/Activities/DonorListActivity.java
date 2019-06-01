package two.ses.donorapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import two.ses.donorapplication.Adapter.CharityAdapter;
import two.ses.donorapplication.Adapter.DonorAdapter;
import two.ses.donorapplication.Model.CharityModel;
import two.ses.donorapplication.R;

public class DonorListActivity extends AppCompatActivity {

    private static final String TAG = DonorListActivity.class.getSimpleName();
    Context context = DonorListActivity.this;
    private ImageView iv_back;
    private RecyclerView rv_donor;

    ArrayList<String> arrayList;
    private DonorAdapter donorAdapter;

    String charity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);
        init();
    }

    private void init() {
        Intent i = getIntent();
        charity = i.getStringExtra("charity");
        iv_back = findViewById(R.id.iv_back);
        rv_donor = findViewById(R.id.rv_donor);
        rv_donor.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rv_donor.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        setdata();
        readCharity();
    }

    private void setdata() {
        arrayList = new ArrayList<>();
        donorAdapter = new DonorAdapter(context, R.layout.inflate_charity_list, arrayList);
        rv_donor.setAdapter(donorAdapter);
    }

    private void readCharity() {
        FirebaseDatabase.getInstance().getReference("Group").child("Charity").child(charity).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.e(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());
                Map<String, String> map;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Log.e(TAG, "onDataChange: " + snapshot.getValue());
                    arrayList.add(snapshot.getKey());
                }
                donorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }
}
