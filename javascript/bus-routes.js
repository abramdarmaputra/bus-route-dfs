class BusRoutes {
  constructor(routes, source, destination) {
    this.routes = routes;
    this.source = source;
    this.destination = destination;
    this.answer = Infinity;
  }

  minBusesViaDFS() {
    const startBuses = [];
    const targetBuses = new Set();

    // cari bus yang punya source & destination
    this.routes.forEach((r, i) => {
      if (r.includes(this.source)) startBuses.push(i);
      if (r.includes(this.destination)) targetBuses.add(i);
    });

    if (startBuses.length === 0 || targetBuses.size === 0) {
      return -1; // tidak ada bus yang valid
    }

    // build graph antar bus (jika punya stop sama)
    const n = this.routes.length;
    const graph = Array.from({ length: n }, () => []);

    for (let i = 0; i < n; i++) {
      for (let j = i + 1; j < n; j++) {
        const setI = new Set(this.routes[i]);
        if (this.routes[j].some(stop => setI.has(stop))) {
          graph[i].push(j);
          graph[j].push(i);
        }
      }
    }

    const visited = new Set();

    const dfs = (busIndex, count) => {
      if (targetBuses.has(busIndex)) {
        this.answer = Math.min(this.answer, count);
        return;
      }
      visited.add(busIndex);

      for (const neighbor of graph[busIndex]) {
        if (!visited.has(neighbor)) {
          dfs(neighbor, count + 1);
        }
      }

      visited.delete(busIndex); // backtrack
    };

    for (const sb of startBuses) {
      dfs(sb, 1);
    }

    return this.answer === Infinity ? -1 : this.answer;
  }
}

// Contoh penggunaan
const routes = [
  [1, 5, 7],
  [3, 8, 10],
  [2, 7, 15]
];
const program = new BusRoutes(routes, 1, 15);
console.log(program.minBusesViaDFS()); // Output = 2
