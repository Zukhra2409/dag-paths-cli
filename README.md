# DAG Paths CLI — Participant 2

Инструмент для работы с **DAG**:
- `topo` — топологическая сортировка (проверка, что граф — DAG)
- `dag-sssp` — кратчайшие пути в DAG (из одной вершины)
- `dag-longest` — длиннейший путь в DAG (критический)

## Как запускать в IntelliJ (Run → Program arguments)
```text
--mode topo --in dag_small.json
--mode dag-sssp --in dag_small.json --source 0 --target 4 --out results/sssp_small.csv
--mode dag-longest --in dag_small.json --source 0 --target 4 --out results/longest_small.csv

--mode topo --in tasks_dag.json
--mode dag-sssp --in tasks_dag.json --source 2 --target 5 --out results/sssp_tasks.csv
--mode dag-longest --in tasks_dag.json --source 2 --target 5 --out results/longest_tasks.csv