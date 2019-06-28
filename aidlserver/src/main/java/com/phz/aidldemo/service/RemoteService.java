package com.phz.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.phz.aidldemo.IRemoteInterface;

public class RemoteService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(RemoteService.class.getName(),"RemoteService启动了");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(RemoteService.class.getName(),"RemoteService关闭了");
    }

    private final IRemoteInterface.Stub mBinder=new IRemoteInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int getPID() throws RemoteException {
            Log.e("TAG","收到了来自客户端的请求");
            return Process.myPid();
        }
    };
}
