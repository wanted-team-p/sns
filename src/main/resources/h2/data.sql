INSERT INTO POST(title, type, content, view_count, share_count, like_count, created_at, updated_at)
values ('제목1', 'FACEBOOK', '근로자는 근로조건의 향상을 위하여 자주적인 단결권·단체교섭권 및 단체행동권을 가진다.', 10, 20, 30, now(), now());
INSERT INTO POST(title, type, content, view_count, share_count, like_count, created_at, updated_at)
values ('제목2', 'FACEBOOK', '국무총리는 국무위원의 해임을 대통령에게 건의할 수 있다. 모든 국민은 보건에 관하여 국가의 보호를 받는다.', 0, 0, 0, TIMESTAMPADD(hour,1,now()), TIMESTAMPADD(hour,1,now()));
INSERT INTO POST(title, type, content, view_count, share_count, like_count, created_at, updated_at)
values ('제목3', 'FACEBOOK', '모든 국민은 법률이 정하는 바에 의하여 선거권을 가진다. 지방자치단체는 주민의 복리에 관한 사무를 처리하고 재산을 관리하며, 법령의 범위안에서 자치에 관한 규정을 제정할 수 있다.', 20, 20, 10, TIMESTAMPADD(hour,2,now()), TIMESTAMPADD(hour,2,now()));
INSERT INTO POST(title, type, content, view_count, share_count, like_count, created_at, updated_at)
values ('제목1', 'INSTAGRAM', '대통령은 법률안의 일부에 대하여 또는 법률안을 수정하여 재의를 요구할 수 없다.', 0, 0, 0, TIMESTAMPADD(hour,3,now()), TIMESTAMPADD(hour,4,now()));
INSERT INTO POST(title, type, content, view_count, share_count, like_count, created_at, updated_at)
values ('제목2', 'TWITTER', '대통령의 선거에 관한 사항은 법률로 정한다. 감사원은 원장을 포함한 5인 이상 11인 이하의 감사위원으로 구성한다.', 20, 20, 10, TIMESTAMPADD(day,1,now()), TIMESTAMPADD(day,1,now()));

INSERT INTO HASHTAG(name) values('해시태그1');
INSERT INTO HASHTAG(name) values('해시태그2');
INSERT INTO HASHTAG(name) values('해시태그3');

INSERT INTO HASHTAG_MAPPING(SEQ_POST, SEQ_HASHTAG) values(1,1);
INSERT INTO HASHTAG_MAPPING(SEQ_POST, SEQ_HASHTAG) values(1,2);
INSERT INTO HASHTAG_MAPPING(SEQ_POST, SEQ_HASHTAG) values(1,3);
INSERT INTO HASHTAG_MAPPING(SEQ_POST, SEQ_HASHTAG) values(2,1);
INSERT INTO HASHTAG_MAPPING(SEQ_POST, SEQ_HASHTAG) values(2,2);
INSERT INTO HASHTAG_MAPPING(SEQ_POST, SEQ_HASHTAG) values(3,3);
INSERT INTO HASHTAG_MAPPING(SEQ_POST, SEQ_HASHTAG) values(4,2);
INSERT INTO HASHTAG_MAPPING(SEQ_POST, SEQ_HASHTAG) values(5,1);