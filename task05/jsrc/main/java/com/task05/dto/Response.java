package com.task05.dto;

import com.task05.model.Event;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Response
{
    private String statusCode;
    private Event event;
}
