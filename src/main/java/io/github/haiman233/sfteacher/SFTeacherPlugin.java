package io.github.haiman233.sfteacher;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * SFTeacher —— 粘液科技教程（独立 Slimefun 附属，由 RSC 配置版改写而来）。
 *
 * <p>功能：在粘液科技指南主菜单注册一个「粘液科技及附属教程」分组，
 * 打开后为教程链接按钮列表，点击按钮在聊天栏输出可点击跳转的链接。
 * 全部内容内嵌于代码，无任何配置文件。
 */
public final class SFTeacherPlugin extends JavaPlugin implements SlimefunAddon {

    private TeacherGroup group;

    @Override
    public void onEnable() {
        if (!getServer().getPluginManager().isPluginEnabled("Slimefun")) {
            getLogger().severe("未检测到 Slimefun！SFTeacher 是 Slimefun 附属，请先安装 Slimefun。插件将自动禁用。");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // 分组图标：成书，对应原 RSC 分组 sfteacher 的 item 定义
        ItemStack icon = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta iconMeta = icon.getItemMeta();
        iconMeta.setDisplayName(Colors.translate("&6&l粘液科技及附属教程"));
        icon.setItemMeta(iconMeta);

        group = new TeacherGroup(this, new NamespacedKey(this, "sfteacher"), icon, -1, Buttons.create(this));
        group.register(this);

        // 注意：不要调用 FlexItemGroup.getItems()——f172239 分支中它会直接抛
        // UnsupportedOperationException: A FlexItemGroup has no items!
        // （Flex 分组不持有 SlimefunItem，按钮不是 SlimefunItem，只统计按钮数即可）
        getLogger().info("粘液科技教程 加载成功，已注册分组 sfteacher，教程按钮 " + group.buttonCount() + " 个，"
                + "共 " + group.totalLinkCount() + " 条教程链接。");
        getLogger().info("感谢您使用粘液科技教程！Bug 反馈：https://github.com/haiman233/SFTeacher/issues");
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/haiman233/SFTeacher/issues";
    }
}
