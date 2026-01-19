package fr.gardahaut.test1;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.DamageBlockEvent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import fr.gardahaut.test1.Utils.Utils;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class BlockDamageEventHandler extends EntityEventSystem<EntityStore, DamageBlockEvent> {
    LandmarksStore landmarksStore;
    public BlockDamageEventHandler(LandmarksStore landmarksStore) {
        super(DamageBlockEvent.class);
        this.landmarksStore = landmarksStore;
    }

    @Override
    public void handle(int index,
                       @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
                       @NonNullDecl Store<EntityStore> store,
                       @NonNullDecl CommandBuffer<EntityStore> commandBuffer,
                       @NonNullDecl DamageBlockEvent e) {

        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
        Player player = store.getComponent(ref, Player.getComponentType());

        if (player == null) {
            return;
        }

        ItemStack is = player.getInventory().getActiveHotbarItem();
        if (is == null){
            return;
        }

        Boolean isWE = is.getFromMetadataOrNull("WorldEdit", Codec.BOOLEAN);
        if (isWE == null || !isWE) return;

        landmarksStore.addLandmark(player, e.getTargetBlock().toVector3d());
        player.sendMessage(Utils.createWorldEditMessage("Landmarked!"));
        e.setCancelled(true);
    }

    @Override
    public Query<EntityStore> getQuery() {
        return Archetype.empty();
    }
}
