package trip_planner.mapper;

import java.time.LocalDateTime;
import java.util.List;

import trip_planner.entity.Load;

public interface QueryMapper {
	
	/**
	 * 
	 * @return a list of all the money spent at that specific date
	 */
	List<Load> findValidLoadsByStart(LocalDateTime startDateTime, LocalDateTime maxDestTime);
	
	/**
	 * @return a list of all the money spent at that specific date
	 */
	Load findLoadById(Integer loadId);

}
