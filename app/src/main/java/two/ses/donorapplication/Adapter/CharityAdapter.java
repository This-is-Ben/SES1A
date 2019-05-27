package two.ses.donorapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import two.ses.donorapplication.Model.CharityModel;
import two.ses.donorapplication.R;

public class CharityAdapter extends RecyclerView.Adapter<CharityAdapter.ViewHolder> {

    private static final String TAG = CharityAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<CharityModel> title;
    int layout;
    OnCategoriesClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        TextView tv_name, tv_reason;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            tv_name = layout.findViewById(R.id.tv_name);
        }
    }

    public CharityAdapter(Context context, int layout, ArrayList<CharityModel> title,OnCategoriesClickListener listener) {
        mContext = context;
        this.layout = layout;
        this.title = title;
        this.listener = listener;
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
        holder.tv_name.setText(title.get(position).getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCategoriesClick(title.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public interface OnCategoriesClickListener {
        void onCategoriesClick(CharityModel model);
    }

}