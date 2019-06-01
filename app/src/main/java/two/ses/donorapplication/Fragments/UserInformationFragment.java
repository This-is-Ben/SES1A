package two.ses.donorapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import two.ses.donorapplication.Model.User;
import two.ses.donorapplication.R;

/**
 * Class: UserInformationFragment
 * Extends: {@link Fragment}
 * Description:
 * <p>
 * This fragment's job will be that to display Users information, and be able to edit that
 * information (either edit it in this fragment or a new fragment, up to you!)
 * <p>
 */
public class UserInformationFragment extends Fragment {
    // Note how Butter Knife also works on Fragments, but here it is a little different
    @BindView(R.id.name_tv)
    TextView nameTV;

    @BindView(R.id.group_tv)
    TextView groupTV;

    @BindView(R.id.email_tv)
    TextView emailTV;

    @BindView(R.id.phone_tv)
    TextView phoneTV;

    @BindView(R.id.address_tv)
    TextView addressTV;

    private FirebaseAuth mAuth;
    private DatabaseReference dr;
    private FirebaseDatabase mFirebaseDatabase;
    private String userId;



    public UserInformationFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle("User Information");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        dr = mFirebaseDatabase.getReference("User").child(mAuth.getCurrentUser().getUid());
        FirebaseUser user = mAuth.getCurrentUser();
        userId = mAuth.getCurrentUser().getUid();

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
<<<<<<< HEAD
                nameTV.setText("Name:" + user.getName());
                emailTV.setText("Email:" + user.getEmail());
                groupTV.setText("Group: " + user.getGroup());
=======
                if(user != null) {
                    nameTV.setText("Name:" + user.getName());
                    emailTV.setText("Email:" + user.getEmail());
                    groupTV.setText("Group: " + user.getGroup());
                }
>>>>>>> dev
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        // .child("User").child(userId).child("group").get

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_information, container, false);

        // Note how we are telling butter knife to bind during the on create view method
        ButterKnife.bind(this, v);

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Now that the view has been created, we can use butter knife functionality
        //blankFragmentTV.setText("Welcome to this fragment");
    }
}