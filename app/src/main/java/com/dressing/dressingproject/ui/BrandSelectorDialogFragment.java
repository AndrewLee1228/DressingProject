//package com.dressing.dressingproject.ui;
//
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.v4.app.DialogFragment;
//import android.support.v7.app.AlertDialog;
//import android.widget.updateFilter;
//
//import com.dressing.dressingproject.ui.models.MallModel;
//
//import java.util.ArrayList;
//
///**
// * Created by lee on 15. 11. 25.
// */
//public class BrandSelectorDialogFragment extends DialogFragment {
//    MallModel[] mArrays = {};
//    ArrayList<MallModel> mSelectedItems;
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        mSelectedItems = new ArrayList<MallModel>();
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        builder.setTitle("브랜드 검색")
//                .setMultiChoiceItems((CharSequence[]) mArrays, null, new DialogInterface.OnMultiChoiceClickListener() {
//                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                        if (isChecked)
//                            mSelectedItems.add(mArrays[which]);
//                        else if (mSelectedItems.contains(mArrays[which]))
//                            mSelectedItems.remove(mSelectedItems.indexOf(mArrays[which]));
//                    }
//                })
//                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        String str = "";
//                        for (int i = 0; i < mSelectedItems.size(); i++)
//                            str = str + mSelectedItems.get(i) + " ";
//                        updateFilter.makeText(getActivity(), str, updateFilter.LENGTH_SHORT).show();
//                    }
//                })
//                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//
//        return builder.create();
//    }
//
//    //브랜드 리스트 추가!
//    public void addBrandList(ArrayList<MallModel> mallList)
//    {
//        // List -> MallModel[]
//        mArrays = mallList.toArray(new MallModel[mallList.size()]);
//
//    }
//}