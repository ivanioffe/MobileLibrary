-keepattributes Signature, EnclosingMethod, InnerClasses

-keep class com.ioffeivan.core.common.result.DataResult { *; }
-keep class com.ioffeivan.core.common.result.DataResult$* { *; }

-keep class com.ioffeivan.core.common.error.AppError { *; }
-keep class com.ioffeivan.core.common.error.AppError$* { *; }

-keep class com.ioffeivan.core.network.call_adapter.factory.DataResultCallAdapterFactory { *; }

-keep @retrofit2.http.* interface * { *; }
-keepclassmembers interface * {
    @retrofit2.http.* <methods>;
}

-keep class com.ioffeivan.feature.**.data.**Dto { *; }
