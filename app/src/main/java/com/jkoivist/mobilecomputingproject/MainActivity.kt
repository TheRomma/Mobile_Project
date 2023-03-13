package com.jkoivist.mobilecomputingproject

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
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
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jkoivist.mobilecomputingproject.data.*
import com.jkoivist.mobilecomputingproject.ui.theme.MobileComputingProjectTheme
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application) : ViewModel(){
    val allReminders: LiveData<List<Reminder>>
    private val repo: ReminderRepo
    val currentReminder: LiveData<Reminder>
    var displayReminder: Reminder
    var app: Context
    val notifier: NotificationHelper

    var useVirtualLocation: Boolean
    var virtualX: Double
    var virtualY: Double

    var posX: Double
    var posY: Double

    var pickedX: Double
    var pickedY: Double

    val fusedLocationClient: FusedLocationProviderClient

    init{
        val reminderDB = ReminderDB.getInstance(application)
        val reminderDao = reminderDB.dao()
        repo = ReminderRepo(reminderDao)
        app = application.applicationContext
        notifier = NotificationHelper(app)
        createNotificationChannel()

        posX = 0.0
        posY = 0.0

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(app)
        if (ActivityCompat.checkSelfPermission(
                app,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                app,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            location: Location? ->
            if (location != null) {
                posX = location.longitude
                posY = location.latitude
            }
        }

        allReminders = repo.allReminders
        currentReminder = repo.currentReminder
        displayReminder = Reminder(
            "",
            "",
            0.0,
            0.0,
            0,
            0,
            0,
            false
        )

        useVirtualLocation = false
        virtualX = 0.0
        virtualY = 0.0
        pickedX = 0.0
        pickedY = 0.0
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

    fun createNotificationChannel(){
        notifier.createChannel()
    }
    fun instantNotification(id: Int, title: String, message: String){
        notifier.createNotification(id, title, message)
    }

    fun timedNotification(id: Int, title: String, message: String, delay: Long){
        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, TimeUnit.SECONDS)
            .setInputData(workDataOf(
                "id" to id,
                "title" to title,
                "message" to message,
            )).build()
        WorkManager.getInstance(app).enqueue(request)
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
