package org;
import java.util.*;
public class DagShortest {
    public static class Res { public long[] dist; public int[] parent; }
    public static Res run(int n, java.util.List<int[]> edges, int s){
        var norm = DagUtils.normalize(edges.stream().map(e->{
            GraphLoader.Edge x=new GraphLoader.Edge(); x.u=e[0]; x.v=e[1]; x.w=e[2]; return x;
        }).toList());
        norm = edges;
        int[] order = DagUtils.topo(n, edges);
        var A = DagUtils.buildAdj(n, edges);
        long INF = Long.MAX_VALUE/4;
        long[] dist = new long[n]; int[] parent=new int[n]; Arrays.fill(dist, INF); Arrays.fill(parent,-1);
        dist[s]=0;
        for(int u: order){
            if(dist[u]==INF) continue;
            for(var vw: A.g[u]){
                int v=vw[0], w=vw[1];
                long cand = dist[u]+w;
                if(cand<dist[v]){ dist[v]=cand; parent[v]=u; }
            }
        }
        Res r=new Res(); r.dist=dist; r.parent=parent; return r;
    }
}