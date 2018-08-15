package com.cr.gankio.ui.gallery;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cr.gankio.Constants;
import com.cr.gankio.GlideApp;
import com.cr.gankio.R;
import com.cr.gankio.data.GankNewsListViewModel;
import com.cr.gankio.data.GankRepository;
import com.cr.gankio.data.database.GankNews;
import com.cr.gankio.ui.fragments.GankNewsListViewModelFactory;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import static com.thefinestartist.Base.getContext;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GalleryActivity extends AppCompatActivity {

    public static final String IMAGE_POSITION = "image_position";
    private GankNewsListViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private TextView textView;
    private ImageButton saveBtn;
    private GalleryActivity mInstance;
    private List<GankNews> items;
    private int mCount;
    private int position = 0;
    private int touchSlop;
    private RelativeLayout rl_black_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;
        touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);
        position = getIntent().getIntExtra(IMAGE_POSITION, 0);
        rl_black_bg = findViewById(R.id.rl_black_bg);
        textView = findViewById(R.id.image_count);
        saveBtn = findViewById(R.id.save_image);
        saveBtn.setOnClickListener(v -> {
            PermissionUtils.permission(PermissionConstants.STORAGE)
                    .callback(new PermissionUtils.FullCallback() {

                        @Override
                        public void onGranted(List<String> permissionsGranted) {
                            GlideApp.with(mInstance.getApplicationContext())
                                    .asBitmap()
                                    .load(items.get(position).getUrl())
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource,
                                                                    @Nullable Transition<? super
                                                                            Bitmap> transition) {
                                            ThreadUtils.executeByIo(new SaveImageTask(mInstance
                                                    .getApplicationContext(), resource));
                                        }
                                    });
                        }

                        @Override
                        public void onDenied(List<String> permissionsDeniedForever, List<String>
                                permissionsDenied) {

                        }
                    })
                    .request();
        });
        GankNewsListViewModelFactory factory = new GankNewsListViewModelFactory(GankRepository
                .getInstance(this), Constants.TYPE_WELFARE);
        mViewModel = ViewModelProviders.of(this, factory).get(GankNewsListViewModel.class);
        mViewModel.getGankNewsList(Constants.TYPE_WELFARE, 20, 1).observe(this, mGankNews -> {
            if (mAdapter == null) {
                items = mGankNews;
                mCount = mGankNews.size();
                mAdapter = new GalleryAdapter(this, mGankNews);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.scrollToPosition(position);
            }
        });
        mRecyclerView = findViewById(R.id.image_gallery);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,
                false));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                position = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                        .findFirstVisibleItemPosition();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(position + 1);
                stringBuilder.append("/");
                stringBuilder.append(mCount);
                textView.setText(stringBuilder.toString());
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            private float mDisplacementX;
            private float mDisplacementY;
            private float mInitialTy;
            private float mInitialTx;
            private boolean mTracking;

            private boolean canSwipe(RecyclerView rv) {
                View view = rv.getChildAt(0);
                PhotoView photoView = view.findViewById(R.id.welfare_img);
                if (photoView.getScale() != 1.0) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mDisplacementX = event.getRawX();
                        mDisplacementY = event.getRawY();

                        mInitialTy = rv.getTranslationY();
                        mInitialTx = rv.getTranslationX();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        float deltaX = event.getRawX() - mDisplacementX;
                        float deltaY = event.getRawY() - mDisplacementY;

                        //只有在不缩放的状态才能下滑
                        if(!canSwipe(rv)){
                            break;
                        }

                        if (deltaY > 0 &&
                                (Math.abs(deltaY) >  touchSlop * 2 &&
                                        Math.abs(deltaX) < Math.abs(deltaY) / 2)) {
                            mTracking = true;
                            return true;
                        }

                    default:
                        break;

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_MOVE:
                        saveBtn.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                        float deltaX = event.getRawX() - mDisplacementX;
                        float deltaY = event.getRawY() - mDisplacementY;

                        rv.setBackgroundColor(Color.TRANSPARENT);
                        rv.setTranslationY(mInitialTy + deltaY);
                        rv.setTranslationX(mInitialTx + deltaX);

                        float mScale = 1 - deltaY / 1000;
                        if (mScale < 0.3) {
                            mScale = 0.3f;
                        }
                        rv.setScaleX(mScale);
                        rv.setScaleY(mScale);
                        rl_black_bg.setAlpha(mScale);

                        break;
                    case MotionEvent.ACTION_UP:
                        float currentTranslateY = rv.getTranslationY();

                        if (currentTranslateY > 1000 / 3) {
                            finish();
                            break;
                        }
                        saveBtn.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        rv.setAlpha(1);
                        rv.setTranslationX(0);
                        rv.setTranslationY(0);
                        rv.setScaleX(1);
                        rv.setScaleY(1);
                        rv.setBackgroundColor(Color.BLACK);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);
    }


}
