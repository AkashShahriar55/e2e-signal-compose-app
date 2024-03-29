package com.nsa.ui.vm

import androidx.lifecycle.ViewModel
import com.nsa.ui.event.UIEvent
import com.nsa.ui.state.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State : UIState,Event:UIEvent> : ViewModel() {

    protected val _uiState: MutableStateFlow<State> = MutableStateFlow(initUIState())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    abstract fun initUIState(): State

    abstract fun onUiEvent(event:Event)
}