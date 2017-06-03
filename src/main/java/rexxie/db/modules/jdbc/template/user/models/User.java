package rexxie.db.modules.jdbc.template.user.models;

public class User {
    public static final String ID = "id";
    public static final String EMAIL = "email";

    private Integer id;
    private String email;

    private User() {
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public static Builder createBuilder() {
        return new User().new Builder();
    }

    public class Builder {
        public Builder setId(Integer id) {
            User.this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            User.this.email = email;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}
