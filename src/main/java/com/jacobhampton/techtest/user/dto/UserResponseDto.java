package com.jacobhampton.techtest.user.dto;

import com.jacobhampton.techtest.user.model.User;

public record UserResponseDto(
        String id,
        String name,
        UserAddressDto address,
        String phoneNumber,
        String email,
        String createdTimestamp,
        String updatedTimestamp
) {

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getCreatedTimestamp().toString(),
                user.getUpdatedTimestamp().toString()
        );
    }

}
