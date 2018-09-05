package me.joeleoli.spectre;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;

import me.joeleoli.spectre.entity.SpectreEntity;
import me.joeleoli.spectre.thread.AnimationThread;

import lombok.Getter;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An interactive NPC library.
 */

@Getter
public class Spectre {

    @Getter
    private static Spectre instance;

    private Set<SpectreEntity> entities = new HashSet<>();

    public Spectre(JavaPlugin plugin) {
        // Cannot be instantiated more than once
        if (instance != null) {
            throw new RuntimeException("Spectre has already been instantiated");
        }

        // Set instance
        instance = this;

        // Add our packet listener
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.PLAYER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacket().getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.ADD_PLAYER) {
                    final List<PlayerInfoData> list = event.getPacket().getPlayerInfoDataLists().read(0);

                    for (PlayerInfoData info : list) {
                        // Get game profile from info data
                        final WrappedGameProfile profile = info.getProfile();

                        // Retrieve entity by UUID (returns null if does not exist)
                        final SpectreEntity entity = SpectreEntity.getByUuid(profile.getUUID());

                        if (entity == null) {
                            continue;
                        }

                        // If texture value and or signature are null we don't need
                        // to do anything else
                        if (entity.getTexture() == null || entity.getTexture().getValue() == null || entity.getTexture().getSignature() == null) {
                            continue;
                        }

                        // Make signed property
                        final WrappedSignedProperty property = new WrappedSignedProperty("textures", entity.getTexture().getValue(), entity.getTexture().getSignature());

                        // Update texture property
                        profile.getProperties().put("textures", property);
                    }
                }
            }
        });

        // Run animation thread (ticks animations)
        new AnimationThread().start();
    }

}
