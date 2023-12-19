import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ParallelBFS {
	public List<Integer> bfs(List<List<Integer>> nodes) throws InterruptedException, ExecutionException {
		int start = 0;
		final List<Integer>[] frontier = new List[]{List.of(start)};

		int[] tmp = new int[nodes.size()];
		Arrays.fill(tmp, -1);
		AtomicIntegerArray isAlreadyDone = new AtomicIntegerArray(tmp);

		List<Integer> result = new ArrayList<>(Collections.nCopies(nodes.size(), -1));
		result.set(start, 0);

		ForkJoinPool forkJoinPool = new ForkJoinPool(4);

		while (!frontier[0].isEmpty()) {
			List<Integer> newFrontier = new CopyOnWriteArrayList<>();
			forkJoinPool.submit(() -> frontier[0].parallelStream().forEach(current -> {
				List<Integer> connected = nodes.get(current);
				for (Integer node : connected) {
					if (isAlreadyDone.compareAndSet(node, -1, result.get(current) + 1)) {
						newFrontier.add(node);
					}
				}
			})).get();
			frontier[0] = newFrontier;
		}
		return result;
	}
}
