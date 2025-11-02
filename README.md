# Assignment 4 – Smart City / Smart Campus Scheduling
**Name:** Arstanbek Fariza  
**Group:** SE-2424

## Objective
The purpose of this project is to integrate two key algorithmic topics:
- Strongly Connected Components (SCC) and Topological Ordering
- Shortest & Longest Paths in Directed Acyclic Graphs (DAGs)

The algorithms are applied to a simulated Smart City / Smart Campus Scheduling problem,
where each node represents a city-service task, and edges represent task dependencies.

---

## Implemented Components
1. Strongly Connected Components (Tarjan’s Algorithm)

This module is responsible for detecting cyclic dependencies in directed graphs.
Using Tarjan’s depth-first search–based algorithm, it identifies groups of nodes that are mutually reachable, known as strongly connected components (SCCs).
These SCCs represent cycles in the dependency structure (e.g., when task A depends on B, and B again depends on A).
This step is essential for breaking down a complex cyclic graph into manageable acyclic parts.

2. Condensation Graph Construction

Once the SCCs are identified, the system builds a condensation graph, where each SCC is represented as a single vertex.
This condensation process effectively transforms any general directed graph into a Directed Acyclic Graph (DAG).
It reduces complexity by eliminating cycles and enabling the application of topological sorting and dynamic programming algorithms on the simplified structure.

3. Topological Sorting (Kahn’s Algorithm)

The topological sorting module computes a linear ordering of nodes in the DAG such that all directed edges go from earlier to later vertices.
This ensures that each task or component is processed only after all of its dependencies have been resolved.
Kahn’s algorithm was implemented for its clarity and efficiency in handling nodes with zero in-degree.
It also serves as the foundation for computing shortest and longest paths in the DAG.

4. Shortest Path in DAG

After topological ordering, the program applies dynamic programming relaxation over the DAG to compute the shortest paths from a given source node.
Since the DAG contains no cycles, this method runs efficiently in linear time (O(V + E)), updating distances as it progresses through the topological order.
This component models the optimal scheduling of city tasks — finding the minimum total time or cost to complete dependent operations.

5. Longest Path (Critical Path Analysis)

The longest path algorithm identifies the critical path, which represents the sequence of tasks that determine the overall project duration.
Using the same topological order but reversing the relaxation condition, it finds the maximum cumulative weight of edges.
The result helps to determine which tasks cannot be delayed without affecting the overall schedule, a concept widely used in project management and smart infrastructure planning.

---

## Data Generation

All datasets were automatically generated using the included `GraphGenerator` class  
and stored under the `/data` directory.

| Category | Files | Nodes (n) | Description |
|-----------|--------|-----------|-------------|
| **Small** | `small_1.json` – `small_3.json` | 6–10 | Simple cases, 1–2 cycles |
| **Medium** | `medium_1.json` – `medium_3.json` | 10–20 | Mixed structures, multiple SCCs |
| **Large** | `large_1.json` – `large_3.json` | 20–50 | Performance and timing tests |

---

## Performance Summary
![img.png](img.png)

## Analysis

- **SCC performance** is linear and scales smoothly with graph size.
- **Topological Sort** shows minimal time increase with larger graphs.
- **Shortest & Longest Paths** in DAGs are highly efficient due to topological relaxation.
- **Dense graphs** have more edges but minimal impact on runtime.
- **Condensation graphs** significantly reduce complexity for cyclic dependencies.

## Visualization

Figure 1 – Execution Time per Dataset shows that both shortest and longest path computations perform efficiently even for large graphs.

![img_1.png](img_1.png)

Figure 2 – Performance vs Graph Size demonstrates a near-linear time growth, confirming O(V+E) complexity.
![img_2.png](img_2.png)

---

## Conclusions
This project successfully integrates three fundamental graph algorithms — Tarjan’s SCC, Topological Sort, and Shortest/Longest Path in DAGs — into a single cohesive system simulating smart city scheduling.

Through this implementation:

- Tarjan’s algorithm efficiently detects cyclic dependencies in city-service tasks, preventing circular scheduling errors.
- Condensation graph construction transforms complex cyclic systems into simplified acyclic ones, enabling efficient task planning.
- Topological sorting provides a valid execution order of tasks, ensuring all dependencies are respected.
- Shortest path analysis identifies the optimal, most time-efficient way to complete dependent city operations.
- Longest path analysis (critical path) determines which sequences of tasks directly impact overall project completion time.

Performance testing on small, medium, and large datasets confirmed that all algorithms scale efficiently with graph size.
Even in dense graphs, computation times remained within milliseconds due to the linear-time complexity of the implemented algorithms.
Overall, this project demonstrates how theoretical graph algorithms can be effectively applied to real-world scheduling and optimization problems within Smart City and Smart Campus systems.
