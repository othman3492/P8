package com.openclassrooms.realestatemanager;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.models.database.RoomDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
public class DaoTest {


    // Data set for test
    private static int USER_ID = 1;
    private static User USER_DEMO = new User(USER_ID, "Brad", "Pitt");
    private static RealEstate REAL_ESTATE_1 = new RealEstate(1, "Maison",
            null, null, null, "200", "200 000",
            "5", "2", "1", false, null,
            null, USER_ID);
    private static RealEstate REAL_ESTATE_2 = new RealEstate(2, "Appartement",
            null, null, null, "80", "120 000",
            "3", "1", "1", false, null,
            null, USER_ID);
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private RoomDatabase database;

    @Before
    public void initDatabase() throws Exception {

        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                RoomDatabase.class)
                .allowMainThreadQueries().build();
    }

    @After
    public void closeDatabase() throws Exception {

        database.close();
    }


    @Test
    public void insertAndGetUser() throws InterruptedException {

        // Add user
        this.database.roomUserDao().createUser(USER_DEMO);

        // Test
        User user = LiveDataTestUtil.getValue(this.database.roomUserDao().getUserById(USER_ID));
        assertTrue(user.getFirstName().equals(USER_DEMO.getFirstName()) && user.getId() == USER_ID);
    }


    @Test
    public void insertAndGetProperties() throws InterruptedException {

        // Add user & properties
        this.database.roomUserDao().createUser(USER_DEMO);
        this.database.roomPropertyDao().createProperty(REAL_ESTATE_1);
        this.database.roomPropertyDao().createProperty(REAL_ESTATE_2);

        // Test
        List<RealEstate> properties = LiveDataTestUtil.getValue(this.database.roomPropertyDao().getAllProperties());
        assertEquals(2, properties.size());
    }


    @Test
    public void insertAndUpdateProperty() throws InterruptedException {

        // Add user & property
        this.database.roomUserDao().createUser(USER_DEMO);
        this.database.roomPropertyDao().createProperty(REAL_ESTATE_1);

        // Get property and update it
        RealEstate realEstateToUpdate = LiveDataTestUtil.getValue(this.database.roomPropertyDao().getAllProperties()).get(0);
        realEstateToUpdate.setStatus(true);
        this.database.roomPropertyDao().updateProperty(realEstateToUpdate);

        // Test
        List<RealEstate> properties = LiveDataTestUtil.getValue(this.database.roomPropertyDao().getAllProperties());
        assertTrue(properties.size() == 1 && properties.get(0).getStatus());
    }


    @Test
    public void insertAndDeleteProperty() throws InterruptedException {

        // Add user & property
        this.database.roomUserDao().createUser(USER_DEMO);
        this.database.roomPropertyDao().createProperty(REAL_ESTATE_2);

        // Get property and delete it
        RealEstate realEstateToDelete = LiveDataTestUtil.getValue(this.database.roomPropertyDao().getAllProperties()).get(0);
        this.database.roomPropertyDao().deleteProperty(realEstateToDelete);
    }


}
