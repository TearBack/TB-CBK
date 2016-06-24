package com.test.zjj.tb_cbk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.zjj.tb_cbk.R;
import com.test.zjj.tb_cbk.beans.Collection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class CollectListAdapter extends BaseAdapter {
    private List<Collection> list;
    private Context context;

    public CollectListAdapter(List<Collection> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_collect, null);
            vh.tv_num = (TextView) convertView.findViewById(R.id.collect_item_num);
            vh.tv_title = (TextView) convertView.findViewById(R.id.collect_item_title);
            vh.tv_time = (TextView) convertView.findViewById(R.id.collect_item_savetime);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_num.setText(position+1+"");
        vh.tv_title.setText(list.get(position).getTitle());
        vh.tv_time.setText(new SimpleDateFormat("MM月dd日 HH:mm").format(new Date(list.get(position).getSaveTime())));
        return convertView;
    }

    class ViewHolder {
        private TextView tv_num, tv_title, tv_time;
    }
}
