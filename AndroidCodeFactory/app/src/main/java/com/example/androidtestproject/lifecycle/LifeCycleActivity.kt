/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidtestproject.lifecycle

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import com.example.androidtestproject.R
import com.example.androidtestproject.databinding.ActivityLifecycleBinding
import timber.log.Timber

/*
* 2020-05-25 21:56:27.944 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onCreate
2020-05-25 21:56:28.008 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onStart
2020-05-25 21:56:28.010 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onResume
2020-05-25 21:56:36.043 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onPause
2020-05-25 21:56:36.064 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onStop
2020-05-25 21:56:39.645 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onRestart
2020-05-25 21:56:39.648 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onStart
2020-05-25 21:56:39.650 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onResume
* */

/*
create - start - resume - pause - create - start - resume - stop
pause - on restart - on start - on resume - on stop - on destroy
on pause - on stop - on destroy

* 2020-05-25 22:03:08.095 7418-7418/com.example.androidtestproject W/ActivityThread: handleWindowVisibility: no activity for token android.os.BinderProxy@9550196
2020-05-25 22:03:08.137 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onCreate
2020-05-25 22:03:08.190 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onStart
2020-05-25 22:03:08.192 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onResume
2020-05-25 22:03:12.204 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onPause
2020-05-25 22:03:12.256 7418-7418/com.example.androidtestproject W/ActivityThread: handleWindowVisibility: no activity for token android.os.BinderProxy@1408e3e
2020-05-25 22:03:12.274 7418-7418/com.example.androidtestproject I/LifeCycleActivity: LifeCycleActivity :: OnCreatedCalled
2020-05-25 22:03:12.307 7418-7418/com.example.androidtestproject I/LifeCycleActivity: LifeCycleActivity :: onStart
2020-05-25 22:03:12.308 7418-7418/com.example.androidtestproject I/LifeCycleActivity: LifeCycleActivity :: onResume
2020-05-25 22:03:12.724 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onStop
2020-05-25 22:03:16.578 7418-7418/com.example.androidtestproject I/LifeCycleActivity: LifeCycleActivity :: onPause
2020-05-25 22:03:16.594 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onRestart
2020-05-25 22:03:16.597 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onStart
2020-05-25 22:03:16.601 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onResume
2020-05-25 22:03:16.977 7418-7418/com.example.androidtestproject I/LifeCycleActivity: LifeCycleActivity :: onStop
2020-05-25 22:03:16.979 7418-7418/com.example.androidtestproject I/LifeCycleActivity: LifeCycleActivity :: onDestroy
2020-05-25 22:03:25.147 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onPause
2020-05-25 22:03:25.608 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onStop
2020-05-25 22:03:25.623 7418-7418/com.example.androidtestproject I/MainActivity: MainActivity :: onDestroy
*
* */
class LifeCycleActivity : AppCompatActivity(), LifecycleObserver {

    private var revenue = 0
//    private var dessertsSold = 0
    private var dessertsSold = 0

    // Contains all the views
    private lateinit var binding: ActivityLifecycleBinding

    /** Dessert Data **/

    /**
     * Simple data class that represents a dessert. Includes the resource id integer associated with
     * the image, the price it's sold for, and the startProductionAmount, which determines when
     * the dessert starts to be produced.
     */
    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    // Create a list of all desserts, in order of when they start being produced
    private val allDesserts = listOf(
            Dessert(R.drawable.cupcake, 5, 0),
            Dessert(R.drawable.donut, 10, 5),
            Dessert(R.drawable.eclair, 15, 20),
            Dessert(R.drawable.froyo, 30, 50),
            Dessert(R.drawable.gingerbread, 50, 100),
            Dessert(R.drawable.honeycomb, 100, 200),
            Dessert(R.drawable.icecreamsandwich, 500, 500),
            Dessert(R.drawable.jellybean, 1000, 1000),
            Dessert(R.drawable.kitkat, 2000, 2000),
            Dessert(R.drawable.lollipop, 3000, 4000),
            Dessert(R.drawable.marshmallow, 4000, 8000),
            Dessert(R.drawable.nougat, 5000, 16000),
            Dessert(R.drawable.oreo, 6000, 20000)
    )
    private var currentDessert = allDesserts[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.i("LifeCycleActivity", "OnCreatedCalled")
        Timber.i("LifeCycleActivity :: OnCreatedCalled")

        // Use Data Binding to get reference to the views
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lifecycle)

        binding.dessertButton.setOnClickListener {
            onDessertClicked()
        }

//        dessertsSold = 0

        // Set the TextViews to the right values
        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // Make sure the correct dessert is showing
        binding.dessertButton.setImageResource(currentDessert.imageId)
    }

    override fun onRestart() {
        super.onRestart()
//        Log.i("LifeCycleActivity", "onRestartCalled")
        Timber.i("LifeCycleActivity :: onRestart")
    }

    override fun onStart() {
        super.onStart()

        dessertsSold = 0

        Timber.i("LifeCycleActivity :: onStart")
    }

    override fun onResume() {
        super.onResume()
//        Log.i("LifeCycleActivity", "onResume")
        Timber.i("LifeCycleActivity :: onResume")
    }

    override fun onPause() {
        super.onPause()
//        Log.i("LifeCycleActivity", "onPause")
        Timber.i("LifeCycleActivity :: onPause")

    }

    override fun onStop() {
        super.onStop()
        Timber.i("LifeCycleActivity :: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
//        Log.i("LifeCycleActivity", "onDestroy")
        Timber.i("LifeCycleActivity :: onDestroy")
    }


    /**
     * Updates the score when the dessert is clicked. Possibly shows a new dessert.
     */
    private fun onDessertClicked() {

        // Update the score
        revenue += currentDessert.price
        dessertsSold++

        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // Show the next dessert
        showCurrentDessert()
    }

    /**
     * Determine which dessert to show.
     */
    private fun showCurrentDessert() {
        var newDessert = allDesserts[0]
        for (dessert in allDesserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert
            }
            // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
            // you'll start producing more expensive desserts as determined by startProductionAmount
            // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
            // than the amount sold.
            else break
        }

        // If the new dessert is actually different than the current dessert, update the image
        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
    }

    /**
     * Menu methods
     */
    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
                .setText(getString(R.string.share_text, dessertsSold, revenue))
                .setType("text/plain")
                .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                    Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share ->
                onShare()
        }
        return super.onOptionsItemSelected(item)
    }
}
