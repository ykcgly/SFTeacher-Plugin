package io.github.haiman233.sfteacher;

import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerSkin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部按钮定义（内容全部内嵌自原 RSC 配置 groups.yml，链接顺序与原 actions 一致）。
 *
 * <p>原 items.yml 中的 SFT0~SFT15 占位符物品（"这就是一个占位符而已"）无实际功能，
 * 独立版不再注册。原分类 sfteacher（nested，tier -1）由 {@link TeacherGroup} 等价实现。
 */
public final class Buttons {

    private Buttons() {
    }

    /**
     * 构建全部按钮（每次调用创建新 ItemStack 实例，避免外部修改共享状态）。
     * 顺序与原 groups.yml 中按钮在 sfteacher 分组下的声明顺序一致。
     */
    public static List<GuideButton> create(SFTeacherPlugin plugin) {
        String versionLine = "&b" + plugin.getPluginVersion();
        List<GuideButton> list = new ArrayList<>();

        // sft0: 信息（原 lore 版本号在 RSC 配置中停留在 Release-1.1，此处改为动态显示插件版本）
        list.add(new GuideButton("sft0", head(
                "16439d2e306b225516aa9a6d007a7e75edd2d5015d113b42f44be62a517e574f",
                "&f&l信息",
                "&e点击对应按钮获取教程链接！",
                " ",
                "&7本附属由海曼（haiman）编写而成",
                "&7旨在搜集全网的粘液科技及附属教程并将其添加至粘液科技书中",
                "&7",
                "&e使用方法：",
                "&7单击相应的分类会在聊天栏里给出链接与描述，点击链接即可查看相应教程",
                "&7",
                "&f当前版本：",
                versionLine,
                "&7您在提issues的时候需要展示这个",
                "&72026年8月18日，海曼系列附属宣布(大概率不会继续更新)",
                "&7如果有新的教程需要添加，或者教程链接失效",
                "&7欢迎联系我(ykcgly)的qq：1424136122",
                "&7或者直接在github提起pr或者issue",
                "&7点我获取仓库地址"
        ), links(
                "https://github.com/haiman233/SFTeacher"
        )));

        // sf_new
        list.add(new GuideButton("sf_new", item(Material.SLIME_BLOCK, "&a&l更新的粘液科技指南"), links(
                "https://space.bilibili.com/453524379?spm_id_from=333.788.upinfo.detail.click"
        )));

        // sft1
        list.add(new GuideButton("sft1", item(Material.SLIME_BALL, "&a&l粘液科技教程",
                "&7粘液科技新手入门教程"
        ), links(
                "https://slimefun-wiki.guizhanss.net/"
        )));

        // sf_guide
        list.add(new GuideButton("sf_guide", item(Material.OAK_SAPLING, "&a&l纯文本粘液科技指南",
                "&7一份有趣的粘液科技新手入门教程",
                "&7可能需要挂梯子"
        ), links(
                "https://github.com/1368139692/SFGuide"
        )));

        // sf_gltc
        list.add(new GuideButton("sf_gltc", head(
                "9d3d250e25bbca3a62be5b3ef02cfcab6dcdc424884c9a7d5cc95c9d0",
                "&#e017e8G&#cb12f2L&#b70efcT&#9b22ffC&#7c3fff联&#5d5bff合&#4c78ff协&#4b95ff议&7-&e教程",
                "&7月砧力作，前往主页查看更多教程"
        ), links(
                "https://www.bilibili.com/video/BV1kc7E6KEB5/?share_source=copy_web&vd_source=cd3c4aca54a80a40b6f3faa08bfe5551"
        )));

        // sf_qiyuan
        list.add(new GuideButton("sf_qiyuan", item(Material.GRASS_BLOCK,
                "&x&e&c&7&f&0&0起&x&e&e&9&e&0&7源&x&f&0&b&d&0&f工&x&f&2&d&c&1&6艺&b教程"
        ), links(
                "https://www.bilibili.com/video/BV1YWa36MEGe/?spm_id_from=333.337.search-card.all.click&vd_source=f8ef1ce3bbb5050f87d12742c05d23d1"
        )));

        // sft2
        list.add(new GuideButton("sft2", item(Material.SWEET_BERRIES, "&6&l粘液科技附属教程",
                "&7包含神秘、bump、星际、无尽、乌托邦、网络、匠魂、末地科技、水晶编年史、农耕、emc、异域、蓬松附属"
        ), links(
                "https://slimefun-addons-wiki.guizhanss.cn/"
        )));

        // sft3
        list.add(new GuideButton("sft3", item(Material.SLIME_BLOCK,
                "&c&l粘液科技及附属视频教程（匠魂、网络、无尽、流水线）",
                "&7b站up主 我的动力鞋"
        ), links(
                "https://space.bilibili.com/6993041/upload/video"
        )));

        // sf_ae2
        list.add(new GuideButton("sf_ae2", item(Material.GRAY_STAINED_GLASS, "&b&lAE2教程"), links(
                "https://www.bilibili.com/video/BV1esVT6jEnS/?spm_id_from=333.1387.homepage.video_card.click&vd_source=f8ef1ce3bbb5050f87d12742c05d23d1",
                "https://www.bilibili.com/video/BV1pGjT6KEoh/?spm_id_from=333.337.search-card.all.click&vd_source=f8ef1ce3bbb5050f87d12742c05d23d1"
        )));

        // sf_komu
        list.add(new GuideButton("sf_komu", head(
                "2a06b7ef30db3cd6a869e015e59913e4dfc9212688c281fab18b1f2938fc9f42",
                "{#00FA9A}&kA{#2DFBAE}&l口{#5AFCC2}&l木{#86FDD7}&l科{#B3FEEB}&l技{#E0FFFF}&kA&r&b教程",
                "&7第一个为作者本人发布的视频"
        ), links(
                "https://www.bilibili.com/video/BV1kdLc6mE4j/?spm_id_from=333.337.search-card.all.click&vd_source=f8ef1ce3bbb5050f87d12742c05d23d1",
                "https://www.bilibili.com/video/BV1VvLx6uERL/?spm_id_from=333.1387.homepage.video_card.click&vd_source=f8ef1ce3bbb5050f87d12742c05d23d1"
        )));

        // sft15
        list.add(new GuideButton("sft15", item(Material.SPYGLASS, "&c&l粘液科技全流程视频",
                "&7b站up主 阿伦Carry"
        ), links(
                "https://space.bilibili.com/1966538866"
        )));

        // sft4
        list.add(new GuideButton("sft4", item(Material.ENCHANTED_GOLDEN_APPLE, "&e&l粘液附属最详细的图文教程",
                "&7包含至尊、动力、网络、神秘、工业、乌托邦、星际、蓬松、水晶编年史的详细图文教程"
        ), links(
                "https://www.bilibili.com/read/readlist/rl613510?spm_id_from=333.1369.opus.module_collection.click"
        )));

        // sft5
        list.add(new GuideButton("sft5", item(Material.OBSERVER, "&f&l乱序技艺教程"), links(
                "https://space.bilibili.com/3546558593239930/lists/3255060?type=season",
                "https://space.bilibili.com/434769868/lists?sid=2332734&spm_id_from=333.788.0.0"
        )));

        // sft6（原配置第二条 link 写在嵌套列表里，行为与普通 link 相同）
        list.add(new GuideButton("sft6", item(Material.CHAIN_COMMAND_BLOCK, "&a&l乱码科技教程&毕业流程"), links(
                "https://space.bilibili.com/3546558593239930/lists/3261589?type=season",
                "https://www.bilibili.com/video/BV15qE26uETq/?spm_id_from=333.788.recommend_more_video.-1&trackid=web_related_0.router-related-2589621-kz84p.1791301028231.873&vd_source=f8ef1ce3bbb5050f87d12742c05d23d1"
        )));

        // sft13
        list.add(new GuideButton("sft13", item(Material.BUDDING_AMETHYST, "&d&l逻辑工艺教程",
                "&7注意github链接需要挂梯子才能打开",
                "&7悲报：逻辑删库了",
                "&7现逻辑仓库在以下第2个链接"
        ), links(
                "https://www.bilibili.com/video/BV1utqfYJEU5/?spm_id_from=333.337.search-card.all.click&vd_source=ce982ede1e0832bc43406d81947d0052",
                "https://github.com/Ruchikanmani/LogiTech/tree/master"
        )));

        // sft7
        list.add(new GuideButton("sft7", head(
                "1638469a599ceef7207537603248a9ab11ff591fd378bea4735b346a7fae893",
                "&e&l基因工程教程"
        ), links(
                "https://www.bilibili.com/opus/785094015913558088?from=search&spm_id_from=333.337.0.0",
                "https://www.bilibili.com/video/BV1LuYqeMEEv/?spm_id_from=333.337.search-card.all.click&vd_source=ce982ede1e0832bc43406d81947d0052"
        )));

        // sft8
        list.add(new GuideButton("sft8", item(Material.AZURE_BLUET, "&b&l农耕工艺教程"), links(
                "https://www.bilibili.com/opus/809658797667647490?from=search&spm_id_from=333.337.0.0"
        )));

        // sft9
        list.add(new GuideButton("sft9", item(Material.COOKED_BEEF, "&6&l美食家教程",
                "&7需要挂梯子"
        ), links(
                "https://schn.pages.dev/gastronomicon"
        )));

        // sft10
        list.add(new GuideButton("sft10", item(Material.CLAY, "&b&l粘土科技教程"), links(
                "https://www.bilibili.com/video/BV1XN41197Ed/?spm_id_from=333.1387.upload.video_card.click&vd_source=ce982ede1e0832bc43406d81947d0052"
        )));

        // sft11
        list.add(new GuideButton("sft11", item(Material.DIAMOND_BLOCK, "&a&l太空科技教程"), links(
                "https://www.bilibili.com/video/BV1DE411J7Ww/?spm_id_from=333.337.search-card.all.click&vd_source=ce982ede1e0832bc43406d81947d0052"
        )));

        // sft12
        list.add(new GuideButton("sft12", item(Material.WRITABLE_BOOK, "&f&l相关英文教程"), links(
                "https://docs.sefiraat.dev/"
        )));

        return list;
    }

    /** 普通材料物品 + 名称/lore（颜色代码经 {@link Colors#translate} 转换） */
    private static ItemStack item(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Colors.translate(name));
        if (lore.length > 0) {
            List<String> translated = new ArrayList<>(lore.length);
            for (String line : lore) {
                translated.add(Colors.translate(line));
            }
            meta.setLore(translated);
        }
        item.setItemMeta(meta);
        return item;
    }

    /** 玩家头颅（对应原配置 material_type: skull_hash，hash 为皮肤贴图 SHA-256） */
    private static ItemStack head(String hash, String name, String... lore) {
        ItemStack head = PlayerHead.getItemStack(PlayerSkin.fromHashCode(hash));
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName(Colors.translate(name));
        if (lore.length > 0) {
            List<String> translated = new ArrayList<>(lore.length);
            for (String line : lore) {
                translated.add(Colors.translate(line));
            }
            meta.setLore(translated);
        }
        head.setItemMeta(meta);
        return head;
    }

    private static List<String> links(String... urls) {
        return List.of(urls);
    }
}
