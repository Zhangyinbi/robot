package com.domain.operationrobot.app.company;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.ApplyInfo;
import com.domain.operationrobot.http.data.RemoteMode;
import com.google.gson.Gson;

/**
 *
 */
public class UserApplyActivity extends AbsActivity {

  private RecyclerView     rlv_recycler;
  private UserApplyAdapter mUserApplyAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_user_apply;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
    rlv_recycler = findViewById(R.id.rlv_recycler);
    mUserApplyAdapter = new UserApplyAdapter(this);
    rlv_recycler.setLayoutManager(new LinearLayoutManager(this));
    rlv_recycler.setAdapter(mUserApplyAdapter);
  }

  @Override
  protected void initData() {
    showProgress();
    RemoteMode.getInstance()
              .getJoinInfo()
              .subscribe(new BaseObserver<ApplyInfo>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(ApplyInfo applyInfo) {
                  hideProgress();
                  mUserApplyAdapter.setData(applyInfo.getInfo());
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  @Override
  public void showEmptyView() {

  }

  /**
   * 处理请求
   */
  public void dispose(int action, String request_userid) {
    showProgress();
    RemoteMode.getInstance()
              .disposeJoinInfo(action, request_userid)
              .subscribe(new BaseObserver<ApplyInfo>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(ApplyInfo applyInfo) {
                  hideProgress();
                  initData();
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }
}
