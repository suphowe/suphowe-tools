package com.soft.method.dll;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * 动态库调用
 * @author suphowe
 */
public interface JnaDll extends Library {

    JnaDll INSTANCEDLL  = (JnaDll)Native.loadLibrary("JNATestDLL",JnaDll.class);
    public int add(int a, int b);
    public int factorial(int n);


}
