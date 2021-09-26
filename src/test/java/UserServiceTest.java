
import dto.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import paramresolver.UserServiceParamResolver;
import service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//the order of method calls is undefined in JUnit5 but we can use @TestMethodOrder
//Random, Order (depends on @Order(1)), Name (alpahbate order), DisplayName

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
@ExtendWith({
        UserServiceParamResolver.class
})
class UserServiceTest { // now may be non only "public"

    UserService userService;

    public UserServiceTest(TestInfo testInfo) { // now we can use constructors in test classes / DI

    }

    @BeforeAll // must be static
    static void beforeAll() {
        System.out.println("before all");
    }

    @BeforeEach
    void prepare(UserService userService) { // example of Dependency Injection
        this.userService = userService;
    }

    @Test
    @Tag("tag1")// tags
    @DisplayName("If users not added")
    void usersEmptyIfNoUserAdded() { //now may be non only "public" and "test" string in signature is not needed
        assertTrue(userService.getAll().isEmpty());
    }

    @Test
    @Tag("tag2")
    void usersSizeIfUserAdded() {
        userService.addUser(new User());
        userService.addUser(new User());
        assertEquals(2, userService.getAll().size());
    }

    @AfterEach
    void afterEach() {
        System.out.println("after test");
    }

    @AfterAll // must be static
    static void afterAll() {
        System.out.println("after all");
    }

    @Tag("login")
    @Nested
    class LoginTest {
        @Test
        void loginSuccessIfUserExists() {
            userService.addUser(new User("Ivan", "123"));
            Optional<User> mayBeUser = userService.login("Ivan", "123");
            assertTrue(mayBeUser.isPresent());
        }

        @Test
            //@Test(expected=IllegalArgumentException.class) // в JUnit4
        void throwExceptionIfNotFound() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class, () -> userService.login(null, null)),
                    () -> assertThrows(IllegalArgumentException.class, () -> userService.login("123", null))
            );
        }
    }
}
