package com.example.jason.rxjava_bestpractice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jason.rxjava_bestpractice.examples.one.DownloadActivity;
import com.example.jason.rxjava_bestpractice.utils.Utils;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<String> mTitles;


    public MainAdapter(List<String> titles) {
        mTitles = titles;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item, parent, false);
        return new MainViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, int position) {
        final String title = mTitles.get(position);
        TextView titleView = holder.mTvTitle;
        titleView.setText(title);
        titleView.setOnClickListener(v -> {
            int position1 = holder.getAdapterPosition();
            switch (position1) {
                case 0:
                    Utils.startActivity(v.getContext(), DownloadActivity.class);
                    break;
//                    case 1:
//                        Utils.startActivity(v.getContext(), BufferActivity.class);
//                        break;
//                    case 2:
//                        Utils.startActivity(v.getContext(), SearchActivity.class);
//                        break;
//                    case 3:
//                        Utils.startActivity(v.getContext(), NewsActivity.class);
//                        break;
//                    case 4:
//                        Utils.startActivity(v.getContext(), PollingActivity.class);
//                        break;
//                    case 5:
//                        Utils.startActivity(v.getContext(), RetryActivity.class);
//                        break;
//                    case 6:
//                        Utils.startActivity(v.getContext(), CombineLatestActivity.class);
//                        break;
//                    case 7:
//                        Utils.startActivity(v.getContext(), CacheActivity.class);
//                        break;
//                    case 8:
//                        Utils.startActivity(v.getContext(), TimeActivity.class);
//                        break;
//                    case 9:
//                        Utils.startActivity(v.getContext(), RotationPersistActivity.class);
//                        break;
//                    case 10:
//                        Utils.startActivity(v.getContext(), WeatherActivity.class);
//                        break;
//                    case 11:
//                        Utils.startActivity(v.getContext(), HotObservableActivity.class);
//                        break;
//                    case 12:
//                        Utils.startActivity(v.getContext(), ErrorActivity.class);
//                        break;
//                    case 13:
//                        Utils.startActivity(v.getContext(), TokenActivity.class);
//                        break;
//                    case 14:
//                        Utils.startActivity(v.getContext(), NewsMvpActivity.class);
//                        break;
//                    case 15:
//                        Utils.startActivity(v.getContext(), UsingActivity.class);
//                        break;
                default:
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvTitle;

        public MainViewHolder(View rootView) {
            super(rootView);
            mTvTitle = rootView.findViewById(R.id.tv_title);
        }
    }
}
