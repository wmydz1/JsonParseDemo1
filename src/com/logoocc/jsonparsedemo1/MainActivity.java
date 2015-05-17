package com.logoocc.jsonparsedemo1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.logoocc.jsonparsedemo1.Adapter.MyAdapter;
import com.logoocc.jsonparsedemo1.bean.User;

public class MainActivity extends Activity {
	private RequestQueue mRequestQueue;
	private List<User> userlist;
	private TextView mtv;
	private PullToRefreshListView mPullToRefreshListView;
	private MyAdapter myAdapter;
    private int mPage=1;
    private int mPageSize=10;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRequestQueue = Volley.newRequestQueue(this);
		userlist = new ArrayList<User>();
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv);
		myAdapter = new MyAdapter(this, userlist, mRequestQueue);
		getData(mPage, mPageSize);
		mPullToRefreshListView.setAdapter(myAdapter);
		mPullToRefreshListView.setMode(Mode.BOTH);
		init();
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				mPullToRefreshListView.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						init();
						mPullToRefreshListView.onRefreshComplete();
						
					}
				}, 2000);
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				mPullToRefreshListView.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mPage++;
						getData(mPage, mPageSize);
						mPullToRefreshListView.onRefreshComplete();
						
					}
				}, 2000);
				
			}
		});
	}

	private void getData(int page, int pageSize) {
	userlist.clear();
		
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
				"http://10.17.67.146:10000/?page=" + page + "&"
						+ "pageSize=" + pageSize, new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						try {
							JSONArray jsonArray = new JSONArray(
									response.toString());

							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jo = jsonArray.getJSONObject(i);

								int id = jo.getInt("Id");
								String name = jo.getString("Name");
								String time = jo.getString("CreatedAt");
                                String url =jo.getString("ImageUrl");
								User user = new User(id, name, time,url);
								userlist.add(user);

							}
                             
							myAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getApplicationContext(), "sorry", 1)
								.show();

					}
				});

		mRequestQueue.add(jsonArrayRequest);

	}
	// 设置页眉和页脚
		private void init() {
			// 页眉设置
			ILoadingLayout iloading = mPullToRefreshListView.getLoadingLayoutProxy(
					true, false);
			//
			iloading.setRefreshingLabel("正在努力加载.....");
			iloading.setPullLabel("下拉刷新");
			iloading.setReleaseLabel("松开即可刷新");
			// 刷新时间
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");

			iloading.setLastUpdatedLabel("上次刷新" + sdFormat.format(new Date()));

			// 设置页脚
			ILoadingLayout iloading2 = mPullToRefreshListView
					.getLoadingLayoutProxy(false, true);
			iloading2.setRefreshingLabel("正在努力加载.....");
			iloading2.setReleaseLabel("松开即可加载数据");
			iloading2.setPullLabel("上拉加载");
		}

	
}
