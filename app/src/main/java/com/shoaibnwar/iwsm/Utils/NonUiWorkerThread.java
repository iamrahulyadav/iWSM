package com.shoaibnwar.iwsm.Utils;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by gold on 6/25/2018.
 */

public class NonUiWorkerThread extends HandlerThread
{
    private Handler mWorkerHandler;

    public NonUiWorkerThread(String name) {
        super(name);
    }

    public NonUiWorkerThread(String name, int priority) {
        super(name, priority);
    }

    public void postTask(Runnable task)
    {
        mWorkerHandler.post(task);

    }

    public void prepareHandler()
    {
        mWorkerHandler = new Handler((getLooper()));
    }
}
