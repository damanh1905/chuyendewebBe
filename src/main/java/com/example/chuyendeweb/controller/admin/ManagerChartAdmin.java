package com.example.chuyendeweb.controller.admin;

import com.example.chuyendeweb.entity.OrderEntity;
import com.example.chuyendeweb.model.response.AdminChartCiResponse;
import com.example.chuyendeweb.model.response.AdminChartResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.service.IOrderDetailService;
import com.example.chuyendeweb.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chart/admin")
public class ManagerChartAdmin {
    @Autowired
    private IOrderService iOrderService;
    //chart colum order
    @GetMapping("/getChartDayMonth")
    public ResponseEntity<?> getChartDayMonth(@RequestParam(required = false) int month,@RequestParam int year) throws ParseException {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
        }
//        List<AdminChartResponse> entityList = iOrderService.getChartDayMonth( month,year);
        AdminChartCiResponse entityList = iOrderService.getChartDayMonth( month,year);
//        List<String> title = new ArrayList<>();
//        List<Double> data = new ArrayList<>();
//        for (Map.Entry<String, Double> entry : entityList.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//            title.add(entry.getKey());
//            data.add(entry.getValue());
//        }
//        AdminChartCiResponse result = new AdminChartCiResponse(title,data);
        if(entityList != null ){
            System.out.println("aaaaa");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(), "show listChart successful!", entityList));

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("không tìm thấy đơn hàng nào");

    }
    //chart colum order
    @GetMapping("/getChartDay")
    public ResponseEntity<?> getChartDay(@RequestParam(required = false) int day,int month) throws ParseException {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
        }
        List<AdminChartResponse> entityList = iOrderService.getChartDay(day,month);
        if(entityList != null ){
            System.out.println("aaaaa");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(), "show listChart successful!", entityList));

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("không tìm thấy đơn hàng nào");

    }
    //chart orderDetail
    @GetMapping("/getChartMonthCi")
    public ResponseEntity<?> getChartMonthCi(@RequestParam(required = false) int month,@RequestParam int year){
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
        }
        Map<String, Double> entityList = iOrderService.getChartDayMonthCi( month,year);
        List<String> title = new ArrayList<>();
        List<Double> data = new ArrayList<>();
        for (Map.Entry<String, Double> entry : entityList.entrySet()) {
				System.out.println(entry.getKey() + " " + entry.getValue());
            title.add(entry.getKey());
            data.add(entry.getValue());
			}
        AdminChartCiResponse result = new AdminChartCiResponse(title,data);

        if(entityList != null ){
            System.out.println("aaaaa");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(), "show listChart successful!", result));

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("không tìm thấy đơn hàng nào");

    }
}
