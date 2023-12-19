import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SequentialBFS {
	public List<Integer> bfs(List<List<Integer>> nodes) {
		int start = 0;
		List<Integer> result = new ArrayList<>(Collections.nCopies(nodes.size(), -1));
		result.set(start, 0);

		Queue<Integer> queue = new LinkedList<>();
		queue.add(0);
		while (!queue.isEmpty()) {
			Integer current = queue.poll();
			List<Integer> connected = nodes.get(current);
			for (Integer node : connected) {
				if (result.get(node) == -1) {
					result.set(node, result.get(current) + 1);
					queue.add(node);
				}
			}
		}
		return result;
	}
}
