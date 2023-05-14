package items.list.guard;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import items.Item;

public class GuardSword extends Item {

	public GuardSword(int level) {
		super("Guard Sword [LV. " + level + "]", Material.IRON_SWORD);

		this.setPriority(false);
		this.setLegal(LegalityType.GuardsOnly);
		this.setDescription("&7Very dangerous sword. Used by ExodusPrison guards only.");
		this.setDropOnDeath(true);

		if (level >= 4)
			this.setMaterial(Material.DIAMOND_SWORD);

		this.compile();
		
		switch (level) {

		case 1:
			getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
			break;
		case 2:
			getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			break;
		case 3:
			getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
			getItemStack().addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
			break;
		case 4:
			break;
		case 5:
			getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
			break;
		case 6:
			getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
			break;
		case 7:
			getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			break;

		}


	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDamageEntity(Player p, EntityDamageByEntityEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUseOnEntity(Player p, PlayerInteractEntityEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBlockPlace(Player p, BlockPlaceEvent e) {
		// TODO Auto-generated method stub
		
	}

}