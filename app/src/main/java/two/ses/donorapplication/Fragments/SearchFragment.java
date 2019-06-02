package two.ses.donorapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import two.ses.donorapplication.Activities.DonorListActivity;
import two.ses.donorapplication.Adapter.CharityAdapter;
import two.ses.donorapplication.Model.CharityModel;
import two.ses.donorapplication.R;

/**
 * Class: UserInformationFragment
 * Extends: {@link Fragment}
 * Description:
 * <p>
 * This fragment's job will be to search for charities and display results
 * <p>
 */
public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private Context context;
    private View view;
    RecyclerView rv_charity;
    EditText input_charity_name;

    ArrayList<CharityModel> charityOriginal, charityAdpModel;
    ArrayList<String> donorIDS;
    DatabaseReference CharityDataref, userRef;
    CharityAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle("Search Donor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        init();
        return view;
    }


    private void init() {
        context = getContext();

        charityOriginal = new ArrayList<>();
        charityAdpModel = new ArrayList<>();
        donorIDS = new ArrayList<>();

        CharityDataref = FirebaseDatabase.getInstance().getReference("Group");
        userRef = FirebaseDatabase.getInstance().getReference("User");
        input_charity_name = view.findViewById(R.id.input_charity_name);
        rv_charity = view.findViewById(R.id.rv_charity);
        rv_charity.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rv_charity.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        input_charity_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setdata();
        getIDS();
    }

    private void filter(String data) {
        charityAdpModel.clear();
        if ("".equals(data.trim())) {
            charityAdpModel.addAll(charityOriginal);
        } else {
            for (CharityModel model : charityOriginal) {
                if (model.getName().toLowerCase().contains(data.toLowerCase())) {
                    charityAdpModel.add(model);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void setdata() {
        adapter = new CharityAdapter(context, R.layout.inflate_charity_list, charityAdpModel, new CharityAdapter.OnCategoriesClickListener() {
            @Override
            public void onCategoriesClick(CharityModel model) {
                Intent i = new Intent(context, DonorListActivity.class);
                i.putExtra("desc", model);
                startActivity(i);
            }
        });
        rv_charity.setAdapter(adapter);
    }


    private void getIDS() {
        CharityDataref.child("donor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.e(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.e(TAG, "onDataChange: " + snapshot.getKey());
                    donorIDS.add(snapshot.getKey());
                    readCharity(snapshot.getKey());
                    Log.e(TAG, "onDataChange: " + donorIDS.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    private void readCharity(String id) {
        Log.e(TAG, "onDataChange read: " + id);
        userRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: " + dataSnapshot.child("name").getValue(String.class) + dataSnapshot.child("email").getValue(String.class) +
                        dataSnapshot.child("phone").getValue(Long.class).toString() + dataSnapshot.child("address").getValue(String.class));
                charityOriginal.add(new CharityModel(dataSnapshot.child("name").getValue(String.class),dataSnapshot.child("email").getValue(String.class),
                        dataSnapshot.child("phone").getValue(Long.class).toString(), dataSnapshot.child("address").getValue(String.class)));
                charityAdpModel.addAll(charityOriginal);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }
}