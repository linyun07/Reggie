package com.linyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyun.pojo.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author linyun
 * @date 2023/04/04/20:58
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

    /**
     * 根据id查寻地址信息
     * @param id
     * @return
     */
    @Select("select *from address_book where id=#{id}")
    AddressBook selectAddressBookById(Long id);
}
