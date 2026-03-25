import sys
import os
from collections import defaultdict, deque, Counter
from itertools import permutations, combinations, accumulate
from math import gcd, lcm, isqrt, inf, log2
from heapq import heappush, heappop

# ─── Lectura rápida ───────────────────────────────────────────────────────────
input = sys.stdin.readline

def ri():    return int(input())
def rl():    return list(map(int, input().split()))
def rs():    return input().strip()
def rls():   return input().split()

# ─── Constantes útiles ────────────────────────────────────────────────────────
MOD = 10**9 + 7
INF = float('inf')

# ─── Algoritmos frecuentes ────────────────────────────────────────────────────

def binary_search(lo, hi, check):
    """Búsqueda binaria genérica sobre enteros. check(mid) debe ser monotónica."""
    while lo < hi:
        mid = (lo + hi) // 2
        if check(mid):
            hi = mid
        else:
            lo = mid + 1
    return lo

def sieve(n):
    """Criba de Eratóstenes. Retorna lista de booleanos is_prime[0..n]."""
    is_prime = bytearray([1]) * (n + 1)
    is_prime[0] = is_prime[1] = 0
    for i in range(2, isqrt(n) + 1):
        if is_prime[i]:
            is_prime[i*i::i] = bytearray(len(is_prime[i*i::i]))
    return is_prime

def factores(n):
    """Factorización prima de n."""
    f = {}
    d = 2
    while d * d <= n:
        while n % d == 0:
            f[d] = f.get(d, 0) + 1
            n //= d
        d += 1
    if n > 1:
        f[n] = f.get(n, 0) + 1
    return f

class DSU:
    """Union-Find (Disjoint Set Union) con compresión de caminos y unión por rango."""
    def __init__(self, n):
        self.parent = list(range(n))
        self.rank   = [0] * n
        self.size   = [1] * n

    def find(self, x):
        while self.parent[x] != x:
            self.parent[x] = self.parent[self.parent[x]]
            x = self.parent[x]
        return x

    def union(self, a, b):
        a, b = self.find(a), self.find(b)
        if a == b: return False
        if self.rank[a] < self.rank[b]: a, b = b, a
        self.parent[b] = a
        self.size[a] += self.size[b]
        if self.rank[a] == self.rank[b]: self.rank[a] += 1
        return True

    def same(self, a, b): return self.find(a) == self.find(b)
    def sz(self, x):      return self.size[self.find(x)]

def bfs(grafo, inicio, n):
    """BFS estándar. Retorna distancias desde inicio (-1 si no alcanzable)."""
    dist = [-1] * n
    dist[inicio] = 0
    q = deque([inicio])
    while q:
        u = q.popleft()
        for v in grafo[u]:
            if dist[v] == -1:
                dist[v] = dist[u] + 1
                q.append(v)
    return dist

# ─── Solución ─────────────────────────────────────────────────────────────────

def solve():
    # Lee los datos del caso de prueba
    n = ri()
    a = rl()

    # TODO: implementar la solución aquí
    print(sum(a))

# ─── Main ─────────────────────────────────────────────────────────────────────

def main():
    sys.setrecursionlimit(300_000)
    t = ri()              # número de casos de prueba
    for _ in range(t):
        solve()

if __name__ == "__main__":
    main()
