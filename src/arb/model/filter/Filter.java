package arb.model.filter;

import java.util.Set;

import arb.model.entity.Potion;

/** This interface provides a portrait of what all other filters must do. */
public interface Filter {

	public Set<Potion> applyFilter(Set<Potion> unfilteredList);
	
}
