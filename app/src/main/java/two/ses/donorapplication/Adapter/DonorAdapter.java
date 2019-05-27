package two.ses.donorapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import two.ses.donorapplication.R;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.ViewHolder> {

    private static final String TAG = DonorAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<String> title;
    int layout;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        TextView tv_name, tv_reason;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            tv_name = layout.findViewById(R.id.tv_name);
        }
    }

    public DonorAdapter(Context context, int layout, ArrayList<String> title) {
        mContext = context;
        this.layout = layout;
        this.title = title;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_name.setText(title.get(position));
    }

    @Override
    public int getItemCount() {
        return title.size();
    }
}