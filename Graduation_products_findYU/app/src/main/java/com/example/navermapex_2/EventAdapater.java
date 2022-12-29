package com.example.navermapex_2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EventAdapater extends RecyclerView.Adapter<EventAdapater.ViewHolder> {
    private Context context;
    private ArrayList<EventItem> eList;
    private LayoutInflater mInflate;


    public EventAdapater(ArrayList<EventItem> eList) {
        this.eList = eList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.from(parent.getContext()).inflate(R.layout.recycler_event_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        this.context = parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(eList.get(position));

        EventItem eventItem = eList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        eventItem.getName() + "\n위도: " + eventItem.getLat() + " 경도: " + eventItem.getLng(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return eList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView eventName, eventDate, eventHall, eventLocation;
        private ImageView eventImg;
        private EventItem item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_name);
            eventDate = itemView.findViewById(R.id.event_date);
            eventHall = itemView.findViewById(R.id.event_sub_location);
            eventImg = itemView.findViewById(R.id.event_img);

        }

        public void onBind(EventItem item) {
            this.item = item;

            eventHall.setText(item.getLocation2());
            eventName.setText(item.getName());
            eventDate.setText(item.getDate());

            // glide
            if (!item.getImage().equals("null")) {
                Glide.with(context)
                        .load(item.getImage())
                        //.override(170, 280)
                        .into(eventImg);
            } else {
                eventImg.setImageResource(R.drawable.cmac_noimg);
            }

        }
    }
}


