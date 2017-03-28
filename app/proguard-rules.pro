# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android_stdio_develop_tool\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#基础配置
# 保持哪些类不被混淆
# 系统组件
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
#shrink，测试后发现会将一些无效代码给移除，即没有被显示调用的代码，该选项 表示 不启用 shrink。
-dontshrink
#不启用优化  不优化输入的类文件
-dontoptimize
#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification
#环信
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
#okio
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}
#shapeimageview
-dontwarn com.github.siyamed.shapeimageview.**
-keep class com.github.siyamed.shapeimageview.**{*;}
-keep interface com.github.siyamed.shapeimageview.**{*;}
#eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep interface okhttp3.**{*;}
-dontwarn org.apache.**
-dontwarn android.support.**
#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}