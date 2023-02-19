package com.jkoivist.mobilecomputingproject

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jkoivist.mobilecomputingproject.data.Reminder
import com.jkoivist.mobilecomputingproject.data.ReminderDB
import com.jkoivist.mobilecomputingproject.data.ReminderRepo
import com.jkoivist.mobilecomputingproject.ui.theme.MobileComputingProjectTheme

class MainViewModel(application: Application) : ViewModel(){
    val allReminders: LiveData<List<Reminder>>
    private val repo: ReminderRepo
    val currentReminder: LiveData<Reminder>
    var displayReminder: Reminder

    init{
        val reminderDB = ReminderDB.getInstance(application)
        val reminderDao = reminderDB.dao()
        repo = ReminderRepo(reminderDao)

        allReminders = repo.allReminders
        currentReminder = repo.currentReminder
        displayReminder = Reminder(
            "",
            "",
            0,
            0,
            0,
            0,
            0
        )
    }

    fun insertReminder(reminder: Reminder){
        repo.insert(reminder)
    }

    fun updateReminder(reminder: Reminder){
        repo.update(reminder)
    }

    fun deleteReminderById(id: Long){
        repo.deleteById(id)
    }

    fun getReminderById(id: Long){
        repo.getById(id)
    }
}

class MainViewModelFactory(val application: Application): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        return MainViewModel(application) as T
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileComputingProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val owner = LocalViewModelStoreOwner.current
                    owner?.let{
                        val viewModel: MainViewModel = viewModel(
                            it,
                            "MainViewModel",
                            MainViewModelFactory(
                                LocalContext.current.applicationContext as Application
                            )
                        )
                        MainApp(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
