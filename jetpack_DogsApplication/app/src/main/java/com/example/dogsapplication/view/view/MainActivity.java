package com.example.dogsapplication.view.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dogsapplication.R;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_SEND_SMS = 3444;
    private Fragment fragment;

    /* 상단에 back button 구현 */
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* main view 생성될때, navcontroller 같이 설정
        * 1. 먼저 fragment 캐스팅
        * 2. 캐스팅 한 fragment 와 main 의 navcontroller 연결
        * 3. navigation direction 이 이동하면서 actionbar 에  표시되는 텍스트 변경
        * the action bar will automatically be updated when the destination changes */
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        /* Navigation up button : 뒤로가기 버튼에 대한 정의 : navController 를 넘겨주어 navigation 에서 정의한 대로 direction 이동 할 것을 정의
        DrawerLayout : 상단에 화살표 버튼이 안보이면, drawer 방식으로 표현할 것인지를 설정하는 과정
        */
        return NavigationUI.navigateUp(navController, (DrawerLayout) null);
    }

    /* sms permission check from detail fragment
    * ContextCompat.checkSelfPermission : permission 여부를 반환
    * 만약 승인하지 않았다면 - PackageManager.PERMISSION_DENIED라면,
    * -
    * 승인이라면: PackageManager.PERMISSION_GRANTED
    * -
    * */
    public void checkSmsPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                new AlertDialog.Builder(this)
                        .setTitle("Send SMS Permission")
                        .setMessage("This app requires access to send an SMS")
                        .setPositiveButton("Ask me", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {    /* 허용을 하면 */
                                requestSmsPermission();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {    /* 거부를 하면 */
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notifyDetailFragment(false);
                            }
                        })
                        .show();
            } else {
                requestSmsPermission();
            }
        } else {
            notifyDetailFragment(true);
        }
    }

    /* request sms permission  */
    private void requestSmsPermission() {
        Log.d("권한요청", "권한요청");
        String[] permissions = {Manifest.permission.SEND_SMS};
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_SEND_SMS);
    }

    /* ActivityCompat.requestPermissions 의 callback */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_SEND_SMS: {
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    notifyDetailFragment(true);
                } else {
                    notifyDetailFragment(false);
                }
                break;
            }
        }
    }

    /* fragment 에게 user의 permission 결과를 알려줌 */
    private void notifyDetailFragment(Boolean permissionGrented) {
        /* 현재 진행중인 fragment 를 반환하여
        *  permission 요청을 보낸 쪽인 DetailFragment 쪽의
        * onPermissionResult 매서드를 통해 사용자의 permission 결과를 알려준다. */
        Fragment activeFragment = fragment.getChildFragmentManager().getPrimaryNavigationFragment();
        if(activeFragment instanceof DetailFragment) {
            ((DetailFragment) activeFragment).onPermissionResult(permissionGrented);
        }
    }

}
