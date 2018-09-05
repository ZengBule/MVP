package com.example.jett.mvp_project_core.net;

import com.example.jett.mvp_project_core.net.callback.IError;
import com.example.jett.mvp_project_core.net.callback.IFailure;
import com.example.jett.mvp_project_core.net.callback.IRequest;
import com.example.jett.mvp_project_core.net.callback.ISuccess;
import com.example.jett.mvp_project_core.net.callback.RequestCallbacks;
import com.example.jett.mvp_project_core.net.download.DownloadHandler;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by jett on 2018/6/6.
 */
public class RestClient {
    private final HashMap<String,Object> PARAMS;
    private final String URL;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    //上传下载
    private final File FILE;

    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String FILENAME;

    public RestClient(HashMap<String, Object> params,
                      String url,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                        File file,String downloadDir,String extension,String filename) {
        this.PARAMS = params;
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;

        this.FILE=file;
        this.DOWNLOAD_DIR=downloadDir;  ///sdcard/XXXX.ext
        this.EXTENSION=extension;
        this.FILENAME=filename;
    }

    public static RestClientBuilder create(){
        return new RestClientBuilder();
    }

    //开始实现真实的网络操作
    private void request(HttpMethod method){
        final RestService service=RestCreator.getRestService();
        Call<String> call=null;
        if(REQUEST!=null){
            REQUEST.onRequestStart();
        }
        switch(method){
            case GET:
                call=service.get(URL,PARAMS);
                break;
            case POST:
                call=service.post(URL,PARAMS);
                break;
            case PUT:
                call=service.put(URL,PARAMS);
                break;
            case DELETE:
                call=service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MultipartBody.FORM,FILE);
                final MultipartBody.Part body=MultipartBody.Part.createFormData(
                        "file",FILE.getName(),requestBody);
                call=service.upload(URL,body);
                break;
            default:
                break;
        }
        if(call!=null){
            call.enqueue(getReqeustCallback());
        }
    }

    private Callback<String> getReqeustCallback(){
        return new RequestCallbacks(REQUEST,SUCCESS,FAILURE,ERROR);
    }

    //各种请求
    public final void get(){
        request(HttpMethod.GET);
    }
    public final void post(){
        request(HttpMethod.POST);
    }
    public final void put(){
        request(HttpMethod.PUT);
    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }
    public final void upload(){
        request(HttpMethod.UPLOAD);
    }
    public final void download(){
        new DownloadHandler(PARAMS,URL,REQUEST,
                    SUCCESS,FAILURE,ERROR,DOWNLOAD_DIR,
                    EXTENSION,FILENAME)
                .handleDownload();
    }

}







