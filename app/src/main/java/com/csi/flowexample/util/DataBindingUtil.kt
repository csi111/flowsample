package com.csi.flowexample.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

object DataBindingUtil {
    @BindingAdapter("bindText")
    @JvmStatic
    fun bindEditText(view: EditText, liveData: MutableLiveData<String>?) {
        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                liveData?.postValue(s.toString())
            }
        })
        val newValue = liveData?.value
        if (view.text.toString() != newValue) {
            view.setText(newValue)
        }
    }
}