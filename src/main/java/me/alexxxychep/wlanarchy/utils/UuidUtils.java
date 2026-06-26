package me.alexxxychep.wlanarchy.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtils {
    public static byte[] convertToBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    public static UUID convertToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long mostBits = byteBuffer.getLong();
        long leastBits = byteBuffer.getLong();
        return new UUID(mostBits, leastBits);
    }
}
