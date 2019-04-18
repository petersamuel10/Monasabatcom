package com.vavisa.monasabatcom.utility;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListSizeUtility {

  public static void setListViewHeightBasedOnChildren(ListView listView) {
    ListAdapter listAdapter = listView.getAdapter();
    if (listAdapter == null) return;

    int desiredWidth =
        View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
    int totalHeight = 0;
    View view = null;
    for (int i = 0; i < listAdapter.getCount(); i++) {
      view = listAdapter.getView(i, view, listView);
      if (i == 0)
        view.setLayoutParams(
            new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

      view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
      totalHeight += view.getMeasuredHeight();
    }
    ViewGroup.LayoutParams params = listView.getLayoutParams();
    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    listView.setLayoutParams(params);
  }

  public static void setRecyclerViewHeightBasedOnChildren(RecyclerView recyclerView) {
    RecyclerView.Adapter listAdapter = recyclerView.getAdapter();
    if (listAdapter == null) return;

    int desiredWidth =
        View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.UNSPECIFIED);
    int totalHeight = 0;
    RecyclerView.ViewHolder view = null;
    for (int i = 0; i < listAdapter.getItemCount(); i++) {
      // view = listAdapter.getView(i, view, listView);
      view = listAdapter.onCreateViewHolder(recyclerView, i);
      // viewHolder.itemView
      if (i == 0)
        view.itemView.setLayoutParams(
            new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

      view.itemView.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
      totalHeight += view.itemView.getMeasuredHeight();
    }
    ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
    params.height = totalHeight;
    recyclerView.setLayoutParams(params);
  }
}
