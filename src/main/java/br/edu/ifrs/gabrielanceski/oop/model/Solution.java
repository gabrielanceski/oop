package br.edu.ifrs.gabrielanceski.oop.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String reportedIssue;

    private String foundIssue;
    @Column(name = "chassis", length = 64)
    private String chassis;
    @Column(columnDefinition = "TEXT")
    private String cause;
    @Column(columnDefinition = "TEXT")
    private String techSolution;
    private SolutionStatus status;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}