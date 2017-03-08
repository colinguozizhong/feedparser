package io.tuuzed.tuuzed.rssparser.simple;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import io.github.tuuzed.adapter.ItemComponent;

/**
 * @author TuuZed
 */
public class RssItemItemComponent implements ItemComponent<RssItem, RssItemItemComponent.ViewHolder> {


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull final RssItem rssItem) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), rssItem.getLink(), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.textTitle.setText(String.format("%s", rssItem.getTitle()));
        viewHolder.textDescription.setText(String.format("%s", rssItem.getDescription()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_rss, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textDescription;

        ViewHolder(View convertView) {
            super(convertView);
            this.textTitle = (TextView) convertView.findViewById(R.id.textTitle);
            this.textDescription = (TextView) convertView.findViewById(R.id.textDescription);
        }
    }
}
