package com.ioffeivan.core.network.call_adapter

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.error.mapToCommonError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.utils.toDataResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

internal class DataResultCallAdapter<T>(
    private val responseType: Type,
) : CallAdapter<T, Call<DataResult<T, AppError>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Call<DataResult<T, AppError>> {
        return ResultCall(call)
    }
}

private class ResultCall<T>(
    private val call: Call<T>,
) : Call<DataResult<T, AppError>> {
    override fun enqueue(callback: Callback<DataResult<T, AppError>>) {
        call.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val dataResult =
                        try {
                            response.toDataResult()
                        } catch (t: Throwable) {
                            createErrorDataResult(t)
                        }

                    callback.onResponse(this@ResultCall, Response.success(dataResult))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    val dataResult = createErrorDataResult(t)

                    callback.onResponse(this@ResultCall, Response.success(dataResult))
                }
            },
        )
    }

    override fun execute(): Response<DataResult<T, AppError>> = throw NotImplementedError()

    override fun clone(): Call<DataResult<T, AppError>> = ResultCall(call.clone())

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

    override fun isExecuted(): Boolean = call.isExecuted

    override fun isCanceled(): Boolean = call.isCanceled

    override fun cancel() = call.cancel()

    private fun createErrorDataResult(t: Throwable): DataResult.Error<AppError> {
        return DataResult.Error(t.mapToCommonError())
    }
}
