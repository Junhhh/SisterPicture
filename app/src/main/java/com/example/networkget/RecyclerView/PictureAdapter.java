package com.example.networkget.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.networkget.GSON.Picture;
import com.example.networkget.R;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Create by 钱俊华 on 2019/1/31 0031
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private List<Picture> pictureList;
    private OnItemClickListener mOnItemClickListener;

    @Override
    public void onClick(View view) {
        if(mOnItemClickListener != null){
            //view.getTag()获取当前item的坐标
            mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemLongClick(view, (Integer) view.getTag());
        }
        return true;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imagePicture;

        public MyViewHolder(View itemView) {
            super(itemView);
            imagePicture = itemView.findViewById(R.id.picture_id);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item,parent,false);
        MyViewHolder holder= new MyViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position) {
        holder.itemView.setTag(position);//存储当前item的坐标（就是第几个item）
        Picture image = pictureList.get(position);
        MultiTransformation multi = new MultiTransformation(
                new CenterCrop(),//图片居中
                new RoundedCornersTransformation(5, 0, RoundedCornersTransformation.CornerType.ALL));//圆角设置
        Glide.with(holder.itemView.getContext()).load(image.url).apply(bitmapTransform(multi)).into(holder.imagePicture);
    }

    @Override
    public int getItemCount() {
        return pictureList == null ? 0:pictureList.size();
    }

    public void setPicture(List<Picture> pictureList){
        this.pictureList = pictureList;
        notifyDataSetChanged();
    }

    public void removeData(int position){
        pictureList.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
