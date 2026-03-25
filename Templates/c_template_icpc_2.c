#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <limits.h>
#include <stdbool.h>

/* ─── Constantes útiles ────────────────────────────────────────────────────── */
#define MOD     1000000007LL
#define INF     0x3f3f3f3f3f3f3f3fLL
#define MAXN    200005
#define MAX(a,b) ((a)>(b)?(a):(b))
#define MIN(a,b) ((a)<(b)?(a):(b))
#define SWAP(a,b) do { __typeof__(a) _t=(a);(a)=(b);(b)=_t; } while(0)

typedef long long ll;
typedef unsigned long long ull;

/* ─── Algoritmos frecuentes ────────────────────────────────────────────────── */

/** MCD con el algoritmo de Euclides. */
ll gcd(ll a, ll b) { return b ? gcd(b, a % b) : a; }

/** Exponenciación modular: base^exp % mod en O(log exp). */
ll pow_mod(ll base, ll exp, ll mod) {
    ll result = 1;
    base %= mod;
    for (; exp > 0; exp >>= 1) {
        if (exp & 1) result = result * base % mod;
        base = base * base % mod;
    }
    return result;
}

/** Criba de Eratóstenes. is_prime[i] = 1 si i es primo (0 y 1 son false). */
bool is_prime[MAXN];
void sieve(int n) {
    memset(is_prime, 1, sizeof(bool) * (n + 1));
    is_prime[0] = is_prime[1] = false;
    for (int i = 2; (ll)i * i <= n; i++)
        if (is_prime[i])
            for (int j = i * i; j <= n; j += i)
                is_prime[j] = false;
}

/* ── Union-Find (DSU) con compresión de caminos y unión por rango ─────────── */
int par[MAXN], rnk[MAXN], sz[MAXN];

void init_dsu(int n) {
    for (int i = 0; i < n; i++) { par[i] = i; rnk[i] = 0; sz[i] = 1; }
}

int find(int x) {
    if (par[x] != x) par[x] = find(par[x]);
    return par[x];
}

bool unite(int a, int b) {
    a = find(a); b = find(b);
    if (a == b) return false;
    if (rnk[a] < rnk[b]) SWAP(a, b);
    par[b] = a; sz[a] += sz[b];
    if (rnk[a] == rnk[b]) rnk[a]++;
    return true;
}

/* ── BFS sobre grafo de listas de adyacencia ─────────────────────────────── */
/* Representación: lista de adyacencia con arreglos estáticos (CSR ligero).    */
int head[MAXN], nxt[MAXN * 2], to[MAXN * 2], edge_cnt;

void init_graph(int n) {
    memset(head, -1, sizeof(int) * (n + 1));
    edge_cnt = 0;
}

void add_edge(int u, int v) {
    to[edge_cnt] = v; nxt[edge_cnt] = head[u]; head[u] = edge_cnt++;
    to[edge_cnt] = u; nxt[edge_cnt] = head[v]; head[v] = edge_cnt++; /* quitar para dirigido */
}

int dist_bfs[MAXN];
int queue_buf[MAXN];

void bfs(int start, int n) {
    for (int i = 0; i < n; i++) dist_bfs[i] = -1;
    int front = 0, back = 0;
    dist_bfs[start] = 0;
    queue_buf[back++] = start;
    while (front < back) {
        int u = queue_buf[front++];
        for (int e = head[u]; e != -1; e = nxt[e]) {
            int v = to[e];
            if (dist_bfs[v] == -1) {
                dist_bfs[v] = dist_bfs[u] + 1;
                queue_buf[back++] = v;
            }
        }
    }
}

/* ─── Solución ──────────────────────────────────────────────────────────────── */

void solve() {
    int n;
    scanf("%d", &n);

    ll sum = 0;
    for (int i = 0; i < n; i++) {
        ll x; scanf("%lld", &x);
        sum += x;
    }

    /* TODO: implementar la solución aquí */
    printf("%lld\n", sum);
}

/* ─── Main ──────────────────────────────────────────────────────────────────── */

int main() {
    int t;
    scanf("%d", &t);           /* número de casos de prueba */
    while (t--) solve();
    return 0;
}
