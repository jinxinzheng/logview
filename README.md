# LogView

A trivial tool that displays logs on an activity.

# How to use

In your BaseActivity (required to be a FragmentActivity),

```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            LogView.install(this)
        }
    }
```

You can add any logs using `LogView.addLog()`.

Currently it supports OkHttpClient logging by the HttpLoggingInterceptor.
Add the logging interceptor to your OkHttpClient.Builder

```kotlin
    builder.addLogViewInterceptor()
```

Or, if you can't access the builder, but only have the OkHttpClient, we can also
set it up for you (via reflection),

```kotlin
    okHttpClient.addLogViewInterceptor()
```

Thanks to [MvRx](https://github.com/airbnb/MvRx) and
[Epoxy](https://github.com/airbnb/epoxy), for that they make life so much easier.
