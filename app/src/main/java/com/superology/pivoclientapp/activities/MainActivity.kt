package com.superology.pivoclientapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.superology.pivoclientapp.R
import com.superology.pivoclientapp.firebase.database.DatabaseService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()
    private val TAG = MainActivity::class.java.canonicalName
    private val inputMethodManager by lazy { getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initViews()
        observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    private fun initViews() {
        textView?.text = ""
    }

    private fun observeData() {
        disposable.add(DatabaseService.observeData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                textView?.text = it
            }) { Log.e(TAG, it.message.toString(), it)})
    }

}