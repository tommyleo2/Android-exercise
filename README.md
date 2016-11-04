# Android-exercise

This repository stores exercises on Android programming.
For more information: [SYSUAndroid2016](https://github.com/WideLee/SYSUAndroid2016)

## Index

1. UI Design
2. Event Handle
3. Intent, Bundle, Listview
4. Broadcast
5. Widget

...

---

Project_3遇到的问题:

1. 隐藏ActionBar: 需要使用AppCompact, 在style中将AppTheme的parent设置为 `Theme.AppCompat.Light.NoActionBar`
2. SQLite使用: 
  1. 继承`SQLiteOpenHelper`, 使用时调用该类的`getReadableDatabase()`或`getWritableDatabase()`
  2. 得到SQLiteDatabase后, 使用`execSQL()`, `query()`, `rawQuery()`等方法CRUD
3. 调试:
  1. 使用adb操作手机CLI, sqlite3操作数据库
  2. 导入数据库后需要修改模拟器中.db的权限和用户组, 否则无法打开.db文件
4.自定义Adapter的使用
  1.一开始想绑定数据库, 实现后发现在删除操作后, 数据库id和查询id将会不一致, 解决方案暂时想到两种(均实验可行):
    1. 改用`CursorAdapter`, 需要实现`newView()`和`bindView()`方法, 并在删除后`swapCursor()`, 关闭原Cursor
    2. 缓存Cursor(其实就是造个CursorAdapter的轮子), 好处是可以让`CursorAdapter`隐藏, 只在删除后调用其方法`notifyDataSetChanged()`, 其余操作均调用DB类的方法即可, 类似数据绑定
5.在实现activity_detail时, 无法将上面的View高度设为1/3, 最后只能手动设置高度

---

Project_4遇到的问题:

1. 产生通知时,`setLargeIcon()`需要用`BitmapFactory`产生bitmap
2. Manifest里面需要将页面设置成`SingleInstance`, 否则跳转时会出现多个实例, 多次返回才能退回
