# Android-exercise

This repository stores exercises on Android programming.
For more information: [SYSUAndroid2016](https://github.com/WideLee/SYSUAndroid2016)

## Index

1. UI Design
2. Event Handle
3. Intent, Bundle, Listview
4. Broadcast
5. Widget
6. Multithreading, service
7. I/O with SharedPreferences and file
8. I/O with databases

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
4. 自定义Adapter的使用
  1.一开始想绑定数据库, 实现后发现在删除操作后, 数据库id和查询id将会不一致, 解决方案暂时想到两种(均实验可行):
    1. 改用`CursorAdapter`, 需要实现`newView()`和`bindView()`方法, 并在删除后`swapCursor()`, 关闭原Cursor
    2. 缓存Cursor(其实就是造个CursorAdapter的轮子), 好处是可以让`CursorAdapter`隐藏, 只在删除后调用其方法`notifyDataSetChanged()`, 其余操作均调用DB类的方法即可, 类似数据绑定
5. 在实现activity_detail时, 无法将上面的View高度设为1/3, 最后只能手动设置高度

---

Project_4遇到的问题:

1. 产生通知时,`setLargeIcon()`需要用`BitmapFactory`产生bitmap
2. Manifest里面需要将页面设置成`SingleInstance`, 否则跳转时会出现多个实例, 多次返回才能退回

---

Project_6遇到的问题:

1. 若音乐为本地文件，如在~/Music/ 目录下，那么在读取文件时会有权限问题。解决方法使使用`ActivityCompat.requestPermissions();` 解决，或者手动在设置里面打开所需权限，注意需要在Manifest里要声明所需权限。
2. 使用上述方法会在用户第一次打开应用时弹出提示框，并让用户授权文件读写权限，不过这并不会阻塞MainActivty的线程，因此如果在onCreate中完成授权和加载文件的话会导致在用户还没点击授权时就执行了加载代码，导致第一次播放失败，需要重启应用。一开始的解决方法是将音乐加载放在play按钮中完成，不过这样需要改动较大的逻辑。后来发现可以重载获得权限后的回调函数，本想着可以将逻辑改回来，不过又有stop后不会立即停止的问题，于是只能在每次play时判断是否已经加载了文件，没有则加载。
3. 点击返回按钮会导致service销毁（home按钮不会），导致溢出。解决方法时重载返回按钮，使其只调回桌面而不退出。
4. 停止后再次播放会导致播放卡顿，手动暂停后再停止不会，代码中直接暂停停止再次播放也会卡顿，但是在停止并调用prepare()后将进度手动设置为0可以解决。对于这一现象暂时不知原因，如果说stop()后并不会将进度归0，那么为何手动暂停后再停止就不会出现这个问题？
5. 接4，stop()后设置进度归0，但是音乐不会马上停止，而是播放开头1s后才停止。手动暂停再停止不会有这个问题。解决方法是stop()后立即reset()，每次play()时重新加载文件...

---

Project_8遇到的问题:

1. 通讯录存储实际上也是用数据库，在调用`getContentResolver().query()`方法时本质上是生成了SQL语句。这句SQL的结构是：`select projection from table where ( selection ) order by order`其中，与其参数对应关系为：
　　* url - table；
　　* projection - projection；
　　* selection - selection；
　　* selectionArgs - 占位符参数；
  * sortOrder - order；
　 * 注意，where语句中的两个括号是系统加的（可用SQL注入加入`group by`）。

