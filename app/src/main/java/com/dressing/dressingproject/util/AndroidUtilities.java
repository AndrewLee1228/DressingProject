package com.dressing.dressingproject.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.ApplicationLoader;

/**
 * Created by lee on 15. 11. 8.
 */
public class AndroidUtilities {

    public static float density = 1;

    static {
        density = ApplicationLoader.getContext().getResources().getDisplayMetrics().density;
    }

    public static void MakeFavoriteToast(Context context) {
        Toast toast = Toast.makeText(context,"", Toast.LENGTH_SHORT);
        //가운데정렬
        toast.setGravity(Gravity.CENTER, 0, AndroidUtilities.dp(-160));
        //토스트 레이아웃
        /*LinearLayout toastView = (LinearLayout) toast.getView();
        toastView.setBackgroundColor(Color.RED);
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(R.mipmap.ic_launcher);
        toastView.addView(imageCodeProject, 0);
        toast.show();*/
        //토스트 레이아웃
        LinearLayout toastView = (LinearLayout) toast.getView();
        toastView.setBackgroundColor(Color.WHITE);
        toastView.setOrientation(LinearLayout.HORIZONTAL);
        toastView.setPadding(0, 0, 0, 0);
//        toastView.setElevation(5);

        ImageView favoriteIcon = new ImageView(context);
        favoriteIcon.setImageResource(R.drawable.ic_favorite);
        toastView.addView(favoriteIcon);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView titleText = new TextView(context);
        titleText.setText(Html.fromHtml("<b>찜하기 !</b>"));
        titleText.setTextColor(Color.BLACK);
        linearLayout.addView(titleText);

        TextView descriptionText = new TextView(context);
        descriptionText.setText(Html.fromHtml("찜하기에서 확인해주세요 !"));
        linearLayout.addView(descriptionText);

        toastView.addView(linearLayout);

        toast.show();
    }

    // 픽셀을 dp로 변환
    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int)Math.ceil(density * value);
    }
}


