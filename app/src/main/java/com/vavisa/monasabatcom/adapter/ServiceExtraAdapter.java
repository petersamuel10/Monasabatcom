package com.vavisa.monasabatcom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

public class ServiceExtraAdapter extends ArrayAdapter<Object> {

  public ServiceExtraAdapter(
      @NonNull Context context, int resource, @NonNull List<Object> objects) {
    super(context, resource, objects);
  }
}
