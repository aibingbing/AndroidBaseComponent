package com.aibb.android.base.example.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aibb.android.base.example.room.pojo.UserEntity
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface UserEntityDao {
    @Query("select * from t_user")
    fun queryAllUser(): List<UserEntity>

    @Query("select * from t_user where name like :name limit 1")
    fun queryUserByName(name: String): UserEntity

    @Query("select * from t_user limit :limit")
    fun queryUserByLimit(limit: Int): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: UserEntity)

    @Update
    fun updateUsers(vararg users: UserEntity)

    @Delete
    fun delete(u: UserEntity)

    // 在一个事物中操作数据库
    @Transaction
    open suspend fun setLoggedInUser(loggedInUser: UserEntity) {
        delete(loggedInUser)
        insertAll(loggedInUser)
    }

    // 进行流响应式查询
    @Transaction
    @Query("select * from t_user")
    fun queryAllUserUseFlow(): Flow<List<UserEntity>>

    // 进行流响应式查询，仅在结果集发生改变时通知界面
    fun queryAllUserDistinctUntilChanged() = queryAllUserUseFlow().distinctUntilChanged()

    // kotlin coroutine 进行异步查询
    @Query("SELECT * FROM t_user")
    suspend fun queryAllUsersSuspend(): List<UserEntity>

    // 使用 LiveData 进行可观察查询
    @Query("SELECT * FROM t_user")
    fun loadAllUsersLiveData(): LiveData<List<UserEntity>>

    //  RxJava 进行响应式查询
    @Query("SELECT * FROM t_user")
    fun queryAllUsersUseRxFlowable(): Flowable<List<UserEntity>>
}