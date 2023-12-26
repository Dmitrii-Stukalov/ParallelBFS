# Parallel BFS
Implementation of parallel BFS and comparison with sequential realization

## Run
To default run shell script is available:
```shell
./run.sh
```

## Results

| Algorithm  | Run 1   | Run 2   | Run 3   | Run 4   | Run 5   | Avg     |
|------------|---------|---------|---------|---------|---------|---------|
| Parallel   | 26403ms | 27192ms | 23601ms | 23614ms | 24531ms | 25068ms |
| Sequential | 76545ms | 75816ms | 77238ms | 82046ms | 76711ms | 77671ms |

### Acceleration coefficient:
* Sequential / Parallel ~ 3.1