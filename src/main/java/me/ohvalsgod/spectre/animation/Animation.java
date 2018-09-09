package me.ohvalsgod.spectre.animation;

import me.ohvalsgod.spectre.entity.SpectreEntity;

import lombok.Data;

@Data
public abstract class Animation {

    protected SpectreEntity entity;
    private Long current;
    private Long ticks;

    public Animation(SpectreEntity entity, Long ticks) {
        this.entity = entity;
        this.current = 0L;
        this.ticks = ticks;
    }

    public boolean tick() {
        this.current++;

        if (this.current % this.ticks == 0) {
            this.onTick();

            return true;
        }

        return false;
    }

    public abstract void onTick();

    public abstract void sendPackets();

}
