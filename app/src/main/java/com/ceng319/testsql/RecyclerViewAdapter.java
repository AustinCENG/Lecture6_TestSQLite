package com.ceng319.testsql;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.FlagViewHolder> {
    Context context;

    class FlagViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;
        private final ImageView flagImage;

        private FlagViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.countryName);
            flagImage = itemView.findViewById(R.id.flagView);
        }
    }

    private final LayoutInflater mInflater;
    private List<FlagEntity> mFlags; // Cached copy of words

    RecyclerViewAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public FlagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = mInflater.inflate(R.layout.recycler_view, parent, false);
        return new FlagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FlagViewHolder holder, int position) {
        if (mFlags != null) {
            FlagEntity current = mFlags.get(position);
            holder.wordItemView.setText(current.getCountry());

            // final FlagEntity name = mFlags.get(position);
           // holder.txtHeader.setText(name.getItemName());
            // holder.txtFooter.setText(String.valueOf(name.getItemId()));

            InputStream inputStream = null;
            try {
                String imageFile = current.getFlag();
                // File operations, need to get the image from asset files.

                inputStream = context.getAssets().open(imageFile);
                Drawable d = Drawable.createFromStream(inputStream, null);
                holder.flagImage.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText(R.string.no_country);
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mFlags != null)
            return mFlags.size();
        else return 0;
    }


    void setFlags(List<FlagEntity> flags){
        mFlags = flags;
        notifyDataSetChanged();
    }
}
