package com.example.common.model;

import com.example.common.enums.MessageRoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SeatUser extends User {

    private Integer role= MessageRoleEnum.SEAT.getCode();
}
