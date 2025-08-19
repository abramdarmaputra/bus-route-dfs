import java.util.*;

public class BusRoutes {
    private List<List<Integer>> routes;
    private int source, destination;
    private int answer = Integer.MAX_VALUE;

    public BusRoutes(List<List<Integer>> routes, int source, int destination) {
        this.routes = routes;
        this.source = source;
        this.destination = destination;
    }

    public int minBusesViaDFS() {
        // cari bus yg mengandung source & destination
        List<Integer> startBuses = new ArrayList<>();
        Set<Integer> targetBuses = new HashSet<>();

        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).contains(source)) startBuses.add(i);
            if (routes.get(i).contains(destination)) targetBuses.add(i);
        }

        if (startBuses.isEmpty() || targetBuses.isEmpty()) {
            return -1;
        }

        // graph antar bus (jika ada stop yang sama)
        int n = routes.size();
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) graph.put(i, new ArrayList<>());

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Set<Integer> setI = new HashSet<>(routes.get(i));
                for (int stop : routes.get(j)) {
                    if (setI.contains(stop)) {
                        graph.get(i).add(j);
                        graph.get(j).add(i);
                        break;
                    }
                }
            }
        }

        Set<Integer> visited = new HashSet<>();

        // DFS recursive
        class DFS {
            void run(int busIndex, int count) {
                if (targetBuses.contains(busIndex)) {
                    answer = Math.min(answer, count);
                    return;
                }
                visited.add(busIndex);
                for (int neighbor : graph.get(busIndex)) {
                    if (!visited.contains(neighbor)) {
                        run(neighbor, count + 1);
                    }
                }
                visited.remove(busIndex); // backtrack
            }
        }

        DFS dfs = new DFS();
        for (int sb : startBuses) {
            dfs.run(sb, 1);
        }

        return (answer == Integer.MAX_VALUE) ? -1 : answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input contoh:
        // 3 (jumlah rute)
        // 3 (jumlah stop dalam rute pertama)
        // 1 5 7
        // 3 (jumlah stop dalam rute kedua)
        // 3 8 10
        // 3 (jumlah stop dalam rute ketiga)
        // 2 7 15
        // Source: 1
        // Destination: 15
        System.out.print("Masukkan jumlah rute: ");
        int r = sc.nextInt();

        List<List<Integer>> routes = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            System.out.print("Masukkan jumlah stop pada rute ke-" + (i+1) + ": ");
            int stops = sc.nextInt();
            List<Integer> route = new ArrayList<>();
            System.out.print("Masukkan stop: ");
            for (int j = 0; j < stops; j++) {
                route.add(sc.nextInt());
            }
            routes.add(route);
        }

        System.out.print("Masukkan source: ");
        int source = sc.nextInt();
        System.out.print("Masukkan destination: ");
        int destination = sc.nextInt();

        BusRoutes program = new BusRoutes(routes, source, destination);
        int result = program.minBusesViaDFS();
        System.out.println("Minimal bus yang dibutuhkan = " + result);

        sc.close();
    }
}
