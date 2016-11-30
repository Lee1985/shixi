package com.bluemobi.www.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 向ajax传值工具类
 * @Description
 * @Author zhangliang
 * @Date 2014-11-11 上午11:10:22
 * @Copyright：上海科匠信息科技有限公司（沈阳研发部） 2014
 * @Version v1.0
 */
public class PrintWriters {
    public static void write(HttpServletResponse response, String json) {
        PrintWriter out = null;
        response.setCharacterEncoding("utf-8");
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(json);// mess为返回到jsp页面的值
        out.flush();
        out.close();
    }
}
