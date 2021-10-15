package vn.nms.sample.data.livedata

import androidx.lifecycle.MutableLiveData

/**
 * Thread-safe live data to resolve this issue: when perform  {@link LiveData#setValue(Object)}
 * not in main Thread. (almost case is in testing)
 */

class SafeMutableLiveData<T> : MutableLiveData<T> {

    constructor(): super()

    constructor(t: T): super(t)

    override fun setValue(value: T) {
        try {
            super.setValue(value)
        } catch (e: Exception) {
            // if we can't set value due to not in main thread, must call post value instead
            super.postValue(value)
        }
    }

    fun setValueIfNew(value: T) {
        if (this.value != value) {
            setValue(value)
        }
    }
}