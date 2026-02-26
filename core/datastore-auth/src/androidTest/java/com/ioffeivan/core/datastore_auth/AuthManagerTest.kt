package com.ioffeivan.core.datastore_auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.datastore_auth.model.AuthDataLocal
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_DATASTORE_NAME: String = "test_auth_datastore.json"

@RunWith(AndroidJUnit4::class)
class AuthManagerTest {
    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testCoroutineDispatcher = UnconfinedTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher)

    private val testDataStore: DataStore<AuthDataLocal> =
        DataStoreFactory.create(
            serializer = AuthDataLocalSerializer,
            scope = testCoroutineScope,
            produceFile = { testContext.dataStoreFile(TEST_DATASTORE_NAME) },
        )
    private val authManager = AuthManager(testDataStore)

    @After
    fun cleanUp() {
        testContext.dataStoreFile(TEST_DATASTORE_NAME).delete()
        testCoroutineScope.cancel()
    }

    @Test
    fun isLoggedIn_whenAccessTokenIsNull_shouldReturnsFalse() {
        testCoroutineScope.runTest {
            val actual = authManager.isLoggedIn.first()

            assertThat(actual).isFalse()
        }
    }

    @Test
    fun isLoggedIn_whenAccessTokenIsNotNull_shouldReturnsTrue() {
        testCoroutineScope.runTest {
            authManager.saveAuthData(AuthDataLocal(accessToken = "token"))

            val actual = authManager.isLoggedIn.first()

            assertThat(actual).isTrue()
        }
    }

    @Test
    fun saveAuthData_shouldCorrectlySaveDataInJson() {
        testCoroutineScope.runTest {
            val expected = AuthDataLocal(accessToken = "token")

            authManager.saveAuthData(expected)
            val actual = authManager.getAuthData().first()

            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun deleteAuthData_shouldResetDataToDefaultValue() {
        testCoroutineScope.runTest {
            val expected = AuthDataLocal()
            authManager.saveAuthData(AuthDataLocal(accessToken = "token"))

            authManager.deleteAuthData()
            val actual = authManager.getAuthData().first()

            assertThat(actual).isEqualTo(expected)
        }
    }
}
