package com.example.mr_g.searchviewscroll;

import android.animation.IntEvaluator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by Mr_g on 16/3/30.
 */
public class SearchViewActivity extends AppCompatActivity {

    private static final float ENDMARGINLEFT = 50;
    private static final float ENDMARGINTOP = 5;
    private static final float STARTMARGINLEFT = 20;
    private static final float STARTMARGINTOP = 140;
    private RelativeLayout rv_bar;
    private RelativeLayout rv_search;

    private ImageView iv_search;
    private int scrollLength;//顶部栏从透明变成不透明滑动的距离



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_searchview);



            rv_bar = (RelativeLayout) findViewById(R.id.rv_bar);
            rv_search = (RelativeLayout) findViewById(R.id.rv_search);
        ObservableScrollView sv_search = (ObservableScrollView) findViewById(R.id.sv_search);
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

                    int abs_y = Math.abs(y);
                    //滑动距离小于顶部栏从透明到不透明所需的距离
                    if ((scrollLength - abs_y) > 0) {

                        //估值器
                        IntEvaluator evaluator = new IntEvaluator();
                        float percent = (float) (scrollLength - abs_y) / scrollLength;
                        
                        if (percent <= 1) {

                            //透明度
                            int evaluate = evaluator.evaluate(percent, 255, 0);
                            rv_bar.getBackground().setAlpha(evaluate);
                            //搜索栏左右margin值
                            evaluatemargin = evaluator.evaluate(percent, DensityUtil.dip2px(SearchViewActivity.this,ENDMARGINLEFT), DensityUtil.dip2px(SearchViewActivity.this,STARTMARGINLEFT));
                            //搜索栏顶部margin值
                            evaluatetop = evaluator.evaluate(percent,  DensityUtil.dip2px(SearchViewActivity.this,ENDMARGINTOP), DensityUtil.dip2px(SearchViewActivity.this,STARTMARGINTOP));

                            layoutParams = (FrameLayout.LayoutParams) rv_search.getLayoutParams();
                            layoutParams.setMargins(evaluatemargin, evaluatetop, evaluatemargin, 0);
                            rv_search.requestLayout();
                        }


                    } else {
                        rv_bar.getBackground().setAlpha(255);
                        if(layoutParams!=null){
                            layoutParams.setMargins(DensityUtil.dip2px(SearchViewActivity.this,ENDMARGINLEFT),DensityUtil.dip2px(SearchViewActivity.this,5), DensityUtil.dip2px(SearchViewActivity.this,ENDMARGINLEFT), 0);
                            rv_search.requestLayout();
                        }

                    }
                }
            });



    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        int height_rv = rv_bar.getHeight();
        int height_iv = iv_search.getHeight();

        scrollLength = Math.abs(height_iv - height_rv);

        //把顶部bar设置为透明
        rv_bar.getBackground().setAlpha(0);

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

            tv_search.setText(strings[position]);
            return view;
        }
    }

}
