package resource;

/**
 * A resource representing the role
 *
 * @author Riccardo Forzan
 */
public class TypeOfRoles {

    private final String role;

    /**
     * Initializes this class
     * @param role  the role
     */
    public TypeOfRoles(String role) {
        this.role = role;
    }

    /**
     * Gets the role
     * @return  the role
     */
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

