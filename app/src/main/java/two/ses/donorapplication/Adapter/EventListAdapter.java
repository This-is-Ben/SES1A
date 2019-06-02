package two.ses.donorapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import two.ses.donorapplication.Model.Event;
import two.ses.donorapplication.R;

public class EventListAdapter extends ArrayAdapter<Event> {

    private static final String TAG = "EventListAdapter";

    private Context mContext;
    int mResource;

    public EventListAdapter(Context context, int resource, ArrayList<Event> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String eventID = getItem(position).getEventID();
        String date = getItem(position).getDate();
        String time = getItem(position).getTime();
        String user = getItem(position).getUserID();
        String charity = getItem(position).getCharityID();

        //Create the event object with the information
        Event event = new Event(eventID, date, time, user, charity);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView dateTV = (TextView) convertView.findViewById(R.id.dateTvInLv);
        TextView timeTV = (TextView) convertView.findViewById(R.id.timeTvInLv);
        TextView userTV = (TextView) convertView.findViewById(R.id.userTvInLv);
        TextView charityTV = (TextView) convertView.findViewById(R.id.charityTvInLv);

        dateTV.setText(date);
        timeTV.setText(time);
        userTV.setText(user);
        charityTV.setText(charity);

        return convertView;
    }
}
