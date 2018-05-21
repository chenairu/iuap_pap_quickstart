package com.yonyou.iuap.example.supervise.support;
 /*
  * Created with Intellij IDEA
  * USER: 焦一平
  * Mail: jiaoyiping@gmail.com
  * Date: 2016/10/22
  * Time: 22:43
  * To change this template use File | Settings | Editor | File and Code Templates
 */

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.PrintWriter;

public class AsyncRequestProcessor implements Runnable {
    private AsyncContext asyncContext;
    private int milliseconds;

    public AsyncRequestProcessor() {

    }

    public AsyncRequestProcessor(AsyncContext asyncContext, int milliseconds) {
        this.asyncContext = asyncContext;
        this.milliseconds = milliseconds;
    }

    @Override
    public void run() {
//        System.out.println("Async Supported? " + asyncContext.getRequest().isAsyncSupported());
        longProcessing(milliseconds);
        try {
            PrintWriter out = asyncContext.getResponse().getWriter();
            out.write("Processing done for " + milliseconds + " milliseconds!!\n");
            out.flush();
//            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        asyncContext.complete();
    }

    private void longProcessing(int secs) {
        // wait for given time before finishing
        try {
            Thread.sleep(secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}