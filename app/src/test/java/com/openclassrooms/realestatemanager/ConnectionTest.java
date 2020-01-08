package com.openclassrooms.realestatemanager;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowNetworkInfo;

import static org.junit.Assert.*;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ConnectionTest {
    private ConnectivityManager connectivityManager;
    private ShadowNetworkInfo shadowOfActiveNetworkInfo;

    @Before
    public void setUp() throws Exception {
        connectivityManager =
                (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        shadowOfActiveNetworkInfo = shadowOf(connectivityManager.getActiveNetworkInfo());
    }

    @Test
    public void getActiveNetworkInfo_shouldInitializeItself() {
        assertThat(connectivityManager.getActiveNetworkInfo()).isNotNull();
    }

    @Test
    public void getActiveNetworkInfo_shouldReturnTrueCorrectly() {
        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED);
        assertThat(connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()).isTrue();
        assertThat(connectivityManager.getActiveNetworkInfo().isConnected()).isTrue();

        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTING);
        assertThat(connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()).isTrue();
        assertThat(connectivityManager.getActiveNetworkInfo().isConnected()).isFalse();

        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.DISCONNECTED);
        assertThat(connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()).isFalse();
        assertThat(connectivityManager.getActiveNetworkInfo().isConnected()).isFalse();
    }
}