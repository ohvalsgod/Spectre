package me.joeleoli.spectre.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;

import me.joeleoli.spectre.animation.Animation;
import me.joeleoli.spectre.direction.Direction;
import me.joeleoli.spectre.texture.Texture;
import me.joeleoli.spectre.util.PositionUtils;

import lombok.Data;
import lombok.Getter;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Data
public class SpectreEntity {

    @Getter
    private static Set<SpectreEntity> entities = new HashSet<>();
    protected static final Random RANDOM = new Random();

    private int id;
    private UUID uuid;
    private String displayName;
    private Texture texture;
    protected Location location;
    private ItemStack[] armour;
    private Animation animation;
    protected Direction direction;

    public SpectreEntity(String displayName, Location location) {
        this.id = RANDOM.nextInt(Integer.MAX_VALUE);
        this.uuid = UUID.randomUUID();
        this.displayName = displayName;
        this.location = location;
        this.direction = Direction.getFromYaw(location.getYaw());

        // Update initial yaw
        this.location.setYaw(this.direction.getYaw());

        entities.add(this);
    }

    public void sendInitialPackets(Player player) {
        double fixedX = this.location.getX() - ((int) this.location.getX());
        double fixedY = this.location.getY() - ((int) this.location.getY());
        double fixedZ = this.location.getZ() - ((int) this.location.getZ());

        PacketContainer addPacket = this.createPlayerInfoAddPacket();
        PacketContainer spawnPacket = this.createSpawnEntityPacket();
        PacketContainer removePacket = this.createPlayerInfoRemovePacket();
        PacketContainer rotationPacket = this.createEntityHeadRotationPacket();
        PacketContainer lookPacket = this.createEntityLookPacket();
        PacketContainer relativePacket = this.createEntityRelativeMoveLookPacket(fixedX, fixedY, fixedZ);
        PacketContainer animationPacket = this.createEntityAnimationPacket();

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, addPacket);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, spawnPacket);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, removePacket);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, rotationPacket);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, lookPacket);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, relativePacket);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, animationPacket);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public PacketContainer createPlayerInfoAddPacket() {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);

        packet.getPlayerInfoAction()
                .write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);

        packet.getPlayerInfoDataLists()
                .write(0, Collections.singletonList(new PlayerInfoData(new WrappedGameProfile(this.uuid, this.displayName), 0, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(this.displayName))));

        return packet;
    }

    public PacketContainer createPlayerInfoRemovePacket() {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);

        packet.getPlayerInfoAction()
                .write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);

        packet.getPlayerInfoDataLists()
                .write(0, Collections.singletonList(new PlayerInfoData(new WrappedGameProfile(this.uuid, this.displayName), 0, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(this.displayName))));

        return packet;
    }

    public PacketContainer createSpawnEntityPacket() {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server
                .NAMED_ENTITY_SPAWN);

        packet.getUUIDs()
                .write(0, this.uuid);

        packet.getIntegers()
                .write(0, this.id)
                .write(1, (int) this.location.getX() * 32)
                .write(2, (int) this.location.getY() * 32)
                .write(3, (int) this.location.getZ() * 32);

        packet.getBytes()
                .write(0, PositionUtils.yawToBytes(this.location.getYaw()))
                .write(1, (byte) 0);

        return packet;
    }

    public PacketContainer createEntityMetadataPacket(boolean sneaking) {
        WrappedDataWatcher dataWatcher = new WrappedDataWatcher();
        dataWatcher.setObject(0, (byte) (sneaking ? 0x02 : 0));
        dataWatcher.setObject(2, this.displayName);
        dataWatcher.setObject(3, (byte) 0);
        dataWatcher.setObject(4, (byte) 0);
        dataWatcher.setObject(6, 20.0F);
        dataWatcher.setObject(15, (byte) 0);

        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server
                .ENTITY_METADATA);

        packet.getIntegers()
                .write(0, this.id);

        packet.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        return packet;
    }

    public PacketContainer createEntityLookPacket() {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_LOOK);

        packet.getIntegers()
                .write(0, this.id);

        packet.getBytes()
                .write(0, PositionUtils.yawToBytes(this.location.getYaw()))
                .write(1, (byte) 0.0F);

        packet.getBooleans()
                .write(0, true);

        return packet;
    }

    public PacketContainer createEntityHeadRotationPacket() {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);

        packet.getIntegers()
                .write(0, this.id);

        packet.getBytes()
                .write(0, PositionUtils.yawToBytes(this.location.getYaw()));

        return packet;
    }

    public PacketContainer createEntityRelativeMoveLookPacket(double x, double y, double z) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK);

        packet.getIntegers()
                .write(0, this.id);

        packet.getBytes()
                .write(0, (byte) (x * 32.0D))
                .write(1, (byte) (y * 32.0D))
                .write(2, (byte) (z * 32.0D))
                .write(3, PositionUtils.yawToBytes(this.location.getYaw()))
                .write(4, (byte) 0.0F);

        packet.getBooleans()
                .write(0, true);

        return packet;
    }

    public PacketContainer createEntityAnimationPacket() {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ANIMATION);

        packet.getIntegers()
                .write(0, this.id)
                .write(1, 0);

        return packet;
    }

    public static SpectreEntity getById(int id) {
        for (SpectreEntity entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }

        return null;
    }

    public static SpectreEntity getByUuid(UUID uuid) {
        for (SpectreEntity entity : entities) {
            if (entity.getUuid().equals(uuid)) {
                return entity;
            }
        }

        return null;
    }

}
