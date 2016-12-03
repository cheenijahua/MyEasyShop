package exmaple.easyshop;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import exmaple.easyshop.model.CachePreferences;

/**
 * Created by Administrator on 2016/11/24.
 */

public class EasyShopApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化imageloader
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//开启内存缓存
                .cacheOnDisk(true)//开启硬盘缓存
                .resetViewBeforeLoading(true)//加载image之前重置它里面的所有图片
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(4*1024*1024)//设置内存缓存的大小4M
                .defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(configuration);

        //初始化本地配置
        CachePreferences.init(this);
    }

//    private List<Activity> activities = new LinkedList<>();
//    public void exit(){
//
//        if (activities==null){
//            Activity activity;
//
//            for (int i = 0; i<activities.size();i++){
//                activity = activities.get(i);
//
//                if (activity !=null){
//                    if (!activity.isFinishing()){
//                        activity.finish();
//                    }
//                    activity =null;
//                }
//                activities.remove(i);
//                i--;
//            }
//
//        }
//    }

}
