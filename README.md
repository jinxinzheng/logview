# LogView

A trivial tool that displays logs on an activity. You can virtually add any
log to it. Now it only supports OkHttpClient logging by the HttpLoggingInterceptor.

# How to use

In your BaseActivity (required to be a FragmentActivity),

```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Config.isDebug) {
            LogView.install(this)
        }
    }
```

Add the logging interceptor to your OkHttpClient.Builder

```kotlin
    builder.addLogViewInterceptor()
```

Or, if you can't access the builder, but only have the OkHttpClient, we can also
set it up for you (via reflection).

```kotlin
    okHttpClient.addLogViewInterceptor()
```

You can add any logs using `LogView.addLog()`.

Thanks to [MvRx](https://github.com/airbnb/MvRx) and
[Epoxy](https://github.com/airbnb/epoxy), for that they make life so much easier.
