package resource;

/**
 * @author Riccardo Forzan
 */
public class TypeOfRoles {

    private final String role;

    public TypeOfRoles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    /**
     * Get the roles available
     *
     * @return A string concatenated with the possible roles
     */
    @Override
    public String toString() {
        return "TypeOfRoles{" +
            "role='" + role + '\'' +
            '}';
    }
}

