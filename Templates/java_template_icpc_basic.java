import java.util.*;
import java.io.*;

public class template_basico {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }
    static int  nextInt()  throws IOException { return Integer.parseInt(next()); }
    static long nextLong() throws IOException { return Long.parseLong(next()); }

    static void solve() throws IOException {
        int n = nextInt();
        long sum = 0;
        for (int i = 0; i < n; i++) sum += nextLong();

        // TODO: tu solución aquí
        sb.append(sum).append('\n');
    }

    public static void main(String[] args) throws IOException {
        int t = nextInt();
        while (t-- > 0) solve();
        System.out.print(sb);
    }
}
