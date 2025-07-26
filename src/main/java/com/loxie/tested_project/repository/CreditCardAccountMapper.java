package com.loxie.tested_project.repository;

import com.loxie.tested_project.entity.CreditCardAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CreditCardAccountMapper {

        @Select("SELECT * FROM credit_card_account WHERE card_no = #{cardNo,jdbcType=VARCHAR}")
        @Results({
                        @Result(property = "cardNo", column = "card_no"),
                        @Result(property = "personName", column = "person_name"),
                        @Result(property = "usableAmount", column = "usable_amount"),
                        @Result(property = "overAmount", column = "over_amount"),
                        @Result(property = "cardPin", column = "card_pin"),
                        @Result(property = "cardExpiredDate", column = "card_expired_date"),
                        @Result(property = "cerType", column = "cer_type"),
                        @Result(property = "cerNo", column = "cer_no"),
                        @Result(property = "trxZoneno", column = "trx_zoneno"),
                        @Result(property = "cardStatus", column = "card_status"),
                        @Result(property = "dailyLimit", column = "daily_limit")
        })
        CreditCardAccount findByCardNo(@Param("cardNo") String cardNo);

        int updateAccountBalance(CreditCardAccount account);
}
