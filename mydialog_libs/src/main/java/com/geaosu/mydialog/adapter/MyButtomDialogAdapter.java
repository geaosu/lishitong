package com.geaosu.mydialog.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geaosu.mydialog.ButtonBean;
import com.geaosu.mydialog.R;

import java.util.ArrayList;
import java.util.List;

public class MyButtomDialogAdapter extends BaseAdapter {
    private List<ButtonBean> sList = new ArrayList<>();

    public void replaceAll(List<ButtonBean> list) {
        sList.clear();
        sList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return sList.size();
    }

    @Override
    public Object getItem(int position) {
        return sList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_dialog_buttom_selector_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ButtonBean bean = sList.get(position);
        String name = bean.getName();
        holder.tvTitle.setText(name);
        if (bean.isChecked()) {
            holder.ivSelect.setImageResource(R.mipmap.my_dialog_check_box_48_2_yes_blue);
            holder.tvTitle.setTextColor(Color.parseColor("#2683FF"));
        } else {
            holder.ivSelect.setImageResource(R.mipmap.my_dialog_check_box_48_2_no_gray);
            holder.tvTitle.setTextColor(Color.parseColor("#5F5F5F"));
        }
        return convertView;
    }

    public class ViewHolder {
        private ImageView ivSelect;
        private TextView tvTitle;

        public ViewHolder(View view) {
            ivSelect = (ImageView) view.findViewById(R.id.ivSelect);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }
}
