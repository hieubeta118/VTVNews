package vccorp.project.cnnd.vtvnews.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.model.VideoRelatedModel;
import vccorp.project.cnnd.vtvnews.main.utils.AppPreferences;

/**
 * Created by Admin on 4/6/2016.
 */
public class VideoRelatedAdapter extends RecyclerView.Adapter<VideoRelatedAdapter.VideoRelatedViewHolder> {
    private Context mContext;
    private ArrayList<VideoRelatedModel> mVideoRelatedItem = new ArrayList<>();

    public VideoRelatedAdapter() {

    }

    public VideoRelatedAdapter(Context ctx, ArrayList<VideoRelatedModel> mVideoRelatedArrayList) {
        mContext = ctx;
        mVideoRelatedItem = mVideoRelatedArrayList;

    }

    @Override
    public VideoRelatedAdapter.VideoRelatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_video_related_item, parent, false);

        return new VideoRelatedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoRelatedAdapter.VideoRelatedViewHolder holder, int position) {
        final VideoRelatedModel videoRelatedModel = mVideoRelatedItem.get(position);
        holder.tvVideoName.setText(videoRelatedModel.getVideoName());
        holder.tvDate.setText(videoRelatedModel.getDateSchedule());
        Picasso.with(mContext).load(videoRelatedModel.getThumbImage()).into(holder.imgThumb);
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("getFileName", videoRelatedModel.getFileName());
//                AppPreferences.INSTANCE.setVideoURI(videoRelatedModel.getFileName());
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return mVideoRelatedItem.size();
    }

    public class VideoRelatedViewHolder extends RecyclerView.ViewHolder {
        public TextView tvVideoName;
        public TextView tvDate;
        public ImageView imgThumb;
        public RelativeLayout relativeLayout;

        public VideoRelatedViewHolder(View itemView) {
            super(itemView);
            tvVideoName = (TextView) itemView.findViewById(R.id.tvVideoName);
            tvDate = (TextView) itemView.findViewById(R.id.time_schedule_video);
            imgThumb = (ImageView) itemView.findViewById(R.id.imgThumbVideo);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rootView_video_related);
        }
    }
}
