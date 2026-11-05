package io.github.haiman233.sfteacher;

import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.core.guide.GuideHistory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideImplementation;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.core.services.sounds.SoundEffect;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.guide.SurvivalSlimefunGuide;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * 「粘液科技及附属教程」分组：对应原 RSC 配置中的 nested 分组 sfteacher（tier -1）。
 *
 * <p>实现方式与 RSC 的 RSCItemGroupLegacy 等价：FlexItemGroup + ChestMenu，
 * 点击按钮只执行 link action（聊天栏输出可点击链接），不打开配方页。
 */
public final class TeacherGroup extends FlexItemGroup {

    /** 每页按钮数（可用槽位 9~44，共 36 个） */
    private static final int BUTTONS_PER_PAGE = 36;
    private static final int FIRST_SLOT = 9;
    private static final int LAST_SLOT = 45;
    private static final int PREV_SLOT = 46;
    private static final int NEXT_SLOT = 52;

    private final JavaPlugin plugin;
    private final List<GuideButton> buttons;

    public TeacherGroup(JavaPlugin plugin, NamespacedKey key, ItemStack icon, int tier, List<GuideButton> buttons) {
        super(key, icon, tier);
        this.plugin = plugin;
        this.buttons = buttons;
    }

    @Override
    public boolean isVisible(Player p, PlayerProfile profile, SlimefunGuideMode mode) {
        // 插件被禁用（如被其他插件卸载/重载）时，Slimefun 注册表中的分组不会自动清理，
        // 此时隐藏分组，避免玩家点到残留的僵尸分组后触发插件 jar 已关闭的类加载错误
        return plugin.isEnabled();
    }

    @Override
    public void open(Player p, PlayerProfile profile, SlimefunGuideMode mode) {
        if (!plugin.isEnabled()) {
            plugin.getLogger().warning("玩家 " + p.getName() + " 尝试打开分组，但 SFTeacher 当前处于禁用状态"
                    + "（可能被其他插件卸载/重载，或启动时发生错误），已拒绝操作。请检查服务器日志并重启。");
            p.sendMessage(Colors.translate("&c[SFTeacher] 插件当前处于禁用状态，无法打开教程分组，请重启服务器后重试。"));
            return;
        }
        openPage(p, profile, mode, 1);
    }

    private void openPage(Player p, PlayerProfile profile, SlimefunGuideMode mode, int page) {
        GuideHistory history = profile.getGuideHistory();
        if (mode == SlimefunGuideMode.SURVIVAL_MODE) {
            history.add(this, page);
        }

        ChestMenu menu = new ChestMenu(Slimefun.getLocalization().getMessage(p, "guide.title.main"));
        menu.setEmptySlotsClickable(false);
        menu.addMenuOpeningHandler(SoundEffect.GUIDE_BUTTON_CLICK_SOUND::playFor);
        SlimefunGuideImplementation guide = Slimefun.getRegistry().getSlimefunGuide(mode);
        if (guide instanceof SurvivalSlimefunGuide survivalGuide) {
            survivalGuide.createHeader(p, profile, menu);
        }

        // 返回按钮（与 RSC 一致：有历史记录则 goBack，否则回主菜单；Shift 恒回主菜单）
        ItemStack backIcon;
        if (history.size() > 1) {
            backIcon = ChestMenuUtils.getBackButton(p, "",
                    org.bukkit.ChatColor.GRAY + Slimefun.getLocalization().getMessage(p, "guide.back.guide"));
        } else {
            backIcon = ChestMenuUtils.getBackButton(p, "",
                    "&f左键: &7返回上一页",
                    "&fShift + 左键: &7返回主菜单");
        }
        menu.addItem(1, backIcon, (pl, slot, item, action) -> {
            GuideHistory gh = profile.getGuideHistory();
            if (gh.size() == 1 || action.isShiftClicked()) {
                SlimefunGuide.openMainMenu(profile, mode, gh.getMainMenuPage());
                return false;
            }
            gh.goBack(guide);
            return false;
        });

        // 按钮内容（每页 36 个）
        int pages = (buttons.size() + BUTTONS_PER_PAGE - 1) / BUTTONS_PER_PAGE;
        int start = BUTTONS_PER_PAGE * (page - 1);
        int end = Math.min(start + BUTTONS_PER_PAGE, buttons.size());
        int slot = FIRST_SLOT;
        for (int i = start; i < end && slot < LAST_SLOT; i++, slot++) {
            GuideButton button = buttons.get(i);
            menu.addItem(slot, button.icon(), (pl, s, item, action) -> {
                sendLinks(pl, button);
                return false;
            });
        }

        menu.addItem(PREV_SLOT, ChestMenuUtils.getPreviousButton(p, page, pages));
        menu.addMenuClickHandler(PREV_SLOT, (pl, s, item, action) -> {
            int previous = page - 1;
            if (previous > 0) {
                openPage(p, profile, mode, previous);
            }
            return false;
        });
        menu.addItem(NEXT_SLOT, ChestMenuUtils.getNextButton(p, page, pages));
        menu.addMenuClickHandler(NEXT_SLOT, (pl, s, item, action) -> {
            int next = page + 1;
            if (next <= pages) {
                openPage(p, profile, mode, next);
            }
            return false;
        });

        menu.open(p);
    }

    /**
     * 输出链接到聊天栏，行为与 RSC 的 readAction("link ...") 完全一致：
     * 先发送提示行，再发送灰色可点击组件（hover 显示 URL，点击 OPEN_URL）。
     * 多链接按钮逐条输出。
     */
    private void sendLinks(Player p, GuideButton button) {
        for (String url : button.links()) {
            p.sendMessage(Colors.translate("&e单击此处打开链接: "));
            TextComponent link = new TextComponent(url);
            link.setColor(ChatColor.GRAY);
            link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new Text(Colors.translate("&e" + url))));
            link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
            p.spigot().sendMessage(link);
        }
    }

    /** 按钮总数（按钮不是 SlimefunItem，不进入 getItems()） */
    public int buttonCount() {
        return buttons.size();
    }

    /** 全部按钮的链接总数 */
    public int totalLinkCount() {
        return buttons.stream().mapToInt(b -> b.links().size()).sum();
    }
}
