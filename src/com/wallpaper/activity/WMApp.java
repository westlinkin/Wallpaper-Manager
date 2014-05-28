package com.wallpaper.activity;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class WMApp extends Application{
	
	@Override
	public void onCreate() {
		super.onCreate();

		initImageLoader(getApplicationContext());
	}
	
	public static void initImageLoader(Context context) {
		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			if (memClass / 16 >= 2) {
				memoryCacheSize = (memClass / 16) * 1024 * 1024; // 1/16 of app
																	// memory
																	// limit
			} else {
				memoryCacheSize = 2 * 1024 * 1024;
			}
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}

		// This configuration tuning is custom.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(6).memoryCacheSize(memoryCacheSize)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .enableLogging() // Not necessary in common
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
