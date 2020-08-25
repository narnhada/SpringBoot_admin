package com.example.test.practice.controller;

// 주소들의 묶음  Controller

import com.example.test.practice.model.SearchParam;
import com.example.test.practice.model.network.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")//  localhost:8080/api
public class GetController {
    @RequestMapping(method = RequestMethod.GET, path = "/getMethod") //localhost:8080/api/getMethod
    public String getRequest() {

        return "Hi getMethod";
    }

    @GetMapping("/getParameter")    //localhost:8080/api/getParameter?id=1234&password=abcd
    public String getParameter(@RequestParam String id, @RequestParam(name = "password") String pwd) {
//        String password = "bbbb"; local변수와 parameter가 꼭 같아야 할경우 pwd처럼 변경

        System.out.println("id:" + id);
        System.out.println("pwd:" + pwd);

        return (id + " " + pwd);
    }

    //localhost:8080/api/getMultiParameter?account=abcd&email=Email@Email.com&page=10

    @GetMapping("/getMultiParameter")
    public SearchParam getMultiParameter(SearchParam searchParam) {
        System.out.println(searchParam.getAccount());
        System.out.println(searchParam.getEmail());
        System.out.println(searchParam.getPage());

        // { "account: "", "email":"" , "page": 0}
        return searchParam;
    }

    @GetMapping("/header")
    public Header getHeader() {
        // {"resultCode: "OK" , "description" : "OK"}
        return Header.builder().resultCode("OK").description("OK").build();
    }
}
