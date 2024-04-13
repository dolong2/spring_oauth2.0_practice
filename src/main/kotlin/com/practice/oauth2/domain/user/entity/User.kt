package com.practice.oauth2.domain.user.entity

import com.practice.oauth2.domain.user.entity.enums.Role
import com.practice.oauth2.domain.user.entity.enums.SocialType
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.UUID

@Entity
class User(
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val name: String,
    val password: String?,
    @Column(unique = true)
    val socialId: String,
    @Enumerated(EnumType.STRING)
    val socialType: SocialType,
    val profileImgUrl: String,
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = [JoinColumn(name = "user_id")])
    val roles: List<Role>
) {
}