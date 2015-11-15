package com.dressing.dressingproject.util;

/**
 * Created by lee on 15. 11. 12.
 */

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by lee on 15. 11. 12.
 *  Glide는 Bitmap포맷을 RGB_565를 사용합니다.
 *
 *  만약 메모리용량보다 화질이 더 중요하다고 생각한다면 Glide의 기본 Bitmap포맷을 ARGB_8888로 변경할수 있습니다.

 GlideModule을 상속받는 클래스를 하나 지정해서 기본 포맷을 ARGB_8888로 설정하고 이 GlideModule을 매니페스트에 등록시켜주면 됩니다.
 */
public class ImageLibraryGlideModule implements GlideModule {


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}