package com.example.mazunina.topanimelist.presentation.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mazunina.topanimelist.R;
import com.example.mazunina.topanimelist.domain.model.ContentModel;
import com.example.mazunina.topanimelist.presentation.ui.adapter.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TopContentListAdapter extends RecyclerView.Adapter<TopContentListAdapter.ItemViewHolder> {
    private final List<ContentModel> data = new ArrayList<>();
    private final Context context;

    private TopContentListAdapter.Callbacks callbacks;

    private final OnItemClickListener listener = (View view, int position, long id) -> {
        if (callbacks != null && position != RecyclerView.NO_POSITION) {
            ContentModel model = data.get(position);
            callbacks.onItemClick(model);
        }
    };

    public TopContentListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_content, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ContentModel model = data.get(position);

        holder.titleView.setText(model.getTitle());
        if (model.getEpisodes() == 0) {
            holder.releasesView.setText(String.format(Locale.getDefault(), "%s: %d, ", context.getString(R.string.volumes), model.getVolumes()));
        } else {
            holder.releasesView.setText(String.format(Locale.getDefault(), "%s: %d, ", context.getString(R.string.episodes), model.getEpisodes()));
        }
        holder.typeView.setText(model.getType().name());
        holder.periodView.setText(String.format("%s - %s", model.getStartDate(), model.getEndDate() == null ?
                "..." : model.getEndDate()));
        holder.scoreView.setText(String.valueOf(model.getScore()));

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_panorama_black_24dp);
        Glide.with(context)
                .load(model.getImage())
                .apply(options)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ContentModel> data) {
        final TopContentListDiffCallback diffCallback = new TopContentListDiffCallback(this.data, data);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.data.clear();
        if (data != null && !data.isEmpty()) {
            this.data.addAll(data);
        }
        diffResult.dispatchUpdatesTo(this);
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView titleView;
        private TextView typeView;
        private TextView releasesView;
        private TextView periodView;
        private TextView scoreView;

        OnItemClickListener listener;

        ItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            titleView = itemView.findViewById(R.id.title);
            releasesView = itemView.findViewById(R.id.releases);
            typeView = itemView.findViewById(R.id.type);
            periodView = itemView.findViewById(R.id.period);
            scoreView = itemView.findViewById(R.id.score);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition(), getItemId());
        }
    }

    public interface Callbacks {
        void onItemClick(ContentModel model);
    }
}
