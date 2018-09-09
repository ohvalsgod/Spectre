package me.ohvalsgod.spectre.thread;

import me.ohvalsgod.spectre.entity.SpectreEntity;

/**
 * Ticks entities that have animations
 */
public class AnimationThread extends Thread {

    @Override
    public void run() {
        while (true) {
            for (SpectreEntity entity : SpectreEntity.getEntities()) {
                if (entity.getAnimation() != null) {
                    if (entity.getAnimation().tick()) {
                        entity.getAnimation().sendPackets();
                    }
                }
            }

            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
