# BluetoothHandleDemo
Android手机蓝牙连接手柄，摇杆和十字按键控制手机焦点，并给选中项焦点反馈效果。

预览效果图片：
![](https://github.com/jakezhang1990/BluetoothHandleDemo/blob/master/screenshot.png)


解决的关键点在Activity的onKeyDown这个方法。得到相应的 keyCode,便可以监听。true表示消费该事件，消费之后就不会再继续向下传递了，false和返回父类的onKeyDown方法都表示不消费继续传递。

在这个方法中根据keyCode来判断上下左右手势，根据哪个控件获取了焦点，来控制事件逻辑。


```
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT&&item2_leftBtn.hasFocus()){
            //码率左摇杆
            bitRatLeftRock();
            return true;
        }else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT&&item2_rightBtn.hasFocus()){
            //码率右摇杆
            bitRatRightRock();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
```


    参考链接：https://www.jianshu.com/p/c0a76cc496fb
    官方说明：
    https://developer.android.google.cn/reference/android/view/MotionEvent.html#AXIS_LTRIGGER
    亚马逊的一篇文章：
    https://developer.amazon.com/zh/docs/fire-tv/getting-started-developing-apps-and-games.html
    
    
    
## 解决手柄焦点控制在android9.0上乱跑的bug。
1.左右按钮.setEnable();替换为左右按钮.setSelected(false);

2.将drawable中xml中控制不同图片展示的state_enable替换为state_selected。

因为在android9.0上，state_enable如果设置了false，该控件会直接失去焦点，如果当前控件从state_enable的true变为false，焦点会直接跑掉，下一次该state_enable为false的控件也不会获取到手柄焦点。
