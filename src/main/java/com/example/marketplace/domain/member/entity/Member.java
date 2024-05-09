package com.example.marketplace.domain.member.entity;

import com.example.marketplace.domain.member.dto.request.CreateMemberRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "oauth_id_unique",
                        columnNames = {"oauth_server_id", "oauth_server"}
                )
        }
)
@AttributeOverrides({
        @AttributeOverride(name="oauthId.oauthServerId", column=@Column(name="oauth_server_id")),
        @AttributeOverride(name="oauthId.oauthServerType", column=@Column(name="oauth_server"))
})
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    private String email;

    private String password;

    private String name;

    private String profileImageUrl;

    private String nickname;

    private String address;

    private String gender;

    private String phoneNumber;

    private Long age;

//    @OneToMany(fetch = FetchType.LAZY)
//    private Set<Review> reviews = new HashSet<>();
//
//    @OneToMany(fetch = FetchType.LAZY)
//    private Set<Coupon> coupons = new HashSet<>();
//
//    @OneToMany(fetch = FetchType.LAZY)
//    private Set<Order> orders = new HashSet<>();
//
//    @OneToOne
//    private Wishlist wishlist;
//
//    @OneToOne
//    private Cart cart;

    @Embedded
    private OauthId oauthId;

    public static Member createMember(CreateMemberRequestDto dto) {
        Member member = new Member();
        member.loginId = dto.getLoginId();
        member.name = dto.getMemberName();
        member.email = dto.getEmail();
        member.password = dto.getPassword();
        return member;
    }
}
