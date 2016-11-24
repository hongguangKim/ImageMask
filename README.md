# ImageMask
通过蒙版画出各种形状的view
# Demo
![IMAGE DEMO](https://raw.githubusercontent.com/hongguangKim/ImageMask/master/demo/mask.PNG)
# Source
```
      <com.example.view.SVGBoard
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scaleType="centerCrop"
        custom:row="4"
        custom:colum= "5"
        custom:svg_raw_resource="@raw/shape_heart"
        android:src="@drawable/dd" />
```
可以  <com.example.view.SVGBoard />部分更换为CircleBoard那么就是圆形图形会展示出来
