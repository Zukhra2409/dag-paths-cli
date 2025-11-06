package org;
import java.util.*;
public class DagLongest {
    public static class Res { public long[] dist; public int[] parent; }
    public static Res run(int n, java.util.List<int[]> edges, int s){
        int[] order = DagUtils.topo(n, edges);
        var A = DagUtils.buildAdj(n, edges);
        long NEG = - (long)1e18;
        long[] dist = new long[n]; int[] parent=new int[n]; Arrays.fill(dist, NEG); Arrays.fill(parent,-1);
        dist[s]=0;
        for(int u: order){
            if(dist[u]==NEG) continue;
            for(var vw: A.g[u]){
                int v=vw[0], w=vw[1];
                long cand = dist[u]+w;
                if(cand>dist[v]){ dist[v]=cand; parent[v]=u; }
            }
        }
        Res r=new Res(); r.dist=dist; r.parent=parent; return r;
    }
}