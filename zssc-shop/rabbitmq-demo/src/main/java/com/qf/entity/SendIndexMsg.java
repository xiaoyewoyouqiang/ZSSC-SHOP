package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendIndexMsg implements Serializable{

    private Integer id;

    private String text; // 消息的文本

    private Integer pid; // 该消息的上一个消息的id
}
