package com.sikeda.databindingtest.entity;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.sikeda.databindingtest.ImageLoaderUtil;

/**
 * Created by Administrator on 2016/11/4.
 */

public class User{
    private String username;
    private String nickname;
    private int age;
    private String userface;

    public User(String username, String nickname, int age, String userface) {
        this.username = username;
        this.nickname = nickname;
        this.age = age;
        this.userface = userface;
    }

    public User() {
    }
    @BindingAdapter("bind:userface")
    public static void getIntImage(ImageView iv,String userface){
        Log.e("info","getIntImage");
        ImageLoaderUtil.setImageWithCache(userface,iv);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }
}