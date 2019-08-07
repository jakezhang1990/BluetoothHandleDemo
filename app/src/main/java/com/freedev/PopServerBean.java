package com.freedev;

/**
 * @Author: jakezhang
 * Company:DHC
 * Description： 描述内容
 * Date: 2019/7/22 17:48
 */
public class PopServerBean {
    private int id;
    private String serverName;
    private String ip;
    private boolean editble;

    public boolean isEditble() {
        return editble;
    }

    public void setEditble(boolean editble) {
        this.editble = editble;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
