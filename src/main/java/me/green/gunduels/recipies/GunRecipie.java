package me.green.gunduels.recipies;

import me.green.gunduels.Gun;
import me.green.gunduels.GunRarity;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class GunRecipie {
    public static ShapedRecipe gunRecipie() {
        Gun gun = new Gun(new ItemStack(Material.IRON_BARDING), "Gun", GunRarity.COMMON, "This is a gun");
        ShapedRecipe recipe = new ShapedRecipe(gun.getItem());
        recipe.shape(
                "III",
                "GBG",
                "IDI");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('B', Material.IRON_BARDING);
        recipe.setIngredient('G', Material.SULPHUR);
        recipe.setIngredient('D', Material.DIAMOND);
        return recipe;
    }

}
