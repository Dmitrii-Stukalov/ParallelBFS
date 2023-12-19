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
| Parallel   | 35622ms | 34824ms | 36621ms | 35698ms | 33684ms | 35298ms |
| Sequential | 78369ms | 76994ms | 77399ms | 80311ms | 76853ms | 78213ms |

### Acceleration coefficient:
* Sequential / Parallel ~ 2.2