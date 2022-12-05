package com.practice.oauth2.domain.member

import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate


@Entity
@DynamicUpdate
class Member(
    val email: String,
    val name: String,
    @Enumerated(EnumType.STRING) @Column(name = "Role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = [JoinColumn(name = "member_id")])
    val roles: MutableList<Role>,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0
}