package com.jacobhampton.techtest.user.service;

import com.jacobhampton.techtest.BaseIntegrationTest;
import com.jacobhampton.techtest.account.model.Account;
import com.jacobhampton.techtest.account.model.AccountType;
import com.jacobhampton.techtest.account.model.Currency;
import com.jacobhampton.techtest.account.repo.AccountRepository;
import com.jacobhampton.techtest.account.service.CodeService;
import com.jacobhampton.techtest.auth.context.AuthContext;
import com.jacobhampton.techtest.shared.exception.AccessDeniedException;
import com.jacobhampton.techtest.shared.exception.ResourceNotFoundException;
import com.jacobhampton.techtest.user.dto.CreateUserRequestDto;
import com.jacobhampton.techtest.user.dto.UpdateUserRequestDto;
import com.jacobhampton.techtest.user.dto.UserAddressDto;
import com.jacobhampton.techtest.user.dto.UserResponseDto;
import com.jacobhampton.techtest.user.model.User;
import com.jacobhampton.techtest.user.repo.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CodeService codeService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CreateUserRequestDto createUserRequestDto;

    @AfterEach
    void tearDown() {
        AuthContext.clear();
        userRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Nested
    @DisplayName("Create User")
    public class CreateUser {

        @Test
        @DisplayName("User should be created successfully when a valid non-duplicate request is provided")
        public void whenCreateUserIsRequestedAndValidNonDuplicateRequestIsProvided_thenUserIsCreatedSuccessfully() {
            UserResponseDto response = userService.createUser(createUserRequestDto);

            assertThat(response.id()).isNotNull();
            assertThat(response.name()).isEqualTo("Bruce Wayne");
            assertThat(response.email()).isEqualTo("bruce@wayneindustries.com");
            assertThat(response.phoneNumber()).isEqualTo("+447777777777");
        }

        @Test
        @DisplayName("User creation should be refused if a duplicate email is provided")
        public void whenCreateUserIsRequestedAndDuplicateEmailIsProvided_thenExceptionIsThrown() {
            userService.createUser(createUserRequestDto);

            CreateUserRequestDto duplicateEmailRequest = new CreateUserRequestDto(
                    "Clark Kent",
                    "securePassword123",
                    testUserAddress(),
                    "+447000000000",
                    "bruce@wayneindustries.com");

            assertThatThrownBy(() -> userService.createUser(duplicateEmailRequest))
                    .isInstanceOf(Exception.class)
                    .hasMessageContaining("email");
        }

        @Test
        @DisplayName("User creation should hash the password before persisting to the database")
        void whenUserIsCreated_thenPasswordIsHashed() {
            UserResponseDto response = userService.createUser(createUserRequestDto);
            User persisted = userRepository.findById(response.id()).orElseThrow();

            boolean passwordMatches = encoder.matches(createUserRequestDto.password(), persisted.getPassword());

            assertThat(passwordMatches)
                    .as("stored password should verify against the original password")
                    .isTrue();
        }

    }

    @Nested
    @DisplayName("Get User")
    public class GetUser {

        @Test
        @DisplayName("User should be able to retrieve their own details when authenticated")
        public void whenUserIsAuthenticatedAndRequestsTheirUser_thenUserDetailsAreReturned() {
            User seeded = seedUser();
            authenticateAs(seeded.getId());

            UserResponseDto response = userService.getUser(seeded.getId());

            assertThat(response.id()).isEqualTo(seeded.getId());
            assertThat(response.name()).isEqualTo(seeded.getName());
            assertThat(response.email()).isEqualTo(seeded.getEmail());
        }

        @Test
        @DisplayName("User should not be able to retrieve another user's details even if authenticated")
        public void whenUserRequestsAnotherUsersData_thenAccessDeniedExceptionIsThrown() {
            User seeded = seedUser();
            authenticateAs("different-user-id");

            assertThatThrownBy(() -> userService.getUser(seeded.getId()))
                    .isInstanceOf(AccessDeniedException.class)
                    .hasMessageContaining("Unauthorized");
        }

        @Test
        @DisplayName("User should receive not found error if they try to retrieve a non-existent user even if authenticated")
        public void whenUserIsNotFound_thenResourceNotFoundExceptionIsThrown() {
            authenticateAs("nonexistent-id");

            assertThatThrownBy(() -> userService.getUser("nonexistent-id"))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining("User not found");
        }

    }

    @Nested
    @DisplayName("Update User")
    public class UpdateUser {

         @Test
         @DisplayName("User should be able to partially update their profile if authenticated")
         public void whenUserIsAuthenticatedAndPartiallyUpdatesTheirUser_thenOnlyProvidedFieldsAreUpdated() {
            User seeded = seedUser();
            authenticateAs(seeded.getId());

            final String updatedName = "Updated Name!";

            UpdateUserRequestDto partialUpdate = new UpdateUserRequestDto(
                     updatedName, null, null, null
            );

            UserResponseDto response = userService.updateUser(seeded.getId(), partialUpdate);
            assertThat(response.name()).isEqualTo(updatedName);
            assertThat(response.email()).isEqualTo("test@example.com");
            assertThat(response.phoneNumber()).isEqualTo("+447000000000");
            assertThat(response.address()).isEqualTo(testUserAddress());
            assertThat(response.createdTimestamp()).isNotEqualTo(seeded.getCreatedTimestamp().toString());
        }

        @Test
        @DisplayName("User should not be able to update a non-existent profile")
        public void whenUserIsNotFound_thenResourceNotFoundExceptionIsThrown() {
            authenticateAs("nonexistent-id");

            UpdateUserRequestDto update = new UpdateUserRequestDto(
                    "Name", null, null, null
            );

            assertThatThrownBy(() -> userService.updateUser("nonexistent-id", update))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining("User not found");
        }

        @Test
        @DisplayName("User should not be able to delete a profile that is not their own")
        public void whenUserTriesToUpdateAnotherUsersProfile_thenAccessDeniedExceptionIsThrown() {
            User seeded = seedUser();
            authenticateAs("different-user-id");

            UpdateUserRequestDto update = new UpdateUserRequestDto(
                    "Ivo Robotnik", null, null, null
            );

            assertThatThrownBy(() -> userService.updateUser(seeded.getId(), update))
                    .isInstanceOf(AccessDeniedException.class)
                    .hasMessageContaining("Unauthorized");
        }
    }

    @Nested
    @DisplayName("Delete User")
    public class DeleteUser {
        @Test
        @DisplayName("User should be deleted successfully when authed with correct user and has no accounts")
        public void whenUserIsAuthenticatedAndHasNoAccountsAndIsDeleted_thenUserIsRemovedFromDatabase() {
            User seeded = seedUser();
            authenticateAs(seeded.getId());
            userService.deleteUser(seeded.getId());
            assertThat(userRepository.findById(seeded.getId())).isEmpty();
        }

        @Test
        @DisplayName("User deletion should be refused if trying to delete another user even if authenticated")
        public void whenUserIsUnauthorizedAndTriesToDeleteAnotherUser_thenAccessDeniedExceptionIsThrown() {
            User seeded = seedUser();
            authenticateAs("different-user-id");
            assertThatThrownBy(() -> userService.deleteUser(seeded.getId()))
                    .isInstanceOf(AccessDeniedException.class);
        }

        @Test
        @DisplayName("User Deletion should be refused if the user has bank accounts even if correctly authed")
        public void whenUserIsAuthenticatedAndHasAccountsAndTriesToDeleteThemselves_thenAccessDeniedExceptionIsThrown() {
            User seeded = seedUser();
            authenticateAs(seeded.getId());
            seedAccount(seeded.getId());
            assertThatThrownBy(() -> userService.deleteUser(seeded.getId()))
                    .isInstanceOf(AccessDeniedException.class)
                    .hasMessageContaining("Cannot delete user with existing accounts");
        }

    }

    private UserAddressDto testUserAddress() {
        return new UserAddressDto(
                "11 Test Street", null, null,
                "Test Town", "Greater Testerton", "TE5 T3R"
        );
    }

    private User seedUser() {
        Instant now = Instant.now();
        return userRepository.save(new User(
                null,
                "Tess Terperson",
                encoder.encode("password123"),
                testUserAddress(),
                "+447000000000",
                "test@example.com",
                now,
                now
        ));
    }

    private void seedAccount(String userId) {
        accountRepository.save(new Account(
                null,
                userId,
                codeService.generateAccountNumber(),
                codeService.getSortCode(),
                "Test Account",
                AccountType.PERSONAL,
                0,
                Currency.GBP,
                Instant.now(),
                Instant.now()
        ));
    }

    private void authenticateAs(String userId) {
        AuthContext.set(new AuthContext(userId));
    }

}



