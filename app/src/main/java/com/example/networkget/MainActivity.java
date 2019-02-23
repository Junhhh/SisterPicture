package com.example.networkget;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.networkget.GSON.Picture;
import com.example.networkget.GSON.PictureResult;
import com.example.networkget.RecyclerView.PictureAdapter;
import com.example.networkget.Util.HttpUtil;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button next;

    private Button previous;

    private TextView number;

    private String TAG = "MainActivity";

    private List<Picture> pictureList;

    private int nowPapg = 0;//当前页数

    private PictureAdapter adapter = new PictureAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_recycle);

        initView();
        getURL(++nowPapg);
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter.setOnItemClickListener(new PictureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击了第"+(position+1)+"个",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("确定删除吗？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.removeData(position);
                            }
                        }).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recycler_next:
                getURL(++nowPapg);
                if(nowPapg == 2){
                    previous.setEnabled(true);
                }
                break;
            case R.id.recycler_previous:
                getURL(--nowPapg);
                if(nowPapg == 1){
                    previous.setEnabled(false);
                }
                break;
        }
    }
    /**
     * 1.map()将获取到的PictureResult转换成Picture的List数组
     * 2.subscribe()订阅，然后将获取到的List数组显示出来
     */

    @SuppressLint("CheckResult")
    private void getURL(final int page){
        HttpUtil.sendRRORequest("https://gank.io/api/",10,page)
                .map(new Function<PictureResult, List<Picture>>() {
                    @Override
                    public List<Picture> apply(PictureResult pictureRs) throws Exception {
                        pictureList = pictureRs.pictures;
                        return pictureList;
                    }
                })
                .subscribe(new Consumer<List<Picture>>() {
                    @Override
                    public void accept(List<Picture> pictures) throws Exception {
                        adapter.setPicture(pictures);
                        number.setText("第" + page +"页");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, throwable.getMessage(),Toast.LENGTH_SHORT);
                    }
                });
    }

    private void initView(){
        next = findViewById(R.id.recycler_next);
        previous = findViewById(R.id.recycler_previous);
        number = findViewById(R.id.recycler_page);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);

        previous.setEnabled(false);
    }

}
