import java.util.List;
import java.util.function.Predicate;

public class FilterForTitle<T> implements Predicate<T> {

	List<String> cards;

	public FilterForTitle(List<String> cards) {
		this.cards = cards;
	}

	public boolean test(T arg0) {
		if (cards.contains(((SpellCard) arg0).title))
			return true;
		return false;
	}

}
