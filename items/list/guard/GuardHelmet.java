package items.list.guard;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import items.Item;

public class GuardHelmet  extends Item {

	public GuardHelmet(int level) {
		super("Guards Helmet [LV. " + level + "]", Material.CHAINMAIL_HELMET);
		
		this.setPriority(true);
		this.addCustomLore("&7Casual armor for ExodusPrison guards");
		this.addCustomLore("&cPrisoners wearing this armor will be killed on sight.");
		this.setLegal(LegalityType.GuardsOnly);
		this.setDropOnDeath(true);
		
		if(level >= 4)
			this.setMaterial(Material.DIAMOND_HELMET);
		
		this.compile();
		
		switch (level) {

		case 1:
			getItemStack().addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			break;
		case 2:
			getItemStack().addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			break;
		case 3:
			getItemStack().addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
			break;
		case 4:
			break;
		case 5:
			getItemStack().addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			break;
		case 6:
			getItemStack().addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			break;
		case 7:
			getItemStack().addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			break;
		case 8:
			getItemStack().addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
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
