## 判断 Activity 上面是否有浮窗(Dialog、PopWindow 等)

### 初始化

Application 初始化：

```kotlin
 override fun onCreate() {
    super.onCreate()
    FloatingWindowManager.init(this)
}
```

### 使用

1、判断 Activity 上面是否有浮窗

```kotlin
 val hasFloatingWindow = FloatingWindowManager.hasFloatingWindow(this)
```

2、获取 Activity 上所有的浮窗 view

```kotlin
 val views = FloatingWindowManager.getFloatWindowView(this)
```