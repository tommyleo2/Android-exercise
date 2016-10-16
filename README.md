# Android-exercise

This repository stores exercises on Android programming.
For more information: [SYSUAndroid2016](https://github.com/WideLee/SYSUAndroid2016)

## Index

1. UI Design
2. Event Handle
3. Intent, Bundle, Listview

...

---

Project_3:

1. 隐藏ActionBar: 需要使用AppCompact, 在style中将AppTheme的parent设置为 `Theme.AppCompat.Light.NoActionBar`
2. SQLite使用: 
  1. 继承`SQLiteOpenHelper`, 使用时调用该类的`getReadableDatabase()`或`getWritableDatabase()`
  2. 得到SQLiteDatabase后, 使用`execSQL()`, `query()`, `rawQuery()`等方法CRUD
3. 调试:
  1. 使用adb操作手机CLI, sqlite3操作数据库
  2. 导入数据库后需要修改模拟器中.db的权限和用户组, 否则无法打开.db文件
