package com.myFileSys.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("blog", "id", Blog.class);
		arp.addMapping("t_document", "id", Document.class);
		arp.addMapping("t_group", "id", Group.class);
		arp.addMapping("t_group_rule", "id", GroupRule.class);
		arp.addMapping("t_memu", "id", Memu.class);
		arp.addMapping("t_memu_rule", "id", MemuRule.class);
		arp.addMapping("t_movie", "id", Movie.class);
		arp.addMapping("t_music", "id", Music.class);
		arp.addMapping("t_picture", "id", Picture.class);
		arp.addMapping("t_user", "id", User.class);
	}
}
