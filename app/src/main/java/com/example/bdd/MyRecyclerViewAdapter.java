package com.example.bdd;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataContainer> {
    private Context context;
    private final List<Planete> data;

    public MyRecyclerViewAdapter(Context context, List<Planete> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public DataContainer onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new DataContainer(view);
    }

    @Override
    public void onBindViewHolder(DataContainer container, int position) {
        container.name.post(() -> {
            String name = context.getString(R.string.name) + " : " + data.get(position).getNom();
            container.name.setText(name);
        });
        container.size.post(() -> {
            String size = context.getString(R.string.size) + " : " + data.get(position).getTaille() + "km";
            container.size.setText(size);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class DataContainer extends RecyclerView.ViewHolder {
        TextView name;
        TextView size;

        public DataContainer(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            size = (TextView) itemView.findViewById(R.id.size);
        }
    }
}




