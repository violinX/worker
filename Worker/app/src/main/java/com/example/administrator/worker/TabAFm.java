package com.example.administrator.worker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TabAFm extends Fragment {

    private int[] imageIds;
    private LinearLayout main_dots;
    private ViewPager image_vp;

    private ListView mListView;
    private ArrayList<ListItem> mList;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            image_vp.setCurrentItem(image_vp.getCurrentItem() + 1,true);
            handler.sendEmptyMessageDelayed(0,5000);
        }
    };
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("AAAAAAAAAA____onAttach");

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("AAAAAAAAAA____onCreate");


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("AAAAAAAAAA____onCreateView");
        View rootView = inflater.inflate(R.layout.tab_a, container, false);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onStart() {
        super.onStart();
        System.out.println("AAAAAAAAAA____onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        System.out.println("AAAAAAAAAA____onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("AAAAAAAAAA____onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        System.out.println("AAAAAAAAAA____onStop");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("AAAAAAAAAA____onDestroyView");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("AAAAAAAAAA____onDestroy");
        handler.removeCallbacksAndMessages(null);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("AAAAAAAAAA____onDetach");
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public  void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageIds = new int[]{R.drawable.c1 , R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5, R.drawable.c6};
        main_dots = (LinearLayout) view.findViewById(R.id.main_dots);
        initDot();
        image_vp = ((ViewPager) view.findViewById(R.id.image_vp));
        image_vp.setAdapter(new MyPagerAdapter());
        image_vp.setCurrentItem(imageIds.length * 10000);
        updateDescAndDot();
        handler.sendEmptyMessageDelayed(0, 5000);
        mListView = (ListView) view.findViewById(R.id.listView1);
        // 获取Resources对象
        Resources res = this.getResources();
        mList = new ArrayList<TabAFm.ListItem>();
        // 初始化data
        ListItem item = new ListItem();
        item.setImage(res.getDrawable(R.drawable.icon1));
        item.setTitle("最新公告");
        item.setMsg("json内容");
        mList.add(item);

        item = new ListItem();
        item.setImage(res.getDrawable(R.drawable.icon2));
        item.setTitle("待办事宜");
        item.setMsg("json内容");
        mList.add(item);
        // 获取MainListAdapter对象
        MainListViewAdapter adapter = new MainListViewAdapter();
        // 将MainListAdapter对象传递给ListView视图
        mListView.setAdapter(adapter);

        image_vp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        handler.sendEmptyMessageDelayed(0, 5000);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessageDelayed(0, 5000);
                        break;
                }
                return false;
            }
        });
        image_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                updateDescAndDot();
            }
            @Override
            public void onPageScrollStateChanged(int position) {
            }
        });
    }
    /**
     * 动态添加点
     */
    private void initDot() {
        for (int i = 0; i < imageIds.length; i++) {
            View view = new View(this.getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25, 25);
            params.leftMargin = (i == 0 ? 0 : 20);//给除了第一个点之外的点都加上marginLeft
            view.setLayoutParams(params);//设置宽高
            view.setBackgroundResource(R.drawable.selector_dot);//设置背景图片
            main_dots.addView(view);
        }
    }
    /**
     * 根据当前page来显示不同的文字和点
     */
    private void updateDescAndDot() {
        int currentPage = image_vp.getCurrentItem() % imageIds.length;
        //更新点
        //遍历所有的点，当点的位置和currentPage相当的时候，则设置为可用，否则是禁用
        for (int i = 0; i < main_dots.getChildCount(); i++) {
            main_dots.getChildAt(i).setEnabled(i == currentPage);
        }
    }
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e("current position =====", position + "");
            ImageView imageView = new ImageView(getActivity().getApplicationContext());
            imageView.setImageResource(imageIds[position % imageIds.length]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    class ListItem {
        private Drawable image;
        private String title;
        private String msg;
        public Drawable getImage() {
            return image;
        }
        public void setImage(Drawable image) {
            this.image = image;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getMsg() {
            return msg;
        }
        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    /**
     * 定义ListView适配器MainListViewAdapter
     */
    class MainListViewAdapter extends BaseAdapter {
        /**
         * 返回item的个数
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mList.size();
        }
        /**
         * 返回item的内容
         */
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mList.get(position);
        }
        /**
         * 返回item的id
         */
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        /**
         * 返回item的视图
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItemView listItemView;

            // 初始化item view
            if (convertView == null) {
                // 通过LayoutInflater将xml中定义的视图实例化到一个View中
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.image_listview_1, null);
                // 实例化一个封装类ListItemView，并实例化它
                listItemView = new ListItemView();
                listItemView.imageView = (ImageView) convertView
                        .findViewById(R.id.listview1_image);
                listItemView.textView = (TextView) convertView
                        .findViewById(R.id.listview1_title);
                listItemView.msg = (TextView) convertView
                        .findViewById(R.id.listview1_msg);
                // 将ListItemView对象传递给convertView
                convertView.setTag(listItemView);
            } else {
                // 从converView中获取ListItemView对象
                listItemView = (ListItemView) convertView.getTag();
            }
            // 获取到mList中指定索引位置的资源
            Drawable img = mList.get(position).getImage();
            String title = mList.get(position).getTitle();
            String msg = mList.get(position).getMsg();

            // 将资源传递给ListItemView的域对象
            listItemView.imageView.setImageDrawable(img);
            listItemView.textView.setText(title);
            listItemView.msg.setText(msg);
            // 返回convertView对象
            return convertView;
        }
    }
    class ListItemView {
        ImageView imageView;
        TextView textView;
        TextView msg;
    }

}
