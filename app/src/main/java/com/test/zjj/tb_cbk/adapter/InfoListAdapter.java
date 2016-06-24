package com.test.zjj.tb_cbk.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.zjj.httplib.BitmapRequest;
import com.test.zjj.httplib.HttpHelper;
import com.test.zjj.httplib.Request;
import com.test.zjj.tb_cbk.R;
import com.test.zjj.tb_cbk.beans.Info;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class InfoListAdapter extends BaseAdapter {
    private List<Info> list;
    private Context context;

    public InfoListAdapter(List<Info> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_info_list, null);
            vh.info_img = (ImageView) convertView.findViewById(R.id.info_item_iv);
            vh.info_tv_title = (TextView) convertView.findViewById(R.id.info_item_tv_title);
            vh.info_tv_desc = (TextView) convertView.findViewById(R.id.info_item_tv_desc);
            vh.info_tv_time = (TextView) convertView.findViewById(R.id.info_item_tv_time);
            vh.info_tv_source = (TextView) convertView.findViewById(R.id.info_item_tv_source);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.info_img.setImageResource(R.mipmap.ic_launcher);
        Info info = list.get(position);
        vh.info_tv_title.setText(info.getTitle());
        vh.info_tv_desc.setText(info.getDesc());
        vh.info_tv_time.setText(info.getPubDate());
        vh.info_tv_source.setText(info.getSource());
        if (info.getImageurls().length != 0) {
            for (int i = 0; i < info.getImageurls().length; i++) {
                String str = info.getImageurls()[i].getUrl();
            }
            String img_url = info.getImageurls()[0].getUrl();
            Picasso.with(context).load(img_url).into(vh.info_img);
//            vh.info_img.setTag(img_url);
//            Log.i("test", "-->getView: imgurl:" + img_url);
//            loadImage(vh.info_img, img_url);
        }
        return convertView;
    }

    private void loadImage( final ImageView info_img, final String img_url) {
        BitmapRequest request = new BitmapRequest(img_url, "GET", new Request.CallBack() {
            @Override
            public void onResoponse(Object o) {
                final Bitmap bitmap = (Bitmap) o;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        String tagUrl = (String) info_img.getTag();
                        if (tagUrl.equals(img_url)) {
                            info_img.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        });
        HttpHelper.addRequest(request);
    }

    class ViewHolder {
        private ImageView info_img;
        private TextView info_tv_title, info_tv_desc, info_tv_time, info_tv_source;
    }
}
