package com.ioffeivan.core.network.call_adapter.factory

import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.call_adapter.DataResultCallAdapter
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class DataResultCallAdapterFactory private constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        // Check if the return type is a Call. If not, this factory cannot handle it.
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        // Extract the inner type (Foo) from Call<Foo>. This is DataResult<T>.
        val callType = getParameterUpperBound(0, returnType as ParameterizedType)

        // Check if the inner type is 'DataResult' (Call<DataResult<T>>).
        if (getRawType(callType) != DataResult::class.java) {
            return null
        }

        // Extract the inner type T from DataResult<T>
        val resultType = getParameterUpperBound(0, callType as ParameterizedType)

        return DataResultCallAdapter<Any>(resultType)
    }

    // Helper method to create an instance of the factory.
    companion object {
        fun create(): DataResultCallAdapterFactory = DataResultCallAdapterFactory()
    }
}
