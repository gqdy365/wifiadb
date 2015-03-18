# wifiadb
安装此apk，可以开机手机WiFi调试，方便开发，自用的demo，分享给大家；
注意：需要root权限才可以运行；

在项目开发过程中，遇到这样的场景： 
有写特殊Android设备只有一个USB口，当挂载U盘之后就无法连接USB实时进行调试了。这儿时候如果设备可以开启WiFi，那就可以用WiFi进行调试； 

要开启网络调试，执行下面指令即可： 
Java代码 
"setprop service.adb.tcp.port 5555",// 设置监听的端口，端口可以自定义，如5554，5555是默认的  
"stop adbd",// 关闭adbd  
"start adbd"// 重新启动adbd  


但执行上述指令必须要有root权限，既先要执行： 
Java代码 
su//切换到root用户；  


上面完成之后就可以用adb命令： 
Java代码 
adb connect 192.168.1.xx//Android设备的ip地址；  


上述在设备连接电脑执行cmd时在 
"stop adbd",// 关闭adbd 
这一步会有问题，执行之后USB就断开了，无法执行接下来的打开指令； 

那怎么办呢？ 

想到直接做一个apk，安装在手机上执行，于是就有了下面的apk： 

Java代码 
public void excuteStartShell() {  
    String[] commands = new String[] {   
            "setprop service.adb.tcp.port 5555",// 设置监听的端口，端口可以自定义，如5554，5555是默认的  
            "stop adbd",// 关闭adbd  
            "start adbd",// 重新启动adbd  
    };  
    try {  
        List<String> temp = RootTools.sendShell(commands, 10, 3000);  
        for (int i = 0; i < temp.size(); i++) {  
            Log.i(TAG, "__This is result from root:__" + temp.get(i));  
        }  
    } catch (Exception e) {  
        e.printStackTrace();  
    }  
}  
  
public void excuteStopShell() {  
    String[] commands = new String[] {  
            "setprop service.adb.tcp.port -1",// 设置监听的端口，端口可以自定义，如5554，5555是默认的  
            "stop adbd",// 关闭adbd  
            "start adbd",// 重新启动adbd  
    };  
    try {  
        List<String> temp = RootTools.sendShell(commands, 10, 3000);  
        for (int i = 0; i < temp.size(); i++) {  
            Log.i(TAG, "__This is result from root:__" + temp.get(i));  
        }  
    } catch (Exception e) {  
        e.printStackTrace();  
    }  
}  
