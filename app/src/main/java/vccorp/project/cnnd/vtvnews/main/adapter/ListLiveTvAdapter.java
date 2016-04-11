package vccorp.project.cnnd.vtvnews.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.model.ListLiveVideoModel;
import vccorp.project.cnnd.vtvnews.main.view.AutoHighlightImageView;

/**
 * Created by Admin on 4/5/2016.
 */
public class ListLiveTvAdapter extends RecyclerView.Adapter<ListLiveTvAdapter.ListLiveTvViewHolder> {
    private Context mContext;
    private ArrayList<ListLiveVideoModel> mLiveVideoItem = new ArrayList<>();
    private int selectedItem = -1;
    private final int[] ICON = {R.drawable.vtv1, R.drawable.vtv2,
            R.drawable.vtv3, R.drawable.vtv4,
            R.drawable.vtv5, R.drawable.vtv6,
            R.drawable.logo_vtv7, R.drawable.logo_vtv8,
            R.drawable.logo_vtv9};

    public ListLiveTvAdapter(Context ctx, ArrayList<ListLiveVideoModel> listLiveVideoModels) {
        mContext = ctx;
        mLiveVideoItem = listLiveVideoModels;
    }


    @Override
    public ListLiveTvAdapter.ListLiveTvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_live_channel_item, parent, false);

        return new ListLiveTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListLiveTvAdapter.ListLiveTvViewHolder holder, int position) {
        final ListLiveVideoModel listLiveVideoModel = mLiveVideoItem.get(position);
//        Picasso.with(mContext).load(ICON).into(holder.imgChannel);
        holder.imgChannel.setImageResource(ICON[position]);
        if (position == selectedItem) {
            holder.underLine.setVisibility(View.VISIBLE);
        } else {
            holder.underLine.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mLiveVideoItem.size();
    }

    public class ListLiveTvViewHolder extends RecyclerView.ViewHolder {
        public AutoHighlightImageView imgChannel;
        public View underLine;

        public ListLiveTvViewHolder(View itemView) {
            super(itemView);
            imgChannel = (AutoHighlightImageView) itemView.findViewById(R.id.imgChannel);
            underLine = itemView.findViewById(R.id.underLineItem);
        }
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }
}
