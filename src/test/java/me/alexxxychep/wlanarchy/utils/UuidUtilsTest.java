package me.alexxxychep.wlanarchy.utils;

import org.junit.jupiter.api.Test;
import java.nio.BufferUnderflowException;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UuidUtilsTest {

    @Test
    void shouldConvertUUIDToBytesAndBack() {
        UUID original = UUID.randomUUID();
        byte[] bytes = UuidUtils.convertToBytes(original);
        UUID converted = UuidUtils.convertToUUID(bytes);
        assertEquals(original, converted);
        assertEquals(16, bytes.length);
    }

    @Test
    void shouldPreserveUUIDParts() {
        UUID original = new UUID(0x123456789ABCDEF0L, 0xFEDCBA9876543210L);
        byte[] bytes = UuidUtils.convertToBytes(original);
        UUID converted = UuidUtils.convertToUUID(bytes);
        assertEquals(original.getMostSignificantBits(), converted.getMostSignificantBits());
        assertEquals(original.getLeastSignificantBits(), converted.getLeastSignificantBits());
    }

    @Test
    void shouldHandleAllZeroUUID() {
        UUID zero = new UUID(0L, 0L);
        byte[] bytes = UuidUtils.convertToBytes(zero);
        UUID converted = UuidUtils.convertToUUID(bytes);
        assertEquals(zero, converted);
        for (byte b : bytes) {
            assertEquals(0, b);
        }
    }

    @Test
    void shouldHandleMaxUUID() {
        UUID max = new UUID(Long.MAX_VALUE, Long.MAX_VALUE);
        byte[] bytes = UuidUtils.convertToBytes(max);
        UUID converted = UuidUtils.convertToUUID(bytes);
        assertEquals(max, converted);
    }

    @Test
    void shouldThrowOnNullInput() {
        assertThrows(NullPointerException.class, () -> UuidUtils.convertToBytes(null));
        assertThrows(NullPointerException.class, () -> UuidUtils.convertToUUID(null));
    }

    @Test
    void shouldHandleInvalidByteArrayLength() {
        byte[] invalidBytes = new byte[8];
        assertThrows(BufferUnderflowException.class,
                () -> UuidUtils.convertToUUID(invalidBytes));
    }
}