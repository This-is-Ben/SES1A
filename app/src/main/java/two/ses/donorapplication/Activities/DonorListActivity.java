package two.ses.donorapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import two.ses.donorapplication.Model.CharityModel;
import two.ses.donorapplication.R;

public class DonorListActivity extends AppCompatActivity {

    private TextView tv_name, tv_email, tv_contact, tv_address;
    private ImageView iv_back;
    private CharityModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_contact = findViewById(R.id.tv_contact);
        tv_address = findViewById(R.id.tv_address);

        Intent i = getIntent();
        model = (CharityModel) i.getSerializableExtra("desc");

        tv_name.setText("Name : " + model.getName());
        tv_email.setText("Email : " + model.getEmail());
        tv_contact.setText("Contact : " + model.getContact());
        tv_address.setText("Address : " + model.getAddress());

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    @OnClick(R.id.btn_booking)
    public void addBooking(){
        Intent i = new Intent(this, AddBooking.class);
        i.putExtra("Charity", model.getName());
        startActivity(i);
    }
}
