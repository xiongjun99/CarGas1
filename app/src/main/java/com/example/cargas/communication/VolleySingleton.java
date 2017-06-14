package com.example.cargas.communication;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2015/2/2.
 */
public class VolleySingleton
{
    private static VolleySingleton _singleton;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;
    
    public VolleySingleton(Context context)
    {
        this.mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue,
        new ImageLoader.ImageCache()
        {
            private final LruCache<String,Bitmap> cache = new LruCache<String ,Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url){
                return cache.get(url);
            }
            @Override
            public void putBitmap(String url,Bitmap bitmap){
                cache.put(url,bitmap);
            }
        });
    }
    
    public static synchronized VolleySingleton getVolleySingleton(Context context)
    {
        if(_singleton == null){
            _singleton = new VolleySingleton(context);
        }
        return _singleton;
    }
    
    public RequestQueue getRequestQueue()
    {
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }
    
    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
    }
    
    public ImageLoader getImageLoader()
    {
        return mImageLoader;
    }
}
