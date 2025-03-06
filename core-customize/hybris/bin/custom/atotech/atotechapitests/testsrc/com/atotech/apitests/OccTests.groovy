package com.atotech.apitests

import com.atotech.apitests.tests.occ.DemoOccTest
import de.hybris.bootstrap.annotations.UnitTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite.class)
@Suite.SuiteClasses([DemoOccTest.class])
@UnitTest
class OccTests {

    @Test
    public static void testPlaceholder() {
    }
}
