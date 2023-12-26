import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {
	private static final int SIZE = 500;
	private static final int NODES = SIZE * SIZE * SIZE;
	private static final int RUNS = 5;

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		List<List<Integer>> nodes = new ArrayList<>(NODES);
		for (int i = 0; i < NODES; i++) {
			nodes.add(new ArrayList<>());
		}
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				for (int k = 0; k < SIZE; k++) {
					int[] currentDimensions = new int[]{i, j, k};
					int u = flatCoordinates(currentDimensions);
					for (int l = 0; l < currentDimensions.length; l++) {
						if (currentDimensions[l] + 1 < SIZE) {
							currentDimensions[l]++;
							int v = flatCoordinates(currentDimensions);
							nodes.get(u).add(v);
							nodes.get(v).add(u);
							currentDimensions[l]--;
						}
					}
				}
			}
		}
		for (int i = 0; i < RUNS; i++) {
			System.out.printf("===== RUN #%d =====\n", i + 1);

			SequentialBFS sequentialBFS = new SequentialBFS();
			long beforeSequential = System.currentTimeMillis();
			List<Integer> sequentialResult = sequentialBFS.bfs(nodes);
			long afterSequential = System.currentTimeMillis();
			System.out.printf("Sequential time %d ms\n", afterSequential - beforeSequential);


			ParallelBFS parallelBFS = new ParallelBFS();
			long beforeParallel = System.currentTimeMillis();
			List<Integer> parallelResult = parallelBFS.bfs(nodes);
			long afterParallel = System.currentTimeMillis();
			System.out.printf("Parallel time %d ms\n", afterParallel - beforeParallel);

			checkResult(sequentialResult, parallelResult);
		}
	}

	private static void checkResult(List<Integer> sequentialResult, List<Integer> parallelResult) {
		if (sequentialResult.size() != parallelResult.size()) {
			System.err.println("ERROR Results of different sizes");
			return;
		}
		for (int i = 0; i < sequentialResult.size(); i++) {
			if (!sequentialResult.get(i).equals(parallelResult.get(i))) {
				System.err.printf("ERROR Results not equal on index %d sequential=%d parallel=%d\n",
						i, sequentialResult.get(i), parallelResult.get(i));
				return;
			}
		}
		System.out.println("Results are equal");
	}

	private static int flatCoordinates(int[] dimensions) {
		int result = 0;
		int elementsInDimension = 1;
		for (int i = 0; i < dimensions.length; i++) {
			int realIndex = dimensions.length - i - 1;
			result += elementsInDimension * dimensions[realIndex];
			elementsInDimension *= SIZE;
		}
		return result;
	}
}