package com.yonyou.iuap.example.supervise.support;
 /*
  * Created with Intellij IDEA
  * USER: 焦一平
  * Mail: jiaoyiping@gmail.com
  * Date: 2016/10/22
  * Time: 22:48
  * To change this template use File | Settings | Editor | File and Code Templates
 */

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.PrintWriter;

@WebListener
public class AppAsyncListener implements AsyncListener {
    @Override
    public void onComplete(AsyncEvent event) throws IOException {
//        System.out.println("AppAsyncListener complete");
        PrintWriter out = event.getAsyncContext().getResponse().getWriter();
        out.write("AppAsyncListener complete");
        out.flush();
        out.close();
    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        ServletResponse response = event.getAsyncContext().getResponse();
        PrintWriter out = response.getWriter();
        out.write("TimeOut Error in Processing");
        out.flush();
        out.close();

    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
//        System.out.println("AppAsyncListener error");
        ServletResponse response = event.getAsyncContext().getResponse();
        PrintWriter out = response.getWriter();
        out.write("error on processing");
        out.flush();
        out.close();
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {
        System.out.println("AppAsyncListener start");
    }
}