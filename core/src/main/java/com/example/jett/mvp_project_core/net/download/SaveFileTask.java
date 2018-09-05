package com.example.jett.mvp_project_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.jett.mvp_project_core.app.ProjectInit;
import com.example.jett.mvp_project_core.net.callback.IRequest;
import com.example.jett.mvp_project_core.net.callback.ISuccess;
import com.example.jett.mvp_project_core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by jett on 2018/6/6.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File>{
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir=(String)params[0];
        String extension=(String)params[1];
        ResponseBody body=(ResponseBody)params[2];
        String name=(String)params[3];
        InputStream is=body.byteStream();
        if(downloadDir==null || downloadDir.equals("")){
            downloadDir="down_loads";
        }
        if(extension==null){
            extension="";
        }
        if(name==null){
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else{
            return FileUtil.writeToDisk(is,downloadDir,name);
        }

    }
    //如果文件已经下完了
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST!=null){
            REQUEST.onRequestEnd();
        }

        //下到了APK直接安装
        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install=new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            ProjectInit.getApplicationContext().startActivity(install);
        }
    }
}
