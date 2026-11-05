package io.github.haiman233.sfteacher;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 颜色代码转换工具。
 *
 * <p>原 RSC 配置中使用了多种颜色格式（由 CMIChatColor.translate 统一处理），
 * 本工具在代码侧完成等价转换，全部输出为 Bukkit 标准 legacy 格式（§x 十六进制 + § 功能码）：
 * <ul>
 *     <li>{@code {#RRGGBB}} —— 如 sf_komu 按钮名称</li>
 *     <li>{@code &#RRGGBB} —— 如 sf_gltc 按钮名称</li>
 *     <li>{@code &x&R&G&B&R&G&B}（Spigot legacy 十六进制的 & 写法）—— 如 sf_qiyuan 按钮名称</li>
 *     <li>{@code &0-9a-fk-or} 等常规功能码，最后统一交给 translateAlternateColorCodes</li>
 * </ul>
 */
public final class Colors {

    private static final Pattern BRACE_HEX = Pattern.compile("\\{#([0-9A-Fa-f]{6})}");
    private static final Pattern AMP_HEX = Pattern.compile("&#([0-9A-Fa-f]{6})");
    private static final Pattern X_HEX = Pattern.compile("&x((?:&[0-9A-Fa-f]){6})");

    private Colors() {
    }

    public static String translate(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        s = replaceHex(BRACE_HEX, s, false);
        s = replaceHex(AMP_HEX, s, false);
        s = replaceHex(X_HEX, s, true);
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private static String replaceHex(Pattern pattern, String input, boolean xFormat) {
        Matcher m = pattern.matcher(input);
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            String replacement = xFormat
                    ? "§x" + m.group(1).replace('&', '§')
                    : toLegacyHex(m.group(1));
            m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /** "#e017e8" -> "§x§e§0§1§7§e§8" */
    private static String toLegacyHex(String hex) {
        StringBuilder b = new StringBuilder("§x");
        for (char c : hex.toCharArray()) {
            b.append('§').append(Character.toLowerCase(c));
        }
        return b.toString();
    }
}
