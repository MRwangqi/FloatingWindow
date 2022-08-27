## 判断 Activity 上面是否有浮窗(Dialog、PopWindow、系统弹窗 等)


### 使用


1、判断 Activity 上面是否有浮窗

```kotlin
 val hasFloatingWindow = FloatingWindowManager.hasFloatWindowByToken(this)
```

2、获取 Activity 上所有的浮窗 view

```kotlin
 val views = FloatingWindowManager.getFloatWindowViewByToken(this)
```