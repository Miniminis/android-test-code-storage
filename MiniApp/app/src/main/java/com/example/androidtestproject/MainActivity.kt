package com.example.androidtestproject

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.androidtestproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Log.i("MainActivity", "onCreate")
        Timber.i("MainActivity :: onCreate")

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

//        val appLinkIntent = intent
//        val appLinkAction = appLinkIntent.action
//        val appLinkData = appLinkIntent.data
//
//        handleIntent(intent)

//        val intent = intent
//        Toast.makeText(this, "from deepLink : ${intent.action} :: ${intent.dataString} ", Toast.LENGTH_SHORT).show()

        navController = this.findNavController(R.id.main_nav_host)

        /* bottom navigationview */
        NavigationUI.setupActionBarWithNavController(this, navController)
        bottom_navigation.setOnNavigationItemSelectedListener(mNavItemSelectedListener)

        /* drawer layout + navigation view  */
//        drawerLayout = binding.drawerLayout
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
//
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            if(destination.id == controller.graph.startDestination) {
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//            } else {
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//            }
//        }
//        NavigationUI.setupWithNavController(binding.navView, navController)

        genKeyHash()

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            appLinkData?.lastPathSegment?.also { eventId ->
                Uri.parse("content://com.example.androidtestproject/share/")
                    .buildUpon()
                    .appendPath(eventId)
                    .build().also { appData ->
                        moveToShare(appData)
                    }
            }
        }
    }

    private fun moveToShare(appData: Uri?) {
        navController.navigate(R.id.shareFragment)
    }


    /* Navigation up
    - 뒤로가기 버튼에 대한 정의 : navController 를 넘겨주어 navigation 에서 정의한 대로 direction 이동 할 것을 정의
    - DrawerLayout : 상단에 화살표 버튼이 안보이면, drawer 방식으로 표현할 것인지를 설정
    */
    override fun onSupportNavigateUp(): Boolean {
        //return super.onSupportNavigateUp()

        /* bottom nav view */
        return NavigationUI.navigateUp(navController, null)

        /* drawer layout  */
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    private val mNavItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.menu_topic_list -> {
                navController.popBackStack()
                navController.navigate(R.id.mainFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_setting -> {
                navController.popBackStack()
                navController.navigate(R.id.settingFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun genKeyHash() {

        val packageInfo: PackageInfo =
            packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
                ?: return

        try {
            for (signature: Signature in packageInfo.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash(packageName) : ", packageName)
                Log.d("KeyHash : ", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }


    override fun onRestart() {
        super.onRestart()
//        Log.i("MainActivity", "onRestart")
        Timber.i("MainActivity :: onRestart")

    }

    override fun onStart() {
        super.onStart()
        Timber.i("MainActivity :: onStart")
    }

    override fun onResume() {
        super.onResume()
//        Log.i("MainActivity", "onResume")
        Timber.i("MainActivity :: onResume")

    }

    override fun onPause() {
        super.onPause()
//        Log.i("MainActivity", "onPause")
        Timber.i("MainActivity :: onPause")

    }

    override fun onStop() {
        super.onStop()
        Timber.i("MainActivity :: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
//        Log.i("MainActivity", "onDestroy")
        Timber.i("MainActivity :: onDestroy")

    }
}
