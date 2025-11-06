package org;

import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Map<String,String> a = parse(args);
        String mode   = a.get("mode");
        String in     = a.get("in");
        int    source = Integer.parseInt(a.getOrDefault("source","0"));
        String out    = a.get("out");
        Integer target = a.containsKey("target") ? Integer.parseInt(a.get("target")) : null;

        GraphLoader.G g = GraphLoader.load(in);
        if (!g.directed) throw new IllegalArgumentException("Ожидается ориентированный граф.");

        List<int[]> E = new ArrayList<>();
        for (var e : g.edges) E.add(new int[]{ e.u, e.v, e.w==null?1:e.w });

        if ("topo".equals(mode)) {
            int[] ord = DagUtils.topo(g.n, E);
            System.out.println("Topo: " + Arrays.toString(ord));
            return;
        }

        if ("dag-sssp".equals(mode)) {
            DagShortest.Res r = DagShortest.run(g.n, E, source);
            printAndMaybeCsv(r.dist, r.parent, out);
            if (target != null) System.out.println("path: " + path(r.parent, source, target));
            return;
        }

        if ("dag-longest".equals(mode)) {
            DagLongest.Res r = DagLongest.run(g.n, E, source);
            printAndMaybeCsv(r.dist, r.parent, out);
            if (target != null) System.out.println("path: " + path(r.parent, source, target));
            return;
        }

        System.out.println("--mode topo|dag-sssp|dag-longest --in file.json [--source N] [--target N] [--out file.csv]");
    }

    static void printAndMaybeCsv(long[] dist, int[] parent, String out) throws Exception {
        System.out.println("node,dist,parent");
        List<String[]> rows = new ArrayList<>();
        for (int v = 0; v < dist.length; v++) {
            System.out.printf("%d,%s,%d%n", v, dist[v], parent[v]);
            rows.add(new String[]{ String.valueOf(v), String.valueOf(dist[v]), String.valueOf(parent[v]) });
        }
        if (out != null) {
            java.io.File f = new java.io.File(out);
            if (f.getParentFile() != null) f.getParentFile().mkdirs(); // создадим results/
            try (PrintWriter pw = new PrintWriter(f)) {
                pw.println("node,dist,parent");
                for (var r : rows) pw.println(String.join(",", r));
            }
        }
    }

    static List<Integer> path(int[] parent, int s, int t) {
        ArrayList<Integer> p = new ArrayList<>();
        if (t != s && parent[t] == -1) return null;
        for (int v = t; v != -1; v = parent[v]) { p.add(v); if (v == s) break; }
        if (p.get(p.size()-1) != s) return null;
        Collections.reverse(p);
        return p;
    }

    static Map<String,String> parse(String[] a) {
        Map<String,String> m = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (a[i].startsWith("--")) {
                String k = a[i].substring(2);
                String v = (i+1 < a.length && !a[i+1].startsWith("--")) ? a[++i] : "true";
                m.put(k, v);
            }
        }
        return m;
    }
}