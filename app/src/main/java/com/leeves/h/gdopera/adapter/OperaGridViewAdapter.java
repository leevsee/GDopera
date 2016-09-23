package com.leeves.h.gdopera.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leeves.h.gdopera.OperaInfo;
import com.leeves.h.gdopera.R;

import java.util.List;

/**
 * Function：
 * Created by h on 2016/9/7.
 *
 * @author Leeves
 */
public class OperaGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<OperaInfo> mOperaInfos;
    private LayoutInflater mLayoutInflater;

    static class ViewHolder {
        TextView mTextView;
        SimpleDraweeView mSimpleDraweeView;
    }

    public OperaGridViewAdapter(Context context, List<OperaInfo> operaInfos) {
        mContext = context;
        mOperaInfos = operaInfos;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mOperaInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mOperaInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_opera, null);
            holder.mSimpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.opera_frescoView);
            holder.mTextView = (TextView) convertView.findViewById(R.id.opera_textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mSimpleDraweeView.setImageURI(Uri.parse(mOperaInfos.get(position).getPictureUrl()));
        holder.mTextView.setText(mOperaInfos.get(position).getOperaTitle());
        return convertView;
    }

    private void getDataBaseData() {

    }
}
