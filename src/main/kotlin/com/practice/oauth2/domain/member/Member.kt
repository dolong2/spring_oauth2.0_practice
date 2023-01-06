package com.practice.oauth2.domain.member

import com.practice.oauth2.global.entity.BaseIdEntity
import jakarta.persistence.*


@Entity
class Member(
    val email: String,
    val name: String,
    val provider: String,
    val picture: String,
    @Enumerated(EnumType.STRING) @Column(name = "Role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = [JoinColumn(name = "member_id")])
    val roles: MutableList<Role>,
): BaseIdEntity(){
    fun update(name: String, email: String): Member =
        Member(
            email = email,
            name = name,
            provider = this.provider,
            roles = this.roles,
            picture = this.picture,
        )
}