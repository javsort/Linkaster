package com.linkaster.messageHandler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrivateChatSeedDTO {
    private String destEmail;
    private String requesterId;
}
