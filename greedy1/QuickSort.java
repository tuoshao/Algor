package greedy1;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class QuickSort{

	public static <E extends Comparable<? super E>> List<E>[] split(List<E> v) {
            List<E>[] results = (List<E>[])new List[] { new ArrayList<E>(), new ArrayList<E>(), new ArrayList<E>() };
            Iterator<E> it = v.iterator();
            E pivot = it.next();
            results[1].add(pivot);
            while (it.hasNext()) {
                    E x = it.next();
                    if (x.compareTo(pivot) > 0) results[0].add(x);
                    else if (x.compareTo(pivot) == 0) results[1].add(x);
                    else results[2].add(x);
            }
            return results;
    }

    public static <E extends Comparable<? super E>> List<E> quicksort(List<E> v) {
            if (v == null || v.size() <= 1) return v;
            final List<E> result = new ArrayList<E>();
            final List<E>[] lists = split(v);
            result.addAll(quicksort(lists[0]));
            result.addAll(lists[1]);
            result.addAll(quicksort(lists[2]));
            return result;
    }
}


