package me.joeleoli.spectre.animation.impl;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import me.joeleoli.spectre.animation.Animation;
import me.joeleoli.spectre.entity.SpectreEntity;
import me.joeleoli.spectre.util.PositionUtils;

import org.bukkit.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class WalkingAnimation extends Animation {

    private Location previous;
    private Boolean[] steps;
    private float walked;
    private float turned;
    private Float turnDifference;

    public WalkingAnimation(SpectreEntity entity) {
        super(entity, 2L);

        this.steps = new Boolean[4];

        Arrays.fill(this.steps, false);
    }

    private int getCurrentStep() {
        int completed = 0;

        for (boolean step : this.steps) {
            if (step) {
                completed++;
            }
        }

        if (completed == 4) {
            Arrays.fill(this.steps, false);
            return 0;
        }

        return completed;
    }

    @Override
    public void onTick() {
        this.previous = this.entity.getLocation().clone();

        int step = this.getCurrentStep();

        switch (step) {
            case 0:
            case 2: {
                this.walked++;

                (step == 0 ? this.entity.getDirection() : this.entity.getDirection().getOpposite()).move(this.entity.getLocation(), 0.3F);

                if (this.walked >= 25) {
                    this.walked = 0.0F;
                    this.steps[step] = true;
                }
            }
            break;
            case 1:
            case 3: {
                // Set the target to the opposite yaw of where the player is currently facing.
                if (this.turnDifference == null) {
                    this.turnDifference = Math.abs(this.entity.getLocation().getYaw() - PositionUtils
		                    .toNormalYaw(this.entity.getLocation().getYaw() + 180.0F)) / 10;
                }

                this.turned++;

                this.entity.getLocation().setYaw(PositionUtils.toNormalYaw(this.entity.getLocation().getYaw() + this.turnDifference));

                if (this.turned >= 10) {
                    this.turned = 0.0F;
                    this.turnDifference = null;
                    this.steps[step] = true;
                }
            }
            break;
        }
    }

    @Override
    public void sendPackets() {
        // Calculate difference between locations
        float x = (float) (this.entity.getLocation().getX() - this.previous.getX());
        float y = (float) (this.entity.getLocation().getY() - this.previous.getY());
        float z = (float) (this.entity.getLocation().getZ() - this.previous.getZ());

        PacketContainer rotate = this.entity.createEntityHeadRotationPacket();
        PacketContainer relative = this.entity.createEntityRelativeMoveLookPacket(x, y, z);

        this.entity.getLocation().getWorld().getPlayers().forEach(player -> {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, rotate);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, relative);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

}
