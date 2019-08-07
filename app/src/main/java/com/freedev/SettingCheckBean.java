package com.freedev;

/**
 * @Author: jakezhang
 * Company:DHC
 * Description： 设置页面，左右点击选择的选项bean
 * Date: 2019/6/14 15:38
 */
public class SettingCheckBean extends SettingBaseBean {
    private int id;
    private boolean leftClickable;//该项左侧是否可点
    private boolean rightClickable;
    private String content;//该项内容

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public boolean isLeftClickable() {
        return leftClickable;
    }

    public void setLeftClickable(boolean leftClickable) {
        this.leftClickable = leftClickable;
    }

    public boolean isRightClickable() {
        return rightClickable;
    }

    public void setRightClickable(boolean rightClickable) {
        this.rightClickable = rightClickable;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
