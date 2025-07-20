package org.homework.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommandRequest {
    String description;
    String priority;
    String author;
    String time;
}
