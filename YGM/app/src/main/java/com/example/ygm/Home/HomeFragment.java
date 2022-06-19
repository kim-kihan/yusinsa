package com.example.ygm.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ygm.Cart.CartActivity;
import com.example.ygm.DB.DB;
import com.example.ygm.User;
import com.example.ygm.LogIn.LogInActivity;
import com.example.ygm.MainActivity;
import com.example.ygm.MyMenu.ServiceCenter.FAQActivity;
import com.example.ygm.MyMenu.ServiceCenter.NoticeActivity;
import com.example.ygm.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    ArrayList<EventBannerItem> itemEventBanner;

    ViewPager2 eventSlideBanner, productRanking;
    TextView textView_eventBanner_position;

    ArrayList<HomeRankingGridItem> item;

    TextView btnAll;
    RadioGroup rgCategory;
    RadioButton rbALL, rbTOP, rbPants, rbShoes, rbHeadwear;

    TextView tvNotice, tvFAQ;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        MainActivity.navView.setVisibility(View.VISIBLE);
        MainActivity.title.setText("YUSINSA");
        setHasOptionsMenu(true);

        eventSlideBanner = v.findViewById(R.id.viewPager_event);

        itemEventBanner = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    eventSlideBanner.setAdapter(new EventBannerAdapter(itemEventBanner, getActivity()));
                    textView_eventBanner_position = v.findViewById(R.id.textView_eventbanner_position);
                    eventSlideBanner.registerOnPageChangeCallback(new OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            textView_eventBanner_position.setText(String.valueOf(position % itemEventBanner.size() + 1));
                        }
                    });
                    ((TextView) v.findViewById(R.id.textView_event_banner_item_size)).setText(" / " + itemEventBanner.size() + " ");
                    eventSlideBanner.setCurrentItem(Integer.MAX_VALUE / 2, false);
                }
            }
        }, getActivity(), R.id.viewPager_event, itemEventBanner);
        task.execute("http://15.165.152.15//GetEvent.php");

        Handler handler = new Handler();

        Runnable update = new Runnable() {
            @Override
            public void run() {
                eventSlideBanner.setCurrentItem(eventSlideBanner.getCurrentItem() + 1, true);
            }
        };

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 3000);

        btnAll = v.findViewById(R.id.button_view_all);
        btnAll.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_rankingFragment, null));

        productRanking = v.findViewById(R.id.viewPager_home_ranking);

        rgCategory = v.findViewById(R.id.radioGroup_home_ranking_category);
        rbALL = v.findViewById(R.id.radioButton);
        rbTOP = v.findViewById(R.id.radioButton2);
        rbPants = v.findViewById(R.id.radioButton3);
        rbShoes = v.findViewById(R.id.radioButton4);
        rbHeadwear = v.findViewById(R.id.radioButton5);

        item = new ArrayList<>();
        task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    productRanking.setAdapter(new HomeRankingSliderAdapter(item));
                    productRanking.setCurrentItem(Integer.MAX_VALUE / 2 - 3, false);
                    rbALL.setTextColor(Color.parseColor("#FFFFFF"));
                    rbALL.setBackgroundResource(R.drawable.tabitem_home_ranking_tab);

                    rgCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            rbALL.setTextColor(Color.parseColor("#80000000"));
                            rbTOP.setTextColor(Color.parseColor("#80000000"));
                            rbPants.setTextColor(Color.parseColor("#80000000"));
                            rbShoes.setTextColor(Color.parseColor("#80000000"));
                            rbHeadwear.setTextColor(Color.parseColor("#80000000"));
                            rbALL.setBackgroundResource(R.drawable.tabitem_home_ranking);
                            rbTOP.setBackgroundResource(R.drawable.tabitem_home_ranking);
                            rbPants.setBackgroundResource(R.drawable.tabitem_home_ranking);
                            rbShoes.setBackgroundResource(R.drawable.tabitem_home_ranking);
                            rbHeadwear.setBackgroundResource(R.drawable.tabitem_home_ranking);
                            ((RadioButton) v.findViewById(checkedId)).setTextColor(Color.parseColor("#FFFFFF"));
                            ((RadioButton) v.findViewById(checkedId)).setBackgroundResource(R.drawable.tabitem_home_ranking_tab);
                            int position = productRanking.getCurrentItem();
                            int d = position % 6;
                            switch(checkedId) {
                                case R.id.radioButton: productRanking.setCurrentItem(position - d, true); break;
                                case R.id.radioButton2: productRanking.setCurrentItem(position - d + 2, true); break;
                                case R.id.radioButton3: productRanking.setCurrentItem(position - d + 3, true); break;
                                case R.id.radioButton4: productRanking.setCurrentItem(position - d + 4, true); break;
                                case R.id.radioButton5: productRanking.setCurrentItem(position - d + 5, true); break;
                            }
                        }
                    });

                    productRanking.registerOnPageChangeCallback(new OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            rbALL.setTextColor(Color.parseColor("#80000000"));
                            rbTOP.setTextColor(Color.parseColor("#80000000"));
                            rbPants.setTextColor(Color.parseColor("#80000000"));
                            rbShoes.setTextColor(Color.parseColor("#80000000"));
                            rbHeadwear.setTextColor(Color.parseColor("#80000000"));
                            rbALL.setBackgroundResource(R.drawable.tabitem_home_ranking);
                            rbTOP.setBackgroundResource(R.drawable.tabitem_home_ranking);
                            rbPants.setBackgroundResource(R.drawable.tabitem_home_ranking);
                            rbShoes.setBackgroundResource(R.drawable.tabitem_home_ranking);
                            rbHeadwear.setBackgroundResource(R.drawable.tabitem_home_ranking);
                            switch(position % 6) {
                                case 0:
                                case 1: rgCategory.check(R.id.radioButton); rbALL.setTextColor(Color.parseColor("#FFFFFF")); rbALL.setBackgroundResource(R.drawable.tabitem_home_ranking_tab); break;
                                case 2: rgCategory.check(R.id.radioButton2); rbTOP.setTextColor(Color.parseColor("#FFFFFF")); rbTOP.setBackgroundResource(R.drawable.tabitem_home_ranking_tab); break;
                                case 3: rgCategory.check(R.id.radioButton3); rbPants.setTextColor(Color.parseColor("#FFFFFF")); rbPants.setBackgroundResource(R.drawable.tabitem_home_ranking_tab); break;
                                case 4: rgCategory.check(R.id.radioButton4); rbShoes.setTextColor(Color.parseColor("#FFFFFF")); rbShoes.setBackgroundResource(R.drawable.tabitem_home_ranking_tab); break;
                                case 5: rgCategory.check(R.id.radioButton5); rbHeadwear.setTextColor(Color.parseColor("#FFFFFF")); rbHeadwear.setBackgroundResource(R.drawable.tabitem_home_ranking_tab); break;
                            }
                        }
                    });
                }
            }
        }, getActivity(), R.id.viewPager_home_ranking, item);
        task.execute("http://15.165.152.15//ShowRank.php");

        tvNotice = v.findViewById(R.id.textView_notice_home);
        tvFAQ = v.findViewById(R.id.textView_faq_home);

        tvNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NoticeActivity.class));
            }
        });
        tvFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FAQActivity.class));
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.pushAlarmFragment :
                if (((User) getActivity().getApplication()).getUID() == null) {
                    startActivityForResult(new Intent(getActivity(), LogInActivity.class), 3);
                } else {
                    startActivity(new Intent(getActivity(), PushAlarmActivity.class));
                }
                break;
            case R.id.cartFragment :
                if (((User) getActivity().getApplication()).getUID() == null) {
                    startActivityForResult(new Intent(getActivity(), LogInActivity.class), 2);
                } else {
                    startActivityForResult(new Intent(getActivity(), CartActivity.class), 4);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:                     // Login For Cart
                if (resultCode == RESULT_OK) {
                    startActivityForResult(new Intent(getActivity(), CartActivity.class), 4);
                }
                break;
            case 3:                     // Login For PushAlarm
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(getActivity(), PushAlarmActivity.class));
                }
                break;
            case 4:
                if (resultCode == RESULT_OK) {
                    MainActivity.navView.setSelectedItemId(R.id.favoriteFragment);
                }
        }
    }
}