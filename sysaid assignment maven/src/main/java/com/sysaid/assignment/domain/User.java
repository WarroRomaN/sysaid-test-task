package com.sysaid.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * User class represents a user in the system.
 *
 * This class is Serializable, allowing instances
 * of User to be converted into a byte stream for
 * storage or transmission.
 *
 * This class provides the following features:
 * - Getters for the username field
 * - Auto-generated toString() method
 *
 * Example usage:
 *
 * // Creating a new User instance
 * User user = new User("john.doe");
 *
 * // Accessing the username field
 * String username = user.getUsername();
 * System.out.println("Username: " + username);
 */
@Getter
@AllArgsConstructor
@ToString
public class User implements Serializable {

    private String username;

}
