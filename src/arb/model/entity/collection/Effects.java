package arb.model.entity.collection;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import arb.model.entity.Effect;

/**
 * This class corresponds to a set of all effects possible in-game. Note that
 * this is more or less a singleton, as there is no need to create multiple
 * instances of this class. To obtain the instance, see
 * ModelRegistry::getEffects.
 */
public class Effects {

	private Map<String, Effect> effectsByName;

	public Effects(Set<Effect> effects) {
		this.effectsByName = effects.stream().collect(Collectors.toMap(Effect::getName, e -> e));
	}

	public Effect getEffectByName(String name) {
		return this.effectsByName.get(name);
	}

	public Set<Effect> getAllEffects() {
		return this.effectsByName.values().stream().collect(Collectors.toSet());
	}

	public Set<String> getAllEffectNames() {
		return this.effectsByName.keySet();
	}
}
