package me.joeleoli.spectre;

import me.joeleoli.spectre.animation.impl.SneakingAnimation;
import me.joeleoli.spectre.animation.impl.WalkingAnimation;
import me.joeleoli.spectre.direction.Direction;
import me.joeleoli.spectre.entity.SpectreEntity;
import me.joeleoli.spectre.texture.Texture;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

/**
 * Run as a standalone plugin for testing purposes only.
 */
public class SpectrePlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);

        // Instantiate API
        new Spectre(this);

        // Register textures
        // TODO: Pull texture values from Mojang instead of storing manually
        Texture jon = new Texture();

        jon.setUsername("itsjhalt");
        jon.setUuid(UUID.fromString("401202a3-0102-4ed8-979a-e5d4832c8a9b"));
        jon.setValue("eyJ0aW1lc3RhbXAiOjE1MjY2MTM1Mjk4NjgsInByb2ZpbGVJZCI6IjQwMTIwMmEzMDEwMjRlZDg5NzlhZTVkNDgzMmM4YTliIiwicHJvZmlsZU5hbWUiOiJpdHNqaGFsdCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjJhMDVkOTdhMTZiMjU5MTk5MTExOWI5M2RjZTZjNzllYWM0NWU0NzE4NTJlODc3YWRhMWU4ZDdkMzJiNTg3In19fQ==");
        jon.setSignature("q2iQDpKDgiNVv9q0ZUl12jdIMtuhyrduPLqjlalnad5G6F3ewQkvFso8sN/ae3NV9QYcRLj7//tP4PvpdUZ2GyVUIVV7+hf9xqLQSSPszqJHZnHhmF6SbnS+NQzReYHXbIuMdHNjugNYA9WO1gUIq89ySZT/WUTxJ3gnItfFrY8j3DmrQEyv7yPceNX4wHhtyiqaZRXFx1mhzT8Zrdh/wVKCfaqGHvBaMbLHundobK4KsBF8Sxlsn8SWyAjSOqqQdoqvumOTD2LqPRo2r9jNjkRC8yx32oM/EbtzdDRtRWjcvuhx0gbm31JhRhKyrJnGWGntjXJvrdwcPY+MMY+gX0WJ9ZwvPkl+HrdACc7q7CZBsckBZmcFyL10CsiTAAOAIhCWrsBxpkrWCDcvBPPC68LHBGqvOX5qKTIH2avgR0Vl2X73McPKzg7Q55eF1nR7OQnPAF2m+8rlMFeZWj0JLDxeIV9ljfP66HvjWE09v9cs+3m5QtuWebmhy+LBUTTbrahvb6FC0A+2BzyeKhqrrkz7mM3CC5PFhARnlOJQ9cyjD9jnBUSjqDf0+fuO0Ho9wSaTgTx5iBormT0DAUbQt75WF0ZQfv8MuIe+I2XzRe4rTs4p5gqDtJ0/Hm1m7E2PNWAW4DSlXShqgc3KF1e0Xuotvny53+uXmYpdyGck49g=");

        Texture zylowh = new Texture();

        zylowh.setUsername("Zylowh");
        zylowh.setUuid(UUID.fromString("cea81683-1e32-4160-9dfd-4d99fc62cd08"));
        zylowh.setValue("eyJ0aW1lc3RhbXAiOjE1MjcwODgyMDEwNTcsInByb2ZpbGVJZCI6ImNlYTgxNjgzMWUzMjQxNjA5ZGZkNGQ5OWZjNjJjZDA4IiwicHJvZmlsZU5hbWUiOiJaeWxvd2giLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzI1M2EzYmM0ZTJjMDU4NjNmOWE3MmYzMzk5ZmJkYjEwYTFjZGNjYzliMjU3MGI4MzU0ZmI2M2RjMWI0MjEwMGIifX19");
        zylowh.setSignature("kUWo2NsHRMhbBRz3aV42y7+afnUGDtCzz7D041w6vKd7XEbFC8wyuj/9yHG2UfahKfkIb3Khy+6twGjS49uHo9tsQJStdGAEz/8GYo/k0Tdnadl4F/F9FPdQUbKnnAVVkCmuU4yl3OSH7EPpuRkIQj2zYNX8uYU/otwSOZ7htFSjD1JHhYj/PRBJwEbu2tpahHiPYuZUVEcwhuFRphGUS/YQS+cFGUu0TASJGamOJ8G26s6oE/c6AzzIfsGWn5BB1KJfbk471VDi/cuM2kSDT1KPEaNquPD+5Gn1b/QAbMqVtrB3U3GivxR75NYEFeUjupfSHJ6nFyRYLUztqhpHr/fRuVnzIOi/uM3Pq423qSviK0Xp25rkzfxD0fLFRyMs7+Bnw6eh2XFZIhuO24hrPYp9SYN++NjgrdYFupEAp6CsNZZ2gF/1UxHCv6UVGMvah7B/0kVd6456meaPRcjRlJJzauPyEoWTB/XksyU2vedIGWVxthEkzSKBSPOWxaLOoNBFfeEDeqVgZXAl8XsKPwCIpOqT2QfzOqNqCFMzkCFkn8KukjhD61JVHyTYHNLxOtH7+7W4AsLQN440IMuZskUpQxzXFUeZcW89mKcsf3EkpLizsDgp5ckCrTyy00oCqipdG7FUi8DpctPJBKsDDBxPE6czmlUIZEBVUMFJAGM=");

        Texture idiol = new Texture();

        idiol.setUsername("Idiol");
        idiol.setUuid(UUID.fromString("f99ed3b5-abcf-48fc-9949-f0a2fa3cb9b4"));
        idiol.setValue("eyJ0aW1lc3RhbXAiOjE1MjcwOTMzMjI3NzcsInByb2ZpbGVJZCI6ImY5OWVkM2I1YWJjZjQ4ZmM5OTQ5ZjBhMmZhM2NiOWI0IiwicHJvZmlsZU5hbWUiOiJJZGlvbCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzA5NzRjOWFkNjUwYWZkMjM3OTgwZjliYzQ4ZjdjNzViOWJiNzQ1NGM2YzhjNzZiNjA5M2FiNmIxNDg3ZGJhMCJ9fX0=");
        idiol.setSignature("eHFprteQiJJIzgeT/a+AdCjAQLtKzvmblvuDan4uI/yS9Cj3ajPRPhgQwKqucZ+Bvo3MTCxBTz/PW7Gx+P71B8Se2esZJ1cAcZ8gTK0PjTtaGgOD00thlE63S/eBX0o3sjRBhNhKZxfPh1e+wjORNd1qerO4pn/xXXJ1e1bbnl8HSOAbCSK+vW7chV1751dID2hSvevl66veOWkvv6SYosgo4SMgTzT+nE9/O2L0HbNNzZOMR0aPt41y5TereTKWJ/Iy3GUaedQN1b5FYSnEHFKcWn5+zGsUWIA8PTV9fdWPm+H9E6dApHL8+eA2g0zv90LPHg+l9iiQ6raqROfURcXD60c71HeoslCJNwRHdl6+wtWR65vG0TPiq5dQ/3Kak+84q2pjsZcdmakpIsYyZoh5vICpJYqSw1nl/G/ILXMHPkSoGsI43bfRwpotDgyWS1DXqPOR19mBmk08ukLyr5Knaxhx6FajWH5cdrO0lxRGY5M2ZCwi2DyOKVFRnZ6L5mXqpRoGyrZIGbMK8Oef3GsDjrU2G2Pu5NY51Pkc71Hwha27HxRB30wIQns7CHQ9r0E/4xozeDxljD8NXJmBs01fHMNzcIJyp19omb8XsyPvmsj9mk4XL47u8dAG+M7D/WDq9xREclCbG571bcamVYCwaK4SoubQB8d1Mn3xm/A=");

        Texture prestige = new Texture();
        prestige.setUsername("Prestige_PvP");
        prestige.setUuid(UUID.fromString("ab012a04-4c96-456f-86a0-cb7bdfdb064b"));
        prestige.setValue("eyJ0aW1lc3RhbXAiOjE1MjcxMjk1NzY0MDEsInByb2ZpbGVJZCI6ImFiMDEyYTA0NGM5NjQ1NmY4NmEwY2I3YmRmZGIwNjRiIiwicHJvZmlsZU5hbWUiOiJQcmVzdGlnZV9QdlAiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRjZjRhYTFjNGE1NDE4YWNkMTE3NDU0ZjA0NDIzNzc2YzdlMjdlNDM0ZjBmYzdmNzMzYWY5ODdmMjJkNjkwZGMifX19");
        prestige.setSignature("WPkvrcsVYjlDgZ8NajHgfPU4PoTuoRSbPF2/BuB4mJcql1pVxjdatAbTYVWlqtYUFuMY/YuuFLBQfhOLvHZ82WvshM8/vj4HU6svlYBYx7EzlrcuOOw7l58wra3w3PvLImiqWcqKTI0vExH7dZYzeMtR2OozJ97Bo25XvWTZcwE2svbyYDlEpZt++Xich8r33h1mShLaA/QDFLu3LkVp8KknZN20SpH1zAvjUFCeb0Cl2aqVD7eNVE+zeO4GWNRVr/kpMnJxL9wrOosn6lwQORBOZjL9gvcaEvT0BsqyyN1m87HsSPGBcxsu9uSPrGdsUKhGHOa2OqtzFoLstqMgE5ZJ7Z1X6fv5yzB5P+4/P3g0G4uVI6NMw4geESKjSw3VbjYrbYBBONne3bjsTqiz7SGiOBUpoEjFqUM/tYEyDtGYWEp8RVmfkIZW1+c03K1yMqNkbrWpb0m0hY91fg57SoYZl6SNFTdCEcURsdLptVZ2jmmUHRDWRa0fKVP1/aDTzEOk5RPl1QkR+mhkEkzsUpycjYdTqLIhcXlU58Kf8f2MEI3F4rNM2WExTmTpDtyQ+OgUuGkMcntLgtRKJvx+HMh0/PLCpgzW9TBuEsiQfpuTKewzCKwOgE3T1mZa4gzN3ftY/fBofOfKHMGEj7lFKDOe5uBEaQxLXL7VhBBGGX0=");

        Texture.getTextures().add(jon);
        Texture.getTextures().add(zylowh);
        Texture.getTextures().add(idiol);
        Texture.getTextures().add(prestige);
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        switch (event.getMessage().toLowerCase()) {
            case "buttfuck": {
                Location cloned = event.getPlayer().getLocation().clone();

                SpectreEntity tre = new SpectreEntity(ChatColor.BLUE + "Prestige_PvP", cloned);

                tre.setTexture(Texture.getByUsername("prestige_pvp"));
                tre.setAnimation(new SneakingAnimation(tre));
                tre.sendInitialPackets(event.getPlayer());

                Direction.getFromYaw(event.getPlayer().getLocation().getYaw()).getOpposite().move(cloned, 0.6F);

                SpectreEntity jon = new SpectreEntity(ChatColor.DARK_RED + "itsjhalt", cloned);

                jon.setTexture(Texture.getByUsername("itsjhalt"));
                jon.sendInitialPackets(event.getPlayer());
            }
            break;
            case "zylowh": {
                SpectreEntity entity = new SpectreEntity(ChatColor.DARK_RED + "Grandpa", event.getPlayer().getLocation());

                entity.setTexture(Texture.getByUsername("zylowh"));
                entity.setAnimation(new WalkingAnimation(entity));
                entity.sendInitialPackets(event.getPlayer());
            }
            break;
            case "itsjhalt": {
                SpectreEntity entity = new SpectreEntity(ChatColor.DARK_RED + "itsjhalt", event.getPlayer().getLocation());

                entity.setTexture(Texture.getByUsername("itsjhalt"));
                entity.setAnimation(new WalkingAnimation(entity));
                entity.sendInitialPackets(event.getPlayer());
            }
            break;
            default:
                return;
        }

        event.setCancelled(true);
    }

}
