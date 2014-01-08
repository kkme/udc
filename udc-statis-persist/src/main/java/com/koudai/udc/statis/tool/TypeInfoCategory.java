package com.koudai.udc.statis.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TypeInfoCategory {

	public static final String GIRL = "Combine_Default";

	public static final String MAN = "Combine_17de56cf1b05e7813e91b551fdaae218";

	public static final String WOMAN = "Combine_058985b630953675f099ec4169eaae51";

	public static final String BABY = "Combine_0e4dc816cc67537fe71921a781400097";

	public static final String TECHNOLOGY = "Combine_853bc55c1041f24b9392069331e65336";

	public static final List<String> STAT_TYPES = new ArrayList<String>();

	static {
		STAT_TYPES.add(GIRL);
		STAT_TYPES.add(MAN);
		STAT_TYPES.add(WOMAN);
		STAT_TYPES.add(BABY);
		STAT_TYPES.add(TECHNOLOGY);
	}

	public static final Map<String, String> GIRL_TYPES = new HashMap<String, String>();

	static {
		GIRL_TYPES.put("Combine_020afd83a30b4605053f088cacbedd60", "靓衣");
		GIRL_TYPES.put("Combine_1a9d2b6fc4bf27017ba9925ae2491124", "家居");
		GIRL_TYPES.put("Combine_3a9b9cf6e1432dc2e0d547dc47febf4d", "潮包");
		GIRL_TYPES.put("Combine_7e19e2617bfa3e70db4e6f9c153ab82c", "配饰");
		GIRL_TYPES.put("Combine_ab7a5e833dacdcb46c35df05c32111a1", "美鞋");
	}

	public static final Map<String, String> MAN_TYPES = new HashMap<String, String>();

	static {
		MAN_TYPES.put("Combine_014f3711ba7cb6c08583bb41fa13530c", "玩物");
		MAN_TYPES.put("Combine_523b32f8e708e3a416fca6ed3a9f5752", "鞋展");
		MAN_TYPES.put("Combine_6d10357413ffe80e45dd3bc4f5ad3aa4", "潮服");
		MAN_TYPES.put("Combine_853bc55c1041f24b9392069331e65336", "3C");
		MAN_TYPES.put("Combine_ae88884a3f14018c1a14fdf69c43d6d2", "IN包");
	}

	public static final Map<String, String> WOMAN_TYPES = new HashMap<String, String>();

	static {
		WOMAN_TYPES.put("Combine_00ed2c0563df52e2ec871eb4ac07caa5", "服饰");
		WOMAN_TYPES.put("Combine_10f739f3f20718e4811f5ed0b3346d67", "包袋");
		WOMAN_TYPES.put("Combine_1e73c9048ac74f1bdb9f7bc87fe63453", "鞋履");
		WOMAN_TYPES.put("Combine_8ccb67af51d0b55bfac23813d33b5da9", "美容");
		WOMAN_TYPES.put("Combine_be4bddfc49afd72279b27140a41380e2", "配饰");
	}

	public static final Map<String, String> BABY_TYPES = new HashMap<String, String>();

	static {
		BABY_TYPES.put("Combine_42e8f8176560a56c06ba84805d537948", "男童");
		BABY_TYPES.put("Combine_ae49898c0a5aecd8b439889df47f89aa", "女童");
		BABY_TYPES.put("Combine_d3618203ef10a8bbbb0850f8639f2256", "女孩");
		BABY_TYPES.put("Combine_e89eb0528bbac9473821a4b7e316a81b", "婴儿");
		BABY_TYPES.put("Combine_f90168898918739612830d36251a255b", "男孩");
	}

}
