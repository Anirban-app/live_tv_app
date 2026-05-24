package com.example.livetvapp.ui.mobile.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.livetvapp.R;
import com.example.livetvapp.data.model.Channel;
import java.util.ArrayList;
import java.util.List;

public class ChannelCategoryAdapter extends RecyclerView.Adapter<ChannelCategoryAdapter.ChannelViewHolder> {
    public interface OnChannelClickListener {
        void onChannelClicked(Channel channel);
    }

    private final List<Channel> channels = new ArrayList<>();
    private final OnChannelClickListener listener;

    public ChannelCategoryAdapter(OnChannelClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel_card, parent, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        Channel channel = channels.get(position);
        holder.channelName.setText(channel.getName());
        holder.itemView.setOnClickListener(v -> listener.onChannelClicked(channel));
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public void submitList(List<Channel> list) {
        channels.clear();
        if (list != null) {
            channels.addAll(list);
        }
        notifyDataSetChanged();
    }

    static class ChannelViewHolder extends RecyclerView.ViewHolder {
        final TextView channelName;

        ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            channelName = itemView.findViewById(R.id.channelName);
        }
    }
}
