package com.sikeda.supendtitleviewdema;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rc_listview;
    private int mSuspensionHeight;
    private RelativeLayout mSuspensionBar;
    private LayoutInflater inflater;
    private MyAdapter adapter;
    private int mCurrentPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflater = getLayoutInflater();
        rc_listview = (RecyclerView)findViewById(R.id.rc_listview);
        mSuspensionBar = (RelativeLayout)findViewById(R.id.rl_suspend_title_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_listview.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter();
        rc_listview.setAdapter(adapter);
        rc_listview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = mSuspensionBar.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                if (view == null) return;
                if (view.getTop() <= mSuspensionHeight) {
                    mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                } else {
                    mSuspensionBar.setY(0);
                }

                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    mSuspensionBar.setY(0);

//                    updateSuspensionBar();
                }
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = inflater.inflate(R.layout.item_view, viewGroup, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{
            public ItemViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
