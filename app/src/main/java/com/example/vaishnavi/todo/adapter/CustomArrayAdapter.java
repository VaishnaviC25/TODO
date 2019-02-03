package com.example.vaishnavi.todo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.vaishnavi.todo.R;
public class CustomArrayAdapter extends ArrayAdapter<String>{
    private String[] items;
    private int viewResourceId;
    public CustomArrayAdapter(Context context, int viewResourceId, String[] items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.viewResourceId = viewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        String adapterContent = (String)getItem(position);
        if (adapterContent != null) {
            TextView spinnerItem = (TextView) v.findViewById(R.id.textView);
            spinnerItem.setText(adapterContent);
        }
        return v;
    }
}