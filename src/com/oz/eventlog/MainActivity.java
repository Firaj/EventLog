package com.oz.eventlog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends Activity implements  View.OnClickListener  {

	// navigator
	private DrawerLayout drawerLayout;
	private ListView listView, recentList;
	private String controls[];
	private LinearLayout layer;
	// Google Map
	private GoogleMap googleMap;
	MapView mMapView;

	//reside menu
	  private ResideMenu resideMenu;
	    private MainActivity mContext;
	    private ResideMenuItem itemHome;
	    private ResideMenuItem itemProfile;
	    private ResideMenuItem itemCalendar;
	    private ResideMenuItem itemSettings;
	
	// drawer
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	
	Button joinus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
        setUpMenu();


		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		controls = getResources().getStringArray(R.array.controls);

		navigator();
		
		joinus = (Button) findViewById(R.id.joinus);
		
		joinus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),JoinUsActivity.class);
				startActivity(intent);
			}
		});

		recentList = (ListView) findViewById(R.id.recentList);
		recentList.setAdapter(new NavigatorAdapter(this, R.layout.recentitem,
				controls));
		//recentList.setOnItemClickListener(this);

		mMapView = (MapView) findViewById(R.id.mapView);
		mMapView.onCreate(savedInstanceState);

		mMapView.onResume();// needed to get the map to display immediately

		try {
			MapsInitializer.initialize(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		googleMap = mMapView.getMap();
		googleMap.setMyLocationEnabled(true);

		googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		// latitude and longitude
		double latitude = 28.53033;
		double longitude = 83.877905;

		// create marker
		// MarkerOptions marker = new MarkerOptions().position(new
		// LatLng(latitude, longitude)).title("Hello Maps");

		// Changing marker icon
		// marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));

		// adding marker
		// googleMap.addMarker(marker);

		// CameraPosition cameraPosition = new CameraPosition.Builder().target(
		// new LatLng(28.53033,83.877905)).zoom(12).build();

		// googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

	}

	private void navigator() {
		// navigator
		layer = (LinearLayout) findViewById(R.id.layer);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};

		drawerArrow.setColor(R.color.gray);
		mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				drawerArrow, R.string.drawer_open, R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

		listView = (ListView) findViewById(R.id.drawerList);
		listView.setAdapter(new NavigatorAdapter(this, R.layout.navigator,
				controls));
		//listView.setOnItemClickListener(this);

	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (drawerLayout.isDrawerOpen(layer)) {
				drawerLayout.closeDrawer(layer);
			} else {
				drawerLayout.openDrawer(layer);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	 private void setUpMenu() {

	        // attach to current activity;
	        resideMenu = new ResideMenu(this);
	        resideMenu.setBackground(android.R.color.black);
	        resideMenu.attachToActivity(this);
	        resideMenu.setMenuListener(menuListener);
	        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
	        resideMenu.setScaleValue(0.6f);

	        // create menu items;
	        itemHome     = new ResideMenuItem(this, R.drawable.ic_launcher,     "Home");
	        itemProfile  = new ResideMenuItem(this, R.drawable.ic_launcher,  "Profile");
	        itemCalendar = new ResideMenuItem(this, R.drawable.ic_launcher, "Calendar");
	        itemSettings = new ResideMenuItem(this, R.drawable.ic_launcher, "Settings");

	        itemHome.setOnClickListener(this);
	        itemProfile.setOnClickListener(this);
	        itemCalendar.setOnClickListener(this);
	        itemSettings.setOnClickListener(this);
	        

	        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_RIGHT);
	        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_RIGHT);
	        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
	        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);

	        // You can disable a direction by setting ->
	         resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);

	        /*findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
	            }
	        });
	        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
	            }
	        });*/
	         

	         //resideMenu.addIgnoredView(listView);
	    }

	    @Override
	    public boolean dispatchTouchEvent(MotionEvent ev) {
	        return resideMenu.dispatchTouchEvent(ev);
	    }

	    @Override
	    public void onClick(View view) {

	        if (view == itemHome){
	            //changeFragment(new HomeFragment());
	        }else if (view == itemProfile){
	           // changeFragment(new ProfileFragment());
	        }else if (view == itemCalendar){
	            //changeFragment(new CalendarFragment());
	        }else if (view == itemSettings){
	           // changeFragment(new SettingsFragment());
	        }

	        resideMenu.closeMenu();
	    }

	    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
	        @Override
	        public void openMenu() {
	            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
	        }

	        @Override
	        public void closeMenu() {
	            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
	        }
	    };

	    

	    // What good method is to access resideMenu？
	    public ResideMenu getResideMenu(){
	        return resideMenu;
	    }

}
