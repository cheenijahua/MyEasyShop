package exmaple.easyshop.network;

import com.google.gson.Gson;

import java.io.File;

import exmaple.easyshop.model.CachePreferences;
import exmaple.easyshop.model.User;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2016/11/22.
 */

public class EasyShopClient {

    private static EasyShopClient easyShopClient;

    private OkHttpClient okHttpClient;

    private Gson gson;

    private EasyShopClient(){
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        //用于添加bomb的请求头拦截器
//        BombInterceptor bombInterceptor = new BombInterceptor();

        okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(bombInterceptor)
                .addInterceptor(interceptor)
                .build();
        gson = new Gson();
    }

    public static EasyShopClient getInstance(){
        if (easyShopClient==null){
            easyShopClient = new EasyShopClient();
        }
        return easyShopClient;
    }
    /**
     * 注册
     * post请求
     *  @param username
     *  @param password
     */
    public Call register(String username,String password){
        //表单形式构建请求体
        RequestBody body = new FormBody.Builder()
                .add("username",username)
                .add("password",password).build();

        //构建请求
        Request request = new Request.Builder()
                .url(EasyshopAPI.BASE_URL+EasyshopAPI.REGISTER)
                .post(body)
                .build();

        return okHttpClient.newCall(request);

    }

    /**
     * 登录
     * post请求
     *  @param username
     *  @param password
     */
    public Call login(String username,String password){
        //表单形式构建请求体
        RequestBody body = new FormBody.Builder()
                .add("username",username)
                .add("password",password).build();

        //构建请求
        Request request = new Request.Builder()
                .url(EasyshopAPI.BASE_URL+EasyshopAPI.LOGIN)
                .post(body)
                .build();

        return okHttpClient.newCall(request);

    }

    /**
     * 更换昵称
     * post请求
     *  @param user
     *
     */
    public Call updatedUser(User user){
        //多部分构建请求体
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user",gson.toJson(user))
                .build();


        //构建请求
        Request request = new Request.Builder()
                .url(EasyshopAPI.BASE_URL+EasyshopAPI.UPDATA)
                .post(body)
                .build();

        return okHttpClient.newCall(request);

    }
    /**
     * 更换头像
     * post请求
     *  @param file 要更新的文件图像
     *
     */
    public Call uploadAvatar(File file ){
        //多部分构建请求体
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user",gson.toJson(CachePreferences.getUser()))
                .addFormDataPart("image",file.getName(),
                        RequestBody.create(MediaType.parse("image/png"),file))
                .build();


        //构建请求
        Request request = new Request.Builder()
                .url(EasyshopAPI.BASE_URL+EasyshopAPI.UPDATA)
                .post(body)
                .build();

        return okHttpClient.newCall(request);

    }

    /**
     *  获取所有商品
     * post请求
     *  @param pageNo 商品分页string
     *  @param type 商品类型
     *
     */
    public Call getGoods(int pageNo,String type){
        //多部分构建请求体
        RequestBody body = new FormBody.Builder()
                .add("pageNo", String.valueOf(pageNo))
                .add("type",type)
                .build();


        //构建请求
        Request request = new Request.Builder()
                .url(EasyshopAPI.BASE_URL+EasyshopAPI.GETGOODS)
                .post(body)
                .build();

        return okHttpClient.newCall(request);

    }
    /**
     *  获取商品详情
     * post请求
     *  @param goodsUuid 商品表中商品的主键
     *
     *
     */
    public Call getGoodsData(String goodsUuid){
        //多部分构建请求体
        RequestBody body = new FormBody.Builder()
                .add("uuid",goodsUuid)
                .build();


        //构建请求
        Request request = new Request.Builder()
                .url(EasyshopAPI.BASE_URL+EasyshopAPI.DETAIL)
                .post(body)
                .build();

        return okHttpClient.newCall(request);

    }

    /**
     *  删除商品
     * post请求
     *  @param goodsUuid 商品表中商品的主键
     *
     *
     */
    public Call deleteGoods(String goodsUuid){
        //多部分构建请求体
        RequestBody body = new FormBody.Builder()
                .add("uuid",goodsUuid)
                .build();


        //构建请求
        Request request = new Request.Builder()
                .url(EasyshopAPI.BASE_URL+EasyshopAPI.DELETE)
                .post(body)
                .build();

        return okHttpClient.newCall(request);

    }





//
//    /**
//     * bomb云测试
//     * 注册
//     * post请求
//     *  @param username
//     *  @param password
//     */
//    public Call register_demo(String username,String password) {
//
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("username", username);
//            jsonObject.put("password", password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestBody body = RequestBody.create(null,jsonObject.toString());
//
//        //构建请求
//        Request request = new Request.Builder()
//                .url(EasyshopAPI_demo.REGISTER)
//                .post(body)
//                .build();
//
//        return okHttpClient.newCall(request);
//    }
}
