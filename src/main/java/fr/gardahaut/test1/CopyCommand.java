package fr.gardahaut.test1;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import fr.gardahaut.test1.Utils.Utils;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CopyCommand extends AbstractPlayerCommand {
    private final LandmarksStore landmarksStore;
    private final ClipboardStore clipboardStore;
    public CopyCommand(@NonNullDecl String name, @NonNullDecl String description, LandmarksStore landmarksStore, ClipboardStore clipboardStore) {
        super(name, description);
        this.landmarksStore = landmarksStore;
        this.clipboardStore = clipboardStore;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        Player p = store.getComponent(ref, Player.getComponentType());
        assert p != null;

        List<Vector3d> landmarks = landmarksStore.landmarks(p);
        if (landmarks.size() != 2) {
            p.sendMessage(Utils.createWorldEditMessage("You need exactly 2 landmarks to use this command"));
            return;
        }

        Vector3d a = landmarks.get(0);
        Vector3d b = landmarks.get(1);

        int ax = Utils.bx(a), ay = Utils.by(a), az = Utils.bz(a);
        int bx = Utils.bx(b), by = Utils.by(b), bz = Utils.bz(b);

        int minX = Math.min(ax, bx), maxX = Math.max(ax, bx);
        int minY = Math.min(ay, by), maxY = Math.max(ay, by);
        int minZ = Math.min(az, bz), maxZ = Math.max(az, bz);

        clipboardStore.clearClipboard(playerRef);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockType blockType = world.getBlockType(x, y, z);
                    if (blockType != null) {
                        clipboardStore.addBlockToClipboard(playerRef, blockType.getId(), new Vector3d(x, y, z));
                    }
                }
            }
        }
    }
}
