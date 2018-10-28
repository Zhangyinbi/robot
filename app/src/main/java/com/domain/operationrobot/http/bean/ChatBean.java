package com.domain.operationrobot.http.bean;

import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.im.bean.RootMessage2;
import com.domain.operationrobot.im.bean.RootMessage34;
import com.domain.operationrobot.im.bean.RootMessage6;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/21 16:40
 */
public class ChatBean {
  public  long   time;
  public  String userName;
  public  String content;
  public  String url;
  private String token;
  private int type = -1;//默认正常聊天信息  1-> 机器人消息 2
  private ArrayList<RootMessage2.Action> actions;

  /**
   * 磁盘
   */
  private ArrayList<RootMessage6.Action>  actions6;
  /**
   * cpu 和 内存
   */
  private ArrayList<RootMessage34.Action> actions34;

  public ArrayList<RootMessage34.Action> getActions34() {
    return  actions34;
  }

  public ArrayList<RootMessage6.Action> getActions6() {
    return actions6;
  }

  public void setActions6(ArrayList<RootMessage6.Action> actions6) {
    this.actions6 = actions6;
  }

  public ArrayList<RootMessage2.Action> getActions() {
    return actions;
  }

  public void setActions(ArrayList<RootMessage2.Action> actions) {
    this.actions = actions;
  }

  public void setActions34(ArrayList<RootMessage34.Action> actions34) {
    this.actions34 = actions34;
  }

  public String getToken() {
    return token == null ? "" : token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getUrl() {
    return url == null ? "" : url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getContent() {
    return content == null ? "" : content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getUserName() {
    return userName == null ? "" : userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "ChatBean{" + "userName='" + userName + '\'' + ", content='" + content + '\'' + '}';
  }
}
