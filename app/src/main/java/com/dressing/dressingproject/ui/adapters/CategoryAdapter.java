package com.dressing.dressingproject.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.ui.MainActivity;
import com.dressing.dressingproject.ui.models.CategoryModel;
import com.dressing.dressingproject.ui.widget.CheckItemView;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 14.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CheckViewHolder> implements CheckViewHolder.OnItemClickListener
{

    private final Context mContext;
    SparseBooleanArray checkedItems = new SparseBooleanArray();
    ArrayList<CategoryModel> items = new ArrayList<CategoryModel>();

    public CategoryAdapter(Context context) {
        mContext =context;
    }

    @Override
    public CheckViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        CheckItemView checkItemView = new CheckItemView(parent.getContext());
        CheckViewHolder viewHolder = new CheckViewHolder(checkItemView);
        viewHolder.setOnItemCheckListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CheckViewHolder viewHolder, int position)
    {

        viewHolder.setText(items.get(position).getCategoryText());
        viewHolder.setImageId(items.get(position).getResoruceId());
        viewHolder.setChecked(checkedItems.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(View view, int position) {
        boolean checked = !checkedItems.get(position);
        setItemCheck(position, checked);
        notifyDataSetChanged();

        //서브카테고리는 동작안함 카테고리 일 경우에만 동작
        //싱글모드인지 멀티모드인지로 구분
        if(isSingle())
        {
            //서브카테고리 활성화
            ArrayList<CategoryModel> subCategoryList=items.get(position).getSubCategoryList();
            ((MainActivity)mContext).setSubCategory(subCategoryList);
        }


    }

    public void add(CategoryModel item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public SparseBooleanArray getCheckedItemPositions()
    {
        return checkedItems;
    }

    public ArrayList<CategoryModel> getCheckedItems()
    {
        ArrayList<CategoryModel> models = new ArrayList<CategoryModel>();
        if (checkedItems.size() != 0) {
            for (int i = items.size() -1; i > -1 ; i--) {
                if (checkedItems.get(i)) {
                    models.add(items.get(i));
                }
            }
        }
        return models;
    }


    int mSelectedPosition = -1;

    public boolean isSingle() {
        return isSingle;
    }

    public void setIsSingle(boolean isSingle) {
        this.isSingle = isSingle;
    }

    private boolean isSingle = true;

    private void setItemCheck(int position, boolean checked) {
        //싱글선택
        if (isSingle) {
            //새로운 아이템이 Check 되었으면 이전 아이템은 false 상태로 만든다.
            //검색버튼도 비활성화 한다.
            if(mSelectedPosition != position)
            {
                checkedItems.put(mSelectedPosition,false);
                ((MainActivity)mContext).setEnableSearchBtn(false);

            }
            //새로운 아이템은 check한다.
            checkedItems.put(position,true);
            mSelectedPosition = position;
            notifyDataSetChanged();
        }
        //멀티선택
        else
        {
            if(checkedItems.get(position) == true)
            {
                checkedItems.put(position,false);
            }
            else checkedItems.put(position,true);

            notifyDataSetChanged();

            //true 하나이상 선택되어 있다면 버튼 활성화
            if(getCheckedItems().size() >0)
            {
                ((MainActivity)mContext).setEnableSearchBtn(true);
            }
            //버튼 비활성화
            else
            {
                ((MainActivity)mContext).setEnableSearchBtn(false);
            }
        }
    }

    public void Clear() {
        items.clear();
        checkedItems.clear();
        notifyDataSetChanged();
    }
}
