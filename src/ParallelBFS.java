import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.IntStream;

public class ParallelBFS {
	public List<Integer> bfs(List<List<Integer>> nodes) throws InterruptedException, ExecutionException {
		int start = 0;
		final int[][] frontier = {new int[]{start}};

		int[] tmp = new int[nodes.size()];
		Arrays.fill(tmp, -1);
		AtomicIntegerArray isAlreadyDone = new AtomicIntegerArray(tmp);
		isAlreadyDone.set(0, 1);

		List<Integer> result = new ArrayList<>(Collections.nCopies(nodes.size(), -1));
		result.set(start, 0);

		ForkJoinPool forkJoinPool = new ForkJoinPool(4);

		AtomicBoolean loopBreaker = new AtomicBoolean(false);
		forkJoinPool.submit(() -> {
			while (!loopBreaker.get()) {
				loopBreaker.set(true);
				int[] sizes = Arrays.stream(frontier[0]).parallel()
						.map(node -> nodes.get(node).size())
						.toArray();
				Arrays.parallelPrefix(sizes, Integer::sum);
				int newSize = sizes[sizes.length - 1];
				int[] newFrontier = new int[newSize];
				IntStream.range(0, frontier[0].length).parallel().forEach(index -> {
					int current = frontier[0][index];
					List<Integer> connected = nodes.get(current);
					for (int i = 0; i < connected.size(); i++) {
						Integer node = connected.get(i);
						if (isAlreadyDone.compareAndSet(node, -1, 1)) {
							loopBreaker.set(false);
							if (index == 0) {
								newFrontier[i] = node;
							} else {
								newFrontier[sizes[index - 1] + i] = node;
							}
							result.set(node, result.get(current) + 1);
						}
					}
				});
				frontier[0] = Arrays.stream(newFrontier).parallel().filter(x -> x > 0).toArray();
			}
		}).get();
		return result;
	}
}
