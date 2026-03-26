package com.jacobhampton.techtest.user.model;

import com.jacobhampton.techtest.user.dto.UserAddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;

    private UserAddressDto address;

    private String phoneNumber;

    @Indexed(unique = true)
    private String email;

    @CreatedDate
    private Instant createdTimestamp;

    @LastModifiedDate
    private Instant updatedTimestamp;

}
