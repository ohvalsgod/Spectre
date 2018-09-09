package me.ohvalsgod.spectre.texture;

import lombok.Data;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Texture {

    @Getter
    private static Set<Texture> textures = new HashSet<>();

    private String username;
    private UUID uuid;
    private String value;
    private String signature;

    public Texture(UUID uuid) {

    }

    public static Texture getByUsername(String username) {
        for (Texture texture : textures) {
            if (texture.getUsername().equalsIgnoreCase(username)) {
                return texture;
            }
        }
        return null;
    }

}
