package com.gino.method;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.List;

/**
 * @author gino
 * Created on 2018/5/30
 */
public class MethodHandleTest {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        // find method
        MethodHandle mh = lookup.findStatic(MethodHandleTest.class, "doubleVal", MethodType.methodType(int.class, int.class));

        List<Integer> dataList = Arrays.asList(1, 2, 3, 4, 5);
        MethodHandleTest.transform(dataList, mh);

        for (Integer data : dataList) {
            System.out.println(data);
        }
    }

    static void transform(List<Integer> dataList, MethodHandle handle) throws Throwable {
        for (int i = 0; i < dataList.size(); i++) {
            // invoke method
            dataList.set(i, (Integer) handle.invoke(dataList.get(i)));
        }
    }

    private static int doubleVal(int val) {
        return val * 2;
    }


}
