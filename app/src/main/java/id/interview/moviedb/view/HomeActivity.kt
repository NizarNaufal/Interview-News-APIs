package id.interview.moviedb.view

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.interview.moviedb.R
import id.interview.moviedb.support.adapter.FragmentAdapter2
import id.interview.moviedb.support.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_default.*

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var defaultMenu: Menu? = null

    private lateinit var mainPagerAdapter: FragmentAdapter2
    private var lastPosition = 0
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    override fun onBackPressed() {
        if (lastPosition == 0) {

            if (doubleBackToExitPressedOnce) {
                finishAffinity()
            } else {
                this.doubleBackToExitPressedOnce = true
                showToast(getString(R.string.message_double_klik_to_close))
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }

        } else {
            val defaultScreen = MainScreen.HOME
            scrollToScreen(defaultScreen)
            selectBottomNavigationViewMenuItem(defaultScreen.menuItemId)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        defaultMenu = menu
//        showBadgeMenu(false)
        return super.onPrepareOptionsMenu(menu)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_toolbar, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.menu_chat -> showToast(getString(R.string.message_feature_coming_soon))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        getMainScreenForMenuItem(menuItem.itemId)?.let {
            scrollToScreen(it)
            return true
        }
        return false
    }

    private fun initView() {
        initToolbar()

//        val isGetGift = intent?.getBooleanExtra("isGetGift", false)
//
//        if (isGetGift == true) {
//            runOnUiThread {
//                BottomSheetGift().show(supportFragmentManager, "get gift")
//            }
//        }

        bottom_navigation?.itemIconTintList = null

        mainPagerAdapter = FragmentAdapter2(supportFragmentManager)
        mainPagerAdapter.setItems(
            arrayListOf(
                MainScreen.HOME,
                MainScreen.SHOP,
                MainScreen.DEALS,
                MainScreen.NEARBY
            )
        )

        val defaultScreen = MainScreen.HOME
        scrollToScreen(defaultScreen)
        selectBottomNavigationViewMenuItem(defaultScreen.menuItemId)

        bottom_navigation?.setOnNavigationItemSelectedListener(this)

        view_pager?.adapter = mainPagerAdapter

        view_pager?.disableSwipePaging()

        view_pager?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val selectedScreen = mainPagerAdapter.getItems()[position]
                selectBottomNavigationViewMenuItem(selectedScreen.menuItemId)
            }
        })

    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
    }

    private fun scrollToScreen(mainScreen: MainScreen) {
        val screenPosition = mainPagerAdapter.getItems().indexOf(mainScreen)
        lastPosition = screenPosition
        if (screenPosition != view_pager?.currentItem) {
            view_pager?.currentItem = screenPosition
        }
    }

    private fun selectBottomNavigationViewMenuItem(@IdRes menuItemId: Int) {
        bottom_navigation?.setOnNavigationItemSelectedListener(null)
        bottom_navigation?.selectedItemId = menuItemId
        bottom_navigation?.setOnNavigationItemSelectedListener(this)
    }
}
