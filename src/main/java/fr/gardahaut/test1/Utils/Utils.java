package fr.gardahaut.test1.Utils;

import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.Message;

import java.awt.*;

public class Utils {
    public static int bx(Vector3d v) { return (int) Math.floor(v.getX()); }
    public static int by(Vector3d v) { return (int) Math.floor(v.getY()); }
    public static int bz(Vector3d v) { return (int) Math.floor(v.getZ()); }

    public static Message createWorldEditMessage(String rawMessage) {
        return Message.join(
                Message.raw("[WorldEdit] ").color(Color.RED),
                Message.raw(rawMessage)
        );
    }
}
