package trip_planner.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trip_planner.entity.Load;
import trip_planner.entity.Trucker;
import trip_planner.entity.Vertex;
import trip_planner.mapper.QueryMapper;
import trip_planner.service.ITripService;
import trip_planner.util.TripPlanner;

@Service
public class TripServiceImpl implements ITripService{

	/**
	 * use the autowired annotation to let the xml file
	 * to automatically fill into the function defined
	 * in the queryMapper interface
	 */
	@Autowired
	private QueryMapper queryMapper;

	@Override
	public List<Load> findBestValidLoads(Trucker trucker) {
		
		List<Load> possibleLoads = queryMapper.findValidLoadsByStart(trucker.getStartTime(), trucker.getMaxDestTime());
		
		System.out.println(possibleLoads.size());
		System.out.println(possibleLoads.get(0));
		//Initialize the trip planner with trucker's start time (so to get rid of some useless data)
		TripPlanner tp = new TripPlanner(possibleLoads);
		
		//Compute the loads that will help make the most profit
		Integer[] loadIds = tp.plan(new Vertex(trucker.getStartLatitude(), trucker.getStartLongitude()), trucker.getStartTime(), trucker.getMaxDestTime());
		
		//Print the array of loadIds
		System.out.println(Arrays.toString(loadIds));
		
		/*
		 * The list of loadIds are already sorted. So just add them in sequence.
		 */
		List<Load> bestLoads = new ArrayList<Load>();
		for(int i=0; i<loadIds.length; i++) {
			bestLoads.add(fetchLoadById(loadIds[i]));
		}
		
		return bestLoads;
	}
	
	/**
	 * 
	 * @param loadId
	 * @return the load with that ID
	 */
	public Load fetchLoadById(Integer loadId) {
		return queryMapper.findLoadById(loadId);
	}
	
	
	
}
