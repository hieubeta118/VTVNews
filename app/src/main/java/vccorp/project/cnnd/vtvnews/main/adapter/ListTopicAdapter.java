package vccorp.project.cnnd.vtvnews.main.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.model.ListNewsTopic;

/**
 * Created by Admin on 3/26/2016.
 */
public class ListTopicAdapter extends RecyclerView.Adapter<ListTopicAdapter.ListTopicViewHolder> {
    private Context mContext;
    private ArrayList<ListNewsTopic> mNewsTopicItem = new ArrayList<>();
    private Fragment mFragment = null;
    private static Fragment fragment;
    private int selectedItem = -1;

    public ListTopicAdapter(Context ctx, ArrayList<ListNewsTopic> listNewsTopics) {
        mContext = ctx;
        mNewsTopicItem = listNewsTopics;
    }

    @Override
    public ListTopicAdapter.ListTopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_item, parent, false);
        return new ListTopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListTopicAdapter.ListTopicViewHolder holder, final int position) {
        final ListNewsTopic listNewsTopic = mNewsTopicItem.get(position);
        holder.tvTopic.setText(listNewsTopic.getCateTitle());
        if(position == selectedItem){
            holder.tvTopic.setTextColor(Color.parseColor("#FF0000"));

        }else{
            holder.tvTopic.setTextColor(Color.parseColor("#6e6e6e"));
        }

    }


    @Override
    public int getItemCount() {
        return mNewsTopicItem.size();
    }

    public class ListTopicViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTopic;
        public RelativeLayout relativeLayout;

        public ListTopicViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.root_relative);
            tvTopic = (TextView) itemView.findViewById(R.id.tv_topic);

        }

    }
    public void setSelecteditem(int selecteditem) {
        this.selectedItem = selecteditem;
        notifyDataSetChanged();
    }


}
