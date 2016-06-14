package flavor.pie.util.conversions;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.item.inventory.ItemStack;

public class ItemStackTranslator {
    private ItemStackTranslator(){}
    private static HoconConfigurationLoader generator;
    static {
        generator = HoconConfigurationLoader.builder().build();
    }
    public static ConfigurationNode convertItem(ItemStack in) {
        ConfigurationNode node = generator.createEmptyNode();
        try {
            node.setValue(TypeToken.of(ItemStack.class), in);
        } catch (ObjectMappingException ignored) {}
        ConfigurationNode out = generator.createEmptyNode();
        out.getNode("tag").setValue(node.getNode("UnsafeData").getValue());
        out.getNode("id").setValue(node.getNode("ItemType").getValue());
        out.getNode("Damage").setValue(node.getNode("UnsafeDamage").getValue());
        out.getNode("Count").setValue(node.getNode("Count").getValue());
        return out;
    }
    public static ItemStack convertNode(ConfigurationNode in) throws ObjectMappingException {
        ConfigurationNode out = generator.createEmptyNode();
        out.getNode("UnsafeData").setValue(in.getNode("tag").getValue());
        out.getNode("ItemType").setValue(in.getNode("id").getValue());
        out.getNode("ContentVersion").setValue(1);
        out.getNode("UnsafeDamage").setValue(in.getNode("Damage").getValue());
        out.getNode("Count").setValue(in.getNode("Count").getValue());
        return out.getValue(TypeToken.of(ItemStack.class));
    }
}