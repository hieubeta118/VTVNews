package vccorp.project.cnnd.vtvnews.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialize.color.Material;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.model.LichChieuModel;
import vccorp.project.cnnd.vtvnews.main.model.ListLiveVideoModel;

/**
 * Created by Admin on 4/5/2016.
 */
public class LichChieuAdapter extends RecyclerView.Adapter<LichChieuAdapter.LichchieuViewholder> {
    private Context mContext;
    private ArrayList<LichChieuModel> mLichChieuItem = new ArrayList<>();
    private int selectedItem = -1;

    public LichChieuAdapter() {

    }

    public LichChieuAdapter(Context ctx, ArrayList<LichChieuModel> mLichChieuItemArrayList) {
        mContext = ctx;
        mLichChieuItem = mLichChieuItemArrayList;
    }

    @Override
    public LichChieuAdapter.LichchieuViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_lichchieu_item, parent, false);
        return new LichchieuViewholder(view);
    }

    @Override
    public void onBindViewHolder(LichChieuAdapter.LichchieuViewholder holder, int position) {
        final LichChieuModel lichChieuModel = mLichChieuItem.get(position);
        holder.tvTitle.setText(lichChieuModel.getTitle());
        holder.tvDescription.setText(lichChieuModel.getDescription());
        holder.tvTime.setText(lichChieuModel.getScheduleTime());
        try {
            CompareDate(holder, position);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        String urlShare = lichChieuModel.getUrl();
//        if(urlShare != null){
//            Bitmap bitmap = BitmapFactory
//                    .decodeResource(mContext.getResources(), R.drawable.camera);
//            holder.imgCamera.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 20, 20, true));
//        }
    }

    @Override
    public int getItemCount() {
        return mLichChieuItem.size();
    }

    public class LichchieuViewholder extends RecyclerView.ViewHolder {

        public TextView tvTime;
        public TextView tvTitle;
        public TextView tvDescription;
        public ImageView imgCamera;

        public LichchieuViewholder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.time_schedule);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            imgCamera = (ImageView) itemView.findViewById(R.id.imgCamera);
        }
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }

    public void CompareDate(LichChieuAdapter.LichchieuViewholder holder ,int position) throws ParseException {
        final LichChieuModel lichChieuModel = mLichChieuItem.get(position);
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            String formattedDate = formatter.format(calendar.getTime());
            Log.i("getCurrent", String.valueOf(formattedDate));
            Date currentDate = formatter.parse(formattedDate);
            int differenceLon = currentDate.compareTo(formatter.parse(lichChieuModel.getScheduleTime()));
//            int differenceNho = currentDate.compareTo(formatter.parse(lichChieuModel.getEndTime()));
//            if(differenceLon > 0 && differenceNho < 1){
//                Log.i("getOnAir", "OnAir");
//            }
        } catch (ParseException e) {
            e.getMessage();
        }
    }
}
