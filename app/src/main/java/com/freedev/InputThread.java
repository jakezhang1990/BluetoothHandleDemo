package com.freedev;

import android.app.Instrumentation;
import android.view.KeyEvent;

/**
 *模拟输入 线程
 */
public class InputThread extends Thread {
    public InputThread() {
    }

    @Override
    public void run() {
        int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_N, KeyEvent.KEYCODE_I, KeyEvent.KEYCODE_SPACE, KeyEvent.KEYCODE_H, KeyEvent.KEYCODE_A, KeyEvent.KEYCODE_O};
        simulationInput(keyCodeArray);

    }


    /**
     * 模拟输入事件
     *
     * @param keyCodeArray 需要模拟输入的内容
     */
    private void simulationInput(int[] keyCodeArray) {
        for (int keycode : keyCodeArray) {
            try {
                Instrumentation inst = new Instrumentation();
                inst.sendKeyDownUpSync(keycode);
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


}
