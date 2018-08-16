package com.junaid.demotestmma;


import android.widget.ImageView;

import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.bumptech.glide.Glide;

public class MediaLoader implements AlbumLoader {

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
//                .error(R.drawable.placeholder)
//                .placeholder(R.drawable.logo)
//                .crossFade()
                .into(imageView);
    }
}