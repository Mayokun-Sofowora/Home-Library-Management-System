package com.library.domain;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents an owner of bibliographic artifacts in the library system.
 */
@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerId;
    private String name;

    @OneToMany(mappedBy = "owner")
    private List<BibliographicArtifact> ownedItems;

    /**
     * Default constructor.
     */
    public Owner() {
    }

    /**
     * Adds an owned item to the owner's list of owned items.
     * @param item The bibliographic artifact to be added.
     */
    public void addOwnedItem(BibliographicArtifact item) {
        // Add the item to the owner's list of owned items
        this.ownedItems.add(item);
        // Set the owner of the item to this owner
        item.setOwner(this);
    }

    /**
     * Removes an owned item from the owner's list of owned items.
     * @param item The bibliographic artifact to be removed.
     */
    public void removeOwnedItem(BibliographicArtifact item) {
        // Remove the item from the owner's list of owned items
        this.ownedItems.remove(item);
        // Set the owner of the item to null
        item.setOwner(null);
    }

    // Getters and Setters

    /**
     * Retrieves the unique identifier of the owner.
     * @return The unique identifier of the owner.
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the unique identifier of the owner.
     * @param ownerId The unique identifier to be set.
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Retrieves the name of the owner.
     * @return The name of the owner.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the owner.
     * @param name The name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the list of bibliographic artifacts owned by the owner.
     * @return The list of owned items.
     */
    public List<BibliographicArtifact> getOwnedItems() {
        return ownedItems;
    }

    /**
     * Sets the list of bibliographic artifacts owned by the owner.
     * @param ownedItems The list of owned items to be set.
     */
    public void setOwnedItems(List<BibliographicArtifact> ownedItems) {
        this.ownedItems = ownedItems;
    }
}
