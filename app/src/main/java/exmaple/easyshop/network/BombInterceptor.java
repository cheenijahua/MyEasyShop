package exmaple.easyshop.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/23.
 */

public class BombInterceptor implements Interceptor {
    @Override
    //        X-Bmob-Application-Id: Your Application ID
    //        X-Bmob-REST-API-Key: Your REST API Key
    //        Content-Type: application/json
    public Response intercept(Chain chain) throws IOException {
        //拿到拦截请求
        Request request = chain.request();
        //拿到拦截请求的构造器
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-Type","application/json");
        builder.addHeader("X-Bmob-REST-API-Key","c00104962a9b67916e8cbcb9157255de");
        builder.addHeader("X-Bmob-Application-Id",
                "623aaef127882aed89b9faa348451da3");

        //添加请求头的请求
        return chain.proceed(builder.build());
    }
}
