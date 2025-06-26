package roomescape.repository;

import org.springframework.stereotype.Component;
import roomescape.model.entity.Member;

import java.util.List;

@Component
public class MemberRepository {
    private final List<Member> members;

    public MemberRepository(final List<Member> members) {
        this.members = members;
    }

    public boolean isMemberExist(Member member) {
        return this.members.contains(member);
    }

    public void saveMember(final Member member) {
        members.add(member);
    }

}
