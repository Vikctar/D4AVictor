package com.digital4africa.d4avictor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digital4africa.d4avictor.R;
import com.digital4africa.d4avictor.activity.PostDetailActivity;
import com.digital4africa.d4avictor.model.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListViewHolder>{

    private Context context;
    private List<Post> items;

    public ListItemAdapter(Context context, List<Post> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        final Post item = items.get(position);
        holder.name.setText(item.getTitle());
        holder.description.setText(item.getExcerpt());


        Glide.with(context).load(item.getThumbnail()).into(holder.thumbnail);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("content", item.getContent());
                intent.putExtra("excerpt", item.getExcerpt());
                intent.putExtra("author", item.getAuthor());
                intent.putExtra("thumbnail", item.getThumbnail());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();

    }


    public class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.thumbnail)
        ImageView thumbnail;
        View view;
        private ListViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
