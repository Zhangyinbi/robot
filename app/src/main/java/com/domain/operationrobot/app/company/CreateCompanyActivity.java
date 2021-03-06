package com.domain.operationrobot.app.company;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.library.utils.ActivityStackManager;
import com.domain.library.utils.InputUtils;
import com.domain.library.utils.SpUtils;
import com.domain.library.utils.ToastUtils;
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.home.MainActivity;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.listener.ThrottleLastClickListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.domain.operationrobot.util.Constant.USER_SP_KEY;

public class CreateCompanyActivity extends AbsActivity {
  private DeleteEdit de_company_name;
  private DeleteEdit de_email;
  private DeleteEdit de_name;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.iv_back:
          finish();
          startActivity(new Intent(CreateCompanyActivity.this, MainActivity.class));
          break;
        case R.id.btn_complete:
          String companyName = de_company_name.getValue();
          if (isEmpty(companyName)) {
            return;
          }
          if (companyName.length() > 50) {
            showToast("公司名称不能大于50个字");
            return;
          }
          if (isSpecialChar(companyName)){
            showToast("公司名称不可以输入特殊字符");
            return;
          }
          String email = de_email.getValue();
          if (isEmpty(email)) {
            return;
          }
          if (!InputUtils.isEmail(email)) {
            showToast("邮箱格式不对，请输入正确的邮箱");
            return;
          }
          String name = de_name.getValue();
          if (isEmpty(name)) {
            return;
          }
          if (name.length() > 20) {
            showToast("名称应在20字以内");
            return;
          }
          createCompany(companyName, email, name);
          break;
      }
    }
  };

  public static boolean isSpecialChar(String str) {
    String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(str);
    return m.find();
  }
  /**
   * 创建公司 TODO  创建公司成功 暂无api
   */
  private void createCompany(final String companyName, String email, String name) {
    showProgress();
    RemoteMode.getInstance()
              .createCompany(companyName, email, name)
              .subscribe(new BaseObserver<Company>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(Company company) {
                  hideProgress();
                  ToastUtils.showToast(company.getMsg());
                  User user = BaseApplication.getInstance()
                                             .getUser();
                  user.setUsername(company.getUsername());
                  user.setCompanyname(company.getCompanyname());
                  user.setCompanyid(company.getCompanyid());
                  user.setCompanyrole(company.getCompanyrole());
                  user.setRole(company.getRole());
                  user.setOprole(company.getOprole());
                  user.setCompanyexpiredate(company.getCompanyexpiredate());
                  SpUtils.setObject(USER_SP_KEY, BaseApplication.getInstance()
                                                                .getUser());
                  startMain();
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  private void startMain() {
    startActivity(new Intent(this, MainActivity.class));
    ActivityStackManager.getInstance()
                        .killActivity(RegisterSussActivity.class);
    ActivityStackManager.getInstance()
                        .killActivity(JoinCompanyActivity.class);
    finish();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_create_company;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    TextView tv_name = findViewById(R.id.tv_name);
    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.TRANSPARENT);
    SpannableString spannableString = new SpannableString("姓名姓名");
    spannableString.setSpan(foregroundColorSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    tv_name.setText(spannableString);
    findViewById(R.id.iv_back).setOnClickListener(listener);
    findViewById(R.id.btn_complete).setOnClickListener(listener);

    de_company_name = findViewById(R.id.de_company_name);
    de_company_name.addFilter(new InputFilter.LengthFilter(12));
    de_email = findViewById(R.id.de_email);
    de_name = findViewById(R.id.de_name);
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }
}
