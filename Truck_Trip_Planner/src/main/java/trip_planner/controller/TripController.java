package trip_planner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trip_planner.entity.Load;
import trip_planner.entity.Trucker;
import trip_planner.service.ITripService;
import trip_planner.util.ResponseResult;

@RestController
@RequestMapping("/trip")
public class TripController extends BaseController{

	@Autowired
	private ITripService tripService;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/plan_trip.do")
	public ResponseResult<List<Load>> handlePlanTripAnalysis(Trucker trucker){
		
		//debug
		System.out.println(trucker);
		
		//Get one piece of trucker information from the front end
		List<Load> result = tripService.findBestValidLoads(trucker);
		System.out.println("Resulting list of loads are: ");
		for (Load load : result) {
			System.out.println(load);
		}
		ResponseResult<List<Load>> rr = null;
		
		if(result!=null) {
			rr = new ResponseResult<List<Load>>(SUCCESS);
			rr.setData(result);
		}else {
			rr = new ResponseResult<List<Load>>(0);
		}
		
		return rr;
	}
	
	
}
