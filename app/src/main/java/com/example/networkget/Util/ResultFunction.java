package com.example.networkget.Util;

import com.example.networkget.GSON.Picture;
import com.example.networkget.GSON.PictureResult;

import java.util.List;

import io.reactivex.functions.Function;

/**
 * Create by 钱俊华 on 2019/1/31 0031
 */
public class ResultFunction implements Function<PictureResult,List<Picture>> {


    @Override
    public List<Picture> apply(PictureResult pictureResult) throws Exception {
        return null;
    }
}
