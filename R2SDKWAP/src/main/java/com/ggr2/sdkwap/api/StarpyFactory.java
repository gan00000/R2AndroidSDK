package com.ggr2.sdkwap.api;

/**
 * Created by GanYuanrong on 2017/2/13.
 */

public class StarpyFactory {

    public static IStarpy create(){

        return new StarpyImpl();
    }

}
