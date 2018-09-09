package me.ohvalsgod.spectre.util;

public class PositionUtils {

    /**
     * Minecraft's yaw ranges from -180 to 180 instead of 0 to 360.
     * This method simplifies calculating turning below or above those limits.
     *
     * @param yaw The yaw to fix.
     * @return The fixed yaw if below or above the Minecraft yaw limits, otherwise the given yaw.
     */
    public static float toNormalYaw(float yaw) {
        while (yaw >= 180 || yaw <= -180) {
            if (yaw >= 180) {
                yaw = yaw - 360;
            } else if (yaw <= -180) {
                yaw = yaw + 360;
            }
        }

        return yaw;
    }

    /**
     * Converts the given yaw to bytes.
     * When sending packets, a position's yaw uses the Angle data type.
     * See http://wiki.vg/index.php?title=Protocol&oldid=7368#Data_types
     *
     * @param yaw The yaw to convert to bytes.
     * @return The yaw converted to bytes.
     */
    public static byte yawToBytes(float yaw) {
        return (byte) (yaw * 256.0F / 360.0F);
    }

}
