package com.jkoivist.mobilecomputingproject.data

import android.content.Context
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kotlinx.coroutines.*

@Entity(tableName = "reminders",
    indices = [
        Index("itemId", unique = true)
    ]
)
data class Reminder(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "message") var message: String,
    @ColumnInfo(name = "location_x") var location_X: Long,
    @ColumnInfo(name = "location_y") var location_Y: Long,
    @ColumnInfo(name = "reminder_time") var reminder_time: Long,
    @ColumnInfo(name = "creation_time") var creation_time: Long,
    @ColumnInfo(name = "creator_id") var creator_id: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemId")
    var itemId: Long = 0

    @ColumnInfo(name = "seen")
    var seen: Boolean = false
}

@Dao
interface ReminderDao{
    @Insert
    fun insert(reminder: Reminder)

    @Query("DELETE FROM reminders WHERE itemId = :id")
    fun deleteById(id: Long)

    @Update
    fun update(reminder: Reminder)

    @Query("SELECT * FROM reminders WHERE itemId = :id")
    fun getById(id: Long): Reminder

    @Query("SELECT * FROM reminders")
    fun getAll(): LiveData<List<Reminder>>
}

@Database(entities = [Reminder::class], version = 1)
abstract class ReminderDB: RoomDatabase(){
    abstract fun dao(): ReminderDao
        companion object{
            private var INSTANCE: ReminderDB? = null

            fun getInstance(context: Context): ReminderDB{
                synchronized(this){
                    var instance = INSTANCE

                    if(instance == null){
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            ReminderDB::class.java,
                            "reminder_database"
                        ).fallbackToDestructiveMigration().build()

                        INSTANCE = instance
                    }
                    return instance
                }
            }
        }
}

class ReminderRepo(private val dao: ReminderDao){
    val allReminders: LiveData<List<Reminder>> = dao.getAll()
    val currentReminder = MutableLiveData<Reminder>()
    private val scope = CoroutineScope(Dispatchers.Main)

    fun insert(reminder: Reminder){
        scope.launch(Dispatchers.IO){
            dao.insert(reminder)
        }
    }

    fun deleteById(id: Long){
        scope.launch(Dispatchers.IO){
            dao.deleteById(id)
        }
    }

    fun update(reminder: Reminder){
        scope.launch(Dispatchers.IO){
            dao.update(reminder)
        }
    }

    fun getById(id: Long){
        scope.launch(Dispatchers.IO){
            currentReminder.value = asyncGetById(id).await()
        }
    }

    private fun asyncGetById(id: Long): Deferred<Reminder> =
        scope.async(Dispatchers.IO){
            return@async dao.getById(id)
        }
}