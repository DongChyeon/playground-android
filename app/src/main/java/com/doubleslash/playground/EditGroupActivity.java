package com.doubleslash.playground;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doubleslash.playground.databinding.ActivityCreateGroupBinding;
import com.doubleslash.playground.retrofit.RetrofitClient;
import com.doubleslash.playground.retrofit.dto.CreateTeamDTO;
import com.doubleslash.playground.retrofit.dto.response.Group_create_responseDTO;
import com.doubleslash.playground.retrofit.dto.response.Team_info_responseDTO;

import java.util.Calendar;

import okhttp3.MultipartBody;

public class EditGroupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityCreateGroupBinding binding;
    DatePickerDialog Dpicker;
    TimePickerDialog Tpicker;
    Uri selectedImageUri;
    final Calendar cal = Calendar.getInstance();
    private RetrofitClient retrofitClient;
    public static String API_URL = "http://222.251.129.150/";
    Team_info_responseDTO team_info_responseDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUI();    // 소모임 생성할 때 넣었던 정보 가져옴
        initUI();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {  //edittext 포커싱 문제 해결하기 위해
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    private void initUI() {
        binding.registerPicIv.setOnClickListener(v -> { // 소모임 사진
            openGallery();
        });

        binding.GroupNameEdit.addTextChangedListener(new TextWatcher() {    // 소모임 이름
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text1 = s.toString();
                String text2 = binding.infoEdit.getText().toString();
                String text3 = binding.locationEdit.getText().toString();
                if (text1.length() > 0 && text2.length() > 0 && text3.length() > 0){
                    onCreateBtn();
                }
                else {
                    offCreateBtn();
                }
            }
        });

        binding.checkBtn.setOnClickListener(v -> {
            //중복확인하기
        });

        binding.createBtn.setOnClickListener(v -> {
            retrofitClient = RetrofitClient.getInstance();

            MultipartBody.Part teamImage = retrofitClient.prepareFilePart("file", selectedImageUri, getApplicationContext());
            String maxMember = binding.memberSpinner.getSelectedItem().toString();
            maxMember = maxMember.substring(0, maxMember.length() - 1);
            Integer maxMemberCount = Integer.parseInt(maxMember);

            CreateTeamDTO createTeamDTO = new CreateTeamDTO();
            createTeamDTO.setTeamImageUrl(selectedImageUri.toString());
            createTeamDTO.setCategory(binding.categorySpinner.getSelectedItem().toString());
            createTeamDTO.setLocation(binding.locationEdit.getText().toString());
            createTeamDTO.setContent(binding.infoEdit.getText().toString());
            createTeamDTO.setMaxMemberSize(maxMemberCount);
            createTeamDTO.setName(binding.GroupNameEdit.getText().toString());
            createTeamDTO.setStartDate(binding.startDate.getText().toString());
            createTeamDTO.setEndDate(binding.endDate.getText().toString());

            retrofitClient.update_group(createTeamDTO, teamImage);

            finish();
        });

        bindEditTextScrolling(binding.infoEdit);
        binding.infoEdit.addTextChangedListener(new TextWatcher() {    //소모임 소개
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
            @Override
            public void afterTextChanged(Editable s) {
                findViewById(R.id.info_edit).setBackground(getResources().getDrawable(R.drawable.focus_box));
                String input = binding.infoEdit.getText().toString();
                binding.textNumTV.setText(input.length()+"/300"); //소모임 소개 실시간 글자수

                String text1 = s.toString();
                String text2 = binding.GroupNameEdit.getText().toString();
                String text3 = binding.locationEdit.getText().toString();
                if (text1.length() > 0 && text2.length() > 0 && text3.length() > 0){
                    onCreateBtn();
                }
                else {
                    offCreateBtn();
                }
            }
        });

        binding.locationEdit.addTextChangedListener(new TextWatcher() {    //위치 입력받기
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text1 = s.toString();
                String text2 = binding.GroupNameEdit.getText().toString();
                String text3 = binding.infoEdit.getText().toString();
                if (text1.length() > 0 && text2.length() > 0 && text3.length() > 0){
                    onCreateBtn();
                }
                else {
                    offCreateBtn();
                }
            }
        });

        ArrayAdapter memberAdapter = ArrayAdapter.createFromResource(this, R.array.member, android.R.layout.simple_spinner_dropdown_item);
        memberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.memberSpinner.setAdapter(memberAdapter);
        binding.memberSpinner.setOnItemSelectedListener(this);

        ArrayAdapter cateAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_dropdown_item);
        cateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(cateAdapter);
        binding.categorySpinner.setOnItemSelectedListener(this);

        ArrayAdapter subAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_dropdown_item);
        // 나중에 팀원들과 상의해서 세부 카테고리에 뭐가 들어갈지 정해야함, array도 만들어야함, 지금은 임시로 category 리스트로 넣었음
        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.subCategorySpinner.setAdapter(subAdapter);
        binding.subCategorySpinner.setOnItemSelectedListener(this);


        binding.switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {//On
                binding.startDate.setTextColor(Color.parseColor("#33353d"));
                binding.startTime.setTextColor(Color.parseColor("#33353d"));
                binding.endDate.setTextColor(Color.parseColor("#33353d"));
                binding.endTime.setTextColor(Color.parseColor("#33353d"));
                binding.startDate.setOnClickListener(v -> {
                    showDate(1);
                });
                binding.startTime.setOnClickListener(v -> {
                    showTime(1);
                });
                binding.endDate.setOnClickListener(v -> {
                    showDate(2);
                });
                binding.endTime.setOnClickListener(v -> {
                    showTime(2);
                });
            }
            else {//Off
                binding.startDate.setTextColor(getResources().getColor(R.color.sub_gray));
                binding.startTime.setTextColor(getResources().getColor(R.color.sub_gray));
                binding.endDate.setTextColor(getResources().getColor(R.color.sub_gray));
                binding.endTime.setTextColor(getResources().getColor(R.color.sub_gray));
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.member_spinner:
                Toast.makeText(EditGroupActivity.this,"선택된 아이템 : "+binding.memberSpinner.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                break;
            case R.id.category_spinner:
                Toast.makeText(EditGroupActivity.this,"선택된 아이템 : "+binding.categorySpinner.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                break;
            case R.id.sub_category_spinner:
                Toast.makeText(EditGroupActivity.this,"선택된 아이템 : "+binding.subCategorySpinner.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                break;
        }//Toast는 그저 확인용
    }//이 오버라이드 메소드에서 position은 몇번째 값이 클릭됐는지 알 수 있음
    //getItemAtPosition(position)를 통해서 해당 값을 받아올수있음

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressLint("IntentReset")
    public void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == 101) {
                selectedImageUri = data.getData();
                Glide.with(getApplicationContext()).asBitmap().load(selectedImageUri).into(binding.registerPicIv);
            }
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    public static void bindEditTextScrolling(EditText view)
    {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction() & MotionEvent.ACTION_MASK)
            {
                // 터치가 눌렸을때 터치 이벤트를 활성화한다.
                case MotionEvent.ACTION_DOWN:
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                // 터치가 끝났을때 터치 이벤트를 비활성화한다 [원상복구]
                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        });
    }

    // 다음 버튼 활성화
    private void onCreateBtn() {
        binding.createBtn.setBackgroundResource(R.drawable.ic_button);
        binding.createBtn.setTextColor(getResources().getColor(R.color.white));
        binding.createBtn.setEnabled(true);
    }

    // 다음 버튼 비활성화
    private void offCreateBtn() {
        binding.createBtn.setBackgroundResource(R.drawable.ic_disabled_button);
        binding.createBtn.setTextColor(getResources().getColor(R.color.sub_gray));
        binding.createBtn.setEnabled(false);
    }
    private void showDate(int id){
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        Dpicker = new DatePickerDialog(EditGroupActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    cal.set(year1, monthOfYear, dayOfMonth);
                    int weekDay = cal.get(Calendar.DAY_OF_WEEK);
                    String weekday = dayofweek(weekDay);
                    switch (id){
                        case 1:
                            binding.startDate.setText(year1 + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일 " + weekday);
                            break;
                        case 2:
                            binding.endDate.setText(year1 + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일 " + weekday);
                            break;
                    }
                }, year, month, day);
        Dpicker.show();
    }
    private void showTime(int id){

        Tpicker = new TimePickerDialog(EditGroupActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                (TimePickerDialog.OnTimeSetListener) (timePicker, hour, min) -> {
                    String status = ((hour>12))? "오후":"오전";
                    String format = String.format("%s %02d:%02d", status, hour % 12, min);
                    switch (id){
                        case 1:
                            binding.startTime.setText(format);
                            break;
                        case 2:
                            binding.endTime.setText(format);
                            break;
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);

        Tpicker.show();
    }
    private String dayofweek(int weekDay){
        switch (weekDay){
            case 2:
                return "월요일";
            case 3:
                return "화요일";
            case 4:
                return "수요일";
            case 5:
                return "목요일";
            case 6:
                return "금요일";
            case 7:
                return "토요일";
            case 1:
                return "일요일";
            default:
                return "error";
        }
    }

    private void setUI(){
        Bundle bundle=getIntent().getExtras();
        binding.GroupNameEdit.setText(bundle.getString("name"));
        binding.infoEdit.setText(bundle.getString("content"));
        binding.locationEdit.setText(bundle.getString("location"));
        binding.startDate.setText(bundle.getString("startdate"));
        binding.endDate.setText(bundle.getString("enddate"));
        selectedImageUri=Uri.parse(bundle.getString("url"));

        Glide.with(this)
                .load(ClientApp.API_URL + bundle.getString("url"))
                .into(binding.registerPicIv);

    }
}