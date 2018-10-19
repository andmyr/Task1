package ua.od.and.task1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {
    private List<LogItem> logList;
    private List<LogItem> originalValues;
    private Context context;

    RecyclerViewAdapter(Context context, List<LogItem> logList) {
        this.logList = logList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LogItem item = logList.get(position);

        holder.label.setText(String.format(context.getString(R.string.label), item.getLabel()));
        holder.longitude.setText(String.format(context.getString(R.string.longitude),
                String.format(Locale.ENGLISH, "%.4f", item.getLocation().getLongitude())));
        holder.latitude.setText(String.format(context.getString(R.string.latitude),
                String.format(Locale.ENGLISH, "%.4f", item.getLocation().getLatitude())));
        holder.distance.setText(String.format(context.getString(R.string.from_previous),
                item.getDistanceFromPreviousLocationM(), "m"));
    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<LogItem> filteredList = new ArrayList<>();

                if (originalValues == null
                        || originalValues.size() != logList.size()) {
                    originalValues = new ArrayList<>(logList); // saves the original data in originalValues
                }

                if (constraint == null
                        || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = originalValues.size();
                    results.values = originalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < originalValues.size(); i++) {
                        String label = originalValues.get(i).getLabel();
                        if (label.toLowerCase().contains(constraint.toString())) {
                            filteredList.add(originalValues.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = filteredList.size();
                    results.values = filteredList;
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                logList = (List<LogItem>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }
        };
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView label;
        private TextView longitude;
        private TextView latitude;
        private TextView distance;

        MyViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            longitude = itemView.findViewById(R.id.longitude);
            latitude = itemView.findViewById(R.id.latitude);
            distance = itemView.findViewById(R.id.distance_from_previous);
        }
    }
}