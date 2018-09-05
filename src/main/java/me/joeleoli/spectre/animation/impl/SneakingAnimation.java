package me.joeleoli.spectre.animation.impl;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import me.joeleoli.spectre.animation.Animation;
import me.joeleoli.spectre.entity.SpectreEntity;

import java.lang.reflect.InvocationTargetException;

public class SneakingAnimation extends Animation {

    private boolean sneaking;
    private float held;

    public SneakingAnimation(SpectreEntity entity) {
        super(entity, 2L);
    }

    @Override
    public void onTick() {
        this.held++;

        if (this.held >= 6) {
            this.sneaking = !this.sneaking;
            this.held = 0;
        }
    }

    @Override
    public void sendPackets() {
        PacketContainer metadata = this.entity.createEntityMetadataPacket(this.sneaking);

        this.entity.getLocation().getWorld().getPlayers().forEach(player -> {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, metadata);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

}
