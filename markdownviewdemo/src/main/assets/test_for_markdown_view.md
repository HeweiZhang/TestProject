###### *下拉刷新，上拉加载，在Android项目中可谓是随处可见，其实现方式也是各种各样。今天介绍一个android support v4包下提供的下拉刷新控件SwipeRefreshLayout,个人觉得这个还是比较好看的*

***
#### 一、下拉刷新
看看知乎中的下拉刷新效果图：

![SwipeRefreshLayout【截图来自：知乎app】](http://upload-images.jianshu.io/upload_images/2510928-894b6c7216501703.PNG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###### 如何使用
```java
<android.support.v4.widget.SwipeRefreshLayout
    android:id="@+id/wifi_swipe_refresh"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_below="@+id/fl_title">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_wifi_table"
        android:layout_width="match_parent"
         android:layout_height="match_parent" />
    </android.support.v4.widget.SwipeRefreshLayout>

//Activity中使用
wifi_swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.wifi_swipe_refresh);
wifi_swipe_refresh.setOnRefreshListener(this);//SwipeRefreshLayout.OnRefreshListener
wifi_swipe_refresh.setColorSchemeResources(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
    );
 ``` 
上述监听事件（这里使用 [RxJava](https://github.com/ReactiveX/RxJava)实现定时操作）
```
    @Override
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).map(new Func1<Long, Object>() {
            @Override
            public Object call(Long aLong) {
                initData();
                if (null != wifi_swipe_refresh) {
                    wifi_swipe_refresh.setRefreshing(false);
                }
                return null;
            }
        }).subscribe();
    }   
    
```
--------------------------------------

### 进入时加载circleview 加载动画：

#### 1、默认setRefreshing(true)方法，无法实线初次加载显示动画的效果。我们可以这样处理：
```
    swipeRefreshLayout.setProgressViewOffset(false, 0,
     	(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().
     	getDisplayMetrics()));
    swipeRefreshLayout.setRefreshing(true);.0
```
---------------------------

##### 2、使用swipeRefreshLayout.post()方式来加载显示动画
```
    swipeRefreshLayout.post(new Runnable() {
		@Override
		public void run() {
			swipeRefreshLayout.setRefreshing(true);
		}
	});
```
--------------------------
### 关闭circleview 加载动画：

#### 由于子线程中不能更新UI，所以需要通过hander来定时关闭加载动画
使用handler计时  定时关闭
```
    final Handler handler = new Handler();

    handler.postDelayed(new Runnable() {
		@Override
		public void run() {
			swipeRefreshLayout.setRefreshing(false);
 		}
	},2000);//2000ms后取消（关闭）
```
-----
#### 避免刷新过程中仍可以操作界面,自定义SwipeRefreshLayout，通过父类拿到refresh状态，从而进行事件的传递机制控制
```java
public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (super.isRefreshing()) {//调用父view中的isRefreshing（）方法，从而拿到刷新状态。刷新时消化点击事件
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
```
#### 二、上拉加载(滑到底部自动加载)


![滑动底部，自动加载](http://upload-images.jianshu.io/upload_images/2510928-b39f2603a25dcec7.PNG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###### 结合HeaderViewRecyclerAdapter 
```java
private void initView() {
        firstCreateFootView = true;//首次加载headview，headview始终只能创建一次

        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_fans.setLayoutManager(manager);

        adapter = new FansInfoAdapter(getActivity());
        /**
         * 核心代码：RecyclerView.Adapter的adapter作为参数，初始化HeaderViewRecyclerAdapter
         * headerViewRecyclerAdapter才是作为RecyclerView的adapter，也是在这一步完成绑定headerView
         */
        headerViewRecyclerAdapter = new HeaderViewRecyclerAdapter(adapter);
        recyclerView_fans.setAdapter(headerViewRecyclerAdapter);
        recyclerView_fans.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisiableItemPosition = manager.findLastVisibleItemPosition();
                if (lastVisiableItemPosition + 1 == headerViewRecyclerAdapter.getItemCount() && headerViewRecyclerAdapter.getItemCount() >= pageSize) {
                    LogUtil.i("info", "滑动到底了pos:" + lastVisiableItemPosition);
                    //执行加载更多数据
                    simulateLoadMoreData();
                }
            }
        });

        /**
         * 下拉刷新监听
         */
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        initData();
                        swipe_refresh_layout.setRefreshing(false);
                        headerViewRecyclerAdapter.notifyDataSetChanged();
//                        ToastUtils.show(getActivity(), "刷新成功");
                        return null;
                    }
                }).subscribe();
            }
        });
    }
```
###### 核心的点在于何时去创建底部的headerview，这很有讲究，因为我们这里做的是滑动到底部自动加载更多数据，因此判断这个临界点成为核心，解决思路：从每次的请求网络数据回调中判断数据条目是否满一页即是否等于pageSize,瞒着条件即可调用创建hederview方法，动态添加headerview，注意：全程只可以创建一次，因此这边还要控制好。
```java
    //第一次网络请求，数据回调时进行判断，满足条件才创建
    private void createLoadMoreView() {
        if (getActivity() == null) {
            return;
        }
        //headerView 布局可以自行定义
        View loadMoreView = LayoutInflater.from(getActivity()).inflate(R.layout.item_view_load_more, recyclerView_fans, false);
        headerViewRecyclerAdapter.addFooterView(loadMoreView);
    }
```
###### 同时当数据已加载全部时还需要将headerview隐藏（remove）
```java
      //此处进行判断，满足条件时调用headerViewRecyclerAdapter.notifyItemRemoved(headerViewRecyclerAdapter.getItemCount());去掉headerview
      if (null != fansCardTableBeen.get(0).getUser_phone() && fansCardTableBeen.get(0).getUser_phone().equals("")) {//已滑到最后一页
                        if (currentPage != 0) {
                            currentPage--;
                            ToastUtils.show(getActivity(), "没有更多数据");
                            headerViewRecyclerAdapter.notifyItemRemoved(headerViewRecyclerAdapter.getItemCount());
                        } else {
                            tv_empty_data.setVisibility(View.VISIBLE);
                            recyclerView_fans.setVisibility(View.GONE);
                        }
                    } else {
                        for (FansCardTableBean bean : fansCardTableBeen) {
                            list.add(bean);
                        }
                        adapter.setData(list);
                        adapter.notifyDataSetChanged();
                    }
      }
```