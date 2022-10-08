package vn.nms.sample.presentation.ui.base.viewmodel

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import timber.log.Timber
import vn.nms.sample.data.livedata.SafeMutableLiveData
import vn.nms.sample.data.manager.ErrorManager
import vn.nms.sample.data.manager.ResourceManager
import vn.nms.sample.domain.`interface`.PlainConsumer
import vn.nms.sample.domain.define.*
import vn.nms.sample.domain.manager.UserManager
import vn.nms.sample.presentation.utils.NavigatorHelper
import vn.nms.sample.presentation.utils.SchedulerProvider
import javax.inject.Inject

abstract class BaseViewModel(private val savedStateHandle: SavedStateHandle? = null) : ViewModel() {

    @Inject
    lateinit var errorManager: ErrorManager

    @Inject
    lateinit var resource: ResourceManager

    @Inject
    lateinit var userManager: UserManager

    @NonNull
    protected var mCompositeDisposable = CompositeDisposable()

    @Inject lateinit var navigationHelper: NavigatorHelper

    @NonNull
    val stateLiveData = SafeMutableLiveData<State>()

    private var isFirstTimeUiCreate = true

    val arguments get() = savedStateHandle?.get<Bundle>("bundle_args")

    @MainThread
    inline fun <reified Args : NavArgs> navArgs() = NavArgsLazy(Args::class) {
        arguments ?: Bundle()
    }

    @CallSuper
    fun onCreate(bundle: Bundle?) {
        if (isFirstTimeUiCreate) {
            onFirstTimeUiCreate(bundle ?: Bundle())
            isFirstTimeUiCreate = false
        }
    }

    abstract fun onFirstTimeUiCreate(bundle: Bundle)

    open fun onDestroyView() {
        stateLiveData.postValue(InitState)
    }

    open fun onResume() {

    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposeAllExecutions()
    }

    @MainThread
    protected fun publishState(state: State) {
        stateLiveData.postValue(state)
    }

    private fun disposeAllExecutions() {
        mCompositeDisposable.dispose()
        mCompositeDisposable = CompositeDisposable()
        publishState(SuccessState())
    }

    open fun onBackPressed(popBackStack: () -> Unit) {}

    protected fun <T> Single<T>.execute(
        showProgress: Boolean = true,
        responseConsumer: PlainConsumer<T>? = null,
        errorConsumer: PlainConsumer<ErrorState>? = null,
        loadingMessage: String = "",
        disposable: CompositeDisposable? = null,
        skipDispose: Boolean = false
    ) {
        if (showProgress) publishState(LoadingState(loadingMessage))
        val request =
            this.compose(if (showProgress) SchedulerProvider.ioToMainSingle() else SchedulerProvider.ioToIoSingle())
                .subscribe({
                    if (showProgress) publishState(SuccessState())
                    responseConsumer?.accept(it)
                }, {
                    val errorState = errorManager.getErrorState(it)
                    if (showProgress) publishState(errorState)
                    Timber.e("executeFailed:%s", errorState.toString())
                    errorConsumer?.accept(errorState)
                })
        if (!skipDispose) {
            request.addTo(disposable ?: mCompositeDisposable)
        }
    }

    protected fun <T> Flowable<T>.execute(
        showProgress: Boolean = true,
        responseConsumer: PlainConsumer<T>? = null,
        errorConsumer: PlainConsumer<ErrorState>? = null,
        loadingMessage: String = "",
        disposable: CompositeDisposable? = null,
        skipDispose: Boolean = false
    ) {
        if (showProgress) publishState(LoadingState(loadingMessage))
        val request =
            this.compose(if (showProgress) SchedulerProvider.ioToMainFlowable() else SchedulerProvider.ioToIoFlowable())
                .subscribe({
                    if (showProgress) publishState(SuccessState())
                    responseConsumer?.accept(it)
                }, {
                    val errorState = errorManager.getErrorState(it)
                    if (showProgress) publishState(errorState)
                    Timber.e("executeFailed:%s", errorState.toString())
                    errorConsumer?.accept(errorState)

                })

        if (!skipDispose) {
            request.addTo(disposable ?: mCompositeDisposable)
        }
    }

    protected fun <T> Observable<T>.execute(
        showProgress: Boolean = true,
        responseConsumer: PlainConsumer<T>? = null,
        errorConsumer: PlainConsumer<ErrorState>? = null,
        loadingMessage: String = "",
        disposable: CompositeDisposable? = null,
        skipDispose: Boolean = false
    ) {
        if (showProgress) publishState(LoadingState(loadingMessage))
        val request =
            this.compose(if (showProgress) SchedulerProvider.ioToMainObservable() else SchedulerProvider.ioToIoObservable())
                .subscribe({
                    if (showProgress) publishState(SuccessState())
                    responseConsumer?.accept(it)
                }, {
                    val errorState = errorManager.getErrorState(it)
                    if (showProgress) publishState(errorState)
                    Timber.e("executeFailed:%s", errorState.toString())
                    errorConsumer?.accept(errorState)
                })

        if (!skipDispose) {
            request.addTo(disposable ?: mCompositeDisposable)
        }
    }
}