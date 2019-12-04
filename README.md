# RoundCorners
比较常用的ViewGroup和View的圆角实现，一发治好设计的圆角病。
### 效果预览

|![](images/01.png)|![](images/02.png)|![](images/03.png)|
|---|---|---|
|![](images/04.png)|![](images/05.png)|![](images/06.png)|

### 特点
* LinearLayout、RelativeLayout、FrameLayout支持圆角
* ImageView、TextView、View支持圆角
* 使用xml进行配置，使用简单
* ......


### 基本用法
**Step 1. 添加JitPack仓库**
在项目根目录下的 `build.gradle` 中添加仓库:
``` gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
**Step 2. 添加项目依赖**
``` gradle
dependencies {
    implementation 'com.github.KuangGang:RoundCorners:v1.0.0'
}
```
**Step 3. 在布局文件中添加需要的RoundCorners**
```
<com.kproduce.roundcorners.RoundImageView
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:scaleType="centerCrop"
    android:src="@mipmap/ic_test"
    app:rRadius="30dp"/>

<com.kproduce.roundcorners.RoundTextView
    android:layout_width="200dp"
    android:layout_height="100dp"
    android:background="@android:color/holo_blue_dark"
    android:gravity="center"
    android:text="Hello!"
    android:textColor="@android:color/white"
    android:textSize="40sp"
    app:rRightRadius="30dp" />

<com.kproduce.roundcorners.RoundRelativeLayout
    android:layout_width="200dp"
    android:layout_height="200dp"
    app:rTopRightRadius="30dp"
    app:rBottomRightRadius="30dp">

    <View
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/holo_blue_dark" />
</com.kproduce.roundcorners.RoundRelativeLayout>

……
```

### 支持的属性、方法
|属性名|含义|默认值
|---|---|---|
|rRadius|统一设置四个角的圆角半径|0dp
|rLeftRadius|左边两个角圆角半径|0dp
|rRightRadius|右边两个角圆角半径|0dp
|rTopRadius|上边两个角圆角半径|0dp
|rBottomRadius|下边两个角圆角半径|0dp
|rTopLeftRadius|左上角圆角半径|0dp
|rTopRightRadius|右上角圆角半径|0dp
|rBottomLeftRadius|左下角圆角半径|0dp
|rBottomRightRadius|右下角圆角半径|0dp