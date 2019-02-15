package com.huashe.pizz.other;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huashe.pizz.MainActivity;
import com.huashe.pizz.MyApplication;
import com.huashe.pizz.R;
import com.huashe.pizz.other.rx.RetrofitHelper;
import com.huashe.pizz.other.util.Constant;
import com.huashe.pizz.other.util.CryptoUtil;
import com.huashe.pizz.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    boolean isForgetPwd = false; //默认没有忘记密码
    @BindView(R.id.Switch_layout)
    LinearLayout SwitchLayout; //切换界面的区域
    View normalLoginView, ForgetPwdView, newPasswordView; //正常登录界面,忘记密码碎片,新密码碎片
    Button NextButton, sureButton; //登录中的下一步按钮 ,新密码确定按钮

    EditText user_pad_txt;

    TextView user_phone_txt, Yanzhengma_txt;//验证码text，手机号
    Button getYz_number;
    boolean isEmpty_user = false;
    boolean isEmpty_pwd = false;
    EditText pwdEdit, userEdit;
    EditText editText4YanZheng, user_pad_txt1;
    ImageView user_pwd_image_eyes, user_pwd_image_eyes1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        insertDummyContactWrapper();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        SMSSDK.getInstance().setIntervalTime(60 * 1000);
        loadLoginView();


    }


    private void loadLoginView() {
        if (!isForgetPwd) {
            normalLoginView = getLayoutInflater().from(this).inflate(R.layout.login_normal, null);
        }

        if (null == SwitchLayout) {
            SwitchLayout = findViewById(R.id.Switch_layout);
        }

        if (isForgetPwd) {
            SwitchLayout.removeAllViews();
            SwitchLayout.addView(normalLoginView);
        } else {
            SwitchLayout.addView(normalLoginView);
        }
        normalLoginView.findViewById(R.id.ForgetPwd_txt).setOnClickListener(this);
        final EditText pwdEdit = normalLoginView.findViewById(R.id.user_pad_txt);
        userEdit = normalLoginView.findViewById(R.id.user_name_txt);
        final ImageView eyes_imageView = normalLoginView.findViewById(R.id.user_pwd_image_eyes);
        final Button button = normalLoginView.findViewById(R.id.login_button);

        userEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    normalLoginView.findViewById(R.id.user_image).setBackgroundResource(R.drawable.icon_login_user_sel);
                } else {
                    normalLoginView.findViewById(R.id.user_image).setBackgroundResource(R.drawable.icon_login_user_nor);
                }

                if (userEdit.getText() != null && userEdit.getText().toString().trim().length() > 0) {
                    isEmpty_user = true;
                } else {
                    isEmpty_user = false;
                }

            }
        });

        userEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (userEdit.getText() != null && userEdit.getText().toString().trim().length() > 0) {
                    isEmpty_user = true;
                } else {
                    isEmpty_user = false;
                }

                if (isEmpty_pwd && isEmpty_user) {
                    button.setBackgroundResource(R.drawable.ready_submit_button);
                    button.setTextColor(getResources().getColor(R.color.white));
                    button.setEnabled(true);
                } else {
                    button.setBackgroundResource(R.drawable.login_button_blue_shape);
                    button.setTextColor(getResources().getColor(R.color.button_unsubmit));
                    button.setEnabled(false);
                }
            }
        });

        pwdEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (pwdEdit.getText() != null && pwdEdit.getText().toString().trim().length() > 0) {
                    isEmpty_pwd = true;
                } else {
                    isEmpty_pwd = false;
                }
                if (pwdEdit.getText().toString().trim().length() > 0) {
                    eyes_imageView.setVisibility(View.VISIBLE);
                } else {
                    eyes_imageView.setVisibility(View.GONE);
                }


                if (isEmpty_pwd && isEmpty_user) {
                    button.setBackgroundResource(R.drawable.ready_submit_button);
                    button.setTextColor(getResources().getColor(R.color.white));
                    button.setEnabled(true);
                } else {
                    button.setBackgroundResource(R.drawable.login_button_blue_shape);
                    button.setTextColor(getResources().getColor(R.color.button_color));
                    button.setEnabled(false);
                }
            }
        });
        pwdEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    normalLoginView.findViewById(R.id.user_pwd_image).setBackgroundResource(R.drawable.icon_login_qrpwd_sel);
                    isEmpty_pwd = true;
                } else {
                    normalLoginView.findViewById(R.id.user_pwd_image).setBackgroundResource(R.drawable.icon_login_qrpwd_nor);
                }

            }
        });

        eyes_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwdEdit.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    pwdEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    pwdEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userEdit != null && pwdEdit != null) {
                    if (userEdit.getText().toString().trim().length() > 0 && pwdEdit.getText().toString().trim().length() > 0) {
                        getData(userEdit.getText().toString().trim(), pwdEdit.getText().toString().trim());
                    } else {
                        Toast.makeText(LoginActivity.this, "请确认登录信息", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        //normalLoginView.findViewById(R.id.login_button).setOnClickListener(this);
        isForgetPwd = false;//这里把判断状态设置成 未忘记密码
    }


    // 切换成  忘记密码页面
    public void Switchlayout() {
        isForgetPwd = true;
        ForgetPwdView = LayoutInflater.from(this).inflate(R.layout.login_forget_pwd, null);
        if (NextButton == null) {
            NextButton = ForgetPwdView.findViewById(R.id.forget_next_button);
        }

        SwitchLayout.removeAllViews();
        SwitchLayout.addView(ForgetPwdView);
        NextButton = ForgetPwdView.findViewById(R.id.forget_next_button);
        getYz_number = ForgetPwdView.findViewById(R.id.getYz_number);
        getYz_number.setOnClickListener(this);
        user_phone_txt = ForgetPwdView.findViewById(R.id.user_phone_txt);
        user_phone_txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (user_phone_txt == null || user_phone_txt.getText().toString().trim().length() <= 0) {
                        NextButton.setBackgroundResource(R.drawable.login_button_blue_shape);
                        NextButton.setTextColor(getResources().getColor(R.color.button_unsubmit));
                    }
                    ForgetPwdView.findViewById(R.id.user_image).setBackgroundResource(R.drawable.icon_login_user_nor);
                } else {
                    ForgetPwdView.findViewById(R.id.user_image).setBackgroundResource(R.drawable.icon_login_user_sel);
                }
                //TODO 这里进行了修改
            }
        });
        editText4YanZheng = ForgetPwdView.findViewById(R.id.Yanzhengma_txt);
        editText4YanZheng.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ForgetPwdView.findViewById(R.id.yzm_img).setBackgroundResource(R.drawable.icon_login_sryzh_sel);

                } else {
                    ForgetPwdView.findViewById(R.id.yzm_img).setBackgroundResource(R.drawable.icon_login_sryzm_nor);
                }
            }
        });
        editText4YanZheng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (null != editText4YanZheng.getText() && editText4YanZheng.getText().toString().trim().length() > 0 && user_phone_txt.getText() != null && user_phone_txt.getText().toString().trim().length() > 0) {
                    NextButton.setBackgroundResource(R.drawable.ready_submit_button);
                    NextButton.setTextColor(getResources().getColor(R.color.white));
                } else {
                    NextButton.setBackgroundResource(R.drawable.login_button_blue_shape);
                    NextButton.setTextColor(getResources().getColor(R.color.button_unsubmit));
                }

            }


        });

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 这里是重新设置密码的界面

                SMSSDK.getInstance().checkSmsCodeAsyn(user_phone_txt.getText().toString(), editText4YanZheng.getText().toString().trim(), new SmscheckListener() {
                    @Override
                    public void checkCodeSuccess(final String code) {
                        // 验证码验证成功，code 为验证码信息。
                        Toast.makeText(LoginActivity.this, "验证通过", Toast.LENGTH_SHORT).show();
                        newPassword();
                        /**SwitchLayout.removeAllViews();
                         SwitchLayout.addView(normalLoginView);*/
                    }

                    @Override
                    public void checkCodeFail(int errCode, final String errMsg) {
                        // 验证码验证失败, errCode 为错误码，详情请见文档后面的错误码表；errMsg 为错误描述。
                        Toast.makeText(LoginActivity.this, errMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


    //切换 新密码界面
    private void newPassword() {

        if (newPasswordView == null) {
            newPasswordView = LayoutInflater.from(this).inflate(R.layout.login_forget_next, null);
            SwitchLayout.removeAllViews();
            SwitchLayout.addView(newPasswordView);

        } else {
            SwitchLayout.removeAllViews();
            SwitchLayout.addView(newPasswordView);
        }
        sureButton = newPasswordView.findViewById(R.id.forget_next_sure_button);
        user_pad_txt = newPasswordView.findViewById(R.id.user_pad_txt);
        user_pad_txt1 = newPasswordView.findViewById(R.id.user_pad_txt1);
        user_pwd_image_eyes = newPasswordView.findViewById(R.id.user_pwd_image_eyes);
        user_pwd_image_eyes1 = newPasswordView.findViewById(R.id.user_pwd_image_eyes1);


        user_pwd_image_eyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_pad_txt.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    user_pad_txt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    user_pad_txt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });

        user_pwd_image_eyes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_pad_txt1.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    user_pad_txt1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    user_pad_txt1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });

        user_pad_txt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != user_pad_txt.getText() && user_pad_txt.getText().toString().trim().length() > 0 && user_pad_txt1.getText() != null && user_pad_txt1.getText().toString().trim().length() > 0) {
                    sureButton.setBackgroundResource(R.drawable.ready_submit_button);
                    sureButton.setTextColor(getResources().getColor(R.color.white));
                } else {
                    sureButton.setBackgroundResource(R.drawable.login_button_blue_shape);
                    sureButton.setTextColor(getResources().getColor(R.color.button_unsubmit));
                }
            }
        });
        user_pad_txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    newPasswordView.findViewById(R.id.user_pwd_image).setBackgroundResource(R.drawable.icon_login_qrpwd_sel);
                    isEmpty_pwd = true;
                } else {
                    newPasswordView.findViewById(R.id.user_pwd_image).setBackgroundResource(R.drawable.icon_login_qrpwd_nor);
                }
            }
        });
        user_pad_txt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    newPasswordView.findViewById(R.id.user_pwd_image1).setBackgroundResource(R.drawable.icon_login_qrpwd_sel);
                    isEmpty_pwd = true;
                } else {
                    newPasswordView.findViewById(R.id.user_pwd_image1).setBackgroundResource(R.drawable.icon_login_qrpwd_nor);
                }
            }
        });
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user_pad_txt == null || user_pad_txt1 == null || user_pad_txt.getText().toString().trim().length() <= 0 || user_pad_txt1.getText().toString().trim().length() <= 0) {
                    Toast.makeText(LoginActivity.this, "请确认信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!user_pad_txt1.getText().toString().trim().equals(user_pad_txt.getText().toString().trim())) {
                    Toast.makeText(LoginActivity.this, "密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                resetPwdNetRequest();


            }
        });
    }


    public void resetPwdNetRequest() {

        Map<String, String> map = new HashMap<>();
        map.put("name", user_phone_txt.getText().toString().trim());
        map.put("password", user_pad_txt.getText().toString().trim());
        map.put("Password1", user_pad_txt.getText().toString().trim());


        RetrofitHelper.getHttpAPITest()
                .alterpwd(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object object) {
                        try {
                            Gson gson = new Gson();
                            String jsonString = gson.toJson(object);
                            JSONObject jsonObject = new JSONObject(jsonString);

                            if (jsonObject.getString("success").equals("true")) {
                                Toast.makeText(LoginActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(LoginActivity.this, "重置失败", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d("dyhNews--->", "onError: detail > " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        loadLoginView();

    }


    @Override
    public void onBackPressed() {
        if (isForgetPwd) {
            loadLoginView();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        isForgetPwd = false;
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.login_button:
//
//                break;
            case R.id.ForgetPwd_txt:
                Switchlayout();
                break;
            case R.id.getYz_number:

                String phoneNum = user_phone_txt.getText().toString();
                if (TextUtils.isEmpty(phoneNum)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                getYz_number.setClickable(false);
                //开始倒计时
                startTimer();
                SMSSDK.getInstance().getSmsCodeAsyn(phoneNum, Constant.TEMP_ID + "", new SmscodeListener() {
                    @Override
                    public void getCodeSuccess(final String uuid) {
                        Toast.makeText(LoginActivity.this, uuid, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void getCodeFail(int errCode, final String errmsg) {
                        //失败后停止计时
                        stopTimer();
                        Toast.makeText(LoginActivity.this, errmsg, Toast.LENGTH_SHORT).show();
                    }
                });
                break;

        }
    }


    private TimerTask timerTask;
    private Timer timer;
    private int timess;

    private void startTimer() {
        timess = (int) (SMSSDK.getInstance().getIntervalTime() / 1000);
        getYz_number.setText(timess + "s");
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timess--;
                            if (timess <= 0) {
                                stopTimer();
                                return;
                            }
                            getYz_number.setText(timess + "s");
                        }
                    });
                }
            };
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(timerTask, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        getYz_number.setText("重新获取");
        getYz_number.setClickable(true);
    }

    public void getData(String name, String pwd) {

        String key = "460074818678636";

        RetrofitHelper.getHttpAPITest()
                .getNewsDetails(name, pwd, bytesToHexString(CryptoUtil.aesEncrypt(key.getBytes(), Constant.DEFAULT_KEY)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object object) {

                        try {
                            Gson gson = new Gson();
                            String jsonString = gson.toJson(object);
                            JSONObject jsonObject = new JSONObject(jsonString);

                            if (jsonObject.getString("success").equals("true")) {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                System.out.println(jsonObject.toString());
                                SPUtils.put(MyApplication.getInstance(), "UserId", jsonObject.getString("data"));
                                if (userEdit != null && userEdit.getText() != null) {
                                    SPUtils.put(MyApplication.getInstance(), "mobile", userEdit.getText().toString().trim());
                                }
// TODO: 19-2-14 请求用户信息存放数据库
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("dyhNews--->", "onError: detail > " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * byte转16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    final private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 124;
    private static final String TAGLOG = "Contacts";

    private void insertDummyContact() {
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(2);
        ContentProviderOperation.Builder op =
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        operations.add(op.build());
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        "__DUMMY CONTACT from runtime permissions sample");
        operations.add(op.build());


        ContentResolver resolver = getContentResolver();
        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (RemoteException e) {

        } catch (OperationApplicationException e) {

        }
    }


    private void insertDummyContactWrapper() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Storage");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(LoginActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                                        100);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]),
                    100);
            return;
        }

        //insertDummyContact();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {

                Map<String, Integer> perms = new HashMap<String, Integer>();

                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_CONTACTS, PackageManager.PERMISSION_GRANTED);

                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    insertDummyContact();
                } else {
                    Toast.makeText(this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
