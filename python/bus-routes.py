class BusRoutes:
    def __init__(self, routes, source, destination):
        self.routes = routes
        self.source = source
        self.destination = destination

    def min_buses_via_dfs(self):
        # Cari bus mana saja yang mengandung source dan destination
        start_buses = [i for i, r in enumerate(self.routes) if self.source in r]
        target_buses = {i for i, r in enumerate(self.routes) if self.destination in r}

        if not start_buses or not target_buses:
            return -1  # Tidak ada bus yg layak

        # Graph: bus -> bus lain kalau ada stop yg sama
        n = len(self.routes)
        graph = {i: [] for i in range(n)}

        for i in range(n):
            for j in range(i + 1, n):
                if set(self.routes[i]) & set(self.routes[j]):  # ada irisan stop
                    graph[i].append(j)
                    graph[j].append(i)

        visited = set()
        self.answer = float('inf')

        def dfs(bus_index, count):
            # Kalau sudah mencapai salah satu bus target
            if bus_index in target_buses:
                self.answer = min(self.answer, count)
                return

            visited.add(bus_index)
            for neighbor in graph[bus_index]:
                if neighbor not in visited:
                    dfs(neighbor, count + 1)
            visited.remove(bus_index)  # backtrack

        for sb in start_buses:
            dfs(sb, 1)

        return self.answer if self.answer != float('inf') else -1


# Contoh penggunaan
routes = [
    [1, 5, 7],
    [3, 8, 10],
    [2, 7, 15]
]
program = BusRoutes(routes, 1, 15)
print(program.min_buses_via_dfs())  # Output = 2 (naik bus[0] lalu pindah ke bus[2])
