package web.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import web.user.domain.Address;
import web.user.domain.Role;
import web.user.domain.User;
import web.user.domain.UserRepository;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
public class UserRepoTest {

    @Autowired
    UserRepository memberRepository;

    @Test
    public void testMember() {
        User member = new User(1L, "abc.d", "nick", "pass", "na", "phon", "me", new Address("11", "22", "33"), Role.ROLE_USER, 10, "23", "12");
//               member = User.builder()
//                .address(new Address("11", "22", "33"))
//                .build();

        User savedMember = memberRepository.save(member);
        User findMember = memberRepository.findById(savedMember.getId()).get();

        Assertions.assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
//        Assertions.assertThat(findMember).isEqualTo(member);
    }


}