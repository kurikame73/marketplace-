package com.example.marketplace.domain.member.entity;

import com.example.marketplace.domain.cart.entity.Cart;
import com.example.marketplace.domain.member.dto.request.AddInformationMemberRequestDto;
import com.example.marketplace.domain.member.dto.request.ChangeMemberInfoRequestDto;
import com.example.marketplace.domain.member.dto.request.ChangePasswordRequestDto;
import com.example.marketplace.domain.member.dto.request.CreateMemberRequestDto;
import com.example.marketplace.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
//
//    @OneToOne
//    private Wishlist wishlist;
//
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Embedded
    private OauthId oauthId;

    public Member(String loginId, String email) {
        this.loginId = loginId;
        this.email = email;
    }

    public static Member createMember(CreateMemberRequestDto dto) {
        Member member = new Member();
        member.loginId = dto.getLoginId();
        member.name = dto.getMemberName();
        member.email = dto.getEmail();
        member.password = dto.getPassword();
        member.cart = new Cart();
        member.orders = new ArrayList<>();
        return member;
    }

    public void changePassword(ChangePasswordRequestDto dto, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(dto.getNewPassword());
    }

    public void addInformationMember(AddInformationMemberRequestDto dto) {
        this.email = dto.getEmail();
        this.name = dto.getMemberName();
        this.phoneNumber = dto.getPhoneNumber();
        this.loginId = dto.getLoginId();
        // 등등..
    }

    public void changeMemberInfo(ChangeMemberInfoRequestDto dto) {
        this.email = dto.getEmail();
        this.name = dto.getMemberName();
        this.phoneNumber = dto.getPhoneNumber();
        this.loginId = dto.getLoginId();
        // 등등..
    }
}
