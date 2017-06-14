package com.example.cargas.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cargas.R;
import com.example.cargas.communication.QuerySessionId;
import com.example.cargas.fragment.BlackSmokeFragment;
import com.example.cargas.fragment.HomeFragment;
import com.example.cargas.fragment.RandomInspectionFragment;
import com.example.cargas.fragment.RoadInspectionFragment;
import com.example.cargas.fragment.SettingsFragment;
import com.example.cargas.fragment.SuperviseFragment;
import com.example.cargas.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends FragmentActivity implements OnClickListener {

    private LinearLayout mHomePage;
    private LinearLayout mBlackSmoke;
    private LinearLayout mRoadInspection;
    private LinearLayout mRadnomInspection;
    private LinearLayout mSupervise;
    private LinearLayout mSettings;
    private LinearLayout mPhotograph;
    // private ViewPager mMainViewPager;
    // private FragmentPagerAdapter mMainAdapter;
    private BlackSmokeFragment mBlack;
    private HomeFragment mHome;
    private RandomInspectionFragment mRandom;
    private RoadInspectionFragment mRoad;
    private SuperviseFragment mSuper;
    private SettingsFragment mSetting;
    // private BlackSmokeRegiseter mBlackRegister;
    // private ArrayList<Fragment> mLi = new ArrayList<Fragment>();
    private ImageView home_icon;
    private ImageView blacksmoke_icon;
    private ImageView trafficstops_icon;
    private ImageView spotcheck_icon;
    private ImageView regulation_icon;
    private ImageView setting_icon;
    private TextView tv_home;
    private TextView tv_black;
    private TextView tv_road;
    private TextView tv_random;
    private TextView tv_super;
    private TextView tv_setting;
    private TextView tv_photograph;

    private static String srcPath;
    private int REQUEST_CODE_CARD = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // requestWindowFeature(Window.FEATURE_NO_TITLE);

        // //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 去掉虚拟按键全屏显示
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        // 设置屏幕始终在前面，不然点击鼠标，重新出现虚拟按键
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                        // bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
        );

        // 定期更新SessionId
        QuerySessionId.instance(getApplicationContext());
        setContentView(R.layout.activity_main);

        initViews();
    }

    /**
     * 初始化控件
     */
    private void initViews() {

        mHomePage = (LinearLayout) findViewById(R.id.home_page);
        mBlackSmoke = (LinearLayout) findViewById(R.id.black_smoke);
        mRoadInspection = (LinearLayout) findViewById(R.id.road_inspection);
        mRadnomInspection = (LinearLayout) findViewById(R.id.randow_inspection);
        mSupervise = (LinearLayout) findViewById(R.id.supervise);
        mSettings = (LinearLayout) findViewById(R.id.settings);
        mPhotograph = (LinearLayout) findViewById(R.id.photograph);

        home_icon = (ImageView) findViewById(R.id.home_icon);
        blacksmoke_icon = (ImageView) findViewById(R.id.blacksmoke_icon);
        trafficstops_icon = (ImageView) findViewById(R.id.trafficstops_icon);
        spotcheck_icon = (ImageView) findViewById(R.id.spotcheck_icon);
        regulation_icon = (ImageView) findViewById(R.id.regulation_icon);
        setting_icon = (ImageView) findViewById(R.id.setting_icon);

        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_black = (TextView) findViewById(R.id.tv_black);
        tv_road = (TextView) findViewById(R.id.tv_road);
        tv_random = (TextView) findViewById(R.id.tv_random);
        tv_super = (TextView) findViewById(R.id.tv_super);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        tv_photograph = (TextView) findViewById(R.id.tv_photograph);

        mHome = new HomeFragment(this);
        mBlack = new BlackSmokeFragment(this);
        mRandom = new RandomInspectionFragment(this);
        mRoad = new RoadInspectionFragment(this);
        mSetting = new SettingsFragment(this);
        mSuper = new SuperviseFragment(this);

        // mBlackRegister = new BlackSmokeRegiseter(this);

        mHomePage.setOnClickListener(this);
        mBlackSmoke.setOnClickListener(this);
        mRoadInspection.setOnClickListener(this);
        mRadnomInspection.setOnClickListener(this);
        mSupervise.setOnClickListener(this);
        mSettings.setOnClickListener(this);
        mPhotograph.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "IdCards" + ".jpg";
                srcPath = Utils.Dir + name;
                Intent photoit = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri = Uri.fromFile(new File(srcPath));
                photoit.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(photoit, REQUEST_CODE_CARD);
            }
        });

        // 默认显示首页
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_framelayout, mHome).commit();
    }

    /*
     * (non-Javadoc) 控件点击事件
     */
    @Override
    public void onClick(View v) {

        Fragment frag = null;

        switch (v.getId()) {
            case R.id.home_page:
                home_icon.setImageResource(R.drawable.home_icon_pressed);
                blacksmoke_icon.setImageResource(R.drawable.blacksmoke_icon_normal);
                trafficstops_icon
                        .setImageResource(R.drawable.trafficstops_icon_normal);
                spotcheck_icon.setImageResource(R.drawable.spotcheck_icon_normal);
                regulation_icon.setImageResource(R.drawable.regulation_icon_normal);
                setting_icon.setImageResource(R.drawable.setting_icon_normal);

                // 设置点击的背景效果
                mSettings.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mHomePage.setBackgroundColor(getResources().getColor(
                        R.color.myblue1));
                mBlackSmoke.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mRoadInspection.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mRadnomInspection.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mSupervise.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));

                // 颜色
                tv_home.setTextColor(getResources().getColor(R.color.mywhite));
                tv_black.setTextColor(getResources().getColor(R.color.mygray));
                tv_road.setTextColor(getResources().getColor(R.color.mygray));
                tv_random.setTextColor(getResources().getColor(R.color.mygray));
                tv_super.setTextColor(getResources().getColor(R.color.mygray));
                tv_setting.setTextColor(getResources().getColor(R.color.mygray));
                tv_photograph.setTextColor(getResources().getColor(R.color.mygray));

                frag = mHome;

                break;

            case R.id.black_smoke:

                home_icon.setImageResource(R.drawable.home_icon_normal);

                blacksmoke_icon
                        .setImageResource(R.drawable.blacksmoke_icon_pressed);
                trafficstops_icon
                        .setImageResource(R.drawable.trafficstops_icon_normal);
                spotcheck_icon.setImageResource(R.drawable.spotcheck_icon_normal);
                regulation_icon.setImageResource(R.drawable.regulation_icon_normal);
                setting_icon.setImageResource(R.drawable.setting_icon_normal);

                // 设置点击的背景效果
                mSettings.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mHomePage.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mBlackSmoke.setBackgroundColor(getResources().getColor(
                        R.color.myblue1));
                mRoadInspection.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mRadnomInspection.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mSupervise.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));

                // 颜色
                tv_home.setTextColor(getResources().getColor(R.color.mygray));
                tv_black.setTextColor(getResources().getColor(R.color.mywhite));
                tv_road.setTextColor(getResources().getColor(R.color.mygray));
                tv_random.setTextColor(getResources().getColor(R.color.mygray));
                tv_super.setTextColor(getResources().getColor(R.color.mygray));
                tv_setting.setTextColor(getResources().getColor(R.color.mygray));
                tv_photograph.setTextColor(getResources().getColor(R.color.mygray));
                frag = mBlack;
                break;

            case R.id.road_inspection:

                home_icon.setImageResource(R.drawable.home_icon_normal);

                blacksmoke_icon.setImageResource(R.drawable.blacksmoke_icon_normal);
                spotcheck_icon.setImageResource(R.drawable.spotcheck_icon_normal);
                regulation_icon.setImageResource(R.drawable.regulation_icon_normal);
                setting_icon.setImageResource(R.drawable.setting_icon_normal);
                trafficstops_icon
                        .setImageResource(R.drawable.trafficstops_icon_pressed);

                // 设置点击的背景效果
                mSettings.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mHomePage.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mBlackSmoke.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mRoadInspection.setBackgroundColor(getResources().getColor(
                        R.color.myblue1));
                mRadnomInspection.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mSupervise.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));

                // 颜色
                tv_home.setTextColor(getResources().getColor(R.color.mygray));
                tv_black.setTextColor(getResources().getColor(R.color.mygray));
                tv_road.setTextColor(getResources().getColor(R.color.mywhite));
                tv_random.setTextColor(getResources().getColor(R.color.mygray));
                tv_super.setTextColor(getResources().getColor(R.color.mygray));
                tv_setting.setTextColor(getResources().getColor(R.color.mygray));
                tv_photograph.setTextColor(getResources().getColor(R.color.mygray));

                frag = mRoad;

                break;

            case R.id.randow_inspection:
                spotcheck_icon.setImageResource(R.drawable.spotcheck_icon_pressed);

                home_icon.setImageResource(R.drawable.home_icon_normal);

                blacksmoke_icon.setImageResource(R.drawable.blacksmoke_icon_normal);
                regulation_icon.setImageResource(R.drawable.regulation_icon_normal);
                setting_icon.setImageResource(R.drawable.setting_icon_normal);
                trafficstops_icon
                        .setImageResource(R.drawable.trafficstops_icon_normal);

                // 设置点击的背景效果
                mSettings.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mHomePage.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mBlackSmoke.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mRoadInspection.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mRadnomInspection.setBackgroundColor(getResources().getColor(
                        R.color.myblue1));
                mSupervise.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));

                // 颜色
                tv_home.setTextColor(getResources().getColor(R.color.mygray));
                tv_black.setTextColor(getResources().getColor(R.color.mygray));
                tv_road.setTextColor(getResources().getColor(R.color.mygray));
                tv_random.setTextColor(getResources().getColor(R.color.mywhite));
                tv_super.setTextColor(getResources().getColor(R.color.mygray));
                tv_setting.setTextColor(getResources().getColor(R.color.mygray));
                tv_photograph.setTextColor(getResources().getColor(R.color.mygray));

                frag = mRandom;
                break;

            case R.id.supervise:
                regulation_icon
                        .setImageResource(R.drawable.regulation_icon_pressed);

                spotcheck_icon.setImageResource(R.drawable.spotcheck_icon_normal);

                home_icon.setImageResource(R.drawable.home_icon_normal);

                blacksmoke_icon.setImageResource(R.drawable.blacksmoke_icon_normal);
                setting_icon.setImageResource(R.drawable.setting_icon_normal);
                trafficstops_icon
                        .setImageResource(R.drawable.trafficstops_icon_normal);

                // 设置点击的背景效果
                mSettings.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mHomePage.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mBlackSmoke.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mRoadInspection.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mRadnomInspection.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mSupervise.setBackgroundColor(getResources().getColor(
                        R.color.myblue1));

                // 颜色
                tv_home.setTextColor(getResources().getColor(R.color.mygray));
                tv_black.setTextColor(getResources().getColor(R.color.mygray));
                tv_road.setTextColor(getResources().getColor(R.color.mygray));
                tv_random.setTextColor(getResources().getColor(R.color.mygray));
                tv_super.setTextColor(getResources().getColor(R.color.mywhite));
                tv_setting.setTextColor(getResources().getColor(R.color.mygray));
                tv_photograph.setTextColor(getResources().getColor(R.color.mygray));

                frag = mSuper;

                break;

            case R.id.settings:
                setting_icon.setImageResource(R.drawable.setting_icon_pressed);

                // 设置图片的点击效果
                regulation_icon.setImageResource(R.drawable.regulation_icon_normal);

                spotcheck_icon.setImageResource(R.drawable.spotcheck_icon_normal);

                home_icon.setImageResource(R.drawable.home_icon_normal);

                blacksmoke_icon.setImageResource(R.drawable.blacksmoke_icon_normal);
                trafficstops_icon
                        .setImageResource(R.drawable.trafficstops_icon_normal);

                // 设置点击的背景效果
                mSettings.setBackgroundColor(getResources().getColor(
                        R.color.myblue1));
                mHomePage.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mBlackSmoke.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mRoadInspection.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mRadnomInspection.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));
                mSupervise.setBackgroundColor(getResources().getColor(
                        R.color.mywhite));

                // 颜色
                tv_home.setTextColor(getResources().getColor(R.color.mygray));
                tv_black.setTextColor(getResources().getColor(R.color.mygray));
                tv_road.setTextColor(getResources().getColor(R.color.mygray));
                tv_random.setTextColor(getResources().getColor(R.color.mygray));
                tv_super.setTextColor(getResources().getColor(R.color.mygray));
                tv_setting.setTextColor(getResources().getColor(R.color.mywhite));
                tv_photograph.setTextColor(getResources().getColor(R.color.mygray));
                frag = mSetting;
                break;
            default:
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_framelayout, frag).commit();
    }

    public BlackSmokeFragment getmBlack() {
        return mBlack;
    }

    public void setmBlack(BlackSmokeFragment mBlack) {
        this.mBlack = mBlack;
    }

    public HomeFragment getmHome() {
        return mHome;
    }

    public void setmHome(HomeFragment mHome) {
        this.mHome = mHome;
    }

    public RandomInspectionFragment getmRandom() {
        return mRandom;
    }

    public void setmRandom(RandomInspectionFragment mRandom) {
        this.mRandom = mRandom;
    }

    public RoadInspectionFragment getmRoad() {
        return mRoad;
    }

    public void setmRoad(RoadInspectionFragment mRoad) {
        this.mRoad = mRoad;
    }

    public SuperviseFragment getmSuper() {
        return mSuper;
    }

    public void setmSuper(SuperviseFragment mSuper) {
        this.mSuper = mSuper;
    }

    public SettingsFragment getmSetting() {
        return mSetting;
    }

    public void setmSetting(SettingsFragment mSetting) {
        this.mSetting = mSetting;
    }

    // public BlackSmokeRegiseter getmBlackRegister() {
    // return mBlackRegister;
    // }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Utils.BLACK_PICTURE:
                mBlack.getmBlackRegister().onActivityResult(requestCode,
                        resultCode, data);

                break;

            case Utils.Road_PICTURE:
                mRoad.getRoadRegister().onActivityResult(requestCode, resultCode,
                        data);

                break;
            case Utils.Random_PICTURE:
                mRandom.getRandomInspectionDetail()
                        .getRandomInspectionDetailRegister()
                        .onActivityResult(requestCode, resultCode, data);
                break;

            case Utils.Supervise_PICTURE:
                mSuper.getSuperviseRegiseter().onActivityResult(requestCode,
                        resultCode, data);

                break;

            case Utils.BLACK_TAKE_PICTURE:
                mBlack.getmBlackRegister().onActivityResult(requestCode,
                        resultCode, data);

                break;

            case Utils.ROAD_TAKE_PICTURE:
                mRoad.getRoadRegister().onActivityResult(requestCode, resultCode,
                        data);
                break;

            case Utils.SUPERVISE_TAKE_PICTURE:
                mSuper.getSuperviseRegiseter().onActivityResult(requestCode,
                        resultCode, data);
                break;

            case Utils.Random_TAKE_PICTURE:
                mRandom.getRandomInspectionDetail()
                        .getRandomInspectionDetailRegister()
                        .onActivityResult(requestCode, resultCode, data);
                break;

            case Utils.BLACK_TAKE_VIDEO:
                mBlack.getmBlackRegister().onActivityResult(requestCode,
                        resultCode, data);

                break;

            case Utils.ROAD_TAKE_VIDEO:
                mRoad.getRoadRegister().onActivityResult(requestCode, resultCode,
                        data);
                break;

            case Utils.SUPERVISE_TAKE_VIDEO:
                mSuper.getSuperviseRegiseter().onActivityResult(requestCode,
                        resultCode, data);
                break;

            case Utils.Random_TAKE_VIDEO:
                mRandom.getRandomInspectionDetail()
                        .getRandomInspectionDetailRegister()
                        .onActivityResult(requestCode, resultCode, data);
                break;
            default:
                break;
        }
        if (requestCode == REQUEST_CODE_CARD && resultCode == RESULT_OK) {
            FileInputStream is = null;
            try {
                is = new FileInputStream(srcPath);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                Bitmap newbitmap = ThumbnailUtils.extractThumbnail(bitmap, 1024, 1024);
                Utils.saveBitmap(srcPath, newbitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
