#include <stdio.h>

#define MAXN 200005
typedef long long ll;

void solve() {
    int n;
    scanf("%d", &n);

    ll sum = 0;
    for (int i = 0; i < n; i++) {
        ll x; scanf("%lld", &x);
        sum += x;
    }

    /* TODO: tu solución aquí */
    printf("%lld\n", sum);
}

int main() {
    int t;
    scanf("%d", &t);
    while (t--) solve();
    return 0;
}
