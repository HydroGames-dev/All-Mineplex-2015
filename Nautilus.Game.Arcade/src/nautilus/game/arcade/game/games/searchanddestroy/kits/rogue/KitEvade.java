package nautilus.game.arcade.game.games.searchanddestroy.kits.rogue;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import mineplex.core.common.util.C;
import mineplex.core.itemstack.ItemBuilder;
import mineplex.core.itemstack.ItemStackFactory;
import nautilus.game.arcade.ArcadeManager;
import nautilus.game.arcade.kit.Kit;
import nautilus.game.arcade.kit.KitAvailability;
import nautilus.game.arcade.kit.Perk;
import nautilus.game.arcade.kit.perks.PerkEvade;
import nautilus.game.arcade.kit.perks.PerkShadowmeld;
import nautilus.game.arcade.kit.perks.PerkSpeed;

public class KitEvade extends Kit
{
    public KitEvade(ArcadeManager manager)
    {
        super(manager, "Evade", KitAvailability.Hide, new String[]
            {

            }, new Perk[]
            {
                    new PerkSpeed(1), new PerkShadowmeld(), new PerkEvade()
            }, EntityType.ZOMBIE, new ItemStack(Material.DIAMOND_SWORD));
    }

    @Override
    public void GiveItems(Player player)
    {
        PlayerInventory inv = player.getInventory();
        inv.setItem(2, new ItemBuilder(Material.BLAZE_POWDER).setTitle(ChatColor.GOLD + "Fuse").build());
        inv.addItem(new ItemBuilder(Material.DIAMOND_SWORD)
                .setTitle(C.cGreen + "Hold Block" + C.cWhite + " - " + C.cYellow + "Evade").setUnbreakable(true).build());

        inv.setArmorContents(new ItemStack[]
            {
                    new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.LEATHER_LEGGINGS),
                    new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_HELMET)
            });

        inv.setItem(8,
                new ItemBuilder(Material.LEATHER_CHESTPLATE).setColor(this.Manager.GetGame().GetTeam(player).GetColorBase())
                        .setTitle(ChatColor.WHITE + "Evolve Kit Menu").addLore("Right click to use").build());
    }
}
