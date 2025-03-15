package de.district.api.entity;


import de.district.api.DistrictAPI;
import de.splatgames.aether.permissions.api.Permission;
import de.splatgames.aether.permissions.api.PermissionManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * The {@code Permissible} interface represents an object that can have permissions and groups assigned to it.
 * This interface provides methods to interact with the permissions and groups of the object, including checking
 * for permissions, setting permissions, and adding or removing the object from groups.
 *
 * <p>Implementations of this interface are expected to manage permissions and groups for objects that require
 * permission-based access control, such as players or entities in a game environment.</p>
 *
 * @see PermissionManager
 * @see Permission
 * @since 1.0.0
 */
public interface Permissible {

    /**
     * Retrieves the unique identifier of the object.
     *
     * @return the unique identifier of the object, never {@code null}.
     */
    @NotNull
    UUID getUniqueId();

    /**
     * Checks whether the object has the specified permission.
     *
     * @param permission the permission to check for, never {@code null}.
     * @return {@code true} if the object has the permission, {@code false} otherwise.
     */
    default boolean hasPermission(@NotNull final String permission) {
        return DistrictAPI.getPermissionManager().hasPermission(getUniqueId(), permission);
    }

    /**
     * Checks whether the object has the specified permission.
     *
     * @param permission the permission to check for, never {@code null}.
     * @return {@code true} if the object has the permission, {@code false} otherwise.
     */
    default boolean hasExplicitPermission(@NotNull final String permission) {
        return DistrictAPI.getPermissionManager().hasExplicitPermission(getUniqueId(), permission);
    }

    /**
     * Retrieves the effective value of the specified permission for the object.
     * This method returns the value of the permission, taking into account the object's permissions and groups.
     *
     * @param permission the permission to retrieve the value for, never {@code null}.
     * @param <T>        the type of the permission value.
     * @return an {@link Optional} containing the value of the permission, or an empty {@link Optional} if the permission is not set.
     */
    @NotNull
    default <T> Optional<T> getEffectivePermissionValue(@NotNull final String permission) {
        return DistrictAPI.getPermissionManager().getEffectivePermissionValue(getUniqueId(), permission);
    }

    /**
     * Retrieves the value of the specified permission for the object.
     * This method returns the value of the permission, taking into account only the object's permissions.
     *
     * @param permission the permission to retrieve the value for, never {@code null}.
     * @param <T>        the type of the permission value.
     * @return an {@link Optional} containing the value of the permission, or an empty {@link Optional} if the permission is not set.
     */
    @NotNull
    default <T> Optional<T> getPermissionValue(@NotNull final String permission) {
        return DistrictAPI.getPermissionManager().getPermissionValue(getUniqueId(), permission);
    }

    /**
     * Sets the value of the specified permission for the object.
     * This method sets the value of the permission for the object, overriding any existing value.
     *
     * @param permission the permission to set the value for, never {@code null}.
     * @param value      the value to set for the permission, may be {@code null}.
     * @param <T>        the type of the permission value.
     * @return this {@code Permissible} object, for chaining.
     */
    @NotNull
    default <T> Permissible setPermission(@NotNull final String permission, @Nullable final T value) {
        DistrictAPI.getPermissionManager().setPermission(getUniqueId(), permission, value);
        return this;
    }

    /**
     * Revokes the specified permission from the object.
     * This method revokes the permission from the object, removing any existing value.
     *
     * @param permission the permission to revoke, never {@code null}.
     * @return this {@code Permissible} object, for chaining.
     */
    @NotNull
    default Permissible revokePermission(@NotNull final String permission) {
        DistrictAPI.getPermissionManager().revokePermission(getUniqueId(), permission);
        return this;
    }

    /**
     * Adds the specified permission to the object.
     * This method adds the permission to the object, allowing the object to perform actions that require the permission.
     *
     * @param permission the permission to add, never {@code null}.
     * @return this {@code Permissible} object, for chaining.
     */
    @NotNull
    default Permissible removePermission(@NotNull final String permission) {
        DistrictAPI.getPermissionManager().removePermission(getUniqueId(), permission);
        return this;
    }

    /**
     * Adds the object to the specified group.
     * This method adds the object to the group, allowing the object to inherit permissions from the group.
     *
     * @param group the group to add the object to, never {@code null}.
     * @return this {@code Permissible} object, for chaining.
     */
    @NotNull
    default Permissible addUserToGroup(@NotNull final String group) {
        DistrictAPI.getPermissionManager().addUserToGroup(getUniqueId(), group);
        return this;
    }

    /**
     * Removes the object from the specified group.
     * This method removes the object from the group, revoking any permissions inherited from the group.
     *
     * @param group the group to remove the object from, never {@code null}.
     * @return this {@code Permissible} object, for chaining.
     */
    @NotNull
    default Permissible removeUserFromGroup(@NotNull final String group) {
        DistrictAPI.getPermissionManager().removeUserFromGroup(getUniqueId(), group);
        return this;
    }

    /**
     * Retrieves the groups that the object is a member of.
     *
     * @return a {@link Set} containing the names of the groups that the object is a member of, never {@code null}.
     */
    @NotNull
    default Set<String> getGroups() {
        return DistrictAPI.getPermissionManager().getGroups(getUniqueId());
    }

    /**
     * Checks whether the object is a member of the specified group.
     *
     * @param group the group to check for, never {@code null}.
     * @return {@code true} if the object is a member of the group, {@code false} otherwise.
     */
    default boolean hasGroup(@NotNull final String group) {
        return DistrictAPI.getPermissionManager().hasGroup(getUniqueId(), group);
    }

    /**
     * Checks whether the object is a member of the specified group.
     *
     * @param group the group to check for, never {@code null}.
     * @return {@code true} if the object is a member of the group, {@code false} otherwise.
     */
    default boolean hasExplicitGroup(@NotNull final String group) {
        return DistrictAPI.getPermissionManager().hasExplicitGroup(getUniqueId(), group);
    }

    /**
     * Retrieves the permissions that are set for the object.
     *
     * @return a {@link Set} containing the permissions that are set for the object, never {@code null}.
     */
    @NotNull
    default Set<Permission<?>> getPermissions() {
        return DistrictAPI.getPermissionManager().getPermissions(getUniqueId());
    }

    /**
     * Retrieves the permission with the specified name for the object.
     *
     * @param permission the name of the permission to retrieve, never {@code null}.
     * @param <T>        the type of the permission value.
     * @return an {@link Optional} containing the permission, or an empty {@link Optional} if the permission is not set.
     */
    @NotNull
    default <T> Optional<Permission<T>> getPermission(@NotNull final String permission) {
        return DistrictAPI.getPermissionManager().getPermission(getUniqueId(), permission);
    }
}
