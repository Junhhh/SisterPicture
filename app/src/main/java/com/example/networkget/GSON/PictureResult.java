package com.example.networkget.GSON;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Create by 钱俊华 on 2019/1/26 0026
 *
 * @SerializedName 的意思是注解将对象里的属性 pictures 跟json里的字段results匹配起来
 *
 * 因为这个API是有个数组的,所以要将下面的results写成一个数组（数组的泛型就是新建的model类），再新建一个model类写里面的东西。
 *     "error": false,
 *     "results": [
 *         {...},{...}，.......
 *     ]
 *
 */
public class PictureResult {

    public boolean error;

    public @SerializedName("results") List<Picture> pictures;
}
