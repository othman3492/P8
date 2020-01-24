package com.openclassrooms.realestatemanager;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowNetworkInfo;

import java.util.Objects;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.google.common.truth.Truth.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(AndroidJUnit4.class)
public class ConnectionTest {
    private ConnectivityManager connectivityManager;
    private ShadowNetworkInfo shadowOfActiveNetworkInfo;

    @Before
    public void setUp() throws Exception {
        connectivityManager =
                (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        shadowOfActiveNetworkInfo = shadowOf(Objects.requireNonNull(connectivityManager).getActiveNetworkInfo());
    }

    @Test
    public void getActiveNetworkInfo_shouldInitializeItself() {
        assertThat(connectivityManager.getActiveNetworkInfo()).isNotNull();
    }

    @Test
    public void getActiveNetworkInfo_shouldReturnTrueCorrectly() {
        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED);
        assertThat(Utils.isInternetAvailable(getApplicationContext())).isTrue();

        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED);
        assertThat(Utils.isInternetAvailable(getApplicationContext())).isTrue();

        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTING);
        assertThat(Utils.isInternetAvailable(getApplicationContext())).isFalse();

        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.DISCONNECTED);
        assertThat(Utils.isInternetAvailable(getApplicationContext())).isFalse();
    }
}