package com.woowacourse.tecobrary.user.command.domain;

import com.woowacourse.tecobrary.common.model.Email;
import com.woowacourse.tecobrary.common.model.HttpsUrl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Embeddable
public class UserGithubInfo {

    @Column(name = "github_id",
            unique = true)
    private String githubId;

    @Embedded
    @Column(name = "name")
    private UserName name;

    @Embedded
    @Column(name = "email")
    private Email email;

    @Embedded
    @Column(name = "avatar_url")
    private HttpsUrl avatarUrl;

    public UserGithubInfo(String githubId, UserName name, Email email, HttpsUrl avatarUrl) {
        this.githubId = githubId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    String updateName(String newName) {
        this.name = new UserName(newName);
        return name.getName();
    }

    String getEmailContent() {
        return email.getEmail();
    }

    String getNameContent() {
        return name.getName();
    }

    String getAvatarUrlContent() {
        return avatarUrl.getUrl();
    }
}
