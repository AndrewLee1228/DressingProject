package com.dressing.dressingproject.ui.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.BrandListData;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 25.
 */
public class BrandListAdapter extends ArrayAdapter<BrandListData>
{
    private SparseBooleanArray checks;

    public BrandListAdapter(Context context, ArrayList<BrandListData> data)
    {
        super(context, R.layout.brand_row_layout, data);
        this.checks = new SparseBooleanArray(data.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        BrandListData country = getItem(position);

        if ( convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.brand_row_layout, parent, false);
        }

        ImageView countryImg = (ImageView)convertView.findViewById(R.id.brand_img);
        Glide.with(getContext())
//                .load(country.getImg())
                .load(R.mipmap.ic_launcher)
                .asBitmap()
//                .centerCrop()
                .thumbnail(0.1f) //이미지 크기중 10%만 먼저 가져와 보여줍니다.
                .override(400, 400)
                .diskCacheStrategy (DiskCacheStrategy.RESULT)
                .into(countryImg);

        TextView nameView = (TextView)convertView.findViewById(R.id.brand_name_text);
        nameView.setText(country.getName());

        CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.brand_checkbox);
        checkBox.setChecked(country.isSync());

        checks.put(country.getCode(), country.isSync());
        checkBox.setOnCheckedChangeListener(new CheckedClass( country.getCode() ));
        return convertView;
    }

    //체크항목 초기화
    public void ClearChecked()
    {
        checks.clear();
    }

    //Checked item list
    public ArrayList<BrandListData> getCheckedItems()
    {
        ArrayList<BrandListData> models = new ArrayList<BrandListData>();
        if (checks.size() != 0) {
            for (int i = 0; i < getCount() ; i++) {
                if (checks.get(i)) {
                    //Log.d("getItem(i) :" , getItem(i).getName());
                    models.add(getItem(i));
                }
            }
        }
        return models;
    }

    //Checked list
    public SparseBooleanArray getChecks()
    {
        return checks;
    }

    class CheckedClass implements CompoundButton.OnCheckedChangeListener
    {
        private int code;

        public CheckedClass( int code )
        {
            this.code = code;
        }

        @Override
        public void onCheckedChanged(CompoundButton button, boolean checked)
        {
            // Check code
            checks.put(code, checked);
        }
    }

}