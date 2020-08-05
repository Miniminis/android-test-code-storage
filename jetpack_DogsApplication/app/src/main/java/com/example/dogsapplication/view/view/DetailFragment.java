package com.example.dogsapplication.view.view;


import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.palette.graphics.Palette;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.dogsapplication.R;
import com.example.dogsapplication.databinding.FragmentDetailBinding;
import com.example.dogsapplication.databinding.SendSmsDialogBinding;
import com.example.dogsapplication.view.model.DogBreed;
import com.example.dogsapplication.view.model.DogPalette;
import com.example.dogsapplication.view.model.SmsInfo;
import com.example.dogsapplication.view.util.Util;
import com.example.dogsapplication.view.viewmodel.DetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    private int dogId;
    private DetailViewModel detailViewModel;
    private FragmentDetailBinding fragmentDetailBinding;

    private DogBreed currDog;

    /* fragment 는 user 에게 직접 permission 을 물어볼 수 있는 권한이 없으므로,
    main activity 에게 물어보라고 전달해줘야함 */
    private boolean sendSmsStarted = false;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        this.fragmentDetailBinding = binding;
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* dogId 값 받아오기
        *  Bundle
        *  getArguments : action 에 의해 전달되는 argument
        * - argument 에 의해 전달되는 dogid 가져오기
        * detailViewModel 정의
        * - fetch() 실행
        *  */
        if(getArguments() != null) {
            dogId = DetailFragmentArgs.fromBundle(getArguments()).getDogId();
        }

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailViewModel.fetch(dogId);

        observeViewModel();
    }

    /* viewmodel 내부에 정의된 dogLiveData 를 observe 하면서 데이터 변경 시에 바로 ui 에 적용되도록 함 */
    private void observeViewModel() {
        detailViewModel.dogLiveData.observe(this, dogBreed -> {
            if(dogBreed != null && dogBreed instanceof DogBreed) {
                currDog = dogBreed;
                /* xml 화면에 바인딩된 데이터 setting  */
                fragmentDetailBinding.setDog(dogBreed);
                if(dogBreed.getImageUrl() != null) {
                    /* detail page 배경색 data binding */
                    setupBackgroundColor(dogBreed.getImageUrl());
                }
            }
        });
    }

    /* detail page 배경색 data binding */
    private void setupBackgroundColor (String url) {
        Glide.with(this).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@Nullable Palette palette) {
                        int intColor = palette.getLightMutedSwatch().getRgb();
                        DogPalette dogPalette = new DogPalette(intColor);
                        fragmentDetailBinding.setPalette(dogPalette);
                    }
                });
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    /* menu inflation */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }

    /* menu 선택시 동작 정의 - 경우의수 만큼 */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send_sms: {
                /* sms 보내기 메뉴 클릭 시
                * 만약 smsStarted 가 false 면 true 로 바꿔주고
                * 이 프레그먼트의 엑티비티인 main activity 에서 permission 체크를 해준다.
                * (fragment 는 permission 을 유저에게 direct로 물어볼 수 있는 권한이 없음)*/
                if(!sendSmsStarted) {
                    sendSmsStarted = true;
                    ((MainActivity)getActivity()).checkSmsPermission();
                }
                break;
            }
            case R.id.action_share: {
                //Toast.makeText(getContext(), "Action share content ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this dog breed");
                intent.putExtra(Intent.EXTRA_TEXT, currDog.getDogBreed() + " bred for "+ currDog.getBredFore());
                intent.putExtra(Intent.EXTRA_STREAM, currDog.getImageUrl());
                startActivity(Intent.createChooser(intent,"Share with"));
                break;
            }
        }
        return super.onOptionsItemSelected(item);   //이도저도 아닌 상황의 default 값
    }

    /* 사용자의 permission 결과에 따라 행동 정의
    * 허용 :
    * - sms binding 통해 layout 에 보낼 데이터 binding
    * - alert dialog 팝업으로 만들어서 사용자에게 띄워주기
    * - 팝업에서 사용자가 보내기를 눌렀을때, sendSms() 호출
    * sendSms()
    * -
    * */
    public void onPermissionResult(Boolean permissionGranted) {
        if(isAdded() && sendSmsStarted && permissionGranted) {
            SmsInfo smsInfo = new SmsInfo("mini", currDog.getDogBreed()+ " bred for " + currDog.getBredFore(), currDog.getImageUrl());

            SendSmsDialogBinding smsDialogBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    R.layout.send_sms_dialog,
                    null,
                    false
            );

            new AlertDialog.Builder(getContext())
                    .setView(smsDialogBinding.getRoot())
                    .setPositiveButton("Send SMS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(!smsDialogBinding.smsDestination.getText().toString().isEmpty()) {
                                smsInfo.setTo(smsDialogBinding.smsDestination.getText().toString());
                                sendSms(smsInfo);
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
            sendSmsStarted = false;

            smsDialogBinding.setSmsinfo(smsInfo);
        }
    }

    /* SMS 보내는 상황 정의 : SmsManager 에게 지금 어플리케이션에서 받은 permission 을 그대로 전달
    * A PendingIntent
    * is a token that you give to a foreign application
    * (e.g. NotificationManager, AlarmManager, Home Screen AppWidgetManager, or other 3rd party applications),
    *  which allows the foreign application to use your application's permissions to execute a predefined piece of code.
    *
    * If you give the foreign application an Intent, it will execute your Intent with its own permissions.
    * But if you give the foreign application a PendingIntent,
    * that application will execute your Intent using your application's permission.
     */
    private void sendSms(SmsInfo smsInfo) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(smsInfo.getTo(), null, smsInfo.getText(), pendingIntent, null);
    }
}
