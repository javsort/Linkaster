package com.linkaster.userService.dto.message;

import com.linkaster.userService.dto.message.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PrivateChatReg {
    private UserInfo user1;
    private UserInfo user2;
}
