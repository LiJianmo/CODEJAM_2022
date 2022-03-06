package trip_planner.util;


import java.util.*;

import trip_planner.entity.Load;
import trip_planner.entity.Vertex;

import java.time.LocalDateTime;

public class TripPlanner {
    private final List<Load> allAvailableLoads = new ArrayList<>();

    public TripPlanner(List<Load> loads) {
        for (Load l : loads){
            allAvailableLoads.add(new Load(l));
        }
    }

    public static LocalDateTime plusTime(LocalDateTime dateTime, double hours){
        long h = (long) Math.floor(hours);
        long m = (long) Math.floor((hours - h) * 60);
        long s = (long) Math.floor(((hours - h) * 60 - m) * 60);
        LocalDateTime newDateTime;
        newDateTime= dateTime.plusHours(h);
        newDateTime = newDateTime.plusMinutes(m);
        newDateTime = newDateTime.plusSeconds(s);
        return newDateTime;
    }

    public Integer[] plan(Vertex start, LocalDateTime startTime, LocalDateTime maxDestTime){
        // find loads that are within the queried time window
        List<Load> loadsToConsider = new ArrayList<>();
        System.out.println("--Find available loads--");
        long time = System.currentTimeMillis();
        for (Load l : allAvailableLoads){
            if (plusTime(startTime, Vertex.hours(l.start, start)).compareTo(l.pickupTime) <= 0
            && maxDestTime.compareTo(l.arriveTime) >= 0)
                loadsToConsider.add(l);
        }
        // sort the loads by finishing time in ascending order
        loadsToConsider.sort((o1, o2) -> o1.arriveTime.compareTo(o2.arriveTime));

        System.out.println("Available loads: " + loadsToConsider.size());
        System.out.println("time taken: " + (System.currentTimeMillis() - time));
        // find the last load of the optimal trip
        Load curLoad = find_optimal(loadsToConsider, start, startTime, maxDestTime);
        if (curLoad == null) return new Integer[0];
        // get the entire trip by backtracking to root.
        List<Integer> loadList = new ArrayList<>();
        loadList.add(curLoad.loadID);
        while (curLoad.lastLoad != null){
            loadList.add(curLoad.lastLoad.loadID);
            curLoad = curLoad.lastLoad;
        }
        Integer[] loadArray = new Integer[loadList.size()];
        for (int i=0; i<loadArray.length; i++){
            loadArray[i] = loadList.get(loadList.size()-1-i);
        }
        return loadArray;
    }

    public Load __find_optimal(List<Load> loads, Vertex start, LocalDateTime startTime, LocalDateTime destTime){
        if (loads.size() == 0) return null;
        /*
        computer p(i). p(i) = index of load with latest (arrival time + time to the vertex of this load)
         */
        int[] p = new int[loads.size()];
        p[0] = -1;
        for (int i=1; i<p.length; i++){
            int lastCompatible = -1;
            //LocalDateTime latestArrivalTime = null;
            for (int j=i-1; j>=0; j--){
                LocalDateTime arrivalTime = plusTime(loads.get(j).arriveTime, Vertex.hours(loads.get(i).start, loads.get(j).destination));
                if (arrivalTime.compareTo(loads.get(i).pickupTime) <= 0){
                    lastCompatible = j;
                    break;
                }
            }
            p[i] = lastCompatible;
        }

        double[] opt = new double[loads.size()+1];
        Load[] lastLoadArray = new Load[loads.size()+1];
        opt[0] = 0;
        lastLoadArray[0] = null;
        for (int i=0; i<loads.size(); i++){
            Load curLoad = loads.get(i);
            double curNetProfit = curLoad.profit;
            if(p[i] == -1 || lastLoadArray[p[i]+1] == null){
                curNetProfit -= Vertex.fuelCost(curLoad.start, start);
            } else{
                curNetProfit -= Vertex.fuelCost(curLoad.start, lastLoadArray[p[i]+1].destination);
                curNetProfit += opt[p[i]+1];
            }
            curNetProfit -= Vertex.fuelCost(curLoad.start, curLoad.destination);

            if (curNetProfit > opt[i]){
                opt[i+1] = curNetProfit;
                lastLoadArray[i+1] = curLoad;
                curLoad.lastLoad = lastLoadArray[p[i]+1];
            }
            else{
                opt[i+1] = opt[i];
                lastLoadArray[i+1] = lastLoadArray[i];
            }
        }

        return lastLoadArray[lastLoadArray.length-1];
    }

    public Load find_optimal(List<Load> loads, Vertex start, LocalDateTime startTime, LocalDateTime destTime){
        long time = System.currentTimeMillis();
        System.out.println("Trip Finding Started");
        System.out.println("time taken:" + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        System.out.println("--Fill list P_i--");
        if (loads.size() == 0) return null;
        /*
        computer p(i). p(i) = index of load with latest (arrival time + time to the vertex of this load)
         */
        int[] p = new int[loads.size()];
        p[0] = -1;
        for (int i=1; i<p.length; i++){
            int lastCompatible = -1;
            //LocalDateTime latestArrivalTime = null;
            for (int j=i-1; j>=0; j--){
                LocalDateTime arrivalTime = plusTime(loads.get(j).arriveTime, Vertex.hours(loads.get(i).start, loads.get(j).destination));
                if (arrivalTime.compareTo(loads.get(i).pickupTime) <= 0){
                    lastCompatible = j;
                    break;
                }
            }
            p[i] = lastCompatible;
        }

        System.out.println("time taken:" + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        System.out.println("--Fill lists Opt_j and LL_j--");
        double[] opt = new double[loads.size()+1];
        Load[] lastLoadArray = new Load[loads.size()+1];
        opt[0] = 0;
        lastLoadArray[0] = null;
        for (int i=0; i<loads.size(); i++){
            Load curLoad = loads.get(i);
            double curNetProfit = curLoad.profit;
            if(p[i] == -1 || lastLoadArray[p[i]+1] == null){
                curNetProfit -= Vertex.fuelCost(curLoad.start, start);
            } else{
                curNetProfit -= Vertex.fuelCost(curLoad.start, lastLoadArray[p[i]+1].destination);
                curNetProfit += opt[p[i]+1];
            }
            curNetProfit -= Vertex.fuelCost(curLoad.start, curLoad.destination);

            if (curNetProfit > opt[i]){
                opt[i+1] = curNetProfit;
                lastLoadArray[i+1] = curLoad;
                curLoad.lastLoad = lastLoadArray[p[i]+1];
            }
            else{
                opt[i+1] = opt[i];
                lastLoadArray[i+1] = lastLoadArray[i];
            }
        }
        System.out.println("time taken:" + (System.currentTimeMillis() - time));
        return lastLoadArray[lastLoadArray.length-1];
    }

    public double getProfit(Vertex start, Load lastLoad){
        Load curLoad = lastLoad;
        double netProfit = 0;
        while(true){
            netProfit += curLoad.profit;
            if (curLoad.lastLoad != null){
                netProfit -= Vertex.fuelCost(curLoad.start, curLoad.lastLoad.destination);
                curLoad = curLoad.lastLoad;
            }
            else {
                break;
            }
        }
        netProfit -= Vertex.fuelCost(start, curLoad.start);
        return netProfit;
    }
}

