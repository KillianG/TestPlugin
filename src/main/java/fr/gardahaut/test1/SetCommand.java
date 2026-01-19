package fr.gardahaut.test1;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.awt.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.gardahaut.test1.Utils.Utils;

public class SetCommand extends AbstractPlayerCommand {

    private final LandmarksStore landmarksStore;
    private final RequiredArg<String> blockType;

    public SetCommand(@NonNullDecl String name, @NonNullDecl String description, LandmarksStore landmarksStore) {
        super(name, description);
        this.landmarksStore = landmarksStore;
        this.blockType = this.withRequiredArg("type", "The block type you want to place between the landmarks", ArgTypes.BLOCK_TYPE_KEY);

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

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    world.setBlock(x, y, z, this.blockType.get(commandContext));
                }
            }
        }
    }
}
