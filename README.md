# wifiadb
安装此apk，可以开机手机WiFi调试，方便开发，自用的demo，分享给大家；
注意：需要root权限才可以运行；

在项目开发过程中，遇到这样的场景： 
有写特殊Android设备只有一个USB口，当挂载U盘之后就无法连接USB实时进行调试了。这儿时候如果设备可以开启WiFi，那就可以用WiFi进行调试； 

要开启网络调试，执行下面指令即可： 
```java
"setprop service.adb.tcp.port 5555",// 设置监听的端口，端口可以自定义，如5554，5555是默认的  
"stop adbd",// 关闭adbd  
"start adbd"// 重新启动adbd  
```

但执行上述指令必须要有root权限，既先要执行： 
Java代码 
su//切换到root用户；  

[详情请查看](http://gqdy365.iteye.com/admin/blogs/2181894)
