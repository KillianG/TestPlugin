package fr.gardahaut.test1;

import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.entity.entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LandmarksStore {
    private final Map<String, List<Vector3d>> byId = new ConcurrentHashMap<>();

    public List<Vector3d> landmarks(Player p) {
        return byId.computeIfAbsent(p.getDisplayName(), id -> new ArrayList<>());
    }

    public void addLandmark(Player p, Vector3d landmark) {
        List<Vector3d> list = landmarks(p);

        Vector3d snap = new Vector3d(landmark.getX(), landmark.getY(), landmark.getZ());
        synchronized (list) {
            if (list.size() >= 2) {
                list.clear();
            }
            list.add(snap);
        }
    }
}