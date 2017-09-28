# PanelView

## 使用

### 效果
![image](http://note.youdao.com/yws/api/personal/file/5034EEB9077B428CA0C101851F10D694?method=download&shareKey=105eab37ca310dabe7ca68dbbb2c2a47)

### 一 添加依赖
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
	        compile 'com.github.Yuyang1257312385:PanelView:1.0.0'
	}
```
### 二 布局中添加,==目前外面要套一层linearlayout==
```
<LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">
        <com.lyj.panel.PanelView
            android:id="@+id/view_panel"
            android:layout_width="300dp"
            android:layout_height="200dp"
            ></com.lyj.panel.PanelView>
    </LinearLayout>
```
### 二 方法

```
//设置最大值
mPanelView.setMaxValue(5000);
 //设置当前值
 mPanelView.setCurrentValue(3000);
  //==开始动画==
  mPanelView.start()


 //设置阶梯颜色
  mPanelView.setStepColorList(new String[]{"#ffffff","#000000"});
 // 设置刻度背景画笔颜色
 mPanelView.setScaleBgColor("#00ff00");
 // 设置刻度颜色  若设置此项，阶梯颜色不生效
 mPanelView.setScaleColor("#ff0000");
 //设置跑完全程的时间
 mPanelView.setMaxDuration(3000)

 ```
 
### TODO
- 目前只能在LinearLayout中使用，在相对布局中onMeasure()有问题
- 自定义属性抽取