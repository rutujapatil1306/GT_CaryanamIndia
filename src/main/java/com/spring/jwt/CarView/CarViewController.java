package com.spring.jwt.CarView;

import com.spring.jwt.CarView.DTO.CarViewDto;
import com.spring.jwt.CarView.DTO.CarViewResponseDto;
import com.spring.jwt.CarView.DTO.CarViewResponseDto2;
import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cars/carView")
public class CarViewController {

    @Autowired
    CarViewService carViewService;

    @Autowired
    UserRepository userRepository;

//    @PostMapping("/saveUserCarViews")
//    public ResponseEntity<CarViewResponseDto> saveUserCarView(@RequestParam Integer userId,
//                                                              @RequestParam Integer carId)
//    {
//
//        CarViewDto view = carViewService.saveUserCarView(userId, carId);
//        return ResponseEntity.ok(new CarViewResponseDto("Saved Car Views Successfully", "Success", HttpStatus.CREATED, null));
//    }
//
//    @GetMapping("/getCarViewCountByUser")
//    public ResponseEntity<CarViewResponseDto2> getCarViewCountByUser(@RequestParam Integer userId,
//                                                                     @RequestParam Integer carId)
//    {
//        CarViewDto view = carViewService.getCarViewCountByUser(userId,carId);
//        return ResponseEntity.ok(new CarViewResponseDto2("View count for car at Id " + carId + " by user at Id : " +userId, view, "Success", HttpStatus.OK, null));
//    }

}
