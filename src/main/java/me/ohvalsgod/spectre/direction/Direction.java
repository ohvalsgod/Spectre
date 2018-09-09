package me.ohvalsgod.spectre.direction;

import org.bukkit.Location;

public enum Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    public float getYaw() {
        switch (this) {
            case NORTH:
                return -180;
            case EAST:
                return -90;
            case SOUTH:
                return 0;
            case WEST:
                return 90;
            default:
                return 0;
        }
    }

    public void move(Location location, double blocks) {
        switch (this) {
            case NORTH:
                location.setZ(location.getZ() - blocks);
                break;
            case EAST:
                location.setX(location.getX() + blocks);
                break;
            case SOUTH:
                location.setZ(location.getZ() + blocks);
                break;
            case WEST:
                location.setX(location.getX() - blocks);
                break;
        }
    }

    public Direction getOpposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            default:
                return null;
        }
    }

    public static Direction getFromYaw(float yaw) {
        if (yaw <= -45 && yaw > -135) {
            return EAST;
        } else if (yaw > -45 && yaw <= 45) {
            return SOUTH;
        } else if (yaw > 45 && yaw <= 135) {
            return WEST;
        } else {
            return NORTH;
        }
    }

}
