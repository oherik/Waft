package com.alive_n_clickin.commutity.util.event;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by mats on 02/10/15.
 */
public class ObservableHelperTest {

    private ObservableHelper observableHelper;

    @Before
    public void initialize() {
        observableHelper = new ObservableHelper();
    }

    @Test
    public void testNotifyObservers() throws Exception {
        IEvent mockEvent = mock(IEvent.class);
        IObserver mockObserver;

        // assert that onEvent gets called on added observers
        mockObserver = mock(IObserver.class);
        observableHelper.addObserver(mockObserver);
        observableHelper.notifyObservers(mockEvent);
        verify(mockObserver).onEvent(mockEvent);

        // assert that onEvent doesn't get called on removed observers
        mockObserver = mock(IObserver.class);
        observableHelper.addObserver(mockObserver);
        observableHelper.removeObserver(mockObserver);
        observableHelper.notifyObservers(mockEvent);
        verify(mockObserver, never()).onEvent(mockEvent);
    }
}