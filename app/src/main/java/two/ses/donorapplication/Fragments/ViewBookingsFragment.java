package two.ses.donorapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.blank_frag_msg)
    TextView blankFragmentTV;


    public ViewBookingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle("Bookings");
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
        blankFragmentTV.setText("Welcome to this fragment");
    }
}
