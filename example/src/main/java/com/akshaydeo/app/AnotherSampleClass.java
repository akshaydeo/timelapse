package com.akshaydeo.app;

/**
 * Created by akshay.d on 17/09/17.
 */

public class AnotherSampleClass {
    void timeConsumingMethod(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        secondCall();
    }

    void secondCall(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
