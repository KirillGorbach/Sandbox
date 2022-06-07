package com.sandbox.fragments.room

import android.app.Application
import androidx.room.*


@Dao
interface UsersDao {

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<UserDB>

    @Query("SELECT * FROM User WHERE age >= :age ORDER BY age")
    fun getOldPeopleBeers(age: Int): List<UserDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserDB)

    @Query("DELETE FROM User")
    fun deleteAllUsers()
}

@Entity(tableName = "User")
data class UserDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int
)

@Database(entities = [UserDB::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun getUsersDao(): UsersDao
}


class App : Application() {
    private var database: UsersDatabase? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            this,
            UsersDatabase::class.java,
            "database"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getDatabase(): UsersDatabase? {
        return database
    }

    

    companion object {
        var instance: App? = null
    }
}
