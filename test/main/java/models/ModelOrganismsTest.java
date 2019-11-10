package main.java.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelOrganismsTemplateTest {
    @Test
    public void MouseMM9Test() {
        ModelOrganismTemplate m = null;
        try {
            m = new MouseMM9();
        } catch (Exception e) {
            fail("Cannot instantiate MouseMM9 template");
        }
        assertNotNull(m);
    }
}