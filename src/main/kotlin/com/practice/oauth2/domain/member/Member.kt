package com.practice.oauth2.domain.member

import jakarta.persistence.*


@Entity
class Member(
    val email: String,
    name: String,
    password: String,
    @Enumerated(EnumType.STRING) @Column(name = "Role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = [JoinColumn(name = "member_id")])
    val roles: MutableList<Role>,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    var password: String = password
    private set
    var name: String = name
    private set
}