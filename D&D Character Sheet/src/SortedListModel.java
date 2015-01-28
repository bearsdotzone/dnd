import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

class SortedListModel extends AbstractListModel {
	SortedSet<String> model;

	public SortedListModel() {
		model = new TreeSet<String>();
	}

	public int getSize() {
		return model.size();
	}

	public String getElementAt(int index) {
		String[] temp = model.toArray(new String[0]);
		return temp[index];
	}

	public void addElement(String element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}

	public void addAll(String elements[]) {
		Collection<String> c = Arrays.asList(elements);
		model.addAll(c);
		fireContentsChanged(this, 0, getSize());
	}

	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}

	public boolean contains(String element) {
		return model.contains(element);
	}

	public String firstElement() {
		return model.first();
	}

	public Iterator iterator() {
		return model.iterator();
	}

	public String lastElement() {
		return model.last();
	}

	public boolean removeElement(String element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;
	}

	public boolean removeAllElements() {
		model = new TreeSet<String>();
		return true;
	}
}