package com.phz.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.phz.aidldemo.IRemoteInterface;

public class MainActivity extends AppCompatActivity {
    private final String TAG=MainActivity.class.getName();
    private IRemoteInterface iRemoteInterface;
    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iRemoteInterface=IRemoteInterface.Stub.asInterface(iBinder);
            Log.e(TAG, "onServiceConnected:服务连接成功",null );
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "onServiceConnected:服务断开连接",null );
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //新版本（5.0后）必须显式intent启动 绑定服务
//        Intent intent=new Intent();
//        ComponentName componentName =new ComponentName("com.phz.aidldemo","com.phz.aidldemo.service.RemoteService");//pkg应用包名（见配置文件manifest），cls服务名称
//        intent.setComponent(componentName);
//        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

        //新版本（5.0后）必须显式intent启动 绑定服务
        Intent intent1=new Intent();
        intent1.setPackage("com.phz.aidldemo");// 从源码中的逻辑来看的话,判断一个intent是不是显式声明的点就是component和package,只要这两个有一个生效就不算是隐式声明的
        bindService(intent1,mConnection, Context.BIND_AUTO_CREATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (iRemoteInterface!=null){
                   try {
                       Log.e(TAG,iRemoteInterface.getPID()+"端口号");
                       Toast.makeText(MainActivity.this,iRemoteInterface.getPID()+"端口号",Toast.LENGTH_LONG).show();
                   } catch (RemoteException e) {
                       e.printStackTrace();
                   }
               }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        mConnection=null;
        iRemoteInterface=null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
