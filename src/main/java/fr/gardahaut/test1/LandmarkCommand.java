package fr.gardahaut.test1;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import fr.gardahaut.test1.Utils.Utils;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.awt.Color;
import java.util.List;

public class LandmarkCommand extends AbstractPlayerCommand {

    private final LandmarksStore landmarksStore;
    public LandmarkCommand(@NonNullDecl String name, @NonNullDecl String description, LandmarksStore landmarksStore) {
        super(name, description);
        this.landmarksStore = landmarksStore;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        Player p = store.getComponent(ref, Player.getComponentType());
        assert p != null;

        Vector3d pt = playerRef.getTransform().getPosition();
        landmarksStore.addLandmark(p, pt);
        p.sendMessage(Utils.createWorldEditMessage("Landmarked!"));
        List<Vector3d> lm = landmarksStore.landmarks(p);
        lm.forEach(v -> p.sendMessage(Message.raw("Landmark: " + v.toString())));
    }
}
