package fr.gardahaut.test1;

import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClipboardStore {
    private final Map<String, List<Block>> byId = new ConcurrentHashMap<>();

    public List<Block> clipboard(PlayerRef p){
        return byId.computeIfAbsent(p.getUuid().toString(), id -> new ArrayList<>());

    }

    public void addBlockToClipboard(PlayerRef p, String blockType, Vector3d absolutePosition) {
        List<Block> list = clipboard(p);

        Vector3d userPos = p.getTransform().getPosition();

        Vector3d relative = new Vector3d(
                absolutePosition.getX() - userPos.getX(),
                absolutePosition.getY() - userPos.getY(),
                absolutePosition.getZ() - userPos.getZ()
        );

        list.add(new Block(blockType, relative));
    }

    public List<Block> getBlocksWithAbsolutePosition(PlayerRef p) {
        List<Block> list = clipboard(p);
        Vector3d userPos = p.getTransform().getPosition();

        List<Block> result = new ArrayList<>(list.size());
        for (Block b : list) {
            // absolute = playerPos + relative
            Vector3d absolute = new Vector3d(
                    userPos.getX() + b.position().getX(),
                    userPos.getY() + b.position().getY(),
                    userPos.getZ() + b.position().getZ()
            );

            result.add(new Block(b.type(), absolute));
        }
        return result;
    }

    public void clearClipboard(PlayerRef p) {
        byId.remove(p.getUuid().toString());
    }
}
