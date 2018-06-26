package com.example.jason.rxjava_bestpractice.examples.one;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.jason.rxjava_bestpractice.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DownloadActivity extends AppCompatActivity {

    private TextView mTvDownload;
    private TextView mTvDownloadResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
        mTvDownload = findViewById(R.id.tv_download);
        mTvDownloadResult = findViewById(R.id.tv_download_result);
        mTvDownload.setOnClickListener(v -> startDownload());
    }

    private void startDownload() {
        Observable
                .intervalRange(1, 100, 0, 100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long value) {
                        Log.d("DownloadActivity", "onNext=" + value);
                        mTvDownloadResult.setText("当前下载进度：" + value + "%");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("DownloadActivity", "onError=" + e);
                        mTvDownloadResult.setText("下载失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("DownloadActivity", "onComplete");
                        mTvDownloadResult.setText("下载成功100%");
                    }
                });

    }

}