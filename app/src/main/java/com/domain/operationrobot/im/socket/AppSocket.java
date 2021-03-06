package com.domain.operationrobot.im.socket;

import android.text.TextUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.im.listener.IConstants;
import com.google.gson.Gson;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author silencezwm on 2017/8/25 上午11:12
 * @email silencezwm@gmail.com
 * @description AppSocket
 */
public class AppSocket extends BaseSocket {

  private static volatile AppSocket INSTANCE = null;

  private AppSocket(Builder builder) {
    super(builder);
    INSTANCE = this;
  }

  public static AppSocket getInstance() {
    if (INSTANCE == null) {
      throw new NullPointerException("must first call the build() method");
    }
    return INSTANCE;
  }

  public static AppSocket init(Builder builder) {
    return new AppSocket(builder);
  }

  /**
   * fasong机器人消息
   */
  public void sendMessage(int type, String content, ArrayList<String> ids) {
    try {
      JSONObject jsonObject = new JSONObject();
      JSONObject jsonObject1 = new JSONObject();
      jsonObject.put("msgid", String.valueOf(System.currentTimeMillis()));
      jsonObject.put("token", BaseApplication.getInstance()
                                             .getUser()
                                             .getToken());
      jsonObject.put("type", type);
      jsonObject.put("msg", content);
      if (ids != null && ids.size() > 0) {
        JSONArray jsonArray = new JSONArray(new Gson().toJson(ids));
        jsonObject.put("ids", jsonArray);
      }
      String companyid = BaseApplication.getInstance()
                                        .getUser()
                                        .getCompanyid();
      jsonObject.putOpt("companyid", companyid);
      jsonObject1.putOpt("data", jsonObject);
      mSocket.emit(IConstants.CHAT_BOT, jsonObject1);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * fasong机器人消息,重启服务器 其他人点击重启服务器
   */
  public void sendRobotMessage(String hosdip) {
    User user = BaseApplication.getInstance()
                               .getUser();
    try {
      JSONObject jsonObject = new JSONObject();
      JSONObject jsonObject1 = new JSONObject();
      jsonObject.put("token", user.getToken());
      jsonObject.put("msgid", String.valueOf(System.currentTimeMillis()));
      jsonObject.put("type", 11);
      JSONObject jsonObject2 = new JSONObject();
      jsonObject2.put("hostip", hosdip);
      jsonObject2.put("msg", "我需要执行 重启服务器 " + hosdip + "（hostip：" + hosdip + "）" + "的命令");
      jsonObject2.put("msgid", String.valueOf(System.currentTimeMillis()));

      String companyid = BaseApplication.getInstance()
                                        .getUser()
                                        .getCompanyid();
      jsonObject.putOpt("companyid", companyid);
      jsonObject.put("rootbean", jsonObject2);
      jsonObject1.putOpt("data", jsonObject);
      mSocket.emit(IConstants.CHAT_BOT, jsonObject1);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * fasong机器人消息,重启服务器 同意拒绝 运维用户自己点击重启
   */
  public void sendRobotMessage11(String hosdip, String action, String msgId) {
    try {
      User user = BaseApplication.getInstance()
                                 .getUser();
      JSONObject jsonObject = new JSONObject();
      JSONObject jsonObject1 = new JSONObject();
      jsonObject.put("token", BaseApplication.getInstance()
                                             .getUser()
                                             .getToken());
      jsonObject.put("msgid", String.valueOf(System.currentTimeMillis()));
      jsonObject.put("type", 10);
      JSONObject jsonObject2 = new JSONObject();
      jsonObject2.put("hostip", hosdip);
      String msg = "重启服务器 " + hosdip + "（hostip:" + hosdip + "）";
      jsonObject2.put("msg", msg);
      jsonObject2.put("action", action);

      jsonObject2.put("msgid", msgId);

      String companyid = user.getCompanyid();
      jsonObject.putOpt("companyid", companyid);
      jsonObject.put("rootbean", jsonObject2);
      jsonObject1.putOpt("data", jsonObject);
      mSocket.emit(IConstants.CHAT_BOT, jsonObject1);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * fasong机器人消息,重启服务器 同意拒绝 运维用户自己点击重启
   */
  public void sendRobotMessage12(String hosdip, String action, String msgId) {
    try {
      User user = BaseApplication.getInstance()
                                 .getUser();
      JSONObject jsonObject = new JSONObject();
      JSONObject jsonObject1 = new JSONObject();
      jsonObject.put("token", BaseApplication.getInstance()
                                             .getUser()
                                             .getToken());
      jsonObject.put("msgid", String.valueOf(System.currentTimeMillis()));
      jsonObject.put("type", 10);
      JSONObject jsonObject2 = new JSONObject();
      jsonObject2.put("hostip", hosdip);
      String msg = action.equals("agree") ? "同意 重启服务器 " + hosdip + "（hostip:" + hosdip + "）的命令" : "拒绝 重启服务器 " + hosdip + "（hostip:" + hosdip + "）的命令";
      jsonObject2.put("msg", msg);
      jsonObject2.put("action", action);

      jsonObject2.put("msgid", msgId);

      String companyid = user.getCompanyid();
      jsonObject.putOpt("companyid", companyid);
      jsonObject.put("rootbean", jsonObject2);
      jsonObject1.putOpt("data", jsonObject);
      mSocket.emit(IConstants.CHAT_BOT, jsonObject1);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * 发送消息
   */
  public void sendMessage(String content, ArrayList<String> ids) {

    try {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("token", BaseApplication.getInstance()
                                             .getUser()
                                             .getToken());
      jsonObject.put("msgid", String.valueOf(System.currentTimeMillis()));
      if (ids != null && ids.size() > 0) {
        JSONArray jsonArray = new JSONArray(new Gson().toJson(ids));
        jsonObject.put("ids", jsonArray);
      }
      String companyid = BaseApplication.getInstance()
                                        .getUser()
                                        .getCompanyid();
      jsonObject.putOpt("companyid", companyid);
      jsonObject.put("msg", content);
      mSocket.emit(IConstants.TALK, jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * 发送给机器人消息
   */
  public void connTest() {
    mSocket.emit(IConstants.CONN, "...");
  }

  /**
   * 加入房间
   */
  public void setConnSure() {
    try {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("token", BaseApplication.getInstance()
                                             .getUser()
                                             .getToken());
      jsonObject.put("msgid", String.valueOf(System.currentTimeMillis()));
      String companyid = BaseApplication.getInstance()
                                        .getUser()
                                        .getCompanyid();
      jsonObject.put("msg", "joinroom");
      jsonObject.putOpt("companyid", companyid);
      mSocket.emit(IConstants.TALK, jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * 输入中
   */
  public void typing() {
    mSocket.emit(IConstants.TYPING);
  }

  /**
   * 停止输入
   */
  public void stopTyping() {
    mSocket.emit(IConstants.STOP_TYPING);
  }
}
