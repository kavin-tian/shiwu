package com.example.shiwu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.shiwu.BaseFragment;
import com.example.shiwu.DetailActivity;
import com.example.shiwu.glide.GlideRoundTransform;
import com.example.shiwu.R;
import com.example.shiwu.bean.DataBean;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

public class MyAdapter extends RecyclerView.Adapter {

    private static final int TYPE_BANNER = 1;
    private static final int TYPE_NOMAL = 2;
    private final Context context;
    private BaseFragment fragment;

    public MyAdapter(BaseFragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getActivity();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_tiltle, parent, false);
            return new BannerViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, parent, false);
            return new MyViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position == 0) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setBanner();
        } else {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.setPosition(position);
            String gifUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3531761125,3665413676&fm=26&gp=0.jpg";
            //普通显示GIF
            Glide.with(context)
                    .load(gifUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new MultiTransformation(new FitCenter(), new GlideRoundTransform(context, 20)))
                    .into(myViewHolder.imageVeiw);
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        } else {
            return TYPE_NOMAL;
        }
    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {

        private final Banner banner;

        public BannerViewHolder(View view) {
            super(view);
            banner = view.findViewById(R.id.banner);
        }

        public void setBanner() {
            useBanner(banner);
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageVeiw;
        private int position;

        public MyViewHolder(View view) {
            super(view);
            imageVeiw = view.findViewById(R.id.image_view);
            imageVeiw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "item: " + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(fragment.getActivity(), DetailActivity.class);
                    fragment.getActivity().startActivity(intent);
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }


    public void useBanner(Banner banner) {
        //—————————————————————————如果你想偷懒，而又只是图片轮播————————————————————————
        banner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData3()) {
            @Override
            public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data.imageUrl)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        })
                .addBannerLifecycleObserver(fragment)//添加生命周期观察者, 让banner自己控制
                .setIndicator(new CircleIndicator(context));
        //更多使用方法仔细阅读文档，或者查看demo

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();

            }
        });

    }


}
