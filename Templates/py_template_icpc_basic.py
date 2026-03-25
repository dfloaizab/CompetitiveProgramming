import sys
input = sys.stdin.readline

def solve():
    n = int(input())
    a = list(map(int, input().split()))

    # TODO: tu solución aquí
    print(sum(a))

t = int(input())
for _ in range(t):
    solve()
