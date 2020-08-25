package com.example.test.practice.ifs;

import com.example.test.practice.model.network.Header;

// 중복해서 사용하므로 interface로 만들어서 사용
public interface CrudInterface<Req, Res> {

    Header<Res> create(Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> request);

    Header delete(Long id);
}
