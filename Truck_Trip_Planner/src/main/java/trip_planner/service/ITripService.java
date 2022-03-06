package trip_planner.service;

import java.util.List;

import trip_planner.entity.Load;
import trip_planner.entity.Trucker;

/**
 * The interface to handle the money issues
 * @author kaiwenxu
 */
public interface ITripService {
	
	/**
	 * @param trucker
	 * @return the list of loads that are most profitable for the particular trucker's trip
	 */
	List<Load> findBestValidLoads(Trucker trucker);
	
	

}
