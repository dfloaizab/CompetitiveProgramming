import java.util.*;
import java.io.*;

public class template_icpc {

    // ─── Lectura rápida ──────────────────────────────────────────────────────
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static String nextLine() throws IOException { return br.readLine(); }
    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }
    static int    nextInt()    throws IOException { return Integer.parseInt(next()); }
    static long   nextLong()   throws IOException { return Long.parseLong(next()); }
    static double nextDouble() throws IOException { return Double.parseDouble(next()); }

    // ─── Constantes útiles ───────────────────────────────────────────────────
    static final int  MOD = 1_000_000_007;
    static final long INF = Long.MAX_VALUE / 2;

    // ─── Algoritmos frecuentes ───────────────────────────────────────────────

    /** MCD usando el algoritmo de Euclides. */
    static long gcd(long a, long b) { return b == 0 ? a : gcd(b, a % b); }

    /** Exponenciación modular: base^exp % mod en O(log exp). */
    static long powMod(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }

    /** Criba de Eratóstenes. Retorna arreglo booleano isPrime[0..n]. */
    static boolean[] sieve(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int i = 2; (long) i * i <= n; i++)
            if (isPrime[i])
                for (int j = i * i; j <= n; j += i)
                    isPrime[j] = false;
        return isPrime;
    }

    /** Union-Find (DSU) con compresión de caminos y unión por rango. */
    static int[] parent, rank, size;

    static void initDSU(int n) {
        parent = new int[n]; rank = new int[n]; size = new int[n];
        for (int i = 0; i < n; i++) { parent[i] = i; size[i] = 1; }
    }

    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    static boolean union(int a, int b) {
        a = find(a); b = find(b);
        if (a == b) return false;
        if (rank[a] < rank[b]) { int t = a; a = b; b = t; }
        parent[b] = a; size[a] += size[b];
        if (rank[a] == rank[b]) rank[a]++;
        return true;
    }

    /** BFS estándar sobre grafo de listas de adyacencia. */
    static int[] bfs(List<List<Integer>> graph, int start, int n) {
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        dist[start] = 0;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : graph.get(u)) {
                if (dist[v] == -1) { dist[v] = dist[u] + 1; q.add(v); }
            }
        }
        return dist;
    }

    /** Dijkstra sobre grafo ponderado (sin pesos negativos). */
    static long[] dijkstra(List<long[]>[] graph, int start, int n) {
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[start] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(x -> x[0]));
        pq.add(new long[]{0, start});
        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0]; int u = (int) cur[1];
            if (d > dist[u]) continue;
            for (long[] edge : graph[u]) {
                int v = (int) edge[0]; long w = edge[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.add(new long[]{dist[v], v});
                }
            }
        }
        return dist;
    }

    // ─── Solución ────────────────────────────────────────────────────────────

    static void solve() throws IOException {
        int n = nextInt();
        long sum = 0;
        for (int i = 0; i < n; i++) sum += nextLong();

        // TODO: implementar la solución aquí
        sb.append(sum).append('\n');
    }

    // ─── Main ────────────────────────────────────────────────────────────────

    public static void main(String[] args) throws IOException {
        int t = nextInt();          // número de casos de prueba
        while (t-- > 0) solve();
        System.out.print(sb);       // salida en bloque (más rápido que println)
    }
}
