package io.github.haiman233.sfteacher;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * 指南按钮模型：对应原 RSC 配置 groups.yml 中 {@code type: button} 的条目。
 *
 * @param id    原配置中的按钮 ID（如 sft0、sf_ae2），仅用于日志与排查
 * @param icon  在分组菜单页中显示的图标物品
 * @param links 点击按钮后在聊天栏输出的链接（原 actions 中的 "link <url>" 列表，顺序保持一致）
 */
public record GuideButton(String id, ItemStack icon, List<String> links) {
}
