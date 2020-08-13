package com.jsh.tenqube

import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.jsh.tenqube.data.db.TenqubeDatabase
import com.jsh.tenqube.data.source.shop.local.DataShop.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@SmallTest
class ShopDaoTest {

    private lateinit var database: TenqubeDatabase
    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TenqubeDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertShop_getShopById_returnShopName() = runBlockingTest {
        // GIVEN - insert a Shop entity
        val shop = LocalShopModel("0", "myshop", "url")
        database.shopDao().insertShop(shop)

        // WHEN - Get the shop by id from the database
        val loaded = database.shopDao().getShopById(shop.id)

        // THEN - The loaded data contains the expected values
        MatcherAssert.assertThat<LocalShopModel>(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.shopName, `is`(shop.shopName))
    }

}