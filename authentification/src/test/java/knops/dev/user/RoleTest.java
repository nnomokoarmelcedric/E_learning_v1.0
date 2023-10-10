package knops.dev.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

    @Test
    public void testRoleEnumValues() {
        assertEquals("ADMIN", Role.ADMIN.name());
        assertEquals("TEACHER", Role.TEACHER.name());
        assertEquals("USER", Role.USER.name());
        assertEquals("STUDENT", Role.STUDENT.name());
    }
}
