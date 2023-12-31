package com.dev.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * This class is used as Base class for ViewModel
 */
abstract class BaseViewModel<VS: ViewState, VI: ViewIntent, SE: SideEffect>: ViewModel() {

    protected val _state = MutableSharedFlow<VS>()

    val stateSharedFlow: MutableSharedFlow<VS> get() = _state

    protected val _sideEffect = MutableSharedFlow<SE>()

    val sideEffectSharedFlow: MutableSharedFlow<SE> get() = _sideEffect
    abstract fun sendIntent(intent: VI)

}