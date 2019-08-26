package com.freedev;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jakezhang
 * Company:DHC
 * Description： 描述内容
 * Date: 2019/8/7 14:22
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, CompoundButton.OnCheckedChangeListener {
    private RelativeLayout layout1_bg;
    private EditText serverTV,editIP;
    private ImageView serverSpiner;
    private TextView ipTV;

    private ConstraintLayout layout2_bg;
    private TextView item2_txtTV;
    private ImageView item2_rightBtn,item2_leftBtn;

    private ConstraintLayout layout3_bg;
    private TextView item3_txtTV;
    private ImageView item3_rightBtn,item3_leftBtn;

    private ConstraintLayout layout4_bg;
    private TextView item4_txtTV;
    private ImageView item4_rightBtn,item4_leftBtn;

    private ConstraintLayout layout5_bg;
    private TextView item5_txtTV;
    private ImageView item5_rightBtn,item5_leftBtn;

    private ConstraintLayout layout6_bg;
    private TextView item6_txtTV;
    private ImageView item6_rightBtn,item6_leftBtn;

    private ConstraintLayout layout7_bg;
    private Switch switchWidget;
    private ConstraintLayout layout8_bg;
    private Button itemBottom_btnSave;//底部保存按钮

    private String prefsName= Common.SERVER_SETTING_PREFS;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private int BIT_RAT_SETTING_ID,FRAME_RATE_SETTING_ID,RESOLUTION_SETTING_ID,DECODEING_MODE_ID,
            TRANSPORT_PROTOCOL_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();
        prefs=getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        editor=prefs.edit();
        setBitratData();
        setFrameRatData();
        setResolutionData();
        setDecodeModeData();
        setTransportProtocolData();

        //码率
        BIT_RAT_SETTING_ID=prefs.getInt(Common.BIT_RAT_SETTING_ID,3);
        tempBitRat=BIT_RAT_SETTING_ID;
        setCheckItem(bitRats,BIT_RAT_SETTING_ID,item2_txtTV,item2_leftBtn,item2_rightBtn);

//帧率
        FRAME_RATE_SETTING_ID=prefs.getInt(Common.FRAME_RATE_SETTING_ID,1);
        tempFrameRat=FRAME_RATE_SETTING_ID;
        setCheckItem(frameRats,FRAME_RATE_SETTING_ID,item3_txtTV,item3_leftBtn,item3_rightBtn);

//分辨率
        RESOLUTION_SETTING_ID=prefs.getInt(Common.RESOLUTION_SETTING_ID,1);
        tempResolution=RESOLUTION_SETTING_ID;
        setCheckItem(resolutionRats,RESOLUTION_SETTING_ID,item4_txtTV,item4_leftBtn,item4_rightBtn);

        //编码格式
        DECODEING_MODE_ID=prefs.getInt(Common.DECODEING_MODE_ID,1);
        tempDecoding=DECODEING_MODE_ID;
        setCheckItem(decodeModes,DECODEING_MODE_ID,item5_txtTV,item5_leftBtn,item5_rightBtn);

        //传输协议
        TRANSPORT_PROTOCOL_ID=prefs.getInt(Common.TRANSPORT_PROTOCOL_ID,1);
        tempTransport=TRANSPORT_PROTOCOL_ID;
        setCheckItem(transportProtols,TRANSPORT_PROTOCOL_ID,item6_txtTV,item6_leftBtn,item6_rightBtn);

        //Switch开关 默认关闭,0关闭，1打开
        if (prefs.getInt(Common.SUSPENSION_SWITCH_KEY,0)==1){
            switchWidget.setChecked(true);
        }else {
            switchWidget.setChecked(false);
        }
    }

    private void initView() {
        layout1_bg=findViewById(R.id.layout1_bg);
        serverTV=findViewById(R.id.serverTV);
        editIP=findViewById(R.id.editIP);
        serverSpiner=findViewById(R.id.serverSpiner);
        ipTV=findViewById(R.id.ipTV);

        layout2_bg=findViewById(R.id.layout2_bg);
        item2_txtTV=findViewById(R.id.item2_txtTV);
        item2_rightBtn=findViewById(R.id.item2_rightBtn);
        item2_leftBtn=findViewById(R.id.item2_leftBtn);

        layout3_bg=findViewById(R.id.layout3_bg);
        item3_txtTV=findViewById(R.id.item3_txtTV);
        item3_rightBtn=findViewById(R.id.item3_rightBtn);
        item3_leftBtn=findViewById(R.id.item3_leftBtn);

        layout4_bg=findViewById(R.id.layout4_bg);
        item4_txtTV=findViewById(R.id.item4_txtTV);
        item4_rightBtn=findViewById(R.id.item4_rightBtn);
        item4_leftBtn=findViewById(R.id.item4_leftBtn);

        layout5_bg=findViewById(R.id.layout5_bg);
        item5_txtTV=findViewById(R.id.item5_txtTV);
        item5_rightBtn=findViewById(R.id.item5_rightBtn);
        item5_leftBtn=findViewById(R.id.item5_leftBtn);

        layout6_bg=findViewById(R.id.layout6_bg);
        item6_txtTV=findViewById(R.id.item6_txtTV);
        item6_rightBtn=findViewById(R.id.item6_rightBtn);
        item6_leftBtn=findViewById(R.id.item6_leftBtn);

        layout7_bg=findViewById(R.id.layout7_bg);
        switchWidget=findViewById(R.id.switchWidget);
        layout8_bg=findViewById(R.id.layout8_bg);
        itemBottom_btnSave=findViewById(R.id.item4_btnSave);
    }



    int tempBitRat,tempFrameRat,tempResolution,tempDecoding,tempTransport;
    //摇杆
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //item2
        if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT&&item2_leftBtn.hasFocus()){
            //码率左摇杆
            bitRatLeftRock();
            return true;
        }else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT&&item2_rightBtn.hasFocus()){
            //码率右摇杆
            bitRatRightRock();
            return true;
        }
        //item3
        if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT&&item3_leftBtn.hasFocus()){
            //帧率左摇杆
            frameRatLeftRock();
            return true;
        }else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT&&item3_rightBtn.hasFocus()){
            //帧率右摇杆
            frameRatRightRock();
            return true;
        }
        //item4
        if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT&&item4_leftBtn.hasFocus()){
            //分辨率左摇杆
            resolutionLeftRock();
            return true;
        }else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT&&item4_rightBtn.hasFocus()){
            //分辨率右摇杆
            resolutionRightRock();
            return true;
        }
        //item5
        if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT&&item5_leftBtn.hasFocus()){
            //编码格式 左摇杆
            decodeModeLeftRock();
            return true;
        }else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT&&item5_rightBtn.hasFocus()){
            //编码格式 右摇杆
            decodeModeRightRock();
            return true;
        }
        //item6
        if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT&&item6_leftBtn.hasFocus()){
            //传输协议 左摇杆
            transProtocolLeftRock();
            return true;
        }else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT&&item6_rightBtn.hasFocus()){
            //传输协议 右摇杆
            transProtocolRightRock();
            return true;
        }else if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT&&switchWidget.hasFocus()){
            boolean isChecked=switchWidget.isChecked();
            if (isChecked){
                switchWidget.setChecked(false);
                editor.putInt(Common.SUSPENSION_SWITCH_KEY,0);
                Toast.makeText(this,"close",Toast.LENGTH_SHORT).show();
            }
            return true;
        }else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT&&switchWidget.hasFocus()){
            boolean isChecked=switchWidget.isChecked();
            if (!isChecked){
                switchWidget.setChecked(true);
                editor.putInt(Common.SUSPENSION_SWITCH_KEY,1);
                Toast.makeText(this,"open",Toast.LENGTH_SHORT).show();
            }
            return true;
        }else if (keyCode==KeyEvent.KEYCODE_DPAD_DOWN&&itemBottom_btnSave.hasFocus()){
            Toast.makeText(this, "已经到了最后一项了", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void bitRatLeftRock() {
        int left = --tempBitRat;
//            Toast.makeText(this, "left"+left, Toast.LENGTH_SHORT).show();
        if (left>0){
            tempBitRat=left;
            setCheckItem(bitRats,left,item2_txtTV,item2_leftBtn,item2_rightBtn);
            editor.putInt(Common.BIT_RAT_SETTING_ID,left);
        }else if (left==0){
            tempBitRat=0;
            setCheckItem(bitRats,left,item2_txtTV,item2_leftBtn,item2_rightBtn);
            editor.putInt(Common.BIT_RAT_SETTING_ID,left);
        }else {
            tempBitRat=0;
//             item2_leftBtn.setEnabled(false);
            item2_leftBtn.setSelected(false);
//                Toast.makeText(this, "left left", Toast.LENGTH_SHORT).show();
        }
    }

    private void bitRatRightRock() {
        int right = ++tempBitRat;
//            Toast.makeText(this, "right"+right, Toast.LENGTH_SHORT).show();
        if (right<bitRats.size()-1){
            tempBitRat=right;
            setCheckItem(bitRats,right,item2_txtTV,item2_leftBtn,item2_rightBtn);
            editor.putInt(Common.BIT_RAT_SETTING_ID,right);
        }else if (right==bitRats.size()-1){
            tempBitRat=bitRats.size()-1;
            setCheckItem(bitRats,right,item2_txtTV,item2_leftBtn,item2_rightBtn);
            editor.putInt(Common.BIT_RAT_SETTING_ID,right);
        }else {
            tempBitRat=bitRats.size()-1;
//             item2_rightBtn.setEnabled(false);
            item2_rightBtn.setSelected(false);
//                Toast.makeText(this, "right right", Toast.LENGTH_SHORT).show();
        }
    }

    private void frameRatLeftRock() {
        int left = --tempFrameRat;
//            Toast.makeText(this, "left"+left, Toast.LENGTH_SHORT).show();
        if (left>0){
            tempFrameRat=left;
            setCheckItem(frameRats,left,item3_txtTV,item3_leftBtn,item3_rightBtn);
            editor.putInt(Common.FRAME_RATE_SETTING_ID,left);
        }else if (left==0){
            tempFrameRat=0;
            setCheckItem(frameRats,left,item3_txtTV,item3_leftBtn,item3_rightBtn);
            editor.putInt(Common.FRAME_RATE_SETTING_ID,left);
        }else {
            tempFrameRat=0;
//             item3_leftBtn.setEnabled(false);
            item3_leftBtn.setSelected(false);
//                Toast.makeText(this, "left left", Toast.LENGTH_SHORT).show();
        }
    }

    private void frameRatRightRock() {
        int right = ++tempFrameRat;
//            Toast.makeText(this, "right"+right, Toast.LENGTH_SHORT).show();
        if (right<frameRats.size()-1){
            tempFrameRat=right;
            setCheckItem(frameRats,right,item3_txtTV,item3_leftBtn,item3_rightBtn);
            editor.putInt(Common.FRAME_RATE_SETTING_ID,right);
        }else if (right==frameRats.size()-1){
            tempFrameRat=frameRats.size()-1;
            setCheckItem(frameRats,right,item3_txtTV,item3_leftBtn,item3_rightBtn);
            editor.putInt(Common.FRAME_RATE_SETTING_ID,right);
        }else {
            tempFrameRat=frameRats.size()-1;
//             item3_rightBtn.setEnabled(false);
            item3_rightBtn.setSelected(false);
//                Toast.makeText(this, "right right", Toast.LENGTH_SHORT).show();
        }
    }

    private void resolutionLeftRock() {
        int left = --tempResolution;
//            Toast.makeText(this, "left"+left, Toast.LENGTH_SHORT).show();
        if (left>0){
            tempResolution=left;
            setCheckItem(resolutionRats,left,item4_txtTV,item4_leftBtn,item4_rightBtn);
            editor.putInt(Common.RESOLUTION_SETTING_ID,left);
        }else if (left==0){
            tempResolution=0;
            setCheckItem(resolutionRats,left,item4_txtTV,item4_leftBtn,item4_rightBtn);
            editor.putInt(Common.RESOLUTION_SETTING_ID,left);
        }else {
            tempResolution=0;
//             item4_leftBtn.setEnabled(false);
            item4_leftBtn.setSelected(false);
//                Toast.makeText(this, "left left", Toast.LENGTH_SHORT).show();
        }
    }

    private void resolutionRightRock() {
        int right = ++tempResolution;
//            Toast.makeText(this, "right"+right, Toast.LENGTH_SHORT).show();
        if (right<resolutionRats.size()-1){
            tempResolution=right;
            setCheckItem(resolutionRats,right,item4_txtTV,item4_leftBtn,item4_rightBtn);
            editor.putInt(Common.RESOLUTION_SETTING_ID,right);
        }else if (right==resolutionRats.size()-1){
            tempResolution=resolutionRats.size()-1;
            setCheckItem(resolutionRats,right,item4_txtTV,item4_leftBtn,item4_rightBtn);
            editor.putInt(Common.RESOLUTION_SETTING_ID,right);
        }else {
            tempResolution=resolutionRats.size()-1;
//             item4_rightBtn.setEnabled(false);
            item4_rightBtn.setSelected(false);
//                Toast.makeText(this, "right right", Toast.LENGTH_SHORT).show();
        }
    }

    private void decodeModeLeftRock() {
        int left = --tempDecoding;
//            Toast.makeText(this, "left"+left, Toast.LENGTH_SHORT).show();
        if (left>0){
            tempDecoding=left;
            setCheckItem(decodeModes,left,item5_txtTV,item5_leftBtn,item5_rightBtn);
            editor.putInt(Common.DECODEING_MODE_ID,left);
        }else if (left==0){
            tempDecoding=0;
            setCheckItem(decodeModes,left,item5_txtTV,item5_leftBtn,item5_rightBtn);
            editor.putInt(Common.DECODEING_MODE_ID,left);
        }else {
            tempDecoding=0;
//             item5_leftBtn.setEnabled(false);
            item5_leftBtn.setSelected(false);
//                Toast.makeText(this, "left left", Toast.LENGTH_SHORT).show();
        }
    }

    private void decodeModeRightRock() {
        int right = ++tempDecoding;
//            Toast.makeText(this, "right"+right, Toast.LENGTH_SHORT).show();
        if (right<decodeModes.size()-1){
            tempDecoding=right;
            setCheckItem(decodeModes,right,item5_txtTV,item5_leftBtn,item5_rightBtn);
            editor.putInt(Common.DECODEING_MODE_ID,right);
        }else if (right==decodeModes.size()-1){
            tempDecoding=decodeModes.size()-1;
            setCheckItem(decodeModes,right,item5_txtTV,item5_leftBtn,item5_rightBtn);
            editor.putInt(Common.DECODEING_MODE_ID,right);
        }else {
            tempDecoding=decodeModes.size()-1;
//             item5_rightBtn.setEnabled(false);
            item5_rightBtn.setSelected(false);
//                Toast.makeText(this, "right right", Toast.LENGTH_SHORT).show();
        }
    }

    private void transProtocolLeftRock() {
        int left = --tempTransport;
//            Toast.makeText(this, "left"+left, Toast.LENGTH_SHORT).show();
        if (left>0){
            tempTransport=left;
            setCheckItem(transportProtols,left,item6_txtTV,item6_leftBtn,item6_rightBtn);
            editor.putInt(Common.TRANSPORT_PROTOCOL_ID,left);
        }else if (left==0){
            tempBitRat=0;
            setCheckItem(transportProtols,left,item6_txtTV,item6_leftBtn,item6_rightBtn);
            editor.putInt(Common.TRANSPORT_PROTOCOL_ID,left);
        }else {
            tempTransport=0;
//             item6_leftBtn.setEnabled(false);
            item6_leftBtn.setSelected(false);
//                Toast.makeText(this, "left left", Toast.LENGTH_SHORT).show();
        }
    }

    private void transProtocolRightRock() {
        int right = ++tempTransport;
//            Toast.makeText(this, "right"+right, Toast.LENGTH_SHORT).show();
        if (right<transportProtols.size()-1){
            tempTransport=right;
            setCheckItem(transportProtols,right,item6_txtTV,item6_leftBtn,item6_rightBtn);
            editor.putInt(Common.TRANSPORT_PROTOCOL_ID,right);
        }else if (right==transportProtols.size()-1){
            tempTransport=transportProtols.size()-1;
            setCheckItem(transportProtols,right,item6_txtTV,item6_leftBtn,item6_rightBtn);
            editor.putInt(Common.TRANSPORT_PROTOCOL_ID,right);
        }else {
            tempTransport=transportProtols.size()-1;
//             item6_rightBtn.setEnabled(false);
            item6_rightBtn.setSelected(false);
//                Toast.makeText(this, "right right", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        serverTV.setOnClickListener(this);
        itemBottom_btnSave.setOnClickListener(this);
        serverTV.setOnFocusChangeListener(this);
        editIP.setOnFocusChangeListener(this);
        item2_rightBtn.setOnFocusChangeListener(this);
        item2_leftBtn.setOnFocusChangeListener(this);

        item3_rightBtn.setOnFocusChangeListener(this);
        item3_leftBtn.setOnFocusChangeListener(this);
        item4_rightBtn.setOnFocusChangeListener(this);
        item4_leftBtn.setOnFocusChangeListener(this);
        item5_rightBtn.setOnFocusChangeListener(this);
        item5_leftBtn.setOnFocusChangeListener(this);
        item6_rightBtn.setOnFocusChangeListener(this);
        item6_leftBtn.setOnFocusChangeListener(this);

        switchWidget.setOnFocusChangeListener(this);
        itemBottom_btnSave.setOnFocusChangeListener(this);
        switchWidget.setOnCheckedChangeListener(this);

        item2_rightBtn.setOnClickListener(this);
        item2_leftBtn.setOnClickListener(this);

        item3_rightBtn.setOnClickListener(this);
        item3_leftBtn.setOnClickListener(this);
        item4_rightBtn.setOnClickListener(this);
        item4_leftBtn.setOnClickListener(this);
        item5_rightBtn.setOnClickListener(this);
        item5_leftBtn.setOnClickListener(this);
        item6_rightBtn.setOnClickListener(this);
        item6_leftBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item2_leftBtn:
                bitRatLeftRock();
                break;
            case R.id.item2_rightBtn:
                bitRatRightRock();
                break;
            case R.id.item3_leftBtn:
                frameRatLeftRock();
                break;
            case R.id.item3_rightBtn:
                frameRatRightRock();
                break;
            case R.id.item4_leftBtn:
                resolutionLeftRock();
                break;
            case R.id.item4_rightBtn:
                resolutionRightRock();
                break;
            case R.id.item5_leftBtn:
                decodeModeLeftRock();
                break;
            case R.id.item5_rightBtn:
                decodeModeRightRock();
                break;
            case R.id.item6_leftBtn:
                transProtocolLeftRock();
                break;
            case R.id.item6_rightBtn:
                transProtocolRightRock();
                break;
            case R.id.serverTV:
//                下拉弹窗
                serverSpinnerPop(v);
                break;
            case R.id.item4_btnSave:
                editor.commit();
                if (editIP.getVisibility()==View.VISIBLE){
                    editor.putString(Common.CUSTOM_IP,editIP.getText().toString().trim());
                }
                editor.commit();
                Toast.makeText(this,"save the setting succeed",Toast.LENGTH_SHORT).show();
//                finish();
                break;
        }
    }

    //
    private void serverSpinnerPop(View v) {
        final ListPopupWindow listPopupWindow=new ListPopupWindow(this);
        final List<PopServerBean> data=new ArrayList<>();
        for (int i=0;i<2;i++){
            if (i==0){
                PopServerBean bean=new PopServerBean();
                bean.setServerName("Cloud game Server");
                bean.setEditble(false);
                data.add(bean);
            }else{
                PopServerBean bean=new PopServerBean();
                bean.setServerName("cunstom");
                bean.setEditble(true);
                data.add(bean);
            }
        }

        PopServerAdapter popServerAdapter=new PopServerAdapter(this,data);
//                    listPopupWindow.setBackgroundDrawable(context.getDrawable(R.color.colorPrimaryDark));
        listPopupWindow.setAdapter(popServerAdapter);
        listPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setModal(true);
        listPopupWindow.setAnchorView(v);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==data.size()-1){
                    ipTV.setVisibility(View.GONE);
                    editIP.setVisibility(View.VISIBLE);
                    editIP.requestFocus();//获得焦点
                }else {
                    ipTV.setVisibility(View.VISIBLE);
                    editIP.setVisibility(View.GONE);
                }
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.equals(serverTV)){
            if (hasFocus){
                serverSpiner.setImageResource(R.drawable.triangle_pic_enable);
                layout1_bg.setBackgroundColor(getResources().getColor(R.color.whiteTranslate20));
            }else {
                serverSpiner.setImageResource(R.drawable.triangle_pic);
                layout1_bg.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }else if (v.equals(editIP)){
            if (hasFocus){
                layout1_bg.setBackgroundColor(getResources().getColor(R.color.whiteTranslate20));
            }else {
                layout1_bg.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }else if (v.equals(item2_rightBtn)||v.equals(item2_leftBtn)){
            if (hasFocus){
                layout2_bg.setBackgroundColor(getResources().getColor(R.color.whiteTranslate20));
            }else {
                layout2_bg.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }else if (v.equals(item3_rightBtn)||v.equals(item3_leftBtn)){
            if (hasFocus){
                layout3_bg.setBackgroundColor(getResources().getColor(R.color.whiteTranslate20));
            }else {
                layout3_bg.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }else if (v.equals(item4_rightBtn)||v.equals(item4_leftBtn)){
            if (hasFocus){
                layout4_bg.setBackgroundColor(getResources().getColor(R.color.whiteTranslate20));
            }else {
                layout4_bg.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }else if (v.equals(item5_rightBtn)||v.equals(item5_leftBtn)){
            if (hasFocus){
                layout5_bg.setBackgroundColor(getResources().getColor(R.color.whiteTranslate20));
            }else {
                layout5_bg.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }else if (v.equals(item6_leftBtn)||v.equals(item6_rightBtn)){
            if (hasFocus){
                layout6_bg.setBackgroundColor(getResources().getColor(R.color.whiteTranslate20));
            }else {
                layout6_bg.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }else if (v.equals(switchWidget)){
            if (hasFocus){
                layout7_bg.setBackgroundColor(getResources().getColor(R.color.whiteTranslate20));
            }else {
                layout7_bg.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }else if (v.equals(itemBottom_btnSave)){
            if (hasFocus){
                layout8_bg.setBackgroundColor(getResources().getColor(R.color.whiteTranslate20));
            }else {
                layout8_bg.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }




    }
    List<SettingCheckBean> bitRats;
    public void setBitratData(){
        //码率数据源
        bitRats=new ArrayList<>();
        for (int j=0;j<getResources().getStringArray(R.array.bitRateList).length;j++){
            SettingCheckBean bitBean=new SettingCheckBean();
            bitBean.setId(j);
            bitBean.setContent(getResources().getStringArray(R.array.bitRateList)[j]);
            if (j==0){
                bitBean.setLeftClickable(false);
                bitBean.setRightClickable(true);
            }else if (j==getResources().getStringArray(R.array.bitRateList).length-1){
                bitBean.setLeftClickable(true);
                bitBean.setRightClickable(false);
            }else{
                bitBean.setLeftClickable(true);
                bitBean.setRightClickable(true);
            }
            bitRats.add(bitBean);
        }
    }

    //选择item项，刷新展示
    private void setCheckItem(List<? extends SettingBaseBean> list, int defaultCheckId, TextView contentTxtview, ImageView leftImgView, ImageView rightImgView) {
        //选中的码率
//        SettingCheckBean bean=bitRats.get(defaultCheckId);
        SettingCheckBean bean=(SettingCheckBean)list.get(defaultCheckId);
        contentTxtview.setText(bean.getContent());
//         leftImgView.setEnabled(bean.isLeftClickable());
//         rightImgView.setEnabled(bean.isRightClickable());
        leftImgView.setSelected(bean.isLeftClickable());
        rightImgView.setSelected(bean.isRightClickable());
    }


    List<SettingCheckBean> frameRats;
    private void setFrameRatData() {
        //帧率数据源
        frameRats=new ArrayList<>();
        for (int k=0;k<getResources().getStringArray(R.array.frameRateList).length;k++){
            SettingCheckBean frameBean=new SettingCheckBean();
            frameBean.setId(k);
            frameBean.setContent(getResources().getStringArray(R.array.frameRateList)[k]);
            if (k==0){
                frameBean.setRightClickable(true);
                frameBean.setLeftClickable(false);
            }else if (k==getResources().getStringArray(R.array.frameRateList).length-1){
                frameBean.setRightClickable(false);
                frameBean.setLeftClickable(true);
            }else{
                frameBean.setRightClickable(true);
                frameBean.setLeftClickable(true);
            }
            frameRats.add(frameBean);
        }
    }

    List<SettingCheckBean> resolutionRats;
    private void setResolutionData() {
        //分辨率数据源
        resolutionRats=new ArrayList<>();
        for (int l=0;l<getResources().getStringArray(R.array.resolutionList).length;l++){
            SettingCheckBean resolutionBean=new SettingCheckBean();
            resolutionBean.setId(l);
            resolutionBean.setContent(getResources().getStringArray(R.array.resolutionList)[l]);
            if (l==0){
                resolutionBean.setLeftClickable(false);
                resolutionBean.setRightClickable(true);
            }else if (l==getResources().getStringArray(R.array.resolutionList).length-1){
                resolutionBean.setLeftClickable(true);
                resolutionBean.setRightClickable(false);
            }else{
                resolutionBean.setRightClickable(true);
                resolutionBean.setLeftClickable(true);
            }
            resolutionRats.add(resolutionBean);
        }

    }

    List<SettingCheckBean> decodeModes;
    private void setDecodeModeData() {
        //编码方式 数据源
        decodeModes=new ArrayList<>();
        for (int a=0;a<getResources().getStringArray(R.array.decodeModeList).length;a++){
            SettingCheckBean decodeModeBean=new SettingCheckBean();
            decodeModeBean.setId(a);
            decodeModeBean.setContent(getResources().getStringArray(R.array.decodeModeList)[a]);
            if (a==0){
                decodeModeBean.setLeftClickable(false);
                decodeModeBean.setRightClickable(true);
            }else if (a==getResources().getStringArray(R.array.decodeModeList).length-1){
                decodeModeBean.setLeftClickable(true);
                decodeModeBean.setRightClickable(false);
            }else {
                decodeModeBean.setLeftClickable(true);
                decodeModeBean.setRightClickable(true);
            }
            decodeModes.add(decodeModeBean);
        }
    }

    List<SettingCheckBean> transportProtols;
    private void setTransportProtocolData() {
        //传输协议
        transportProtols=new ArrayList<>();
        for (int s=0;s<getResources().getStringArray(R.array.transportProtocolList).length;s++){
            SettingCheckBean transportBean=new SettingCheckBean();
            transportBean.setId(s);
            transportBean.setContent(getResources().getStringArray(R.array.transportProtocolList)[s]);
            if (s==0){
                transportBean.setLeftClickable(false);
                transportBean.setRightClickable(true);
            }else if (s==getResources().getStringArray(R.array.transportProtocolList).length-1){
                transportBean.setLeftClickable(true);
                transportBean.setRightClickable(false);
            }else {
                transportBean.setLeftClickable(true);
                transportBean.setRightClickable(true);
            }

            transportProtols.add(transportBean);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            editor.putInt(Common.SUSPENSION_SWITCH_KEY,1);
            Toast.makeText(this,"open",Toast.LENGTH_SHORT).show();
        }else {
            editor.putInt(Common.SUSPENSION_SWITCH_KEY,0);
            Toast.makeText(this,"close",Toast.LENGTH_SHORT).show();
        }
    }
}
