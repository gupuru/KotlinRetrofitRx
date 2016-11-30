package gupuru.kotlinretrofitsample.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import gupuru.kotlinretrofitsample.R
import gupuru.kotlinretrofitsample.adapter.SearchAdapter
import gupuru.kotlinretrofitsample.api.client
import gupuru.kotlinretrofitsample.extention.onNext
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var inputMethodManager: InputMethodManager
    lateinit var searchAdapter: SearchAdapter
    lateinit var progressDialog: ProgressDialog
    private var isSearch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this@MainActivity)

        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        activity_main.edit_search.setOnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_ENTER) {
                inputMethodManager.hideSoftInputFromWindow(activity_main.edit_search.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN)

                val text = activity_main.edit_search.text.toString()
                if (text != "") {
                    searchMusic(activity_main.edit_search.text.toString())
                }

                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    override fun onStop() {
        super.onStop()
    }

    private fun searchMusic(name: String) {
        if (!isSearch) {
            showProgress()
            isSearch = true
            client().search(name)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onNext {
                        if (it.tracks.items.size > 0) {
                            activity_main.recycler_view.setHasFixedSize(true)
                            activity_main.recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
                            searchAdapter = SearchAdapter(this@MainActivity, it.tracks.items)
                            activity_main.recycler_view.adapter = searchAdapter
                        } else {
                            Toast.makeText(this@MainActivity, "該当する項目がありません", Toast.LENGTH_LONG).show()
                        }
                    }
                    .onError {
                        Toast.makeText(this@MainActivity, "エラー" + it.toString(), Toast.LENGTH_LONG).show()
                        hideProgress()
                    }
                    .onCompleted {
                        isSearch = false
                        hideProgress()
                    }
                    .subscribe()
        }
    }

    private fun showProgress() {
        progressDialog.setTitle("通信中...")
        progressDialog.setIndeterminate(false)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun hideProgress() {
        progressDialog.hide()
    }

}
