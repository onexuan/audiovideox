package com.example.audiovideox.primary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaExtractor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.example.audiovideox.R;
import com.example.audiovideox.util.cache.VideoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MediaExtractorActivity extends AppCompatActivity {

    private MediaExtractor mediaExtractor;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private VideosAdapter adapter;
    private List<String[]> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_extractor);
        recyclerView = findViewById(R.id.recycler_view_videos);
        layoutManager = new LinearLayoutManager(this);
        getVideos();
        adapter = new VideosAdapter(list, this, R.layout.item_layout_tv);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getVideos() {
        File mountedDir = new File(getExternalFilesDir(Environment.MEDIA_MOUNTED), "mediavideos");
        if (mountedDir.exists()) {
            File[] files = mountedDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                String[] arr = {files[i].getName(), files[i].getAbsolutePath()};
                this.list.add(arr);
            }
        }
    }

    private void initMediaExtractor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mediaExtractor = new MediaExtractor();
        }
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.playvideo_btn:
                //只播放视频不播放音频
                playVideo(adapter.getSelectedPath());
                break;
            case R.id.play_btn:
                //播放完整视频
                break;
            case R.id.playaudio_btn:
                //只播放音频
                break;
            default:
                break;
        }
    }

    void playVideo(final String path) {
        new Thread(){
            @Override
            public void run() {
                VideoUtil.playVideo(path);
            }
        }.start();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MediaExtractorActivity.class);
        context.startActivity(starter);
    }

}