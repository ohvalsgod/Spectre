package me.joeleoli.spectre.animation.impl;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import me.joeleoli.spectre.animation.Animation;
import me.joeleoli.spectre.entity.SpectreEntity;

import java.lang.reflect.InvocationTargetException;

public class SpinningAnimation extends Animation {

    public SpinningAnimation(SpectreEntity entity) {
        super(entity, 1L);
    }

    @Override
    public void onTick() {
        this.entity.getLocation().setYaw(this.entity.getLocation().getYaw() + 4);
    }

    @Override
    public void sendPackets() {
        PacketContainer look = this.entity.createEntityLookPacket();
        PacketContainer rotate = this.entity.createEntityHeadRotationPacket();
        PacketContainer relative = this.entity.createEntityRelativeMoveLookPacket(0, 0, 0);
        PacketContainer animation = this.entity.createEntityAnimationPacket();

        this.entity.getLocation().getWorld().getPlayers().forEach(player -> {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, look);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, rotate);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, relative);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, animation);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

}
