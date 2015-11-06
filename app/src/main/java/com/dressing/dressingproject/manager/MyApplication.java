package com.dressing.dressingproject.manager;

import android.app.Application;
import android.content.Context;

/**
 * Created by lee on 15. 11. 6.
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        initImageLoader(this);
    }

    public static Context getContext() {
        return mContext;
    }

    public static void initImageLoader(Context context) {
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub)
//                .showImageForEmptyUri(R.drawable.ic_empty)
//                .showImageOnFail(R.drawable.ic_error)
//                .cacheInMemory(true)
//                .cacheOnDisc(true)
//                .considerExifParams(true)
//                .build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .writeDebugLogs() // Remove for release app
//                .defaultDisplayImageOptions(options)
//                .imageDownloader(new HttpClientImageDownloader(context, NetworkManager.getInstance().getHttpClient()))
//                .build();
//        ImageLoader.getInstance().init(config);
    }

}
