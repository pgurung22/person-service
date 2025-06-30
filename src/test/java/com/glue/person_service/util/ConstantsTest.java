package com.glue.person_service.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {
    @Test
    void testConstants() {
        assertEquals("admin", Constants.ADMIN);
        assertEquals("guest", Constants.GUEST);
        assertEquals("password", Constants.PASSWORD);
    }
}
