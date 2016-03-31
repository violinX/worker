package com.example.administrator.worker;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TabAFm extends Fragment {

    private int[] imageIds;
    private LinearLayout main_ll_dots;
    private ViewPager main_vp;

    TabPage activity = ((TabPage)getActivity());

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            main_vp.setCurrentItem(main_vp.getCurrentItem() + 1,true);
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

    public  void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageIds = new int[]{R.drawable.c1 , R.drawable.c2, R.drawable.c3};
        main_ll_dots = (LinearLayout) view.findViewById(R.id.main_ll_dots);
        System.out.println("调用到这里");

        initDot();

        main_vp = ((ViewPager) view.findViewById(R.id.main_vp));

        main_vp.setAdapter(new MyPagerAdapter());

        main_vp.setCurrentItem(imageIds.length * 10000);

        updateDescAndDot();

        handler.sendEmptyMessageDelayed(0, 5000);

        main_vp.setOnTouchListener(new View.OnTouchListener() {
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

        main_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            main_ll_dots.addView(view);
        }
    }

    /**
     * 根据当前page来显示不同的文字和点
     */
    private void updateDescAndDot() {
        int currentPage = main_vp.getCurrentItem() % imageIds.length;
        // tv_desc.setText(list.get(currentPage).getDesc());
        //更新点
        //遍历所有的点，当点的位置和currentPage相当的时候，则设置为可用，否则是禁用
        for (int i = 0; i < main_ll_dots.getChildCount(); i++) {
            main_ll_dots.getChildAt(i).setEnabled(i == currentPage);
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

}
