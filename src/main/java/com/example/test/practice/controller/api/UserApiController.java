package com.example.test.practice.controller.api;

import com.example.test.practice.ifs.CrudInterface;
import com.example.test.practice.model.network.Header;
import com.example.test.practice.model.network.request.UserApiRequest;
import com.example.test.practice.model.network.response.UserApiResponse;
import com.example.test.practice.model.network.response.UserOrderInfoApiResponse;
import com.example.test.practice.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 로그 남겨줌
@RestController
@RequestMapping("/api/user")
public class UserApiController implements CrudInterface <UserApiRequest,UserApiResponse> {

    @Autowired
    private UserApiLogicService userApiLogicService;

    @GetMapping("/{id}/orderInfo")
    public Header<UserOrderInfoApiResponse> orderInfo(@PathVariable Long id){
        return userApiLogicService.orderInfo(id);

    }

    @GetMapping("")
    public Header<List<UserApiResponse>> search(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        log.info("{}", pageable);
        return userApiLogicService.search(pageable);

    }


    @Override
    @PostMapping("")            // /api/user
    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> request) {
        log.info("{}", request);
        return userApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")         // /api/user/{id}
    public Header<UserApiResponse> read(@PathVariable(name = "id") Long id) {

        log.info("read id : {}", id);
        return userApiLogicService.read(id);
    }

    @Override
    @PutMapping("")             // /api/user
    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> request) {

        return userApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")      // /api/user/{id}
    public Header delete(@PathVariable Long id) {
        log.info("delete : {}", id);
        return userApiLogicService.delete(id);
    }
}
