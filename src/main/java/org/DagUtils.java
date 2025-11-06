package org;
import java.util.*;

public class DagUtils {
    public static class Adj { public List<int[]>[] g; public int[] indeg; }

    public static List<int[]> normalize(List<GraphLoader.Edge> es){
        List<int[]> out = new ArrayList<>();
        for (var e: es) out.add(new int[]{e.u, e.v, e.w==null?1:e.w});
        return out;
    }
    public static Adj buildAdj(int n, List<int[]> e){
        List<int[]>[] g = new ArrayList[n]; int[] indeg = new int[n];
        for (int i=0;i<n;i++) g[i]=new ArrayList<>();
        for (var a: e){ g[a[0]].add(new int[]{a[1], a[2]}); indeg[a[1]]++; }
        Adj A = new Adj(); A.g=g; A.indeg=indeg; return A;
    }
    public static int[] topo(int n, List<int[]> e){
        Adj A = buildAdj(n, e);
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for(int i=0;i<n;i++) if (A.indeg[i]==0) q.add(i);
        int[] ord = new int[n]; int k=0;
        while(!q.isEmpty()){
            int u=q.remove(); ord[k++]=u;
            for(var vw: A.g[u]) if(--A.indeg[vw[0]]==0) q.add(vw[0]);
        }
        if (k!=n) throw new IllegalStateException("Это не DAG (есть цикл). Сначала сделайте конденсацию.");
        return ord;
    }
}