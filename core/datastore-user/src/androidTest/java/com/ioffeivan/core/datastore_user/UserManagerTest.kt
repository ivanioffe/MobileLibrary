package com.ioffeivan.core.datastore_user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.datastore_user.model.UserLocal
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_DATASTORE_NAME: String = "test_user_datastore.json"

@RunWith(AndroidJUnit4::class)
class UserManagerTest {
    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testCoroutineDispatcher = UnconfinedTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher)

    private val testDataStore: DataStore<UserLocal> =
        DataStoreFactory.create(
            serializer = UserLocalSerializer,
            scope = testCoroutineScope,
            produceFile = { testContext.dataStoreFile(TEST_DATASTORE_NAME) },
        )
    private val userManager = UserManager(testDataStore)

    @After
    fun cleanUp() {
        testContext.dataStoreFile(TEST_DATASTORE_NAME).delete()
        testCoroutineScope.cancel()
    }

    @Test
    fun saveUser_shouldCorrectlySaveDataInJson() {
        testCoroutineScope.runTest {
            val expected = UserLocal(name = "name", email = "email")

            userManager.saveUser(expected)
            val actual = userManager.getUser().first()

            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun deleteUser_shouldResetDataToDefaultValue() {
        testCoroutineScope.runTest {
            val expected = UserLocal()
            userManager.saveUser(UserLocal("name", "email"))

            userManager.deleteUser()
            val actual = userManager.getUser().first()

            assertThat(actual).isEqualTo(expected)
        }
    }
}
