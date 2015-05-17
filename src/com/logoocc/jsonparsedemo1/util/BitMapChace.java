package com.logoocc.jsonparsedemo1.util;

import android.R.integer;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitMapChace implements ImageCache {
	private LruCache<String, Bitmap> mLruCache;

	public BitMapChace() {
		int maxSize = 1024 * 1024 * 10;
		mLruCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {

				return value.getHeight() * value.getRowBytes();
			}
		};

	}

	@Override
	public Bitmap getBitmap(String url) {

		return mLruCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mLruCache.put(url, bitmap);
	}

}
