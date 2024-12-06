package com.linkaster.messageHandler.model.p2p;

import com.linkaster.messageHandler.model.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 *  Title: PrivateChatReg.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PrivateChatReg {
    private UserInfo user1;
    private UserInfo user2;
}
