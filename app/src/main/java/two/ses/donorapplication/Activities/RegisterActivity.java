package two.ses.donorapplication.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import two.ses.donorapplication.Model.User;
import two.ses.donorapplication.R;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.nameET)
    EditText nameEditText;
    @BindView(R.id.emailET)
    EditText emailEditText;
    @BindView(R.id.passwordET)
    EditText passwordEditText;
    @BindView(R.id.confirmET)
    EditText confirmEditText;
    @BindView(R.id.rgroup)
    RadioGroup radioGroup;
    @BindView(R.id.categoriesSP)
    Spinner categoriesSpinner;
    @BindView(R.id.addressET)
    EditText addressEditText;
   @BindView(R.id.phoneET)
    EditText phoneEditText;

    private static String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private RadioButton radioButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Sets title
        setTitle(R.string.register_activity_title);

        //Get instance
        mAuth = FirebaseAuth.getInstance();

        // Set drop down menu and setup process dialog
        /*
        categoriesSpinner.setVisibility(View.INVISIBLE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);*/

        //Load options for the drop down menu for selecting categories
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(mAuth.getCurrentUser() != null) {
            // Start a new activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user" ,mAuth.getCurrentUser());
            startActivity(intent);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
                if(radioButton.getText().toString().equals("Charity"))
                    categoriesSpinner.setVisibility(View.VISIBLE);
                else
                    categoriesSpinner.setVisibility(View.INVISIBLE);
            }
        });
    }

    @OnClick(R.id.register_btn)
    public void Register(){
        final String username = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        final String confirm = confirmEditText.getText().toString();
        final String name = nameEditText.getText().toString();
        final String address = addressEditText.getText().toString();
        final Integer phone = Integer.valueOf(phoneEditText.getText().toString());
        //Check which group is selected
        //progressDialog.show();
        radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        final String group = radioButton.getText().toString();
        final String category = categoriesSpinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(username)){
            toastMessage("Enter email address!");
            return;
        }
        //Check password is entered
        if (TextUtils.isEmpty(password)){
            toastMessage("Enter password!");
            return;
        }
        //Check confirm password is entered
        if (TextUtils.isEmpty(confirm)){
            toastMessage("Enter password confirmation!");
            return;
        }
        //Check length
        if (password.length() < 6){
            toastMessage("Password too short, 6 characters minimum!");
            return;
        }
        //Check passwords match
        if (!password.equals(confirm)){
            toastMessage("Passwords don't match!");
            return;
        }


        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            User user = new User(
                                    name,
                                    username,
                                    group,
                                    address,
                                    phone
                            );
                            //Add user to correct group with categories if charity was selected
                            if(group.equals("Donor")){
                                //Add user to list of donors
                                FirebaseDatabase.getInstance().getReference("Group").child("donor")
                                        .child(name).setValue(true);
                            }else if (group.equals("Charity")){
                                //Add user to list of charities
                                FirebaseDatabase.getInstance().getReference("Group").child("charity")
                                        .child(category).child(name).setValue(true);
                            }
                            //Add user to list of users
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        toastMessage("User Registered Successfully");
                                        //progressDialog.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            toastMessage("Authentication failed.");
                        }
                    }
                });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
