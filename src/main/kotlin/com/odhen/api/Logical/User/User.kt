package com.odhen.api.Logical.User

import org.hibernate.validator.constraints.Length
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(
        uniqueConstraints = [
            UniqueConstraint(
                    name = "UniqueEmail",
                    columnNames = ["email"]
            )
        ]
)
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        val id: Long?,

        @Column(name = "email")
        @Email(message = "*Please provide a valid Email")
        @NotEmpty(message = "*Please provide an email")
        var email: String,

        @Column(name = "password")
        @Length(min = 5, message = "*Your password must have at least 5 characters")
        @NotEmpty(message = "*Please provide your password")
        var password: String,

        @NotNull
        @Enumerated(EnumType.STRING)
        var role: Role
) {
    enum class Role(val value: String) {
        USER("USER"),
        ADMIN("ADMIN"),
        DEVELOPER("DEVELOPER")
    }

    constructor(): this(null, "", "", Role.USER)
}