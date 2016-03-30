package com.example.mr_g.searchviewscroll;

import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * Created by Mr_g on 16/3/30.
 */
public class SearchViewActivity extends AppCompatActivity {

    private static final float ENDMARGINLEFT = 50;
    private static final float ENDMARGINTOP = 5;
    private static final float STARTMARGINLEFT = 20;
    private static final float STARTMARGINTOP = 140;
    private RelativeLayout rv_search;
    private RelativeLayout search_rv;
    private ObservableScrollView sv_search;
    
    private ImageView iv_search;
    private int result;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_searchview);

        init();


    }


    @TargetApi(Build.VERSION_CODES.M)
    private void init() {
        rv_search = (RelativeLayout) findViewById(R.id.rv_search);
        search_rv = (RelativeLayout) findViewById(R.id.search_rv);

        sv_search = (ObservableScrollView) findViewById(R.id.sv_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        MyListview lv_searchview = (MyListview) findViewById(R.id.lv_searchview);

        String[] strings = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",};
        lv_searchview.setAdapter(new searchAdapter(SearchViewActivity.this, strings));
        sv_search.smoothScrollTo(0, 0);

        sv_search.setScrollViewListener(new ScrollViewListener() {

            private int evaluatemargin;
            private int evaluatetop;
            private FrameLayout.LayoutParams layoutParams;
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                Log.e("gaoyiming", "x" + x + "y" + y);

                int abs_y = Math.abs(y);
                if ((result - abs_y) > 0) {


                    IntEvaluator evaluator = new IntEvaluator();
                    float v = (float) (result - abs_y) / result;
                    Log.e("v", "" + v);
                    if (v <= 1) {
                        int evaluate = (Integer) evaluator.evaluate(v, 255, 0);
                        Log.e("evaluate", evaluate + "");
                        rv_search.getBackground().setAlpha(evaluate);

                        evaluatemargin = (Integer) evaluator.evaluate(v, DensityUtil.dip2px(SearchViewActivity.this,ENDMARGINLEFT), DensityUtil.dip2px(SearchViewActivity.this,STARTMARGINLEFT));
                        evaluatetop = (Integer) evaluator.evaluate(v,  DensityUtil.dip2px(SearchViewActivity.this,ENDMARGINTOP), DensityUtil.dip2px(SearchViewActivity.this,STARTMARGINTOP));
                        layoutParams = (FrameLayout.LayoutParams) search_rv.getLayoutParams();
                        layoutParams.setMargins(evaluatemargin, evaluatetop, evaluatemargin, 0);
                        search_rv.requestLayout();

                    }


                } else {
                    rv_search.getBackground().setAlpha(255);
                    if(layoutParams!=null){
                        layoutParams.setMargins(DensityUtil.dip2px(SearchViewActivity.this,ENDMARGINLEFT),DensityUtil.dip2px(SearchViewActivity.this,5), DensityUtil.dip2px(SearchViewActivity.this,ENDMARGINLEFT), 0);
                        search_rv.requestLayout();
                    }

                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {


        super.onWindowFocusChanged(hasFocus);

        int height_rv = rv_search.getHeight();
        int measuredHeight = rv_search.getMeasuredHeight();
        int bottom = rv_search.getBottom();

        int height_iv = iv_search.getHeight();
        int measuredHeight1 = iv_search.getMeasuredHeight();
        int bottom1 = iv_search.getBottom();
        Log.e("gaoyiming2", "x" + height_iv + measuredHeight1 + bottom1);
        result = Math.abs(height_iv - height_rv);
        rv_search.getBackground().setAlpha(0);

    }

    class searchAdapter extends BaseAdapter {
        Context context;
        String[] strings;
        private View view;
        private TextView tv_search;

        public searchAdapter(Context context, String[] strings) {
            this.context = context;
            this.strings = strings;
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                view = View.inflate(context, R.layout.layout_search, null);
                tv_search = (TextView) view.findViewById(R.id.tv_search);
            } else {
                view = convertView;

            }

            tv_search.setText("1");
            return view;
        }
    }

}
