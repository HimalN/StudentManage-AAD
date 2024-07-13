package org.example.demo2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class studentDto implements Serializable {
    private String id;
    private String name;
    private String email;
    private String city;
    private String level;
}
