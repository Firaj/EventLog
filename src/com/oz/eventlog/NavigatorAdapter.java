package com.oz.eventlog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class NavigatorAdapter extends ArrayAdapter<String> {
	private int resourceLayoutId;
	Context a;

	public NavigatorAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		resourceLayoutId = resource;
		a = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(resourceLayoutId, parent, false);
		TextView tv = (TextView) view.findViewById(R.id.eventitem);

		tv.setText(getItem(position));

		return view;
	}

}
