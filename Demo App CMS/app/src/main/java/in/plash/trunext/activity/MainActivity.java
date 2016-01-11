package in.plash.trunext.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.backend.BookmarkHelper;
import in.plash.trunext.backend.LoginHelper;
import in.plash.trunext.backend.NavDrawerHelper;
import in.plash.trunext.backend.NotificationHelper;
import in.plash.trunext.fragments.ArticleFeedFragment;
import in.plash.trunext.fragments.IssueFeedFragment;
import in.plash.trunext.responceObjects.LoginResponse;
import in.plash.trunext.responceObjects.ResponseBookMarkIds;
import in.plash.trunext.util.L;
import in.plash.trunext.util.RoundedTransformation;
import ly.count.android.sdk.Countly;


public class MainActivity extends SuperActivity
        implements View.OnClickListener {

    private static TextView toolbarTitle;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView bookMarks, emailUS, aboutUS, userName;
    ImageView watsAppShare, profilePic;
    TextView logout;
    ImageButton drawerCloser;
    public static ImageButton toolbarLeft, toolbarHome;
    private DrawerLayout mDrawerLayout;
    ResponseBookMarkIds bookMarkIds;
    BookmarkHelper bookmarkHelper;
    LoginHelper loginHelper;
    Context context;
    ToggleButton toggleButton;
    private NotificationHelper notificationHelper;
    private LoginResponse loginResponse;
    private CollapsingToolbarLayout collapsing_container;

    @Override
    protected void onStart() {
        super.onStart();
        Countly.sharedInstance().onStart();
        L.m("countly", " Session Started");
        // Need to set userid from db for global use
        LoginHelper.setUserId();


        bookmarkHelper = new BookmarkHelper();
        loginHelper = new LoginHelper();


        bookmarkHelper.doBookMarksIdsCall();
        bookmarkHelper.updateBookMarkDataBase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        context = this;


        notificationHelper = new NotificationHelper();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        try {
            getSupportActionBar().setTitle("");
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Toolbar content
        toolbarLeft = (ImageButton) toolbar.findViewById(R.id.btn_toolbar_left);
        toolbarHome = (ImageButton) toolbar.findViewById(R.id.btn_toolbar_home);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_forbes);

        // Navigation Drawer content
        userName = (TextView) findViewById(R.id.nav_user_name);
        profilePic = (ImageView) findViewById(R.id.btn_nav_pro_pic);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
        emailUS = (TextView) findViewById(R.id.nav_email_us);
        aboutUS = (TextView) findViewById(R.id.nav_about_us);
        watsAppShare = (ImageView) findViewById(R.id.nav_watsapp);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        bookMarks = (TextView) findViewById(R.id.txt_nav_bookmark);
        logout = (TextView) findViewById(R.id.txt_nav_logout);
        drawerCloser = (ImageButton) findViewById(R.id.btn_nav_red_cross);


        toggleButton.setOnClickListener(this);
        emailUS.setOnClickListener(this);
        aboutUS.setOnClickListener(this);
        watsAppShare.setOnClickListener(this);
        bookMarks.setOnClickListener(this);
        logout.setOnClickListener(this);
        toolbarLeft.setOnClickListener(this);
        toolbarHome.setOnClickListener(this);
        drawerCloser.setOnClickListener(this);

        loginResponse = LoginHelper.getUserDetails();
        if (loginResponse != null) {

            try {
                if (!loginResponse.getJsonobject().getUserName().equals("null"))
                    userName.setText(loginResponse.getJsonobject().getUserName());

                String imageUrl = loginResponse.getJsonobject().getAvatar();

                if (imageUrl != null) {
                    Picasso.with(this)
                            .load(imageUrl)
                            .transform(new RoundedTransformation(50, 4))
                            .resizeDimen(R.dimen.nav_drw_190px, R.dimen.nav_drw_190px)
                            .centerCrop()
                            .into(profilePic);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        fragment = new IssueFeedFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragment.setRetainInstance(true);
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            //   fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.face_out);
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.content_frame, fragment);

        }
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.nav_email_us:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                NavDrawerHelper.sendEmail(context);
                break;
            case R.id.nav_about_us:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lmt-lss.com"));
                startActivity(browserIntent);
                break;
            case R.id.toggleButton1:
                break;
            case R.id.nav_watsapp:
                notificationHelper.shareApp(context, "http://www.dsij.in/");
                break;
            case R.id.btn_nav_red_cross:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.txt_nav_bookmark:
                openBookmarked();
                break;
            case R.id.btn_toolbar_left:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.btn_toolbar_home:
                goToHomeScreen();
                break;
            case R.id.txt_nav_logout:
                loginHelper.doLogoutNetworkCall();
                loginHelper.resetDatabase();
                Constants.isANNONYMOUS = false;
                goToLoginActivity();
                //Write logout for facebook
                LoginManager.getInstance().logOut();
                mDrawerLayout.closeDrawers();
                break;
        }


    }


    private void goToHomeScreen() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new IssueFeedFragment()).addToBackStack(null).commit();
    }


    private void goToLoginActivity() {
       // Intent intent = new Intent(this, LoginForbesActivity.class);
         Intent intent = new Intent(this, LoginInsideActivity.class);
        // Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public final static void setHomeButtonVisibility(boolean flag) {

        if (flag) {
            toolbarHome.setVisibility(View.VISIBLE);
        } else {
            toolbarHome.setVisibility(View.GONE);
        }

    }

    public final static void setNavButtonVisibility(boolean flag) {

        if (flag) {
            toolbarLeft.setVisibility(View.VISIBLE);
        } else {
            toolbarLeft.setVisibility(View.GONE);
        }

    }

    public final static void setToolBarTitle(String text) {
        //  toolbarTitle.setText(text);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (getFragmentManager().getBackStackEntryCount() <= 1) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        Countly.sharedInstance().onStop();
        L.m("countly", " Session Ended");
    }


    private void openBookmarked() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        bookMarkIds = BookmarkHelper.getBookMarkIds();
        if (bookMarkIds != null) {

            int j = bookMarkIds.getList().size();

            if (j > 0) {
                Bundle bundle = new Bundle();
                bundle.putString("bookMark", "Hi");
                Fragment fragment = new ArticleFeedFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                if (fragment.isAdded()) {
                    transaction.show(fragment);
                } else {
                    //  fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.face_out);
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                }
                transaction.commit();
            } else {
                L.T(MainActivity.this, "No Articles BookMarked");
            }
        } else {
            L.T(MainActivity.this, "No Articles BookMarked");
        }
    }


}
